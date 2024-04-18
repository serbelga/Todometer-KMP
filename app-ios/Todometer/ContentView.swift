//
//  ContentView.swift
//  Todometer
//
//  Created by Sergio Belda Galbis on 12/11/23.
//

import SwiftUI
import app

struct ContentView: View {
    var body: some View {
        ComposeView().ignoresSafeArea(.all) // Compose has own keyboard handler
    }
}

struct ComposeView: UIViewControllerRepresentable {
    func makeUIViewController(context: Context) -> UIViewController {
        MainKt.MainViewController()
    }

    func updateUIViewController(_ uiViewController: UIViewController, context: Context) {
    }
}
