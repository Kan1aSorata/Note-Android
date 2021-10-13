package com.fengyuhe.notebook

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

class NoteApplication : Application() {
    companion object {
        const val Setting = "Setting"
        var deleteConfirm:Boolean = false

        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        println("执行成功")
        context = applicationContext
        deleteConfirm = context.getSharedPreferences(Setting, Context.MODE_PRIVATE).getBoolean("boolean_deleteConfirm_state", false)
    }
}