package com.chicchan.diagrams.view

import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.chicchan.diagrams.R
import com.chicchan.diagrams.databinding.MainListItemBinding
import com.chicchan.diagrams.viewmodel.MainViewModel

class DrawsAdapter(var items:List<Drawable>,val mainViewModel:MainViewModel):RecyclerView.Adapter<DrawsViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DrawsViewHolder {

        val inflater=LayoutInflater.from(parent.context)
        val binding=MainListItemBinding.inflate(inflater,parent,false)
        return DrawsViewHolder(binding)

    }

    override fun onBindViewHolder(holder: DrawsViewHolder, position: Int) {

        with(holder.bind){
            mainListItemIv.setImageDrawable(items[position])
            root.setOnClickListener {
                mainViewModel.addShape(items[position])
            }
        }

    }

    override fun getItemCount()=items.size


}

class DrawsViewHolder(val bind:MainListItemBinding) :RecyclerView.ViewHolder(bind.root){
}