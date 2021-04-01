package com.example.mycustomedittext

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.widget.AppCompatEditText
import androidx.core.content.res.ResourcesCompat
import androidx.core.widget.addTextChangedListener

class EditTextWithClear : AppCompatEditText {

    constructor(context: Context) : super(context)
    constructor(context: Context, attrs: AttributeSet) : super(context, attrs)
    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    )

    private var clearButtonImage: Drawable =
        ResourcesCompat.getDrawable(resources, R.drawable.ic_clear_transparent_24, null)!!

    // ------------------------------------------------------------| Initializer |
    init {
        listenerChangeText()
    }

    // ------------------------------------------------------------| Show and Hide |
    private fun showClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, clearButtonImage, null)
    }

    private fun hideClearButton() {
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null)
    }

    // ------------------------------------------------------------| Listeners |
    private fun listenerChangeText() {

        this.addTextChangedListener(
            object : TextWatcher {

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {
                    // TODO: 01.04.2021
                }

                @SuppressLint("ClickableViewAccessibility")
                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                    showClearButton()

                    setOnTouchListener(
                        OnTouchListener { v, event ->
                            if (compoundDrawablesRelative[2] != null) {
                                var clearButtonStart: Int
                                var clearButtonEnd: Int
                                var isClearButtonClicked = false

                                if (layoutDirection == LAYOUT_DIRECTION_RTL) {
                                    clearButtonEnd = clearButtonImage.intrinsicWidth + paddingStart

                                    if (event.x < clearButtonEnd) {
                                        isClearButtonClicked = true
                                    }
                                } else {
                                    clearButtonStart =
                                        width - paddingEnd - clearButtonImage.intrinsicWidth

                                    if (event.x > clearButtonStart) {
                                        isClearButtonClicked = true
                                    }
                                }

                                if (isClearButtonClicked) {
                                    if (event.action == MotionEvent.ACTION_DOWN) {
                                        clearButtonImage = ResourcesCompat.getDrawable(
                                            resources,
                                            R.drawable.ic_clear_black_24,
                                            null
                                        )!!
                                        showClearButton()
                                    }

                                    if (event.action == MotionEvent.ACTION_UP) {
                                        clearButtonImage = ResourcesCompat.getDrawable(
                                            resources,
                                            R.drawable.ic_clear_transparent_24,
                                            null
                                        )!!
                                        text?.let { it.clear() }
                                        hideClearButton()
                                        return@OnTouchListener true
                                    }
                                } else {
                                    return@OnTouchListener false
                                }
                            }

                            false
                        }
                    )

                }

                override fun afterTextChanged(s: Editable?) {
                    // TODO: 01.04.2021
                }

            }
        )

    }
}