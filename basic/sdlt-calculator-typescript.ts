/**
 * SDLT (Stamp Duty Land Tax) Calculator for residential properties.
 */

interface BandBreakdown {
  band: string;
  tax: number;
}

/**
 * Represents a single SDLT tax band.
 */
class TaxBand {
  constructor(
    public readonly from: number,
    public readonly to: number | null,
    public readonly rate: number
  ) {}

  /**
   * Calculate tax owed in this band for a given property price.
   */
  calculate(price: number): number {
    if (price <= this.from) return 0;

    const upper = this.to !== null ? Math.min(price, this.to) : price;
    const taxable = upper - this.from;

    return Math.round(taxable * this.rate * 100) / 100;
  }

  toString(): string {
    const toStr = this.to !== null ? `£${this.to.toLocaleString()}` : "above";
    return `£${this.from.toLocaleString()} – ${toStr} @ ${this.rate * 100}%`;
  }
}

/**
 * Holds the result of an SDLT calculation.
 */
class SDLTResult {
  constructor(
    public readonly price: number,
    public readonly breakdown: BandBreakdown[],
    public readonly total: number
  ) {}

  print(): void {
    console.log(`\nSDLT Calculation for property price: £${this.price.toLocaleString()}`);
    console.log("─".repeat(55));

    for (const { band, tax } of this.breakdown) {
      if (tax > 0) {
        console.log(`  ${band.padEnd(40)} = £${tax.toLocaleString()}`);
      }
    }

    console.log("─".repeat(55));
    console.log(`  Total SDLT: £${this.total.toLocaleString()}`);
    console.log(`  Effective rate: ${((this.total / this.price) * 100).toFixed(2)}%\n`);
  }
}

/**
 * SDLT Calculator using configurable tax bands.
 */
class SDLTCalculator {
  private readonly bands: TaxBand[];

  constructor() {
    this.bands = [
      new TaxBand(0,         125_000,   0.00),  // 0%
      new TaxBand(125_000,   250_000,   0.02),  // 2%
      new TaxBand(250_000,   925_000,   0.05),  // 5%
      new TaxBand(925_000,   1_500_000, 0.10),  // 10%
      new TaxBand(1_500_000, null,      0.12),  // 12%
    ];
  }

  /**
   * Calculate SDLT for a given property price.
   */
  calculate(price: number): SDLTResult {
    if (!Number.isInteger(price) || price < 0) {
      throw new Error("Price must be a non-negative integer.");
    }

    const breakdown: BandBreakdown[] = this.bands.map((band) => ({
      band: band.toString(),
      tax: band.calculate(price),
    }));

    const total = breakdown.reduce((sum, b) => sum + b.tax, 0);

    return new SDLTResult(price, breakdown, total);
  }
}

// ── Run ──────────────────────────────────────────────

const calculator = new SDLTCalculator();

// Example from the brief
calculator.calculate(295_000).print();

// Additional examples
calculator.calculate(125_000).print();
calculator.calculate(500_000).print();
calculator.calculate(1_000_000).print();
calculator.calculate(2_000_000).print();
