package com.seguridadinteligente.domotica;

import android.app.ProgressDialog;
import android.graphics.PixelFormat;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.VideoView;

import java.text.DateFormat;
import java.util.Date;

public class VideoActivity extends AppCompatActivity {

    VideoView videoView;

    String currentDateTimeString = DateFormat.getDateTimeInstance().format(new Date());

    private static ProgressDialog progressDialog;

    private TextView txtv4;

    public MediaPlayer.OnCompletionListener completionListener,completionListener2;

    public Uri uri , uri2;

    public Button btnvma;

    public VideoView videoView2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_video);

        final MediaPlayer mediaPlayer2 = MediaPlayer.create(this, R.raw.cellvid);

        btnvma = (Button) findViewById(R.id.videomasaudio);
        btnvma.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view)  {
                PlayVideo();
            }
        });

        txtv4 = (TextView) findViewById(R.id.textView4);

        videoView = (VideoView) findViewById(R.id.VideoView);

        videoView2 = (VideoView) findViewById(R.id.videoView2);

        final MediaController mediaController2 = new MediaController(this);

        mediaController2.setAnchorView(videoView2);

        final MediaController mediaController = new MediaController(this);

        mediaController.setAnchorView(videoView);

        txtv4.setText(currentDateTimeString);

        uri2 = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.cellvid);

        uri = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.ultimaversion);


        videoView.setMediaController(mediaController);

        videoView.setEnabled(true);

        videoView.setVideoURI(uri);

        videoView2.setMediaController(mediaController2);

        videoView2.setEnabled(true);

        videoView2.setVideoURI(uri2);

        //el on completion puede que haya que sacarlo//

        completionListener = (new MediaPlayer.OnCompletionListener() {

            boolean mVideoCompleted = false;

            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                mVideoCompleted = true;
            }
        });

    }
    private void PlayVideo() {

        progressDialog = ProgressDialog.show(VideoActivity.this, "", "Cargando Video...", true);

        progressDialog.setCancelable(true);

        videoView2.setOnCompletionListener(completionListener);
        videoView.setOnCompletionListener(completionListener);

        try {

           // getWindow().setFormat(PixelFormat.TRANSLUCENT);

            MediaController mediaController = new MediaController(VideoActivity.this);
            mediaController.setAnchorView(videoView);

            MediaController mediaController1= new MediaController(VideoActivity.this);
            mediaController1.setAnchorView(videoView2);

            videoView2.setMediaController(mediaController1);
            videoView2.setVideoURI(uri2);

            videoView2.setOnPreparedListener( new MediaPlayer.OnPreparedListener() {

                public void onPrepared(MediaPlayer mediaPlayer){
                    progressDialog.dismiss();
                    videoView2.start();
                }
            });
            videoView.setMediaController(mediaController);
            videoView.setVideoURI(uri);

            videoView.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {

                public void onPrepared(MediaPlayer mp) {

                    progressDialog.dismiss();
                    videoView.start();
                }
            });



        } catch (Exception e) {

            progressDialog.dismiss();

            System.out.println("Video Play Error :" + e.toString());
            finish();

        }

    }

}




