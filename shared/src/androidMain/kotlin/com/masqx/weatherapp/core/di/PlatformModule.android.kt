package com.masqx.weatherapp.core.di

import android.annotation.SuppressLint
import android.content.Context
import com.masqx.weatherapp.core.service.location.LocationService
import org.koin.core.module.Module
import org.koin.dsl.module

// Application context, не Activity — утечки нет.
@SuppressLint("StaticFieldLeak")
private lateinit var appContext: Context

/** Вызвать из Application/Activity до первого использования Koin-графа. */
fun initSharedAndroidContext(context: Context) {
    appContext = context.applicationContext
}

actual val platformModule: Module = module {
    single { LocationService(appContext) }
}
