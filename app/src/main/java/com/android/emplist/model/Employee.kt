package com.android.emplist.model

/**
 * A data class in Kotlin that represents an employee.
 * Data classes automatically provide equals(), hashCode(), toString(), and copy() methods.
 *
 * @property id The unique identifier for an employee.
 * @property listId The identifier for the list to which this employee belongs.
 * @property name The name of the employee.
 */
data class Employee(
    val id: Int,        // Unique identifier for the employee, typically assigned by the database.
    val listId: Int,    // Represents the list ID that this employee is part of.
    val name: String,   // The name of the employee.
)
