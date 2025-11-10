package org.example.project

import com.google.protobuf.Empty
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.example.project.grpc.XTomRemoteClientGrpcKt


class GrpcClient {

    // Replace with your server's actual host and port
    private val channel: ManagedChannel = ManagedChannelBuilder
        .forAddress("localhost", 5070)
        .usePlaintext() // Use this for local development without TLS
        .build()

    private val client = XTomRemoteClientGrpcKt.XTomRemoteCoroutineStub(channel)

    /**
     * Calls the Heartbeat RPC to check if the server is alive.
     */
    suspend fun checkHeartbeat(): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                val response = client.heartbeat(Empty.getDefaultInstance())
                response.isAlive
            }
        } catch (e: Exception) {
            // Handle exceptions like connection errors
            println("Error calling heartbeat: ${e.message}")
            false
        }
    }

    // You can add other client methods here, for example:
    // suspend fun getSystemConfiguration() = client.getSystemConfiguration(Empty.getDefaultInstance())

    /**
     * Shuts down the gRPC channel.
     */
    fun shutdown() {
        channel.shutdown()
    }
}
