package by.sitko.restapp.manager

import android.util.Log
import tech.gusavila92.websocketclient.WebSocketClient
import java.net.URI

private const val TAG = "SocketManager"

interface SocketManager {
    fun init()
    fun connect()
    fun disconnect()
    fun sendToServer(message: String)

    class Impl(
          private val listener: SocketListener
    ) : SocketManager {

        private lateinit var client: WebSocketClient

        override fun init() {

            client = object : WebSocketClient(URI("wss://ws.blockchain.info/inv")) {
                override fun onOpen() {
                    Log.d(TAG, "onOpen()")
                }

                override fun onTextReceived(message: String?) {
                    Log.d(TAG, "onTextReceived(), message $message")
                    message?.let { listener.onMessage(it) }
                }

                override fun onPongReceived(data: ByteArray?) {
                    Log.d(TAG, "onPongReceived()")
                }

                override fun onException(e: java.lang.Exception?) {
                    Log.d(TAG, "onException() e: $e")
                    e?.let { listener.onFail(it) }
                }

                override fun onCloseReceived() {
                    Log.d(TAG, "onCloseReceived()")
                }

                override fun onBinaryReceived(data: ByteArray?) {
                    Log.d(TAG, "onBinaryReceived()")
                }

                override fun onPingReceived(data: ByteArray?) {
                    Log.d(TAG, "onPingReceived()")
                }
            }

            client.send("{\"op\":\"ping\"}")
        }

        override fun connect() {
            client.connect()
        }

        override fun disconnect() {
            client.close()
        }

        override fun sendToServer(message: String) {
            client.send(message)
        }
    }

    interface SocketListener {
        fun onMessage(message: String)
        fun onFail(e: Exception)
    }
}