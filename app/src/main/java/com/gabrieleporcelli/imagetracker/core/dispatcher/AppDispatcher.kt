package com.gabrieleporcelli.imagetracker.core.dispatcher

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainCoroutineDispatcher

sealed interface AppDispatcher {
    val main: MainCoroutineDispatcher
    val io: CoroutineDispatcher
}

class AppDispatcherDefault : AppDispatcher {
    override val main = Dispatchers.Main
    override val io = Dispatchers.IO
}