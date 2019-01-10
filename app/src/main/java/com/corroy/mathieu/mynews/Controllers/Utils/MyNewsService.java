package com.corroy.mathieu.mynews.Controllers.Utils;

import com.corroy.mathieu.mynews.Models.Article;
import java.util.List;
import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;

// Top Stories API

public interface MyNewsService {
    // Create a GET request on a URL complement (EndPoints)
    @GET("svc/topstories/v2/{section}.json")
    Observable<List<Article>> getTopStoriesArticle(@Path("section") String section);

    // Configure a Retrofit Object
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://api.nytimes.com/") // URL racine
            .addConverterFactory(GsonConverterFactory.create()) // Sérialiseur / Désérialiseur
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}
