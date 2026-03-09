public class Main {
    public static void main(String[] args) {
        SDLTCalculator calculator = new SDLTCalculator();

        calculator.calculate(295_000).print();
        calculator.calculate(125_000).print();
        calculator.calculate(500_000).print();
        calculator.calculate(1_000_000).print();
        calculator.calculate(2_000_000).print();
    }
}