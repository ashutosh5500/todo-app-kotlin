package com.ashu.test.navigation

sealed class Screen(val route: String) {
    data object Main : Screen(route = "main");
    data object AddOrUpdate : Screen(route = "addOrUpdate/{id}") {
        fun passArgs(id: String) = "addOrUpdate/$id"
    }
}