package com.android.emplist.view.theme

// Import statements are self-explanatory: they bring in necessary modules and functionalities from the Android framework and Jetpack Compose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SmallFloatingActionButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModelProvider
import com.android.emplist.model.Employee
import com.android.emplist.model.EmployeeRepository
import com.android.emplist.view.theme.ui.EmplistTheme
import com.android.emplist.viewmodel.EmployeeViewModel
import com.android.emplist.viewmodel.EmployeeViewModelFactory
import com.sunnychung.lib.android.composabletable.ux.Table

// ViewModel instance to hold and manage UI-related data in a lifecycle-conscious way
private lateinit var viewModel: EmployeeViewModel

// MainActivity: The entry point of the app where the UI is set
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Repository initialization for fetching and managing employee data
        val employeeRepository = EmployeeRepository()

        // Factory for creating a ViewModel, necessary for passing additional parameters
        val factory = EmployeeViewModelFactory(employeeRepository)

        // Assigning the ViewModel to this activity
        viewModel = ViewModelProvider(this, factory)[EmployeeViewModel::class.java]

        // Define the UI content for this Activity
        setContent {
            // Apply the custom theme for the app which defines colors, typography, shapes, etc.
            EmplistTheme {
                // Surface is a container that uses the background color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    // Surface is a container that uses the background color from the theme
                    LaunchedEffect(Unit) {
                        viewModel.fetchEmployees()
                    }
                    // Column for laying out items vertically
                    Column(modifier = Modifier.fillMaxWidth()) {
                        // Composable function that creates the UI for displaying employees
                        EmployeesScreen(viewModel = viewModel)

                    }

                }
            }
        }
    }
}

// A composable function that displays the employees and filter buttons
@Composable
fun EmployeesScreen(viewModel: EmployeeViewModel) {

    // Collecting the latest employees list from the ViewModel
    val employeesState =
        viewModel.userFilteredEmployeeListToBeUsed.observeAsState(initial = emptyList()).value

    // Remembering the mutable state to update UI upon data change
    val employees = remember { mutableStateOf(employeesState) }

    // State to track which filter button is selected
    val selectedButton = remember { mutableIntStateOf(0) }

    // Side-effect to update employees list when the source of truth changes
    LaunchedEffect(Unit) {
        viewModel.userFilteredEmployeeListToBeUsed.observeForever { list ->
            employees.value = list
        }
    }

    // Organizing the screen in a vertical fashion
    Column(modifier = Modifier.fillMaxWidth()) {
        Spacer(modifier = Modifier.padding(2.dp))

        // A row to organize filter buttons horizontally
        Row(modifier = Modifier.fillMaxWidth()) {
            // A series of buttons to filter the employees list
            // When clicked, they instruct the ViewModel to apply the corresponding filter
            // and update the selectedButton state

            FilterButton(textToShow = "All Employees", onClick = {
                viewModel.applyFilter(listId = 0)
                selectedButton.intValue = 0
            })
            FilterButton(textToShow = "1", onClick = {
                viewModel.applyFilter(listId = 1)
                selectedButton.intValue = 1
            }


            )
            FilterButton(textToShow = "2", onClick = {
                viewModel.applyFilter(listId = 2)
                selectedButton.intValue = 2
            }

            )
            FilterButton(textToShow = "3", onClick = {
                viewModel.applyFilter(listId = 3)
                selectedButton.intValue = 3
            })
            FilterButton(textToShow = "4", onClick = {
                viewModel.applyFilter(listId = 4)
                selectedButton.intValue = 4
            })
        }
        Spacer(modifier = Modifier.padding(2.dp))

        // Reacting to the selectedButton state change
        LaunchedEffect(selectedButton) {

            println("Selected button is now: $selectedButton")
        }

        // Decide which employees list to show based on the selected button
        // ShowEmployeeTable is a custom composable that takes a list of employees and displays it in a tabular format
        when (selectedButton.intValue) {
            0 -> {
                ShowEmployeeTable(employees = employees)
            }

            1 -> {
                ShowEmployeeTable(employees = employees)
            }

            2 -> {
                ShowEmployeeTable(employees = employees)
            }

            3 -> {
                ShowEmployeeTable(employees = employees)
            }

            4 -> {
                ShowEmployeeTable(employees = employees)
            }
        }

    }
}

// Composable function to create a filter button
@Composable
fun FilterButton(onClick: () -> Unit, textToShow: String) {
    // Floating action button with a custom shape and padding
    // onClick defines what happens when the button is tapped
    SmallFloatingActionButton(
        modifier = Modifier.padding(all = 8.dp),
        onClick = { onClick() },
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.primaryContainer,
        shape = RoundedCornerShape(corner = CornerSize(16.dp)),
    ) {
        Text(modifier = Modifier.padding(all = 16.dp), text = textToShow)
    }
}

// Composable function to display a table of employees
@Composable
fun ShowEmployeeTable(employees: MutableState<List<Employee>>) {
    // Using a third-party library to create a table layout
    // rowCount and columnCount define the size of the table
    // Lambda expression defines how to populate each cell of the table
    Table(
        rowCount = employees.value.size,
        columnCount = 3
    ) { rowIndex, columnIndex ->
        if (employees.value[rowIndex].name.isNotBlank()
        ) {
            when (columnIndex) {
                0 -> {
                    if (rowIndex == 0) {
                        Box(
                            modifier = Modifier
                                .width(135.dp)
                                .background(
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(corner = CornerSize(0.dp))
                                )
                                .border(width = 1.dp, color = Color.Gray)
                        ) {
                            Text(
                                text = "ListID",
                                modifier = Modifier
                                    .padding(10.dp)
                                    .align(Alignment.Center)
                            )
                        }
                    } else {
                        Box(modifier = Modifier
                            .background(
                                color = Color.White,
                                shape = RoundedCornerShape(corner = CornerSize(0.dp))
                            )
                            .border(width = 1.dp, color = Color.Gray)
                            .clickable {
                                // do something wonderful
                            }
                        ) {
                            Text(
                                text = employees.value[rowIndex].listId.toString(),
                                modifier = Modifier
                                    .padding(10.dp)
                                    .align(Alignment.Center)
                            )
                        }
                    }


                }

                1 -> {
                    if (rowIndex == 0) {
                        Box(
                            modifier = Modifier
                                .width(135.dp)
                                .background(
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(corner = CornerSize(0.dp))
                                )
                                .border(width = 1.dp, color = Color.Gray)
                        ) {
                            Text(
                                text = "ID",
                                modifier = Modifier
                                    .padding(10.dp)
                                    .align(Alignment.Center)
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = Color.White,
                                    shape = RoundedCornerShape(corner = CornerSize(0.dp))
                                )
                                .border(width = 1.dp, color = Color.Gray)
                        ) {
                            Text(
                                text = employees.value[rowIndex].id.toString(),
                                modifier = Modifier
                                    .padding(10.dp)
                                    .align(Alignment.Center)
                            )
                        }
                    }
                }

                2 -> {
                    if (rowIndex == 0) {
                        Box(
                            modifier = Modifier
                                .width(135.dp)
                                .background(
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(corner = CornerSize(0.dp))
                                )
                                .border(width = 1.dp, color = Color.Gray)
                        ) {
                            Text(
                                text = "Name",
                                modifier = Modifier
                                    .padding(10.dp)
                                    .align(Alignment.Center)
                            )
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .background(
                                    color = Color.White,
                                    shape = RoundedCornerShape(corner = CornerSize(0.dp))
                                )
                                .border(width = 1.dp, color = Color.Gray)
                        ) {
                            Text(
                                text = employees.value[rowIndex].name,
                                modifier = Modifier
                                    .padding(10.dp)
                                    .align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }
    }
}


