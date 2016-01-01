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
    private static Sudoku instance_ = null; //singleton instance_
    
    
    public void setSize(int size){
        this.size = size;
        //Re-initiate object when size changes
        instance_ = new Sudoku();
    }
    
    private Sudoku(){
        matrix = new int[size][size];
        //Initializing all cells to empty
        for(int i = 0; i < size; i++)
            for(int j = 0 ; j < size; j++)
                matrix[i][j] = empty;
        
    }
    
    public static Sudoku getInstance(){
        if(instance_ == null)
            instance_ = new Sudoku();
        return instance_;
    }
}
