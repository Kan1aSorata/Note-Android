package com.fengyuhe.notebook.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.fengyuhe.notebook.NoteApplication
import com.fengyuhe.notebook.R
import com.fengyuhe.notebook.databinding.ActivityNotepageBinding
import com.fengyuhe.notebook.factory.NotePageActivityViewModelFactory
import com.fengyuhe.notebook.model.Note
import com.fengyuhe.notebook.model.NotePageActivityViewModel

class NotePageActivity : AppCompatActivity() {

    private var nBinding: ActivityNotepageBinding? = null
    lateinit var viewModel: NotePageActivityViewModel

    //单例
    companion object {
        fun actionStart(context: Context, note: Note) {
            val intent = Intent(context, NotePageActivity::class.java)
            intent.putExtra("note_data", note)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        nBinding = ActivityNotepageBinding.inflate(layoutInflater)
        setContentView(nBinding!!.root)
        viewModel(intent.getSerializableExtra("note_data") as Note)

        addToolBar()
        activityNotePageTitleText()
        activityNotePageContentText()
    }

    fun viewModel(note: Note) {
        viewModel = ViewModelProvider(this, NotePageActivityViewModelFactory(note)).get(
            NotePageActivityViewModel::class.java
        )
    }

    private fun addToolBar() {
        setSupportActionBar(findViewById(R.id.activityNotepage_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun activityNotePageTitleText() {
        nBinding!!.activityNotePageTitleText.setText(viewModel.note.value?.title)
        nBinding!!.activityNotePageTitleText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.note.value?.title = s.toString()
                viewModel.isSaved.value = false
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun activityNotePageContentText() {
        nBinding!!.activityNotePageContentText.setText(viewModel.note.value?.content)
        nBinding!!.activityNotePageContentText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                viewModel.note.value?.content = s.toString()
                viewModel.isSaved.value = false
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_notepage, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                onBackPressed()
            }

            R.id.toolbarNotePage_Save -> {
                viewModel.executeSave()
                Toast.makeText(this, "保存成功", Toast.LENGTH_SHORT).show()
            }

            R.id.toolbarNotePage_Delete -> {
                if (NoteApplication.deleteConfirm) {
                    AlertDialog.Builder(this).apply {
                        setTitle("删除")
                        setMessage("确认删除？")
                        setCancelable(false)
                        setPositiveButton("确认") { _, _ ->
                            viewModel.executeDelete()
                            finish()
                        }
                        setNegativeButton("删除") { _, _ ->
                        }
                        show()
                    }
                } else {
                    viewModel.executeDelete()
                    finish()
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        println(viewModel.isSaved.value!!)
        if (viewModel.isSaved.value!!) {
            finish()
        } else {
            AlertDialog.Builder(this).apply {
                setTitle("保存")
                setMessage("尚未保存，是否退出")
                setCancelable(false)
                setPositiveButton("确认") { _, _ ->
                    finish()
                }
                setNegativeButton("取消") { _, _ ->
                }
                show()
            }
        }
    }
}