package client.model;

import client.controller.GameHandler;
import client.controller.MoveResponseMessage;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class Board implements Serializable {
    private Square[][] squares;
    private ArrayList<Ship> ships;
    private boolean ownBoard;
    private transient GameHandler gameHandler;
    private transient boolean boatPositionLocked = true;
    private transient ArrayList<PropertyChangeListener> changeListeners;

    //tao mới board với 5 ship
    public Board(boolean ownBoard) {
        this.ownBoard = ownBoard;
        squares = new Square[10][10];

        // populates the squares array
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                squares[i][j] = new Square(i, j, ownBoard);
            }
        }

        ships = new ArrayList<>();
        ships.add(new Ship(Ship.Type.AIRCRAFT_CARRIER));
        ships.add(new Ship(Ship.Type.BATTLESHIP));
        ships.add(new Ship(Ship.Type.DESTROYER));
        ships.add(new Ship(Ship.Type.PATROL_BOAT));
        ships.add(new Ship(Ship.Type.SUBMARINE));

        this.changeListeners = new ArrayList<>();
    }

    //kiem tra 1 board có hợp lệ
    public static boolean isValid(Board board) {
        Board tempBoard = new Board(true);
        for (Ship s : board.getShips()) {
            if (s.getSquares().isEmpty()) {
                return false;
            }
            int[] tl = s.getTopLeft();
            Ship tempBoardShip = tempBoard.findShipByType(s.getType());
            tempBoardShip.setVertical(s.isVertical());
            if (!tempBoard.placeShip(tempBoardShip, tl[0], tl[1])) {
                return false;
            }
        }
        return tempBoard.shipPlacementEquals(board);
    }

    //Kiểm tra xem các vị trí Ship có bị khóa không
    public boolean isBoatPositionLocked() {
        return boatPositionLocked;
    }

    public void setBoatPositionLocked(boolean boatPositionLocked) {
        this.boatPositionLocked = boatPositionLocked;
        gameHandler.getView().setSendShipState(!boatPositionLocked);
        firePropertyChange("resetSelectedShip", null, null);
    }

    public boolean isOwnBoard() {
        return (ownBoard);
    }

    public Square getSquare(int x, int y) {
        return squares[x][y];
    }

    //kiểm tra ship có đặt được lên board không
    public boolean placeShip(Ship ship, int x, int y) {
        // kiem tra nằm trong board
        int end = (ship.isVertical()) ? y + ship.getLength() - 1 : x
                + ship.getLength() - 1;
        if (x < 0 || y < 0 || end >= 10) {
            return false;
        }

        // kiểm tra bị trùng
        for (int i = 0; i < ship.getLength(); i++) {
            if (ship.isVertical()) {
                if (squares[x][y + i].isShip())
                    return false;
            } else {
                if (squares[x + i][y].isShip())
                    return false;
            }
        }

        // đặt ship vào square
        for (int i = 0; i < ship.getLength(); i++) {
            if (ship.isVertical()) {
                squares[x][y + i].setShip(ship);
                ship.setSquare(squares[x][y + i]);
            } else if (!ship.isVertical()) {
                squares[x + i][y].setShip(ship);
                ship.setSquare(squares[x + i][y]);
            }
        }

        return true;
    }

    //lấy ship từ board và xóa các square của nó, setship=null
    public void pickUpShip(Ship ship) {
        for (Square s : ship.getSquares()) {
            s.setShip(null);
        }
        ship.clearSquares();
    }

    //check ganme over nếu tất cả ship đã chìm
    public boolean gameOver() {
        for (Ship ship : ships) {
            if (!ship.isSunk())
                return false;
        }
        return true;
    }

    // print board ra console
    public void printBoard(boolean clean) {
        for (int i = 0; i < 10; ++i) {
            for (int j = 0; j < 10; ++j) {
                Square s = squares[j][i];
                Ship ship = s.getShip();
                char c = '-';
                if (s.isGuessed() && !clean && s.getState() == Square.State.CONTAINS_SHIP) {
                    c = 'X';
                } else if (s.isGuessed() && !clean) {
                    c = 'O';
                } else if (ship != null) {
                    switch (ship.getType()) {
                        case AIRCRAFT_CARRIER:
                            c = 'A';
                            break;
                        case BATTLESHIP:
                            c = 'B';
                            break;
                        case SUBMARINE:
                            c = 'S';
                            break;
                        case DESTROYER:
                            c = 'D';
                            break;
                        case PATROL_BOAT:
                            c = 'P';
                    }
                }
                System.out.print(c + " ");
            }
            System.out.println();
        }
    }

    //Return ArrayList về các ship từ Board, có thể chưa được đặt trên Board
    public ArrayList<Ship> getShips() {
        return ships;
    }

    //Áp dụng move đối với Board, cập nhật Square và đánh chìm Ship nếu có
    // (Chèn hiệu ứng nổ, updateSquare, addChat...)
    public void applyMove(MoveResponseMessage move) {
        Ship ship = move.shipSank();
        if (ship != null) {
            ship.sink();
            if (!ownBoard) {
                ship.updateSquareReferences(this);
                ships.add(ship);
                firePropertyChange("sankShip", null, ship);
            }
            for (Square shipSquare : ship.getSquares()) {
                Square boardSquare = getSquare(shipSquare.getX(),
                        shipSquare.getY());
                boardSquare.update(true, ship);
            }
            // TODO: Fix me
//            gameHandler.getView().addChatMessage("SUNK SHIP");
        } else {
            Square square = getSquare(move.getX(), move.getY());
            square.update(move.isHit(), null);
        }
    }

    //Kiểm tra xem hai Board có vị trí Ship giống hệt nhau không
    //- param board: Board mà đang được so sánh với
    //- Return true nếu các Board có Ship ở các vị trí giống nhau
    public boolean shipPlacementEquals(Board board) {
        for (int y = 0; y < 10; ++y) {
            for (int x = 0; x < 10; ++x) {
                Square s1 = this.getSquare(x, y);
                Square s2 = board.getSquare(x, y);
                if ((s1.isShip() != s2.isShip())) {
                    return false;
                }
                if (s1.getShip() != null && s2.getShip() != null
                        && s1.getShip().getType() != s2.getShip().getType()) {
                    return false;
                }
            }
        }
        return true;
    }

    private Ship findShipByType(Ship.Type type) {
        for (Ship s : ships) {
            if (s.getType() == type) {
                return s;
            }
        }
        return null;
    }

    //Kiểm tra xem Square này có nằm cạnh Ship theo chiều ngang, chiều dọc hay đường chéo không
    //- param square: Square đang được kiểm tra với link Ship gần đó
    //- Return true nếu có link Ship bên cạnh link Square này, ngược lại là false
    public boolean isSquareNearShip(Square square) {
        for (int x = square.getX() - 1; x <= square.getX() + 1; x++) {
            for (int y = square.getY() - 1; y <= square.getY() + 1; y++) {
                if (isCoordWithinBounds(x, y) && getSquare(x, y).isShip()
                        && !(x == square.getX() && y == square.getY())) {
                    return true;
                }
            }
        }
        return false;
    }

    // checks x, y trong khoang 0,9
    private boolean isCoordWithinBounds(int x, int y) {
        return (x >= 0 && x < 10 && y >= 0 && y < 10);
    }

    //Gửi một move tại các tọa độ được cung cấp tới ObjectOutputStream của client
    public void sendMove(int x, int y) throws IOException {
        gameHandler.sendMove(x, y);
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        changeListeners.add(listener);
    }

    //Kích hoạt PropertyChangeEvent khi ship được xoay
    public void selectedShipRotated() {
        firePropertyChange("rotateSelectedShip", null, null);
    }

    public GameHandler getClient() {
        return gameHandler;
    }

    public void setClient(GameHandler gameHandler) {
        this.gameHandler = gameHandler;
    }

    private void firePropertyChange(String property, Object oldValue, Object newValue) {
        PropertyChangeEvent event = new PropertyChangeEvent(this, property, oldValue, newValue);
        for (PropertyChangeListener listener : changeListeners) {
            listener.propertyChange(event);
        }
    }
}