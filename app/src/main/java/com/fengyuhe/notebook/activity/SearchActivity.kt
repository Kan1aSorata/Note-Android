package com.fengyuhe.notebook.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.widget.LinearLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import cn.nnn.notebook.ui.search.NoteListAdapter
import com.fengyuhe.notebook.R
import com.fengyuhe.notebook.databinding.ActivitySearchBinding
import com.fengyuhe.notebook.model.Note
import com.fengyuhe.notebook.model.SearchActivityViewModel

class SearchActivity : AppCompatActivity() {

    private lateinit var viewModel: SearchActivityViewModel
    private var noteListAdapter: NoteListAdapter? = null
    private var sBinding: ActivitySearchBinding? = null

    //单例
    companion object {
        fun actionStart(context: Context) {
            val intent = Intent(context, SearchActivity::class.java)
            context.startActivity(intent)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        sBinding = ActivitySearchBinding.inflate(layoutInflater)
        setContentView(sBinding!!.root)

        toolBar()
        viewModel()
        activitySearch_RecyclerView()
        activitySearch_Clear()
        activitySearch_Search()
    }

    private fun viewModel() {
        viewModel = ViewModelProvider(this).get(SearchActivityViewModel::class.java)
        viewModel.noteList.observe(this, Observer {
            noteListAdapter?.notifyDataSetChanged()
        })
    }

    private fun toolBar() {
        setSupportActionBar(sBinding?.activitySearchToolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun activitySearch_RecyclerView() {
        val layoutManager = LinearLayoutManager(this)
        sBinding!!.activitySearchRecyclerView.layoutManager = layoutManager
        noteListAdapter = NoteListAdapter(this, viewModel.noteList.value!! as List<Note>)
        sBinding!!.activitySearchRecyclerView.adapter = noteListAdapter
    }

    private fun activitySearch_Clear() {
        sBinding!!.activitySearchClear.setOnClickListener {
            sBinding!!.activitySearchSearch.setText("")
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> {
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun activitySearch_Search() {
        sBinding!!.activitySearchSearch.addTextChangedListener(object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString() != "") {
                    viewModel.queryNote(p0.toString())
                } else {
                    viewModel.clear()
                }
            }
        })
    }

}