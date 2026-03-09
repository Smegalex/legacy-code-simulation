public class TaxBand {
    private final int lower;
    private final Integer upper; // null means no upper limit
    private final double rate;

    public TaxBand(int lower, Integer upper, double rate) {
        this.lower = lower;
        this.upper = upper;
        this.rate = rate;
    }

    public double calculate(int price) {
        if (price <= lower) return 0.0;

        int top = (upper != null) ? Math.min(price, upper) : price;
        int taxable = top - lower;

        return Math.round(taxable * rate * 100.0) / 100.0;
    }

    @Override
    public String toString() {
        String upperStr = (upper != null)
                ? String.format("£%,d", upper)
                : "above";
        return String.format("£%,d – %s @ %.0f%%", lower, upperStr, rate * 100);
    }
}