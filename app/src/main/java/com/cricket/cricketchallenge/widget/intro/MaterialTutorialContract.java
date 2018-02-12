package com.cricket.cricketchallenge.widget.intro;


import com.cricket.cricketchallenge.commonmodel.TutorialItem;

import java.util.List;

/**
 * @author rebeccafranks
 * @since 15/11/09.
 */
public interface MaterialTutorialContract {

    interface View {
        void showNextTutorial();
        void showEndTutorial();
        void setBackgroundColor(int color);
        void showDoneButton();
        void showSkipButton();
        void setViewPagerFragments(List<MaterialTutorialFragment> materialTutorialFragments);
        void showLoginView();
    }

    interface UserActionsListener {
        void loadViewPagerFragments(List<TutorialItem> tutorialItems);
        void doneOrSkipClick();
        void nextClick();
        void onPageSelected(int pageNo);
        void transformPage(android.view.View page, float position);

        int getNumberOfTutorials();
    }

}
