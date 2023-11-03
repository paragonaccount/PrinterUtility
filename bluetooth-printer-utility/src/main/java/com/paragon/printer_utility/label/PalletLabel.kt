package com.paragon.printer_utility.label

import android.os.*
import com.paragon.printer_utility.util.*
import kotlinx.parcelize.*

@Parcelize
data class PalletLabel(
    var palletNumber: String? = null,
    val productCode: String?,
    val productDescription: String?,
    val customerName: String?,
    val icEndCustomerName: String?,
    val qty: Int?,
    val binId: String?,
    val lotNumber: String?,
    val itemCode: String?,
    val companyName: String?,
    val productDescription2: String?,
    val qtyPerBox: Int?,
    val ecoLabelCode: String?,
    val itemPrintCode: String?,
    val weightTotal: Int?,
    val depth: String?,
    val weight: String?,
    val width: String?,
    val thickness: String?,
    val receiptDate: String?,
    val zplTemplate: String?
) : Parcelable, Printable {
    override fun getVariables(): MutableMap<Int, String> {
        return HashMap<Int, String>().also {
            it[1] = customerName ?: ""              // customer (50)
            it[2] = palletNumber ?: ""              // internal pallet number
            it[3] = palletNumber ?: ""              // internal pallet number (9)
            it[4] = receiptDate ?: ""               // date reception (date)
            it[5] = itemCode ?: ""                  // item code (20)
            it[6] = productDescription ?: ""        // item description (50)
            it[7] = productDescription2 ?: ""       // item description 2 (50)
            it[8] = itemCode ?: ""                  // item code
            it[9] = productCode ?: ""               // product code customer (20)
            it[19] = ecoLabelCode ?: ""             // eco label code (20)
            it[10] = itemPrintCode ?: ""            // print code (35)
            it[11] = qty?.toString() ?: ""          // qty (decimal)
            it[12] = weight ?: ""                   // total weight (decimal)
            it[13] = lotNumber ?: ""                // lot number (20)
            it[14] = depth ?: ""                    // depth (decimal)
            it[15] = width ?: ""                    // width (decimal)
            it[16] = weightTotal?.toString() ?: ""  // weight (decimal)
            it[17] = thickness ?: ""                // thickness (decimal)
            it[18] = icEndCustomerName ?: ""
        }
    }

    override fun getTemplate(): String = zplTemplate ?: ""
}