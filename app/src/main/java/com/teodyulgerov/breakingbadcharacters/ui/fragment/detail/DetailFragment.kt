package com.teodyulgerov.breakingbadcharacters.ui.fragment.detail

import android.os.Bundle
import android.view.View
import com.teodyulgerov.breakingbadcharacters.R
import com.teodyulgerov.breakingbadcharacters.databinding.FragmentDetailBinding
import com.teodyulgerov.breakingbadcharacters.ui.fragment.base.BaseFragment

class DetailFragment : BaseFragment<DetailVM, FragmentDetailBinding>() {

    override val layoutResId = R.layout.fragment_detail

    override val viewModelClass = DetailVM::class

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.setCharacter(arguments)
    }

    override fun getActionBarTitle(): String {
        viewModel.actionBarTitle.value?.let {
            return it
        }
        return ""
    }
}