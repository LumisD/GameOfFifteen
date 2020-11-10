package com.lumisdinos.gameoffifteen.di

import androidx.lifecycle.ViewModel
import com.lumisdinos.gameoffifteen.presentation.SettingsViewModel
import com.lumisdinos.gameoffifteen.ui.fragment.SettingsFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class SettingsModule {
    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class
        ]
    )
    internal abstract fun settingsFragment(): SettingsFragment

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindViewModel(viewmodel: SettingsViewModel): ViewModel
}