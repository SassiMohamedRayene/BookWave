package edu.gvsu.cis.bookwave.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.ai.client.generativeai.GenerativeModel
import com.google.ai.client.generativeai.type.content
import edu.gvsu.cis.bookwave.BuildConfig
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class ChatMessage(
    val content: String,
    val isUser: Boolean
)

class ChatbotViewModel : ViewModel() {

    private val _messages = MutableStateFlow<List<ChatMessage>>(emptyList())
    val messages: StateFlow<List<ChatMessage>> = _messages.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    // La clé API est maintenant chargée depuis BuildConfig
    private val generativeModel = GenerativeModel(
        modelName = "gemini-2.0-flash-exp",  // Changé de gemini-1.5-flash à gemini-pro
        apiKey = BuildConfig.GEMINI_API_KEY
    )

    private val chat = generativeModel.startChat(
        history = listOf(
            content(role = "user") {
                text("Tu es un assistant virtuel pour BookWave, une application d'audiolivres. Tu aides les utilisateurs à découvrir de nouveaux audiolivres, à obtenir des recommandations, et à répondre à leurs questions sur la littérature et les audiolivres. Sois amical, concis et utile.")
            },
            content(role = "model") {
                text("Bonjour! Je suis votre assistant BookWave. Je suis là pour vous aider à découvrir de merveilleux audiolivres et répondre à toutes vos questions sur la littérature. Que puis-je faire pour vous aujourd'hui?")
            }
        )
    )

    init {
        // Message de bienvenue
        _messages.value = listOf(
            ChatMessage(
                content = "Bonjour! 👋 Je suis votre assistant BookWave. Comment puis-je vous aider aujourd'hui?",
                isUser = false
            )
        )
    }

    fun sendMessage(userMessage: String) {
        viewModelScope.launch {
            try {
                // Ajouter le message de l'utilisateur
                _messages.value = _messages.value + ChatMessage(userMessage, true)
                _isLoading.value = true

                // Log pour déboguer
                println("🔑 API Key exists: ${BuildConfig.GEMINI_API_KEY.isNotEmpty()}")
                println("📤 Sending message: $userMessage")

                // Envoyer le message à Gemini
                val response = chat.sendMessage(userMessage)

                println("✅ Response received: ${response.text}")

                // Ajouter la réponse de l'AI
                _messages.value = _messages.value + ChatMessage(
                    content = response.text ?: "Désolé, je n'ai pas pu générer une réponse.",
                    isUser = false
                )
            } catch (e: Exception) {
                // Log l'erreur détaillée
                println("❌ Error: ${e.message}")
                println("❌ Error type: ${e::class.simpleName}")
                e.printStackTrace()

                _messages.value = _messages.value + ChatMessage(
                    content = "Erreur: ${e.message ?: "Une erreur s'est produite"}",
                    isUser = false
                )
            } finally {
                _isLoading.value = false
            }
        }
    }
}