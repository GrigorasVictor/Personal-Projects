public abstract class Piesa {
    private final byte ID;
    private final String nume;
    protected byte culoare; // 0 -alb | 1 - negru
    protected boolean hasKing;
    public Piesa(byte ID, String nume, byte culoare) {
        this.ID = ID;
        this.nume = nume;
        this.culoare = culoare;
    }
    public byte getID() {
        return ID;
    }
    public String getNume() {
        return nume;
    }
    public byte getCuloare() {
        return culoare;
    }
    public boolean isHasKing() {
        return hasKing;
    }
    boolean move(byte fromX, byte fromY, byte toX, byte toY, byte playerTurn){
        Piesa[][] table= Board.getMatrix();

        if(playerTurn != this.culoare){
            System.out.println("The piece is not yours!");
            return false;
        }
        if(table[fromX][fromY]==null){
            System.out.println("The piece doesn't exist!");
            return false;
        }
        //Implementing Rocate
        if(table[toX][toY]!=null && table[toX][toY].getCuloare()==this.culoare){
            if((table[fromX][fromY] instanceof Rege && table[toX][toY] instanceof Tura) || (table[fromX][fromY] instanceof Tura && table[toX][toY] instanceof Rege)) {
                Rege king = table[fromX][fromY] instanceof Rege ? (Rege)table[fromX][fromY] : (Rege)table[toX][toY];
                Tura rock = table[fromX][fromY] instanceof Tura ? (Tura) table[fromX][fromY] : (Tura) table[toX][toY];
                if(king.isFirstMove()) {
                    System.out.println("!Illegal move!");
                    return false;
                }
                if(rock.isFirstMove()) {
                    System.out.println("!Illegal move!");
                    return false;
                }
                if(fromX!=toX) {
                    System.out.println("!Illegal move!");
                    return false;
                }
                int yMin= Math.min(fromY, toY);
                int yMax= Math.max(fromY, toY);
                for(int i=yMin + 1; i<yMax; i++)
                    if(table[fromX][i] != null){
                        System.out.println("!Illegal move!");
                        return false;
                    }
                return true;
            }
            System.out.println("!Illegal move!");
            return false;
        }
        if(toX==fromX && toY==fromY)
            return false;
        return true;
    }
    public abstract void mark(byte toX, byte toY, byte[][] board);

    public abstract void unmark(byte fromX, byte fromY, byte[][] board);

}
