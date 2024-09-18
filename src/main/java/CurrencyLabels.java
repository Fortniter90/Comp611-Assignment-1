public class CurrencyLabels {
    private static String[] labels; // Static Labels So That Every Case Uses The Same Labels

    // Default Labels
    static {
        labels = new String[]{"NZD", "GBP", "JPY", "AUD", "CAD", "CHF", "HKD"};
    }

    // Sets New Labels
    public static void setLabels(String[] labels) {
            CurrencyLabels.labels = labels;
    }

    // Gets Current Labels
    public static String[] getLabels(){
        return labels;
    }

}
