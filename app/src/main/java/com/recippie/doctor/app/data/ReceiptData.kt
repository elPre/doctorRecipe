package com.recippie.doctor.app.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "receiptdata")
data class ReceiptData(
    @ColumnInfo(name = "num_medicine") @PrimaryKey (autoGenerate=true) val numMedicine: Int,
    @ColumnInfo(name = "num_receipt") val numReceipt: Int,
    @ColumnInfo(name = "medicine") val description: String,
    @ColumnInfo(name = "each_time") val eachTime: String,
    @ColumnInfo(name = "during_time") val duringTime: String
)
