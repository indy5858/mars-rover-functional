import Calculator from "../src/Calculator";

describe("calculate", () => {
  it("add", () => {
    const result = Calculator.sum(5, 2);
    expect(result).toBe(7);
  });
});
