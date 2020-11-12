package com.lumisdinos.gameoffifteen.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import com.lumisdinos.gameoffifteen.data.Database
import com.lumisdinos.gameoffifteen.data.GameRepositoryImpl
import com.lumisdinos.gameoffifteen.data.PuzzleLogicRepositoryImpl
import com.lumisdinos.gameoffifteen.domain.repos.GameRepository
import com.lumisdinos.gameoffifteen.domain.repos.PuzzleLogicRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module(includes = [ApplicationModuleBinds::class])
object ApplicationModule {

    @JvmStatic
    @Singleton
    @Provides
    fun provideDataBase(context: Context): Database {
        return Room.databaseBuilder(
            context.applicationContext,
            Database::class.java,
            "games.db"
        ).build()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideDaoDB(db: Database) = db.daoDB()


    @JvmStatic
    @Singleton
    @Provides
    fun providePreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences("games_pref", MODE_PRIVATE)
    }

}

@Module
abstract class ApplicationModuleBinds {

    @Binds
    abstract fun bindGameRepository(repository: GameRepositoryImpl): GameRepository

    @Binds
    abstract fun bindPuzzleLogicRepository(repository: PuzzleLogicRepositoryImpl): PuzzleLogicRepository

}