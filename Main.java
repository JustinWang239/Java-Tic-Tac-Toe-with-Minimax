// Tic Tac Toe implemented with a GUI as the board. The user plays
// against a computer AI using Minimax algorithm. Turn order is
// randomly selected.

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Main{

    public static void main(String[] args) {
        TicTacToe();
    }

    public static void TicTacToe() {
        JFrame frame = new JFrame();
        frame.setSize(500, 500);
        JPanel text_panel = new JPanel();
        JPanel buttons = new JPanel();
        JPanel restart = new JPanel();
        JLabel text = new JLabel("Tic Tac Toe");
        JButton restartbtn = new JButton("Restart");

        GridLayout button_layout = new GridLayout(3, 3);
        buttons.setLayout(button_layout);

        boolean Com_First_Turn = Turn_Order_X();

        JButton[] button = new JButton[9];
        for (int i = 0; i < 9; i++) {
            button[i] = new JButton("");
            button[i].addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    for (int i = 0; i < 9; i++) {
                        if (button[i] == e.getSource()) {
                            if (button[i].getText().equals("")) {
                                if (Com_First_Turn) {
                                    button[i].setText("O");
                                } else {
                                    button[i].setText("X");
                                }
                            }
                        }
                    }
                    for (int i = 0; i < 3; i++) {
                        if (button[3 * i].getText().equals(button[(3 * i) + 1].getText()) &&
                                button[(3 * i) + 1].getText().equals(button[(3 * i) + 2].getText())) {
                            if (!button[3 * i].getText().equals("")) {
                                System.out.println("X Wins");
                            }
                        }
                    }

                    if (!Game_End(button).equals("")) {
                        if (Game_End(button).equals("tie")) {
                            text.setText("The game has ended in a tie");
                        } else {
                            text.setText(Game_End(button) + " has won the game");
                        }
                    } else {
                        int move = Com_Move(button, Com_First_Turn);
                        if (Com_First_Turn) {
                            button[move].setText("X");
                        } else {
                            button[move].setText("O");
                        }
                        System.out.println(Game_End(button));
                        if (!Game_End(button).equals("")) {
                            if (Game_End(button).equals("tie")) {
                                text.setText("The game has ended in a tie");
                            } else {
                                text.setText(Game_End(button) + " has won the game");
                            }
                        }
                    }
                }
            });
            buttons.add(button[i]);
        }

        text_panel.add(text);
        restart.add(restartbtn);
        frame.add(text_panel, BorderLayout.NORTH);
        frame.add(buttons);
        frame.add(restart, BorderLayout.SOUTH);
        frame.setVisible(true);

        //First Move
        if (Com_First_Turn) {
            int random_pos = (int) (Math.random() * 9);
            button[random_pos].setText("X");
        }

        //Restart
        restartbtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (restartbtn == e.getSource()) {
                    frame.dispose();
                    TicTacToe();
                }
            }
        });
    }

    //Decides Turn Order
    public static boolean Turn_Order_X() {
        int random_num = (int) (Math.random() * 2 + 1);
        if (random_num == 1) {
            return false;
        } else {
            return true;
        }
    }

    //Check For Full Board
    public static boolean Board_Full(JButton[] button) {
        for (int i = 0; i < 9; i++) {
            if (button[i].getText().equals("")) {
                return false;
            }
        }
        return true;
    }

    //Check If Game Over
    public static String Game_End(JButton[] button) {
        String board_state = "";
        for (int i = 0; i < 3; i++) {
            if (button[3 * i].getText().equals(button[(3 * i) + 1].getText()) &&
                    button[(3 * i) + 1].getText().equals(button[(3 * i) + 2].getText()) &&
                    !button[3 * i].getText().equals("")) {
                board_state = button[3 * i].getText();
                return board_state;
            }
        }
        for (int i = 0; i < 3; i++) {
            if (button[i].getText().equals(button[i + 3].getText()) &&
                    button[i + 3].getText().equals(button[i + 6].getText()) &&
                    !button[i + 3].getText().equals("")) {
                board_state = button[i].getText();
                return board_state;
            }
        }
        if (button[0].getText().equals(button[4].getText()) &&
                button[4].getText().equals(button[8].getText()) &&
                !button[0].getText().equals("")) {
            board_state = button[0].getText();
            return board_state;
        }
        if (button[2].getText().equals(button[4].getText()) &&
                button[4].getText().equals(button[6].getText()) &&
                !button[2].getText().equals("")) {
            board_state = button[2].getText();
            return board_state;
        }
        if (Board_Full(button)) {
            board_state = "tie";
        }
        return board_state;
    }

    //MiniMax Algorithm
    public static int MinMax(JButton[] button, Boolean max, Boolean Com_First_Turn) {
        int max_score = 0;
        if (Game_End(button).equals("")) {
            if (max) {
                max_score = -2;
                for (int i = 0; i < 9; i++) {
                    if (button[i].getText().equals("")) {
                        if (Com_First_Turn) {
                            button[i].setText("X");
                        } else {
                            button[i].setText("O");
                        }
                        int score = MinMax(button, false, Com_First_Turn);
                        max_score = Math.max(max_score, score);
                        button[i].setText("");
                    }
                }
            } else {
                max_score = 2;
                for (int i = 0; i < 9; i++) {
                    if (button[i].getText().equals("")) {
                        if (Com_First_Turn) {
                            button[i].setText("O");
                        } else {
                            button[i].setText("X");
                        }
                        int score = MinMax(button, true, Com_First_Turn);
                        max_score = Math.min(max_score, score);
                        button[i].setText("");
                    }
                }
            }
        } else if (Game_End(button).equals("tie")) {
            max_score = 0;
        } else if (Game_End(button).equals("X")) {
            if (Com_First_Turn) {
                max_score = 1;
            } else {
                max_score = -1;
            }
        } else if (Game_End(button).equals("O")) {
            if (Com_First_Turn) {
                max_score = -1;
            } else {
                max_score = 1;
            }
        }
        return max_score;
    }

    //Best Move For Computer
    public static int Com_Move(JButton[] button, Boolean Com_First_Turn) {
        int max_score = -2;
        int move = 0;
        for (int i = 0; i < 9; i++) {
            if (button[i].getText().equals("")) {
                if (Com_First_Turn) {
                    button[i].setText("X");
                    int score = MinMax(button, false, true);
                    if (score > max_score) {
                        max_score = score;
                        move = i;
                    }
                } else {
                    button[i].setText("O");
                    int score = MinMax(button, false, false);
                    if (score > max_score) {
                        max_score = score;
                        move = i;
                    }
                }
                button[i].setText("");
            }
        }
        return move;
    }
}

