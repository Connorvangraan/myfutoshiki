package FutoshikiPuzzle;

public class GreaterThan extends Constraints {
    
    public GreaterThan(boolean t) {
        if (t) {
            type = ">";
        } else {
            type = "V";
        }
    }

    public boolean process(int x, int y) {
        return x > y;
    }
    
    
    public String getType () {
        return type;
    }

    
}
