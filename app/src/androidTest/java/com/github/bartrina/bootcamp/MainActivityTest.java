package com.github.bartrina.bootcamp;

import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.action.ViewActions;
import androidx.test.espresso.intent.Intents;
import androidx.test.espresso.intent.matcher.IntentMatchers;
import androidx.test.espresso.matcher.ViewMatchers;
import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import org.hamcrest.Matchers;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityTest {

    @Rule
    public ActivityScenarioRule<MainActivity> testRule = new ActivityScenarioRule<>(MainActivity.class);

    @Test
    public void sameNameAsInTheTextFieldIsPassed() {
        Intents.init();
        ViewInteraction textField = Espresso.onView(ViewMatchers.withId(R.id.main_ptext_name));
        textField.perform(ViewActions.click());
        textField.perform(ViewActions.typeText("TestName"));

        ViewInteraction button = Espresso.onView(ViewMatchers.withId(R.id.main_butt_go));
        button.perform(ViewActions.click());

        Intents.intended(IntentMatchers.hasExtra(GreetingActivity.EXTRA_NAME, "TestName"));

        Intents.release();
    }

    @Test
    public void sameNameAsInTheTextFieldIsPassedButIsEmpty() {
        Intents.init();
        ViewInteraction textField = Espresso.onView(ViewMatchers.withId(R.id.main_ptext_name));
        textField.perform(ViewActions.click());

        ViewInteraction button = Espresso.onView(ViewMatchers.withId(R.id.main_butt_go));
        button.perform(ViewActions.click());

        Intents.intended(IntentMatchers.hasExtra(GreetingActivity.EXTRA_NAME, ""));
        Intents.release();
    }

}
