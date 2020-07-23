package com.teodyulgerov.breakingbadcharacters.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import com.teodyulgerov.breakingbadcharacters.App

/**
 * For easier testing.
 */
interface NetworkConnectivityChecker {
    fun isNetworkAvailable(): Boolean
}

object NetworkConnectivityCheckerImpl : NetworkConnectivityChecker {
    override fun isNetworkAvailable(): Boolean {
        var result = false
        val connectivityManager =
            App.instance.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectivityManager?.run {
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.run {
                    result = when {
                        hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                        hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                        else -> false
                    }
                }
            }
        } else {
            connectivityManager?.run {
                val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
                return activeNetwork?.isConnected == true
            }
        }

        return result
    }
}