package com.ysmstudio.bcsddrawer;

import java.util.ArrayList;
import java.util.Stack;

public class Calculator {
    String expr;
    ArrayList<String> arrayList;

    public Calculator(String expr) {
        this.expr = expr;
        arrayList = new ArrayList<>();
        makeList();
        System.out.println(arrayList);
        arrayList = convertPostExpr();
    }

    public void makeList() {
        StringBuilder stringBuilder = new StringBuilder();
        char[] exprChar = expr.toCharArray();

        for (char e : exprChar) {
            switch (e) {
                case '+':
                case '-':
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
                        b = Double.parseDouble(stringStack.pop());
                        a = Double.parseDouble(stringStack.pop());

                        stringStack.push(String.valueOf(a + b));
                        break;
                    case "-":
                        b = Double.parseDouble(stringStack.pop());
                        a = Double.parseDouble(stringStack.pop());

                        stringStack.push(String.valueOf(a - b));
                        break;
                    case "*":
                        b = Double.parseDouble(stringStack.pop());
                        a = Double.parseDouble(stringStack.pop());

                        stringStack.push(String.valueOf(a * b));
                        break;
                    case "/":
                        b = Double.parseDouble(stringStack.pop());
                        a = Double.parseDouble(stringStack.pop());

                        stringStack.push(String.valueOf(a / b));
                        break;
                    default:
                        stringStack.push(s);
                        break;
                }
            }

            return stringStack.pop();
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