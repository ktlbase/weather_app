package com.masqx.weatherapp.feature.city.data.dto

import com.masqx.weatherapp.feature.city.domain.entity.Timezone
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class CityDto(
    val id: Long,
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val country: String,
    val timezone: String,

    @SerialName("admin1")
    val region: String? = null,

)
