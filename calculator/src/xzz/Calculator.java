package xzz;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;

record Calculator(ArrayList<Word> words, HashMap<Character, Integer> map) {

    public Result calculate() {
        //后缀表达式，数组形式
        ArrayList<String> postfixExpression = new ArrayList<>();
        //后缀表达式，字符串形式
        StringBuilder compileResult = new StringBuilder();
        //符号栈
        ArrayDeque<String> operators = new ArrayDeque<>();
        //确认语法正确后生成后缀表达式
        for (Word word : words) {
            switch (word.s) {
                case "+" -> {
                    while (!operators.isEmpty() && !operators.getLast().equals("(") && map.get(operators.getLast().charAt(0)) > 0) {
                        postfixExpression.add(operators.pollLast());
                    }
                    operators.addLast("+");
                }
                case "-" -> {
                    while (!operators.isEmpty() && !operators.getLast().equals("(") && map.get(operators.getLast().charAt(0)) > 1) {
                        postfixExpression.add(operators.pollLast());
                    }
                    operators.addLast("-");
                }
                case "*" -> operators.addLast("*");
                case "/" -> operators.addLast("/");
                case "(" -> operators.addLast("(");
                case ")" -> {
                    while (!operators.getLast().equals("(")) {
                        postfixExpression.add(operators.pollLast());
                    }
                    operators.pollLast();
                }
                default -> postfixExpression.add(word.s);
            }
        }
        while (!operators.isEmpty()) postfixExpression.add(operators.pollLast());

        //根据后缀表达式计算
        ArrayDeque<Double> stack = new ArrayDeque<>();

        for (String s : postfixExpression) {
            compileResult.append(s).append("   ");

            switch (s) {
                case "+":
                    double d1 = stack.pollLast(), d2 = stack.pollLast();
                    stack.addLast(d2 + d1);
                    break;
                case "-":
                    d1 = stack.pollLast();
                    d2 = stack.pollLast();
                    stack.addLast(d2 - d1);
                    break;
                case "*":
                    d1 = stack.pollLast();
                    d2 = stack.pollLast();
                    stack.addLast(d2 * d1);
                    break;
                case "/":
                    d1 = stack.pollLast();
                    d2 = stack.pollLast();
                    stack.addLast(d2 / d1);
                    break;
                default:
                    stack.add(Double.parseDouble(s));
                    break;
            }
        }
        return new Result(compileResult.toString(), String.valueOf(stack.getLast()));
    }
}
