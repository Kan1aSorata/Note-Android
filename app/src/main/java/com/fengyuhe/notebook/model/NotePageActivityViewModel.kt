package com.fengyuhe.notebook.model

import android.os.Build
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fengyuhe.notebook.NoteApplication
import com.fengyuhe.notebook.model.dao.AppDatabase
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.concurrent.thread

class NotePageActivityViewModel(private var noteReserved: Note) : ViewModel() {
    val isSaved = MutableLiveData<Boolean>()
    val note = MutableLiveData<Note>()

    init {
        note.value = noteReserved
        isSaved.value = true
    }

    fun executeSave() {
        thread {
            thread {
                val now = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
                    val current = LocalDateTime.now()
                    current.format(formatter).toString()
                }else{
                    Date(System.currentTimeMillis()).toString()
                }

                val zero:Long = 0
                if (note.value?.id != zero){
                    note.value?.update_time = now
                    AppDatabase.getDatabase(NoteApplication.context).noteDao().updateNote(note.value!!)
                }else{
                    note.value?.create_time = now
                    note.value?.update_time = now
                    val id = AppDatabase.getDatabase(NoteApplication.context).noteDao().insertNote(note.value)
                    note.value?.id = id
                }

                isSaved.postValue(true)
            }
        }
    }

    fun executeDelete() {
        thread {
            val zero:Long = 0
            if (note.value?.id != zero)
            {
                AppDatabase.getDatabase(NoteApplication.context).noteDao().deleteNote(note.value!!)
            }
        }
    }
}