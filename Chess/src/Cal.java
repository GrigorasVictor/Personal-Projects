public class Cal extends Piesa{
    public Cal(byte culoare) {
        super((byte)3, "Cal", culoare);
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
        if(Math.abs(fromX - toX) + Math.abs(fromY - toY) != 3){
                System.out.println("!Miscare invalida!");
                return false;
            }
        if(Math.abs(fromX - toX) == Math.abs(fromY - toY)){
            System.out.println("!Miscare invalida!");
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
    public void mark(byte toX, byte toY, byte[][] board) {
        int[] positions = {1, -1};
        int[] positions2 = {2, -2};
        for(int i=0 ;i<positions.length; i++)
            for(int j=0; j<positions.length; j++){
                if ((positions[i] + toX) >= 0 && (positions[i] + toX) < Board.DIMENSION_BOARD)
                    if ((positions2[j] + toY) >= 0 && (positions2[j] + toY) < Board.DIMENSION_BOARD) {
                        board[positions[i] + toX][positions2[j] + toY] += 1;
                        Piesa piesa = Board.getPiesa(toX, toY);
                        if(piesa instanceof Rege && this.hasKing && piesa.getCuloare()!= this.culoare)
                            this.hasKing = true;
                    }

                if ((positions2[i] + toX) >= 0 && (positions2[i] + toX) < Board.DIMENSION_BOARD)
                    if ((positions[j] + toY) >= 0 && (positions[j] + toY) < Board.DIMENSION_BOARD) {
                        board[positions2[i] + toX][positions[j] + toY] += 1;
                        Piesa piesa = Board.getPiesa(toX, toY);
                        if(piesa instanceof Rege && this.hasKing && piesa.getCuloare()!= this.culoare)
                            this.hasKing = true;
                    }
            }
    }
    @Override
    public void unmark(byte fromX, byte fromY, byte[][] board) {
        this.hasKing = false;
        int[] positions = {1, -1};
        int[] positions2 = {2, -2};
        for(int i=0 ;i<positions.length; i++)
            for(int j=0; j<positions.length; j++){
                if ((positions[i] + fromX) >= 0 && (positions[i] + fromX) < Board.DIMENSION_BOARD)
                    if ((positions2[j] + fromY) >= 0 && (positions2[j] + fromY) < Board.DIMENSION_BOARD){
                        if(board[positions[i] + fromX][positions2[j] + fromY] > 0)
                            board[positions[i] + fromX][positions2[j] + fromY] -= 1;
                        }

                if ((positions2[i] + fromX) >= 0 && (positions2[i] + fromX) < Board.DIMENSION_BOARD)
                    if ((positions[j] + fromY) >= 0 && (positions[j] + fromY) < Board.DIMENSION_BOARD) {
                        if (board[positions2[i] + fromX][positions[j] + fromY] > 0)
                            board[positions2[i] + fromX][positions[j] + fromY] -= 1;
                    }
            }
    }
}
