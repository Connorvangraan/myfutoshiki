/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FutoshikiPuzzle;

/**
 *
 * @author Connor
 */
public class SmallerThan  extends Constraints {

    public SmallerThan (boolean t) {
        if (t) {
            type = "<";
        } else {
            type =  "^";
        }
    }
    
    public String getType() {
        return type;
    }
    
    
   
}
