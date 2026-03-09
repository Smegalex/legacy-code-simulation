import java.util.AbstractMap;
import java.util.List;
import java.util.stream.Collectors;

public class SDLTCalculator {
    private final List<TaxBand> bands = List.of(
        new TaxBand(0,         125_000,   0.00),
        new TaxBand(125_000,   250_000,   0.02),
        new TaxBand(250_000,   925_000,   0.05),
        new TaxBand(925_000,   1_500_000, 0.10),
        new TaxBand(1_500_000, null,      0.12)
    );

    public SDLTResult calculate(int price) {
        if (price < 0) {
            throw new IllegalArgumentException("Price must be a non-negative integer.");
        }

        List<Map.Entry<String, Double>> breakdown = bands.stream()
                .map(band -> new AbstractMap.SimpleEntry<>(band.toString(), band.calculate(price)))
                .collect(Collectors.toList());

        double total = breakdown.stream()
                .mapToDouble(Map.Entry::getValue)
                .sum();

        return new SDLTResult(price, breakdown, total);
    }
}
