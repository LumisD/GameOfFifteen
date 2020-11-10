package com.lumisdinos.gameoffifteen.di

import androidx.lifecycle.ViewModel
import com.lumisdinos.gameoffifteen.presentation.ListViewModel
import com.lumisdinos.gameoffifteen.ui.fragment.ListFragment
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap

@Module
abstract class ListModule {
    @ContributesAndroidInjector(
        modules = [
            ViewModelBuilder::class
        ]
    )
    internal abstract fun listFragment(): ListFragment

    @Binds
    @IntoMap
    @ViewModelKey(ListViewModel::class)
    abstract fun bindViewModel(viewmodel: ListViewModel): ViewModel
}