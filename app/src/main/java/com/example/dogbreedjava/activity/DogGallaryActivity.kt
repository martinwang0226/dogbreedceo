package com.example.dogbreedjava.activity

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.viewpager.widget.ViewPager
import com.example.dogbreedjava.R
import com.example.dogbreedjava.adapter.DogViewPagerAdapter

/**
 * @Description
 * @Author chenlongwang
 * @CreateTime 2024/05/15
 */
class DogGallaryActivity : BaseActivity() {
    private var viewPager: ViewPager? = null
    private var dogImageUrls: Array<String>? = null
    private var currentIndex = 0
    private var imageView: ImageView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.album_dog_show)
        super.onCreate(savedInstanceState)
    }

    override fun setupView() {
        imageView = findViewById(R.id.back_icon)
        imageView?.setOnClickListener(View.OnClickListener { v: View? -> finish() })

        viewPager = findViewById(R.id.view_pager)
        viewPager?.adapter = DogViewPagerAdapter(this, dogImageUrls, currentIndex)
        viewPager?.pageMargin = 20
        viewPager?.offscreenPageLimit = 3
        viewPager?.currentItem = currentIndex
    }

    override fun initPresenter() {}
    override fun initData() {
        val intent = intent
        dogImageUrls = intent.getStringArrayExtra("dogTypeName")
        currentIndex = intent.getIntExtra("currentIndex", 0)
    }

    override fun networkRequest() {}
}
