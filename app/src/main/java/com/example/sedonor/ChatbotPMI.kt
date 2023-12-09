package com.example.sedonor

import androidx.appcompat.app.AppCompatActivity
import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import okhttp3.MediaType
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

//
data class OpenAiRequest(
    val model: String,
    val messages: List<Message>,
    val temperature: Double
)

data class Message(
    val role: String,
    val content: String
)

data class OpenAiResponse(
    val id: String,
    val `object`: String,
    val created: Long,
    val model: String,
    val usage: Usage,
    val choices: List<Choice>
)

data class Usage(
    val prompt_tokens: Int,
    val completion_tokens: Int,
    val total_tokens: Int
)

data class Choice(
    val message: Message,
    val finish_reason: String,
    val index: Int
)

interface OpenAiApi {
    @POST("https://api.openai.com/v1/chat/completions")
    fun getGpt3Response(
        @Header("Authorization") authorization: String,
        @Body requestBody: OpenAiRequest
    ): Call<OpenAiResponse>
}

class ChatbotPMI : AppCompatActivity() {

    private lateinit var conversationLayout: LinearLayout
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_chatbot_pmi)

        conversationLayout = findViewById(R.id.conversationLayout)

        val submitButton = findViewById<Button>(R.id.buttonSubmit)
        submitButton.setOnClickListener {
            val userInput = findViewById<EditText>(R.id.editTextUserInput).text.toString()
            addMessageToConversation(userInput, true) // Add user's question to conversation
            getGpt3Response(userInput)
        }
    }
    private fun showProcessingDialog() {
        progressDialog = ProgressDialog(this)
        progressDialog.setMessage("Sedang memproses pertanyaan...")
        progressDialog.setCancelable(false)
        progressDialog.show()
    }

    private fun dismissProcessingDialog() {
        if (progressDialog.isShowing) {
            progressDialog.dismiss()
        }
    }

    private fun getGpt3Response(userInput: String) {
        showProcessingDialog()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.openai.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(
                OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS) // Set connection timeout to 60 seconds
                    .readTimeout(60, TimeUnit.SECONDS) // Set read timeout to 60 seconds
                    .build()
            )
            .build()

        val openAiApi = retrofit.create(OpenAiApi::class.java)

        val apiKey = ""  // Replace with your actual API KEY
        val authorizationHeader = "Bearer $apiKey"

        val request = OpenAiRequest(
            model = "gpt-3.5-turbo",
            messages = listOf(Message(role = "user", content = userInput)),
            temperature = 0.7
        )

        var responseCount = 0
        val runnable = Runnable {
            openAiApi.getGpt3Response(authorizationHeader, request)
                .enqueue(object : Callback<OpenAiResponse> {
                    override fun onResponse(call: Call<OpenAiResponse>, response: Response<OpenAiResponse>) {
                        dismissProcessingDialog()
                        if (response.isSuccessful) {
                            val gpt3Answer = response.body()?.choices?.get(0)?.message?.content
                            runOnUiThread {
                                displayAnswer(gpt3Answer)
                                resetInputField()
                            }
                            responseCount++
                            if (responseCount >= 3) {
                                // Stop after reaching response limit
                                Log.i("Responses", "Total responses: $responseCount")
                            }
                        } else {
                            // Handle errors
                            Log.e("API Error", "Error: ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<OpenAiResponse>, t: Throwable) {
                        // Handle connection or request failures
                        Log.e("API Failure", "Failure: ${t.message}")
                        dismissProcessingDialog()
                    }
                })
        }

        Thread(runnable).start()
    }

    private fun resetInputField() {
        val userInputEditText = findViewById<EditText>(R.id.editTextUserInput)
        userInputEditText.text.clear()
    }

    private fun displayAnswer(answer: String?) {
        // Display chat bubble for chatbot's response
        addMessageToConversation(answer, false)
    }


    private fun addMessageToConversation(message: String?, isFromUser: Boolean) {
        // Create and add chat bubble to the conversation layout
        val inflater = LayoutInflater.from(this)
        val chatBubbleLayout = inflater.inflate(R.layout.chat_bubble, null) as LinearLayout
        val messageTextView = chatBubbleLayout.findViewById<TextView>(R.id.messageTextView)

        // Set the message text and background color based on the sender
        messageTextView.text = message
        if (isFromUser) {
            // Align user's message to the right
            messageTextView.setBackgroundResource(R.drawable.user_bubble)
            val params = LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            params.gravity = Gravity.END
            chatBubbleLayout.layoutParams = params
        } else {
            // Align chatbot's message to the left
            messageTextView.setBackgroundResource(R.drawable.chatbot_bubble)
        }

        conversationLayout.addView(chatBubbleLayout)
    }


    fun intentKeHome(view: View) {
        // Ini akan dipanggil ketika tombol "Lihat semua" ditekan
        val intent = Intent(this, HomePage::class.java)
        startActivity(intent)
    }
    //checkpoint push
}




