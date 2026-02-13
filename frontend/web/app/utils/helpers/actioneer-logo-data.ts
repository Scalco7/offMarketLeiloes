import klocknerLogo from "~/assets/actioneers-logos/klockner.png";

interface IActioneerLogoData {
  backgroundColor: string;
  logoPath: string;
}

export const actioneerLogoData: Record<string, IActioneerLogoData> = {
  KlocknerLeiloes: {
    backgroundColor: "#f1dcdcc1",
    logoPath: klocknerLogo,
  },
};
