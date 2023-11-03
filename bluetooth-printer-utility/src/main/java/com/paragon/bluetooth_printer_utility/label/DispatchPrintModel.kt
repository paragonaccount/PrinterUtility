package com.paragon.bluetooth_printer_utility.label

import android.os.*
import com.paragon.bluetooth_printer_utility.util.*
import kotlinx.parcelize.*

@Parcelize
data class DispatchPrintModel(
    val barcode: String?,
    val postalProvider: String?,
    val mailDate: String?,
    val zplTemplate: String?
) : Parcelable, Printable {

    override fun getVariables(): MutableMap<Int, String> {
        return HashMap<Int, String>().also {
            it[1] = barcode         ?: ""       // 1 | number
            it[2] = barcode         ?: ""       // 2 | barcode
            it[3] = postalProvider  ?: ""       // 3 | postal provider
            it[4] = mailDate        ?: ""       // 4 | mail date
        }
    }

    override fun getTemplate(): String = zplTemplate ?: ""
}


