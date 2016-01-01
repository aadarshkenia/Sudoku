
package views;

import controller.PuzzleGenerator;
import entities.Sudoku;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

/**
 *
 * @author aadarsh-ubuntu
 */
public class SudokuUI {
    private JFrame mainFrame;
    private List<JFormattedTextField> numberFields = null; 
   
    public static void main(String args[]){
        SudokuUI userInterface = new SudokuUI();
        userInterface.prepareGUI();
        
    }
    
    private void prepareGUI(){
        mainFrame = new JFrame();
        mainFrame.setSize(700, 400);
        mainFrame.setLayout(new GridLayout(1, 2));
        
        //Adding the main sudoku panel for the actual game
        mainFrame.add(createSudokuPanel());
        mainFrame.add(createButtonPanel());
        
        mainFrame.setVisible(true);
    }
    
    private JPanel createSudokuPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(9,9));
        numberFields = new ArrayList();
        for(int i=0; i<81; i++){
            MaskFormatter digitMask = digitLimiterMask();
            if(digitMask != null){
                JFormattedTextField jtf = new JFormattedTextField(digitMask);
                jtf.setHorizontalAlignment(JFormattedTextField.CENTER);
                panel.add(jtf);
                numberFields.add(jtf);
            }
        }
        return panel;
    }
    
    private JPanel createButtonPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 1));
        JLabel optionsLabel = new JLabel("", JLabel.CENTER);
        optionsLabel.setText("OPTIONS");
        optionsLabel.setSize(50, 50);
        panel.add(optionsLabel);
        
        JPanel optionsPanel = createOptionsPanel();
        panel.add(optionsPanel);
        
        return panel;
    }
    
    private JPanel createOptionsPanel(){
        JPanel panel = new JPanel();
        panel.setLayout(new FlowLayout());
        JButton newGameButton = new JButton("New");
        JButton solutionButton = new JButton("Check");
        JButton exitButton = new JButton("Exit");
        panel.add(newGameButton);
        panel.add(solutionButton);
        panel.add(exitButton);
        
        //Adding listeners to buttons
        addListeners(newGameButton, solutionButton, exitButton);
        
        return panel;
    }

    private void addListeners(JButton newGameButton, JButton solutionButton, JButton exitButton){
        newGameButton.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //Generate sudoku using controller's instance
                PuzzleGenerator generatorInstance = PuzzleGenerator.getInstance();
                generatorInstance.generate();
                //Get Sudoku's instance
                Sudoku sudoku = Sudoku.getInstance();
                
                int size = Sudoku.size;
                int numSquares = size*size;
                for(int i=0; i < numSquares; i++){
                    JFormattedTextField jtf = numberFields.get(i);
                    int row = i/size;
                    int col = i%size;
                    int value = sudoku.matrix[row][col];
                    if(value >=1 && value <= size)
                        jtf.setText(Integer.toString(value));
                    else
                        jtf.setText("");
                }
          }
        });
        
        
    }    
    
    //Returns a mask that limits input in textfield from 1-9
    private MaskFormatter digitLimiterMask(){
        MaskFormatter mf = null;
        try {
            mf = new MaskFormatter("*");
            mf.setValidCharacters("123456789");
        } catch (ParseException ex) {
            Logger.getLogger(SudokuUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mf;
    }
    
}
