package com.byagowi.persiancalendar.ui.calendar.dialogs

import androidx.appcompat.app.AlertDialog
import androidx.core.content.edit
import androidx.fragment.app.Fragment
import com.byagowi.persiancalendar.R
import com.byagowi.persiancalendar.ui.shared.DayPickerView
import com.byagowi.persiancalendar.utils.Jdn
import com.byagowi.persiancalendar.utils.appPrefs

// Only one use but to match with showColorPickerDialog
fun Fragment.showDayPickerDialog(key: String): Boolean {
    val todayJdn = Jdn.today
    val jdn = activity?.appPrefs?.getLong(key, todayJdn.value)?.let(::Jdn) ?: todayJdn
    showDayPickerDialog(jdn) { result -> activity?.appPrefs?.edit { putLong(key, result.value) } }
    return true // Just a convenience result meaning click event is consumed here
}

fun Fragment.showDayPickerDialog(jdn: Jdn, onSuccess: (jdn: Jdn) -> Unit) {
    val activity = activity ?: return
    val dayPickerView = DayPickerView(activity).also { it.jdn = jdn }
    AlertDialog.Builder(activity)
        .setView(dayPickerView)
        .setCustomTitle(null)
        .setPositiveButton(R.string.go) { _, _ -> dayPickerView.jdn?.also(onSuccess) }
        .show()
}
