package com.recippie.doctor.app.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import java.text.SimpleDateFormat
import java.util.*

@Dao
interface ReceiptDao {
    // Falta agregar que compare la fecha por si hay datos mayores a la fecha actual
    @Query("SELECT EXISTS(SELECT * FROM receiptdata)")
    fun existReceipt(): Boolean

    @Query("SELECT MAX(num_receipt) FROM receiptdata")
    fun getLastReceipt(): Int

    @Query("SELECT * FROM receiptdata WHERE num_receipt = :lastReceipt")
    fun currentReceipt(lastReceipt: Int): List<ReceiptData>

    @Insert
    fun insertReceipt(vararg receipt: ReceiptData)

    @Delete
    fun delete(receipt: ReceiptData)
}