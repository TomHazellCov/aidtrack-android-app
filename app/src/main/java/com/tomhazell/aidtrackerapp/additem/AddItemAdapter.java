package com.tomhazell.aidtrackerapp.additem;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tomhazell.aidtrackerapp.additem.fragments.ValidatedFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tom Hazell on 16/02/2017.
 */

public class AddItemAdapter extends FragmentPagerAdapter {

    private List<ValidatedFragment> fragments;

    public AddItemAdapter(FragmentManager fm, List<ValidatedFragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }


    @Override
    public Fragment getItem(int pos) {
        return fragments.get(pos).getFragment();
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    /**
     * Validates that all the fields on the house page are valid
     *
     * @return null if filds are invalid or hte model if they are valid
     */
    public NewItem createModel() {
        NewItem item = new NewItem();
        for (ValidatedFragment fragment : fragments) {
            item = fragment.addDataToModel(item);
        }
        return item;
    }

    public boolean validatePage(int pos){
        ValidatedFragment validatedFragment = fragments.get(pos);
        return validatedFragment.validateDetails();
    }
}
