package com.waracle.cakelist.di

import com.waracle.cakelist.data.remote.CakeService
import com.waracle.cakelist.data.repository.CakeRepository
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton
import org.kodein.di.generic.with
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

val networkModule = Kodein.Module("NetworkModule") {
    bind<OkHttpClient>() with singleton { getOkHttpClient() }
    bind<Retrofit>() with singleton { getRetrofit(instance(), instance(tag = "baseUrl")) }
    bind<CakeService>() with singleton { getCakeService(instance()) }
    bind<CakeRepository>() with singleton { CakeRepository(instance()) }
    constant(tag = "baseUrl") with "https://gist.githubusercontent.com"
}

private fun getOkHttpClient(): OkHttpClient {
    val builder = OkHttpClient.Builder()
    val httpLoggingInterceptor = HttpLoggingInterceptor()
    httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
    builder.interceptors().add(httpLoggingInterceptor)
    return builder.build()
}

private fun getRetrofit(okClient: OkHttpClient, baseUrl: String) = Retrofit.Builder()
    .client(okClient)
    .baseUrl(baseUrl)
    .addConverterFactory(MoshiConverterFactory.create())
    .build()

private fun getCakeService(retrofit: Retrofit) = retrofit.create(CakeService::class.java)