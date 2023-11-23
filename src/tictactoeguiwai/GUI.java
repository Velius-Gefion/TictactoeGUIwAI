package tictactoeguiwai;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class GUI extends JFrame implements ActionListener
{
    Logic logic = new Logic();
    
    Font buttonFont = new Font(null,Font.PLAIN,50), 
         labelFont = new Font(null,Font.PLAIN,15);
    JPanel panel;
    JLabel p1Label, computerLabel, p1ScoreLabel, computerScoreLabel, turnLabel;
    JToggleButton[] button = new JToggleButton[9];
    boolean[] check = new boolean[9];
    int i, j, k, x, y, p1Score, computerScore;
    
    
    GUI()
    {
        setSize(355,430);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        add(panel = new JPanel());
        panel.setLayout(null);
        
        panel.add(p1Label = new JLabel("Player 1"));
        panel.add(p1ScoreLabel = new JLabel("Score: "));
        p1Label.setFont(labelFont);
        p1ScoreLabel.setFont(labelFont);
        p1Label.setBounds(10,10,100,15);
        p1ScoreLabel.setBounds(230,10,100,15);
        
        panel.add(computerLabel = new JLabel("Computer"));
        panel.add(computerScoreLabel = new JLabel("Score: "));
        computerLabel.setFont(labelFont);
        computerScoreLabel.setFont(labelFont);
        computerLabel.setBounds(10,35,100,15);
        computerScoreLabel.setBounds(230,35,100,15);
        
        panel.add(turnLabel = new JLabel("  Turn"));
        turnLabel.setFont(labelFont);
        turnLabel.setBounds(140,25,100,15);
        
        x = 10; y = 60;
        for(i = 0;i < 9;i++)
        {
            j = i;
            panel.add(button[i] = new JToggleButton());
            button[i].setBounds(x,y,100,100);
            button[i].addActionListener(this);
            
            x += 110;
            if ((j + 1) % 3 == 0)
            {
                x = 10;
                y += 110;
            }
        }
        logic.turn();
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
