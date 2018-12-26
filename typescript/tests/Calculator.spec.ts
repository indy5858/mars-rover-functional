import Calculator from "../src/Calculator";

describe("calculate", () => {
  it("add", () => {
    const result = Calculator.Sum(4, 2);
    expect(result).toBe(7);
  });
});
