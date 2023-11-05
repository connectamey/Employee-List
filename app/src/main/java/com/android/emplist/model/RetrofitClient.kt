package com.android.emplist.model

// Retrofit is a type-safe HTTP client for Android and Java.
import retrofit2.Retrofit
// GsonConverterFactory is a converter which uses Gson for JSON.
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Singleton object to initialize Retrofit instance.
 */
object RetrofitClient {

    // Lazy initialization of Retrofit, meaning it's created only when needed.
    private val retrofit by lazy {
        // Builder to create the Retrofit instance.
        Retrofit.Builder()
            // Base URL for the web service.
            .baseUrl("https://fetch-hiring.s3.amazonaws.com/")
            // Add converter factory to serialize objects.
            .addConverterFactory(GsonConverterFactory.create())
            // Build the Retrofit instance.
            .build()
    }

    // Lazy initialization for the API service interface.
    val api: ApiService by lazy {
        // Create an implementation of the API endpoints defined by the ApiService interface.
        retrofit.create(ApiService::class.java)
    }
}
