package FutoshikiPuzzle;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class FutoshikiPuzzle {

    FutoshikiSquare[][] grid;
    int gridsize;
    Constraints[][] colConstraints;
    Constraints[][] rowConstraints;
    Scanner scanner = new Scanner(System.in);
    Random rand = new Random();
    String board = "";
    ArrayList<String> errors = new ArrayList<String>();

    public FutoshikiPuzzle(int gridsize) {
        this.gridsize = gridsize;
        grid = new FutoshikiSquare[gridsize][gridsize];
        colConstraints = new Constraints[gridsize][gridsize - 1];
        rowConstraints = new Constraints[gridsize][gridsize - 1];
    }

    public void setSquare(int input, int y, int x, boolean editable) {
        grid[y][x] = new FutoshikiSquare(input, 0, editable);
    }

    public FutoshikiSquare getSquare(int x, int y) {
        return grid[x][y];
    }

    public int getSquareCell(int x, int y) {
        return grid[y][x].getCell();
    }

    public void setRowConstraint(int y, int x, int type) {
        switch (type) {
            case 0:
                rowConstraints[y][x] = new GreaterThan(false);
                break;
            case 1:
                rowConstraints[y][x] = new SmallerThan(false);
                break;
            default:
                rowConstraints[y][x] = new Empty();
                break;
        }
    }

    public Constraints getRowConstraint(int y, int x) {
        return rowConstraints[y][x];
    }

    public String getRowConstraintType(int y, int x) {
        return rowConstraints[y][x].getType();
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

    public String getColumnConstraintType(int y, int x) {
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
            int[] numbers = new int[gridsize];

            int m = 1;
            while (m <= gridsize) {
                int index = getRandom(gridsize);
                if (numbers[index] == 0) {
                    numbers[index] = m;
                    m++;
                }
            }

            for (int i = 0; i < grid[j].length; i++) {
                Boolean fill = true;
                System.out.println("i: "+i);

                boolean success = false;
                while (success == false) {
                    if (j > 0) {
                        if (checkColumnNumbers(j, i)) {
                            setSquare(numbers[i], i, j, false); //change with random boolean to make it unmarked
                            success = true;
                        }
                    }
                    else {
                        setSquare(numbers[i], i, j, false);
                        success = true; 
                    }

                }

                if (grid[i][j].getCell() == 0) {
                    board = board.concat(" |   | ");
                } else {
                    board = board.concat(" | " + grid[i][j].getCell() + " | ");
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
                    board = board.concat("   " + rowConstraints[k][j].getType() + "    ");
                }
            }
        }
    }

    public FutoshikiSquare[][] getGrid() {
        return grid;
    }

    public Constraints[][] getAllRowConstraints() {
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

    /**
     * public int[] getNumbers() { int[] numbers = new int[gridsize]; int m = 1;
     * while (m <= gridsize) { int index = getRandom(gridsize); if
     * (numbers[index] == 0) { numbers[index] = m; m++; } } return numbers; }
     *
     */
    public boolean isLegal() {
        boolean legal;

        /**
         * for (FutoshikiSquare[] square : grid) { for (int i = 0; i <
         * grid.length; i++) { System.out.print(square[i].getCell()); }
         * System.out.println(newLine()); }
        *
         */
        for (int row = 0; row < grid.length; row++) {
            for (int column = 0; column < grid.length; column++) {
                System.out.println("Current number: " + grid[row][column].getCell());
                checkRow(row, column);
                checkColumn(row, column);
                if (grid[row][column].getCell() > gridsize) {
                    errors.add("Number is larger than max");
                }
            }
        }

        legal = getProblems().isEmpty();
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
        boolean legal = checkColumnNumbers(row, column);
        if (legal) {
            legal = checkColumnConstraints(row, column);
        }
        return legal;
    }

    public boolean checkColumnNumbers(int row, int column) {
        System.out.println("Row: "+row+" Column: "+column);
        boolean legal = true;
        for (int y = 0; y < grid.length; y++) {
            System.out.println("Y: " + y);
            if (y != row) {
                System.out.println("passed ");
                try {
                    if (grid[y][column].getCell() == grid[row][column].getCell()) {
                        errors.add(grid[y][column].getCell() + " occurs again at " + "row:" + y + " column: " + column);
                        legal = false;
                        System.out.println("tried");
                    }
                    else {
                        System.out.println("success");
                    }
                } catch (Exception e) {
                    System.out.println("caught");
                }
            }
        }
        System.out.println("Column clear: " + legal);
        return legal;
    }

    public boolean checkColumnConstraints(int row, int column) {
        boolean legal = true;

        if (row < rowConstraints.length - 1 && column < rowConstraints[row].length) {
            try {
                if (rowConstraints[row + 1][column].getType().equals("V")) {
                    if (grid[row][column].getCell() <= grid[row + 1][column].getCell()) {
                        errors.add("rowcon " + grid[row][column].getCell() + " is not > " + grid[row + 1][column].getCell());
                        legal = false;
                    }

                } else if (rowConstraints[row + 1][column].getType().equals("^")) {
                    if (grid[row][column].getCell() >= grid[row + 1][column].getCell()) {
                        errors.add("rowcon " + grid[row][column].getCell() + " is not < " + grid[row + 1][column].getCell());
                        legal = false;
                    }
                }
            } catch (Exception e) {
            }
        }
        return legal;
    }

    public boolean checkRow(int row, int column) {
        boolean legal = true;
        legal = checkRowNumbers(row, column);
        if (legal) {
            legal = checkRowConstraints(row, column);
        }
        return legal;
    }

    public boolean checkRowNumbers(int row, int column) {
        boolean legal = true;

        for (int x = 0; x < grid.length; x++) {
            if (x != column) {
                try {
                    if (grid[row][x].getCell() == grid[row][column].getCell()) {
                        errors.add(grid[row][column].getCell() + " occurs again at " + "row:" + row + " column:" + x);
                        legal = false;
                    }
                } catch (Exception e) {
                    legal = true;
                }
            }
        }

        System.out.println("Row clear: " + legal);
        return legal;
    }

    public boolean checkRowConstraints(int row, int column) {
        boolean legal = true;
        if (column < colConstraints[row].length - 1) {
            try {
                if (getColumnConstraintType(row, column).equals("<")) {
                    if (grid[row][column].getCell() >= grid[row][column + 1].getCell()) {
                        errors.add("colcon " + grid[row][column].getCell() + " is not < " + grid[row][column + 1].getCell());
                        legal = false;
                    }
                } else if (getColumnConstraintType(row, column).equals(">")) {
                    if (grid[row][column].getCell() <= grid[row][column + 1].getCell()) {
                        errors.add("colcon " + grid[row][column].getCell() + " is not > " + grid[row][column + 1].getCell());
                        legal = false;
                    }
                }
            } catch (Exception e) {
                legal = true;
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
