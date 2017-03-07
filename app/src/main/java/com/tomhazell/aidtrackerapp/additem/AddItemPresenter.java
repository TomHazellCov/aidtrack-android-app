package com.tomhazell.aidtrackerapp.additem;

/**
 * Created by Tom Hazell on 16/02/2017.
 */

public class AddItemPresenter {
    private AddItemActivity activity;
    private int numItems;

    private NewItem model;

    public AddItemPresenter(AddItemActivity activity, int numItems) {
        this.activity = activity;
        this.numItems = numItems;
    }

    public void onPageSelected(int pos) {
        activity.showBackButton(pos != 0);
        activity.showFinishButton(pos == numItems - 1);
    }

    public void onNextClick() {
        int currentPage = activity.getCurrentPage();
        if (activity.validatePage(currentPage)) {
            if (currentPage != numItems - 1) {
                activity.setPage(currentPage + 1);
            } else {
                activity.hideNavigation(true);
                activity.setPage(currentPage + 1);
//                doUpload();TODO
            }
        }
    }

    public void onBackClick() {
        //TODO cancel network request if it is ongoing
        activity.setPage(activity.getCurrentPage() - 1);
        activity.hideNavigation(false);
        //cancel network request if any are ongoing TODO
    }

    public void onStop() {
        //cancel network request if any are ongoing TODO
    }
}
