package com.cricket.cricketchallenge.widget.intro;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.cricket.cricketchallenge.custom.TfTextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.cricket.cricketchallenge.R;
import com.cricket.cricketchallenge.commonmodel.TutorialItem;
import com.cricket.cricketchallenge.helper.Functions;


/**
 * @author rebeccafranks
 * @since 2015/10/15.
 */
public class MaterialTutorialFragment extends Fragment {
    private static final String ARG_TUTORIAL_ITEM = "arg_tut_item";
    private static final String TAG = "MaterialTutFragment";
    private static final String ARG_PAGE = "arg_page";

    public static MaterialTutorialFragment newInstance(TutorialItem tutorialItem, int page) {
        MaterialTutorialFragment helpTutorialImageFragment = new MaterialTutorialFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_TUTORIAL_ITEM, tutorialItem);
        args.putInt(ARG_PAGE, page);
        helpTutorialImageFragment.setArguments(args);
        return helpTutorialImageFragment;
    }

    private TutorialItem tutorialItem;
    int page;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle b = getArguments();
        tutorialItem = b.getParcelable(ARG_TUTORIAL_ITEM);
        page = b.getInt(ARG_PAGE);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_help_tutorial_image, container, false);
        v.setTag(page);

        ImageView imageViewFront = (ImageView) v.findViewById(R.id.fragment_help_tutorial_imageview);
        ImageView imageViewBack = (ImageView) v.findViewById(R.id.fragment_help_tutorial_imageview_background);
        TfTextView textViewSubTitle = (TfTextView) v.findViewById(R.id.fragment_help_tutorial_subtitle_text);

        TfTextView textView = (TfTextView) v.findViewById(R.id.fragment_help_tutorial_text);
        textView.setTypeface(Functions.getRegularFont(getActivity()));
        if (!TextUtils.isEmpty(tutorialItem.getTitleText())) {
            textView.setText(tutorialItem.getTitleText());
        } else if (tutorialItem.getTitleTextRes() != -1) {
            textView.setText(tutorialItem.getTitleTextRes());
        }
        if (!TextUtils.isEmpty(tutorialItem.getSubTitleText())) {
            textViewSubTitle.setText(tutorialItem.getSubTitleText());
        } else if (tutorialItem.getSubTitleTextRes() != -1) {
            textViewSubTitle.setText(tutorialItem.getSubTitleTextRes());
        }
        if (tutorialItem.getBackgroundImageRes() != -1) {
            Glide.with(this).load(tutorialItem.getBackgroundImageRes()).into(imageViewBack);
        }
        if (tutorialItem.getForegroundImageRes() != -1 && !tutorialItem.isGif()) {
            Glide.with(this).load(tutorialItem.getForegroundImageRes()).into(imageViewFront);
        }
        if (tutorialItem.getForegroundImageRes() != -1 && tutorialItem.isGif()){
            Glide.with(this).load(tutorialItem.getForegroundImageRes()).asGif().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(imageViewFront);
        }
        return v;
    }
}

