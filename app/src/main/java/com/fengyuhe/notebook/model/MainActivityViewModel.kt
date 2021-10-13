package com.fengyuhe.notebook.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fengyuhe.notebook.NoteApplication
import com.fengyuhe.notebook.model.dao.AppDatabase
import kotlin.concurrent.thread

class MainActivityViewModel : ViewModel() {
    private var _noteList = ArrayList<Note>()
    val noteList : MutableLiveData<ArrayList<Note>> = MutableLiveData()

    init {
        noteList.value = _noteList
    }

    fun queryNoteList(){
        thread {
            _noteList.clear()
            _noteList.addAll(AppDatabase.getDatabase(NoteApplication.context).noteDao().loadAllNotes())
            noteList.postValue(noteList.value)
        }
    }
}