package io.chaldeaprjkt.gamespace.widget.tiles

import android.app.NotificationManager
import android.content.Context
import android.util.AttributeSet
import io.chaldeaprjkt.gamespace.R

class DndTile @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null
) : BaseTile(context, attrs) {

    private val notificationManager by lazy {
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        title?.text = context.getString(R.string.dnd_title)
        icon?.setImageResource(android.R.drawable.ic_lock_silent_mode)

        val currentlyOn = notificationManager.currentInterruptionFilter !=
            NotificationManager.INTERRUPTION_FILTER_ALL
        super.setSelected(currentlyOn)
        summary?.text = context.getString(
            if (currentlyOn) R.string.state_enabled else R.string.state_disabled
        )
    }

    override fun setSelected(selected: Boolean) {
        super.setSelected(selected)
        if (!notificationManager.isNotificationPolicyAccessGranted) return
        notificationManager.setInterruptionFilter(
            if (selected) NotificationManager.INTERRUPTION_FILTER_PRIORITY
            else NotificationManager.INTERRUPTION_FILTER_ALL
        )
        summary?.text = context.getString(
            if (selected) R.string.state_enabled else R.string.state_disabled
        )
    }
}
