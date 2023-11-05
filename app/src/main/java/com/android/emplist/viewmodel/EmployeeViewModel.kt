package com.android.emplist.viewmodel

// Necessary imports for Android logging, LiveData, ViewModel, etc.
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.emplist.model.Employee
import com.android.emplist.model.EmployeeRepository
import kotlinx.coroutines.launch

// EmployeeViewModel extends ViewModel. It's used to store and manage UI-related data in a lifecycle-conscious way.
class EmployeeViewModel(private val repository: EmployeeRepository) : ViewModel() {

    // The '_employees' MutableLiveData to hold the list of employees. It's private because we expose only the immutable LiveData.
    private val _employees = MutableLiveData<List<Employee>>()

    // Similarly, '_userFilteredEmployeeList' holds the filtered list of employees.
    private val _userFilteredEmployeeList = MutableLiveData<List<Employee>>()

    // 'userFilteredEmployeeListToBeUsed' is another LiveData exposed to the UI for observing the filtered list.
    val userFilteredEmployeeListToBeUsed: LiveData<List<Employee>> = _userFilteredEmployeeList

    // Initializer block that fetches the employees as soon as the ViewModel is created.
    init {
        fetchEmployees()
    }

    // 'fetchEmployees' method fetches the employee data from the repository within a coroutine.
    fun fetchEmployees() {
        // 'viewModelScope' is used to launch coroutines in the ViewModel.
        viewModelScope.launch {
            try {
                // Getting the employee list from the repository.
                val employeeList = repository.getEmployees()

                // Filtering out employees with blank or null names.
                val filteredEmployees = employeeList.filter { it.name?.isNotBlank() == true }

                // Sorting the list first by listId and then by name.
                val sortedEmployees = filteredEmployees
                    .sortedWith(compareBy<Employee> { it.listId }.thenComparing(nameComparator))

                // Updating the LiveData with the sorted list of employees.
                _employees.value = sortedEmployees
                _userFilteredEmployeeList.value = sortedEmployees
            } catch (e: Exception) {
                // Logging errors if the fetch operation fails.
                Log.e("Error", "Failed to fetch employees: ${e.message}")
            }
        }
    }

    // 'applyFilter' method is used to filter the employee list based on the 'listId'.
    fun applyFilter(listId: Int) {
        // Getting the current list of employees, or an empty list if null.
        val currentList = _employees.value ?: emptyList()

        // If 'listId' is 0, it implies no filtering, so the whole list is set.
        // Otherwise, the list is filtered by the given 'listId'.
        _userFilteredEmployeeList.value = if (listId == 0) {
            currentList
        } else {
            currentList.filter { it.listId == listId }
        }
    }

}

// Custom comparator function that extracts the numerical part of the name and compares it
private val nameComparator = Comparator<Employee> { e1, e2 ->
    // Regex pattern to find numbers in the name string
    val numberRegex = """\d+""".toRegex()

    // Extracting numbers from names or null if no number is present
    val number1 = numberRegex.find(e1.name)?.value?.toIntOrNull()
    val number2 = numberRegex.find(e2.name)?.value?.toIntOrNull()

    // When both names have numbers, compare them numerically
    if (number1 != null && number2 != null) {
        number1.compareTo(number2)
    } else {
        // If one or both names don't have numbers, fall back to normal string comparison
        e1.name.compareTo(e2.name)
    }
}