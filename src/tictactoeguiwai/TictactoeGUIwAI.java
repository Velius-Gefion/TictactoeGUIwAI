package tictactoeguiwai;
import javax.swing.JFrame;

public class TictactoeGUIwAI extends JFrame
{
    private GUI gui;
    
    void showGUIElements()
    {
        getContentPane().removeAll();
        getContentPane().add(gui);
        setSize(355, 430);
        setLocationRelativeTo(null);
        revalidate();
        repaint();
    }
    
    TictactoeGUIwAI()
    {
        setSize(355,430);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        
        gui = new GUI(this);
        
        showGUIElements();
        
        setVisible(true);
    }
    
    public static void main(String[] args)
    {
        TictactoeGUIwAI app = new TictactoeGUIwAI();
    }
}
