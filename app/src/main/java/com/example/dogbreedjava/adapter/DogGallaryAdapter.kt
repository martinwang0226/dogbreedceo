package com.example.dogbreedjava.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.dogbreedjava.R
import com.example.dogbreedjava.adapter.DogGallaryAdapter.DogGallaryViewHolder
import com.example.dogbreedjava.listener.DogItemClickListener

/**
 * @Description
 * @Author chenlongwang
 * @CreateTime 2024/05/14
 */
class DogGallaryAdapter(private val mContext: Context) :
    RecyclerView.Adapter<DogGallaryViewHolder>() {
    private val mLayoutInflater: LayoutInflater = LayoutInflater.from(mContext)
    private var imageViewUrl: Array<String?>? = null
    private var dogItemClickListener: DogItemClickListener? = null

    init {
        Log.d("DogGalleryActivity", "=====DogGallaryAdapter construct")
    }

    fun setOnItemClickListener(listener: DogItemClickListener?) {
        dogItemClickListener = listener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DogGallaryViewHolder {
        val view = mLayoutInflater.inflate(R.layout.item_imageview, parent, false)
        val dogGallaryViewHolder = DogGallaryViewHolder(view)
        Log.d("DogGalleryActivity", "=====DogGallaryAdapter onCreateViewHolder")
        return dogGallaryViewHolder
    }

    override fun onBindViewHolder(holder: DogGallaryViewHolder, position: Int) {
        Log.d("DogGalleryActivity", "=====DogGallaryAdapter onBindViewHolder")
        if (imageViewUrl != null && imageViewUrl!!.size > 0) {
            Glide.with(mContext)
                .asBitmap()
                .load(imageViewUrl!![position])
                .placeholder(R.drawable.dogceo)
                .error(R.drawable.place_holder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .centerCrop()
                .into(holder.imageView)
            //                    .into(new GlideRatioScaleTransForm(holder.imageView));
        }
        holder.imageView.setOnClickListener {
            if (dogItemClickListener != null) {
                dogItemClickListener!!.OnItemClickListener(imageViewUrl!![position], position)
            }
        }
    }

    fun updateDataChanged(dogTypeName: List<String?>) {
        Log.d("DogGalleryActivity", "=====DogGallaryAdapter updateDataChanged")
        val size = dogTypeName.size
        imageViewUrl = arrayOfNulls(size)
        for (i in 0 until size) {
            imageViewUrl!![i] = dogTypeName[i]
        }
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return if (imageViewUrl == null || imageViewUrl!!.size == 0) 8 else imageViewUrl!!.size
    }

    class DogGallaryViewHolder(itemView: View) : ViewHolder(itemView) {
        val imageView: ImageView

        init {
            imageView = itemView.findViewById(R.id.image_view)
        }
    }

}
