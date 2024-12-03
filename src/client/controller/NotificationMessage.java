/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package client.controller;

import java.io.Serializable;

/**
 *
 * @author bhhoa
 */
public class NotificationMessage implements Serializable {


    private int code;
    private String[] text;

    public NotificationMessage(int code) {
        this.code = code;
    }

    public NotificationMessage(int code, String... text) {
        this.code = code;
        this.text = text;
    }

    public int getCode() {
        return code;
    }

    public String[] getText() {
        return text;
    }
}

