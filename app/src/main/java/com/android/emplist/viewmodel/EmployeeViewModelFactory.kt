package com.android.emplist.viewmodel

// Import statements for ViewModel classes.
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.android.emplist.model.EmployeeRepository

/**
 * Factory for creating instances of EmployeeViewModel.
 * ViewModelProvider.Factory is an interface to create ViewModels.
 */
class EmployeeViewModelFactory(private val repository: EmployeeRepository) :
    ViewModelProvider.Factory {

    /**
     * Creates a new instance of the given `Class`.
     * @param modelClass a `Class` whose instance is requested
     * @return a newly created ViewModel
     */
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        // Check if the ViewModel we are trying to create is EmployeeViewModel.
        if (modelClass.isAssignableFrom(EmployeeViewModel::class.java)) {
            // If it's EmployeeViewModel, then create and return its instance.
            @Suppress("UNCHECKED_CAST")
            return EmployeeViewModel(repository) as T
        }
        // If it's not an EmployeeViewModel, then throw an exception.
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
