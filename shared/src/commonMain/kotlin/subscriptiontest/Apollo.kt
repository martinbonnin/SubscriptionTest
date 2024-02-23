package subscriptiontest

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.network.ws.WebSocketNetworkTransport
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import subscription.test.GetTimeSubscription

fun currentTime(): Flow<ApolloResponse<GetTimeSubscription.Data>> {
    return ApolloClient.Builder()
        .serverUrl("https://leonidas-naiwjdzjsq-od.a.run.app/graphql")
        .subscriptionNetworkTransport(
            WebSocketNetworkTransport.Builder()
                .serverUrl("https://leonidas-naiwjdzjsq-od.a.run.app/subscription")
                .reopenWhen { throwable, attempt ->
                    println("Reopening...")
                    delay(2000)
                    true
                }
                .build()
        )
        .build()
        .subscription(GetTimeSubscription())
        .toFlow()
}