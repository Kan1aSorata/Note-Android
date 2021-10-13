package com.fengyuhe.notebook.factory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.fengyuhe.notebook.model.Note
import com.fengyuhe.notebook.model.NotePageActivityViewModel

class NotePageActivityViewModelFactory(private val note: Note) : ViewModelProvider.Factory {

    //将 countReserved 参数传给 MainViewModel 实例
    override fun <T : ViewModel> create(modelClass: Class<T>): T {

        //这里可以创建 MainViewModel 的实例，因为 create()方法的执行时机和 Activity 的生命周期无关，所以不会产生之前提到的问题。
        return NotePageActivityViewModel(note) as T
    }

}