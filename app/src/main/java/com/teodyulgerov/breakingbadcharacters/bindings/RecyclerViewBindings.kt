package com.teodyulgerov.breakingbadcharacters.bindings

import androidx.databinding.BindingAdapter
import androidx.databinding.ObservableArrayList
import androidx.recyclerview.widget.RecyclerView
import com.google.android.flexbox.FlexDirection
import com.google.android.flexbox.FlexWrap
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.flexbox.JustifyContent
import com.teodyulgerov.breakingbadcharacters.adapter.base.BaseRecyclerAdapter
import com.teodyulgerov.breakingbadcharacters.adapter.overview.OverviewRecyclerAdapter
import com.teodyulgerov.breakingbadcharacters.ui.fragment.base.BaseVM
import com.teodyulgerov.breakingbadcharacters.ui.fragment.overview.OverviewVM

@BindingAdapter("viewModel", "items", "itemLayoutResId", requireAll = true)
fun <T> bindRecyclerView(
    recyclerView: RecyclerView,
    viewModel: BaseVM,
    items: ObservableArrayList<T>,
    itemLayoutResId: Int
) {
    if (recyclerView.layoutManager == null) {
        val layoutManager = FlexboxLayoutManager(recyclerView.context)
        layoutManager.flexDirection = FlexDirection.ROW
        layoutManager.flexWrap = FlexWrap.WRAP
        layoutManager.justifyContent = JustifyContent.SPACE_EVENLY
        recyclerView.layoutManager = layoutManager
    }

    if (recyclerView.adapter == null) {
        if (viewModel is OverviewVM) {
            recyclerView.adapter = OverviewRecyclerAdapter(viewModel, items, itemLayoutResId)
        }
    } else {
        (recyclerView.adapter as BaseRecyclerAdapter<T>).setItems(items)
    }
}