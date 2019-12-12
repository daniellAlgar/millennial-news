package com.algar.local.rule

import androidx.arch.core.executor.ArchTaskExecutor
import androidx.arch.core.executor.DefaultTaskExecutor
import androidx.arch.core.executor.TaskExecutor
import org.junit.rules.TestWatcher
import org.junit.runner.Description

/**
 * When testing a Room @Transaction within a runBlocking{} we run into a deadlock, since
 * RoomDatabase itself uses runBlocking{} somewhere. This test rule is meant to unlock the deadlock.
 */
class IsMainExecutorRule : TestWatcher() {

    val defaultExecutor = DefaultTaskExecutor()

    override fun starting(description: Description?) {
        super.starting(description)
        ArchTaskExecutor.getInstance().setDelegate(object : TaskExecutor() {
            override fun executeOnDiskIO(runnable: Runnable) {
                defaultExecutor.executeOnDiskIO(runnable)
            }

            override fun postToMainThread(runnable: Runnable) {
                defaultExecutor.executeOnDiskIO(runnable)
            }

            override fun isMainThread(): Boolean {
                return true
            }
        })
    }

    override fun finished(description: Description?) {
        super.finished(description)
        ArchTaskExecutor.getInstance().setDelegate(null)
    }
}