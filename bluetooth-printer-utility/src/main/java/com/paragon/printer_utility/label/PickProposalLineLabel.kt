package com.paragon.printer_utility.label

import android.os.*
import com.paragon.printer_utility.util.*
import kotlinx.parcelize.*

@Parcelize
data class PickProposalLineLabel(
    val taskNo: String?,
    val pickProposalNo: String?,
    val deadlineDate: String?,
    val deadlineTime: String?,
    val valZone: String?,
    val type: String?,
    val pvJobNo: String?,
    val productCode: String?,
    val quantity: Int?,
    val reference: String?,
    val customerName: String?,
    val palletCode: String?,
    val lotNo: String?,
    val pvCaseID: String?,
    val binId: String?,
    val zplTemplate: String?
) : Parcelable, Printable {

    override fun getVariables(): MutableMap<Int, String> {
        return HashMap<Int, String>().also {
            it[1] = taskNo ?: ""              //1 | pick proposal
            it[2] = pickProposalNo ?: ""      //2 | barcode task number
            it[3] = deadlineDate ?: ""        //3 | delivery date
            it[4] = deadlineTime ?: ""        //4 | delivery time
            it[5] = valZone ?: ""             //5 | val zone
            it[6] = type ?: ""                //6 | type
            it[7] = pvJobNo ?: ""             //7 | job number
            it[10] = productCode ?: ""        //10 | productCode
            it[8] = quantity.toString()       //8 | quantity
            it[9] = pickProposalNo ?: ""      //9 | pick proposal number
            it[11] = reference ?: ""          //11 | reference
            it[12] = customerName ?: ""       //12 | customer
            it[13] = palletCode ?: ""         //13 | barcode pallet
            it[14] = palletCode ?: ""         //14 | pallet
            it[15] = lotNo ?: ""              //15 | lot number
            it[16] = pvCaseID ?: ""           //16 | barcode caseID
            it[17] = binId ?: ""              //17 | bin
            it[18] = pvCaseID ?: ""           //18 | caseId
        }
    }

    override fun getTemplate(): String = zplTemplate ?: ""

}