package com.android.emplist.model

// EmployeeRepository acts as a data layer which abstracts the data sources from the rest of the app.
class EmployeeRepository {

    // A suspend function that can be paused and resumed at a later time.
    // This function fetches a list of employees from a remote data source.
    suspend fun getEmployees(): List<Employee> {
        // Access the RetrofitClient to get the ApiService instance and call the getEmployees method.
        // This method is a suspend function and must be called from a coroutine or another suspend function.
        return RetrofitClient.api.getEmployees()
    }
}
