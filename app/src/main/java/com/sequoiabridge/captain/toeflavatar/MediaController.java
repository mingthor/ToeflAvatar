package com.sequoiabridge.captain.toeflavatar;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.util.Log;

import com.sequoiabridge.captain.toeflavatar.data.RecordingDbHelper;

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
    private RecordingDbHelper mDBHelper = null;
    private String mQuestionId;

    private MediaController(Activity activity) {
        mDBHelper = new RecordingDbHelper(activity);
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
            mPlayer.setDataSource(filename);
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
        mRecorder.setOutputFile(mFileName);
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);

        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    public void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;

        mDBHelper.insert(mQuestionId, mFileName, DateFormat.getDateTimeInstance().format(new Date()));
    }

    private String getUniqueFilename(String path) {
        File file = new File(path);
        if (!file.exists()) {
            Log.e(LOG_TAG, "getUniqueFilename failed because the path specified does not exists: " + path);
            return null;
        }
        String format = "yyyy-MM-dd-HH-mm-ss";
        SimpleDateFormat sdf = new SimpleDateFormat(format);
        String filename = "/voice-" + sdf.format(new Date()) + ".3gp";
        return path + filename;
    }
}