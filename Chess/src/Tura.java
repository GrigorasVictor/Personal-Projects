public class Tura extends Piesa{
    public Tura(byte culoare) {
        super((byte)2, "Tura", culoare);
    }
    private boolean firstMove=false;
    @Override
    boolean move(byte fromX, byte fromY, byte toX, byte toY, byte playerTurn) {
        boolean isSuccesful=super.move(fromX, fromY, toX, toY, playerTurn);
        if(!isSuccesful)
            return false;
        Piesa[][] matrix = Board.getMatrix();
        isSuccesful=canMove(fromX, fromY, toX, toY, matrix);
        if(isSuccesful) {
            Board.setMatrix(matrix);
            firstMove = true;
        }
        return isSuccesful;
    }
    private boolean canMove(byte fromX, byte fromY, byte toX, byte toY, Piesa[][] matrix){
        if((matrix[fromX][fromY] instanceof Rege && matrix[toX][toY] instanceof Tura) || (matrix[fromX][fromY] instanceof Tura && matrix[toX][toY] instanceof Rege)){
            ((Rege)matrix[toX][toY]).setFirstMove(true);
            if(fromY==Board.DIMENSION_BOARD-1){
                matrix[fromX][fromY-2]=matrix[fromX][fromY];
                matrix[fromX][fromY]=null;
                matrix[fromX][toY+2]=matrix[fromX][toY];
                matrix[fromX][toY]=null;
            }
            else{
                matrix[fromX][fromY+3]=matrix[fromX][fromY];
                matrix[fromX][fromY]=null;
                matrix[fromX][toY-2]=matrix[fromX][toY];
                matrix[fromX][toY]=null;
            }
            return true;
        }
        if(fromX==toX){
            int yAxis= fromY < toY ? 1 : -1;
            for(int i=1; i<Math.abs(fromY-toY); i++){
                if(matrix[toX][fromY+i*yAxis]!=null){
                    System.out.println("!Miscare ilegala!");
                    return false;
                }
            }
        }
        else if(fromY==toY){
            int xAxis= fromX < toX ? 1 : -1;
            for(int i=1; i<Math.abs(fromX-toX); i++){
                if(matrix[fromX+i*xAxis][toY]!=null){
                    System.out.println("!Miscare ilegala!");
                    return false;
                }
            }
        }
        else{
            System.out.println("!Miscare ilegala!");
            return false;
        }
        if(matrix[toX][toY] != null) {
            byte[][] markers2 = Board.getMarkers(matrix[toX][toY].getCuloare());
            matrix[toX][toY].unmark(toX, toY, markers2);
            Board.setMarkers(markers2, matrix[toX][toY].getCuloare());
        }

        byte[][] markers = Board.getMarkers(this.culoare);

        matrix[toX][toY]=matrix[fromX][fromY];
        matrix[fromX][fromY]=null;

        unmark(fromX, fromY, markers);
        mark(toX, toY, markers);

        Board.setMarkers(markers, this.culoare);
        return true;
    }
    @Override
    public void mark(byte fromX, byte fromY, byte[][] board) {
        Piesa[][] matrix = Board.getMatrix();
        int kingX = this.culoare == 1 ? Rege.getxW() : Rege.getxB(); // getting the opponent position
        int kingY = this.culoare == 1 ? Rege.getyW() : Rege.getyB(); // getting the opponent position
        for(int i=fromX + 1; i<Board.DIMENSION_BOARD; i++) {
            if (matrix[i][fromY] != null) {
                board[i][fromY] += 1;
                break;
            }
            if(i==kingX && fromY==kingY)
                this.hasKing=true;
            board[i][fromY] += 1;
        }
        for(int i=fromX - 1; i>=0; i--) {
            if (matrix[i][fromY] != null) {
                board[i][fromY] += 1;
                break;
            }
            if(i == kingX && kingY == fromY)
                this.hasKing=true;
            board[i][fromY] += 1;
        }
        for(int i=fromY + 1; i<Board.DIMENSION_BOARD; i++) {
            if (matrix[fromX][i] != null) {
                board[fromX][i] += 1;
                break;
            }
            if(fromX == kingX && fromY == kingY)
                this.hasKing=true;
            board[fromX][i] += 1;
        }
        for(int i=fromY - 1; i>=0; i--) {
            if (matrix[fromX][i] != null) {
                board[fromX][i] += 1;
                break;
            }
            if(fromX == kingX && kingY == i)
                this.hasKing=true;
            board[fromX][i] += 1;
        }
    }

    @Override
    public void unmark(byte fromX, byte fromY, byte[][] board) {
        Piesa[][] matrix = Board.getMatrix();
        this.hasKing = false;
        for(int i=fromX + 1; i<Board.DIMENSION_BOARD; i++) {
            if (matrix[i][fromY] != null && board[i][fromY]>0) {
                board[i][fromY] -= 1;
                break;
            }

            if(board[i][fromY]>0)
                board[i][fromY] -= 1;
        }
        for(int i=fromX - 1; i>=0; i--) {
            if (matrix[i][fromY] != null && board[i][fromY]>0) {
                board[i][fromY] -= 1;
                break;
            }
            if(board[i][fromY]>0)
                board[i][fromY] -= 1;
        }
        for(int i=fromY + 1; i<Board.DIMENSION_BOARD; i++) {
            if (matrix[fromX][i] != null && board[fromX][i]>0) {
                board[fromX][i] -= 1;
                break;
            }
            if(board[fromX][i]>0)
                board[fromX][i] -= 1;
        }
        for(int i=fromY - 1; i>=0; i--) {
            if (matrix[fromX][i] != null && board[fromX][i]>0) {
                board[fromX][i] -= 1;
                break;
            }
            if(board[fromX][i]>0)
                board[fromX][i] -= 1;
        }
    }

    public boolean isFirstMove() {
        return firstMove;
    }

    public void setFirstMove(boolean firstMove) {
        this.firstMove = firstMove;
    }
}
