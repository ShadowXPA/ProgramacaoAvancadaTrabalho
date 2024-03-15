package pt.isec.a2019112789.connect4s.game.logic.data;

public enum EDisc {
    Empty('e'),
    Red('r'),
    Yellow('y'),
    Green('g'),
    Blue('b'),
    Magenta('m'),
    White('w'),
    Black('k'),
    Orange('o'),
    Pink('p'),
    Cyan('c'),
    Purple('u'),
    Gold('a'),
    Silver('b'),
    Special('s');

    private final char ch;

    private EDisc(char ch) {
        this.ch = ch;
    }

    public char getChar() {
        return this.ch;
    }

    public static EDisc getDisc(char ch) {
        ch = Character.toLowerCase(ch);
        for (var d : EDisc.values()) {
            if (d.getChar() == ch) {
                return d;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "\n Color: " + this.name() + "\n Character: " + ch;
    }
}
