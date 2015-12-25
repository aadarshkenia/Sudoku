/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

/**
 *
 * @author aadarsh-ubuntu
 */
public class Position {
    public int row;
    public int col;
    public int val;
    
    public Position(int row, int col, int val){
        this.row = row;
        this.col = col;
        this.val = val;
    }
    
    public Position(int row, int col){
        this.row = row;
        this.col = col;
        this.val = Sudoku.empty;
    }
}
