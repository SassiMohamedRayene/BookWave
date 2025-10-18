package edu.gvsu.cis.bookwave.viewmodel

import androidx.lifecycle.ViewModel
import edu.gvsu.cis.bookwave.data.model.User

class UserViewModel : ViewModel() {

    // Utilisateur actuellement connecté
    var currentUser: User? = null
        private set

    /**
     * Inscription d'un nouvel utilisateur
     */
    fun registerUser(id: Int, username: String, email: String, password: String, profileImageUrl: String? = null) {
        currentUser = User(
            id = id,
            username = username,
            email = email,
            password = password,
            profileImageUrl = profileImageUrl
        )
    }

    /**
     * Connexion d'un utilisateur
     */
    fun loginUser(email: String, password: String): Boolean {
        return currentUser?.let {
            it.email == email && it.password == password
        } ?: false
    }

    /**
     * Déconnexion
     */
    fun logoutUser() {
        currentUser = null
    }
}
