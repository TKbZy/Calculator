package xzz;

import java.util.ArrayList;
import java.util.HashMap;

record WordScanner(String expression, ArrayList<Word> words,
                   HashMap<Character, Integer> map) {

    //错误信息
    private String errorMessage(String message, int i, int j) {
        return message + "  :   " +
            expression.substring(Math.max(0, i - 10), i) + "  -->  " + expression.substring(i, j) + "  <--  "
            + expression.substring(j, Math.min(j + 10, expression.length()));
    }

    public Result wordScan() {
        int n = expression.length();
        char[] s = expression.toCharArray();
        for (int i = 0; i < n; i++) {
            if (s[i] == ' ') continue;
            if ((s[i] == '+' || s[i] == '-') && (i == 0 || s[i - 1] == '(')) {
                if (i == n - 1 || !('0' <= s[i + 1] && s[i + 1] <= '9')) {
                    //开头的+/-号后面没有数字，如++1、+  1
                    return new Result(errorMessage("wrong format of operator/number", i, Math.min(n, i + 5)), "NAN");
                }
                int j = i + 1;
                while (j < n && ('0' <= s[j] && s[j] <= '9' || s[j] == '.')) j++;
                String sub = expression.substring(i, j);
                try {
                    Double.parseDouble(sub);
                    words.add(new Word(sub, i, 6));
                } catch (Exception _e) {
                    //数字不规范，如123.333.33
                    return new Result(errorMessage("wrong format of number", i, j), "NAN");
                }
                i = j - 1;
            } else if (s[i] == '+' || s[i] == '-' || s[i] == '*' || s[i] == '/' || s[i] == '(' || s[i] == ')') {
                words.add(new Word(String.valueOf(s[i]), i, map.get(s[i])));
            } else if ('0' <= s[i] && s[i] <= '9' || s[i] == '.') {
                int j = i + 1;
                while (j < n && ('0' <= s[j] && s[j] <= '9' || s[j] == '.')) j++;
                String sub = expression.substring(i, j);
                try {
                    Double.parseDouble(sub);
                    words.add(new Word(sub, i, 6));
                } catch (Exception _e) {
                    //数字不规范，如123.333.33
                    return new Result(errorMessage("wrong format of number", i, j), "NAN");
                }
                i = j - 1;
            } else {
                //其他字符，如abc...
                return new Result(errorMessage("unexpected character", i, i + 1), "NAN");
            }
        }
        return null;
    }
}
