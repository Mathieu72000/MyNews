package com.corroy.mathieu.mynews.Controllers.Utils;

import com.corroy.mathieu.mynews.Models.Article;
import com.corroy.mathieu.mynews.Models.Search;
import io.reactivex.Observable;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MyNewsService {

    // Create a GET request on a URL complement (EndPoints)

    // TOP STORIES
    @GET("svc/topstories/v2/{section}.json")
    Observable<Article> getTopStoriesArticle(@Path("section") String section,
                                             @Query("api-key") String apiKey);

    // MOST POPULAR
    @GET("svc/mostpopular/v2/viewed/{period}.json")
    Observable<Article> getMostPopularArticle(@Path("period") int period,
                                              @Query("api-key") String apiKey2);

    // POLITICS
    @GET("svc/search/v2/articlesearch.json?api-key=pX69N3N5cVmjfynWXnSvWQ92GaxGuIAh&fq=news_desk:\"Politics\"&sort=newest")
    Observable<Search> getPoliticsArticle();

    // SEARCH
    @GET("svc/search/v2/articlesearch.json?api-key=pX69N3N5cVmjfynWXnSvWQ92GaxGuIAh&sort=newest")
    Observable<Search> getSearch(
            @Query("q") String query,
            @Query("begin_date") String b_date,
            @Query("end_date") String e_date,
            @Query("fq") String section);

    // Configure a new Retrofit Client
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://api.nytimes.com/") // URL racine
            .addConverterFactory(GsonConverterFactory.create()) // Sérialiseur / Désérialiseur
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build();
}