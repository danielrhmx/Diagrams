package com.chicchan.diagrams.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.*
import android.view.View.MeasureSpec
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.contains
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.chicchan.diagrams.R
import com.chicchan.diagrams.common.position
import com.chicchan.diagrams.databinding.ActivityMainBinding
import com.chicchan.diagrams.model.Connection
import com.chicchan.diagrams.model.Shape
import com.chicchan.diagrams.viewmodel.MainViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.geeksforgeeks.gfgmodalsheet.BottomSheet
import java.io.*


class MainActivity : AppCompatActivity()  {

    private var _binding:ActivityMainBinding?=null
    private val binding get()=_binding!!
    private val mainViewModel:MainViewModel by viewModels()
    private var gDetector: GestureDetectorCompat? = null
    private lateinit var bottomSheet:BottomSheet
    private var shapeSelected:Shape?=null
    private var initialShape:Pair<Shape,position>? = null

    private var shapes:List<Shape>?=null
    private var currentX:Int=0
    private var currentY:Int=0
    private var filePermisons=false


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: DIAGRAMS")
        _binding=ActivityMainBinding.inflate(layoutInflater)

        bottomSheet= BottomSheet(mainViewModel)
        mainViewModel.loadDimensions()
        setPremisions()

        mainViewModel.dimensions.observe(this, Observer {
            binding.mainImageView.layoutParams.width=it.first.toInt()*2
            binding.mainImageView.layoutParams.height=it.second.toInt()*2
        })

        mainViewModel.draws.observe(this, Observer {
           // Log.d(TAG, "onCreate: number ${binding}")
            if(bottomSheet.isVisible){
                bottomSheet.dismiss()
            }
            bottomSheet.adapterDraws=DrawsAdapter(it,mainViewModel)
            bottomSheet.show(supportFragmentManager,"ModalBottomSheet")
        })

        mainViewModel.shapes.observe(this,Observer{ shapes ->
            if(bottomSheet!=null && bottomSheet.isVisible){
                bottomSheet.dismiss()
            }
            if(shapes.isNotEmpty()){
                this.shapes=shapes
                configureShape()
            }
        })

        mainViewModel.lines.observe(this,Observer{
            Log.d(TAG, "onCreate:observe lines ${it.size} ")
        })

        mainViewModel.bitmap.observe(this,Observer{
            Log.d(TAG, "onCreate:observe bitmap  ")
            binding.mainImageView.setImageBitmap(it)
        })

        mainViewModel.connectionDelete.observe(this,Observer{

           if(it==null){

             //  Toast.makeText(this, "Select a final point", Toast.LENGTH_SHORT).show()

           }
            else{
               connectionDeleteDialog(it)
               initialShape= null
           }
        })

        setContentView(binding.root)
       // this.gDetector = GestureDetectorCompat(this, this)
       // gDetector?.setOnDoubleTapListener(this)
       // binding.mainImageView.setOnTouchListener(this)

    }




    @SuppressLint("ClickableViewAccessibility")
    fun enableScrolls(enable:Boolean){
         if(enable)
         {
             binding.mainScrollView.setOnTouchListener(null)
             binding.mainScrollViewHorizontal.setOnTouchListener(null)
             initialShape=null

         }
         else
         {

             binding.mainScrollView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                 Log.d(TAG, "enableScrolls Y: $scrollY ")
                 currentY=scrollY
             }
             binding.mainScrollViewHorizontal.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
                 Log.d(TAG, "enableScrolls X: $scrollX ")
                 currentX=scrollX
             }

             binding.mainScrollView.setOnTouchListener { _, event ->moveShape(event) }
             binding.mainScrollViewHorizontal.setOnTouchListener { _, event ->moveShape(event) }

         }
     }


    @SuppressLint("ClickableViewAccessibility")
    fun configureShape(){

        shapes?.last()?.let { shape ->
            val gestureDetector = GestureDetector(this,
                object: GestureDetector.SimpleOnGestureListener() {
                    override fun onDoubleTap(e: MotionEvent): Boolean {
                        Log.d(TAG, "onDoubleTap: ")
                        shapeDialog(shape)
                        return true
                    }

                } )


            if (!binding.mainAl.contains(shape.frameLayout)) {
                binding.mainAl.addView(shape.frameLayout, shape.size.first, shape.size.second)
                shape.frameLayout.x = 100F
                shape.frameLayout.setOnLongClickListener {
                    shapeSelected = shape
                    shape.frameLayout.setBackgroundColor(Color.BLACK)
                    enableScrolls(false)
                    false
                }
                shape.frameLayout.setOnTouchListener { v, event ->
                    if (event.action == MotionEvent.ACTION_UP) {
                        shapeSelected = null
                        enableScrolls(true)
                        shape.frameLayout.setBackgroundColor(Color.TRANSPARENT)

                    }
                    gestureDetector.onTouchEvent(event)

                    false
                }
                shape.frameLayout.setOnClickListener {
                    showShapeButtons()
                }
                shape.btnUp.setOnClickListener {
                    paintLine(shape,position.UP)
                }
                shape.btnDown.setOnClickListener {
                    paintLine(shape,position.DOWN)
                }
                shape.btnLeft.setOnClickListener {
                    paintLine(shape,position.LEFT)
                }
                shape.btnRight.setOnClickListener {
                    paintLine(shape,position.RIGTH)
                }

            }
        }
    }

    private  fun showShapeButtons(){
        shapes?.let {
            for(sh in it){
                sh.showButtons()
            }
        }

    }
    private fun connectionDeleteDialog(connection:Connection) {

        MaterialAlertDialogBuilder(this)
            .setTitle("Delete Connection")
            .setMessage("Delete this connection? ")
            .setNegativeButton("No") { dialog, which ->
                // Respond to negative button press
            }
            .setPositiveButton("SI") { dialog, which ->
                mainViewModel.deleteConnectionShape(connection)
            }
            .show()
    }

    private fun shapeDialog(shape:Shape)
    {

        val dialogView=layoutInflater.inflate(R.layout.dialog_shape,null)
        val textDialog=dialogView.findViewById<EditText>(R.id.dialog_shape_et)
        textDialog.setText(shape.text)

        val btn=dialogView.findViewById<Button>(R.id.dialog_delete_btn)


        val dialog=  MaterialAlertDialogBuilder(this)
            .setView(dialogView)
            .setTitle("Shape")
            .setNeutralButton("cancel") { dialog, which ->
            }
            .setPositiveButton("SI") { dialog, which ->
                shape.text=textDialog.text.toString()
            }.show()

        btn.setOnClickListener {
            shapeDeleteDialog(shape,dialog)
        }
    }

    private fun fileDialog()
    {

        val dialogView=layoutInflater.inflate(R.layout.dialog_shape,null)
        val textDialog=dialogView.findViewById<EditText>(R.id.dialog_shape_et)
        textDialog.hint="Filename"
        val btn=dialogView.findViewById<Button>(R.id.dialog_delete_btn)
        btn.isVisible=false

        MaterialAlertDialogBuilder(this)
            .setView(dialogView)
            .setTitle("File")
            .setNeutralButton("cancel") { dialog, which ->
            }
            .setPositiveButton("SI") { dialog, which ->
                val fileName=textDialog.text.toString()
                if(fileName.isNotEmpty()){
                    exportDiagram(fileName)
                }
            }.show()


    }
    private fun shapeDeleteDialog(shape:Shape, dialogShape: AlertDialog)
    {
        MaterialAlertDialogBuilder(this)
            .setTitle("Delete Shape")
            .setMessage("Delete this shape and connections")
            .setNegativeButton("No") { dialog, which ->
                // Respond to negative button press
            }
            .setPositiveButton("SI") { dialog, which ->
                mainViewModel.deleteShape(shape)
                dialogShape.dismiss()
            }
            .show()
    }


    fun paintLine(shape:Shape,point: position)
    {

        if(initialShape==null)
        {
            initialShape= Pair(shape,point)
            mainViewModel.verifyLine(shape,point)
            if(initialShape!=null)
            {
                Toast.makeText(this, "Select a final point", Toast.LENGTH_SHORT).show()

            }


       }
        else
        {
            mainViewModel.addLine(
                initShape = initialShape?.first!!,
                initPoint = initialShape?.second!!,
                endShape = shape,
                endPoint = point
                )
            initialShape=null
            showShapeButtons()
        }

    }
    fun moveShape(event: MotionEvent):Boolean{
        shapeSelected?.let {
            it.frameLayout.x=event.x+currentX
            it.frameLayout.y=event.y+currentY
            Log.d(TAG, "move: ${event.x} , ${event.y}")
        }
        if(event.action==MotionEvent.ACTION_UP)
        {
            shapeSelected?.frameLayout?.setBackgroundColor(Color.TRANSPARENT)
            shapeSelected=null
            enableScrolls(true)
        }
         mainViewModel.genGraph()

        return true
    }



    override fun onTouchEvent(event: MotionEvent): Boolean {
        this.gDetector?.onTouchEvent(event)
        Log.d(TAG, "onTouchevent: ${event.x},${event.y} ")
        return super.onTouchEvent(event)
    }


    

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu,menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return when (item.itemId) {
            R.id.menu_items -> {
               mainViewModel.generateDraws()
               true
            }
            R.id.menu_export->{
                if(filePermisons){
                    fileDialog()
                }
                else{
                    Toast.makeText(this, "This app requires File Write Permission", Toast.LENGTH_SHORT).show()
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    fun exportDiagram(fileName:String){
       if(fileName.isEmpty()){return}
       val bm=getBitmapFromView(binding.mainLayOut)
       val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS)
       val file= File(path,"$fileName.png")
       val outStream:OutputStream= FileOutputStream(file)
       if(bm?.compress(Bitmap.CompressFormat.PNG, 90, outStream)==true )
       {
           Toast.makeText(this, "File $fileName.png saved in DOWNLOADS directory", Toast.LENGTH_SHORT).show()

       }
       outStream.close()

    }


    fun getBitmapFromView(view: View): Bitmap? {
        view.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED)
        val bitmap = Bitmap.createBitmap(
            view.measuredWidth, view.measuredHeight,
            Bitmap.Config.ARGB_8888
        )
        val canvas = Canvas(bitmap)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
        view.draw(canvas)
        return bitmap
    }

    
    fun setPremisions():Boolean{
        var res=false
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.WRITE_EXTERNAL_STORAGE, false) -> {
                    Log.d(TAG, "setPermisions: OK")
                    filePermisons=true                }
                else -> {
               
            }
            }
        }

        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE))
        return res
    }




}

