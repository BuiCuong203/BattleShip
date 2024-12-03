package server.controller;

import client.controller.MoveMessage;
import client.controller.MoveResponseMessage;
import client.controller.NotificationMessage;
import client.model.Ship;
import client.model.Square;
import static client.util.Constants.Configs.*;
import static client.util.Constants.NotificationCode.*;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Game {

    private ServerThread player1;
    private ServerThread player2;
    private ServerThread turn;

    private Timer placementTimer;
    private Timer turnTimer;

    private boolean gameStarted;

    //Khởi tạo 1 game, thông báo cho player tên đối phương, bộ đếm thời gian bắt đầu
    public Game(ServerThread player1, ServerThread player2) {
        this.player1 = player1;
        this.player2 = player2;
        player1.setGame(this);
        player2.setGame(this);
        player1.writeNotification(OPPONENTS_NAME, player2.getPlayerName());
        player2.writeNotification(OPPONENTS_NAME, player1.getPlayerName());
        NotificationMessage placeShipsMessage = new NotificationMessage(PLACE_SHIPS);

        player1.writeObject(placeShipsMessage);
        player2.writeObject(placeShipsMessage);

        placementTimer = new Timer();
        placementTimer.schedule(new PlacementTimerTask(), PLACEMENT_TIMEOUT);
    }

    //get người chơi còn lại, không phải player đang được chỉ định
    public ServerThread getOpponent(ServerThread self) {
        if (player1 == self) {
            return player2;
        }
        return player1;
    }

    //set game cho cả 2 player về null
    public void killGame() {
        player1.setGame(null);
        player2.setGame(null);
    }

    // Set turn cho player được truyền vào
    // Set 1 new turnTime
    // Gửi thông báo tương ứng cho 2 bên
    public synchronized void setTurn(ServerThread player) {
        turn = player;
        if (turnTimer != null) {
            turnTimer.cancel();
        }
        turnTimer = new Timer();
        turnTimer.schedule(new TurnTimerTask(), TURN_TIMEOUT);
        turn.writeNotification(YOUR_TURN);
        getOpponent(turn).writeNotification(OPPONENTS_TURN);
    }

    //neu 2 player co board hợp lệ thì start game
    public void checkBoards() {
        if (player1.getBoard() != null && player2.getBoard() != null) {
            placementTimer.cancel();
            startGame();
        }
    }

    //random turn
    private void startGame() {
        gameStarted = true;
        if (new Random().nextInt(2) == 0) {
            setTurn(player1);
        } else {
            setTurn(player2);
        }
    }

    // bắn tàu
    public synchronized void applyMove(MoveMessage move, ServerThread player) {
        int x = move.getX();
        int y = move.getY();
        int max = BOARD_DIMENSION;
        
        if (player != turn) {
            player.writeNotification(NOT_YOUR_TURN);
            return;
        }

        if (x < 0 || x >= max || y < 0 || y >= max) {
            player.writeNotification(INVALID_MOVE);
        } else {
            ServerThread opponent = getOpponent(player);
            Square square = opponent.getBoard().getSquare(x, y);
            if (square.isGuessed()) {
                player.writeNotification(REPEATED_MOVE);
                return;
            }

            boolean hit = square.guess();
            Ship ship = square.getShip();
            MoveResponseMessage response;
            if (ship != null && ship.isSunk()) {
                response = new MoveResponseMessage(x, y, ship, true, false);
            } else {
                response = new MoveResponseMessage(x, y, null, hit, false);
            }
            player.writeObject(response);
            response.setOwnBoard(true);
            opponent.writeObject(response);

            if (opponent.getBoard().gameOver()) {
                player.increaseNumberOfGame();
                opponent.increaseNumberOfGame();
            	player.addWinGame(); // update điểm
                turn.writeNotification(GAME_WIN);
                opponent.writeNotification(GAME_LOSE);
                turn = null;
            } else if (hit) {
                setTurn(player); // player gets another go if hit
            } else {
                setTurn(getOpponent(player));
            }
        }
    }

    private class PlacementTimerTask extends TimerTask {
        @Override
        public void run() {
            if (player1.getBoard() == null & player2.getBoard() == null) {
                NotificationMessage draw = new NotificationMessage(TIMEOUT_DRAW);
                player1.writeObject(draw);
                player2.writeObject(draw);
                killGame();
            } else if (player1.getBoard() == null) {
                // ServerThread1 failed to place ships in time
                player1.writeNotification(TIMEOUT_LOSE);
                player2.writeNotification(TIMEOUT_WIN);
                killGame();
            } else if (player2.getBoard() == null) {
                // ServerThread2 failed to place ships in time
                player1.writeNotification(TIMEOUT_WIN);
                player2.writeNotification(TIMEOUT_LOSE);
                killGame();
            }
        }
    }

    private class TurnTimerTask extends TimerTask {
        @Override
        public void run() {
            if (turn != null) {
                turn.writeNotification(TIMEOUT_LOSE);
                getOpponent(turn).writeNotification(TIMEOUT_WIN);
                killGame();
            }
        }
    }

}