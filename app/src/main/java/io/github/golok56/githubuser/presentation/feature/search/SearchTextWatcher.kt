package io.github.golok56.githubuser.presentation.feature.search

import android.text.Editable
import android.text.TextWatcher
import java.util.*

class SearchTextWatcher(private val action: (String) -> Unit) : TextWatcher {
    private var timer = Timer()

    override fun afterTextChanged(s: Editable?) {
        timer.cancel()
        timer = Timer().apply {
            schedule(object : TimerTask() {
                override fun run() {
                    action(s.toString())
                }
            }, DELAY)
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    companion object {
        private const val DELAY = 500L
    }
}