package com.waracle.cakelist.ui.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.waracle.cakelist.R
import com.waracle.cakelist.data.model.Cake
import kotlinx.android.synthetic.main.item_cake.view.*

class CakeAdapter : ListAdapter<Cake, CakeViewHolder>(diffItemCallback) {

    companion object {
        private val diffItemCallback = object : DiffUtil.ItemCallback<Cake>() {
            override fun areItemsTheSame(oldItem: Cake, newItem: Cake): Boolean = oldItem == newItem

            override fun areContentsTheSame(oldItem: Cake, newItem: Cake): Boolean =
                oldItem == newItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CakeViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_cake, parent, false)
        return CakeViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CakeViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}

class CakeViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    fun bind(cake: Cake) {
        itemView.apply {
            // Some URLs with http scheme don't load so replacing with https fixes
            Glide.with(this).load(cake.image.replace("http://", "https://")).into(cake_image)
            cake_title.text = cake.title.capitalize()
            setOnClickListener {
                AlertDialog.Builder(context)
                    .setTitle(cake.title.capitalize())
                    .setMessage(cake.desc.capitalize())
                    .setPositiveButton(android.R.string.ok, null)
                    .show()
            }
        }
    }
}