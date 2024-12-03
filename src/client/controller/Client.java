package client.controller;

import javax.swing.JFrame;
import client.model.User;
import client.views.CompetitorInfoFrm;
import client.views.CreateRoomPasswordFrm;
import client.views.FindRoomFrm;
import client.views.FriendListFrm;
import client.views.FriendRequestFrm;
import client.views.GameNoticeFrm;
import client.views.GameView;
import client.views.HomePageFrm;
import client.views.JoinRoomPasswordFrm;
import client.views.LoginFrm;
import client.views.RankFrm;
import client.views.RegisterFrm;
import client.views.RoomListFrm;
import client.views.RoomNameFrm;
import client.views.WaitingRoomFrm;

public class Client {
    public static User user;
    public static SocketHandle socketHandle;
    public static LoginFrm loginFrm;
    public static RegisterFrm registerFrm;
    public static GameNoticeFrm gameNoticeFrm;
    public static HomePageFrm homePageFrm;
    public static RankFrm rankFrm;
    public static CompetitorInfoFrm competitorInfoFrm;
    public static FriendRequestFrm friendRequestFrm;
    public static WaitingRoomFrm waitingRoomFrm;
    public static CreateRoomPasswordFrm createRoomPasswordFrm;
    public static RoomNameFrm roomNameFrm;
    public static FriendListFrm friendListFrm;
    public static FindRoomFrm findRoomFrm;
    public static RoomListFrm roomListFrm;
    public static JoinRoomPasswordFrm joinRoomPasswordFrm;
    public static GameView gameView;
   
    public Client() {
    }

    public static JFrame getVisibleJFrame() {
        if (roomListFrm != null && roomListFrm.isVisible()) {
            return roomListFrm;
        }
        if (friendListFrm != null && friendListFrm.isVisible()) {
            return friendListFrm;
        }
        if (createRoomPasswordFrm != null && createRoomPasswordFrm.isVisible()) {
            return createRoomPasswordFrm;
        }
        if (joinRoomPasswordFrm != null && joinRoomPasswordFrm.isVisible()) {
            return joinRoomPasswordFrm;
        }
        if (rankFrm != null && rankFrm.isVisible()) {
            return rankFrm;
        }
        return homePageFrm;
    }

    public static void openView(View viewName, int arg1, String arg2) {
        if (viewName != null) {
            switch (viewName) {
                case JOIN_ROOM_PASSWORD:
                    joinRoomPasswordFrm = new JoinRoomPasswordFrm(arg1, arg2);
                    joinRoomPasswordFrm.setVisible(true);
                    break;
                case FRIEND_REQUEST:
                    friendRequestFrm = new FriendRequestFrm(arg1, arg2);
                    friendRequestFrm.setVisible(true);
            }
        }
    }

    public static void openView(View viewName, User user) {
        if (viewName != null) {
            switch (viewName) {
                case COMPETITOR_INFO:
                    competitorInfoFrm = new CompetitorInfoFrm(user);
                    competitorInfoFrm.setVisible(true);
                    break;
            }
        }
    }
    
//    public static void openView(View viewName, User competitor, int room_ID, int isStart) {
//        if (viewName == View.GAME_PLAY) {
//            gameView = new GameView();
//            gameView.setVisible(true);
//        }
//    }
    
    public static void openView(View viewName, String arg1, String arg2) {
        if (viewName != null) {
            switch (viewName) {
                case GAME_NOTICE:
                    gameNoticeFrm = new GameNoticeFrm(arg1, arg2);
                    gameNoticeFrm.setVisible(true);
                    break;
                case LOGIN:
                    loginFrm = new LoginFrm(arg1, arg2);
                    loginFrm.setVisible(true);
            }
        }
    }

    public static void openView(View viewName) {
        if (viewName != null) {
            switch (viewName) {
                case LOGIN:
                    loginFrm = new LoginFrm();
                    loginFrm.setVisible(true);
                    break;
                case REGISTER:
                    registerFrm = new RegisterFrm();
                    registerFrm.setVisible(true);
                    break;
                case HOMEPAGE:
                    homePageFrm = new HomePageFrm();
                    homePageFrm.setVisible(true);
                    break;
                case RANK:
                    rankFrm = new RankFrm();
                    rankFrm.setVisible(true);
                    break;
                case WAITING_ROOM:
                    waitingRoomFrm = new WaitingRoomFrm();
                    waitingRoomFrm.setVisible(true);
                    break;
                case CREATE_ROOM_PASSWORD:
                    createRoomPasswordFrm = new CreateRoomPasswordFrm();
                    createRoomPasswordFrm.setVisible(true);
                    break;
                case ROOM_NAME_FRM:
                    roomNameFrm = new RoomNameFrm();
                    roomNameFrm.setVisible(true);
                    break;
                case FRIEND_LIST:
                    friendListFrm = new FriendListFrm();
                    friendListFrm.setVisible(true);
                    break;
                case FIND_ROOM:
                    findRoomFrm = new FindRoomFrm();
                    findRoomFrm.setVisible(true);
                    break;
                case ROOM_LIST:
                    roomListFrm = new RoomListFrm();
                    roomListFrm.setVisible(true);
                    break;
            }
        }
    }
    public static void closeView(View viewName) {
        if (viewName != null) {
            switch (viewName) {
                case LOGIN:
                    loginFrm.dispose();
                    break;
                case REGISTER:
                    registerFrm.dispose();
                    break;
                case GAME_NOTICE:
                    gameNoticeFrm.dispose();
                    break;
                case HOMEPAGE:
                    homePageFrm.dispose();
                    break;
                case RANK:
                    rankFrm.dispose();
                    break;
                case COMPETITOR_INFO:
                    competitorInfoFrm.dispose();
                    break;
                case FRIEND_REQUEST:
                    friendRequestFrm.dispose();
                    break;
                case WAITING_ROOM:
                    waitingRoomFrm.dispose();
                    break;
                case CREATE_ROOM_PASSWORD:
                    createRoomPasswordFrm.dispose();
                    break;
                case ROOM_NAME_FRM:
                    roomNameFrm.dispose();
                    break;
                case FRIEND_LIST:
                    friendListFrm.stopAllThread();
                    friendListFrm.dispose();
                    break;
                case FIND_ROOM:
                    findRoomFrm.stopAllThread();
                    findRoomFrm.dispose();
                    break;
                case ROOM_LIST:
                    roomListFrm.dispose();
                    break;
                case JOIN_ROOM_PASSWORD:
                    joinRoomPasswordFrm.dispose();
                    break;
                case GAME_VIEW:
                    gameView.dispose();
                    break;
            }
        }
    }

    public static void closeAllViews() {
        if (loginFrm != null) {
            loginFrm.dispose();
        }
        if (registerFrm != null) {
            registerFrm.dispose();
        }
        if (gameNoticeFrm != null) {
            gameNoticeFrm.dispose();
        }
        if (homePageFrm != null) {
            homePageFrm.dispose();
        }
        if (rankFrm != null) {
            rankFrm.dispose();
        }
        if (competitorInfoFrm != null) {
            competitorInfoFrm.dispose();
        }
        if (friendRequestFrm != null) {
            friendRequestFrm.dispose();
        }
        if (waitingRoomFrm != null) {
            waitingRoomFrm.dispose();
        }
        if (createRoomPasswordFrm != null) {
            createRoomPasswordFrm.dispose();
        }
        if (roomNameFrm != null) {
            roomNameFrm.dispose();
        }
        if (friendListFrm != null) {
            friendListFrm.stopAllThread();
            friendListFrm.dispose();
        }
        if (findRoomFrm != null) {
            findRoomFrm.stopAllThread();
            findRoomFrm.dispose();
        }
        if (roomListFrm != null) {
            roomListFrm.dispose();
        }
        if (joinRoomPasswordFrm != null) {
            joinRoomPasswordFrm.dispose();
        }
        if (gameView != null) {
            gameView.dispose();
        }
    }

    public static void main(String[] args) {
        new Client().initView();
    }

    public void initView() {
        loginFrm = new LoginFrm();
        loginFrm.setVisible(true);
        socketHandle = new SocketHandle(Client.gameView);
        socketHandle.run();
    }

    public enum View {
        LOGIN,
        REGISTER,
        GAME_NOTICE,
        HOMEPAGE,
        RANK,
        COMPETITOR_INFO,
        FRIEND_REQUEST,
        WAITING_ROOM,
        CREATE_ROOM_PASSWORD,
        ROOM_NAME_FRM,
        FRIEND_LIST,
        FIND_ROOM,
        ROOM_LIST,
        JOIN_ROOM_PASSWORD,
        GAME_VIEW
    }
}
