package client.views;

import client.controller.SocketHandle;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.Timer;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import client.controller.Client;
import client.controller.GameHandler;
import static client.util.Constants.Configs.PLACEMENT_TIMEOUT;
import javax.swing.ImageIcon;

/**
 *
 * @author bhhoa
 */
public class GameView extends JFrame {

    private JTextField inputField = new JTextField();
    private JButton rotateButton = new JButton("Xoay tàu");
    private JButton saveShipState = new JButton("Sẵn sàng");
    private JScrollPane chatScrollPane;
    private JList<String> chat = new JList<>();
    private DefaultListModel<String> chatModel = new DefaultListModel<>();
    private GameHandler model;
    private JLabel timerView;
    private JLabel message;
    private Timer timer;

    public GameView(ObjectOutputStream out, final ObjectInputStream in, final SocketHandle socketHandle) {
        chat.setModel(chatModel);

        this.setIconImage(new ImageIcon("assets/images/SpaceShip.png").getImage());
        JPanel rootPanel = new JPanel(new BorderLayout(5, 5));
        rootPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

        final BoardView myBoard = new BoardView(true);
        final BoardView enemyBoard = new BoardView(false);

        model = new GameHandler(this, myBoard.getModel(), enemyBoard.getModel(), out, in);

        JPanel controlPanel = new JPanel(new BorderLayout(10, 5));
        chatScrollPane = new JScrollPane(chat);

        controlPanel.add(chatScrollPane, BorderLayout.CENTER);

        controlPanel.setPreferredSize(new Dimension(180, 150));

        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        rotateButton.setEnabled(false);
        rotateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ShipView shipView = myBoard.getSelectedShip();
                if (shipView != null) {
                    myBoard.getModel().selectedShipRotated();
                }
            }
        });

        saveShipState.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    model.sendBoard(myBoard.getModel());
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
        });

        saveShipState.setEnabled(false);
        buttons.add(saveShipState);
        buttons.add(rotateButton);

        JPanel bottomPanel = new JPanel(new GridLayout(1, 0));

        message = new JLabel("");
        message.setHorizontalAlignment(JLabel.CENTER);
        message.setVerticalAlignment(JLabel.CENTER);
        message.setFont(new Font("SansSerif", Font.PLAIN, 14));
        timerView = new JLabel(Integer.toString(PLACEMENT_TIMEOUT / 1000));
        timerView.setHorizontalAlignment(JLabel.CENTER);
        timerView.setVerticalAlignment(JLabel.CENTER);
        timerView.setFont(new Font("SansSerif", Font.BOLD, 16));

        bottomPanel.add(message);
        bottomPanel.add(buttons);
        bottomPanel.add(timerView);

        JPanel boards = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));

        boards.add(new LabeledBoardView(myBoard));
        boards.add(new LabeledBoardView(enemyBoard));

        JPanel gamePanel = new JPanel(new BorderLayout(10, 10));

        gamePanel.add(boards, BorderLayout.CENTER);
        gamePanel.add(bottomPanel, BorderLayout.SOUTH);

        rootPanel.add(gamePanel, BorderLayout.CENTER);
        rootPanel.add(controlPanel, BorderLayout.EAST);
        setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

        setContentPane(rootPanel);

        pack();
        setMinimumSize(getSize());
        setVisible(true);

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                exitGame();
            }
        });
        
        setTimer(PLACEMENT_TIMEOUT / 1000);
    }
    
    public void exitGame() {
        try {
            stopTimer();
            Client.socketHandle.write("left-room,");
            Client.closeAllViews();
            Client.socketHandle.reopen();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(rootPane, ex.getMessage());
        }
        Client.closeAllViews();
        Client.openView(Client.View.HOMEPAGE);
    }
    
    public void stopTimer() {
        if (timer != null) {
            timer.stop();
        }
        timerView.setText("");
    }

    public void setMessage(String s) {
        message.setText(s);
    }

    public void setTimer(final int seconds) {
        timer = new Timer(1000, null);
        timer.addActionListener(new ActionListener() {

            private int secondsLeft = seconds;

            @Override
            public void actionPerformed(ActionEvent e) {
                --secondsLeft;
                timerView.setText(Integer.toString(secondsLeft));
                if (secondsLeft == 0) {
                    timer.stop();
                }
            }
        });
        timer.start();
    }

    public void sendChatMessage() {
        try {
            String text = inputField.getText();
            model.sendChatMessage(text);
            addChatMessage("<b>" + Client.user.getNickname() + ":</b> " + text);
            inputField.setText("");
        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    public void addChatMessage(String text) {
        JScrollBar bar = chatScrollPane.getVerticalScrollBar();
        chatModel.addElement("<html>" + text + "</html>" + "\n");
        bar.setValue(bar.getMaximum());
    }

    public void setSendShipState(boolean state) {
        saveShipState.setEnabled(state);
    }

    public void setRotateButtonState(boolean state) {
        rotateButton.setEnabled(state);
    }

    public GameHandler getModel() {
        return this.model;
    }

    public void gameOverAction(String result) {
        Object[] options = {"Quay về trang Home", "Thoát"};
        int n = JOptionPane.showOptionDialog(this,
                result + " Bạn muốn làm gì?", "Trận đấu đã kết thúc ",
                JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                options, options[0]);
        switch(n) {
            case 0:
                exitGame();
                break;
            case 1:
                System.exit(0);
        }
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(rootPane, message);
    }
}
