package FutoshikiPuzzle;


import org.junit.Test;
import static org.junit.Assert.*;
import java.util.HashSet;
import java.util.Arrays;
import java.util.Set;

public class FutoshikiPuzzleTest {

    public FutoshikiPuzzleTest() {
    }

    /**
     * Tests the constructor and set up of the FutoshikiPuzzle class
     * Should pass
     */
    @Test
    public void setUp() {
        FutoshikiPuzzle f = new FutoshikiPuzzle(5);
        assertEquals(0, f.getSquare(0, 0));
        assertEquals(null, f.getColumnConstraint(0, 0));
        assertEquals(null, f.getRowConstraint(0, 0));
    }

    /**
     * Tests the setSquare method. Sets a square at grid loc (1,1) to be 1 and
     * checks it
     */
    @Test
    public void setSquareTest() {
        FutoshikiPuzzle f = new FutoshikiPuzzle(4);
        f.setSquare(1, 1, 1);
        assertEquals(1, f.getSquare(1, 1));
    }
    
    /**
     * Test for set square. Should fail and produce data type error as a string
     * is given instead of an int. Additionally, the other two parameters are 
     * larger in size than is available, also causing an error
     */
    
    @Test 
    public void setSquareTestFail() {
        FutoshikiPuzzle f = new FutoshikiPuzzle(4);
        f.setSquare("1", 10, 10); 
        
    }

    /**
     * Sets three row constraints in different location and tests the value are
     * stored correctly
     */
    @Test
    public void setRowConstraintTest() {
        FutoshikiPuzzle f = new FutoshikiPuzzle(5);
        for (int i = 0; i < 3; i++) {
            f.setRowConstraint(i, i, i);
        }
        assertEquals("^", f.getRowConstraint(0, 0));
        assertEquals("V", f.getRowConstraint(1, 1));
        assertEquals(" ", f.getRowConstraint(2, 2));
    }
    
    /**
     * Test for setRowConstraint method. Should fail and produce data type error
     * as a string is given instead of an int. Also, the other parameters are 
     * larger than the size of the array they are used for
     */
    
    @Test
    public void setRowConstraintTestFail() {
        FutoshikiPuzzle f = new FutoshikiPuzzle(10);
        for (int i = 0; i < 3; i++) {
            f.setRowConstraint(20, 50, "i");
        }
    }

    /**
     * Same as row test but for column
     */
    @Test
    public void setColumnConstraintTest() {
        FutoshikiPuzzle f = new FutoshikiPuzzle(5);
        for (int i = 0; i < 3; i++) {
            f.setColumnConstraint(i, i, i);
        }
        assertEquals(">", f.getColumnConstraint(0, 0));
        assertEquals("<", f.getColumnConstraint(1, 1));
        assertEquals(" ", f.getColumnConstraint(2, 2));
    }
    
    /**
     * Same as the setRowConstraint test. Should fail because string is given 
     * instead of int and also the other parameters are too large
     */
    @Test
    public void setColumnConstraintTestFail() {
        FutoshikiPuzzle f = new FutoshikiPuzzle(5);
        for (int i = 0; i < 3; i++) {
            f.setColumnConstraint(100, 100, "i");
        }
    }

    /**
     * Tests the getRandom method. Returned number will be smaller than the 
     * parameter of the method
     */
    @Test
    public void getRandomTest() {
        FutoshikiPuzzle f = new FutoshikiPuzzle(5);
        int r = f.getRandom(5);
        assertEquals(true, r < 5);
    }

    /**
     * Tests new line method. Should return a new line string. It does. Good.
     */
    @Test
    public void newLineTest() {
        FutoshikiPuzzle f = new FutoshikiPuzzle(5);
        assertEquals("\n", f.newLine());
    }

    /**
     * Tests enterDashes method. Same as the new line, checks correct string is
     * returned. Basically just included because I thought I had to.
     */
    @Test
    public void enterDashesTest() {
        FutoshikiPuzzle f = new FutoshikiPuzzle(5);
        f.enterDashes();
        assertEquals("\n  ---     ---     ---     ---     ---   \n", f.getString());
    }

    /**
     * This tests that the colConstraints, rowConstraints and the grid are all
     * populated correctly. The arrays are all checked to contain the correct
     * values for their respective type, e.g. numbers in the grid. The three 2
     * dimensional arrays (grid, colConstraints & rowConstraints)
     */
    @Test
    public void fillPuzzleTest() {
        String[] cvalues = {"<", ">", " "};
        String[] rvalues = {"V", "^", " "};
        FutoshikiPuzzle f = new FutoshikiPuzzle(5);
        f.fillPuzzle();
        String[][] cc = f.getAllColConstraints();
        String[][] rc = f.getAllRowConstraints();
        boolean[] result = new boolean[cc.length];
        for (int i = 0; i < cc.length; i++) {
            if (i <= cc[i].length) {
                Set<String> set = new HashSet<>(Arrays.asList(cc[i]));
                result[i] = Arrays.stream(cvalues).anyMatch(set::contains);
                assertEquals(true, result[i]);
            }
        }

        for (int i = 0; i < rc.length; i++) {
            if (i <= rc[i].length) {
                Set<String> set = new HashSet<>(Arrays.asList(rc[i]));
                result[i] = Arrays.stream(rvalues).anyMatch(set::contains);
                assertEquals(true, result[i]);
            }
        }

        int[][] grid = f.getGrid();
        String[] values = {"[1-5]", " "};
        for (int i = 0; i < grid.length; i++) {
            Set<String> set = new HashSet<>(Arrays.asList(rc[i]));
            result[i] = Arrays.stream(values).anyMatch(set::contains);
            assertEquals(true, result[i]);
        }
    }

    
    
    /**
     * Test to see if the board is correctly created and forms the correct
     * board shape upon printing
     */
    @Test
    public void displayStringTest() {
        FutoshikiPuzzle f = new FutoshikiPuzzle(5);
        f.fillPuzzle();
        System.out.println(f.displayString());
    }
    
    @Test
    public void isLegalTest () {
        FutoshikiPuzzle f = new FutoshikiPuzzle(5);
        assertEquals(f.isLegal(),true);
        
    }
    
    /**
    @Test
    public void getProblemsTest () {
        FutoshikiPuzzle f = new FutoshikiPuzzle(5);
        f.isLegal();
        f.getProblems();
    }
    **/ 
}
