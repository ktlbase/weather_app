package com.masqx.weatherapp.feature.city.data.mapper

import com.masqx.weatherapp.feature.city.data.dto.CityDto
import com.masqx.weatherapp.feature.city.domain.entity.City
import com.masqx.weatherapp.feature.city.domain.entity.CityId
import com.masqx.weatherapp.feature.city.domain.entity.Location
import com.masqx.weatherapp.feature.city.domain.entity.Timezone

fun CityDto.toDomain(): City = City(
    id = CityId(id.toString()),
    name = name,
    location = Location(
        latitude,
        longitude
    ),
    country = country,
    timezone = Timezone(timezone),
    region = region,
)