package com.chicchan.diagrams.viewmodel

import android.content.ContentValues.TAG
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.ImageView
import kotlin.math.cos
import kotlin.math.sin

class Graph( var dimensions:Pair<Float,Float>,val listLines:List<List<Pair<Double,Double>>> ) {
    private var width: Float=0F
    private var height: Float =0F
    private var center:Pair<Float,Float> = Pair(0F,0F)
    private var canvas: Canvas?=null
    private  lateinit var img: ImageView
    private lateinit var paint: Paint
    private lateinit var bitmap: Bitmap


    init {
        width=dimensions.first*2
        height=dimensions.second*2
        center=Pair(width/2F,height/2F)
        Log.d("DEB","$dimensions")
        configFrame()
    }

    private fun configFrame(){
        paint = Paint()
        bitmap = Bitmap.createBitmap(width.toInt(), height.toInt(), Bitmap.Config.ARGB_8888)
        canvas= Canvas(bitmap)
        canvas?.let {
            with(paint) {
                color = android.graphics.Color.BLACK
                strokeWidth = 2.5F
            }
            paintDiagram()
        }
    }


    val Number.toPx get() = TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this.toFloat(),
        Resources.getSystem().displayMetrics)

    private fun paintDiagram()
    {
        //val points=listPoints as List<Pair<Float,Float>>
        val lines=listLines as List<List<Pair<Float,Float>>>

    //    Log.d(TAG, "paintDiagram:  ")


        canvas?.let {
          for(points in lines){
            for(pointid in points.indices){
                if(pointid+1 < points.size){
                    it.drawLine( points[pointid].first, points[pointid].second, points[pointid+1].first, points[pointid+1].second, paint)
                }

            }
            it.drawCircle(points.last().first, points.last().second,7F,paint)
          }
            //it.drawLine(0F,0F, 100F, 138F, paint)
        }
    }

    fun createGraphic():Bitmap
    {
        configFrame()
        paint.color= Color.BLUE
        paint.strokeWidth=4F
        return bitmap
    }
}