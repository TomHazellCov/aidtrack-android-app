package com.tomhazell.aidtrackerapp;


import android.support.test.espresso.ViewInteraction;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import static android.support.test.espresso.Espresso.onView;
import static android.support.test.espresso.Espresso.pressBack;
import static android.support.test.espresso.action.ViewActions.click;
import static android.support.test.espresso.action.ViewActions.closeSoftKeyboard;
import static android.support.test.espresso.action.ViewActions.replaceText;
import static android.support.test.espresso.assertion.ViewAssertions.matches;
import static android.support.test.espresso.matcher.ViewMatchers.isDisplayed;
import static android.support.test.espresso.matcher.ViewMatchers.withClassName;
import static android.support.test.espresso.matcher.ViewMatchers.withId;
import static android.support.test.espresso.matcher.ViewMatchers.withParent;
import static android.support.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.is;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class AddItemTest {

    @Rule
    public ActivityTestRule<MainActivity> mActivityTestRule = new ActivityTestRule<>(MainActivity.class);

    @Test
    public void addItemTest() {
        ViewInteraction appCompatButton = onView(
                allOf(withId(R.id.startupNext), withText("Next"),
                        withParent(allOf(withId(R.id.startupControls),
                                withParent(withId(R.id.startupCoordinator)))),
                        isDisplayed()));
        appCompatButton.perform(click());

        ViewInteraction unscrollableViewPager = onView(
                allOf(withId(R.id.startupViewPager), isDisplayed()));

        ViewInteraction appCompatSpinner = onView(
                allOf(withId(R.id.select_product_products), isDisplayed()));
        appCompatSpinner.perform(click());

        ViewInteraction appCompatCheckedTextView = onView(
                allOf(withId(android.R.id.text1), withText("Add a new product"), isDisplayed()));
        appCompatCheckedTextView.perform(click());

        ViewInteraction editText = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.select_product_name),
                                0),
                        0),
                        isDisplayed()));
        editText.check(matches(isDisplayed()));

        ViewInteraction editText2 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.select_product_discription),
                                0),
                        0),
                        isDisplayed()));
        editText2.check(matches(isDisplayed()));

        ViewInteraction appCompatSpinner2 = onView(
                allOf(withId(R.id.select_product_manufacture), isDisplayed()));
        appCompatSpinner2.perform(click());

        ViewInteraction appCompatCheckedTextView2 = onView(
                allOf(withId(android.R.id.text1), withText("Add a new manufacturer"), isDisplayed()));
        appCompatCheckedTextView2.perform(click());

        ViewInteraction editText3 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.select_product_manufacture_name),
                                0),
                        0),
                        isDisplayed()));
        editText3.check(matches(isDisplayed()));

        ViewInteraction textInputEditText = onView(
                allOf(withClassName(is("android.support.design.widget.TextInputEditText")), isDisplayed()));
        textInputEditText.perform(replaceText("Na"), closeSoftKeyboard());

        ViewInteraction textInputEditText2 = onView(
                allOf(withClassName(is("android.support.design.widget.TextInputEditText")), isDisplayed()));
        textInputEditText2.perform(replaceText("Test Description"), closeSoftKeyboard());

        ViewInteraction textInputEditText3 = onView(
                allOf(withClassName(is("android.support.design.widget.TextInputEditText")), isDisplayed()));
        textInputEditText3.perform(replaceText("Man"), closeSoftKeyboard());

        ViewInteraction textInputEditText4 = onView(
                allOf(withText("Man"), isDisplayed()));
        textInputEditText4.perform(replaceText("Manafa"), closeSoftKeyboard());

        ViewInteraction appCompatButton2 = onView(
                allOf(withId(R.id.startupNext), withText("Next"),
                        withParent(allOf(withId(R.id.startupControls),
                                withParent(withId(R.id.startupCoordinator)))),
                        isDisplayed()));
        appCompatButton2.perform(click());

        ViewInteraction appCompatButton3 = onView(
                allOf(withId(R.id.startupNext), withText("Next"),
                        withParent(allOf(withId(R.id.startupControls),
                                withParent(withId(R.id.startupCoordinator)))),
                        isDisplayed()));
        appCompatButton3.perform(click());

        ViewInteraction unscrollableViewPager2 = onView(
                allOf(withId(R.id.startupViewPager), isDisplayed()));

        ViewInteraction appCompatSpinner3 = onView(
                allOf(withId(R.id.select_campaign_campaigns), isDisplayed()));
        appCompatSpinner3.perform(click());

        ViewInteraction appCompatCheckedTextView3 = onView(
                allOf(withId(android.R.id.text1), withText("Add a new campaign"), isDisplayed()));
        appCompatCheckedTextView3.perform(click());

        ViewInteraction editText4 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.select_campaign_name),
                                0),
                        0),
                        isDisplayed()));
        editText4.check(matches(isDisplayed()));

        ViewInteraction textInputEditText5 = onView(
                allOf(withClassName(is("android.support.design.widget.TextInputEditText")), isDisplayed()));
        textInputEditText5.perform(click());

        ViewInteraction textInputEditText6 = onView(
                allOf(withClassName(is("android.support.design.widget.TextInputEditText")), isDisplayed()));
        textInputEditText6.perform(click());

        ViewInteraction textInputEditText7 = onView(
                allOf(withClassName(is("android.support.design.widget.TextInputEditText")), isDisplayed()));
        textInputEditText7.perform(replaceText("Test Campaign"), closeSoftKeyboard());

        ViewInteraction appCompatButton4 = onView(
                allOf(withId(R.id.startupNext), withText("Next"),
                        withParent(allOf(withId(R.id.startupControls),
                                withParent(withId(R.id.startupCoordinator)))),
                        isDisplayed()));
        appCompatButton4.perform(click());

        ViewInteraction unscrollableViewPager3 = onView(
                allOf(withId(R.id.startupViewPager), isDisplayed()));

        ViewInteraction appCompatSpinner4 = onView(
                allOf(withId(R.id.select_shipment_shipments), isDisplayed()));
        appCompatSpinner4.perform(click());

        ViewInteraction appCompatCheckedTextView4 = onView(
                allOf(withId(android.R.id.text1), withText("Add a new shipment"), isDisplayed()));
        appCompatCheckedTextView4.perform(click());

        ViewInteraction editText5 = onView(
                allOf(childAtPosition(
                        childAtPosition(
                                withId(R.id.select_shipment_name),
                                0),
                        0),
                        isDisplayed()));
        editText5.check(matches(isDisplayed()));

        ViewInteraction textInputEditText8 = onView(
                allOf(withClassName(is("android.support.design.widget.TextInputEditText")), isDisplayed()));
        textInputEditText8.perform(click());

        ViewInteraction textInputEditText9 = onView(
                allOf(withClassName(is("android.support.design.widget.TextInputEditText")), isDisplayed()));
        textInputEditText9.perform(replaceText("Shipme"), closeSoftKeyboard());

        ViewInteraction textInputEditText10 = onView(
                allOf(withText("Shipme"), isDisplayed()));
        textInputEditText10.perform(replaceText("Shipment"), closeSoftKeyboard());

        ViewInteraction appCompatButton5 = onView(
                allOf(withId(R.id.startupNext), withText("Next"),
                        withParent(allOf(withId(R.id.startupControls),
                                withParent(withId(R.id.startupCoordinator)))),
                        isDisplayed()));
        appCompatButton5.perform(click());

        ViewInteraction unscrollableViewPager4 = onView(
                allOf(withId(R.id.startupViewPager), isDisplayed()));

        ViewInteraction textView = onView(
                allOf(withText("Scan an NFC tag to finish!"),
                        childAtPosition(
                                childAtPosition(
                                        withId(R.id.create_product_flipper),
                                        0),
                                0),
                        isDisplayed()));
        textView.check(matches(withText("Scan an NFC tag to finish!")));

    }

    private static Matcher<View> childAtPosition(
            final Matcher<View> parentMatcher, final int position) {

        return new TypeSafeMatcher<View>() {
            @Override
            public void describeTo(Description description) {
                description.appendText("Child at position " + position + " in parent ");
                parentMatcher.describeTo(description);
            }

            @Override
            public boolean matchesSafely(View view) {
                ViewParent parent = view.getParent();
                return parent instanceof ViewGroup && parentMatcher.matches(parent)
                        && view.equals(((ViewGroup) parent).getChildAt(position));
            }
        };
    }
}
