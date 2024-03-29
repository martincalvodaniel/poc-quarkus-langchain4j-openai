package com.dmartinc

import org.slf4j.Logger
import org.slf4j.LoggerFactory

object Utils {
    inline fun <reified T> T.logger(): Logger = LoggerFactory.getLogger(T::class.java)
}