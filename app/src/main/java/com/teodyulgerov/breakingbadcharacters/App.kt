package com.teodyulgerov.breakingbadcharacters

import com.teodyulgerov.breakingbadcharacters.injection.component.DaggerAppComponent
import com.teodyulgerov.breakingbadcharacters.injection.module.network.RetrofitModule
import com.teodyulgerov.breakingbadcharacters.injection.module.persistence.RoomModule
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class App : DaggerApplication() {

    companion object {
        lateinit var instance: App
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> =
        DaggerAppComponent.builder()
            .retrofitModule(RetrofitModule())
            .roomModule(RoomModule(this))
            .build()

}