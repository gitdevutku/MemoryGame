import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;

public class MemoryGame extends JFrame implements ActionListener {
    private int BOARD_SIZE = 4;
    private ArrayList<ImageIcon> cardIcons = new ArrayList<>();
    private ArrayList<Point> flippedCards = new ArrayList<>();
    private int attempts = 0;
    private JButton[][] buttons = new JButton[BOARD_SIZE][BOARD_SIZE];
    private JButton resetButton = new JButton("Reset");
    private Container BOARD = new Container();
    private JPanel UPPER_MENU = new JPanel();
    private JFrame frame = new JFrame("Memory Game");
    private Icon uni_logo = new ImageIcon("src/abu-logo-tr.png");
    private int score = 0;
    private JLabel Score = new JLabel("Score: "+score);

    public MemoryGame() {
        frame.setLayout(new BorderLayout());
        BOARD.setLayout(new GridLayout(BOARD_SIZE, BOARD_SIZE));
        frame.add(BOARD, BorderLayout.CENTER);
        frame.add(UPPER_MENU, BorderLayout.NORTH);
        UPPER_MENU.setLayout(new GridBagLayout());
        UPPER_MENU.add(new JLabel(uni_logo));
        UPPER_MENU.add(new JLabel("Attempts: " + attempts + "/15"));
        UPPER_MENU.add(resetButton);
        UPPER_MENU.add(Score);
        resetButton.addActionListener(this);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setSize(700, 700);
        for (int i = 1; i <= 8; i++) {
            cardIcons.add(new ImageIcon("src/images" + i + ".png"));
            cardIcons.add(new ImageIcon("src/images" + i + ".png"));
        }
        Collections.shuffle(cardIcons);
        for (int i = 0; i < buttons.length; i++) {
            for (int j = 0; j < buttons[i].length; j++) {
                JButton button = new JButton();
                button.addActionListener(this);
                button.setIcon(uni_logo);
                button.setBackground(Color.WHITE);
                buttons[i][j] = button;
                BOARD.add(button);
            }
        }
        frame.setVisible(true);
    }
    public void flipCard(JButton button, int row, int col) {
        button.setIcon(cardIcons.get(row * BOARD_SIZE + col));
        flippedCards.add(new Point(row, col));
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getActionCommand().equals("Reset")) {
            frame.dispose();
            new MemoryGame();
        }
        if (attempts == 15) {
            JOptionPane.showMessageDialog(null, "You lost the game");
            frame.dispose();
            new MemoryGame();
        }
        if (score == 8) {
            JOptionPane.showMessageDialog(null, "You won the game in " + attempts + " attempts");
            frame.dispose();
            new MemoryGame();
        }
        if (e.getSource() instanceof JButton) {
            JButton button = (JButton) e.getSource();
            int row = -1;
            int col = -1;
            for (int i = 0; i < buttons.length; i++) {
                for (int j = 0; j < buttons[i].length; j++) {
                    if (buttons[i][j] == button) {
                        row = i;
                        col = j;
                        break;
                    }
                }
            }
            if (flippedCards.size() == 1 && flippedCards.get(0).equals(new Point(row, col))) {
                return;
            }
            flipCard(button, row, col);
            if (flippedCards.size() == 2) {
                attempts++;
                // Check if the two flipped cards match
                if (cardIcons.get(flippedCards.get(0).x * BOARD_SIZE + flippedCards.get(0).y).getImage() ==
                        cardIcons.get(flippedCards.get(1).x * BOARD_SIZE + flippedCards.get(1).y).getImage()) {
                    // If cards match, disable the buttons and clear flipped cards list
                    buttons[flippedCards.get(0).x][flippedCards.get(0).y].setEnabled(false);
                    buttons[flippedCards.get(1).x][flippedCards.get(1).y].setEnabled(false);
                    score++;
                    flippedCards.clear();
                } else {
                    // If cards don't match, use a Timer to delay flipping the cards back
                    // to their original state and clear flipped cards list
                    Timer timer = new Timer(1000, new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            buttons[flippedCards.get(0).x][flippedCards.get(0).y].setIcon(uni_logo);
                            buttons[flippedCards.get(1).x][flippedCards.get(1).y].setIcon(uni_logo);
                            flippedCards.clear();
                        }
                    });
                    timer.setRepeats(false);
                    timer.start();
                }
                // Update the number of attempts
                updateLabels();
            }

        }
    }
    private void updateLabels() {
        JLabel attemptsLabel = (JLabel) UPPER_MENU.getComponent(1);
        attemptsLabel.setText("Attempts: " + attempts + "/15");
        JLabel scoreLabel = (JLabel) UPPER_MENU.getComponent(3);
        scoreLabel.setText("Score: " + score);
    }
    public static void main(String[] args) {
        new MemoryGame();
    }
}
