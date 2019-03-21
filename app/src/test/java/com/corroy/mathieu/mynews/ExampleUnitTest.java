package com.corroy.mathieu.mynews;

import com.corroy.mathieu.mynews.Controllers.Utils.MyNewsStreams;
import com.corroy.mathieu.mynews.Models.Article;
import com.corroy.mathieu.mynews.Models.Search;
import io.reactivex.Observable;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.observers.TestObserver;
import io.reactivex.schedulers.Schedulers;
import org.junit.BeforeClass;
import org.junit.Test;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import static org.junit.Assert.*;

public class ExampleUnitTest {

    @BeforeClass
    public static void setupClass(){
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(__ -> Schedulers.trampoline());
    }

    @Test
    public void checkHttpTopStoriesRequest() {
        Observable<Article> observableArticle = MyNewsStreams.streamFetchTopStories("home", "pX69N3N5cVmjfynWXnSvWQ92GaxGuIAh");
        TestObserver<Article> testObserver = new TestObserver<>();
        observableArticle.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        Article article = testObserver.values().get(0);
        assertTrue(article.getResults().size() > 0);
    }

    @Test
    public void checkHttpMostPopularRequest() {
        Observable<Article> observableArticle = MyNewsStreams.streamFetchMostPopular(7, "pX69N3N5cVmjfynWXnSvWQ92GaxGuIAh");
        TestObserver<Article> testObserver = new TestObserver<>();
        observableArticle.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        Article article = testObserver.values().get(0);
        assertTrue(article.getResults().size() > 0);
    }
    @Test
    public void checkHttpSearch(){
        Observable<Search> observableSearch = MyNewsStreams.streamFetchSearch("world", "20180101", "20180303", "politics");
        TestObserver<Search> testObserver = new TestObserver<>();
        observableSearch.subscribeWith(testObserver)
                .assertNoErrors()
                .assertNoTimeout()
                .awaitTerminalEvent();

        Search search = testObserver.values().get(0);
        assertTrue(search.getResponse().getDocs().size() > 0);
    }

    @Test
    public void testDateConverter(){

        String date1 = ("01/02/2018");
        String date2 = ("20180201");

        String convertedDate1 = "";

        SimpleDateFormat inputFormat = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat outputFormat = new SimpleDateFormat("yyyyMMdd");

        try{
            convertedDate1 = outputFormat.format(inputFormat.parse(date1));
        } catch (ParseException e){
            e.printStackTrace();
        }

        assertEquals(convertedDate1, date2);
    }
}