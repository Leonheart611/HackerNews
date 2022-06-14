package com.mikaocto.hackernews.util

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


suspend fun io(block: suspend CoroutineScope.() -> Unit) {
    withContext(Dispatchers.IO) {
        block.invoke(this)
    }
}
