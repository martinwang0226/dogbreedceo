package com.example.dogbreedjava.view

/**
 * @Description
 * @Author chenlongwang
 * @CreateTime 2024/05/16
 */
interface IImageFetchView {
    fun fetchImageUrlsFromNet(imageUrls: Array<String?>)
    fun fetchImageUrlsFailed()
}
