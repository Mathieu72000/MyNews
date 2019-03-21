package com.corroy.mathieu.mynews;

import com.corroy.mathieu.mynews.Controllers.Activities.MainActivity;
import com.corroy.mathieu.mynews.Controllers.Activities.NotificationActivity;
import com.corroy.mathieu.mynews.Controllers.Activities.SearchActivity;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import androidx.test.espresso.intent.rule.IntentsTestRule;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.swipeLeft;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.intent.Intents.intended;
import static androidx.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static androidx.test.espresso.matcher.ViewMatchers.isCompletelyDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.hamcrest.core.AllOf.allOf;


@RunWith(AndroidJUnit4.class)
public class ExempleInstrumentedTest {

    @Rule
    public IntentsTestRule<MainActivity> mActivityRule = new IntentsTestRule<>(
            MainActivity.class);

    @Test
    public void checkIfEachFragmentOfViewPagerIsVisible(){
        onView(allOf(withId(R.id.activityViewPager), isCompletelyDisplayed()))
                .check(matches(withEffectiveVisibility(ViewMatchers.Visibility.VISIBLE)));
    }

    @Test
    public void checkNavigationSearchArticle(){
        onView(withId(R.id.search)).perform(click());
        intended(hasComponent(SearchActivity.class.getName()));
    }

    @Test
    public void checkNavigationNotification(){
        openActionBarOverflowOrOptionsMenu(getInstrumentation().getTargetContext());
        onView(withText("Notifications")).perform(click());
        intended(hasComponent(NotificationActivity.class.getName()));
    }

    @Test
    public void checkViewPagerSwipe(){
        onView(withId(R.id.activityViewPager)).perform(swipeLeft());
        onView(withId(R.id.activityViewPager)).perform(swipeLeft());
    }
}