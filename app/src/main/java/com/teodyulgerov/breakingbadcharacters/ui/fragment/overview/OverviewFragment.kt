package com.teodyulgerov.breakingbadcharacters.ui.fragment.overview

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import com.google.android.flexbox.FlexboxLayoutManager
import com.teodyulgerov.breakingbadcharacters.R
import com.teodyulgerov.breakingbadcharacters.databinding.FragmentOverviewBinding
import com.teodyulgerov.breakingbadcharacters.ui.fragment.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_overview.*

class OverviewFragment : BaseFragment<OverviewVM, FragmentOverviewBinding>(),
    SearchView.OnQueryTextListener {

    override val layoutResId = R.layout.fragment_overview

    override val viewModelClass = OverviewVM::class

    private lateinit var searchView: SearchView

    private var recViewLastPosition: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onResume() {
        super.onResume()
        restoreRecyclerViewPosition()
    }

    override fun onPause() {
        saveRecyclerViewPosition()
        super.onPause()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_overview, menu)

        // Setup search facility
        val searchItem = menu.findItem(R.id.menu_item_search) as MenuItem
        searchView = searchItem.actionView as SearchView
        searchView.setOnQueryTextListener(this)
        searchView.queryHint = requireContext().getString(R.string.menu_item_search_hint)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when (item.itemId) {
            R.id.menu_item_search -> true
            R.id.menu_item_filter_all -> {
                viewModel.filterBySeason("0")
                true
            }
            R.id.menu_item_filter_s1 -> {
                viewModel.filterBySeason("1")
                true
            }
            R.id.menu_item_filter_s2 -> {
                viewModel.filterBySeason("2")
                true
            }
            R.id.menu_item_filter_s3 -> {
                viewModel.filterBySeason("3")
                true
            }
            R.id.menu_item_filter_s4 -> {
                viewModel.filterBySeason("4")
                true
            }
            R.id.menu_item_filter_s5 -> {
                viewModel.filterBySeason("5")
                true
            }
            else -> super.onOptionsItemSelected(item)
        }

    override fun onQueryTextSubmit(query: String?): Boolean {
        searchView.clearFocus()
        return true;
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        viewModel.filterByName(newText)
        return true;
    }

    override fun getActionBarTitle(): String = requireActivity().getString(R.string.app_name)

    private fun saveRecyclerViewPosition() {
        recViewLastPosition =
            (recyclerViewOverview.layoutManager as FlexboxLayoutManager).findFirstCompletelyVisibleItemPosition()
    }

    private fun restoreRecyclerViewPosition() {
        if (recViewLastPosition != -1) {
            recyclerViewOverview.layoutManager?.scrollToPosition(recViewLastPosition)
            recViewLastPosition = -1
        }
    }
}