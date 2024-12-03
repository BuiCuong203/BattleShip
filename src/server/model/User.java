/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package server.model;

/**
 *
 * @author bhhoa
 */
public class User {
    private int ID;
    private String username;
    private String password;
    private String nickname;
    private int numberOfGame;
    private int numberOfWin;
    private int numberOfDraw;
    private boolean isOnline;
    private boolean isPlaying;
    private int rank;

    public User() {
    }

    public User(int ID, String username, String password, String nickname, int numberOfGame, int numberOfWin, int numberOfDraw, boolean isOnline, boolean isPlaying, int rank) {
        this.ID = ID;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.numberOfGame = numberOfGame;
        this.numberOfWin = numberOfWin;
        this.numberOfDraw = numberOfDraw;
        this.isOnline = isOnline;
        this.isPlaying = isPlaying;
        this.rank = rank;
    }
    
    public User(int ID, String nickname, boolean isOnline, boolean isPlaying) {
        this.ID = ID;
        this.nickname = nickname;
        this.isOnline = isOnline;
        this.isPlaying = isPlaying;
    }
    
    public User(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public User(String username, String password, String nickname) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getNumberOfGame() {
        return numberOfGame;
    }

    public void setNumberOfGame(int numberOfGame) {
        this.numberOfGame = numberOfGame;
    }

    public int getNumberOfWin() {
        return numberOfWin;
    }

    public void setNumberOfWin(int numberOfWin) {
        this.numberOfWin = numberOfWin;
    }

    public int getNumberOfDraw() {
        return numberOfDraw;
    }

    public void setNumberOfDraw(int numberOfDraw) {
        this.numberOfDraw = numberOfDraw;
    }
    
    public boolean getIsOnline() {
        return isOnline;
    }

    public void setIsOnline(boolean isOnline) {
        this.isOnline = isOnline;
    }

    public boolean getIsPlaying() {
        return isPlaying;
    }

    public void setIsPlaying(boolean isPlaying) {
        this.isPlaying = isPlaying;
    }
}
