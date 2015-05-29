package com.sequoiabridge.captain.toeflavatar;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import com.sequoiabridge.captain.toeflavatar.dummy.DummyContent;

/**
 * A fragment representing a single Question detail screen.
 * This fragment is either contained in a {@link QuestionListActivity}
 * in two-pane mode (on tablets) or a {@link QuestionDetailActivity}
 * on handsets.
 */
public class QuestionDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";

    /**
     * The dummy content this fragment is presenting.
     */
    private DummyContent.QuestionItem mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public QuestionDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // TODO:
        // to be able to recover the fragment content when press back button
        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = DummyContent.ITEM_MAP.get(getArguments().getString(ARG_ITEM_ID));
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_question_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.question_subject_english)).setText(mItem.contentEnglish);
            ((TextView) rootView.findViewById(R.id.question_subject_chinese)).setText(mItem.contentChinese);
        }

        return rootView;
    }
}
