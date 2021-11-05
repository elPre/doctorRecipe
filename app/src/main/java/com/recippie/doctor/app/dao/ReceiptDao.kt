package com.recippie.doctor.app.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.recippie.doctor.app.data.ReceiptData

@Dao
interface ReceiptDao {
    // Falta agregar que compare la fecha por si hay datos mayores a la fecha actual
    @Query("SELECT EXISTS(SELECT * FROM receiptdata)")
    suspend fun existReceipt(): Boolean

    @Query("SELECT MAX(num_receipt) FROM receiptdata")
    suspend fun getLastReceipt(): Int?

    @Query("SELECT * FROM receiptdata WHERE num_receipt = :lastReceipt")
    suspend fun currentReceipt(lastReceipt: Int): List<ReceiptData>

    @Insert
    suspend fun insertReceipt(vararg receipt: ReceiptData)

    @Delete
    suspend fun deleteReceipt(receipt: ReceiptData)
}