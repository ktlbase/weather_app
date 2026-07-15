package com.masqx.weatherapp.feature.city.domain.entity

import kotlin.jvm.JvmInline

/** Идентификатор города */
@JvmInline
value class CityId(val value: String)

/** Таймзона */
@JvmInline
value class Timezone(val value: String)

/** Город */
data class City(
    val id: CityId,
    val name: String,
    val location: Location,
    val country: String,
    val timezone: Timezone,
    val region: String? = null,
)