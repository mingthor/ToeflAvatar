package com.sequoiabridge.captain.toeflavatar;

import android.app.Activity;
import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class RecordingDialogFragment extends DialogFragment {

    private static final String LOG_TAG = "RecordingDialogFragment";
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private View.OnClickListener mClickCallback = null;
    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public RecordingDialogFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment RecordingDialogFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static RecordingDialogFragment newInstance(String param1, String param2) {
        RecordingDialogFragment fragment = new RecordingDialogFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recording_dialog, container);
        getDialog().setTitle("Listening ...");
        getDialog().setCanceledOnTouchOutside(false);

        (view.findViewById(R.id.btnStopRecording)).setOnClickListener(mClickCallback);
        return view;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mClickCallback = (View.OnClickListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString() + " must implement View.OnClickListener");
        }

        Log.d(LOG_TAG, "onAttach");
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mClickCallback = null;
        Log.d(LOG_TAG, "onDetach");
    }


}
