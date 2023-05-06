//IT21318320 - Silva T.U.D
package com.example.proplanner.models

//Model used for budgets
data class Budget(var projectName: String? = null, var totalBudget: Double? = null, var available: Double? = null, var categories: ArrayList<Category>? = null)

