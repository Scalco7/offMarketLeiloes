package com.backend.offMarketLeiloes.infrastructure.email;

import java.util.List;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import com.backend.offMarketLeiloes.application.features.scraper.events.ScrapingCompletedEvent;
import com.backend.offMarketLeiloes.domain.entities.Account;
import com.backend.offMarketLeiloes.domain.repositories.AccountRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class ScrapingNotificationListener {

    private final AccountRepository accountRepository;
    private final EmailService emailService;

    @EventListener
    public void handleScrapingCompleted(ScrapingCompletedEvent event) {
        log.info("Received ScrapingCompletedEvent for job {} (Site: {}). Notifying users...",
                event.getJobId(), event.getScraperSite());

        List<Account> accounts = accountRepository.findAll();

        if (accounts.isEmpty()) {
            log.warn("No accounts found to notify.");
            return;
        }

        String subject = "Novos imóveis encontrados";
        String htmlBody = String.format(
                "<!DOCTYPE html>" +
                        "<html>" +
                        "<head>" +
                        "  <meta charset='UTF-8'>" +
                        "  <meta name='viewport' content='width=device-width, initial-scale=1.0'>" +
                        "  <style>" +
                        "    body { font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif; background-color: #f4f7f9; margin: 0; padding: 0; -webkit-font-smoothing: antialiased; }"
                        +
                        "    .container { max-width: 600px; margin: 20px auto; background: #ffffff; border-radius: 12px; overflow: hidden; box-shadow: 0 4px 20px rgba(0,0,0,0.08); }"
                        +
                        "    .header { background: linear-gradient(45deg, #1867c0, #5cbbf6); color: white; padding: 40px 20px; text-align: center; }"
                        +
                        "    .header h1 { margin: 0; font-size: 26px; font-weight: 700; text-transform: uppercase; letter-spacing: 1px; }"
                        +
                        "    .content { padding: 40px; color: #333333; line-height: 1.6; text-align: center; }" +
                        "    .content p { font-size: 17px; margin-bottom: 20px; color: #555; }" +
                        "    .opportunity-box { background: #f8fafd; border-radius: 12px; padding: 25px; margin: 25px 0; border: 1px solid #e0e8f0; }"
                        +
                        "    .opportunity-box strong { color: #1867c0; font-size: 24px; display: block; margin: 10px 0; }"
                        +
                        "    .btn-container { margin-top: 35px; }" +
                        "    .btn { background-color: #1867c0; color: #ffffff !important; padding: 16px 40px; text-decoration: none; border-radius: 8px; font-weight: 700; font-size: 16px; display: inline-block; transition: transform 0.2s; box-shadow: 0 4px 12px rgba(24, 103, 192, 0.2); }"
                        +
                        "    .footer { background: #f4f7f9; color: #999; padding: 25px; text-align: center; font-size: 13px; border-top: 1px solid #eeeeee; }"
                        +
                        "  </style>" +
                        "</head>" +
                        "<body>" +
                        "  <div class='container'>" +
                        "    <div class='header'>" +
                        "      <h1>Mineração Concluída!</h1>" +
                        "    </div>" +
                        "    <div class='content'>" +
                        "      <p>Diversos imóveis foram encontrados, não perca essa chance!</p>" +
                        "      <div class='opportunity-box'>" +
                        "        Foram encontradas" +
                        "        <strong>%d</strong>" +
                        "        novas oportunidades." +
                        "      </div>" +
                        "      <div class='btn-container'>" +
                        "        <a href='https://frontend-production-6ccc.up.railway.app/' class='btn'>Confira antes dos outros!</a>"
                        +
                        "      </div>" +
                        "    </div>" +
                        "    <div class='footer'>" +
                        "      &copy; 2026 Off-Market Leilões. Todos os direitos reservados." +
                        "    </div>" +
                        "  </div>" +
                        "</body>" +
                        "</html>",
                event.getResultCount());

        for (Account account : accounts) {
            try {
                emailService.sendHtmlEmail(account.getEmail(), subject, htmlBody);
                log.info("Notification sent to {}", account.getEmail());
            } catch (Exception e) {
                log.error("Failed to notify user {}", account.getEmail(), e);
            }
        }
    }
}
