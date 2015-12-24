/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

/**
 *
 * @author aadarsh-ubuntu
 */
public class Validator {
   
    public static boolean validateRow(int[][] matrix, int size, int x, int y, int val){
        for(int i=0; i < size; i++){
            if(i!=y && matrix[x][i]==val)
                return false;
        }
        return true;
    }
    
    public static boolean validateColumn(int[][] matrix, int size, int x, int y, int val){
        for(int i=0; i < size; i++){
            if(i!=y && matrix[i][y]==val)
                return false;
        }
        return true;
    }
    
    public static boolean validateBlock(int[][] matrix, int size, int x, int y, int val){
        int blocksize = (int)Math.sqrt(size);
        int blockRowStart = (x/blocksize) * blocksize;
        int blockColStart = (y/blocksize) * blocksize;
        
        for(int i=0; i < blocksize; i++){
            int cur_row = blockRowStart + i;    
            for(int j=0; j < blocksize; j++){
                int cur_col = blockColStart + j;
                if(cur_row != x || cur_col != y){
                    if(matrix[cur_row][cur_col] == val)
                        return false;
                }
            }
        }        
        return true;
    }    
}
