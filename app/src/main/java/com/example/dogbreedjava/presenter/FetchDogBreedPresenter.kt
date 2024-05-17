package com.example.dogbreedjava.presenter

import com.example.dogbreedjava.model.DogBreedsListResponse
import com.example.dogbreedjava.net.api.DogBreedApi
import com.example.dogbreedjava.view.IDogTypeView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @Description
 * @Author chenlongwang
 * @CreateTime 2024/05/16
 */
class FetchDogBreedPresenter(private val iDogTypeView: IDogTypeView?) : IFetchDogBreedsPresenter {
    override fun fetchDogBreeds() {
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dog.ceo/api/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val api = retrofit.create(DogBreedApi::class.java)
        val dataCall = api.fetchDogBreeds()
        dataCall!!.enqueue(object : Callback<DogBreedsListResponse?> {
            override fun onResponse(
                call: Call<DogBreedsListResponse?>,
                response: Response<DogBreedsListResponse?>
            ) {
                val dogBreedsListResponse = response.body()
                val status = dogBreedsListResponse!!.status
                if (status == "success") {
                    val dogTypeNameMap = dogBreedsListResponse.message
                    var num = 0
                    val dogTypeNames = dogTypeNameMap?.let { arrayOfNulls<String>(it.size) }
                    if (dogTypeNameMap != null) {
                        for (key in dogTypeNameMap.keys) {
                            dogTypeNames?.set(num, key)
                            num++
                        }
                    }
                    iDogTypeView?.fetchDogBreeds(dogTypeNameMap, dogTypeNames)
                } else {
                    iDogTypeView?.fetchDogBreedsFailed()
                }
            }

            override fun onFailure(call: Call<DogBreedsListResponse?>, t: Throwable) {
                iDogTypeView?.fetchDogBreedsFailed()
            }
        })
    }
}
