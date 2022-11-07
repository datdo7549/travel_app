package com.example.travel_app.feature.chat.model

data class MessageData(val uuid: String, val senderId : String, val senderName : String, val message: String, val time: Long) {
    constructor() : this("", "", "","", 0)
}