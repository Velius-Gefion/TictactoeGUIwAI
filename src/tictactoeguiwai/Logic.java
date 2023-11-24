package tictactoeguiwai;
import java.util.Random;
import javax.swing.JOptionPane;

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
        //TO DO difficulty method
        
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
        firstTurn = !firstTurn;
        String condition = null;
        for (i = 0; i < 9;i++)
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
    
    void aiTurn() {
        
        
        switch(difficulty)
        {
            case "easy":
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
            case "medium":
                break;
                
            case "hard":
                int bestScore = Integer.MIN_VALUE;
                int bestMove = -1;
                
                for (int move = 0; move < 9; move++)
                {
                    if (gui.button[move].isEnabled())
                    {
                        gui.button[move].setText("O");
                        int score = minimax(0, false);
                        gui.button[move].setText("");
                        if (score > bestScore)
                        {
                            bestScore = score;
                            bestMove = move;
                        }
                    }
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

    private int minimax(int depth, boolean isMaximizing)
    {
        int result = evaluate();
        if (result != 0) {
            return result;
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int move = 0; move < 9; move++) {
                if (gui.button[move].isEnabled()) {
                    gui.button[move].setText("O");
                    bestScore = Math.max(bestScore, minimax(depth + 1, !isMaximizing));
                    gui.button[move].setText("");
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int move = 0; move < 9; move++) {
                if (gui.button[move].isEnabled()) {
                    gui.button[move].setText("X");
                    bestScore = Math.min(bestScore, minimax(depth + 1, !isMaximizing));
                    gui.button[move].setText("");
                }
            }
            return bestScore;
        }
    }

    // Evaluate the current state of the game
    private int evaluate() {
        for (i = 0; i < 8; i++) {
            String line = "";
            switch (i) {
                case 0:
                    line = gui.button[0].getText() + gui.button[1].getText() + gui.button[2].getText();
                    break;
                case 1:
                    line = gui.button[3].getText() + gui.button[4].getText() + gui.button[5].getText();
                    break;
                case 2:
                    line = gui.button[6].getText() + gui.button[7].getText() + gui.button[8].getText();
                    break;
                case 3:
                    line = gui.button[0].getText() + gui.button[3].getText() + gui.button[6].getText();
                    break;
                case 4:
                    line = gui.button[1].getText() + gui.button[4].getText() + gui.button[7].getText();
                    break;
                case 5:
                    line = gui.button[2].getText() + gui.button[5].getText() + gui.button[8].getText();
                    break;
                case 6:
                    line = gui.button[0].getText() + gui.button[4].getText() + gui.button[8].getText();
                    break;
                case 7:
                    line = gui.button[6].getText() + gui.button[4].getText() + gui.button[2].getText();
                    break;
            }
            if (line.equals("XXX")) {
                return 10;
            } else if (line.equals("OOO")) {
                return -10;
            }
        }
        return 0; // No winner yet
    }
}