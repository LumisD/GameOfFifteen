package com.lumisdinos.gameoffifteen.di

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import androidx.room.Room
import com.lumisdinos.gameoffifteen.data.Database
import com.lumisdinos.gameoffifteen.data.GameRepositoryImpl
import com.lumisdinos.gameoffifteen.data.GameStateRepositoryImpl
import com.lumisdinos.gameoffifteen.data.PuzzleLogicRepositoryImpl
import com.lumisdinos.gameoffifteen.domain.repos.GameRepository
import com.lumisdinos.gameoffifteen.domain.repos.GameStateRepository
import com.lumisdinos.gameoffifteen.domain.repos.PuzzleLogicRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module(includes = [ApplicationModuleBinds::class])
object ApplicationModule {

    @Provides
    @Singleton
    fun provideDataBase(@ApplicationContext context: Context): Database {
        return Room.databaseBuilder(
            context.applicationContext,
            Database::class.java,
            "games.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideDaoDB(db: Database) = db.daoDB()


    @Singleton
    @Provides
    fun providePreferences(@ApplicationContext context: Context): SharedPreferences {
        return context.getSharedPreferences("games_pref", MODE_PRIVATE)
    }

}

@InstallIn(SingletonComponent::class)
@Module
abstract class ApplicationModuleBinds {

    @Binds
    abstract fun bindGameRepository(repository: GameRepositoryImpl): GameRepository

    @Binds
    abstract fun bindGameStateRepository(repository: GameStateRepositoryImpl): GameStateRepository

    @Singleton
    @Binds
    abstract fun bindPuzzleLogicRepository(repository: PuzzleLogicRepositoryImpl): PuzzleLogicRepository

}