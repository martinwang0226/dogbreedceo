package com.example.dogbreedjava.view

/**
 * @Description
 * @Author chenlongwang
 * @CreateTime 2024/05/16
 */
interface IDogTypeView {
    fun fetchDogBreeds(dogTypeNameMap: Map<String, List<String>>?, dogTypeNames: Array<String?>?)
    fun fetchDogBreedsFailed()
}
