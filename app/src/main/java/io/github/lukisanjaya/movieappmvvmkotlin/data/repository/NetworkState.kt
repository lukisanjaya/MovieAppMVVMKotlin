package io.github.lukisanjaya.movieappmvvmkotlin.data.repository

import io.github.lukisanjaya.movieappmvvmkotlin.data.repository.NetworkState.Companion.ENDOFLIST
import io.github.lukisanjaya.movieappmvvmkotlin.data.repository.NetworkState.Companion.LOADING

/**
 * Created by Luki Sanjaya on 07/05/20.
 * ukykazuki@gmail.com
 */

enum class Status {
    RUNNING,
    SUCCESS,
    FAILED
}

class NetworkState(val status: Status, val msg: String) {

    companion object {

        val LOADED: NetworkState
        val LOADING: NetworkState
        val ERROR: NetworkState
        val ENDOFLIST: NetworkState

        init {
            LOADED = NetworkState(Status.SUCCESS, "Success")
            LOADING = NetworkState(Status.RUNNING, "Running")
            ERROR = NetworkState(Status.FAILED, "Something when wrong")
            ENDOFLIST = NetworkState(Status.FAILED, "You have reached the end")
        }
    }
}