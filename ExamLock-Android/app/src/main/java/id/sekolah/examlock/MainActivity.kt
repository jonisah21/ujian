package id.sekolah.examlock

import android.app.Activity
import android.app.admin.DevicePolicyManager
import android.content.ComponentName
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.webkit.JavascriptInterface
import android.webkit.WebView
import android.webkit.WebViewClient

class MainActivity : Activity() {
    private lateinit var web: WebView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Blokir screenshot dan rekam layar, layar tetap menyala
        window.addFlags(WindowManager.LayoutParams.FLAG_SECURE)
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        web = WebView(this)
        setContentView(web)
        web.settings.javaScriptEnabled = true
        web.settings.domStorageEnabled = true
        web.webViewClient = WebViewClient()
        web.addJavascriptInterface(Bridge(), "AndroidLock")
        web.loadUrl("file:///android_asset/examlock.html")
        hideBars()
    }

    inner class Bridge {
        @JavascriptInterface fun lock() = runOnUiThread { enableLock() }
        @JavascriptInterface fun unlock() = runOnUiThread {
            try { stopLockTask() } catch (e: Exception) { }
        }
    }

    private fun enableLock() {
        val dpm = getSystemService(DevicePolicyManager::class.java)
        if (dpm.isDeviceOwnerApp(packageName)) {
            // Mode kios penuh: tanpa tombol Home dan Recent, tanpa cara keluar bagi siswa
            dpm.setLockTaskPackages(ComponentName(this, AdminReceiver::class.java), arrayOf(packageName))
        }
        try { startLockTask() } catch (e: Exception) { }
    }

    @Deprecated("Tombol Back diblokir selama ujian")
    override fun onBackPressed() { }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) hideBars()
    }

    @Suppress("DEPRECATION")
    private fun hideBars() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
            or View.SYSTEM_UI_FLAG_FULLSCREEN or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
            or View.SYSTEM_UI_FLAG_LAYOUT_STABLE or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
            or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION)
    }
}
