package tictactoeguiwai;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import java.util.Stack;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

public class Logic
{
    GUI gui;
    Random rnd = new Random();
    
    boolean firstTurn, p1Check, computerCheck;
    int i, j, k;
    String difficulty;
    
    public Logic(GUI gui)
    {
        this.gui = gui;
    }
    
    void turn()
    {
        String[] difficultyOptions = {"Easy", "Medium", "Hard"};

        JOptionPane optionPane = new JOptionPane(
                "Select Difficulty Level",
                JOptionPane.QUESTION_MESSAGE,
                JOptionPane.DEFAULT_OPTION,
                null,
                difficultyOptions,
                difficultyOptions[0]);

        JDialog dialog = optionPane.createDialog("Difficulty Level");
        
        dialog.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);

        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                JOptionPane.showMessageDialog(dialog, "Please select a difficulty level.");
            }
        });

        dialog.setVisible(true);

        Object selectedValue = optionPane.getValue();

        if (selectedValue != null && selectedValue instanceof String) {
            difficulty = (String) selectedValue;
        }
        
        firstTurn = rnd.nextBoolean();
        if (firstTurn == true)
        {
            gui.p1Label.setText("Player is X");
            gui.computerLabel.setText("Computer is O");
        }
        else
        {
            gui.p1Label.setText("Player is O");
            gui.computerLabel.setText("Computer is X");
        }

        firstTurn = rnd.nextBoolean();
        if (firstTurn == true)
        {
            gui.turnLabel.setText("X's Turn");
        }
        else
        {
            gui.turnLabel.setText("O's Turn");
        }
        
        if(gui.computerLabel.getText().substring(12).matches(gui.turnLabel.getText().substring(0,0)))
        {
            aiTurn();
        }
    }
    
    void winCondition()
    {
        String condition = null;
        for (i = 0; i < 8;i++)
        {
            switch(i)
            {
                case 0:
                    condition = gui.button[0].getText() + gui.button[1].getText() + gui.button[2].getText();
                    break;
                case 1:
                    condition = gui.button[3].getText() + gui.button[4].getText() + gui.button[5].getText();
                    break;
                case 2:
                    condition = gui.button[6].getText() + gui.button[7].getText() + gui.button[8].getText();
                    break;
                case 3:
                    condition = gui.button[0].getText() + gui.button[3].getText() + gui.button[6].getText();
                    break;
                case 4:
                    condition = gui.button[1].getText() + gui.button[4].getText() + gui.button[7].getText();
                    break;
                case 5:
                    condition = gui.button[2].getText() + gui.button[5].getText() + gui.button[8].getText();
                    break;
                case 6:
                    condition = gui.button[0].getText() + gui.button[4].getText() + gui.button[8].getText();
                    break;
                case 7:
                    condition = gui.button[6].getText() + gui.button[4].getText() + gui.button[2].getText();
                    break;
            }
            
            switch(condition)
            {
                case "XXX":
                    p1Check = gui.p1Label.getText().contains("X");
                    computerCheck = gui.computerLabel.getText().contains("X");
                    score(); clear();
                    break;
                case "OOO":
                    p1Check = gui.p1Label.getText().contains("O");
                    computerCheck = gui.computerLabel.getText().contains("O");
                    score(); clear();
                    break;
                default:
                    break;
            }
        }
        
    }

    void score()
    {
        if (p1Check == true && computerCheck == false)
        {
            JOptionPane.showMessageDialog(null, gui.p1Label.getText().substring(0,6) + " won",
                    "Announcement", JOptionPane.PLAIN_MESSAGE);
            gui.p1Score += 1;
            gui.p1ScoreLabel.setText("Score: " + gui.p1Score);
        }
        else
        {
            JOptionPane.showMessageDialog(null, gui.computerLabel.getText().substring(0,8) + " won", 
                    "Announcement", JOptionPane.PLAIN_MESSAGE);
            gui.computerScore += 1;
            gui.computerScoreLabel.setText("Score: " + gui.computerScore);
        }
    }
    
    void clear()
    {
        for(i = 0;i < 9;i++)
        {
            gui.button[i].setText("");
            gui.button[i].setSelected(false);
            gui.button[i].setEnabled(true);
        }
        turn();
    }
    
    void aiTurn()
    {
        switch(difficulty)
        {
            case "Easy":
                int emptyButtonIndex;
                do
                {
                    emptyButtonIndex = rnd.nextInt(9);
                }
                while (!gui.button[emptyButtonIndex].getText().isEmpty());
                
                gui.button[emptyButtonIndex].setFont(gui.buttonFont);
                gui.button[emptyButtonIndex].setText(gui.computerLabel.getText().substring(12));
                gui.turnLabel.setText(gui.p1Label.getText().substring(10) + "'s Turn");
                gui.button[emptyButtonIndex].setEnabled(false);
                break;
            case "Medium":
                break;
                
            case "Hard":
                int bestScore = Integer.MIN_VALUE;
                int bestMove = -1;
                int alpha = Integer.MIN_VALUE;
                int beta = Integer.MAX_VALUE;
                
                for (int move = 0; move < 9; move++)
                {
                    if (gui.button[move].isEnabled())
                    {
                        gui.button[move].setText(gui.computerLabel.getText().substring(12));
                        int score = minimax(0, alpha, beta, false);
                        gui.button[move].setText("");
                        if (score > bestScore)
                        {
                            bestScore = score;
                            bestMove = move;
                        }
                    }
                    alpha = Math.max(alpha, bestScore);
                }

                gui.button[bestMove].setFont(gui.buttonFont);
                gui.button[bestMove].setText(gui.computerLabel.getText().substring(12));
                gui.turnLabel.setText(gui.p1Label.getText().substring(10) + "'s Turn");
                gui.button[bestMove].setEnabled(false);
                break;
        }
        winCondition();
        isGameFinished();
    }
    
    protected boolean isGameFinished()
    {
        for (int j = 0; j < 9; j++)
        {
            if (gui.button[j].isEnabled())
            {
                return false;
            }
        }
        JOptionPane.showMessageDialog(null, "Draw", "Announcement", JOptionPane.PLAIN_MESSAGE);
        clear();
        return true;
    }

    private static final int MAX_DEPTH = 4420;

    private int minimax(int depth, int alpha, int beta, boolean isMaximizing)
    {
        int result = evaluate();
        if (result != 0 || depth == MAX_DEPTH)
        {
            return result;
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int move = 0; move < 9; move++) {
                if (gui.button[move].isEnabled()) {
                    gui.button[move].setText(gui.computerLabel.getText().substring(12));
                    bestScore = Math.max(bestScore, minimax(depth + 1, alpha, beta, !isMaximizing));
                    gui.button[move].setText("");

                    alpha = Math.max(alpha, bestScore);

                    if (beta <= alpha) {
                        break; // Beta cut-off
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int move = 0; move < 9; move++) {
                if (gui.button[move].isEnabled()) {
                    gui.button[move].setText(gui.p1Label.getText().substring(10));
                    bestScore = Math.min(bestScore, minimax(depth + 1, alpha, beta, !isMaximizing));
                    gui.button[move].setText("");

                    beta = Math.min(beta, bestScore);

                    if (beta <= alpha) {
                        break; // Alpha cut-off
                    }
                }
            }
            return bestScore;
        }
    }
    
    private int evaluate()
    {
        for (int i = 0; i < 8; i++)
        {
            int row1 = WINNING_COMBINATIONS[i][0];
            int row2 = WINNING_COMBINATIONS[i][1];
            int row3 = WINNING_COMBINATIONS[i][2];

            String line = gui.button[row1].getText() + gui.button[row2].getText() + gui.button[row3].getText();

            if (line.equals("XXX"))
            {
                return 10;
            } else if (line.equals("OOO"))
            {
                return -10;
            }
        }
        return 0;
    }

    private static final int[][] WINNING_COMBINATIONS =
    {
        {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
        {0, 3, 6}, {1, 4, 7},{2, 5, 8},
        {0, 4, 8}, {6, 4, 2}
    };

}