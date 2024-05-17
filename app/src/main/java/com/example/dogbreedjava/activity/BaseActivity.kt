package com.example.dogbreedjava.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
 * @Description
 * @Author chenlongwang
 * @CreateTime 2024/05/17
 */
abstract class BaseActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initData()
        setupView()
        initPresenter()
        networkRequest()
    }

    abstract fun initData()
    abstract fun setupView()
    abstract fun initPresenter()
    abstract fun networkRequest()
}
