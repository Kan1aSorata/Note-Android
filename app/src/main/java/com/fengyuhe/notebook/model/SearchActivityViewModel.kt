package com.fengyuhe.notebook.model

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fengyuhe.notebook.NoteApplication
import com.fengyuhe.notebook.model.dao.AppDatabase
import kotlin.concurrent.thread

class SearchActivityViewModel: ViewModel() {
    private var _noteList = ArrayList<Note>()
    val noteList = MutableLiveData<ArrayList<Note>>()

    init {
        noteList.value = _noteList
    }

    fun queryNote(s: String) {
        thread {
            _noteList.clear()
            _noteList.addAll(AppDatabase.getDatabase(NoteApplication.context).noteDao().queryNoteByCondition(s))
            noteList.postValue(noteList.value)
        }
    }

    fun clear() {
        _noteList.clear()
        noteList.value = noteList.value
    }
}