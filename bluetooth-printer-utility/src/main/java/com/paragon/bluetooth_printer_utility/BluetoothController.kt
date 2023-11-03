package com.paragon.bluetooth_printer_utility

import android.annotation.*
import android.bluetooth.*
import android.bluetooth.BluetoothDevice
import android.content.*
import android.content.Intent.*
import com.paragon.bluetooth_printer_utility.data.*

@SuppressLint("MissingPermission")
class BluetoothController(context: Context) {

    private val bluetoothAdapter by lazy {
        context.getSystemService(BluetoothManager::class.java)?.adapter
    }

    fun getPairedDevices(context: Context): List<MyBluetoothDevice> =
        if (bluetoothAdapter?.isEnabled == true) {
            bluetoothAdapter?.bondedDevices
                ?.map { it.toBluetoothDeviceData() } ?: emptyList()
        } else {
            val intent = Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE)
            intent.addFlags(FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
            emptyList()
        }
}

@SuppressLint("MissingPermission")
private fun BluetoothDevice.toBluetoothDeviceData(): MyBluetoothDevice {
    return MyBluetoothDevice(
        name = name,
        address = address
    )
}