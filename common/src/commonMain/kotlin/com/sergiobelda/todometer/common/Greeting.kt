package com.sergiobelda.todometer.common

class Greeting {
    fun greeting(): String {
        return "Hello, ${Platform().platform}!"
    }
}
