package com.example.pagination.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.example.pagination.App
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.suspendCancellableCoroutine
import kotlinx.coroutines.withContext
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

suspend fun isNetworkAvailable(): Boolean {
    val connectivityManager = App.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val nw = connectivityManager.activeNetwork ?: return false
    val actNw = connectivityManager.getNetworkCapabilities(nw) ?: return false
    return when {
        actNw.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ||
                actNw.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> checkConnection()

        else -> false
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
private suspend fun checkConnection(): Boolean = suspendCancellableCoroutine {
    CoroutineScope(Dispatchers.IO).launch {
        var isOnline: Boolean
        try {
            val socket = Socket()
            withContext(Dispatchers.IO) { socket.connect(InetSocketAddress("8.8.8.8", 53), 10000) }
            isOnline = true
        } catch (e: IOException) {
            isOnline = false
            e.printStackTrace()
        }
        it.resume(isOnline) {}
    }
}


private fun networkAvailableStatus(): Boolean {
    val connectivityManager = App.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork = connectivityManager.activeNetwork ?: return false
    val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
    val isConnected = when {
        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        else -> false
    }
    return isConnected
}