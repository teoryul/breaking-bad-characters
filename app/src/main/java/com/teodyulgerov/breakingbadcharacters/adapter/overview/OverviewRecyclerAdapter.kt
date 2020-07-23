package com.teodyulgerov.breakingbadcharacters.adapter.overview

import androidx.databinding.ObservableArrayList
import com.teodyulgerov.breakingbadcharacters.adapter.base.BaseRecyclerAdapter
import com.teodyulgerov.breakingbadcharacters.ui.fragment.base.BaseVM

class OverviewRecyclerAdapter<T>(
    viewModel: BaseVM,
    items: ObservableArrayList<T>,
    private val layoutResId: Int
) : BaseRecyclerAdapter<T>(viewModel, items) {
    override fun getLayoutResId(): Int = layoutResId
}