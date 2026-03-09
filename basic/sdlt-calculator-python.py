"""
SDLT (Stamp Duty Land Tax) Calculator for residential properties.
"""


class TaxBand:
    """Represents a single SDLT tax band."""

    def __init__(self, lower: int, upper: int | None, rate: float):
        self.lower = lower
        self.upper = upper  # None means no upper limit
        self.rate = rate

    def calculate(self, price: int) -> float:
        """Calculate tax owed in this band for a given property price."""
        if price <= self.lower:
            return 0.0

        top = min(price, self.upper) if self.upper is not None else price
        taxable = top - self.lower

        return round(taxable * self.rate, 2)

    def __str__(self) -> str:
        upper_str = f"£{self.upper:,}" if self.upper is not None else "above"
        return f"£{self.lower:,} – {upper_str} @ {self.rate * 100:.0f}%"


class SDLTResult:
    """Holds the result of an SDLT calculation."""

    def __init__(self, price: int, breakdown: list[dict], total: float):
        self.price = price
        self.breakdown = breakdown
        self.total = total

    def print(self) -> None:
        print(f"\nSDLT Calculation for property price: £{self.price:,}")
        print("─" * 55)

        for entry in self.breakdown:
            if entry["tax"] > 0:
                print(f"  {entry['band']:<40} = £{entry['tax']:,.2f}")

        print("─" * 55)
        print(f"  Total SDLT: £{self.total:,.2f}")
        print(f"  Effective rate: {(self.total / self.price) * 100:.2f}%\n")


class SDLTCalculator:
    """SDLT Calculator using configurable tax bands."""

    def __init__(self):
        self.bands = [
            TaxBand(0,         125_000,   0.00),  # 0%
            TaxBand(125_000,   250_000,   0.02),  # 2%
            TaxBand(250_000,   925_000,   0.05),  # 5%
            TaxBand(925_000,   1_500_000, 0.10),  # 10%
            TaxBand(1_500_000, None,      0.12),  # 12%
        ]

    def calculate(self, price: int) -> SDLTResult:
        """Calculate SDLT for a given property price."""
        if not isinstance(price, int) or price < 0:
            raise ValueError("Price must be a non-negative integer.")

        breakdown = [
            {"band": str(band), "tax": band.calculate(price)}
            for band in self.bands
        ]

        total = sum(entry["tax"] for entry in breakdown)

        return SDLTResult(price, breakdown, total)


# ── Run ──────────────────────────────────────────────

if __name__ == "__main__":
    calculator = SDLTCalculator()

    # Example from the brief
    calculator.calculate(295_000).print()

    # Additional examples
    calculator.calculate(125_000).print()
    calculator.calculate(500_000).print()
    calculator.calculate(1_000_000).print()
    calculator.calculate(2_000_000).print()
