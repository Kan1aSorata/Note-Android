package com.fengyuhe.notebook.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.fengyuhe.notebook.databinding.ActivityMainBinding
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.fengyuhe.notebook.R
import com.fengyuhe.notebook.activity.adapter.NoteCardAdapter
import com.fengyuhe.notebook.model.MainActivityViewModel
import com.fengyuhe.notebook.model.Note


class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainActivityViewModel
    private var mBinding: ActivityMainBinding? = null
    private var noteCardAdapter: NoteCardAdapter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mBinding!!.root)
        addToolBar()
        activityMain_AddButton()
        viewModel()
        activityMainRecyclerView()
    }

    override fun onStart() {
        super.onStart()
        viewModel.queryNoteList()
    }

    private fun addToolBar() {
        setSupportActionBar(findViewById(R.id.activityMain_Toolbar))
        mBinding!!.activityMainSwipeRefresh.isRefreshing = false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)
        return true
    }

    private fun activityMain_AddButton() {
        mBinding!!.activityMainAddButton.setOnClickListener {
            val note = Note(title = "", content = "")
            NotePageActivity.actionStart(this, note)
        }
    }

    private fun viewModel() {
        viewModel = ViewModelProvider(this)
            .get(MainActivityViewModel::class.java)

        viewModel.noteList.observe(this, Observer {
            noteCardAdapter?.notifyDataSetChanged()
            mBinding!!.activityMainSwipeRefresh.isRefreshing = false
        })
    }

    private fun activityMainRecyclerView() {
        val layoutManager = StaggeredGridLayoutManager(
            2,
            StaggeredGridLayoutManager.VERTICAL
        )
        mBinding!!.activityMainRecyclerView.layoutManager = layoutManager
        noteCardAdapter = NoteCardAdapter(this, viewModel.noteList.value!!)
        mBinding!!.activityMainRecyclerView.adapter = noteCardAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_search -> {
                SearchActivity.actionStart(this)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun activitySearch() {

    }

}