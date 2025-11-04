package org.example.project

import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder

//import org.example.project.grpc.XTomRemoteGrpcKt

class GrpcClient {

    // Replace with your server's actual host and port
    private val channel: ManagedChannel = ManagedChannelBuilder
        .forAddress("localhost", 5070)
        .usePlaintext() // Use this for local development without TLS
        .build()

//    private val client = XTomRemoteGrpcKt.XTomRemoteCoroutineStub(channel)

    /**
     * Calls the Heartbeat RPC to check if the server is alive.
     */
    suspend fun checkHeartbeat(): Boolean {
//        return try {
//            withContext(Dispatchers.IO) {
//                val response = client.heartbeat(Empty.getDefaultInstance())
//                response.isAlive
//            }
//        } catch (e: Exception) {
//            // Handle exceptions like connection errors
//            println("Error calling heartbeat: ${e.message}")
//            false
//        }

        return true
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
