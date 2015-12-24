package Entities;

/**
 *
 * @author aadarsh-ubuntu
 */


//Singleton classs
public class Sudoku {
    private int size = 9;
    int matrix[][];
    
    private static Sudoku instance = null;
    
    public int getSize(){
        return this.size;
    }
    
    public void setSize(int size){
        this.size = size;
        //Re-initiate object when size changes
        instance = new Sudoku();
    }
    
    private Sudoku(){
        matrix = new int[size][size];
    }
    
    public static Sudoku getInstance(){
        if(instance == null)
            instance = new Sudoku();
        return instance;
    }
}
