//
//  ContentView.swift
//  SubscriptionTest
//
//  Created by Martin on 23/02/2024.
//

import SwiftUI
import Shared

struct ContentView: View {
    @StateObject
    private var viewModel = TimeViewModel()

    var body: some View {
        VStack {
            Image(systemName: "globe")
                .imageScale(.large)
                .foregroundStyle(.tint)
            Text(viewModel.message)
        }
        .padding()
        .task {
            await viewModel.activate()
        }
    }
}

#Preview {
    ContentView()
}


class TimeViewModel: ObservableObject {
    @Published
    private(set) var message: String = "Loading ..."

    @MainActor
    func activate() async {
        for await response in ApolloKt.currentTime() {
            print("hello" + response.data!.time)
            message = response.data!.time
        }
    }
}
