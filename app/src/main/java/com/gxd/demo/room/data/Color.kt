package com.gxd.demo.room.data

enum class Color {
    BLACK, YELLOW, BLUE, RED;

    companion object {
        fun randomColor(): Color = when ((0..3).random()) {
            0 -> BLACK
            1 -> YELLOW
            2 -> BLUE
            else -> RED
        }
    }
}