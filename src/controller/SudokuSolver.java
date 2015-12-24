/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import Entities.Sudoku;
import Entities.Position;
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
    
    //Naive backtracking solution
    public static void solver(Sudoku sudoku){
       
        List<Position> emptyPositions = findEmptyPositions(sudoku);
        solverHelper(sudoku.matrix, emptyPositions, 0);       
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
        try{
            PrintWriter writer = new PrintWriter("output.txt");
            int size = Sudoku.size;
            for(int i=0; i < size; i++){
                StringBuilder row = new StringBuilder();
                for(int j=0; j < size; j++){
                    row.append(sudoku.matrix[i][j]+" ");
                }
                writer.println(row.toString()+"\n");
            }            
        }
        catch(IOException e){
            System.out.println(e.getMessage());
            e.printStackTrace();            
        }
    }
    
    private static boolean solverHelper(int matrix[][], List<Position> emptyPositions, int index){
        int size = Sudoku.size;
        int emptyVal = Sudoku.empty;
        
        Position cur_pos = emptyPositions.get(index);
        int cur_row = cur_pos.row;
        int cur_col = cur_pos.col;
        
        for(int i=1; i <= size; i++){
            if(Validator.validateRow(matrix, size, cur_row, cur_col, i)
               && Validator.validateColumn(matrix, size, cur_row, cur_col, i)
               && Validator.validateBlock(matrix, size, cur_row, cur_col, i)){
               
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
    
    private static List<Position> findEmptyPositions(Sudoku sudoku){
        int size = Sudoku.size;
        int emptyVal = Sudoku.empty;
        ArrayList<Position> ans = new ArrayList<Position>();
        
        for(int i=0; i < size; i++){
            for(int j=0; j < size; j++){
                if(sudoku.matrix[i][j] == emptyVal)
                    ans.add(new Position(i, j));
            }
        }
        return ans;
    }
    
    
}
