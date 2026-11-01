package io.androidone.columbus.sensors

interface Sensor {
    fun isListening(): Boolean

    fun startListening()

    fun stopListening()
}
