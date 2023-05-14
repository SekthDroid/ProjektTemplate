package com.sekthdroid.projek.template.di

import android.content.Context
import android.util.Log
import com.sekthdroid.projek.template.data.network.RestDatasource
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kotlin.properties.Delegates

object Injector {
    private var appContext: Context by Delegates.notNull()

    fun init(context: Context) {
        this.appContext = context
    }

    val httpClient by lazy {
        HttpClient {
            install(Logging) {
                level = LogLevel.ALL
                logger = object : Logger {
                    override fun log(message: String) {
                        Log.i(Logging.key.name, message)
                    }
                }
            }
            install(ContentNegotiation) {
                json(
                    Json {
                        ignoreUnknownKeys = true
                    }
                )
            }
        }
    }

    val restDatasource by lazy {
        RestDatasource(httpClient)
    }
}