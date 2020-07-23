package com.teodyulgerov.breakingbadcharacters.injection.module.fragment

import androidx.lifecycle.ViewModel
import com.teodyulgerov.breakingbadcharacters.injection.module.viewmodel.ViewModelKey
import com.teodyulgerov.breakingbadcharacters.ui.fragment.detail.DetailFragment
import com.teodyulgerov.breakingbadcharacters.ui.fragment.detail.DetailVM
import com.teodyulgerov.breakingbadcharacters.ui.fragment.overview.OverviewFragment
import com.teodyulgerov.breakingbadcharacters.ui.fragment.overview.OverviewVM
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class FragmentModule {
    @ContributesAndroidInjector
    abstract fun contributesOverviewFragment(): OverviewFragment

    @Binds
    @IntoMap
    @ViewModelKey(OverviewVM::class)
    abstract fun bindsOverviewVM(overviewVM: OverviewVM): ViewModel

    @ContributesAndroidInjector
    abstract fun contributesDetailFragment(): DetailFragment

    @Binds
    @IntoMap
    @ViewModelKey(DetailVM::class)
    abstract fun bindsDetailVM(detailVM: DetailVM): ViewModel
}