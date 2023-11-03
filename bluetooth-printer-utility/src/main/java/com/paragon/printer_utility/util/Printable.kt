package com.paragon.printer_utility.util

interface Printable {

    fun getVariables(): MutableMap<Int, String>

    fun getTemplate(): String
}