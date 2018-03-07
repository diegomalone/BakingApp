package com.diegomalone.bakingapp;

import android.support.test.espresso.Espresso;
import android.support.test.espresso.IdlingResource;
import android.support.test.espresso.contrib.RecyclerViewActions;
import android.support.test.espresso.intent.rule.IntentsTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.diegomalone.bakingapp.ui.activity.RecipeListActivity;
import com.diegomalone.bakingapp.ui.activity.RecipeStepListActivity;
import com.diegomalone.bakingapp.ui.fragment.RecipeListFragment;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.intent.Intents.intended;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent;
import static android.support.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static com.diegomalone.bakingapp.utils.FlowController.RECIPE_EXTRA;

/**
 * Created by malone on 05/03/18.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeListActivityTest {

    @Rule
    public IntentsTestRule<RecipeListActivity> activityTestRule = new IntentsTestRule<>(RecipeListActivity.class);

    private IdlingResource idlingResource;

    @Before
    public void setup() {
        idlingResource = activityTestRule.getActivity().getIdlingResource();
        Espresso.registerIdlingResources(idlingResource);
    }

    @Test
    public void test_clickRecipe() {
        RecipeListFragment fragment = new RecipeListFragment();
        activityTestRule.getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, fragment).commit();

        onView(withId(R.id.recipeListRecyclerView))
                .perform(RecyclerViewActions.actionOnItemAtPosition(1, click()));

        intended(hasExtraWithKey(RECIPE_EXTRA));
        intended(hasComponent(RecipeStepListActivity.class.getName()));
    }

    @After
    public void tearDown() {
        if (idlingResource != null) {
            Espresso.unregisterIdlingResources(idlingResource);
        }
    }
}
