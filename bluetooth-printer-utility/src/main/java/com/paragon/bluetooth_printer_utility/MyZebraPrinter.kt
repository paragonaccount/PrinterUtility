package com.paragon.bluetooth_printer_utility

import android.util.*
import com.zebra.sdk.comm.*
import com.zebra.sdk.printer.*

sealed class ZebraStatus {
    data object OK : ZebraStatus()
    data class Error(
        val reason: ErrorReason = ErrorReason.EXCEPTION,
        val message: String?
    ) : ZebraStatus()
}

enum class ErrorReason {
    EXCEPTION,
    HEAD_OPEN,
    HEAD_TOO_HOT,
    PAPER_OUT,
    RIBBON_OUT,
    RECEIVE_BUFFER_FULL,
    PAUSE
}

class MyZebraPrinter {
    private var macDebug: String = "TODO TEST MAC"

    fun getVariableFieldsFromZpl(
        zpl: String,
        mac: String? = macDebug,
    ): Array<out FieldDescriptionData> {

        var variables: Array<out FieldDescriptionData> = arrayOf()

        BluetoothConnection(mac).let { connection ->
            try {
                connection.open()
                val printer = ZebraPrinterFactory.getInstance(PrinterLanguage.ZPL, connection)
                variables = printer.getVariableFields(zpl)

            } catch (e: Exception) {
                Log.d(javaClass.name, "Exception: $e")
            }

            connection.close()

            variables.map {
                Log.d(javaClass.name, "${it.fieldNumber} | ${it.fieldName}")
            }

            return variables
        }

    }

    fun printFromZPL(
        zpl: String,
        mac: String? = macDebug,
        vars: MutableMap<Int, String>?,
        status: ZebraStatus.() -> Unit
    ) {

        BluetoothConnectionInsecure(mac).let { connection ->
            try {
                if (!connection.isConnected) {
                    connection.open()
                }
                ZebraPrinterFactory.getInstance(PrinterLanguage.ZPL, connection).let { printer ->
                    Thread.sleep(300)
                    if (printer.currentStatus.isReadyToPrint) {
                        val newZpl = migrateFromTemplateToConstant(zpl, vars)
                        connection.write(
                            newZpl.toByteArray(charset("UTF-8"))
                        ).apply {
                            Thread.sleep(300)
                            status(ZebraStatus.OK)
                        }
                    } else {
                        val message =
                            PrinterStatusMessages(printer.currentStatus).statusMessage?.joinToString { it }

                        val type: ErrorReason = when {
                            printer.currentStatus.isPaperOut -> {
                                ErrorReason.PAPER_OUT
                            }
                            printer.currentStatus.isPaused -> {
                                ErrorReason.PAUSE
                            }
                            printer.currentStatus.isReceiveBufferFull -> {
                                ErrorReason.RECEIVE_BUFFER_FULL
                            }

                            printer.currentStatus.isHeadTooHot -> {
                                ErrorReason.HEAD_TOO_HOT
                            }

                            printer.currentStatus.isHeadOpen -> {
                                ErrorReason.HEAD_OPEN
                            }

                            printer.currentStatus.isRibbonOut -> {
                                ErrorReason.RIBBON_OUT
                            }
                            else -> {
                                ErrorReason.EXCEPTION
                            }
                        }
                        status(ZebraStatus.Error(type, message))
                    }
                }
            } catch (e: Exception) {
                status(ZebraStatus.Error(message = e.message))
            } finally {
                if (connection.isConnected) {
                    Thread.sleep(300)
                    connection.close()
                }
            }
        }
    }

    private fun migrateFromTemplateToConstant(zpl: String, vars: MutableMap<Int, String>?): String {

        if (vars.isNullOrEmpty()) {
            return zpl
        }
        var newZpl = zpl
            .replace("\\^DFR(.*?)\\^FS".toRegex(), "")

        vars.forEach {
            val regex = "\\^FN${it.key}\"(.*?)\\^FS"
            val replaceValue = "^FD${it.value}^FS"
            newZpl = newZpl.replace(regex.toRegex(), replaceValue)
        }
        Log.d(javaClass.name, "Result: ${newZpl.replace("\n", "")}")

        return newZpl
    }

    fun getPrinterStatus(mac: String, status: (String) -> Unit) {
        BluetoothConnectionInsecure(mac).let { connection ->
            try {
                if (!connection.isConnected) {
                    connection.open()
                }
                ZebraPrinterFactory.getInstance(PrinterLanguage.ZPL, connection).let { printer ->
                    Thread.sleep(300)
                    if (printer.currentStatus.isReadyToPrint) {
                        status("isReadyToPrint")
                    } else {
                        val message =
                            PrinterStatusMessages(printer.currentStatus).statusMessage?.joinToString { it }

                        val type: ErrorReason = when {
                            printer.currentStatus.isPaperOut -> {
                                ErrorReason.PAPER_OUT
                            }
                            printer.currentStatus.isPaused -> {
                                ErrorReason.PAUSE
                            }
                            printer.currentStatus.isReceiveBufferFull -> {
                                ErrorReason.RECEIVE_BUFFER_FULL
                            }

                            printer.currentStatus.isHeadTooHot -> {
                                ErrorReason.HEAD_TOO_HOT
                            }

                            printer.currentStatus.isHeadOpen -> {
                                ErrorReason.HEAD_OPEN
                            }

                            printer.currentStatus.isRibbonOut -> {
                                ErrorReason.RIBBON_OUT
                            }
                            else -> {
                                ErrorReason.EXCEPTION
                            }
                        }
                        status(message ?: "!${type.name}!")
                    }
                }
            } catch (e: Exception) {
                status(e.message ?: "catchException")
            } finally {
                if (connection.isConnected) {
                    Thread.sleep(300)
                    connection.close()
                }
            }
        }
    }

}