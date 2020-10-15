package com.github.bartrina.bootcamp;

import android.content.Context;
import android.content.Intent;

import androidx.test.core.app.ActivityScenario;
import androidx.test.core.app.ApplicationProvider;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.assertion.ViewAssertions;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class GreetingActivityTest {

    @Test
    public void checkPassedNameIsDisplayed() {
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = new Intent(context, GreetingActivity.class);
        intent.putExtra(GreetingActivity.EXTRA_NAME, "TestName");

        try (ActivityScenario<GreetingActivity> scenario = ActivityScenario.launch(intent)) {
            ViewInteraction textView = Espresso.onView(ViewMatchers.withId(R.id.greeting_textv_message));
            textView.check(ViewAssertions.matches(ViewMatchers.withText("Welcome TestName!")));
        }
    }

    @Test
    public void checkPassedNameIsDisplayedButEmpty() {
        Context context = ApplicationProvider.getApplicationContext();
        Intent intent = new Intent(context, GreetingActivity.class);
        intent.putExtra(GreetingActivity.EXTRA_NAME, "");

        try (ActivityScenario<GreetingActivity> scenario = ActivityScenario.launch(intent)) {
            ViewInteraction textView = Espresso.onView(ViewMatchers.withId(R.id.greeting_textv_message));
            textView.check(ViewAssertions.matches(ViewMatchers.withText("Welcome !")));
        }
    }
}
