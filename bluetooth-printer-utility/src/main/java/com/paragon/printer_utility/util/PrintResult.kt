package com.paragon.printer_utility.util

sealed class PrintResult {
    data object Success : PrintResult()
    class Error(val errorMessage: String?) : PrintResult()
}