package com.example.broccoli.utils

import nl.dionsegijn.konfetti.core.Angle
import nl.dionsegijn.konfetti.core.Party
import nl.dionsegijn.konfetti.core.Position
import nl.dionsegijn.konfetti.core.Spread
import nl.dionsegijn.konfetti.core.emitter.Emitter
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

class Utility {
    companion object {
        fun getGreetingsMsg(): String {
            val c = Calendar.getInstance()
            val sdf = SimpleDateFormat("dd-MMM-yyyy HH:mm:ss z")
            sdf.timeZone = TimeZone.getTimeZone("Australia/Melbourne")
            return when (c.get(Calendar.HOUR_OF_DAY)) {
                in 0..11 -> "Good Morning!"
                in 12..15 -> "Good Afternoon!"
                in 16..20 -> "Good Evening!"
                in 21..23 -> "Hope you are doing good!"
                else -> "Hello"
            }
        }

        fun rain(): List<Party> {
            return listOf(
                Party(
                    speed = 0f,
                    maxSpeed = 15f,
                    damping = 0.9f,
                    angle = Angle.BOTTOM,
                    spread = Spread.ROUND,
                    colors = listOf(0xfce18a, 0xff726d, 0xf4306d, 0xb48def),
                    emitter = Emitter(duration = 5, TimeUnit.SECONDS).perSecond(100),
                    position = Position.Relative(0.0, 0.0).between(Position.Relative(1.0, 0.0))
                )
            )
        }


    }
}