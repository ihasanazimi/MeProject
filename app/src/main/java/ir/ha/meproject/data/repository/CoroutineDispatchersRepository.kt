package ir.ha.meproject.data.repository


import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlin.coroutines.CoroutineContext


interface CoroutineDispatchers {
    fun ioDispatchers(): CoroutineDispatcher
    fun ioDispatchersWithSupervisorJob(): CoroutineContext
    fun mainDispatchers(): CoroutineDispatcher
    fun defaultDispatchers(): CoroutineDispatcher
    fun defaultDispatchersWithSupervisorJob():CoroutineContext
}

class CoroutineDispatchersImpl: CoroutineDispatchers {
    override fun ioDispatchers(): CoroutineDispatcher = Dispatchers.IO
    override fun ioDispatchersWithSupervisorJob() = Dispatchers.IO + SupervisorJob()
    override fun mainDispatchers() = Dispatchers.Main
    override fun defaultDispatchers() = Dispatchers.Default
    override fun defaultDispatchersWithSupervisorJob() = Dispatchers.Default + SupervisorJob()

}

class TestCoroutineDispatchersImpl: CoroutineDispatchers {
    override fun ioDispatchers(): CoroutineDispatcher = StandardTestDispatcher()
    override fun ioDispatchersWithSupervisorJob() = StandardTestDispatcher()
    override fun mainDispatchers() = StandardTestDispatcher()
    override fun defaultDispatchers() = StandardTestDispatcher()
    override fun defaultDispatchersWithSupervisorJob() = StandardTestDispatcher()

}