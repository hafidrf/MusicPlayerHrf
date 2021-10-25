package com.hafidrf.musicplayer.utils.ext

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import org.spekframework.spek2.dsl.Root

fun Root.instantTaskExecutorRule(){
    beforeGroup {
        ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
            override fun executeOnDiskIO(runnable: Runnable) {
                runnable.run()
            }

            override fun postToMainThread(runnable: Runnable) {
                runnable.run()
            }

            override fun isMainThread(): Boolean {
                return true
            }
        })
    }

    afterGroup {
        ArchTaskExecutor.getInstance().setDelegate(null)
    }
}