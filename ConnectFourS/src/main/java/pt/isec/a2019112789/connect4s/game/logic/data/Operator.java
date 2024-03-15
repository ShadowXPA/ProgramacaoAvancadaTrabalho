package pt.isec.a2019112789.connect4s.game.logic.data;

public enum Operator {
    Plus('+') {
        @Override
        public int calculate(int a, int b) {
            return a + b;
        }
    },
    Minus('-') {
        @Override
        public int calculate(int a, int b) {
            return a - b;
        }
    },
    Multiply('*') {
        @Override
        public int calculate(int a, int b) {
            return a * b;
        }
    },
    Divide('/') {
        @Override
        public int calculate(int a, int b) {
            return a / b;
        }
    };

    private final char ch;

    private Operator(char ch) {
        this.ch = ch;
    }

    public char getChar() {
        return this.ch;
    }

    public abstract int calculate(int a, int b);
}
