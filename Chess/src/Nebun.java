public class Nebun extends Piesa{
    public Nebun(byte culoare) {
        super((byte)4, "Nebun", culoare);
    }
    @Override
    boolean move(byte fromX, byte fromY, byte toX, byte toY, byte playerTurn) {
        boolean isSuccesful=super.move(fromX, fromY, toX, toY, playerTurn);
        if(!isSuccesful)
            return false;
        Piesa[][] matrix = Board.getMatrix();
        isSuccesful=canMove(fromX, fromY, toX, toY, matrix);

        if(isSuccesful)
            Board.setMatrix(matrix);
        return isSuccesful;
    }
    private boolean canMove(byte fromX, byte fromY, byte toX, byte toY, Piesa[][] matrix){
        int xAxis= fromX < toX ? 1 : -1;
        int yAxis= fromY < toY ? 1 : -1;

        if(Math.abs(fromX - toX) != Math.abs(fromY - toY))
            return false;

        for(int i=1 ; i<Math.abs(fromX - toX); i++){
            if(matrix[fromX + (i*xAxis)][fromY + (i*yAxis)]!=null){
                System.out.println("!Miscare invalida!");
                return false;
            }
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
        for(int i= 1; i<Board.DIMENSION_BOARD; i++){
            if(!(i+fromX>0 && i+fromX<Board.DIMENSION_BOARD))
                break;
            if(!(i+fromY>0 && i+fromY<Board.DIMENSION_BOARD))
                break;
            if(i+fromX == kingX && i+fromY == kingY)
                this.hasKing = true;
            if(matrix[fromX+i][fromY+ i] != null){
                board[fromX+i][fromY+i]+=1;
                break;
            }
            board[fromX+i][fromY+i]+=1;
        }
        for(int i= 1; i<Board.DIMENSION_BOARD; i++){
            if(!(fromX-i>=0 && fromX-i < Board.DIMENSION_BOARD))
                break;
            if(!(fromY-i>=0 && fromY-i < Board.DIMENSION_BOARD))
                break;
            if(fromX-i == kingX && fromY-i == kingY)
                this.hasKing = true;
            if(matrix[fromX-i][fromY-i] != null){
                board[fromX-i][fromY-i]+=1;
                break;
            }
            board[fromX-i][fromY-i]+=1;
        }
        //pe diagonala dreapta sus

        int x= fromX;
        for(int i=fromY+1; i<Board.DIMENSION_BOARD; i++){
            x--;
            if(!(x>=0 && x<Board.DIMENSION_BOARD))
                break;
            if(x == kingX && i == kingY)
                this.hasKing = true;
            if(matrix[x][i] != null){
                board[x][i]+=1;
                break;
            }
            board[x][i]+=1;
        }

        //pe diagonala stanga jos
        x = fromX;
        for(int i=fromY -1; i>=0; i--){
            x++;
            if(!(x>=0 && x<Board.DIMENSION_BOARD))
                break;
            if(x == kingX && i == kingY)
                this.hasKing = true;
            if(matrix[x][i] != null){
                board[x][i]+=1;
                break;
            }
            board[x][i]+=1;
        }
    }

    @Override
    public void unmark(byte fromX, byte fromY, byte[][] board) {
        Piesa[][] matrix = Board.getMatrix();
        this.hasKing = false;
        for(int i= 1; i<Board.DIMENSION_BOARD; i++){
            if(!(fromX+i>0 && fromX+i<Board.DIMENSION_BOARD))
                break;
            if(!(fromY+i>0 && fromY+i<Board.DIMENSION_BOARD))
                break;
            if(matrix[fromX+i][fromY+i] != null){
                if(board[fromX+i][fromY+i]>0)
                    board[fromX+i][fromY+i]-=1;
                break;
            }
            if(board[fromX+i][fromY+i]>0)
                board[fromX+i][fromY+i]-=1;
        }
        for(int i= 1; i<Board.DIMENSION_BOARD; i++){
            if(!(fromX-i>=0 && fromX-i<Board.DIMENSION_BOARD))
                break;
            if(!(fromY-i>=0 && fromY-i<Board.DIMENSION_BOARD))
                break;
            if(matrix[fromX-i][fromY-i] != null){
                if(board[fromX-i][fromY-i]>0)
                    board[fromX-i][fromY-i]-=1;
                break;
            }
            if(board[fromX-i][fromY-i]>0)
                board[fromX-i][fromY-i]-=1;
        }
        //pe diagonala dreapta sus
        int x= fromX;
        for(int i=fromY+1; i<Board.DIMENSION_BOARD; i++){
            x--;
            if(!(x>=0 && x<Board.DIMENSION_BOARD))
                break;
            if(matrix[x][i] != null){
                if(board[x][i]>0)
                    board[x][i]-=1;
                break;
            }
            if(board[x][i]>0)
                board[x][i]-=1;
        }

        //pe diagonala stanga jos
        x = fromX;
        for(int i=fromY -1; i>=0; i--){
            x++;
            if(!(x>=0 && x<Board.DIMENSION_BOARD))
                break;
            if(matrix[x][i] != null){
                if(board[x][i]>0)
                    board[x][i]-=1;
                break;
            }
            if(board[x][i]>0)
                board[x][i]-=1;
        }
    }
}
