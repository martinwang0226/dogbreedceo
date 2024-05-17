package com.example.dogbreedjava.listener

/**
 * @Description
 * @Author chenlongwang
 * @CreateTime 2024/05/15
 */
fun interface DogItemClickListener {
    fun OnItemClickListener(dogTypeName: String?, currentIndex: Int)
}
