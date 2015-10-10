package com.sequoiabridge.toeflavatar;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import com.sequoiabridge.toeflavatar.data.ToeflAvatarDbHelper;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class MediaController {
    private static final String STORAGE_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + "/avatar";
    private static final String LOG_TAG = "MediaManager";
    private static MediaRecorder mRecorder = null;
    private static MediaPlayer mPlayer = null;
    private static MediaController mInstance = null;
    private String mFileName = null;
    private ToeflAvatarDbHelper mDBHelper = null;
    private String mQuestionId;
    private long mStartTime;

    private MediaController(Activity activity) {
        mDBHelper = new ToeflAvatarDbHelper(activity);
    }

    public static MediaController getInstance(Activity activity) {
        if (mInstance == null)
            mInstance = new MediaController(activity);
        return mInstance;
    }

    public void startPlaying(String filename) {
        if (mPlayer == null) {
            mPlayer = new MediaPlayer();
            mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    if (mPlayer != null && mPlayer.isPlaying()) {
                        stopPlaying();
                    }
                }
            });
            mPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer mp) {
                    mPlayer.start();
                }
            });
        } else {
            mPlayer.reset();
        }
        try {
            mPlayer.setDataSource(STORAGE_PATH + "/" + filename);
            mPlayer.prepareAsync();
        } catch (IOException e) {
            Log.e(LOG_TAG, "startPlaying() prepare() failed");
        }
    }

    public void stopPlaying() {
        mPlayer.stop();
        mPlayer.release();
        mPlayer = null;
    }

    public void startRecording(String questionId) {
        mQuestionId = questionId;
        mFileName = getUniqueFilename(STORAGE_PATH);
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(STORAGE_PATH + "/" + mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
        mStartTime = System.currentTimeMillis();
    }

    public void stopRecording() {
        mRecorder.stop();
        long duration = (System.currentTimeMillis() - mStartTime) / 1000;
        mRecorder.release();
        mRecorder = null;

        mDBHelper.insertRecordingItem(mQuestionId, STORAGE_PATH, mFileName,
                DateFormat.getDateTimeInstance().format(new Date()),
                duration);
    }

    private String getUniqueFilename(String path) {
        File file = new File(path);
        if (!file.exists()) {
            Log.e(LOG_TAG, "getUniqueFilename failed because the path specified does not exists: " + path);
            return null;
        }
        String format = "yyyy-MM-dd-HH-mm-ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        return sdf.format(new Date()) + ".3gp";
    }
}
