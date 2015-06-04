package com.sequoiabridge.captain.toeflavatar;

/**
 * Media control interface
 * Any activity that used media or camera resource shouldi implement this class
 */
public interface IMediaController {
    void startRecording(String outputFilename);

    void stopRecording();

    void startPlaying(String filename);

    void stopPlaying();
}
