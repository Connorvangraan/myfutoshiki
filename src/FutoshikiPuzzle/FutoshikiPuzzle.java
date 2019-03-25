package FutoshikiPuzzle;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class FutoshikiPuzzle {

    int[][] grid;
    int gridsize;
    String[][] colConstraints;
    String[][] rowConstraints;
    Scanner scanner = new Scanner(System.in);
    Random rand = new Random();
    String board = "";
    ArrayList<String> errors = new ArrayList<String>(); 

    public FutoshikiPuzzle(int gridsize) {
        this.gridsize=gridsize;
        grid = new int[gridsize][gridsize];
        colConstraints = new String[gridsize][gridsize-1];
        rowConstraints = new String[gridsize][gridsize-1];
    }

    public void setSquare(int input, int y, int x) {
        grid[y][x] = input;
    }
    
    public int getSquare (int x, int y) {
        return grid[y][x];
    }

    public void setRowConstraint(int y, int x, int type) {
        switch (type) {
            case 0:
                rowConstraints[y][x] = "^";
                break;
            case 1:
                rowConstraints[y][x] = "V";
                break;
            default:
                rowConstraints[y][x] = " ";
                break;
        }
    }
    
    public String getRowConstraint(int y, int x) {
        return rowConstraints[y][x];
    }

    public void setColumnConstraint(int y, int x, int type) {
        switch (type) {
            case 0:
                colConstraints[y][x] = ">";
                break;
            case 1:
                colConstraints[y][x] = "<";
                break;
            default:
                colConstraints[y][x] = " ";
                break;
        }
    }
    
    public String getColumnConstraint(int y, int x) {
        return colConstraints[y][x];
    }

    public void fillPuzzle() {
        for (int i = 0; i < colConstraints.length; i++) {
            for (int j = 0; j < colConstraints[i].length; j++) {
                setColumnConstraint(i, j, getRandom(10));
            }
        }

        for (int i = 0; i < rowConstraints.length; i++) {
            for (int j = 0; j < rowConstraints[i].length; j++) {
                setRowConstraint(i, j, getRandom(10));
            }
        }
        for (int j = 0; j < grid.length; j++) {
            enterDashes();
            for (int i = 0; i < grid[j].length; i++) {
                //Boolean fill = rand.nextBoolean();
                Boolean fill = true;

                if (fill) {
                    setSquare(getRandom(grid.length) + 1, i, j);
                }

                if (grid[i][j] == 0) {
                    board = board.concat(" |   | ");
                } else {
                    board = board.concat(" | " + grid[i][j] + " | ");
                }

                if (j == 0 && i < grid.length - 1) {
                    board = board.concat(colConstraints[i][j]);
                } else if (i < grid.length - 1) {
                    board = board.concat(colConstraints[i][j - 1]);
                }
            }
            enterDashes();
            for (int k = 0; k < grid.length; k++) {
                if (j < grid.length - 1) {
                    board = board.concat("   " + rowConstraints[k][j] + "    ");
                }
            }
        }
    }
    
    public int[][] getGrid () {
        return grid;
    }
    
    public String[][] getAllRowConstraints() {
        return rowConstraints;
    }
    
    public String[][] getAllColConstraints() {
        return colConstraints;
    }

    public String displayString() {
        return board;
    }
    
    public String getString() {
        return board;
    }

    public int getRandom(int limit) {
        Random random = new Random();
        int number = random.nextInt(limit);
        return number;
    }

    public void enterDashes() {
        board = board.concat(newLine());
        for (int[] value : grid) {
            board = board.concat("  ---   ");
        }
        board = board.concat(newLine());

    }

    public String newLine() {
        return "\n";
    }

    
    public boolean isLegal(){
        fillPuzzle();
        boolean legal = true;
        for (int y = 0; y < grid.length; y++) {
            for (int x = 0; x < grid[y].length;x++){
                if (!checkRow(y,grid[y][x])) {
                    legal = false;
                }
                if (!checkColumn(x,grid[y][x])) {
                    legal = false;
                }
            }
        }
        getProblems();
        System.out.println("Is legal: "+legal);
        return legal;
    }
    
    public String getProblems () {
        String errorReport = "";
        for (int i = 0; i<errors.size();i++) {
            int index = i+1;
            errorReport = errorReport.concat(index+". "+errors.get(i)+"\n");
        }
       System.out.println(errorReport);
       return errorReport;
    }
    
    public boolean checkColumn (int column, int check) {
        boolean legal = true;
        for (int y = 0; y < grid.length; y++) {
            if(grid[y][column]==check) {
                String report = check+" occurs again at "+"row:"+y+" column:"+column;
                errors.add(report);
                legal = false;
            }
        }
        return legal;
    }
    
    public boolean checkRow(int row,int check) {
        boolean legal = true;
        for (int x = 0; x < grid.length;x++){
                if(grid[row][x]==check) {
                    String report = check+" occurs again at "+"row:"+row+" column:"+x;
                    errors.add(report);
                    legal = false;
                }
            } 
        return legal;
    }
    
    public static void main(String[] args) {
        FutoshikiPuzzle f = new FutoshikiPuzzle(5);
        f.fillPuzzle();
        System.out.println(f.displayString());
    }

}
