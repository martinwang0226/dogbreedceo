package com.example.dogbreedjava.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.PagerAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.dogbreedjava.R

/**
 * @Description
 * @Author chenlongwang
 * @CreateTime 2024/05/15
 */
class DogViewPagerAdapter(
    private val context: Context,
    private val imageUrls: Array<String>?,
    private val currentIndex: Int
) : PagerAdapter() {
    override fun getCount(): Int {
        return imageUrls?.size ?: 0
    }

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view === `object`
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as View)
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val view = View.inflate(context, R.layout.dog_item_pic_detail, null)
        val textView = view.findViewById<TextView>(R.id.current_index)
        textView.text = (position + 1).toString() + "/" + imageUrls!!.size
        val imageView = view.findViewById<ImageView>(R.id.image_view)
        val imgUrl = imageUrls[position]
        if (imgUrl != null) {
            Glide.with(context)
                .asBitmap()
                .load(imageUrls[position])
                .placeholder(R.drawable.dogceo)
                .error(R.drawable.place_holder)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(imageView)
            container.addView(view)
        }
        return view
    }
}
