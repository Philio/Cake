package com.waracle.cakelist.ui.list

import android.os.Bundle
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.waracle.cakelist.R
import com.waracle.cakelist.di.viewModel
import com.waracle.cakelist.ui.base.BaseActivity
import kotlinx.android.synthetic.main.activity_list.*

class ListActivity : BaseActivity() {

    private val viewModel: ListViewModel by viewModel()

    private val adapter = CakeAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setSupportActionBar(toolbar)

        recycler_view.apply {
            layoutManager = LinearLayoutManager(this@ListActivity)
            adapter = this@ListActivity.adapter
        }

        swipe_refresh_layout.setOnRefreshListener {
            viewModel.refresh()
        }

        viewModel.cakes.observe(this, Observer {
            when (it) {
                Result.Loading -> {
                    showLoading(true)
                }
                is Result.Success -> {
                    showLoading(false)
                    adapter.submitList(it.cakes)
                }
                is Result.Error -> {
                    showLoading(false)
                    showError()
                }
            }
        })
    }

    private fun showLoading(loading: Boolean) {
        swipe_refresh_layout.isRefreshing = loading
    }

    private fun showError() {
        Snackbar.make(coordinator_layout, R.string.generic_error, Snackbar.LENGTH_INDEFINITE)
            .setAction(getString(R.string.retry)) { viewModel.refresh() }
            .show()
    }
}
