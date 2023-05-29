package xzz;

import java.util.ArrayDeque;
import java.util.ArrayList;

record LRAnalyzer(String expression, ArrayList<Word> words) {
    //action、goto表  0代表出错、-1代表规约、1-9代表状态
    private static final int[][] ACTION = {
        {0, 0, 0, 0, 6, 0, 7, 0, 1},
        {2, 3, 4, 5, 0, 0, 0, 100, 0},
        {0, 0, 0, 0, 6, 0, 7, 0, 10},
        {0, 0, 0, 0, 6, 0, 7, 0, 10},
        {0, 0, 0, 0, 6, 0, 7, 0, 10},
        {0, 0, 0, 0, 6, 0, 7, 0, 10},
        {0, 0, 0, 0, 6, 0, 7, 0, 8},
        {-1, -1, -1, -1, -1, -1, -1, -1, 0},
        {2, 3, 4, 5, 0, 9, 0, 0, 0},
        {-1, -1, -1, -1, -1, -1, -1, -1, 0},
        {-1, -1, -1, -1, -1, -1, -1, -1, 0}
    };

    private static final String e1 = "need a number";
    private static final String e2 = "need a operator";
    private static final String e3 = "wrong format of brackets";
    private static final String e4 = "unexpected end";

    //错误表
    private static final String[][] ERROR = {
        {e1, e1, e1, e1, "", e1, "", e4, ""},
        {"", "", "", "", e2, e3, e2, "", e2},
        {e1, e1, e1, e1, "", e3, "", e4, ""},
        {e1, e1, e1, e1, "", e3, "", e4, ""},
        {e1, e1, e1, e1, "", e3, "", e4, ""},
        {e1, e1, e1, e1, "", e3, "", e4, ""},
        {e1, e1, e1, e1, "", e3, "", e4, ""},
        {},
        {"", "", "", "", e3, "", e2, e3, e2},
        {},
        {}
    };


    //错误信息
    private String errorMessage(String message, int i, int j) {
        return message + "  :   " +
            expression.substring(Math.max(0, i - 10), i) + "  -->  " + expression.substring(i, j) + "  <--  "
            + expression.substring(j, Math.min(j + 10, expression.length()));
    }

    public Result LRAnalyze() {
        //状态栈
        ArrayDeque<Integer> stack = new ArrayDeque<>() {{
            add(0);
        }};

        int nextState;
        words.add(new Word("", expression.length(), 7));
        for (Word word : words) {
            nextState = ACTION[stack.getLast()][word.id];

            if (nextState == 0) {
                int i = word.pos, j = word.s.length();
                return new Result(errorMessage(ERROR[stack.getLast()][word.id], i, i + j), "NAN");
            } else if (nextState == -1) {
                while (true) {
                    int lastState = stack.getLast();
                    if (lastState == 7) {
                        stack.pollLast();
                    } else {
                        stack.pollLast(); stack.pollLast(); stack.pollLast();
                    }
                    nextState = ACTION[stack.getLast()][8];
                    if (nextState == 0) {
                        int i = word.pos, j = word.s.length();
                        return new Result(errorMessage(ERROR[stack.getLast()][word.id], i, i + j), "NAN");
                    }
                    stack.addLast(nextState);
                    nextState = ACTION[stack.getLast()][word.id];
                    if (nextState == 0) {
                        int i = word.pos, j = word.s.length();
                        return new Result(errorMessage(ERROR[stack.getLast()][word.id], i, i + j), "NAN");
                    }
                    if (nextState != -1) {
                        stack.addLast(nextState);
                        break;
                    }
                }
            } else {
                stack.addLast(nextState);
            }
        }

        return null;
    }
}
