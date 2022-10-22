package org.geeksforgeeks.gfgmodalsheet

import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import androidx.activity.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.chicchan.diagrams.R
import com.chicchan.diagrams.common.Colors
import com.chicchan.diagrams.view.DrawsAdapter
import com.chicchan.diagrams.viewmodel.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import java.io.InputStream


class BottomSheet(val mainViewModel:MainViewModel) : BottomSheetDialogFragment() {

    var adapterDraws: DrawsAdapter?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(
            R.layout.bottom_sheet_layout,
            container, false
        )

        view.findViewById<Button>(R.id.bottom_bt_blue).setOnClickListener {
        mainViewModel.setColor(Colors.BLUE)
        }
        view.findViewById<Button>(R.id.bottom_bt_red).setOnClickListener {
            mainViewModel.setColor(Colors.RED)
        }
        view.findViewById<Button>(R.id.bottom_bt_green).setOnClickListener {
            mainViewModel.setColor(Colors.GREEN)
        }
        view.findViewById<Button>(R.id.bottom_bt_gray).setOnClickListener {
            mainViewModel.setColor(Colors.GRAY)
        }
        view.findViewById<Button>(R.id.bottom_bt_orange).setOnClickListener {
            mainViewModel.setColor(Colors.ORANGE)
        }
        view.findViewById<Button>(R.id.bottom_bt_white).setOnClickListener {
            mainViewModel.setColor(Colors.WHITE)
        }
        view.findViewById<Button>(R.id.bottom_bt_yellow).setOnClickListener {
            mainViewModel.setColor(Colors.YELLOW)
        }

      adapterDraws?.let {
          val rv =view.findViewById<RecyclerView>(R.id.bottom_rv)
          rv.layoutManager= GridLayoutManager(context,5)
          rv.adapter=adapterDraws
      }
      return view
    }
}