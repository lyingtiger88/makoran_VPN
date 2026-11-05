package com.makran.vpn

import android.content.Context
import com.wireguard.android.backend.GoBackend
import com.wireguard.android.backend.Tunnel
import com.wireguard.config.Config
import java.io.BufferedReader
import java.io.StringReader

/** WireGuard engine wrapper. Configuration is parsed in memory and never logged or persisted. */
class WireGuardController(context: Context) {
    private val backend = GoBackend(context.applicationContext)
    private val tunnel = object : Tunnel {
        override fun getName(): String = "makran-gaming"
        override fun onStateChange(newState: Tunnel.State) = Unit
    }

    fun connect(configText: String) {
        require(configText.isNotBlank()) { "فایل کانفیگ خالی است." }
        val config = Config.parse(BufferedReader(StringReader(configText)))
        backend.setState(tunnel, Tunnel.State.UP, config)
    }

    fun disconnect() {
        backend.setState(tunnel, Tunnel.State.DOWN, null)
    }

    fun isConnected(): Boolean = backend.getState(tunnel) == Tunnel.State.UP
}
