package com.masqx.weatherapp.core.navigation

import androidx.navigation.NavHostController

/**
 * Обёртка над [NavHostController], живёт в Koin как singleton.
 * Позволяет вызывать навигацию из любого места (вьюмодели, репозитории), не таская NavController руками.
 * [controller] выставляется один раз при монтировании NavHost ([bind]).
 */
class Navigator {
    private var controller: NavHostController? = null

    fun bind(navHostController: NavHostController) {
        controller = navHostController
    }

    fun unbind() {
        controller = null
    }

    fun navigate(route: String) {
        controller?.navigate(route)
    }

    fun popBackStack() {
        controller?.popBackStack()
    }
}
