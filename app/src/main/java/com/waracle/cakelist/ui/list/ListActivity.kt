package com.waracle.cakelist.ui.list

import android.os.Bundle
import com.waracle.cakelist.R
import com.waracle.cakelist.di.viewModel
import com.waracle.cakelist.ui.base.BaseActivity

class ListActivity : BaseActivity() {

    private val viewModel: ListViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
    }
}
