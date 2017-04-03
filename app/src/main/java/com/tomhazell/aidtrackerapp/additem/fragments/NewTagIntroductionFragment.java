package com.tomhazell.aidtrackerapp.additem.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tomhazell.aidtrackerapp.R;
import com.tomhazell.aidtrackerapp.additem.NewItem;
import com.tomhazell.aidtrackerapp.summary.SummaryActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by Tom Hazell on 27/02/2017.
 */

public class NewTagIntroductionFragment extends Fragment implements ValidatedFragment {

    @BindView(R.id.additem_intro_title)
    TextView title;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_additem_intro, container, false);
        ButterKnife.bind(this, v);
        Bundle arguments = getArguments();
        if (arguments != null) {
            if (arguments.getBoolean(SummaryActivity.EXTRA_NEW_TAG)) {
                title.setText(R.string.additem_intro_text_newTag);
            } else if (arguments.getBoolean(SummaryActivity.EXTRA_TAG_BADID)) {
                title.setText(R.string.additem_intro_text_badid);
            }
        }
        return v;
    }

    @Override
    public boolean validateDetails() {
        return true;
    }

    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public NewItem addDataToModel(NewItem newItem) {
        return newItem;
    }

    @Override
    public String getTitle() {
        return "Adding a new product";
    }
}
