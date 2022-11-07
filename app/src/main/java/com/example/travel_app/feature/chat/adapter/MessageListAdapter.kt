package com.example.travel_app.feature.chat.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.travel_app.R
import com.example.travel_app.feature.chat.model.MessageData
import java.text.SimpleDateFormat
import java.util.*


private var previousDate: Long = 0

class ReceivedMessageHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    @SuppressLint("CutPasteId")
    fun bind(messageData: MessageData) {
        val date = itemView.findViewById<TextView>(R.id.text_chat_date_other)
        date.text = getDate(messageData.time, "dd/MM/yyyy")
        itemView.findViewById<TextView>(R.id.text_chat_user_other).text = messageData.senderName
        itemView.findViewById<TextView>(R.id.text_chat_message_other).text = messageData.message
        itemView.findViewById<TextView>(R.id.text_chat_timestamp_other).text =
            getDate(messageData.time, "hh:mm")
    }
}

class SentMessageHolder(itemView: View) :
    RecyclerView.ViewHolder(itemView) {
    @SuppressLint("CutPasteId")
    fun bind(messageData: MessageData) {
        val date = itemView.findViewById<TextView>(R.id.text_chat_date_me)
        date.text = getDate(messageData.time, "dd/MM/yyyy")
        itemView.findViewById<TextView>(R.id.text_chat_message_me).text = messageData.message
        itemView.findViewById<TextView>(R.id.text_chat_timestamp_me).text =
            getDate(messageData.time, "hh:mm")
    }
}

class MessageListAdapter(var messages: MutableList<MessageData>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        val messageData = messages[position]
        return if (messageData.senderId == "1234") {
            VIEW_TYPE_MESSAGE_SENT
        } else {
            VIEW_TYPE_MESSAGE_RECEIVED
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        if (viewType == VIEW_TYPE_MESSAGE_SENT) {
            SentMessageHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_message_right, parent, false)
            )
        } else {
            ReceivedMessageHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_message_left, parent, false)
            )
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val messageData = messages[position]
        when (holder.itemViewType) {
            VIEW_TYPE_MESSAGE_SENT -> (holder as SentMessageHolder).bind(messageData)
            VIEW_TYPE_MESSAGE_RECEIVED -> (holder as ReceivedMessageHolder).bind(messageData)
        }
        previousDate = messageData.time
    }

    override fun getItemCount(): Int = messages.size

}

fun getDate(millisecond: Long, formatType: String): String {
    val formatter = SimpleDateFormat(formatType, Locale.US)
    val calendar = Calendar.getInstance()
    calendar.timeInMillis = millisecond
    return formatter.format(calendar.time)
}

const val VIEW_TYPE_MESSAGE_SENT = 1
const val VIEW_TYPE_MESSAGE_RECEIVED = 2