package tictactoeguiwai;
import java.util.Random;
import javax.swing.JOptionPane;

public class Logic
{
    GUI gui;
    Random rnd = new Random();
    
    boolean firstTurn, p1Check, computerCheck;
    int i, j, k;

    public Logic(GUI gui) {
        this.gui = gui;
    }
    
    void turn() {
        firstTurn = rnd.nextBoolean();
        if (firstTurn == true) {
            gui.p1Label.setText("Player is X");
            gui.computerLabel.setText("Computer is O");
        } else {
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
        for (i = 0; i < 10;i++)
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
        int emptyButtonIndex;
        do {
            emptyButtonIndex = rnd.nextInt(9);
        } while (!gui.button[emptyButtonIndex].getText().isEmpty());

        gui.button[emptyButtonIndex].setFont(gui.buttonFont);
        gui.button[emptyButtonIndex].setText(gui.computerLabel.getText().substring(12));
        gui.turnLabel.setText(gui.p1Label.getText().substring(10) + "'s Turn");
        gui.button[emptyButtonIndex].setEnabled(false);
        winCondition();
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
    
    protected void computerMove()
    {
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

        gui.button[bestMove].setText("O");
        gui.button[bestMove].setFont(gui.buttonFont);
        gui.button[bestMove].setEnabled(false);
        gui.turnLabel.setText("X's Turn");
    }

    private int minimax(int i, boolean b)
    {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}