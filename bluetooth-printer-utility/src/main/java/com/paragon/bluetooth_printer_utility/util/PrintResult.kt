package com.paragon.bluetooth_printer_utility.util

sealed class PrintResult {
    data object Success : PrintResult()
    class Error(val errorMessage: String?) : PrintResult()
}