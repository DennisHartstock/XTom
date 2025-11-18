package org.example.project

/**
 * Common interface for the gRPC client, defining the available functions.
 */
interface GrpcClientApi {
    suspend fun checkHeartbeat(): Boolean
    fun shutdown()
}

/**
 * Expected gRPC client. Each platform (android, desktop) will provide its own actual implementation.
 */
expect class GrpcClient() : GrpcClientApi
