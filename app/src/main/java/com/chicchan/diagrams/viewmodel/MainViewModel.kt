package com.chicchan.diagrams.viewmodel

import android.app.Application
import android.content.ContentValues.TAG
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.Point
import android.graphics.drawable.Drawable
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.AbsoluteLayout
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chicchan.diagrams.common.Colors
import com.chicchan.diagrams.common.position
import com.chicchan.diagrams.model.Connection
import com.chicchan.diagrams.model.Shape
import java.io.IOException
import java.io.InputStream




class MainViewModel(application: Application) :AndroidViewModel(application) {


    var colorShapes:Colors=Colors.BLUE

    private val _dimensions= MutableLiveData<Pair<Int,Int>>()
    val dimensions:LiveData<Pair<Int,Int>> = _dimensions

    private val _draws=MutableLiveData<List<Drawable>>()
    val draws:LiveData<List<Drawable>> = _draws

    private val _shapes=MutableLiveData<MutableList<Shape>>().apply {
        value= mutableListOf()
    }
    val shapes:LiveData<MutableList<Shape>> = _shapes

    private val _lines=MutableLiveData<MutableList<Connection>>().apply {
        value= mutableListOf()
    }
    val lines:LiveData<MutableList<Connection>> =_lines

    private val _bitmap=MutableLiveData<Bitmap>()
    val bitmap:LiveData<Bitmap> =_bitmap

    private val _connectionDelete=MutableLiveData<Connection?>()
    val connectionDelete:LiveData<Connection?> = _connectionDelete


    val _application=application

    fun loadDimensions()
    {
        _dimensions.value=getDimensions()
    }

    private fun getDimensions():Pair<Int,Int>{
        val size = Point()
        val wm = _application.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        display.getSize(size)
        return Pair(size.x,size.y)

    }

    fun generateDraws(){
       _draws.value=listAssetFiles("images", colorShapes.color)
    }


    private fun listAssetFiles(path: String,color: String): List<Drawable> {
        val list: Array<String>
        val drawsList= mutableListOf<Drawable>()
        try {
            list = _application.getAssets().list(path) as Array<String>
            if (list.size > 0) {
                for (file in list) {
                      if(!file.contains(color)){
                          continue
                      }
                      val ims: InputStream = _application.assets.open("$path/$file")
                      Drawable.createFromStream(ims, null)?.let {
                         drawsList.add(it)
                      }
                }
            }
        } catch (e: IOException) {
            return emptyList()
        }
        return drawsList
    }

    fun addShape(draw:Drawable)
    {
        Log.d(TAG, "addShape:OK ")
        val shape=Shape(context = _application, drawable = draw)
        _shapes.value?.add(shape)
        _shapes.value=_shapes.value
    }

    fun setColor(color: Colors) {
        colorShapes=color
        generateDraws()
    }

    fun addLine(initShape:Shape, initPoint: position, endShape:Shape, endPoint:position){
        val connection=Connection(initShape,initPoint,endShape,endPoint)
        _lines.value?.add(connection)
        _lines.value=_lines.value

         genGraph()

    }

    fun genGraph(){
        val conections= mutableListOf<List<Pair<Double,Double>>>()
        for(connection in lines.value!!)
        {
            val pointsForLine=LineBuilder().createLine(
                item1=Pair(connection.shapeIni,connection.pointInit),
                item2 = Pair(connection.shapeEnd,connection.pointEnd)
            )
            conections.add(pointsForLine)

        }

        val graph=Graph(getdimensions(),conections)
        _bitmap.value=graph.createGraphic()
    }

    fun getdimensions():Pair<Float,Float>{
        val size = Point()
        val wm = _application.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = wm.defaultDisplay
        display.getSize(size)
      //  Log.d(TAG, "getdimensions: $size")
        return Pair(size.x.toFloat(),size.y.toFloat())

    }


    fun deleteShape(shape:Shape){
        _shapes.value?.let {
            deleteAllConnectionWithShape(shape)
            it.remove(shape)
            val parent=shape.frameLayout.parent as AbsoluteLayout
            parent.removeView(shape.frameLayout)
            genGraph()
        }
    }

    private  fun deleteAllConnectionWithShape(shape:Shape )
    {
        val linesToDelete= mutableListOf<Connection>()
        for(line in lines.value!!){
            if (line.shapeIni==shape || line.shapeEnd==shape){
                linesToDelete.add(line)
            }
        }
        _lines.value!!.removeAll(linesToDelete)
    }

    fun verifyLine(shape:Shape,pos:position){
        for(connection in lines.value!!){

            if(connection.shapeIni==shape && connection.pointInit==pos)
            {
                _connectionDelete.value=connection
            }
        }
        _connectionDelete.value=null
    }

    fun deleteConnectionShape(connection:Connection){
        _lines.value?.remove(connection)
        genGraph()
    }



}