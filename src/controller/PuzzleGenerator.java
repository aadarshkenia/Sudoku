
package controller;

import entities.Position;
import entities.Sudoku;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PuzzleGenerator {
    
    public static void main(String args[]){
        generate();
    }
    
    private static void generate(){
        Sudoku sudoku = Sudoku.getInstance(); //This should ideally be the place of initialization of singleton obj
        List<Position> emptyPositions = generateEmptyPositionsList(sudoku.size);
     
        
        generateHelper(sudoku.matrix, emptyPositions, 0);
        System.out.println("Valid sudoku: ");
        for(int i=0; i < sudoku.size; i++){
            for(int j=0; j < sudoku.size; j++){
                System.out.print(sudoku.matrix[i][j]+" ");
            }
            System.out.println();
        }
        
        List<Position> filledPos = generateFilledPositionsList(sudoku.matrix, Sudoku.size);
        
        while(filledPos.size() > 0){
            Position randomPos = getRandomPosition(filledPos);
            sudoku.matrix[randomPos.row][randomPos.col] = Sudoku.empty;
            int[][] dup_matrix = duplicateMatrix(sudoku.matrix);
            int solCount = SudokuSolver.solutionCounter(dup_matrix);
            if(solCount > 1){
                sudoku.matrix[randomPos.row][randomPos.col] = randomPos.val;
                break;
            }
            else if(solCount == 1){
                filledPos.remove(randomPos);
            }
            else{
                System.out.println("WTF!");
            }
        }
        
        System.out.println("Valid puzzle: ");
        for(int i=0; i < sudoku.size; i++){
            for(int j=0; j < sudoku.size; j++){
                System.out.print(sudoku.matrix[i][j]+" ");
            }
            System.out.println();
        }        
        
    }
    
    //Backtracking recursive function to generate a full-valid sudoku
    private static boolean generateHelper(int[][] matrix, List<Position> emptyPos, int index){
        Position currentPos = emptyPos.get(index);
        int row = currentPos.row;
        int col = currentPos.col;
        List<Integer> posIntegers = possibleNumbers(matrix, currentPos);
        while(posIntegers.size() > 0){
            int randomVal = getRandomValue(posIntegers);
            matrix[row][col] = randomVal;
            if(index == emptyPos.size() - 1)
                return true;
            if(generateHelper(matrix, emptyPos, index+1)){
                //System.out.println("index: "+index+" returned true");
                return true;           
            }
            else
                posIntegers.remove((Integer)randomVal);
        }
        matrix[row][col] = Sudoku.empty;
        return false;        
    }    
    
    
    public static int[][] duplicateMatrix(int[][] orig){
        int n = orig.length;
        int dup[][] = new int[n][n];
        for(int i = 0; i < n; i++)
            for(int j=0; j < n; j++)
                dup[i][j] = orig[i][j];
        return dup;
    }
    
    
    //Generate position objects for all 81 positions
    private static List<Position> generateEmptyPositionsList(int size){
        List<Position> ans = new ArrayList();
        for(int i=0; i < size; i++){
            for(int j=0; j < size; j++){
                Position position = new Position(i, j);
                ans.add(position);
            }
        }
        return ans;
    }
    
    private static List<Position> generateFilledPositionsList(int matrix[][], int size){
        List<Position> ans = new ArrayList();
        int emptyVal = Sudoku.empty;
        
        for(int i=0; i < size; i++)
            for(int j=0; j < size; j++)
                if(matrix[i][j] != emptyVal)
                    ans.add(new Position(i, j, matrix[i][j]));
        return ans;
    }
    
    //Returns list of possible numbers for a given position in a given sudoku
    private static List<Integer> possibleNumbers(int[][] matrix, Position p){
        List<Integer> ans = new ArrayList();
        int size = Sudoku.size;
        for(int i=1; i<= size; i++){
            if(Validator.validateAll(matrix, size, p.row, p.col, i))
                ans.add(i);
        }
        return ans;
    }
    
    //Selects a random position from a list of positions
    private static Position getRandomPosition(List<Position> emptyPos){
        int emptyListSize = emptyPos.size();
       
        Random random = new Random();
        int index = random.nextInt(emptyListSize);
        return emptyPos.get(index);        
    }
    
    private static int getRandomValue(List<Integer> values){
        int size = values.size();
       
        Random random = new Random();
        int index = random.nextInt(size);
        return values.get(index);        
    }
    
    
}
