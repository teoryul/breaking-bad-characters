package com.teodyulgerov.breakingbadcharacters.ui.activity.main

import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import com.teodyulgerov.breakingbadcharacters.R
import com.teodyulgerov.breakingbadcharacters.ui.activity.base.BaseActivity

class MainActivity : BaseActivity() {

    override val layoutResId = R.layout.activity_main

    private var navHostFragment = NavHostFragment()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        navHostFragment = NavHostFragment.create(R.navigation.navigation_graph)
        supportFragmentManager.beginTransaction()
            .replace(R.id.content, navHostFragment)
            .setPrimaryNavigationFragment(navHostFragment)
            .commit()
    }
}