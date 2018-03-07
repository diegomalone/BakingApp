package com.diegomalone.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.support.test.InstrumentationRegistry;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;

import com.diegomalone.bakingapp.model.Recipe;
import com.diegomalone.bakingapp.ui.activity.RecipeStepListActivity;
import com.diegomalone.bakingapp.ui.fragment.RecipeStepListFragment;
import com.google.gson.Gson;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.InstrumentationRegistry.getInstrumentation;
import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static com.diegomalone.bakingapp.utils.FlowController.RECIPE_EXTRA;
import static org.hamcrest.Matchers.allOf;

/**
 * Created by malone on 07/03/18.
 */

@RunWith(AndroidJUnit4.class)
public class RecipeStepListActivityTest {

    @Rule
    public ActivityTestRule<RecipeStepListActivity> activityTestRule =
            new ActivityTestRule<>(RecipeStepListActivity.class, true, false);

    private static Intent activityIntent = new Intent(InstrumentationRegistry.getTargetContext(), RecipeStepListActivity.class);

    private Recipe recipe;

    @Before
    public void setup() {
        Context context = getInstrumentation().getTargetContext();
        recipe = new Gson().fromJson(Utils.readFileFromAssets(context, "recipe.json"), Recipe.class);

        activityIntent.putExtra(RECIPE_EXTRA, recipe);

        activityTestRule.launchActivity(activityIntent);
    }

    @Test
    public void test_ingredientsShown() {
        RecipeStepListFragment fragment = RecipeStepListFragment.newInstance(recipe);
        activityTestRule.getActivity().getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragment, fragment).commit();

        onView(allOf(withId(R.id.ingredientTextView), withText("granulated sugar")))
                .check(matches(isDisplayed()));
    }
}
