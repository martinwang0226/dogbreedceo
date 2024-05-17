package com.example.dogbreedjava.utils

import android.util.Log

/**
 * @Description
 * @Author chenlongwang
 * @CreateTime 2024/05/15
 */
object StringUtils {
    fun contains(originString: String?, searchString: String?): Boolean {
        val isContainSearchString: Boolean
        if (originString == null) {
            Log.d("DogSearchActivity1", "==originString is null")
            return false
        } else {
            Log.d("DogSearchActivity1", "==originString is $originString")
        }
        isContainSearchString = if (originString.contains(searchString!!)) {
            true
        } else {
            false
        }
        return isContainSearchString
    }

    @JvmStatic
    fun dogTypeFilter(originDogTypeNames: Array<String?>?, searchString: String?): Array<String?> {
        val dogFilterList: MutableList<String> = ArrayList()
        if (originDogTypeNames != null) {
            for (i in originDogTypeNames.indices) {
                originDogTypeNames[i]?.let { dogFilterList.add(it) }
            }
        }
        if (originDogTypeNames != null) {
            for (i in originDogTypeNames.indices) {
                val originString = originDogTypeNames[i]
                Log.d("DogSearchActivity1", "==i==$i")
                Log.d("DogSearchActivity1", "==originDogTypeList.size" + originDogTypeNames.size)
                if (!contains(originString, searchString)) {
                    dogFilterList.remove(originString)
                }
            }
        }
        val dogFilterNames = arrayOfNulls<String>(dogFilterList.size)
        for (i in dogFilterNames.indices) {
            dogFilterNames[i] = dogFilterList[i]
        }
        return dogFilterNames
    }
}
