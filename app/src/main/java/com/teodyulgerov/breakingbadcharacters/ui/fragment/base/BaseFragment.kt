package com.teodyulgerov.breakingbadcharacters.ui.fragment.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.teodyulgerov.breakingbadcharacters.BR
import com.teodyulgerov.breakingbadcharacters.utils.NavigationAction
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject
import kotlin.reflect.KClass

abstract class BaseFragment<V : BaseVM, B : ViewDataBinding> : Fragment() {

    protected abstract val layoutResId: Int

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    protected lateinit var viewModel: V
    protected abstract val viewModelClass: KClass<V>
    private lateinit var binding: B

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidSupportInjection.inject(this)
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory).get(viewModelClass.java)
        lifecycle.addObserver(viewModel)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, layoutResId, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        initBinding(binding)
        observeNavigationEvents()

        return binding.root
    }

    override fun onResume() {
        super.onResume()
        (activity as AppCompatActivity).supportActionBar?.title = getActionBarTitle()
    }

    override fun onDestroy() {
        lifecycle.removeObserver(viewModel)
        super.onDestroy()
    }

    protected open fun initBinding(binding: B) = binding.setVariable(BR.viewModel, viewModel)

    private fun observeNavigationEvents() =
        viewModel.newDestinationEvent.observe(viewLifecycleOwner, Observer { event ->
            if (event != null) {
                event.getContent()?.let { actionId ->
                    if (actionId == NavigationAction.POP_BACK_STACK.value) {
                        findNavController().popBackStack()
                    } else {
                        navigateTo(actionId, event.bundle)
                    }
                }
            }
        })

    private fun navigateTo(actionId: Int, bundle: Bundle?) =
        findNavController().navigate(actionId, bundle)

    protected abstract fun getActionBarTitle(): String
}