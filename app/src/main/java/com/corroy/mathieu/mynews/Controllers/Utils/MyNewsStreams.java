package com.corroy.mathieu.mynews.Controllers.Utils;

import com.corroy.mathieu.mynews.Models.Article;
import com.corroy.mathieu.mynews.Models.Doc;
import com.corroy.mathieu.mynews.Models.Result;
import com.corroy.mathieu.mynews.Models.Search;

import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

// NETWORK CALLS

public class MyNewsStreams {

    // TopStories Streams

    public static Observable<Article> streamFetchTopStories(String section, String apiKey){
        MyNewsService myNewsService = MyNewsService.retrofit.create(MyNewsService.class);
        return myNewsService.getTopStoriesArticle(section, apiKey)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    // MostPopular Streams

    public static Observable<Article> streamFetchMostPopular(int period, String apiKey2){
        MyNewsService myNewsService = MyNewsService.retrofit.create(MyNewsService.class);
        return myNewsService.getMostPopularArticle(period, apiKey2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    // Politics Streams

    public static Observable<Search> streamFetchPolitics(){
        MyNewsService myNewsService = MyNewsService.retrofit.create(MyNewsService.class);
        return myNewsService.getPoliticsArticle()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    // Search Streams

    public static Observable<Search> streamFetchSearch(String query, String start_date, String end_date, String section){
        MyNewsService myNewsService = MyNewsService.retrofit.create(MyNewsService.class);
        if(query.isEmpty()) query = null;
        if(start_date.isEmpty()) start_date = null;
        if(end_date.isEmpty()) end_date = null;
        return myNewsService.getSearch(query, start_date, end_date, section)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}