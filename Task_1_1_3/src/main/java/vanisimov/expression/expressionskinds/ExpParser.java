package vanisimov.expression.expressionskinds;

import java.util.ArrayList;

class ExpParser {

    private ArrayList<String> tokens;
    private int parsePoint;
    private String st;

    ExpParser(String st) {
        this.tokens = new ArrayList<String>();
        this.parsePoint = 0;
        this.st = st;
    }

    Expression parse() {
        this.tokens = new ArrayList<String>();
        this.parsePoint = 0;
        this.tokenize();
        return this.parseHelper();
    }

    void tokenize() {
        char s;
        int atomBeg = -1, atomSize = 0;
        boolean isAtom = false;
        String st = this.st;
        for (int i = 0; i < this.st.length(); i++) {
            s = this.st.charAt(i);
            if (s == '(' || s == '*' || s == '/' || s == '+' || s == ')') {
                if (isAtom) {
                    this.tokens.add(st.substring(atomBeg, atomBeg + atomSize));
                    isAtom = false;
                }
                this.tokens.add(st.substring(i, i + 1));
            } else if (i >= 1 && s == '-' && !(st.charAt(i - 1) == '(')) {
                this.tokens.add(st.substring(i, i + 1));
            } else if (s == '-' && ((i >= 1 && st.charAt(i - 1) == '(') || i == 0)) {
                atomBeg = i;
                isAtom = true;
                atomSize = 1;
            } else if (Character.isDigit(st.charAt(i)) || Character.isLetter(st.charAt(i))) {
                if (!isAtom) {
                    atomSize = 1;
                    isAtom = true;
                    atomBeg = i;
                } else {
                    atomSize++;
                }
            } else {
                if (isAtom) {
                    this.tokens.add(st.substring(atomBeg, atomBeg + atomSize));
                    isAtom = false;
                }
            }
        }
        if (isAtom) {
            this.tokens.add(st.substring(atomBeg));
        }
    }

    private Expression parseHelper() {
        String token = this.tokens.get(this.parsePoint);
        char s = token.charAt(0);
        Expression arg1;
        if (s == '(') {
            this.parsePoint++;
            arg1 = this.parseHelper();
        } else if (Character.isDigit(s) || (s == '-' && token.length() > 1)) {
            arg1 = new Number(Integer.parseInt(token));
            this.parsePoint++;
        } else {
            arg1 = new Variable(token);
            this.parsePoint++;
        }
        if (!(this.parsePoint < this.tokens.size())) {
            return arg1;
        }
        token = this.tokens.get(this.parsePoint);
        s = token.charAt(0);
        if (s == ')') {
            this.parsePoint++;
            return arg1;
        }
        char binOp = s;
        this.parsePoint++;
        token = this.tokens.get(this.parsePoint);
        s = token.charAt(0);
        Expression arg2;
        if (s == '(') {
            this.parsePoint++;
            arg2 = this.parseHelper();
        } else if (s == '-' || Character.isDigit(s)) {
            arg2 = new Number(Integer.parseInt(token));
            this.parsePoint++;
        } else {
            arg2 = new Variable(token);
            this.parsePoint++;
        }
        this.parsePoint++;
        if (binOp == '+') {
            return new Add(arg1, arg2);
        } else if (binOp == '-') {
            return new Sub(arg1, arg2);
        } else if (binOp == '*') {
            return new Mul(arg1, arg2);
        } else {
            return new Div(arg1, arg2);
        }
    }
}