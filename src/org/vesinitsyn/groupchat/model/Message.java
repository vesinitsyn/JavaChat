package org.vesinitsyn.groupchat.model;

import java.io.Serializable;

public class Message implements Serializable {

    /**
     * Sender's name.
     */
    private String sender;

    /**
     * Message body.
     */
    private String body;

    public Message(String sender, String body) {
        this.sender = sender;
        this.body = body;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    @Override
    public String toString() {
        return "Message{" +
                "sender='" + sender + '\'' +
                ", body='" + body + '\'' +
                '}';
    }
}
