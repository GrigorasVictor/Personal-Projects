import java.util.Scanner;

public class Pion  extends Piesa{
    private boolean firstMove = true;
    public Pion(byte culoare) {
        super((byte)1, "Pion", culoare);
    }
    @Override
    boolean move(byte fromX, byte fromY, byte toX, byte toY, byte playerTurn) {
        boolean isSuccesful=super.move(fromX, fromY, toX, toY, playerTurn);
        if(!isSuccesful)
            return false;
        Piesa[][] matrix = Board.getMatrix();
        if(playerTurn == 0)
            isSuccesful=moveWhite(fromX, fromY, toX, toY, matrix);
        else
            isSuccesful=moveBlack(fromX, fromY, toX, toY, matrix);

        if(isSuccesful)
            Board.setMatrix(matrix);
        return isSuccesful;
    }
    private boolean moveWhite(byte fromX, byte fromY, byte toX, byte toY, Piesa[][] matrix){
        int steps = firstMove ? 2 : 1;
        byte[][] markers = Board.getMarkers(0);

        if(fromY!=toY) {
            if (Math.abs(fromY-toY)>1) {
                System.out.println("!Miscare invalida!");
                return false;
            }
            if(fromX != toX + 1){
                System.out.println("!Miscare invalida!");
                return false;
            }
            if (matrix[toX][toY] != null && matrix[toX][toY].getCuloare() == this.culoare) {
                System.out.println("!Miscare invalida!");
                return false;
            }
            if(fromY > toY){
                if(matrix[fromX][fromY - 1] != null && matrix[fromX][fromY - 1] instanceof Pion && matrix[fromX][fromY - 1].getCuloare() != matrix[fromX][fromY].getCuloare() && matrix[toX][toY]==null){
                    matrix[toX][toY]=matrix[fromX][fromY];
                    matrix[fromX][fromY-1]=null;
                    matrix[fromX][fromY]=null;
                    return true;
                }
            }
            else{
                if(matrix[fromX][fromY+1] !=null && matrix[fromX][fromY+1] instanceof Pion && matrix[fromX][fromY+1].getCuloare() != matrix[fromX][fromY].getCuloare() && matrix[toX][toY]==null){
                    matrix[toX][toY]=matrix[fromX][fromY];
                    matrix[fromX][fromY+1]=null;
                    matrix[fromX][fromY]=null;
                    return true;
                }
            }
            if(matrix[toX][toY]==null){
                System.out.println("!Miscare invalida!");
                return false;
            }
            matrix[toX][toY]=matrix[fromX][fromY];
            matrix[fromX][fromY]=null;
            unmark(fromX, fromY, markers);

            if(toX == 0){
                changePiece(toX, toY, matrix);
            }
            mark(toX, toY, markers);
            Board.setMarkers(markers, 0);

            firstMove = false;
            return true;
        }

        if(fromX - steps > toX){
            System.out.println("!Miscare invalida!");
            return false;
        }
        
        for(int i=1; i<= toX; i++){
            if(matrix[fromX - i][fromY] != null && fromX-i >= toX) {
                System.out.println("!Miscare invalida!");
                return false;
            }
        }

        matrix[toX][toY]=matrix[fromX][fromY];
        matrix[fromX][fromY]=null;
        unmark(fromX, fromY, markers);
        if(toX == 0){
            changePiece(toX, toY, matrix);
        }
        mark(toX, toY, markers);
        Board.setMarkers(markers, 0);

        firstMove = false;
        return true;
    }
    private boolean moveBlack(byte fromX, byte fromY, byte toX, byte toY, Piesa[][] matrix){
        int steps = firstMove ? 2 : 1;
        byte[][] markers = Board.getMarkers(1);

        if(fromY!=toY) {
            if (Math.abs(fromY-toY)>1) {
                System.out.println("!Miscare invalida!");
                return false;
            }
            if(fromX != toX - 1){
                System.out.println("!Miscare invalida!");
                return false;
            }
            if (matrix[toX][toY] != null && matrix[toX][toY].getCuloare() == this.culoare) {
                System.out.println("!Miscare invalida!");
                return false;
            }
            //Implementing En Passant
            if(fromY > toY){
                if(matrix[fromX][fromY - 1] != null && matrix[fromX][fromY - 1] instanceof Pion && matrix[fromX][fromY - 1].getCuloare() != matrix[fromX][fromY].getCuloare() && matrix[toX][toY]==null){
                    matrix[toX][toY]=matrix[fromX][fromY];
                    matrix[fromX][fromY-1]=null;
                    matrix[fromX][fromY]=null;
                    return true;
                }
            }
            else{
                if(matrix[fromX][fromY+1] !=null && matrix[fromX][fromY+1] instanceof Pion && matrix[fromX][fromY+1].getCuloare() != matrix[fromX][fromY].getCuloare() && matrix[toX][toY]==null){
                    matrix[toX][toY]=matrix[fromX][fromY];
                    matrix[fromX][fromY+1]=null;
                    matrix[fromX][fromY]=null;
                    return true;
                }
            }
            if(matrix[toX][toY]==null){
                System.out.println("!Miscare invalida!");
                return false;
            }
            if(matrix[toX][toY] != null) {
                byte[][] markers2 = Board.getMarkers(matrix[toX][toY].getCuloare());
                matrix[toX][toY].unmark(toX, toY, markers2);
                Board.setMarkers(markers2, matrix[toX][toY].getCuloare());
            }

            matrix[toX][toY]=matrix[fromX][fromY];
            matrix[fromX][fromY]=null;
            unmark(fromX, fromY, markers);

            if(toX == Board.DIMENSION_BOARD -1){
                changePiece(toX, toY, matrix);
            }

            mark(toX, toY, markers);
            Board.setMarkers(markers, 1);

            firstMove=false;
            return true;
        }

        if(Math.abs(fromX- toX) > steps){
            System.out.println("!Miscare invalida!");
            return false;
        }

        for(int i=1; i<= steps ; i++){
            if(matrix[fromX + i][fromY] != null && i+fromX <= toX) {
                System.out.println("!Miscare invalida!");
                return false;
            }
        }

        matrix[toX][toY]=matrix[fromX][fromY];
        matrix[fromX][fromY]=null;
        unmark(fromX, fromY, markers);

        if(toX == Board.DIMENSION_BOARD -1){
            changePiece(toX, toY, matrix);
        }

        mark(toX, toY, markers);
        Board.setMarkers(markers, 1);
        firstMove = false;
        return true;
    }
    @Override
    public void mark(byte toX, byte toY, byte[][] board){
        int playerPosition = this.culoare == 0 ? -1 : 1;
        int kingX = this.culoare == 1 ? Rege.getxW() : Rege.getxB(); // getting the opponent position
        int kingY = this.culoare == 1 ? Rege.getyW() : Rege.getyB(); // getting the opponent position

        if(toX == 0 || toX == Board.DIMENSION_BOARD - 1)
            return;
        if(toY == 0) {
            board[toX + playerPosition][toY + 1] += 1;
            if(toX+ playerPosition == kingX && toY + 1 == kingY)
                this.hasKing = true;
        }
        else if(toY == Board.DIMENSION_BOARD - 1) {
            board[toX + playerPosition][toY - 1] += 1;
            if(toX + playerPosition == kingX && toY - 1 == kingY)
                this.hasKing = true;
        }
        else{
            board[toX+playerPosition][toY-1]+=1;
            board[toX+playerPosition][toY+1]+=1;

            if(toX + playerPosition == kingX && toY - 1 == kingY)
                this.hasKing = true;

            if(toX+ playerPosition == kingX && toY + 1 == kingY)
                this.hasKing = true;
        }
    }
    @Override
    public void unmark(byte fromX, byte fromY, byte[][] board){
        int playerPosition = this.culoare == 0 ? -1 : 1;
        this.hasKing = false;

        if(fromX == 0 || fromX == Board.DIMENSION_BOARD - 1)
            return;
        if(fromY == 0) {
            if (board[fromX + playerPosition][fromY + 1] > 0)
                board[fromX + playerPosition][fromY + 1] -= 1;
        }
        else if(fromY == Board.DIMENSION_BOARD - 1) {
            if(board[fromX+playerPosition][fromY-1] > 0)
                board[fromX + playerPosition][fromY - 1] -= 1;

        }
        else {
            if (board[fromX + playerPosition][fromY - 1] > 0 && board[fromX + playerPosition][fromY + 1] > 0) {
                board[fromX + playerPosition][fromY - 1] -= 1;
                board[fromX + playerPosition][fromY + 1] -= 1;
            }
        }
    }
    private void changePiece(int toX, int toY, Piesa[][] matrix){
            Scanner scanner = new Scanner(System.in);
            System.out.println("2 - Rock");
            System.out.println("3 - Knight");
            System.out.println("4 - Bishop");
            System.out.println("5 - Queen");
            System.out.print("> ");
            String input = scanner.nextLine();
            while(true){
                if(input.length() == 1)
                    break;
                System.out.println("Invalid selection!");
                input = scanner.nextLine();
            }
            matrix[toX][toY]= switch (input){
                case "2" -> new Tura(this.culoare);
                case "3" -> new Cal(this.culoare);
                case "4" -> new Nebun(this.culoare);
                default -> new Regina(this.culoare);
            };
    }
}
