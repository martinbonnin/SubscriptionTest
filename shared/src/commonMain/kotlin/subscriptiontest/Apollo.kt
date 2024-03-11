package subscriptiontest

import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.annotations.ApolloExperimental
import com.apollographql.apollo3.api.ApolloResponse
import com.apollographql.apollo3.api.Subscription
import com.apollographql.apollo3.network.ws.incubating.SubscriptionWsProtocol
import com.apollographql.apollo3.network.ws.incubating.WebSocketNetworkTransport
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import subscription.test.GetTimeSubscription

@OptIn(ApolloExperimental::class)
fun currentTime(): Flow<ApolloResponse<GetTimeSubscription.Data>> {
    return ApolloClient.Builder()
        .serverUrl("https://leonidas-naiwjdzjsq-od.a.run.app/graphql")
        .retryOnError { it.operation is Subscription<*> }
        .subscriptionNetworkTransport(
            WebSocketNetworkTransport.Builder()
                .wsProtocol(SubscriptionWsProtocol { null })
                .serverUrl("https://leonidas-naiwjdzjsq-od.a.run.app/subscription")
                .build()
        )
        .build()
        .subscription(GetTimeSubscription())
        .toFlow()
}