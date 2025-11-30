📚 BookWave – Intelligent Audiobook Application

1. Project Overview
BookWave is a next-generation Android audiobook application built with Kotlin and Jetpack Compose, designed to redefine how readers discover, consume, and interact with books.
 It blends the power of AI, modern UI, and cloud technologies to deliver an engaging, intelligent, and community-driven reading experience.
At its core, BookWave is an audiobook player, offering a smooth, modern interface inspired by popular streaming platforms. But the application goes far beyond simple playback—BookWave aims to become a complete digital reading ecosystem:
🎧 Audiobook Experience
Users can listen to their favorite titles through a sleek and intuitive player.
 The audio interface is optimized for simplicity, accessibility, and a premium user experience.
🤖 AI-Powered Literary Assistant
Using Gemini API, BookWave includes a built-in AI chatbot capable of:
Generating book summaries in seconds


Recommending titles tailored to the user’s interests


Creating themed lists (e.g., “Top 10 fantasy books of all time”)


Answering questions about books, genres, authors, and more


This transforms BookWave into a smart reading companion, not just an audiobook player.
💬 Reader Community & Messaging
BookWave integrates a miniature social layer where readers can:
Send and receive direct messages


Discuss books and share recommendations


This feature makes BookWave not only about consuming content, but also about connecting people through literature.
🙋 Personalized User Profile
Every user has a customizable profile with:
Profile picture


Personal library


🎯 Project Goal
The mission of BookWave is to merge:
The usability of modern streaming platforms


The intelligence of AI assistants


The engagement of social networks


…into a single, elegant mobile application centered around books and audiobooks.

2. Cloud Services Used
BookWave leverages several cloud services to ensure real-time synchronization, scalability, and a seamless user experience.
🔹 Gemini API (Google AI Studio)
Powers the AI chatbot


Creates summaries, recommendations, and answers questions


Enhances personalization based on user data


🔹 Firebase Authentication
Handles login & registration (email/password)


Creates a unique userId for every user


Acts as the primary identifier for all other services


🔹 Firebase Firestore
Stores all user-related and community data:
userId


name, email


private messages (DM)


🔹 Firebase Storage
Used to store media assets such as:
Profile pictures
Files are named using the userId, ensuring perfect consistency between Storage and Firestore.

🔹 Firebase Cloud Messaging (FCM)
Provides real-time push notifications for:
New book releases or updates


General app alerts and engagement events






3. How the Cloud Services Interact
The cloud architecture is built around a central identifier: userId from Firebase Authentication.
🔑 Authentication → Identity Link
Every registered user receives a unique userId which is used consistently across:
Firestore documents


Storage file paths


Notification targets


AI personalization data


🔗 Firestore ↔ Storage Integration
Firestore stores user metadata such as name and profileImageURL


Storage stores the actual image as /profiles/{userId}.jpg


The URL stored in Firestore points to the image in Storage


This ensures:
No duplicated data


Quick retrieval


Clean, scalable file organization





4. How to Setup / Install / Run BookWave
Follow these steps to run the project locally.
✅ Prerequisites
Android Studio Narval or newer


Kotlin + Jetpack Compose


Firebase account


Gemini API key from Google Cloud Platform


Active internet connection


Step 1 — Clone the Repository
git clone https://github.com/SassiMohamedRayene/BookWave.git

link : https://github.com/SassiMohamedRayene/BookWave
Step 2 — Configure Firebase
Open Firebase Console


Accept the project invitation (if invited by me)


Add Android App → enter package name


Add SHA-1 & SHA-256 keys


Download google-services.json


Place it in:


app/src/main/

Enable the required Firebase services:


Authentication → Email/Password


Firestore Database


Storage


Cloud Messaging (FCM)



Step 3 — Add Gemini API Key
Edit your local.properties:
GEMINI_API_KEY=your_api_key_here

Never hardcode API keys in source code.

Step 4 — Install Dependencies
Android Studio will sync Gradle automatically.
 Ensure the following libraries are included:
Firebase BOM


Authentication


Firestore


Storage


Cloud Messaging


Jetpack Compose libraries








Step 5 — Run the App
Open project in Android Studio


Choose an emulator


Click Run


The app will:
Connect to Firebase


Load user information


Display personalized home screen


Activate the AI chatbot


Enable messaging and notifications



