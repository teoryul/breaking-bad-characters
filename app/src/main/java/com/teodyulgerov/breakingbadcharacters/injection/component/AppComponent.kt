package com.teodyulgerov.breakingbadcharacters.injection.component

import com.teodyulgerov.breakingbadcharacters.injection.module.fragment.FragmentModule
import com.teodyulgerov.breakingbadcharacters.injection.module.network.RetrofitModule
import com.teodyulgerov.breakingbadcharacters.injection.module.persistence.RoomModule
import com.teodyulgerov.breakingbadcharacters.injection.module.viewmodel.ViewModelModule
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidInjectionModule::class,
        ViewModelModule::class,
        FragmentModule::class,
        RoomModule::class,
        RetrofitModule::class
    ]
)
interface AppComponent : AndroidInjector<DaggerApplication>