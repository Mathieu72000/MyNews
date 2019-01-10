package com.corroy.mathieu.mynews.Controllers.Utils;

import com.corroy.mathieu.mynews.Models.Article;
import java.util.List;
import java.util.concurrent.TimeUnit;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

// NETWORK CALLS

public class MyNewsStreams {

    // TopStories Streams

    public static Observable<List<Article>> streamFetchTopStories(String key){
        MyNewsService myNewsService = MyNewsService.retrofit.create(MyNewsService.class);
        return myNewsService.getTopStoriesArticle(key)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .timeout(10, TimeUnit.SECONDS);
    }
}
