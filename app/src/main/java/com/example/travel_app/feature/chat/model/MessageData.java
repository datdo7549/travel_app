package com.example.travel_app.feature.chat.model;


public class MessageData {
    private String uuid;
    private String senderId;
    private String senderName;
    private String message;
    private Long time;


    public MessageData() {}

    public MessageData(String uuid, String senderId, String senderName, String message, Long time) {
        this.uuid = uuid;
        this.senderId = senderId;
        this.senderName = senderName;
        this.message = message;
        this.time = time;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
    }

    public String getSenderName() {
        return senderName;
    }

    public void setSenderName(String senderName) {
        this.senderName = senderName;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Long getTime() {
        return time;
    }

    public void setTime(Long time) {
        this.time = time;
    }
}