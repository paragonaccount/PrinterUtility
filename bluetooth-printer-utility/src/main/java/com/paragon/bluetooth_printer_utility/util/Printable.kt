package com.paragon.bluetooth_printer_utility.util

interface Printable {

    fun getVariables(): MutableMap<Int, String>

    fun getTemplate(): String
}