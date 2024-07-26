package net.redmelon.fishandshiz.entity.variant;

public class ModVariants {
    public static void initializeVariants() {
        AngelfishPattern.init();
        AmurCarpPattern.init();
        BettaPattern.init();
        ClownfishPattern.init();
        TangPattern.init();
        GoatfishPattern.init();

        AmurCarpDetail.init();
        AngelfishDetail.init();
        BettaDetail.init();
        ClownfishDetail.init();
        TangDetail.init();
        GoatfishDetail.init();

        ModEntityColor.init();
    }
}
