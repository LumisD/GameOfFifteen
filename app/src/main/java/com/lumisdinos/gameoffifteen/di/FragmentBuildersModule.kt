package com.lumisdinos.gameoffifteen.di

import com.lumisdinos.gameoffifteen.ui.fragment.HomeFragment
import com.lumisdinos.gameoffifteen.ui.fragment.ListFragment
import com.lumisdinos.gameoffifteen.ui.fragment.SettingsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeListFragment(): ListFragment

    @ContributesAndroidInjector
    abstract fun contributeSettingsFragment(): SettingsFragment

}