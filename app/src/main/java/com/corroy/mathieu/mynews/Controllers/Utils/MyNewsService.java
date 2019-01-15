package com.corroy.mathieu.mynews.Controllers.Utils;

import com.corroy.mathieu.mynews.Models.Article;
import com.corroy.mathieu.mynews.Models.Result;
import java.util.List;
import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

// Top Stories API

public interface MyNewsService {

    // TOP STORIES

    // Create a GET request on a URL complement (EndPoints)
    @GET("svc/topstories/v2/{section}.json")
    Observable<Article> getTopStoriesArticle(@Path("section") String section,
                                             @Query("api-key") String apiKey);

    // SEARCH
    @GET("svc/search/v2/articlesearch.json?api-key=b8ce4364a4f24d9e8159ad992ca61a45&sort=newest")
    Observable<Result> getSearch(
            @Query("q") String query,
            @Query("begin_date") String b_date,
            @Query("end_date") String e_date,
            @Query("fq") String section2);

    // Configure a new Retrofit Client
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://api.nytimes.com") // URL racine
            .addConverterFactory(GsonConverterFactory.create()) // Sérialiseur / Désérialiseur
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}