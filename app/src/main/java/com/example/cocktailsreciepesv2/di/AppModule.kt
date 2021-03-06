package com.example.cocktailsreciepesv2.di

import android.app.Application
import androidx.room.Room
import com.example.cocktailsreciepesv2.data.DataConstants
import com.example.cocktailsreciepesv2.data.api.TCDBService
import com.example.cocktailsreciepesv2.data.db.DrinkListDao
import com.example.cocktailsreciepesv2.data.db.TCDBDatabase
import com.example.cocktailsreciepesv2.data.repository.drinkInfo.DrinkInfoRemoteDataSource
import com.example.cocktailsreciepesv2.data.repository.drinkInfo.DrinkInfoRemoteDataSourceImpl
import com.example.cocktailsreciepesv2.data.repository.drinkInfo.DrinkInfoRepositoryImpl
import com.example.cocktailsreciepesv2.data.repository.drinkList.*
import com.example.cocktailsreciepesv2.domain.repository.DrinkInfoRepository
import com.example.cocktailsreciepesv2.domain.repository.DrinkListRepository
import com.example.cocktailsreciepesv2.presentation.viewmodel.DrinkInfoViewModel
import com.example.cocktailsreciepesv2.presentation.viewmodel.DrinkListViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    viewModel { DrinkListViewModel(get()) }
    viewModel { DrinkInfoViewModel(get()) }

    factory<DrinkListRepository> { DrinkListRepositoryImpl(get(), get()) }
    factory<DrinkInfoRepository> { DrinkInfoRepositoryImpl(get()) }

    factory<DrinkListRemoteDataSource> { DrinkListRemoteDataSourceImpl(get()) }
    factory<DrinkInfoRemoteDataSource> { DrinkInfoRemoteDataSourceImpl(get()) }
    factory<DrinkListLocalDataSource> { DrinkListLocalDataSourceImpl(get()) }


    fun provideDatabase(application: Application): TCDBDatabase {
        return Room.databaseBuilder(application, TCDBDatabase::class.java, "drink_list_table")
            .fallbackToDestructiveMigration()
            .build()
    }

    fun provideDrinkListDao(database: TCDBDatabase): DrinkListDao {
        return database.drinkListDao()
    }
    single { provideDatabase(androidApplication()) }
    single { provideDrinkListDao(get()) }


    fun getTCDBService(retrofit: Retrofit): TCDBService = retrofit.create(TCDBService::class.java)
    single { getTCDBService(get()) }

    fun provideRetrofit(baseUrl: String): Retrofit {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single {
        provideRetrofit(DataConstants.BASE_URL)
    }
}