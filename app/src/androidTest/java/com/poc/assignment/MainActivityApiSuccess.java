package com.poc.assignment;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.InstrumentationTestCase;
import android.test.RenamingDelegatingContext;

import com.poc.assignment.activity.MainActivity;
import com.poc.assignment.database.DBManager;
import com.poc.assignment.database.DatabaseHelper;
import com.poc.assignment.model.AlbumDataModel;
import com.poc.assignment.model.AppConstants;
import com.squareup.okhttp.mockwebserver.MockResponse;
import com.squareup.okhttp.mockwebserver.MockWebServer;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.doesNotExist;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.RootMatchers.withDecorView;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withEffectiveVisibility;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;

@RunWith(AndroidJUnit4.class)
public class MainActivityApiSuccess extends InstrumentationTestCase {
    private Context mContext;
    private DBManager database;

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

        mContext = new RenamingDelegatingContext(InstrumentationRegistry.getInstrumentation().getTargetContext(), DatabaseHelper.DATABASE_NAME);
    }


    @Test
    public void testUIOnAPISuccess() throws Exception {
        String fileName = "api_success_response.json";
        server.enqueue(new MockResponse()
                .setResponseCode(200)
                .setBody(RestServiceTestHelper.getStringFromFile(getInstrumentation().getContext(), fileName)));

        Intent intent = new Intent();
        mActivityRule.launchActivity(intent);

        //***********Testing DB***************

        ArrayList<AlbumDataModel> albumList = DBManager.getInstance(mContext).open().getAlbumList();
        ViewMatchers.assertThat(albumList.size(), is(3));
        assertTrue(albumList.get(0).getTitle().equals("quidem molestiae enim"));
        //****************
        onView(ViewMatchers.withId(R.id.tvErrorMsg)).check(matches(withEffectiveVisibility(ViewMatchers.Visibility.GONE)));
        onView(ViewMatchers.withId(R.id.rvAlbums))
                .check(matches(ViewMatchers.hasDescendant(withText("quidem molestiae enim"))));
    }

    @After
    public void tearDown() throws Exception {
        server.shutdown();
        DBManager.getInstance(mActivityRule.getActivity()).close();
    }

}
