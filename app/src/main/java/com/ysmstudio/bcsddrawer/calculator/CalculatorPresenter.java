package com.ysmstudio.bcsddrawer.calculator;

public class CalculatorPresenter {
    private CalculatorView calculatorView;
    private Calculator calculator;

    public CalculatorPresenter(CalculatorView calculatorView) {
        this.calculatorView = calculatorView;
    }

    public void calculate(String expr) {
        Calculator calculator = new Calculator(expr);
        calculatorView.showCalculatedResult(calculator.calculate());
    }
}