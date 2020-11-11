package com.lumisdinos.gameoffifteen.di

import android.content.Context
import com.lumisdinos.gameoffifteen.GameOfFifteenApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        ApplicationModule::class,
        AndroidSupportInjectionModule::class,
        ActivitiesBuildersModule::class,
        ViewModelModule::class
    ]
)
interface ApplicationComponent : AndroidInjector<GameOfFifteenApp> {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance applicationContext: Context): ApplicationComponent
    }
}