/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client.util;

/**
 *
 * @author bhhoa
 */
public class Constants {

    public static final String ALPHABET = "abcdefghijklmnopqrstuvwxyz";

    public static class Configs {
        public static final int PORT_NUMBER = 7777;
        // public static final String HOST_NAME = "26.64.248.188";
//        public static final String HOST_NAME = "26.36.9.179";
        public static final String HOST_NAME = "localhost";
        public static final int BOARD_DIMENSION = 10;

        public final static int TURN_TIMEOUT = 30000;
        public final static int PLACEMENT_TIMEOUT = 60000;
    }

    public static class NotificationCode {
        public static final int REQUEST_PLAYER_LIST = 5;
        public final static int PLACE_SHIPS = 101;
        public final static int YOUR_TURN = 102;
        public final static int OPPONENTS_TURN = 103;
        public final static int SHOT = 104;

        public final static int GAME_WIN = 201;
        public final static int GAME_LOSE = 202;
        public final static int TIMEOUT_WIN = 203;
        public final static int TIMEOUT_LOSE = 204;
        public final static int TIMEOUT_DRAW = 205;
        public final static int OPPONENT_DISCONNECTED = 206;
        public final static int PLAYER_CONNECTED = 207;

        public final static int OPPONENTS_NAME = 301;
        public final static int INVALID_NAME = 302;
        public final static int NAME_TAKEN = 303;
        public final static int NAME_ACCEPTED = 304;
        public final static int NAME_REQUEST = 305;

        public final static int GAME_TOKEN = 401;
        public final static int GAME_NOT_FOUND = 402;
        public final static int CANNOT_PLAY_YOURSELF = 403;

        public final static int REPEATED_MOVE = 501;
        public final static int NOT_YOUR_TURN = 502;
        public final static int INVALID_MOVE = 503;
        public final static int INVALID_BOARD = 504;
        public final static int NOT_IN_GAME = 505;

        public final static int SEND_BOARD = 601;
        public final static int BOARD_ACCEPTED = 602;

        public final static int NEW_JOIN_GAME_REQUEST = 701;
        public final static int JOIN_GAME_REQUEST_REJECTED = 702;
        public final static int JOIN_GAME_REQUEST_ACCEPTED = 703;
        public final static int JOIN_GAME_REQUEST_CANCELLED = 704;
    }
}
