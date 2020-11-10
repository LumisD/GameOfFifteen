package com.lumisdinos.gameoffifteen.di

import androidx.lifecycle.ViewModel
import com.lumisdinos.gameoffifteen.presentation.HomeViewModel
import com.lumisdinos.gameoffifteen.presentation.ListViewModel
import com.lumisdinos.gameoffifteen.presentation.SettingsViewModel
import com.lumisdinos.gameoffifteen.ui.activity.MainActivity
import com.lumisdinos.gameoffifteen.ui.fragment.HomeFragment
import com.lumisdinos.gameoffifteen.ui.fragment.ListFragment
import com.lumisdinos.gameoffifteen.ui.fragment.SettingsFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class PresentationModule {
    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class
        ]
    )
    internal abstract fun mainActivity(): MainActivity

    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class
        ]
    )
    internal abstract fun homeFragment(): HomeFragment

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    abstract fun bindHomeViewModel(viewmodel: HomeViewModel): ViewModel

    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class
        ]
    )
    internal abstract fun listFragment(): ListFragment

    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel::class)
    abstract fun bindListViewModel(viewmodel: ListViewModel): ViewModel

    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class
        ]
    )
    internal abstract fun settingsFragment(): SettingsFragment

    @Binds
    @IntoMap
    @ViewModelKey(SettingsViewModel::class)
    abstract fun bindSettingsViewModel(viewmodel: SettingsViewModel): ViewModel
}