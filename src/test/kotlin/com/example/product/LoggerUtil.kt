package com.example.product

object LoggerUtil {
    fun info(message: String) {
        println("${System.currentTimeMillis()} - INFO - ${Thread.currentThread().name} - $message")
    }

}