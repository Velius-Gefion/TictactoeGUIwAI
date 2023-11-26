package tictactoeguiwai;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class GUI extends JPanel implements ActionListener
{
    TictactoeGUIwAI tictactoe;
    
    Logic logic = new Logic(this);
    
    Font buttonFont = new Font(null,Font.PLAIN,50), 
         labelFont = new Font(null,Font.PLAIN,15);
    JLabel playerLabel, computerLabel, playerScoreLabel, computerScoreLabel, turnLabel;
    JToggleButton[][] button = new JToggleButton[3][3];
    int i, j, x, y, playerScore, computerScore;
    
    GUI(TictactoeGUIwAI tictactoe)
    {
        this.tictactoe = tictactoe;
        
        setLayout(null);
        add(playerLabel = new JLabel("Player 1"));
        add(playerScoreLabel = new JLabel("Score: "));
        playerLabel.setFont(labelFont);
        playerScoreLabel.setFont(labelFont);
        playerLabel.setBounds(10,10,100,15);
        playerScoreLabel.setBounds(230,10,100,15);
        
        add(computerLabel = new JLabel("Computer"));
        add(computerScoreLabel = new JLabel("Score: "));
        computerLabel.setFont(labelFont);
        computerScoreLabel.setFont(labelFont);
        computerLabel.setBounds(10,35,100,15);
        computerScoreLabel.setBounds(230,35,100,15);
        
        add(turnLabel = new JLabel("  Turn"));
        turnLabel.setFont(labelFont);
        turnLabel.setBounds(140,25,100,15);
        
        x = 10; y = 60;
        for(i = 0;i < 3;i++)
        {
            for(j = 0; j < 3;j++)
            {
                add(button[i][j] = new JToggleButton());
                button[i][j].setBounds(x,y,100,100);
                button[i][j].addActionListener(this);
                
                x += 110;
            }
            x = 10;
            y += 110;
        }
        logic.turn();
    }
    
    
    
    @Override
    public void actionPerformed(ActionEvent e)
    {   
        for (i = 0; i < 3; i++)
        {
            for (j = 0;j < 3;j++)
            {   
                if (e.getSource() == button[i][j])
                {
                    button[i][j].setFont(buttonFont);
                    button[i][j].setText(playerLabel.getText().substring(10));
                    logic.board[i][j] = playerLabel.getText().charAt(10);
                    turnLabel.setText(computerLabel.getText().substring(12) + "'s Turn");
                    button[i][j].setEnabled(false);
                    logic.winCondition();
                    logic.aiTurn();
                    break;
                }
            }
        }
    }
}
