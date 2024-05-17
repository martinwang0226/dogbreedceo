package com.example.dogbreedjava.net.api

import com.example.dogbreedjava.model.DogBreedImageResponse
import com.example.dogbreedjava.model.DogBreedsListResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

/**
 * @Description
 * @Author chenlongwang
 * @CreateTime 2024/05/14
 */
interface DogBreedApi {
    @GET("breeds/list/all/")
    fun fetchDogBreeds(): Call<DogBreedsListResponse?>?

    @GET("breed/{breed_name}/images/")
    fun fetchDogBreedImages(@Path("breed_name") breedDogName: String?): Call<DogBreedImageResponse?>?
}
