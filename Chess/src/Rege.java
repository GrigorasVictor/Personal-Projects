public class Rege extends Piesa {
    public Rege(byte culoare) {
        super((byte)6, "Rege", culoare);
    }
    private static byte xW, yW;
    private static byte xB, yB;
    private boolean firstMove= false;

    @Override
    boolean move(byte fromX, byte fromY, byte toX, byte toY, byte playerTurn) {
        boolean isSuccesful=super.move(fromX, fromY, toX, toY, playerTurn);
        if(!isSuccesful)
            return false;
        Piesa[][] matrix = Board.getMatrix();
        isSuccesful=canMove(fromX, fromY, toX, toY, matrix);

        if(isSuccesful) {
            Board.setMatrix(matrix);
            this.firstMove=true;
        }
        return isSuccesful;
    }
    private boolean canMove(byte fromX, byte fromY, byte toX, byte toY, Piesa[][] matrix){
        if((matrix[fromX][fromY] instanceof Rege && matrix[toX][toY] instanceof Tura) || (matrix[fromX][fromY] instanceof Tura && matrix[toX][toY] instanceof Rege)){
            ((Tura)matrix[toX][toY]).setFirstMove(true);
            if(toY==Board.DIMENSION_BOARD-1){
                matrix[fromX][fromY+2]=matrix[fromX][fromY];
                matrix[fromX][fromY]=null;
                matrix[fromX][toY-2]=matrix[fromX][toY];
                matrix[fromX][toY]=null;
            }
            else{
                matrix[fromX][fromY-2]=matrix[fromX][fromY];
                matrix[fromX][fromY]=null;
                matrix[fromX][toY+3]=matrix[fromX][toY];
                matrix[fromX][toY]=null;
            }
            return true;
        }
        if(Math.abs(fromX- toX) >=2){
            System.out.println("!Illegal move!");
            return false;
        }
        if(Math.abs(fromY- toY) >=2){
            System.out.println("!Illegal move!");
            return false;
        }
        byte oppositeColor = (byte) ((this.getCuloare()+1)%2);
        byte[][] markers2 = Board.getMarkers(oppositeColor);
        if(markers2[toX][toY]!=0) {
            System.out.println("Check!");
            return false;
        }
        if (matrix[toX][toY] != null) {
            matrix[toX][toY].unmark(toX, toY, markers2);
            Board.setMarkers(markers2, oppositeColor);
        }
        byte[][] markers = Board.getMarkers(this.culoare);
        byte[][] opponent= Board.getMarkers((this.culoare+1)%2);

        if(this.culoare == 0){
            xW = toX;
            yW = toY;
        }
        else {
            xB = toX;
            yB = toY;
        }
        if(matrix[toX][toY]!=null)
            unmark(toX, toY, opponent);
        matrix[toX][toY] = matrix[fromX][fromY];
        matrix[fromX][fromY] = null;

        unmark(fromX, fromY, markers);
        mark(toX, toY, markers);

        Board.setMarkers(markers, this.culoare);
        return true;
    }
    @Override
    public void mark(byte fromX, byte fromY, byte[][] board) {
        int x=fromX - 1;
        if(x>=0) {
            for (int i = fromY - 1; i <= fromY + 1; i++)
                if (i >= 0 && i < Board.DIMENSION_BOARD) {
                    board[x][i] += 1;
                }
        }
        x=fromX+1;
        if(x<Board.DIMENSION_BOARD){
            for (int i = fromY ; i <= fromY + 1; i++)
                if (i >= 0 && i < Board.DIMENSION_BOARD) {
                    board[x][i] += 1;
                }
        }
        int y=fromY-1;
        if(y>=0){
            for(int i= fromX; i<= fromX + 1; i++)
                if (i >= 0 && i < Board.DIMENSION_BOARD){
                    board[i][y]+=1;
                }
        }
        y=fromY+1;
        if(y<Board.DIMENSION_BOARD) {
            for (int i = fromX ; i < fromX + 1; i++)
                if (i >= 0 && i < Board.DIMENSION_BOARD) {
                    board[i][y] += 1;
                }
        }
    }
    @Override
    public void unmark(byte fromX, byte fromY, byte[][] board) {
        int x=fromX - 1;
        if(x>=0) {
            for (int i = fromY - 1; i <= fromY + 1; i++)
                if (i >= 0 && i < Board.DIMENSION_BOARD) {
                    if(board[x][i]>0)
                        board[x][i] -= 1;
                }
        }
        x=fromX+1;
        if(x<Board.DIMENSION_BOARD){
            for (int i = fromY ; i <= fromY + 1; i++)
                if (i >= 0 && i < Board.DIMENSION_BOARD) {
                    if(board[x][i]>0)
                        board[x][i] -= 1;
                }
        }
        int y=fromY-1;
        if(y>=0){
            for(int i= fromX; i<= fromX + 1; i++)
                if (i >= 0 && i < Board.DIMENSION_BOARD){
                    if(board[i][y]>0)
                        board[i][y]-=1;
                }
        }
        y=fromY+1;
        if(y<Board.DIMENSION_BOARD) {
            for (int i = fromX ; i < fromX + 1; i++)
                if (i >= 0 && i < Board.DIMENSION_BOARD) {
                    if(board[i][y]>0)
                        board[i][y] -= 1;
                }
        }
    }
    public static byte getxW() {
        return xW;
    }
    public static byte getyW() {
        return yW;
    }
    public static void setWhite(byte xW, byte yW) {
        Rege.xW = xW;
        Rege.yW = yW;
    }
    public static byte getxB() {
        return xB;
    }
    public static byte getyB() {
        return yB;
    }
    public static void setBlack(byte xB, byte yB) {
        Rege.yB = yB;
        Rege.xB = xB;
    }

    public boolean isFirstMove() {
        return firstMove;
    }

    public void setFirstMove(boolean firstMove) {
        this.firstMove = firstMove;
    }
}
