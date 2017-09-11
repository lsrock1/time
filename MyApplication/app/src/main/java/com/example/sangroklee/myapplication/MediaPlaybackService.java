package com.example.sangroklee.myapplication;

import android.os.Bundle;
import android.support.v4.media.MediaBrowserServiceCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.PlaybackStateCompat;

/**
 * Created by sangroklee on 2017. 9. 8..
 */

public class MediaPlaybackService extends MediaBrowserServiceCompat {
    private static final String MY_MEDIA_ROOT_ID = "media_root_id";
    private static final String MY_EMPTY_MEDIA_ROOT_ID = "empty_root_id";

    private MediaSessionCompat mMediaSession;
    private PlaybackStateCompat.Builder mStateBuilder;

    @Override
    public void onCreate(){
        super.onCreate();

        // create a mediasession
        mMediaSession = new MediaSessionCompat(this, MediaPlaybackService.class.getSimpleName());

        // set flag
        // handle media button events
        // handles transport control commands through its MediaSessionCompat.Callback
        mMediaSession.setFlags(
                MediaSessionCompat.FLAG_HANDLES_MEDIA_BUTTONS|
                MediaSessionCompat.FLAG_HANDLES_TRANSPORT_CONTROLS);

        // Set an initial PlaybackState with ACTION_PLAY, so media buttons can start the player
        // create a empty builder
        // set the current capabilities available on this session
        // available actions: https://developer.android.com/reference/android/support/v4/media/session/PlaybackStateCompat.Builder.html#setActions(long)
        mStateBuilder = new PlaybackStateCompat.Builder()
                .setActions(
                    PlaybackStateCompat.ACTION_PLAY|
                    PlaybackStateCompat.ACTION_PAUSE
                );

        mMediaSession.setPlaybackState(mStateBuilder.build());

        //mMediaSession.setCallback();

        // Set the session's token so that client activities can communicate with it.
        setSessionToken(mMediaSession.getSessionToken());
    }

    @Override
    public BrowserRoot onGetRoot(String clientPackageName, int clientUid, Bundle rootHints){
        //packageName으로 접근 수준 조절 가능
        if()
    }
}
