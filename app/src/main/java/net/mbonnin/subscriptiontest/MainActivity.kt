package net.mbonnin.subscriptiontest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.apollographql.apollo3.ApolloClient
import com.apollographql.apollo3.network.ws.WebSocketNetworkTransport
import kotlinx.coroutines.delay
import net.mbonnin.subscriptiontest.ui.theme.SubscriptionTestTheme
import subscription.test.GetTimeSubscription

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SubscriptionTestTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    val flow = remember {
        ApolloClient.Builder()
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

    val state = flow.collectAsStateWithLifecycle(initialValue = null)

    Text(
        text = "Today is ${state.value?.data?.time}!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    SubscriptionTestTheme {
        Greeting("Android")
    }
}