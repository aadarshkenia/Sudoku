package entities;

/**
 *
 * @author aadarsh-ubuntu
 */


//Singleton classs
public class Sudoku {
    //Static vars
    public static int size = 9;
    public static final int empty = -1; //Indicator of empty position

    //Instance vars    
    public int matrix[][];
    private static Sudoku instance = null; //singleton instance
    
    
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