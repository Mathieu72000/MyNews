package com.corroy.mathieu.mynews.Controllers.Utils;

import com.corroy.mathieu.mynews.Models.Article;
import com.corroy.mathieu.mynews.Models.Result;

import java.util.List;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

// NETWORK CALLS

public class MyNewsStreams {

    // TopStories Streams

    public static Observable<Article> streamFetchTopStories(String section, String key){
        MyNewsService myNewsService = MyNewsService.retrofit.create(MyNewsService.class);

        return myNewsService.getTopStoriesArticle(section, key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }

    // Search Streams

    public static Observable<Result> streamFetchSearch(String query, String start_date, String end_date, String section){
        MyNewsService myNewsService = MyNewsService.retrofit.create(MyNewsService.class);

        return myNewsService.getSearch(query, start_date, end_date, section)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}
