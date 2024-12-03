package client.controller;

import client.model.Board;
import client.util.Constants;
import static client.util.Constants.Configs.TURN_TIMEOUT;
import client.views.GameView;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class GameHandler implements Runnable{

    private Board ownBoard;
    private Board opponentBoard;
    private GameView view;

    private ObjectOutputStream out;
    private ObjectInputStream in;

    private String opponentName = "Player";

    public GameHandler(
            GameView gameView,
            Board ownBoard,
            Board opponentBoard,
            ObjectOutputStream out,
            ObjectInputStream in
    ) {
        this.ownBoard = ownBoard;
        this.opponentBoard = opponentBoard;
        this.view = gameView;

        ownBoard.setClient(this);
        opponentBoard.setClient(this);

        this.out = out;
        this.in = in;
    }

    @Override
    public void run() {
        Object input;
        try {
            while ((input = in.readObject()) != null) {
                parseInput(input);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void parseInput(Object input) {
        if (input instanceof NotificationMessage) {
            NotificationMessage n = (NotificationMessage) input;

            int code = n.getCode();

            if (code != Constants.NotificationCode.OPPONENTS_NAME) {
                System.out.println("<< " + n.getCode());
            }

            switch (n.getCode()) {
                case Constants.NotificationCode.OPPONENTS_NAME:
                    if (n.getText().length == 1) {
                        opponentName = n.getText()[0];
                        view.setTitle("Trận đấu cùng đối thủ: " +
                                opponentName);
                        System.out.println("<< " + n.getCode() + " " + opponentName);
                    }
                    break;
                case Constants.NotificationCode.BOARD_ACCEPTED:
                    view.setMessage("Xếp tàu xong, chờ đối thủ...");
                    view.stopTimer();
                    ownBoard.setBoatPositionLocked(true);
                    break;

                case Constants.NotificationCode.SHOT:
                    break;

                case Constants.NotificationCode.GAME_TOKEN:
                    // TODO: handle receiving game token to share with friend
                    view.addChatMessage("Received game token.");
                    break;
                case Constants.NotificationCode.GAME_NOT_FOUND:
                    // TODO: handle joining a game that doesn't exist
                    view.addChatMessage("Game not found.");
                    break;
                case Constants.NotificationCode.PLACE_SHIPS:
                    view.addChatMessage("Can place ships now.");
                    ownBoard.setBoatPositionLocked(false);
                    break;
                case Constants.NotificationCode.YOUR_TURN:
                    view.stopTimer();
                    view.setTimer(TURN_TIMEOUT / 1000);
                    view.setMessage("Lượt của bạn.");
                    break;
                case Constants.NotificationCode.OPPONENTS_TURN:
                    view.stopTimer();
                    view.setTimer(TURN_TIMEOUT / 1000);
                    view.addChatMessage("Lượt của đối thủ.");
                    view.setMessage("Lượt của đối thủ.");
                    break;
                case Constants.NotificationCode.GAME_WIN:
                    // TODO: inform player they have won the game
                    view.setMessage("Bạn Thắng.");
                    view.stopTimer();
                    view.gameOverAction("Bạn Thắng!");
                    break;
                case Constants.NotificationCode.GAME_LOSE:
                    // TODO: inform player they have lost the game
                    view.setMessage("Bạn thua.");
                    view.stopTimer();
                    view.gameOverAction("Bạn thua!");
                    break;
                case Constants.NotificationCode.TIMEOUT_WIN:
                    // TODO: inform of win due to opponent taking too long
                    view.addChatMessage("Đối thủ của bạn hết giờ, bạn thắng!");
                    view.gameOverAction("Đối thủ của bạn hết giờ, bạn thắng!");
                    break;
                case Constants.NotificationCode.TIMEOUT_LOSE:
                    // TODO: inform of loss due to taking too long
                    view.addChatMessage("Bạn hết giờ, bạn thua!");
                    view.gameOverAction("Bạn hết giờ, bạn thua!");
                    break;
                case Constants.NotificationCode.TIMEOUT_DRAW:
                    // TODO: inform that both took too long to place ships
                    view.addChatMessage("Game hòa.");
                    view.gameOverAction("Game hòa.");
                    break;
                case Constants.NotificationCode.NOT_YOUR_TURN:
                    view.addChatMessage("Không phải lượt của bạn!");
                    break;
                case Constants.NotificationCode.INVALID_BOARD:
                    view.addChatMessage("Board không hợp lệ.");
                    break;
                case Constants.NotificationCode.NOT_IN_GAME:
                    view.addChatMessage("Bạn không trong một trò chơi.");
                    break;
                case Constants.NotificationCode.INVALID_MOVE:
                    view.addChatMessage("Nước đi không hợp lệ.");
                    break;
                case Constants.NotificationCode.REPEATED_MOVE:
                    view.addChatMessage("Bạn không thể lặp lại một nước đi\n.");
                    break;
                case Constants.NotificationCode.OPPONENT_DISCONNECTED:
                    view.addChatMessage("Đối thủ mất kết nối.");
            }
        } else if (input instanceof MoveResponseMessage) {
            MoveResponseMessage move = (MoveResponseMessage) input;
            if (move.isOwnBoard()) {
                int x = move.getX();
                int y = move.getY();
                System.out.println(Constants.NotificationCode.SHOT + " " + x + " " + y);
                ownBoard.applyMove(move);
            } else {
                opponentBoard.applyMove(move);
            }
        } else if (input instanceof ChatMessage) {
            ChatMessage chatMessage = ( ChatMessage) input;
            view.addChatMessage("<b>" + opponentName + ":</b> " + chatMessage.getMessage());
        }
    }

    //gửi board đến server
    public void sendBoard(Board board) throws IOException {
        System.out.println(">> " + Constants.NotificationCode.SEND_BOARD);
        board.printBoard(true);
        out.reset();
        out.writeObject(board);
        out.flush();
    }

    public GameView getView() {
        return view;
    }

    public void sendChatMessage(String message) throws IOException {
        System.out.println(message);
        out.writeObject(new ChatMessage(message));
        out.flush();
    }

    //gửi nước đi thực hiện trên board đối thủ
    public void sendMove(int x, int y) throws IOException {
        System.out.println(">> " + Constants.NotificationCode.SHOT + " " + x + " " + y);
        out.writeObject(new MoveMessage(x, y));
        out.flush();
    }

    public String getOpponentName() {
        return opponentName;
    }

}
