package com.ysmstudio.bcsddrawer.calculator;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Stack;

public class Calculator {
    private static final String errorMessage = "잘못된 수식";
    String expr;
    ArrayList<String> arrayList;

    public Calculator(String expr) {
        this.expr = expr;
        arrayList = new ArrayList<>();
        makeList();
        System.out.println(arrayList);
        arrayList = convertPostExpr();
        System.out.println(arrayList);
    }

    public void makeList() {
        StringBuilder stringBuilder = new StringBuilder();
        char[] exprChar = expr.toCharArray();
        char prev = ' ';

        for (char e : exprChar) {
            switch (e) {
                case '-':
                    if(prev == '+' || prev == '-' || prev == '*' || prev == '/' || prev == '(' || prev == ' ') {
                        arrayList.add("0");
                    }
                case '+':
                case '*':
                case '/':
                case '(':
                case ')':
                    if (stringBuilder.length() > 0)
                        arrayList.add(stringBuilder.toString());
                    stringBuilder = new StringBuilder();
                    arrayList.add(String.valueOf(e));
                    break;
                default:
                    stringBuilder.append(e);
                    break;
            }
            prev = e;
        }

        if (stringBuilder.length() > 0)
            arrayList.add(stringBuilder.toString());
    }

    public String calculate() {
        if (checkValidBraces()) {

            Stack<String> stringStack = new Stack<>();
            double result = 0.0;

            for (String s : arrayList) {
                double b, a;
                switch (s) {
                    case "+":
                        if (stringStack.empty()) return errorMessage;
                        b = Double.parseDouble(stringStack.pop());

                        if (stringStack.empty()) return errorMessage;
                        a = Double.parseDouble(stringStack.pop());

                        stringStack.push(String.valueOf(a + b));
                        break;
                    case "-":
                        if (stringStack.empty()) return errorMessage;
                        b = Double.parseDouble(stringStack.pop());

                        if (stringStack.empty()) return errorMessage;
                        a = Double.parseDouble(stringStack.pop());

                        stringStack.push(String.valueOf(a - b));
                        break;
                    case "*":
                        if (stringStack.empty()) return errorMessage;
                        b = Double.parseDouble(stringStack.pop());

                        if (stringStack.empty()) return errorMessage;
                        a = Double.parseDouble(stringStack.pop());

                        stringStack.push(String.valueOf(a * b));
                        break;
                    case "/":
                        if (stringStack.empty()) return errorMessage;
                        b = Double.parseDouble(stringStack.pop());

                        if (stringStack.empty()) return errorMessage;
                        a = Double.parseDouble(stringStack.pop());

                        stringStack.push(String.valueOf(a / b));
                        break;
                    default:
                        stringStack.push(s);
                        break;
                }
            }

            while (stringStack.size() > 1) {
                double b = Double.parseDouble(stringStack.pop());
                double a = Double.parseDouble(stringStack.pop());
                stringStack.push(String.valueOf(a * b));
            }

            if (stringStack.empty()) return errorMessage;

            DecimalFormat decimalFormat = new DecimalFormat("#.#########");

            return decimalFormat.format(Double.parseDouble(stringStack.pop()));
        } else return "ERROR";
    }

    private ArrayList<String> convertPostExpr() {
        ArrayList<String> postArrayList = new ArrayList<>();
        Stack<String> stringStack = new Stack<>();

        for (String s : arrayList) {
            switch (s) {
                case "(":
                    stringStack.push("(");
                    break;
                case ")":
                    while (stringStack.peek() != "(") {
                        postArrayList.add(stringStack.pop());
                    }
                    stringStack.pop();
                    break;
                case "+":
                case "-":
                case "*":
                case "/":
                    while (!stringStack.isEmpty() && priority(s) >= priority(stringStack.peek())) {
                        postArrayList.add(stringStack.pop());
                    }
                    stringStack.push(s);
                    break;
                default:
                    postArrayList.add(s);
                    break;
            }
        }

        while (!stringStack.isEmpty()) {
            postArrayList.add(stringStack.pop());
        }


        return postArrayList;
    }

    private int priority(String op) {
        switch (op) {
            case "+":
            case "-":
                return 1;
            case "*":
            case "/":
                return 0;
            case "(":
                return 2;
        }
        return -1;
    }

    private boolean checkValidBraces() {
        Stack<String> stackBraces = new Stack<>();
        for (String s : arrayList) {
            if (s.equals("(")) stackBraces.push(s);
            else if (s.equals(")")) {
                if (s.isEmpty()) return false;
                else stackBraces.pop();
            }
        }

        return stackBraces.empty();
    }

    public ArrayList<String> getArrayList() {
        return arrayList;
    }
}