package com.chicchan.diagrams.model

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.core.view.children
import androidx.core.view.isVisible
import androidx.core.view.marginTop
import com.google.android.material.textfield.TextInputEditText


class Shape( context:Context,drawable:Drawable ) {

    private var isButtonVisible=false
    private var imageView:ImageView
    var frameLayout=FrameLayout(context)
    private val btnSize=70
    var size=Pair(220,220)



    val btnUp=Button(context).apply {
        text="+"

    }
    val btnDown=Button(context).apply {
        text="+"
    }
    val btnLeft=Button(context).apply {
        text="+"
    }
    val btnRight=Button(context).apply {
        text="+"
    }

    val paramsDown = FrameLayout.LayoutParams(
        btnSize,
        btnSize,
        Gravity.BOTTOM or Gravity.CENTER_HORIZONTAL
    )
    val paramsUp = FrameLayout.LayoutParams(
        btnSize,
        btnSize,
        Gravity.TOP or Gravity.CENTER_HORIZONTAL
    )
    val paramsLeft = FrameLayout.LayoutParams(
        btnSize,
        btnSize,
        Gravity.CENTER_VERTICAL
    )

    val paramsRigth = FrameLayout.LayoutParams(
        btnSize,
        btnSize,
        Gravity.CENTER_VERTICAL or Gravity.RIGHT
    )

    val textBox=TextView(context).apply {
        maxLines=3
        minLines=3
        textSize=9F
        hint="Description"
        height=30
        textAlignment= View.TEXT_ALIGNMENT_CENTER
    }

    var text:String = ""
        set(value) {
            textBox.text = value
            field = value
        }

    var position:Pair<Float,Float> = Pair(0F,0F)
        get() = Pair(frameLayout.x,frameLayout.y)

    var center:Pair<Float,Float> = Pair(0F,0F)
        get() = Pair(frameLayout.x+(size.first/2),frameLayout.y+(size.second/2))


    var positionUp:Pair<Float,Float> = Pair(0F,0F)
        get() = Pair(frameLayout.x+(size.first/2),frameLayout.y)

    var positionDown:Pair<Float,Float> = Pair(0F,0F)
        get() = Pair(frameLayout.x+(size.first/2),size.second+frameLayout.y)

    var positionLeft:Pair<Float,Float> = Pair(0F,0F)
        get() = Pair(frameLayout.x,frameLayout.y+(size.second/2))

    var positionRight:Pair<Float,Float> = Pair(0F,0F)
        get() = Pair(frameLayout.x+size.first,frameLayout.y+(size.second/2))

    val paramsText = FrameLayout.LayoutParams(
        120,
        60,
        Gravity.CENTER
    )

    init {


        imageView= ImageView(context)
        imageView.scaleType=ImageView.ScaleType.FIT_XY
        imageView.setImageDrawable(drawable)
        frameLayout.addView(imageView)
        frameLayout.addView(textBox,paramsText)
        frameLayout.addView(btnLeft,paramsLeft)
        frameLayout.addView(btnRight,paramsRigth)
        frameLayout.addView(btnUp,paramsUp)
        frameLayout.addView(btnDown,paramsDown)

        showButtons()

    }

    fun showButtons(){
        btnUp.isVisible=isButtonVisible
        btnDown.isVisible=isButtonVisible
        btnLeft.isVisible=isButtonVisible
        btnRight.isVisible=isButtonVisible
        isButtonVisible=!isButtonVisible

    }


}