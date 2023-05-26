package com.example.groovyspotify

import android.net.Uri
import android.webkit.WebView
import android.webkit.WebViewClient

class SpotifyWebViewClient(
    private val redirectUri: String,
    private val onAccessTokenReceived: (String) -> Unit,
    private val onError: (String) -> Unit
) : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView, url: String): Boolean {
        if (url.startsWith(redirectUri)) {
            val uri = Uri.parse(url)
            val code = uri.getQueryParameter("code")
            if (code != null) {
                onAccessTokenReceived(code)
            } else {
                val error = uri.getQueryParameter("error")
                if (error != null) {
                    onError(error)
                }
            }
            return true
        }
        return false
    }
}
