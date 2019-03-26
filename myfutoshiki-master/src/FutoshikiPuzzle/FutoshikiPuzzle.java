package FutoshikiPuzzle;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class FutoshikiPuzzle {

    FutoshikiSquare[][] grid;
    int gridsize;
    Constraints[][] colConstraints;
    String[][] rowConstraints;
    Scanner scanner = new Scanner(System.in);
    Random rand = new Random();
    String board = "";
    ArrayList<String> errors = new ArrayList<String>();

    public FutoshikiPuzzle(int gridsize) {
        this.gridsize = gridsize;
        grid = new FutoshikiSquare[gridsize][gridsize];
        colConstraints = new Constraints[gridsize][gridsize - 1];
        rowConstraints = new String[gridsize][gridsize - 1];
    }

    public void setSquare(int input, int y, int x,boolean editable) {
        grid[y][x] = new FutoshikiSquare(input,0,editable);
    }
    
    public FutoshikiSquare getSquare (int x, int y) {
        return grid[x][y];
    }

    public int getSquareCell(int x, int y) {
        return grid[y][x].getCell();
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
                colConstraints[y][x] = new GreaterThan(true);
                break;
            case 1:
                colConstraints[y][x] = new SmallerThan(true);
                break;
            default:
                colConstraints[y][x] = new Empty();
                break;
        }
    }

    public Constraints getColumnConstraint(int y, int x) {
        return colConstraints[y][x];
    }
    
    public String getColumnConstraintValue(int y, int x) {
        return colConstraints[y][x].getType();
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
                Boolean fill = true;
                if (fill) {
                    setSquare(getRandom(grid.length) + 1, i, j,false); //change with random boolean to make it unmarked
                }

                if (grid[i][j].getCell() == 0) {
                    board = board.concat(" |   | ");
                } else {
                    board = board.concat(" | " + grid[i][j] + " | ");
                }

                if (j == 0 && i < grid.length - 1) {
                    board = board.concat(colConstraints[i][j].getType());
                } else if (i < grid.length - 1) {
                    board = board.concat(colConstraints[i][j - 1].getType());
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

    public FutoshikiSquare[][] getGrid() {
        return grid;
    }

    public String[][] getAllRowConstraints() {
        return rowConstraints;
    }

    public Constraints[][] getAllColConstraints() {
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
        for (FutoshikiSquare[] value : grid) {
            board = board.concat("  ---   ");
        }
        board = board.concat(newLine());

    }

    public String newLine() {
        return "\n";
    }

    public boolean isLegal() {
        boolean legal;
        for (FutoshikiSquare[] square : grid) {
            for (int i = 0; i < grid.length; i++) {
                System.out.print(square[i].getCell());
            }
            System.out.println(newLine());
        }

        for (int row = 0; row < grid.length; row++) {
            System.out.println("y: " + row);
            for (int column = 0; column < grid.length; column++) {
                System.out.println("Current number: " + grid[row][column].getCell());
                checkRow(row, column);
                checkColumn(row, column);
                if (grid[row][column].getCell() > gridsize) {
                    errors.add("Number is larger than max");
                }
            }
        }

        if (getProblems().isEmpty()) {
            legal = true;
        } else {
            legal = false;
        }

        System.out.println("Is legal: " + legal);
        return legal;
    }

    public String getProblems() {
        String errorReport = "";
        for (int i = 0; i < errors.size(); i++) {
            int index = i + 1;
            errorReport = errorReport.concat(index + ". " + errors.get(i) + "\n");
        }
        System.out.println(errorReport);
        return errorReport;
    }

    public boolean checkColumn(int row, int column) {
        boolean legal = true;
        for (int y = 0; y < grid.length; y++) {
            if (y != row) {
                if (grid[y][column].getCell() == grid[row][column].getCell()) {
                    errors.add(grid[y][column].getCell() + " occurs again at " + "row:" + y + " column: " + column);
                }
            }
        }

        if (row < rowConstraints.length - 1 && column < rowConstraints[row].length) {
            if (rowConstraints[row + 1][column].equals("V")) {
                if (grid[row][column].getCell() <= grid[row + 1][column].getCell()) {
                    errors.add("rowcon " + grid[row][column].getCell() + " is not > " + grid[row + 1][column].getCell());
                }

            } else if (rowConstraints[row + 1][column].equals("^")) {
                if (grid[row][column].getCell() >= grid[row + 1][column].getCell()) {
                    errors.add("rowcon " + grid[row][column].getCell() + " is not < " + grid[row + 1][column].getCell());
                }
            }
        }
        return legal;
    }

    public boolean checkRow(int row, int column) {
        boolean legal = true;
        for (int x = 0; x < grid.length; x++) {
            if (x != column) {
                if (grid[row][x].getCell() == grid[row][column].getCell()) {
                    errors.add(grid[row][column].getCell() + " occurs again at " + "row:" + row + " column:" + x);
                    legal = false;
                }
            }
        }

        if (column < colConstraints[row].length - 1) {
            if (getColumnConstraint(row, column).equals("<")) {
                if (grid[row][column].getCell() >= grid[row][column + 1].getCell()) {
                    errors.add("colcon " + grid[row][column].getCell() + " is not < " + grid[row][column + 1]);
                }
            } else if (getColumnConstraint(row, column).equals(">")) {
                if (grid[row][column].getCell() <= grid[row][column + 1].getCell()) {
                    errors.add("colcon " + grid[row][column].getCell() + " is not > " + grid[row][column + 1].getCell());
                }
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
