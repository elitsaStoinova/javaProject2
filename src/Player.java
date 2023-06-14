public class Player {
    private String name;
    private String nickname;
    private char symbol;
    private int positionRow;
    private int initialPositionRow;
    private int initialPositionCol;
    private int positionCol;

    public int getPositionRow(){
        return positionRow;
    }

    public void setPositionRow(int row){
        this.positionRow = row;
    }

    public int getPositionCol(){
        return positionCol;
    }

    public void setPositionCol(int col){
        this.positionCol = col;
    }

    public int getInitialPositionCol(){
        return initialPositionCol;
    }

    public void setInitialPositionCol(int col){
        this.initialPositionCol = col;
    }
    public int getInitialPositionRow(){
        return initialPositionRow;
    }

    public void setInitialPositionRow(int row){
        this.initialPositionRow = row;
    }

    public char getSymbol(){
        return symbol;
    }

    public Player(String name, String nickname, char symbol, int row, int col){
        this.name = name;
        this.nickname = nickname;
        this.symbol = symbol;
        this.positionRow = row;
        this.positionCol = col;
    }
}
