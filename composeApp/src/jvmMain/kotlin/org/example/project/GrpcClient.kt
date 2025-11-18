package org.example.project

import com.google.protobuf.Empty
import io.grpc.ManagedChannel
import io.grpc.ManagedChannelBuilder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.example.project.grpc.XTomRemoteCoroutineStub

/**
 * JVM-specific implementation of the gRPC client.
 * This single implementation is used by both Android and Desktop.
 */
actual class GrpcClient actual constructor() : GrpcClientApi {

    private val channel: ManagedChannel = ManagedChannelBuilder
        .forAddress("localhost", 5070)
        .usePlaintext()
        .build()

    private val client = XTomRemoteCoroutineStub(channel)

    override suspend fun checkHeartbeat(): Boolean {
        return try {
            withContext(Dispatchers.IO) {
                val response = client.heartbeat(Empty.getDefaultInstance())
                response.isAlive
            }
        } catch (e: Exception) {
            println("Error calling heartbeat: ${e.message}")
            false
        }
    }

    override fun shutdown() {
        channel.shutdown()
    }
}
