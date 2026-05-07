package com.mycompany.desktophotelreservationsystem;

public class Message {
    private String chat = "";
    private String content = "";
    private String sender = "";

    public Message() {
    }

    public Message(String chat, String content, String sender) {
        this.chat = chat;
        this.content = content;
        this.sender = sender;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return "Message{" +
                "chat='" + chat + '\'' +
                ", content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                '}';
    }
}
