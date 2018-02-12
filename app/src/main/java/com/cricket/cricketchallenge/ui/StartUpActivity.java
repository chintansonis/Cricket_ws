package com.cricket.cricketchallenge.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.cricket.cricketchallenge.R;
import com.cricket.cricketchallenge.commonmodel.TutorialItem;
import com.cricket.cricketchallenge.helper.AppConstants;
import com.cricket.cricketchallenge.helper.Functions;

import java.util.ArrayList;


/**
 * Created by vaibhavirana on 01-06-2016.
 */
public class StartUpActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 1234;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);
        loadTutorial();

    }

    public void loadTutorial() {
        Intent mainAct = new Intent(this, MaterialTutorialActivity.class);
        mainAct.putParcelableArrayListExtra(MaterialTutorialActivity.MATERIAL_TUTORIAL_ARG_TUTORIAL_ITEMS, getTutorialItems(this));
        startActivityForResult(mainAct, REQUEST_CODE);

    }

    private ArrayList<TutorialItem> getTutorialItems(Context context) {
        TutorialItem tutorialItem1 = new TutorialItem(getString(R.string.take_your_medicine_and_be_healthy), null, R.color.light_green, R.drawable.download);
        TutorialItem tutorialItem2 = new TutorialItem(getString(R.string.take_your_medicine_and_be_healthy), null, R.color.slide_4, R.drawable.download_2);
        TutorialItem tutorialItem3 = new TutorialItem(getString(R.string.take_your_medicine_and_be_healthy), null, R.color.colorPrimaryDark, R.drawable.download3);
        TutorialItem tutorialItem4 = new TutorialItem(getString(R.string.take_your_medicine_and_be_healthy), null, R.color.slide_3, R.drawable.download);

        ArrayList<TutorialItem> tutorialItems = new ArrayList<>();
        tutorialItems.add(tutorialItem1);
        tutorialItems.add(tutorialItem2);
        tutorialItems.add(tutorialItem3);
        tutorialItems.add(tutorialItem4);

        return tutorialItems;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //    super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {


            int getButtonClickValue = data.getIntExtra(AppConstants.INTENT_EXTRA_BUTTON_CLICK, AppConstants.getStartedClick);

            if (getButtonClickValue == AppConstants.getStartedClick) {
                //UIUtil.startActivity(this, MainPageActivity.class, true);
                startActivity(new Intent(this, LoginLandingActivity.class));
            } else if (getButtonClickValue == AppConstants.loginClick) {
                //UIUtil.startActivity(this, LoginActivity.class, true);
                startActivity(new Intent(this, LoginLandingActivity.class));
            }
            overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            finish();

        } else {

            //User has pressed back button
            finish();
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        Functions.hideKeyPad(StartUpActivity.this, findViewById(android.R.id.content));
    }
}
