package com.recippie.doctor.app.util

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.recippie.doctor.app.R
import com.recippie.doctor.app.adapter.ReceiptAdapter
import com.recippie.doctor.app.pojo.Receipt

class SwipeToDeleteCallback(context: Context, val adapter: ReceiptAdapter, val onDeleteReceipt: (Receipt) -> Unit) : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

    private val deleteIcon = ContextCompat.getDrawable(context, R.drawable.ic_delete_white_24)
    private val intrinsicWidth = deleteIcon?.intrinsicWidth
    private val intrinsicHeight = deleteIcon?.intrinsicHeight
    private val background = ColorDrawable()
    private val backgroundColor = Color.parseColor("#f44336")
    private val clearPaint = Paint().apply { xfermode = PorterDuffXfermode(PorterDuff.Mode.CLEAR) }


    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        return false
    }

    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float, actionState: Int, isCurrentlyActive: Boolean) {
        val itemView = viewHolder.itemView
        val itemHeight = itemView.bottom - itemView.top
        val isCanceled = dX == 0f && !isCurrentlyActive

        if (isCanceled) {
            clearCanvas(c, itemView.right + dX, itemView.top.toFloat(), itemView.right.toFloat(), itemView.bottom.toFloat())
            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
            return
        }

        // Draw the red delete background
        background.color = backgroundColor
        background.setBounds(itemView.right + dX.toInt(), itemView.top, itemView.right, itemView.bottom)
        background.draw(c)

        // Calculate position of delete icon
        if(intrinsicHeight != null &&  intrinsicWidth != null && deleteIcon != null) {
            val deleteIconTop = itemView.top + (itemHeight - intrinsicHeight) / 2
            val deleteIconMargin = (itemHeight - intrinsicHeight) / 2
            val deleteIconLeft = itemView.right - deleteIconMargin - intrinsicWidth
            val deleteIconRight = itemView.right - deleteIconMargin
            val deleteIconBottom = deleteIconTop + intrinsicHeight

            // Draw the delete icon
            deleteIcon.setBounds(deleteIconLeft, deleteIconTop, deleteIconRight, deleteIconBottom)
            deleteIcon.draw(c)
        }

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    private fun clearCanvas(c: Canvas?, left: Float, top: Float, right: Float, bottom: Float) {
        c?.drawRect(left, top, right, bottom, clearPaint)
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {

        val description = viewHolder.itemView.findViewById<View>(R.id.et_description) as EditText
        val each = viewHolder.itemView.findViewById<View>(R.id.et_each_time) as EditText
        val during = viewHolder.itemView.findViewById<View>(R.id.et_during_time) as EditText
        val numReceipt = viewHolder.itemView.findViewById<View>(R.id.tv_receipt_number) as TextView
        val numMedicine = viewHolder.itemView.findViewById<View>(R.id.tv_num_medicine) as TextView

        adapter.removeData(viewHolder.absoluteAdapterPosition)
        onDeleteReceipt(Receipt(
            numReceipt = if (numReceipt.text.isNotBlank()) numReceipt.text.toString().toLong() else null,
            description = description.text.toString(),
            eachTime = each.text.toString(),
            duringTime = during.text.toString(),
            numMedicine = if (numMedicine.text.isNotBlank()) numMedicine.text.toString().toInt() else 0
        ))
    }
}