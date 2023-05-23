package xzz;

import java.util.ArrayList;
import java.util.HashMap;

//单词、位置、单词的属性
class Word {
    String s;
    int pos;
    int id;

    public Word(String s, int pos, int id) {
        this.s = s;
        this.pos = pos;
        this.id = id;
    }
}
class Result {
    String compileResult, calculateResult;

    public Result(String compileResult, String calculateResult) {
        this.compileResult = compileResult;
        this.calculateResult = calculateResult;
    }
}

class Handler {
    //每个词的类型
    private static final HashMap<Character, Integer> map = new HashMap<>();
    static {
        map.put('+', 0);
        map.put('-', 1);
        map.put('*', 2);
        map.put('/', 3);
        map.put('(', 4);
        map.put(')', 5);
        // number --> 6
        //$ --> 7
        //E --> 8
    }

    //用户输入的表达式
    private final String expression;
    //单词串
    private final ArrayList<Word> words = new ArrayList<>();

    public Handler(String expression) {
        this.expression = expression;
    }

    public Result getResult() {
        Result scanResult = wordScan();

        if (scanResult == null) return LRAnalyzeAndCalculate();  //返回空代表扫描成功
        else return scanResult;   //扫描失败
    }

    //读取单词、词法分析
    private Result wordScan() {
        return (new WordScanner(expression, words, map)).wordScan();
    }

    //语法分析并计算
    private Result LRAnalyzeAndCalculate() {
        Result LRResult = (new LRAnalyzer(expression, words)).LRAnalyze();
        if (LRResult != null) return LRResult;

        words.remove(words.size() - 1);

        return (new Calculator(words, map)).calculate();
    }
}
