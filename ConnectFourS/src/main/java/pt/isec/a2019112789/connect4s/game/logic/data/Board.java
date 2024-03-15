package pt.isec.a2019112789.connect4s.game.logic.data;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public final class Board implements Serializable {

    private EDisc[][] internalBoard;
    private int numRows;
    private int numCols;
    private int emptySpaces;
    private EDisc winner;

    public Board() {
        this(6, 7);
    }

    public Board(int numRows, int numCols) {
        this.numRows = numRows;
        this.numCols = numCols;
        initializeBoard();
    }

    public Board(Board b) {
        this.internalBoard = b.getBoard();
        this.numRows = b.numRows;
        this.numCols = b.numCols;
        this.emptySpaces = b.emptySpaces;
        this.winner = b.winner;
    }

    //<editor-fold defaultstate="collapsed" desc="Initialize">
    public final void initializeBoard() {
        this.internalBoard = new EDisc[numRows][numCols];
        for (int x = 0; x < numRows; x++) {
            for (int y = 0; y < numCols; y++) {
                internalBoard[x][y] = EDisc.Empty;
            }
        }
        emptySpaces = numRows * numCols;
        winner = EDisc.Empty;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Getters">
    public EDisc[][] getBoard() {
        EDisc[][] temp = new EDisc[numRows][numCols];
        for (int x = 0; x < numRows; x++) {
            System.arraycopy(internalBoard[x], 0, temp[x], 0, numCols);
        }
        return temp;
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public boolean isFull() {
        return emptySpaces == 0;
    }

    public boolean isColumnFull(int column) {
        return nextAvailableX(column - 1) == -1;
    }

    public EDisc getWinner() {
        return winner;
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Manipulate Board">
    public final boolean addDisc(EDisc disc, int column) {
        int y = column - 1;
        return addDisc(disc, nextAvailableX(y), y);
    }

    private boolean addDisc(EDisc disc, int x, int y) {
        if (y < 0 || y >= numCols || isFull()) {
            return false;
        }

        if (disc == EDisc.Special) {
            blowColumn(y);
            return true;
        }

        if (x < 0 || x >= numRows) {
            return false;
        }

        if (internalBoard[x][y] == EDisc.Empty) {
            internalBoard[x][y] = disc;
            emptySpaces--;
            verifyWinner(disc, x, y);
            return true;
        }

        return false;
    }

    private int nextAvailableX(int y) {
        if (y < 0 || y >= numCols) {
            return -1;
        }

        for (int x = numRows - 1; x >= 0; x--) {
            if (internalBoard[x][y] == EDisc.Empty) {
                return x;
            }
        }

        return -1;
    }

    private void blowColumn(int y) {
        for (int x = numRows - 1; x >= 0; x--) {
            if (internalBoard[x][y] != EDisc.Empty) {
                internalBoard[x][y] = EDisc.Empty;
                emptySpaces++;
            } else {
                return;
            }
        }
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Verify Board">
    private boolean verifyWinner(EDisc disc, int x, int y) {
        return (checkLeftRight(y, x, disc)
                || checkTopBottom(x, y, disc)
                || checkTopLeftBottomRight(y, x, disc)
                || checkTopRightBottomLeft(y, x, disc));
    }

    private boolean checkTopRightBottomLeft(int y, int x, EDisc disc) {
        int p = y + 1, DTopRight = 0, DBottomLeft = 0;
        // Top Right
        for (int j = x - 1; j >= 0 && x - j != 4; j--) {
            if (!(p < numCols && p - y != 4)) {
                break;
            }
            if (internalBoard[j][p] == disc) {
                DTopRight++;
            } else {
                break;
            }
            p++;
        }
        // Bottom Left
        p = y - 1;
        for (int k = x + 1; k < numRows && k - x != 4; k++) {
            if (!(p >= 0 && y - p != 4)) {
                break;
            }
            if (internalBoard[k][p] == disc) {
                DBottomLeft++;
            } else {
                break;
            }
            p--;
        }
        if (DTopRight + DBottomLeft >= 3) {
            winner = disc;
            return true;
        }
        return false;
    }

    private boolean checkTopLeftBottomRight(int y, int x, EDisc disc) {
        // Top Left
        int p = y - 1, DTopLeft = 0, DBottomRight = 0;
        for (int j = x - 1; j >= 0 && x - j != 4; j--) {
            if (!(p >= 0 && y - p != 4)) {
                break;
            }
            if (internalBoard[j][p] == disc) {
                DTopLeft++;
            } else {
                break;
            }
            p--;
        }
        // Bottom Right
        p = y + 1;
        for (int k = x + 1; k < numRows && k - x != 4; k++) {
            if (!(p < numCols && p - y != 4)) {
                break;
            }
            if (internalBoard[k][p] == disc) {
                DBottomRight++;
            } else {
                break;
            }
            p++;
        }
        if (DTopLeft + DBottomRight >= 3) {
            winner = disc;
            return true;
        }
        return false;
    }

    private boolean checkTopBottom(int x, int y, EDisc disc) {
        int DTop = 0, DBottom = 0;
        // Top
        for (int i = x - 1; i >= 0 && x - i != 4; i--) {
            if (internalBoard[i][y] == disc) {
                DTop++;
            } else {
                break;
            }
        }
        // Bottom
        for (int i = x + 1; i < numRows && i - x != 4; i++) {
            if (internalBoard[i][y] == disc) {
                DBottom++;
            } else {
                break;
            }
        }
        if (DTop + DBottom >= 3) {
            winner = disc;
            return true;
        }
        return false;
    }

    private boolean checkLeftRight(int y, int x, EDisc disc) {
        int DLeft = 0, DRight = 0;
        // Left
        for (int i = y - 1; i >= 0 && y - i != 4; i--) {
            if (internalBoard[x][i] == disc) {
                DLeft++;
            } else {
                break;
            }
        }
        // Right
        for (int i = y + 1; i < numCols && i - y != 4; i++) {
            if (internalBoard[x][i] == disc) {
                DRight++;
            } else {
                break;
            }
        }
        if (DLeft + DRight >= 3) {
            winner = disc;
            return true;
        }
        return false;
    }
    //</editor-fold>

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Arrays.deepHashCode(this.internalBoard);
        hash = 59 * hash + this.numRows;
        hash = 59 * hash + this.numCols;
        hash = 59 * hash + this.emptySpaces;
        hash = 59 * hash + Objects.hashCode(this.winner);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Board other = (Board) obj;
        if (this.numRows != other.numRows) {
            return false;
        }
        if (this.numCols != other.numCols) {
            return false;
        }
        if (this.emptySpaces != other.emptySpaces) {
            return false;
        }
        return Arrays.deepEquals(this.internalBoard, other.internalBoard);
    }
}
