/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package FutoshikiPuzzle;

public class FutoshikiSquare {
    private int cell = 0;
    private int answer = 0;
    private boolean editable = true;

    public FutoshikiSquare(int cell, int answer, boolean editable) {
        this.cell = cell;
        this.answer=answer;
        this.editable=editable;
    }

    public int getCell() {
        return cell;
    }

    public void setCell(int cell) {
        this.cell = cell;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    public boolean isEditable() {
        return editable;
    }

    public void setEditable(boolean editable) {
        this.editable = editable;
    }
    
    
    
}
