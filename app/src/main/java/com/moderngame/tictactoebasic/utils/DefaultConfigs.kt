package com.moderngame.tictactoebasic.utils

object DefaultConfigs {
    fun getDefaultParams(): Map<String, Any> {
        return hashMapOf(
            ConfigKeys.FORCE_UPDATE to false,
            ConfigKeys.DESCRIPTION to "Check update"
        )
    }

    object ConfigKeys {
        const val FORCE_UPDATE = "force_update"
        const val DESCRIPTION = "message"
    }
}