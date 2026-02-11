package com.backend.offMarketLeiloes.infrastructure.scraping.auctionScraper.providers;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.springframework.stereotype.Component;

import com.backend.offMarketLeiloes.application.common.utils.DateParser;
import com.backend.offMarketLeiloes.application.common.utils.NumberParser;
import com.backend.offMarketLeiloes.application.features.properties.commands.createBatch.viewModels.CreatePropertyAddressRequest;
import com.backend.offMarketLeiloes.application.features.properties.commands.createBatch.viewModels.CreatePropertyRequest;
import com.backend.offMarketLeiloes.infrastructure.scraping.auctionScraper.AuctionScraper;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import com.microsoft.playwright.options.AriaRole;
import com.microsoft.playwright.options.LoadState;
import java.time.LocalDateTime;

import com.backend.offMarketLeiloes.domain.enums.EPropertyStatus;
import com.backend.offMarketLeiloes.domain.enums.EPropertyType;
import com.backend.offMarketLeiloes.domain.enums.EScraperSites;

@Component
public class KlocknerLeiloesScraper implements AuctionScraper {
        private final String auctioneerName = "KlocknerLeiloes";
        private final String url = "https://www.kleiloes.com.br/busca?tipo=Im%C3%B3veis";

        @Override
        public List<CreatePropertyRequest> scrape() {
                List<CreatePropertyRequest> properties = new ArrayList<>();

                try (Playwright playwright = Playwright.create()) {
                        Browser browser = playwright.chromium().launch();
                        Page page = browser.newPage();
                        page.navigate(url);

                        Integer totalPages = page.locator("ul.default-pagination").getByRole(AriaRole.LISTITEM).all()
                                        .size();
                        System.out.println("(" + auctioneerName + ") Total de páginas: " + totalPages);

                        for (int actualPage = 1; actualPage <= totalPages; actualPage++) {
                                String urlWithPage = url + "&page=" + actualPage;
                                if (actualPage > 1) {
                                        page.navigate(urlWithPage);
                                        page.waitForLoadState(LoadState.NETWORKIDLE);
                                }
                                System.out.println("(" + auctioneerName + ") Página atual: " + actualPage);
                                List<Locator> pageProperties = page.getByRole(AriaRole.TABLE)
                                                .getByRole(AriaRole.ROWGROUP)
                                                .getByRole(AriaRole.ROW)
                                                .filter(new Locator.FilterOptions().setHasNotText("Descrição")).all();
                                System.out.println("(" + auctioneerName + ") Elementos encontrados: "
                                                + pageProperties.size());

                                for (int i = 0; i < pageProperties.size(); i++) {
                                        pageProperties.get(i).getByRole(AriaRole.LINK).first().click();
                                        page.waitForLoadState(LoadState.NETWORKIDLE);

                                        String auctionLink = page.url();
                                        String[] nameAndDescription = page.locator("div.div-descricao").textContent()
                                                        .split("Observação:");
                                        String name = nameAndDescription[0].trim();
                                        String description = nameAndDescription[1].trim();

                                        String[] nameSplitedBySlash = name.split("/");
                                        String state = nameSplitedBySlash.length > 1
                                                        ? nameSplitedBySlash[1].trim().replace(".", "").toUpperCase()
                                                        : null;

                                        String imageLink = page.getByTitle("imagem").getAttribute("src");

                                        Locator tableElements = page.getByRole(AriaRole.TABLE)
                                                        .getByRole(AriaRole.ROWGROUP)
                                                        .getByRole(AriaRole.ROW);

                                        String valuedPriceStr = tableElements
                                                        .filter(new Locator.FilterOptions()
                                                                        .setHasText("Valor de Avaliação"))
                                                        .locator("td").nth(1).textContent().trim();
                                        Pattern currentPriceRegex = Pattern.compile("Valor Inicial|Valor 1º Leilão");
                                        String currentPriceStr = tableElements
                                                        .filter(new Locator.FilterOptions()
                                                                        .setHasText(currentPriceRegex))
                                                        .locator("td").nth(1).textContent().trim();
                                        String typeStr = tableElements
                                                        .filter(new Locator.FilterOptions().setHasText("Tipo do Bem"))
                                                        .locator("td").nth(1).textContent().trim();
                                        String statusStr = tableElements
                                                        .filter(new Locator.FilterOptions().setHasText("Status"))
                                                        .locator("td").nth(1).textContent().trim();

                                        Locator dateLocator = page.locator("table.table").getByRole(AriaRole.ROWGROUP)
                                                        .getByRole(AriaRole.ROW).getByRole(AriaRole.CELL).nth(1);
                                        String rawText = dateLocator.textContent();

                                        String auctionDateTimeStr;
                                        Boolean isDateTime = !rawText.contains("Até");
                                        if (isDateTime) {
                                                Pattern dateRegex = Pattern.compile("1º Leilão|Leilão único");
                                                auctionDateTimeStr = dateLocator.getByText(dateRegex).textContent()
                                                                .replaceAll("1º Leilão|[()]|Leilão único", "")
                                                                .trim().replaceAll("\\s+", " ");
                                        } else {
                                                auctionDateTimeStr = dateLocator.getByText("Até").textContent()
                                                                .replaceAll("Até", "").trim();
                                        }

                                        Double valuedPrice = NumberParser.parsePrice(valuedPriceStr);
                                        Double currentPrice = NumberParser.parsePrice(currentPriceStr);
                                        LocalDateTime auctionDateTime = isDateTime
                                                        ? DateParser.parseDateTime(auctionDateTimeStr)
                                                        : DateParser.parseDate(auctionDateTimeStr).atStartOfDay();
                                        EPropertyType type = EPropertyType.parsePropertyType(typeStr);
                                        EPropertyStatus status = EPropertyStatus.parsePropertyStatus(statusStr);

                                        CreatePropertyAddressRequest address = new CreatePropertyAddressRequest();
                                        address.setState(state);
                                        address.setCountry("Brasil");

                                        CreatePropertyRequest property = new CreatePropertyRequest();
                                        property.setAddress(address);
                                        property.setAuctionDateTime(auctionDateTime);
                                        property.setAuctionLink(auctionLink);
                                        property.setAuctioneerName(auctioneerName);
                                        property.setDescription(description);
                                        property.setImageLink(imageLink);
                                        property.setName(name);
                                        property.setValuedPrice(valuedPrice);
                                        property.setCurrentPrice(currentPrice);
                                        property.setType(type.name());
                                        property.setStatus(status.name());

                                        properties.add(property);
                                        System.out.println("(" + auctioneerName + ") Imóvel adicionado: "
                                                        + property.getName());

                                        page.navigate(urlWithPage);
                                }
                        }
                } catch (Exception e) {
                        System.err.println("(" + auctioneerName + ") Erro ao minerar: " + e.getMessage());
                        System.err.println("(" + auctioneerName + ") Imóveis minerados antes do erro: "
                                        + properties.size());
                }

                System.out.println("(" + auctioneerName + ") Imóveis minerados: " + properties.size());
                return properties;
        }

        @Override
        public boolean supports(EScraperSites site) {
                return site == EScraperSites.KLOCKNER_LEILOES;
        }
}
