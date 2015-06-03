package com.sequoiabridge.captain.toeflavatar;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.SimpleCursorAdapter;

import com.sequoiabridge.captain.toeflavatar.data.DataContract;
import com.sequoiabridge.captain.toeflavatar.data.RecordingDbHelper;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.util.Date;


/**
 * An activity representing a single Question detail screen. This
 * activity is only used on handset devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link QuestionListActivity}.
 * <p/>
 * This activity is mostly just a 'shell' activity containing nothing
 * more than a {@link QuestionDetailFragment}.
 */
public class QuestionDetailActivity extends AppCompatActivity
            implements View.OnClickListener {

    private MediaRecorder mRecorder = null;
    private MediaPlayer mPlayer = null;
    private static String mFileName = null;
    private RecordingDbHelper mDBHelper;
    private static boolean mInitOnce = true;
    boolean mStartRecording = true;
    private SimpleCursorAdapter mRecordCursorAdapter;
    private QuestionDetailFragment mDetailFragment = null;
    private static final String LOG_TAG = "QuestionDetailActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question_detail);

        if (mInitOnce) {
            mFileName = Environment.getExternalStorageDirectory().getAbsolutePath();
            mFileName += "/avatar";
            File file = new File(mFileName);
            if(! file.exists()) {
                if(! file.mkdir())
                    Log.e(LOG_TAG, "onCreate mkdir failed with the following path" + mFileName);
            }
            mFileName += "/audiorecordtest.3gp";
            Log.d(LOG_TAG, "onCreate mFileName = " + mFileName);

            mDBHelper = new RecordingDbHelper(this);

            if (mPlayer != null) {
                mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if (mPlayer != null && mPlayer.isPlaying()) {
                            stopPlaying();
                        }
                    }
                });
            }
            mInitOnce = true;
        }

        // Show the Up button in the action bar.
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(QuestionDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(QuestionDetailFragment.ARG_ITEM_ID));
            mDetailFragment = new QuestionDetailFragment();
            mDetailFragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.question_detail_container, mDetailFragment)
                    .commit();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. Use NavUtils to allow users
            // to navigate up one level in the application structure. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            NavUtils.navigateUpTo(this, new Intent(this, QuestionListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void startPlaying() {
        if (mPlayer == null) {
            mPlayer = new MediaPlayer();
        } else {
            mPlayer.reset();
        }
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "startPlaying() prepare() failed");
        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;

        mDBHelper.insert(mFileName, DateFormat.getDateTimeInstance().format(new Date()));

        if (mDetailFragment == null)
            Log.e(LOG_TAG, "stopRecording detailFragment is null, fragment manager cannot find question_detail_fragment");
        else
            mDetailFragment.reload();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnStartRecording:
                onRecord(mStartRecording);
                if (mStartRecording) {
                    ((Button) v).setText("Stop recording");
                } else {
                    ((Button) v).setText("Start recording");
                }
                mStartRecording = !mStartRecording;
                break;
            case R.id.btnStartPlaying:
                startPlaying();
                break;
        }
    }

}
