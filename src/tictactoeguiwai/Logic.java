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
    
    void turn()
    {
        firstTurn = rnd.nextBoolean();
        if(firstTurn == true)
        {
            gui.p1Label.setText("Player 1 is X");
            gui.computerLabel.setText("Computer is O");
        }
        else
        {
            gui.p1Label.setText("Player 1 is O");
            gui.computerLabel.setText("Computer is X");
        }
        
        firstTurn = rnd.nextBoolean();
        if(firstTurn == true)
        {
            gui.turnLabel.setText("X's Turn");
        }
        else
        {
            gui.turnLabel.setText("O's Turn");
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
            JOptionPane.showMessageDialog(null, gui.p1Label.getText().substring(0,8) + " won",
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
}