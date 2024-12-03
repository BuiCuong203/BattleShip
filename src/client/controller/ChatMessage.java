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
public class ChatMessage implements Serializable {

    private String message;

    public ChatMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
