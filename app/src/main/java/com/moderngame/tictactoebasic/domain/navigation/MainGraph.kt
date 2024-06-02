package com.moderngame.tictactoebasic.domain.navigation


sealed class Destination(protected val route: String, vararg params: String) {
    val fullRoute: String = if (params.isEmpty()) route else {
        val builder = StringBuilder(route)
        params.forEach { builder.append("/{${it}}") }
        builder.toString()
    }

    sealed class NoArgumentsDestination(route: String) : Destination(route) {
        operator fun invoke(): String = route
    }

    data object HomeScreen : NoArgumentsDestination("home")

    data object TicTacToeScreen : Destination("tic_tac_toe_screen", "levels"){
        private const val FIST_NAME_KEY = "levels"

        operator fun invoke(level: String): String = route.appendParams(
            FIST_NAME_KEY to level
        )
    }

    data object SettingScreen : Destination("setting_screen")
}

internal fun String.appendParams(vararg params: Pair<String, Any?>): String {
    val builder = StringBuilder(this)

    params.forEach {
        it.second?.toString()?.let { arg ->
            builder.append("/$arg")
        }
    }

    return builder.toString()
}
