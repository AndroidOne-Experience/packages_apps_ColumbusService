package io.androidone.columbus.actions

import android.content.Context
import android.content.Intent
import io.androidone.columbus.R
import io.androidone.columbus.getDePrefs
import io.androidone.columbus.getLaunchActionApp
import io.androidone.columbus.getLaunchActionAppShortcut

class LaunchAction(context: Context) : Action(context) {
    val app_default = context.getString(R.string.launch_app_default)

    override fun canRun() = isDeviceInteractiveAndUnlocked(context)

    override fun canRunWhenScreenOff() = false

    private fun launchApp() {
        val prefs = context.getDePrefs()

        val packageName: String = prefs.getLaunchActionApp(context)
        val activity: String = prefs.getLaunchActionAppShortcut(context)
        val launchActivity: Boolean = activity != app_default

        if (packageName == app_default) return

        val intent: Intent? =
            if (launchActivity) {
                    Intent(Intent.ACTION_MAIN).apply { setClassName(packageName, activity) }
                } else {
                    context.packageManager.getLaunchIntentForPackage(packageName)
                }
                ?.apply { (Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP) }

        try {
            context.startActivity(intent)
        } catch (e: Exception) {}
    }

    override fun run() {
        launchApp()
    }
}
