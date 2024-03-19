package com.dmartinc

import jakarta.enterprise.context.ApplicationScoped
import kotlin.math.min

@ApplicationScoped
class RequestSplitter {

    fun split(text: String): List<String> {
        val pieces: MutableList<String> = ArrayList()

        if (text.isNotEmpty()) {
            val length = text.length

            if (length <= MAX_CHARACTERS) {
                return listOf(text)
            }

            var startIndex = 0
            var endIndex = MAX_CHARACTERS

            while (startIndex < length) {
                val piece = text.substring(startIndex, endIndex)
                pieces.add(piece)
                startIndex = endIndex
                endIndex = min((startIndex + MAX_CHARACTERS), length)
            }
        }

        return pieces
    }

    companion object {
        private const val MAX_CHARACTERS = 2000
    }
}
