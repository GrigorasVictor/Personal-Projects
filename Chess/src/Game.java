import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Game {
    private static final String green = "\u001B[42m";
    private static final String white = "\u001B[47m";
    private static final String reset = "\u001B[0m";
    private static final String red = "\u001B[41m";
    private static boolean check=false;
    private static boolean gameFinished = false;
    private static boolean FirstMove= false; // used to print in file
    private static int xPiece, yPiece; // last position of piece that hasChecked
    private static boolean statusSacriface= false;
    public static void StartGame() {
        Scanner scanner = new Scanner(System.in);
        String fileName = "inputArhive.txt";

        byte playerTurn = 0; // 0-white, 1-black
        System.out.println("Welcome to the Chess Game!");
        System.out.println("Instructions: ");
        System.out.println("1. Enter your moves in the format 'XY - UV', where XY is the piece position, and UV is the destination.");
        System.out.println("2. Example: 'A2-A4' or 'A2 A4' means move the piece at A2 to A4.");
        System.out.println("3. Players take turns - White goes first.");

        try {
            Thread.sleep(8000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Piesa[][] matrix = Board.getMatrix();
        Board.addPieces();
        Board.printBoard();
        while (!gameFinished) {
            String player = playerTurn == 0 ? "White" : "Black";
            String blockColor = playerTurn == 0 ? white : green;
            System.out.print("Player " +  blockColor + player + reset + " is now chossing:\n> ");

            CheckStatus(playerTurn, player);
            String inputLine = scanner.nextLine();
            while (true) {
                if (isGoodFormat(inputLine))
                    break;
                System.out.println("Error at input! Try again!");
                inputLine = scanner.nextLine();
            }

            byte y = (byte) (inputLine.charAt(0) - 'A');
            byte x = (byte) (8 - (inputLine.charAt(1) - '0'));
            byte toY = (byte) (inputLine.charAt(3) - 'A');
            byte toX = (byte) (8 - (inputLine.charAt(4) - '0'));

            boolean isSuccessful=false;
            if(matrix[x][y]!=null) {
                isSuccessful = matrix[x][y].move(x, y, toX, toY, playerTurn);
            }
            if (check) {
                // If the move was successful, check if the king is still in check
                if (isSuccessful) {
                    byte[][] markers = Board.getMarkers(matrix[xPiece][yPiece].getCuloare());
                    Board.setMarkers(markers, matrix[xPiece][yPiece].getCuloare());
                    CheckStatus(playerTurn, player);

                    // If the king is still in check, the move is not valid
                    if (check) {
                        isSuccessful = false;
                        System.out.println("Error! The move does not get the king out of check.");
                        // Reset the piece to its original position
                        matrix[x][y]=matrix[toX][toY];
                        matrix[toX][toY]=null;
                    }
                }
            }

            if(isSuccessful) {
                playerTurn = (byte) ((playerTurn + 1) % 2);
                statusSacriface= false;
                Board.printBoard();

                try {
                    FileWriter writer = new FileWriter(fileName, true);
                    if(!FirstMove){
                        // Get the current date and time
                        LocalDateTime currentDateTime = LocalDateTime.now();

                        // Format the date and time using a specific pattern
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                        String formattedDateTime = currentDateTime.format(formatter);

                        writer.write("\n*******Current Date and Time: " + formattedDateTime + "*******\n");
                        FirstMove=true;
                    }

                    Piesa piece = Board.getPiesa(toX, toY);
                    String color = playerTurn == 1 ? "Alb" : "Negru";
                    try {
                        writer.write(piece.getNume() + "-" + color + " --> " + inputLine + "\n");
                    }
                    catch (Exception e){
                        writer.write("Rocada" + "-" + color + " --> " + inputLine + "\n");
                    }
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else
                System.out.println("Error!");
        }
        scanner.close();
    }
    static boolean isGoodFormat(String inputLine){
        if(inputLine.length() != 5)
            return false;
        if(inputLine.charAt(0) < 'A' || inputLine.charAt(0) > 'H')
            return false;
        if(inputLine.charAt(1) < '0' || inputLine.charAt(1) > '8')
            return false;
        if(inputLine.charAt(3) < 'A' || inputLine.charAt(3) > 'H')
            return false;
        if(inputLine.charAt(4) < '0' || inputLine.charAt(4) > '8')
            return false;
        if(inputLine.charAt(2)!=' ' && inputLine.charAt(2)!='-')
            return false;
        return true;
    }
    private static void CheckStatus(int playerturn, String player){
        int x = playerturn == 0 ? Rege.getxW() : Rege.getxB();
        int y = playerturn == 0 ? Rege.getyW() : Rege.getyB();
        byte[][] markers =Board.getMarkers((playerturn + 1)%2);
        Rege rege = ((Rege) Board.getPiesa(x, y));
        if(markers[x][y] != 0){
            Board.setPiesa(x, y, rege);
            System.out.println(red +"Check for "+ player + reset);
            check = true;
            isGameFinished(playerturn);
        }
        else{
            check = false;
        }
    }
    private static void isGameFinished(int playerTurn){
        int x = playerTurn == 0 ? Rege.getxW() : Rege.getxB();
        int y = playerTurn == 0 ? Rege.getyW() : Rege.getyB();
        //playerturn, verifica pentru cine ii check mate si trebuie sa printez celalalt board
        if(checkFreeSpace(x, y, playerTurn)){
            return;
        }
        if(canBeEatted(playerTurn)){
            return;
        }
        if(canBeSaved(playerTurn)){
            return;
        }
        gameFinished = true;
        String player = playerTurn == 1 ? "White" : "Black";
        String blockColor = playerTurn == 1 ? white : green;
        System.out.println("Player " +  blockColor + player + reset + " won!!");
    }

    private static boolean checkFreeSpace(int fromX, int fromY, int playerTurn){
        byte[][] board =Board.getMarkers((playerTurn+1)%2);
        Piesa[][] matrix = Board.getMatrix();
        int x=fromX - 1;
        if(x>=0) {
            for (int i = fromY - 1; i <= fromY + 1; i++)
                if (i >= 0 && i < Board.DIMENSION_BOARD) {
                    if(board[x][i]==0 && matrix[x][i]==null)
                        return true;
                }
        }
        x=fromX+1;
        if(x<Board.DIMENSION_BOARD){
            for (int i = fromY ; i <= fromY + 1; i++)
                if (i >= 0 && i < Board.DIMENSION_BOARD) {
                    if(board[x][i]==0 && matrix[x][i]==null)
                        return true;
                }
        }
        int y=fromY-1;
        if(y>=0){
            for(int i= fromX; i<= fromX + 1; i++)
                if (i >= 0 && i < Board.DIMENSION_BOARD){
                    if(board[i][y]==0 && matrix[i][y]==null)
                        return true;
                }
        }
        y=fromY+1;
        if(y<Board.DIMENSION_BOARD) {
            for (int i = fromX ; i < fromX + 1; i++)
                if (i >= 0 && i < Board.DIMENSION_BOARD) {
                    if(board[i][y]==0 && matrix[i][y]==null)
                        return true;
                }
        }
        return false;
    }

    private static boolean canBeEatted(int playerTurn){
        Piesa[][] table = Board.getMatrix();
        byte[][] opponent =Board.getMarkers((playerTurn+1)%2);
        byte[][] team =Board.getMarkers(playerTurn);
        boolean answer=false;

        for(int i=0; i<Board.DIMENSION_BOARD; i++){
            for(int j=0; j<Board.DIMENSION_BOARD; j++){
                int opponentColor = (playerTurn + 1)%2;
                if(table[i][j]!=null && table[i][j].getCuloare() == opponentColor){
                    if(table[i][j].isHasKing()) {
                        xPiece=i; yPiece=j;
                        answer = opponent[i][j]!=0;
                        if(team[i][j]!=0 && Board.getPiesa(xPiece, yPiece).getID()==6)
                            return false;
                        if(answer)
                            check=false;
                        break;
                    }
                }
            }
        }
        return answer;
    }
    private static boolean canBeSaved(int playerTurn){
        Piesa piece = Board.getPiesa(xPiece, yPiece);
        switch (piece.getID()){
            case 1: return false;
            case 3: return false;
            case 6: return false;
        }
        int kingX = playerTurn == 0 ? Rege.getxW() : Rege.getxB(); // getting the opponent position
        int kingY = playerTurn == 0 ? Rege.getyW() : Rege.getyB(); // getting the opponent position
        byte[][] board = Board.getMarkers(playerTurn);
        byte[][] hasKingBoard= Board.getMarkers(piece.getCuloare());
        check=false;
        if(kingX == xPiece){
            int first = Math.min(kingY, yPiece);
            int last = Math.max(kingY, yPiece);

            for(int i=first; i<last; i++){
                if(board[xPiece][i]!=0) {
                    statusSacriface=true;
                    piece.unmark((byte)xPiece, (byte)yPiece, hasKingBoard);
                    Board.setMarkers(hasKingBoard, piece.getCuloare());
                    return true;
                }
            }
        }
        else if(kingY == yPiece){
            int first =Math.min(kingX, xPiece);
            int last =Math.max(kingX, yPiece);

            for(int i=first; i<last; i++){
                if(board[i][yPiece]!=0) {
                    statusSacriface=true;
                    piece.unmark((byte)xPiece, (byte)yPiece, hasKingBoard);
                    Board.setMarkers(hasKingBoard, piece.getCuloare());
                    return true;
                }
            }
        }
        else if(Math.abs(xPiece - kingX) == Math.abs(yPiece - kingY)){
            int xAxis= xPiece < kingX ? 1 : -1;
            int yAxis= yPiece < kingY ? 1 : -1;
            int startX = Math.min(xPiece, kingX);
            int startY = Math.min(yPiece, kingY);
            for(int i=1 ; i<Math.abs(xPiece - kingX); i++){
                if(board[startX + (i*xAxis)][startY + (i*yAxis)]!=0){
                    statusSacriface=true;
                    piece.unmark((byte)xPiece, (byte)yPiece, hasKingBoard);
                    Board.setMarkers(hasKingBoard, piece.getCuloare());
                    return true;
                }
            }
        }
        check=true;
        return false;
    }
}
