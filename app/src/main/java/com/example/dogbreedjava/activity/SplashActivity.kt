package com.example.dogbreedjava.activity

import android.content.Intent
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.dogbreedjava.R
import com.example.dogbreedjava.presenter.FetchImageUrlPresenter
import com.example.dogbreedjava.presenter.IFetchImageUrlPresenter
import com.example.dogbreedjava.view.IImageFetchView

/**
 * @Description
 * @Author chenlongwang
 * @CreateTime 2024/05/15
 */
class SplashActivity : BaseActivity(), IImageFetchView {
    private val dogTypeName = "dachshund"
    private lateinit var dogImageUrls: Array<String?>
    private var imageView: ImageView? = null
    private var mTextField: TextView? = null
    private var splashTextView: TextView? = null
    private var fetchImageUrlPresenter: IFetchImageUrlPresenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.dog_breed_splash)
        super.onCreate(savedInstanceState)
        loadLogoView()
        startCountTimer()
    }

    override fun initPresenter() {
        fetchImageUrlPresenter = FetchImageUrlPresenter(this)
    }

    override fun networkRequest() {
        fetchImageUrls()
    }

    private fun fetchImageUrls() {
        fetchImageUrlPresenter!!.fetchImageUrlFromNet(dogTypeName)
    }

    override fun setupView() {
        imageView = findViewById(R.id.dog_ceo)
        mTextField = findViewById(R.id.countdown_timer)
        splashTextView = findViewById(R.id.dog_ceo_welcome)
    }

    private fun loadLogoView() {
        Glide.with(this)
            .load(R.drawable.dogceo)
            .transition(DrawableTransitionOptions.withCrossFade(5000))
            .into(imageView!!)
    }

    private fun startCountTimer() {
        val countDownTimer = object : CountDownTimer(5000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                mTextField!!.text = "remaining: " + millisUntilFinished / 1000 + "s"
            }

            override fun onFinish() {
                splashTextView!!.visibility = View.VISIBLE
                mTextField!!.text = "Go!"
                val intent = Intent(this@SplashActivity, DogAlbumActivity::class.java)
                intent.putExtra("dogImageUrls", dogImageUrls)
                startActivity(intent)
                finish()
            }
        }.start()
    }

    override fun initData() {}
    override fun fetchImageUrlsFailed() {}
    override fun fetchImageUrlsFromNet(imageUrls: Array<String?>) {
        dogImageUrls = imageUrls
    }
}
