package server.controller;

import client.controller.MoveMessage;
import client.controller.NotificationMessage;
import client.model.Board;
import client.util.Constants;
import server.dao.UserDAO;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import server.model.User;

public class ServerThread implements Runnable {

    private User user;
    private final Socket socketOfServer;
    private final int clientNumber;
    private ObjectInputStream is;
    private ObjectOutputStream os;
    private boolean isClosed;
    private Game game;
    private Room room;
    private Board board;
    private HashMap<String, ServerThread> requestList;
    private String ownKey;
    private String requestedGameKey;
    private final UserDAO userDAO;
    private final String clientIP;

    public ServerThread(Socket socketOfServer, int clientNumber) {
        this.socketOfServer = socketOfServer;
        this.clientNumber = clientNumber;
        System.out.println("Server thread number " + clientNumber + " Started");
        userDAO = new UserDAO();
        isClosed = false;
        room = null;

        if (this.socketOfServer.getInetAddress().getHostAddress().equals("127.0.0.1")) {
            clientIP = "127.0.0.1";
        } else {
            clientIP = this.socketOfServer.getInetAddress().getHostAddress();
        }
    }

    public ObjectInputStream getIs() {
        return is;
    }

    public ObjectOutputStream getOs() {
        return os;
    }

    public int getClientNumber() {
        return clientNumber;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public String getClientIP() {
        return clientIP;
    }

    public String getPlayerName() {
        return user.getNickname();
    }

    public String getStringFromUser(User user1) {
        return user1.getID() + "," + user1.getUsername()
                + "," + user1.getPassword() + "," + user1.getNickname() + "," + user1.getNumberOfGame() + ","
                + user1.getNumberOfWin() + "," + user1.getNumberOfDraw() + "," + user1.getRank();
    }

    public void goToOwnRoom() throws IOException {
        write("go-to-room," + room.getId() + "," + room.getCompetitor(this.getClientNumber()).getClientIP() + ",1," + getStringFromUser(room.getCompetitor(this.getClientNumber()).getUser()));
        room.getCompetitor(this.clientNumber).write("go-to-room," + room.getId() + "," + this.clientIP + ",0," + getStringFromUser(user));
    }

    public void goToPartnerRoom() throws IOException {
        write("go-to-room," + room.getId() + "," + room.getCompetitor(this.getClientNumber()).getClientIP() + ",0," + getStringFromUser(room.getCompetitor(this.getClientNumber()).getUser()));
        room.getCompetitor(this.clientNumber).write("go-to-room," + room.getId() + "," + this.clientIP + ",1," + getStringFromUser(user));
    }

    @Override
    public void run() {
        try {
            // Mở luồng vào ra trên Socket tại Server.
            is = new ObjectInputStream(socketOfServer.getInputStream());
            os = new ObjectOutputStream(socketOfServer.getOutputStream());
            System.out.println("Khời động luông mới thành công, ID là: " + clientNumber);
            write("server-send-id" + "," + this.clientNumber);
            while (!isClosed) {
                Object receivedMsg = is.readObject();
                if (receivedMsg == null) {
                    break;
                }
                if (receivedMsg instanceof String) {
                    String message = (String) receivedMsg;
                    String[] messageSplit = message.split(",");
                    //Xác minh
                    if (messageSplit[0].equals("client-verify")) {
                        System.out.println(message);
                        User user1 = userDAO.verifyUser(new User(messageSplit[1], messageSplit[2]));
                        if (user1 == null) {
                            write("wrong-user," + messageSplit[1] + "," + messageSplit[2]);
                        } else if (!user1.getIsOnline()) {
                            write("login-success," + getStringFromUser(user1));
                            this.user = user1;
                            userDAO.updateToOnline(this.user.getID());
                            Server.serverThreadBus.boardCast(clientNumber, "chat-server," + user1.getNickname() + " đang online");
                            Server.admin.addMessage("[" + user1.getID() + "] " + user1.getNickname() + " đang online");
                        } else {
                            write("dupplicate-login," + messageSplit[1] + "," + messageSplit[2]);
                        }
                    }
                    //Xử lý đăng kí
                    if (messageSplit[0].equals("register")) {
                        boolean checkdup = userDAO.checkDuplicated(messageSplit[1]);
                        if (checkdup) {
                            write("duplicate-username,");
                        } else {
                            User userRegister = new User(messageSplit[1], messageSplit[2], messageSplit[3]);
                            userDAO.addUser(userRegister);
                            this.user = userDAO.verifyUser(userRegister);
                            userDAO.updateToOnline(this.user.getID());
                            Server.serverThreadBus.boardCast(clientNumber, "chat-server," + this.user.getNickname() + " đang online");
                            write("login-success," + getStringFromUser(this.user));
                        }
                    }
                    //Xử lý người chơi đăng xuất
                    if (messageSplit[0].equals("offline")) {
                        userDAO.updateToOffline(this.user.getID());
                        Server.admin.addMessage("[" + user.getID() + "] " + user.getNickname() + " đã offline");
                        Server.serverThreadBus.boardCast(clientNumber, "chat-server," + this.user.getNickname() + " đã offline");
                        this.user = null;
                    }
                    //Xử lý xem danh sách bạn bè
                    if (messageSplit[0].equals("view-friend-list")) {
                        List<User> friends = userDAO.getListFriend(this.user.getID());
                        StringBuilder res = new StringBuilder("return-friend-list,");
                        for (User friend : friends) {
                            res.append(friend.getID()).append(",").append(friend.getNickname()).append(",").append(friend.getIsOnline() ? 1 : 0).append(",").append(friend.getIsPlaying() ? 1 : 0).append(",");
                        }
                        System.out.println(res);
                        write(res.toString());
                    }
                    //Xử lý chat toàn server
                    if (messageSplit[0].equals("chat-server")) {
                        Server.serverThreadBus.boardCast(clientNumber, messageSplit[0] + "," + user.getNickname() + " : " + messageSplit[1]);
                        Server.admin.addMessage("[" + user.getID() + "] " + user.getNickname() + " : " + messageSplit[1]);
                    }
                    //Xử lý vào phòng trong chức năng tìm kiếm phòng
                    if (messageSplit[0].equals("go-to-room")) {
                        int roomName = Integer.parseInt(messageSplit[1]);
                        boolean isFinded = false;
                        for (ServerThread serverThread : Server.serverThreadBus.getListServerThreads()) {
                            if (serverThread.getRoom() != null && serverThread.getRoom().getId() == roomName) {
                                isFinded = true;
                                if (serverThread.getRoom().getNumberOfUser() == 2) {
                                    write("room-fully,");
                                } else {
                                    if (serverThread.getRoom().getPassword() == null || serverThread.getRoom().getPassword().equals(messageSplit[2])) {
                                        this.room = serverThread.getRoom();
                                        room.setUser2(this);
                                        room.increaseNumberOfGame();
                                        this.userDAO.updateToPlaying(this.user.getID());
                                        goToPartnerRoom();
                                    } else {
                                        write("room-wrong-password,");
                                    }
                                }
                                break;
                            }
                        }
                        if (!isFinded) {
                            write("room-not-found,");
                        }
                    }
                    //Xử lý lấy danh sách bảng xếp hạng
                    if (messageSplit[0].equals("get-rank-charts")) {
                        List<User> ranks = userDAO.getUserStaticRank();
                        StringBuilder res = new StringBuilder("return-get-rank-charts,");
                        for (User user : ranks) {
                            res.append(getStringFromUser(user)).append(",");
                        }
                        System.out.println(res);
                        write(res.toString());
                    }
                    //Xử lý tạo phòng
                    if (messageSplit[0].equals("create-room")) {
                        room = new Room(this);
                        if (messageSplit.length == 2) {
                            room.setPassword(messageSplit[1]);
                            write("your-created-room," + room.getId() + "," + messageSplit[1]);
                            System.out.println("Tạo phòng mới thành công, password là " + messageSplit[1]);
                        } else {
                            write("your-created-room," + room.getId());
                            System.out.println("Tạo phòng mới thành công");
                        }
                        userDAO.updateToPlaying(this.user.getID());
                    }
                    //Xử lý xem danh sách phòng trống
                    if (messageSplit[0].equals("view-room-list")) {
                        StringBuilder res = new StringBuilder("room-list,");
                        int number = 1;
                        for (ServerThread serverThread : Server.serverThreadBus.getListServerThreads()) {
                            if (number > 8) {
                                break;
                            }
                            if (serverThread.room != null && serverThread.room.getNumberOfUser() == 1) {
                                res.append(serverThread.room.getId()).append(",").append(serverThread.room.getPassword()).append(",");
                            }
                            number++;
                        }
                        write(res.toString());
                        System.out.println(res);
                    }
                    //Xử lý lấy thông tin kết bạn và rank
                    if (messageSplit[0].equals("check-friend")) {
                        String res = "check-friend-response,";
                        res += (userDAO.checkIsFriend(this.user.getID(), Integer.parseInt(messageSplit[1])) ? 1 : 0);
                        write(res);
                    }
                    //Xử lý tìm phòng nhanh
                    if (messageSplit[0].equals("quick-room")) {
                        boolean isFinded = false;
                        for (ServerThread serverThread : Server.serverThreadBus.getListServerThreads()) {
                            if (serverThread.room != null && serverThread.room.getNumberOfUser() == 1 && serverThread.room.getPassword().equals(" ")) {
                                serverThread.room.setUser2(this);
                                this.room = serverThread.room;
                                room.increaseNumberOfGame();
                                System.out.println("Đã vào phòng " + room.getId());
                                goToPartnerRoom();
                                userDAO.updateToPlaying(this.user.getID());
                                isFinded = true;
                                //Xử lý phần mời cả 2 người chơi vào phòng
                                break;
                            }
                        }

                        if (!isFinded) {
                            this.room = new Room(this);
                            userDAO.updateToPlaying(this.user.getID());
                            System.out.println("Không tìm thấy phòng, tạo phòng mới");
                        }
                    }
                    //Xử lý không tìm được phòng
                    if (messageSplit[0].equals("cancel-room")) {
                        userDAO.updateToNotPlaying(this.user.getID());
                        System.out.println("Đã hủy phòng");
                        this.room = null;
                    }
                    //Xử lý khi có người chơi thứ 2 vào phòng
                    if (messageSplit[0].equals("join-room")) {
                        int ID_room = Integer.parseInt(messageSplit[1]);
                        for (ServerThread serverThread : Server.serverThreadBus.getListServerThreads()) {
                            if (serverThread.room != null && serverThread.room.getId() == ID_room) {
                                serverThread.room.setUser2(this);
                                this.room = serverThread.room;
                                System.out.println("Đã vào phòng " + room.getId());
                                room.increaseNumberOfGame();
                                goToPartnerRoom();
                                userDAO.updateToPlaying(this.user.getID());
                                break;
                            }
                        }
                    }
                    //Xử lý yêu cầu kết bạn
                    if (messageSplit[0].equals("make-friend")) {
                        Server.serverThreadBus.getServerThreadByUserID(Integer.parseInt(messageSplit[1]))
                                .write("make-friend-request," + this.user.getID() + "," + userDAO.getNickNameByID(this.user.getID()));
                    }
                    //Xử lý xác nhận kết bạn
                    if (messageSplit[0].equals("make-friend-confirm")) {
                        userDAO.makeFriend(this.user.getID(), Integer.parseInt(messageSplit[1]));
                        Server.serverThreadBus.getServerThreadByUserID(Integer.parseInt(messageSplit[1]))
                                .write("make-friend-confirm-request,");
                        System.out.println("Kết bạn thành công");
                    }
                    //Xử lý khi gửi yêu cầu thách đấu tới bạn bè
                    if (messageSplit[0].equals("duel-request")) {
                        Server.serverThreadBus.sendMessageToUserID(Integer.parseInt(messageSplit[1]),
                                "duel-notice," + this.user.getID() + "," + this.user.getNickname());
                    }
                    //Xử lý khi đối thủ đồng ý thách đấu
                    if (messageSplit[0].equals("agree-duel")) {
                        this.room = new Room(this);
                        int ID_User2 = Integer.parseInt(messageSplit[1]);
                        ServerThread user2 = Server.serverThreadBus.getServerThreadByUserID(ID_User2);
                        room.setUser2(user2);
                        user2.setRoom(room);
                        room.increaseNumberOfGame();
                        goToOwnRoom();
                        userDAO.updateToPlaying(this.user.getID());
                    }
                    //Xử lý khi không đồng ý thách đấu
                    if (messageSplit[0].equals("disagree-duel")) {
                        Server.serverThreadBus.sendMessageToUserID(Integer.parseInt(messageSplit[1]), message);
                    }
                    if (messageSplit[0].equals("chat")) {
                        room.getCompetitor(clientNumber).write(message);
                    }
                    if (messageSplit[0].equals("win")) {
                        userDAO.addWinGame(this.user.getID());
                        room.increaseNumberOfGame();
                        room.getCompetitor(clientNumber).write("caro," + messageSplit[1] + "," + messageSplit[2]);
                        room.boardCast("new-game,");
                    }
                    if (messageSplit[0].equals("lose")) {
                        userDAO.addWinGame(room.getCompetitor(clientNumber).user.getID());
                        room.increaseNumberOfGame();
                        room.getCompetitor(clientNumber).write("competitor-time-out");
                        write("new-game,");
                    }
                    if (messageSplit[0].equals("draw-request")) {
                        room.getCompetitor(clientNumber).write(message);
                    }
                    if (messageSplit[0].equals("draw-confirm")) {
                        room.increaseNumberOfDraw();
                        room.increaseNumberOfGame();
                        room.boardCast("draw-game,");
                    }
                    if (messageSplit[0].equals("draw-refuse")) {
                        room.getCompetitor(clientNumber).write("draw-refuse,");
                    }
                    // Xử lí left room
                    if (messageSplit[0].equals("left-room")) {
                        if (room != null) {
                            room.setUsersToNotPlaying();
//                            room.decreaseNumberOfGame();
                            room.getCompetitor(clientNumber).write("left-room,");
                            room.getCompetitor(clientNumber).room = null;
                            this.room = null;
                        }
                    }
                } else if (receivedMsg instanceof Board) {
                    Board board = (Board) receivedMsg;

                    // Print Board nhân được từ Client
                    board.printBoard(true);

                    if (Board.isValid(board) && game != null) {
                        writeNotification(Constants.NotificationCode.BOARD_ACCEPTED);
                        this.board = board;
                        //neu 2 player board hợp lệ thì start game
                        game.checkBoards();
                    } else if (game == null) {
                        writeNotification(Constants.NotificationCode.NOT_IN_GAME);
                    } else {
                        writeNotification(Constants.NotificationCode.INVALID_BOARD);
                    }
                } else if (receivedMsg instanceof MoveMessage) {
                    if (game != null) {
                        game.applyMove((MoveMessage) receivedMsg, this);
                    }
                }
            }
        } catch (IOException e) {
            //Thay đổi giá trị cờ để thoát luồng
            isClosed = true;
            //Cập nhật trạng thái của user
            if (this.user != null) {
                userDAO.updateToOffline(this.user.getID());
                userDAO.updateToNotPlaying(this.user.getID());
                Server.serverThreadBus.boardCast(clientNumber, "chat-server," + this.user.getNickname() + " đã offline");
                Server.admin.addMessage("[" + user.getID() + "] " + user.getNickname() + " đã offline");
            }

            //remove thread khỏi bus
            Server.serverThreadBus.remove(clientNumber);
            System.out.println(this.clientNumber + " đã thoát");
            if (room != null) {
                try {
                    if (room.getCompetitor(clientNumber) != null) {
                        room.decreaseNumberOfGame();
                        room.getCompetitor(clientNumber).write("left-room,");
                        room.getCompetitor(clientNumber).room = null;
                    }
                    this.room = null;
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(ServerThread.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public void write(String message) throws IOException {
        os.writeObject(message);
        os.flush();
    }

    public void writeObject(Object object) {
        try {
            os.writeObject(object);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //gui thong bao den client co the kem them Thong tin bo sung
    public void writeNotification(int notificationMessage, String... text) {
        try {
            NotificationMessage nm = new NotificationMessage(notificationMessage, text);
            os.writeObject(nm);
            os.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String getOwnKey() {
        return ownKey;
    }

    public Board getBoard() {
        return this.board;
    }

    // Send  game request đến player, và update req list, req game key
    //  requester là player gửi request
    public synchronized void sendRequest(ServerThread requester) {
        requestList.put(requester.getOwnKey(), requester);
        requester.requestedGameKey = this.ownKey;
        writeNotification(Constants.NotificationCode.NEW_JOIN_GAME_REQUEST, requester.getOwnKey(), requester.getUser().getNickname());
    }

    //  đối thủ chấp nhận một yêu cầu và thông báo cho người chơi
    //  opponent là player  đã accept request
    public synchronized void requestAccepted(ServerThread opponent) {
        opponent.requestList.remove(ownKey);
        requestedGameKey = null;
        writeNotification(Constants.NotificationCode.JOIN_GAME_REQUEST_ACCEPTED);
    }

    // đối thủ từ chối một yêu cầu
    public synchronized void requestRejected(ServerThread opponent) {
        opponent.requestList.remove(ownKey);
        requestedGameKey = null;
        writeNotification(Constants.NotificationCode.JOIN_GAME_REQUEST_REJECTED);
    }

    public void requestPlayerList() {
        writeNotification(Constants.NotificationCode.REQUEST_PLAYER_LIST);
    }
    
    public void addWinGame() {
        userDAO.addWinGame(this.user.getID());
    }
    
    public void increaseNumberOfGame() {
        this.user.setNumberOfGame(this.user.getNumberOfGame() + 1);
    }
}