/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client.controller;

import java.io.Serializable;
import client.model.Ship;

/**
 *
 * @author bhhoa
 */
public class MoveResponseMessage implements Serializable {

    private int x;
    private int y;
    private Ship shipSunk;
    private boolean hit;
    private boolean ownBoard;

    public MoveResponseMessage(int x, int y, boolean hit, boolean ownBoard) {
        this(x, y, null, hit, ownBoard);
    }

    public MoveResponseMessage(int x, int y, Ship shipSunk, boolean hit, boolean ownBoard) {
        this.x = x;
        this.y = y;
        this.shipSunk = shipSunk;
        this.hit = hit;
        this.ownBoard = ownBoard;
    }

    //Trả về ship đã bị chìm trong quá trình di chuyển.
    // Trả về null nếu việc di chuyển không dẫn đến chìm tàu.
    public Ship shipSank() {
        return this.shipSunk;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isHit() {
        return hit;
    }

    public boolean isOwnBoard() {
        return ownBoard;
    }

    public void setOwnBoard(boolean ownBoard) {
        this.ownBoard = ownBoard;
    }

}
