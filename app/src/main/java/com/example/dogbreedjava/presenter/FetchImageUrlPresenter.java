package com.example.dogbreedjava.presenter;

import com.example.dogbreedjava.net.api.DogBreedApi;
import com.example.dogbreedjava.model.DogBreedImageResponse;
import com.example.dogbreedjava.view.IImageFetchView;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @Description
 * @Author chenlongwang
 * @CreateTime 2024/05/16
 */
public class FetchImageUrlPresenter implements IFetchImageUrlPresenter {
    private IImageFetchView iImageFetchView;

    public FetchImageUrlPresenter(IImageFetchView iImageFetchView) {
        this.iImageFetchView = iImageFetchView;

    }

    @Override
    public void fetchImageUrlFromNet(String dogBreed) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dog.ceo/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        DogBreedApi api = retrofit.create(DogBreedApi.class);
        Call<DogBreedImageResponse> dataCall = api.fetchDogBreedImages(dogBreed);
        dataCall.enqueue(new Callback<DogBreedImageResponse>() {
            @Override
            public void onResponse(Call<DogBreedImageResponse> call, Response<DogBreedImageResponse> response) {
                String status = null;
                if (response != null && response.body() != null) {
                    status = response.body().status;
                }
                if (status.equals("success")) {
                    List<String> dogImageUrlList = response.body().message;
                    String[] dogImageUrls = new String[dogImageUrlList.size()];
                    for (int i = 0; i < dogImageUrlList.size(); i++) {
                        dogImageUrls[i] = dogImageUrlList.get(i);
                    }
                    if (iImageFetchView != null) {
                        iImageFetchView.fetchImageUrlsFromNet(dogImageUrls);
                    }
                } else {
                    if (iImageFetchView != null) {
                        iImageFetchView.fetchImageUrlsFailed();
                    }
                }
            }

            @Override
            public void onFailure(Call<DogBreedImageResponse> call, Throwable t) {
                if (iImageFetchView != null) {
                    iImageFetchView.fetchImageUrlsFailed();
                }
            }
        });

    }
}
