package com.piyal.mvvmnoteapp.di

import com.piyal.mvvmnoteapp.api.AuthIntercepter
import com.piyal.mvvmnoteapp.api.NotesAPI
import com.piyal.mvvmnoteapp.api.UserAPI
import com.piyal.mvvmnoteapp.utils.Constants.BASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun provideRetrofitBuilder(): Retrofit.Builder{
        return Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)

    }

    @Singleton
    @Provides
    fun provideOkHttpClient(authIntercepter: AuthIntercepter) : OkHttpClient{
        return  OkHttpClient.Builder().addInterceptor(authIntercepter).build()
    }

    @Singleton
    @Provides
    fun provideUserAPI(retrofitBuilder: Retrofit.Builder): UserAPI{
        return retrofitBuilder.build().create(UserAPI::class.java)
    }



    @Singleton
    @Provides
    fun providesNoteAPI(retrofitBuilder: Retrofit.Builder,okHttpClient: OkHttpClient) : NotesAPI{

        return  retrofitBuilder
            .client(okHttpClient)
            .build().create(NotesAPI::class.java)
    }
}