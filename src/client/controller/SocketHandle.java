package client.controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import javax.swing.JOptionPane;
import client.model.User;
import client.util.Constants;
import static client.util.Constants.NotificationCode.*;
import client.views.GameView;

public class SocketHandle implements Runnable {

    private GameView view;
    private volatile GameHandler gameHandlerModel;
    private String key = "";
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private Socket socketOfClient;
    private Boolean isConnected;

    public boolean isConnected() {
        return isConnected;
    }

    public SocketHandle(GameView GameView) {
        this.view = GameView;
    }

    public List<User> getListUser(String[] message) {
        List<User> friend = new ArrayList<>();
        for (int i = 1; i < message.length; i = i + 4) {
            friend.add(new User(Integer.parseInt(message[i]),
                    message[i + 1],
                    message[i + 2].equals("1"),
                    message[i + 3].equals("1")));
        }
        return friend;
    }

    public List<User> getListRank(String[] message) {
        List<User> friend = new ArrayList<>();
        for (int i = 1; i < message.length; i = i + 8) {
            friend.add(new User(Integer.parseInt(message[i]),
                    message[i + 1],
                    message[i + 2],
                    message[i + 3],
                    Integer.parseInt(message[i + 4]),
                    Integer.parseInt(message[i + 5]),
                    Integer.parseInt(message[i + 6]),
                    Integer.parseInt(message[i + 7])));
        }
        return friend;
    }

    public User getUserFromString(int start, String[] message) {
        return new User(Integer.parseInt(message[start]),
                message[start + 1],
                message[start + 2],
                message[start + 3],
                Integer.parseInt(message[start + 4]),
                Integer.parseInt(message[start + 5]),
                Integer.parseInt(message[start + 6]),
                Integer.parseInt(message[start + 7]));
    }

    @Override
    public void run() {
        try {
            socketOfClient = new Socket("127.0.0.1", 7777);
            System.out.println("Ket noi thanh cong");
            isConnected = true;
            out = new ObjectOutputStream(socketOfClient.getOutputStream());
            in = new ObjectInputStream(socketOfClient.getInputStream());
            while (true) {
                Object receivedMsg = in.readObject();
                if (receivedMsg == null) {
                    break;
                }
                if (receivedMsg instanceof String) {
                    String message = (String) receivedMsg;
                    String[] messageSplit = message.split(",");
                    if (messageSplit[0].equals("server-send-id")) {
                        int serverId = Integer.parseInt(messageSplit[1]);
                    }
                    //Đăng nhập thành công
                    if (messageSplit[0].equals("login-success")) {
                        System.out.println("Dang nhap thanh cong");
                        Client.closeAllViews();
                        Client.user = getUserFromString(1, messageSplit);
                        Client.openView(Client.View.HOMEPAGE);
                    }
                    //Thông tin tài khoản sai
                    if (messageSplit[0].equals("wrong-user")) {
                        System.out.println("Thong tin tai khoan sai");
                        Client.closeView(Client.View.GAME_NOTICE);
                        Client.openView(Client.View.LOGIN, messageSplit[1], messageSplit[2]);
                        Client.loginFrm.showError("Thông tin tài khoản hoặc mật khẩu không chính xác");
                    }
                    //Tài khoản đã đăng nhập
                    if (messageSplit[0].equals("dupplicate-login")) {
                        System.out.println("Thong tin tai khoan sai");
                        Client.closeView(Client.View.GAME_NOTICE);
                        Client.openView(Client.View.LOGIN, messageSplit[1], messageSplit[2]);
                        Client.loginFrm.showError("Tài khoản đã được đăng nhập");
                    }
                    //Xử lý register trùng tên
                    if (messageSplit[0].equals("duplicate-username")) {
                        Client.closeAllViews();
                        Client.openView(Client.View.REGISTER);
                        JOptionPane.showMessageDialog(Client.registerFrm, "Tên tài khoản đã được người khác sử dụng");
                    }
                    //Xử lý nhận thông tin, chat từ toàn server
                    if (messageSplit[0].equals("chat-server")) {
                        if (Client.homePageFrm != null) {
                            Client.homePageFrm.addMessage(messageSplit[1]);
                        }
                    }
                    //Xử lý xem rank
                    if (messageSplit[0].equals("return-get-rank-charts")) {
                        if (Client.rankFrm != null) {
                            Client.rankFrm.setDataToTable(getListRank(messageSplit));
                        }
                    }
                    //Xử lý hiển thị thông tin đối thủ là bạn bè/không
                    if (messageSplit[0].equals("check-friend-response")) {
                        if (Client.competitorInfoFrm != null) {
                            Client.competitorInfoFrm.checkFriend((messageSplit[1].equals("1")));
                        }
                    }
                    //Xử lý yêu cầu kết bạn tới
                    if (messageSplit[0].equals("make-friend-request")) {
                        int ID = Integer.parseInt(messageSplit[1]);
                        String nickname = messageSplit[2];
                        Client.openView(Client.View.FRIEND_REQUEST, ID, nickname);
                    }
                    if (messageSplit[0].equals("make-friend-confirm-request")) {
                        Client.closeView(Client.View.COMPETITOR_INFO);
                    }
                    //Tạo phòng và server trả về tên phòng
                    if (messageSplit[0].equals("your-created-room")) {
                        Client.closeAllViews();
                        Client.openView(Client.View.WAITING_ROOM);
                        Client.waitingRoomFrm.setRoomName(messageSplit[1]);
                        if (messageSplit.length == 3) {
                            Client.waitingRoomFrm.setRoomPassword(messageSplit[2]);
                        }
                    }
                    //Xử lý danh sách bạn bè
                    if (messageSplit[0].equals("return-friend-list")) {
                        if (Client.friendListFrm != null) {
                            Client.friendListFrm.updateFriendList(getListUser(messageSplit));
                        }
                    }
                    //Xử lý khi nhận được yêu cầu thách đấu
                    if (messageSplit[0].equals("duel-notice")) {
                        int res = JOptionPane.showConfirmDialog(Client.getVisibleJFrame(), "Bạn nhận được lời thách đấu của " + messageSplit[2] + " (ID=" + messageSplit[1] + ")", "Xác nhận thách đấu", JOptionPane.YES_NO_OPTION);
                        try {
                            if (res == JOptionPane.YES_OPTION) {
                                Client.socketHandle.write("agree-duel," + messageSplit[1]);
                            } else {
                                Client.socketHandle.write("disagree-duel," + messageSplit[1]);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    //Xử lý không đồng ý thách đấu
                    if (messageSplit[0].equals("disagree-duel")) {
                        Client.closeAllViews();
                        Client.openView(Client.View.HOMEPAGE);
                        JOptionPane.showMessageDialog(Client.homePageFrm, "Đối thủ không đồng ý thách đấu");
                    }
                    //Vào phòng chơi
                    if (messageSplit[0].equals("go-to-room")) {
                        System.out.println("Vào phòng");
                        int roomID = Integer.parseInt(messageSplit[1]);
                        String competitorIP = messageSplit[2];
                        int isStart = Integer.parseInt(messageSplit[3]);

                        User competitor = getUserFromString(4, messageSplit);
                        if (Client.findRoomFrm != null) {
                            Client.findRoomFrm.showFoundRoom();
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException ex) {
                                JOptionPane.showMessageDialog(Client.findRoomFrm, "Lỗi khi sleep thread");
                            }
                        } else if (Client.waitingRoomFrm != null) {
                            Client.waitingRoomFrm.showFoundCompetitor();
                            try {
                                Thread.sleep(3000);
                            } catch (InterruptedException ex) {
                                JOptionPane.showMessageDialog(Client.waitingRoomFrm, "Lỗi khi sleep thread");
                            }
                        }
                        Client.closeAllViews();
                        System.out.println("Đã vào phòng: " + roomID);
                        //Xử lý vào phòng
//                        Client.socketHandle.startGame(new NotificationMessage(PLACE_SHIPS));
                    }
                    //Xử lý khi thoát phòng chơi
                    if (messageSplit[0].equals("left-room")) {
//                        view.stopTimer();
                        Client.closeAllViews();
                        Client.openView(Client.View.GAME_NOTICE, "Đối thủ đã thoát khỏi phòng", "Đang trở về trang chủ");
                        Thread.sleep(3000);
                        Client.closeAllViews();
                        Client.openView(Client.View.HOMEPAGE);
                    }
                    //Xử lý kết quả tìm phòng từ server
                    if (messageSplit[0].equals("room-fully")) {
                        Client.closeAllViews();
                        Client.openView(Client.View.HOMEPAGE);
                        JOptionPane.showMessageDialog(Client.homePageFrm, "Phòng chơi đã đủ 2 người chơi");
                    }
                    // Xử lý không tìm thấy phòng trong chức năng vào phòng
                    if (messageSplit[0].equals("room-not-found")) {
                        Client.closeAllViews();
                        Client.openView(Client.View.HOMEPAGE);
                        JOptionPane.showMessageDialog(Client.homePageFrm, "Không tìm thấy phòng");
                    }
                    // Xử lý phòng có mật khẩu sai
                    if (messageSplit[0].equals("room-wrong-password")) {
                        Client.closeAllViews();
                        Client.openView(Client.View.HOMEPAGE);
                        JOptionPane.showMessageDialog(Client.homePageFrm, "Mật khẩu phòng sai");
                    }
                    //Xử lý lấy danh sách phòng
                    if (messageSplit[0].equals("room-list")) {
                        Vector<String> rooms = new Vector<>();
                        Vector<String> passwords = new Vector<>();
                        for (int i = 1; i < messageSplit.length; i = i + 2) {
                            rooms.add("Phòng " + messageSplit[i]);
                            passwords.add(messageSplit[i + 1]);
                        }
                        Client.roomListFrm.updateRoomList(rooms, passwords);
                    }
                } else {
                    if (gameHandlerModel != null) {
                        gameHandlerModel.parseInput(receivedMsg);
                    } else if (receivedMsg instanceof NotificationMessage) {
                        parserInput(receivedMsg);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void parserInput(Object receivedMsg) throws InterruptedException {
        if (receivedMsg instanceof NotificationMessage) {
            NotificationMessage n = (NotificationMessage) receivedMsg;

            if (n.getCode() != Constants.NotificationCode.OPPONENTS_NAME) {
                System.out.print("<< " + n.getCode());
            }

            switch (n.getCode()) {
                case GAME_TOKEN:
                    if (n.getText().length == 1) {
                        key = n.getText()[0];
                        System.out.println(" " + key);
                    }
                    break;
                case OPPONENTS_NAME:
                    startGame(receivedMsg);
                    break;
            }
        }
    }

    public GameView getView() {
        return view;
    }

    public void write(String message) throws IOException {
        out.writeObject(message);
        out.flush();
    }

    public Socket getSocketOfClient() {
        return socketOfClient;
    }

    private void startGame(Object firstInput) {
//        waitingRoomView.setVisible(false);
        GameView gameView = new GameView(this.out, this.in, this);
        gameHandlerModel = gameView.getModel();
        gameHandlerModel.parseInput(firstInput);
    }

    public void sendStringArray(String[] array) {
        try {
            out.writeObject(array);
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void reopen() {
        if (gameHandlerModel != null) {
            this.gameHandlerModel.getView().dispose();
            this.gameHandlerModel = null;
        }
        Client.homePageFrm.setVisible(true);
    }
}
