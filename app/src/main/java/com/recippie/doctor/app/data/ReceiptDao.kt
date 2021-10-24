package com.recippie.doctor.app.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ReceiptDao {
    @Query("SELECT MAX(num_receipt) FROM receiptdata")
    fun getLastReceipt(): Int?

    @Query("SELECT * FROM receiptdata WHERE num_receipt = :lastReceipt")
    fun currentReceipt(lastReceipt: Int): List<ReceiptData>

    @Insert
    fun insertReceipt(vararg receipt: ReceiptData)

    @Delete
    fun delete(receipt: ReceiptData)
}