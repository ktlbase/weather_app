package com.masqx.weatherapp.feature.city.domain.entity

import kotlinx.datetime.TimeZone as KotlinTimeZone
import kotlin.jvm.JvmInline

/**
 * IANA timezone id (например "Europe/Moscow"). Ограничена валидным набором:
 * значения проверяются через [kotlinx.datetime.TimeZone.of], невалидный id роняет init.
 */
@JvmInline
value class Timezone(val id: String) {
    init {
        // Кидает IllegalTimeZoneException на неизвестном id — валидация набора зон,
        // без ручного перечисления ~600 значений в enum.
        KotlinTimeZone.of(id)
    }

    fun toKotlinTimeZone(): KotlinTimeZone = KotlinTimeZone.of(id)

    companion object {
        val UTC: Timezone = Timezone("UTC")
    }
}
