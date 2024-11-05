package ir.ha.meproject.common.espresso_util

import android.util.Log
import androidx.test.espresso.IdlingResource
import java.util.concurrent.atomic.AtomicInteger



enum class IdlingResourcesKeys{
    SPLASH , HOME , MORE
}

private val IdlingResourcesHash = hashMapOf<IdlingResourcesKeys, Any>()
fun <T>getIdlingResource(key : IdlingResourcesKeys) = IdlingResourcesHash[key] as T
fun <T>createAndReturnIdlingResource(key : IdlingResourcesKeys, resource : IdlingResource) : T? {
    IdlingResourcesHash[key] = resource
    return getIdlingResource(key) as T
}



class MyIdlingResource(val tag: String) : IdlingResource {

    @Volatile
    private var callback: IdlingResource.ResourceCallback? = null
    private var isIdle = true

    override fun getName(): String = tag + "idleResource"

    override fun isIdleNow(): Boolean = isIdle

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback?) {
        this.callback = callback
    }

    fun setIdleState(isIdleNow: Boolean) {
        isIdle = isIdleNow
        if (isIdleNow && callback != null) {
            callback?.onTransitionToIdle()
        }
    }
}





class MyCountingIdlingResource(resourceName: String) : IdlingResource {

    private val TAG = this::class.java.simpleName
    private val counter = AtomicInteger(0)
    private var resourceCallback: IdlingResource.ResourceCallback? = null
    private val resourceName: String = resourceName

    override fun getName(): String = resourceName

    override fun isIdleNow(): Boolean = counter.get() == 0

    override fun registerIdleTransitionCallback(callback: IdlingResource.ResourceCallback) {
        this.resourceCallback = callback
    }

    fun increment() {
        counter.getAndIncrement()
        Log.i(TAG, "increment: ${counter.get()}")
    }

    fun decrement() {
        val counterVal = counter.decrementAndGet()
        if (counterVal == 0) {
            // If the counter reaches zero, we're idle, so notify the callback
            resourceCallback?.onTransitionToIdle()
        } else if (counterVal < 0) {
            throw IllegalStateException("Counter has been corrupted!")
        }
        Log.i(TAG, "decrement: ${counter.get()}")
    }
}

