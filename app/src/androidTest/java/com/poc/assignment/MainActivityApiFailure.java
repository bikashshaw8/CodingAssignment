package com.poc.assignment;

import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;

import com.poc.assignment.activity.MainActivity;
import com.poc.assignment.model.AppConstants;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;

@RunWith(AndroidJUnit4.class)
public class MainActivityApiFailure extends InstrumentationTestCase {


    @Rule
    public ActivityTestRule<MainActivity> mActivityRule =
            new ActivityTestRule<>(MainActivity.class, true, false);
    private MockWebServer server;

    @Before
    public void setUp() throws Exception {
        super.setUp();
        server = new MockWebServer();
        server.start();
        injectInstrumentation(InstrumentationRegistry.getInstrumentation());
        AppConstants.baseURL = server.url("/").toString();
    }

    @Test
    public void testUIOnApiError() throws Exception {
        String fileName = "api_failure_response.json";

        server.enqueue(new MockResponse()
                .setResponseCode(404)
                .setBody(RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), fileName)));

        Intent intent = new Intent();
        mActivityRule.launchActivity(intent);

        onView(ViewMatchers.withId(R.id.tvErrorMsg)).check(matches(isDisplayed()));
        onView(withText(startsWith("Something"))).
                inRoot(withDecorView(
                        not(is(mActivityRule.getActivity().
                                getWindow().getDecorView())))).
                check(matches(isDisplayed()));
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
    }

}
