package com.android.emplist.model

import retrofit2.http.GET

/**
 * An interface for Retrofit to define HTTP operations as Kotlin functions.
 * Retrofit turns your HTTP API into a Kotlin interface.
 */
interface ApiService {

    /**
     * An HTTP GET method to retrieve a list of employees.
     * The `suspend` keyword indicates that this is a suspending function which can be paused and resumed at a later time.
     * This function can be called within a coroutine or another suspending function.
     *
     * @return A list of [Employee] objects retrieved from the API endpoint.
     */
    @GET("hiring.json") // Annotation that denotes the endpoint for the GET request.
    suspend fun getEmployees(): List<Employee> // A function to fetch employee data that returns a list of Employee objects.
}
