package com.recippie.doctor.app.interfaces


interface IAdapter<T> {

    fun notifyItemChanged(position: Int)

    fun getItemAt(position: Int): T?
}