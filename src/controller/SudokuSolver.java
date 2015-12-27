/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import entities.Sudoku;
import entities.Position;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author aadarsh-ubuntu
 */
public class SudokuSolver {
    
    //TESTING
    public static void main(String args[]){
        Sudoku sudoku = Sudoku.getInstance();
        initFromFile(sudoku, "input.txt");
        solver(sudoku);
        writeOutput(sudoku);
    }
    
    //Naive backtracking solution
    public static void solver(Sudoku sudoku){       
        List<Position> emptyPositions = findEmptyPositions(sudoku.matrix);
        boolean ans = solverHelper(sudoku.matrix, emptyPositions, 0);       
        System.out.println("Solution possible: "+ans);
    }
    
    //Counts number of solutions for a given sudoku matrix
    public static int solutionCounter(int[][] matrix){
        List<Position> emptyPositions = findEmptyPositions(matrix);
        return solutionCounterHelper(matrix, emptyPositions, 0);
    }
    
    private static void initFromFile(Sudoku sudoku, String filename){
        int size = Sudoku.size;
        int emptyVal = Sudoku.empty;
        BufferedReader br = null;
        try{
            br = new BufferedReader(new FileReader(new File(filename)));            
            for(int i=0; i < size; i++){
                String str = br.readLine();
                String splitStr[] = str.split("\\s+");
                for(int j=0; j < size; j++){
                    sudoku.matrix[i][j] = Integer.parseInt(splitStr[j]);
                }
            }
        }
        catch(IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    private static void writeOutput(Sudoku sudoku){
        PrintWriter writer = null;
        try{
            writer = new PrintWriter("output.txt");
            int size = Sudoku.size;
            for(int i=0; i < size; i++){
                StringBuilder row = new StringBuilder();
                for(int j=0; j < size; j++){
                    row.append(sudoku.matrix[i][j]+" ");
                }
                writer.println(row.toString());
            }            
        }
        catch(IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();            
        }
        finally{
            if(writer != null)
                writer.close();
        }
    }
    
    //Returns the number of possible solutions
    public static boolean solverHelper(int matrix[][], List<Position> emptyPositions, int index){
        int size = Sudoku.size;
        int emptyVal = Sudoku.empty;
        
        Position cur_pos = emptyPositions.get(index);
        int cur_row = cur_pos.row;
        int cur_col = cur_pos.col;
        
        int numSolutions = 0;
        for(int i=1; i <= size; i++){            
            if(Validator.validateAll(matrix, size, cur_row, cur_col, i)){   
                matrix[cur_row][cur_col] = i;               
                if(index == emptyPositions.size()-1)
                    return true;                
                if(solverHelper(matrix, emptyPositions, index+1))
                    return true;
                else
                    matrix[cur_row][cur_col] = emptyVal;
            }
        }
        return false;
    }
    
    //Alternate solverHelper returning count of possible solutions
    private static int solutionCounterHelper(int matrix[][], List<Position> emptyPositions, int index){
        //System.out.println("ES index: "+index);
        int size = Sudoku.size;
        int emptyVal = Sudoku.empty;
        
        Position cur_pos = emptyPositions.get(index);
        int cur_row = cur_pos.row;
        int cur_col = cur_pos.col;
        
        int numSolutions = 0;
        for(int i=1; i <= size; i++){            
            if(Validator.validateAll(matrix, size, cur_row, cur_col, i)){   
                matrix[cur_row][cur_col] = i;               
                if(index == emptyPositions.size()-1)
                    return 1;            
                int subSolutions = solutionCounterHelper(matrix, emptyPositions, index+1);
                //System.out.println("SS: "+subSolutions);
                if(subSolutions >= 1)
                    numSolutions += subSolutions;
                
                matrix[cur_row][cur_col] = emptyVal;
            }
        }
        //System.out.println("NS: "+numSolutions);
        return numSolutions;
    }
    
    
    private static List<Position> findEmptyPositions(int[][] matrix){
        int size = Sudoku.size;
        int emptyVal = Sudoku.empty;
        ArrayList<Position> ans = new ArrayList<Position>();
        
        for(int i=0; i < size; i++){
            for(int j=0; j < size; j++){
                if(matrix[i][j] == emptyVal)
                    ans.add(new Position(i, j));
            }
        }
        return ans;
    }
    
    
}
