public class Board {
    public static final int DIMENSION_BOARD = 8;
    private static final String green = "\u001B[42m";
    private static final String white = "\u001B[47m";
    private static final String blackLetter = "\u001B[30m";
    private static final String whiteLetter = "\033[1;97m";
    public static final String redLetter = "\u001B[91m";
    private static final String reset = "\u001B[0m";
    private static final char[][] matrixColor = {{'W', 'B', 'W', 'B', 'W', 'B', 'W', 'B'},
            {'B', 'W', 'B', 'W', 'B', 'W', 'B', 'W'},
            {'W', 'B', 'W', 'B', 'W', 'B', 'W', 'B'},
            {'B', 'W', 'B', 'W', 'B', 'W', 'B', 'W'},
            {'W', 'B', 'W', 'B', 'W', 'B', 'W', 'B'},
            {'B', 'W', 'B', 'W', 'B', 'W', 'B', 'W'},
            {'W', 'B', 'W', 'B', 'W', 'B', 'W', 'B'},
            {'B', 'W', 'B', 'W', 'B', 'W', 'B', 'W'}};

    private static final int[] startPos={0, 7};
    private static final int[] turaPos = {0, 7};
    private static final int[] calPos = {1, 6};
    private static final int[] nebunPos = {2, 5};
    private static Piesa[][] matrix = new Piesa[8][8];
    private static byte[][][] markers = new byte[2][8][8];
    public static void addPieces() {
        for (int i = 0; i < DIMENSION_BOARD; i++) {
            matrix[6][i] = new Pion((byte) 0);
            matrix[1][i] = new Pion((byte) 1);
        }

        for (int i = 0; i < 2; i++) {
            for(int j=0; j<2; j++) {
                matrix[startPos[i]][turaPos[j]] = new Tura((byte) (1 - i));
                matrix[startPos[i]][calPos[j]] = new Cal((byte) (1 - i));
                matrix[startPos[i]][nebunPos[j]] = new Nebun((byte) (1 - i));
            }
            matrix[startPos[i]][3] = new Regina((byte)(1-i));
            matrix[startPos[i]][4] = new Rege((byte)(1-i));
        }
        Rege.setWhite((byte)7, (byte)4);
        Rege.setBlack((byte)0, (byte) 4);
        Mark();
    }
    private static void Mark(){
        for(int i=0; i< DIMENSION_BOARD; i++){
            for(int j=0; j<DIMENSION_BOARD; j++)
               if(matrix[i][j]!=null)
                   matrix[i][j].mark((byte)i, (byte)j, markers[matrix[i][j].getCuloare()]);
        }
    }
    public static void printBoard() {
        char letter = 'A';
        System.out.println("=============================");
        System.out.print(' ');
        for(int i=0; i< DIMENSION_BOARD; i++)
            System.out.print(" "+(char)(letter+i)+" ");
        System.out.println();

        for (int i = 0; i < DIMENSION_BOARD; i++) {
            System.out.print(DIMENSION_BOARD - i);
            for (int j = 0; j < DIMENSION_BOARD; j++) {
                String blockColor = matrixColor[i][j] == 'W' ? white : green;
                char piece;
                if(matrix[i][j]!= null) {
                    piece = switch (matrix[i][j].getID()) {
                        case 1 -> 'p';
                        case 2 -> 'r';
                        case 3 -> 'n';
                        case 4 -> 'b';
                        case 5 -> 'Q';
                        default -> 'K';
                    };
                }
                else piece = ' ';
                String colorLetter=blackLetter;
                if(matrix[i][j]!=null) {
                    byte[][] table = Board.getMarkers((matrix[i][j].getCuloare()+1)%2);
                    if(matrix[i][j] instanceof Rege && table[i][j]!=0) {
                        colorLetter = redLetter;
                    }
                    else
                        colorLetter = matrix[i][j].getCuloare() == 1 ? blackLetter : whiteLetter;
                }
                System.out.print(blockColor + colorLetter + ' '+ piece + ' ' + reset);
            }
            System.out.print(DIMENSION_BOARD - i);
            System.out.println();
        }
        System.out.print(' ');
        for(int i=0; i< DIMENSION_BOARD; i++)
            System.out.print(" "+(char)(letter+i)+" ");
        System.out.println("\n=============================");
    }
    public static Piesa[][] getMatrix() {
        return matrix;
    }
    public static void setMatrix(Piesa[][] matrix) {
        Board.matrix = matrix;
    }

    public static byte[][] getMarkers(int color) {
        return markers[color];
    }

    public static void setMarkers(byte[][] markers, int color) {
        Board.markers[color] = markers;
    }
    public static Piesa getPiesa(int x, int y){
        return matrix[x][y];
    }
    public static void setPiesa(int x, int y, Rege piesa){
        Board.matrix[x][y] = piesa;
    }
}

