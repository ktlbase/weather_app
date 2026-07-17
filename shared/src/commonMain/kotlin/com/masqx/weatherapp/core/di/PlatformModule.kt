package com.masqx.weatherapp.core.di

import org.koin.core.module.Module

/** Платформенные зависимости (LocationService и т.п.). Собирается в [appModules]. */
expect val platformModule: Module
