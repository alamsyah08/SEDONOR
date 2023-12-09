package com.example.sedonor

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.TextView
import com.example.sedonor.R

class ChatBubble(context: Context, message: String?, isFromUser: Boolean) : LinearLayout(context) {

    init {
        LayoutInflater.from(context).inflate(R.layout.chat_bubble, this, true)

        val messageTextView: TextView = findViewById(R.id.messageTextView)
        val bubbleLayout: LinearLayout = findViewById(R.id.bubbleLayout)

        messageTextView.text = message

        if (isFromUser) {
            // Set user's message to the right
            bubbleLayout.gravity = Gravity.END
            messageTextView.setBackgroundResource(R.drawable.user_bubble)
        } else {
            // Set chatbot's message to the left
            bubbleLayout.gravity = Gravity.START
            messageTextView.setBackgroundResource(R.drawable.chatbot_bubble)
        }
    }
}
