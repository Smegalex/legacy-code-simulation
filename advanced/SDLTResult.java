import java.util.List;
import java.util.Map;

public class SDLTResult {
    private final int price;
    private final List<Map.Entry<String, Double>> breakdown;
    private final double total;

    public SDLTResult(int price, List<Map.Entry<String, Double>> breakdown, double total) {
        this.price = price;
        this.breakdown = breakdown;
        this.total = total;
    }

    public int getPrice() { return price; }
    public double getTotal() { return total; }
    public List<Map.Entry<String, Double>> getBreakdown() { return breakdown; }

    public void print() {
        System.out.printf("%nSDLT Calculation for property price: £%,d%n", price);
        System.out.println("─".repeat(55));

        for (var entry : breakdown) {
            if (entry.getValue() > 0) {
                System.out.printf("  %-40s = £%,.2f%n", entry.getKey(), entry.getValue());
            }
        }

        System.out.println("─".repeat(55));
        System.out.printf("  Total SDLT: £%,.2f%n", total);
        System.out.printf("  Effective rate: %.2f%%%n%n", (total / price) * 100);
    }
}

