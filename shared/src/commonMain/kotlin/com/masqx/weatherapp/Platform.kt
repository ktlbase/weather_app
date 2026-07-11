package com.masqx.weatherapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform