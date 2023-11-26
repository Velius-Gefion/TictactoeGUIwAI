package tictactoeguiwai;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

public class Logic
{
    GUI gui;
    Random rnd = new Random();
    
    protected char [][] board = new char[3][3];
    
    boolean firstTurn, playerCheck, computerCheck, medium;
    int k;
    String difficulty;
    
    public Logic(GUI gui)
    {
        this.gui = gui;
    }
    
    void turn()
    {
        for(int i = 0;i < 3;i++)
        {
            for(int j = 0;j < 3 ;j++)
            {
                board[i][j] = ' ';
                gui.button[i][j].setText("");
                gui.button[i][j].setSelected(false);
                gui.button[i][j].setEnabled(true);
            }
        }
        
        String[] difficultyOptions = {"Easy", "Medium","Hard"};

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
        gui.playerLabel.setText("Player is " + (firstTurn ? "X" : "O"));
        gui.computerLabel.setText("Computer is " + (firstTurn ? "O" : "X"));

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
        String condition = "";
        boolean check [] = new boolean [9];
        for (int i = 0; i < 8;i++)
        {
            switch(i)
            {
                case 0:
                    condition = gui.button[0][0].getText() + gui.button[0][1].getText() + gui.button[0][2].getText();
                    break;
                case 1:
                    condition = gui.button[1][0].getText() + gui.button[1][1].getText() + gui.button[1][2].getText();
                    break;
                case 2:
                    condition = gui.button[2][0].getText() + gui.button[2][1].getText() + gui.button[2][2].getText();
                    break;
                case 3:
                    condition = gui.button[0][0].getText() + gui.button[1][0].getText() + gui.button[2][0].getText();
                    break;
                case 4:
                    condition = gui.button[0][1].getText() + gui.button[1][1].getText() + gui.button[2][1].getText();
                    break;
                case 5:
                    condition = gui.button[0][2].getText() + gui.button[1][2].getText() + gui.button[2][2].getText();
                    break;
                case 6:
                    condition = gui.button[0][0].getText() + gui.button[1][1].getText() + gui.button[2][2].getText();
                    break;
                case 7:
                    condition = gui.button[2][0].getText() + gui.button[1][1].getText() + gui.button[0][2].getText();
                    break;
            }
            
            if(condition.equals("XXX"))
            {
                playerCheck = gui.playerLabel.getText().contains("X");
                computerCheck = gui.computerLabel.getText().contains("X");
                score(); turn();
                break;
            }
            else if (condition.equals("OOO"))
            {
                playerCheck = gui.playerLabel.getText().contains("O");
                computerCheck = gui.computerLabel.getText().contains("O");
                score(); turn();
                break;
            }
        }   
        for(int i = 0;i < 3; i++)
        {
            for(int j = 0;j < 3;j++)
            {
                check[j] = gui.button[i][j].getText().matches("");
                if(check[j] == false)
                {  
                    k += 1;
                    if (k == 9)
                    {
                        JOptionPane.showMessageDialog(null, "Draw", "Announcement", JOptionPane.PLAIN_MESSAGE);
                        turn();
                    }
                }    
            }
        }
        k = 0;
    }

    void score()
    {
        if (playerCheck == true && computerCheck == false)
        {
            JOptionPane.showMessageDialog(null, gui.playerLabel.getText().substring(0,6) + " won",
                    "Announcement", JOptionPane.PLAIN_MESSAGE);
            gui.playerScore += 1;
            gui.playerScoreLabel.setText("Score: " + gui.playerScore);
        }
        else
        {
            JOptionPane.showMessageDialog(null, gui.computerLabel.getText().substring(0,8) + " won", 
                    "Announcement", JOptionPane.PLAIN_MESSAGE);
            gui.computerScore += 1;
            gui.computerScoreLabel.setText("Score: " + gui.computerScore);
        }
    }
    
    void aiTurn()
    {
        switch(difficulty)
        {
            case "Easy":
                easyAI();
                break;
            case "Medium":
                medium = rnd.nextBoolean();
                if (medium == true)
                {
                    easyAI();
                }
                else
                {
                    hardAI();
                }
                break;
            case "Hard":
                hardAI();
                break;
        }
        winCondition();
    }
    
    void easyAI()
    {
        int emptyButtonRow, emptyButtonCol;
        do
        {
            emptyButtonRow = rnd.nextInt(3);
            emptyButtonCol = rnd.nextInt(3);
        }
        while (!gui.button[emptyButtonRow][emptyButtonCol].getText().isEmpty());
        gui.button[emptyButtonRow][emptyButtonCol].setFont(gui.buttonFont);
        gui.button[emptyButtonRow][emptyButtonCol].setText(gui.computerLabel.getText().substring(12));
        board[emptyButtonRow][emptyButtonCol] = gui.computerLabel.getText().charAt(12);
        gui.turnLabel.setText(gui.playerLabel.getText().substring(10) + "'s Turn");
        gui.button[emptyButtonRow][emptyButtonCol].setEnabled(false);
    }
    
    void hardAI()
    {
        int[] bestMove = minimax(board, gui.computerLabel.getText().charAt(12));
        
        gui.button[bestMove[0]][bestMove[1]].setFont(gui.buttonFont);
        gui.button[bestMove[0]][bestMove[1]].setText(gui.computerLabel.getText().substring(12));
        board[bestMove[0]][bestMove[1]] = gui.computerLabel.getText().charAt(12);
        gui.turnLabel.setText(gui.playerLabel.getText().substring(10) + "'s Turn");
        gui.button[bestMove[0]][bestMove[1]].setEnabled(false);
    }
    
    protected boolean isGameFinished()
    {
        for (int i = 0; i < 3; i++)
        {
            for(int j = 0; j < 3;j++)
            {
                if (board[i][j] == ' ')
                {
                    return false;
                }
            }
        }
        return true;
    }

    private boolean hasContestantWon(char symbol) {
        for (int i = 0; i < 3; i++) {
            if ((board[i][0] == symbol && board[i][1] == symbol && board[i][2] == symbol) ||
                    (board[0][i] == symbol && board[1][i] == symbol && board[2][i] == symbol)) {
                return true;
            }
        }

        return (board[0][0] == symbol && board[1][1] == symbol && board[2][2] == symbol) ||
                (board[0][2] == symbol && board[1][1] == symbol && board[2][0] == symbol);
    }

    private boolean isBoardFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    return false;
                }
            }
        }
        return true;
    }

    private int[] minimax(char[][] board, char player) {
        int[] result = new int[]{-1, -1};
        int bestScore = (player == gui.computerLabel.getText().charAt(12)) ? Integer.MIN_VALUE : Integer.MAX_VALUE;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == ' ') {
                    board[i][j] = player;
                    int score = minimaxHelper(board, 0, false);
                    board[i][j] = ' ';

                    if ((player == gui.computerLabel.getText().charAt(12) && score > bestScore) || (player == gui.playerLabel.getText().charAt(10) && score < bestScore)) {
                        bestScore = score;
                        result[0] = i;
                        result[1] = j;
                    }
                }
            }
        }

        return result;
    }

    private int minimaxHelper(char[][] board, int depth, boolean isMaximizing) {
        if (hasContestantWon(gui.playerLabel.getText().charAt(10))) {
            return -1;
        } else if (hasContestantWon(gui.computerLabel.getText().charAt(12))) {
            return 1;
        } else if (isBoardFull()) {
            return 0;
        }

        if (isMaximizing) {
            int bestScore = Integer.MIN_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = gui.computerLabel.getText().charAt(12);
                        bestScore = Math.max(bestScore, minimaxHelper(board, depth + 1, false));
                        board[i][j] = ' ';
                    }
                }
            }
            return bestScore;
        } else {
            int bestScore = Integer.MAX_VALUE;
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (board[i][j] == ' ') {
                        board[i][j] = gui.playerLabel.getText().charAt(10);
                        bestScore = Math.min(bestScore, minimaxHelper(board, depth + 1, true));
                        board[i][j] = ' ';
                    }
                }
            }
            return bestScore;
        }
    }
}