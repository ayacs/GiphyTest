package com.example.giphytest

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintSet
import com.giphy.sdk.core.models.enums.MediaType
import com.giphy.sdk.ui.pagination.GPHContent
import com.giphy.sdk.ui.views.GiphyGridView
import kotlinx.android.synthetic.main.main_activity.*

class MainActivity : AppCompatActivity(R.layout.main_activity) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        gifsGridView.direction = Config.direction
        gifsGridView.spanCount = Config.spanCount
        gifsGridView.cellPadding = Config.cellPadding
        gifsGridView.fixedSizeCells = Config.fixedSizeCells
        gifsGridView.showCheckeredBackground = Config.showCheckeredBackground
        if (Config.mediaType == MediaType.emoji) {
            searchInput.isEnabled = false
        }

        if (Config.direction == GiphyGridView.HORIZONTAL) {
            val constraints = ConstraintSet()
            constraints.clone(parentView)
            constraints.clear(R.id.gifsGridView, ConstraintSet.BOTTOM)
            constraints.constrainHeight(R.id.gifsGridView, 200 * Config.spanCount)
            constraints.applyTo(parentView)
        }

        searchInput.setOnEditorActionListener { view, actionId, event ->
            if (actionId == EditorInfo.IME_ACTION_SEARCH || actionId == EditorInfo.IME_NULL || actionId == EditorInfo.IME_ACTION_GO) {
                dismissKeyboard()
                performCustomSearch()
                return@setOnEditorActionListener true
            }
            false
        }

        searchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) = Unit

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) = Unit

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                performCustomSearch()
            }
        })
    }

    private fun performCustomSearch() {
        if (!searchInput.text.isNullOrEmpty())
            gifsGridView?.content =
                GPHContent.searchQuery(searchInput.text.toString(), Config.mediaType)
    }

    fun dismissKeyboard() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(searchInput.windowToken, 0)
    }


}