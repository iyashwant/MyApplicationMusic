package com.example.iyashwant.myapplicationmusic;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.SharedPreferences;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.AppCompatSeekBar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetView;

public class External_Player extends AppCompatActivity {
    boolean isBinded = false;
    MediaPlaybackService mediaPlaybackService;
    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mediaPlaybackService = ((MediaPlaybackService.IDBinder)service).getService();
            isBinded = true;
            initInfos(mediaPlaybackService.getFile());
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            isBinded = false;
        }
    };
    BroadcastReceiver receiverElapsedTime;
    BroadcastReceiver receiverCompleted;

    ViewGroup rootView;
    ImageButton buttonPlayPause;
    ImageButton buttonStop;
    ImageView albumArt;
    TextView titleTextView;
    TextView artistTextView;
    TextView elapsedTimeTextView;
    TextView durationTextView;
    AppCompatSeekBar elapsedTimeSeekBar;
    FloatingActionButton fab;

    int elapsedTime = 0;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_external__player);
        rootView = (ViewGroup) findViewById(android.R.id.content);




        receiverElapsedTime = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                elapsedTime = intent.getIntExtra(MediaPlaybackService.MPS_MESSAGE, 0);
                updateElapsedTime(elapsedTime);
            }
        };

        receiverCompleted = new BroadcastReceiver() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onReceive(Context context, Intent intent) {
                clearInfos();
            }
        };

        buttonPlayPause = (ImageButton) findViewById(R.id.imageButtonPlayPause);
        buttonStop = (ImageButton) findViewById(R.id.imageButtonStop);

        albumArt = (ImageView) findViewById(R.id.albumArt);
        titleTextView = (TextView) findViewById(R.id.textViewTitle);
        artistTextView = (TextView) findViewById(R.id.textViewArtist);
        elapsedTimeTextView = (TextView) findViewById(R.id.textViewElapsedTime);
        durationTextView = (TextView) findViewById(R.id.textViewDuration);

        elapsedTimeSeekBar = (AppCompatSeekBar) findViewById(R.id.seekBar);

        buttonPlayPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int resId;
                if (mediaPlaybackService.isPlaying()) {
                    resId = R.drawable.ic_play_circle_outline_black_48dp;
                    mediaPlaybackService.pause();
                } else {
                    resId = R.drawable.ic_pause_circle_outline_black_48dp;
                    mediaPlaybackService.play();
                }
                buttonPlayPause.setImageResource(resId);
            }
        });
        buttonPlayPause.setEnabled(true);

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View v) {
                mediaPlaybackService.stop();
                buttonPlayPause.setImageResource(R.drawable.ic_play_circle_outline_black_48dp);
                buttonPlayPause.setEnabled(false);
                clearInfos();
            }
        });

        elapsedTimeSeekBar.setEnabled(false);
        elapsedTimeSeekBar.setProgress(0);
        elapsedTimeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlaybackService.seekTo(seekBar.getProgress());
            }
        });

        fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onClick(View view) {
                mediaPlaybackService.stop();
                clearInfos();
                Intent intent = new Intent();
                intent.setType("audio/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Choose Track"), 1);
            }
        });


        /*
        rootView.setOnTouchListener(new OnSwipeTouchListener(External_Player.this) {
            public void onSwipeTop() {
                // Toast.makeText(MainActivity.this, "top", Toast.LENGTH_SHORT).show();
            }
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            public void onSwipeRight() {

                mediaPlaybackService.stop();
                clearInfos();

                Intent movetoPlayer = new Intent(External_Player.this,MainActivity.class);
                movetoPlayer.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                startActivity(movetoPlayer);
                finish();
                // Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {


                // Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
                // Toast.makeText(MainActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });
        */

        final Rect droidTarget = new Rect(0, 0, 10 * 2, 10 * 2);
        droidTarget.offsetTo(1100, 1500);

        final Rect droidTarget1 = new Rect(0, 0, 10 * 2, 10 * 2);
        droidTarget.offsetTo(20, 20);

        final TapTargetSequence sequence = new TapTargetSequence(this)
                .targets(
                        // This tap target will target the back button, we just need to pass its containing toolbar

                        TapTarget.forView(buttonPlayPause, "This is the Play/Pause button", " first listen to the song and click the target to pause").dimColor(android.R.color.black)
                                .outerCircleColor(R.color.colorAccent)
                                .targetCircleColor(android.R.color.black)
                                .transparentTarget(true)
                                .textColor(android.R.color.black).id(1),

                        TapTarget.forView(elapsedTimeSeekBar, "Seekbar", "use this to skip through song")
                                .dimColor(android.R.color.black)
                                .outerCircleColor(R.color.colorAccent)
                                .targetCircleColor(android.R.color.black)
                                .transparentTarget(true)
                                .textColor(android.R.color.black)
                                .id(2),

                        TapTarget.forView(buttonStop, "Stop", " click it to stop the song ").dimColor(android.R.color.black)
                                .outerCircleColor(R.color.colorAccent)
                                .targetCircleColor(android.R.color.black)
                                .transparentTarget(true)
                                .textColor(android.R.color.black).id(3)
                        // Likewise, this tap target will target the search button

                        // You can also target the overflow button in your toolbar
                     /*   TapTarget.forToolbarOverflow(toolbar, "This will show more options", "But they're not useful :(").id(3),
                        // This tap target will target our droid buddy at the given target rect
                        TapTarget.forBounds(droidTarget, "Oh look!", "You can point to any part of the screen. You also can't cancel this one!")
                                .cancelable(false)
                                //.icon(droid)
                                .id(4)
                                */

                )
                .listener(new TapTargetSequence.Listener() {



                    // This listener will tell us whneen interesting(tm) events happen in regards
                    // to the sequence
                    @Override
                    public void onSequenceFinish() {


                        Toast.makeText(External_Player.this, "tutorial over", Toast.LENGTH_SHORT).show();
                        //  ((TextView) findViewById(R.id.educated)).setText("Congratulations! You're educated now!");
                    }

                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
                        Log.d("TapTargetView", "Clicked on " + lastTarget.id());
                        if(lastTarget.id()==1)
                        {

                         //   mediaPlaybackService.pause();
                          //  buttonPlayPause.setImageResource(R.drawable.ic_play_circle_outline_black_48dp);

                           // sound.pause();
                          //  play.setImageResource(R.drawable.ic_play_arrow_grey_700_24dp);
                        }


                        else if(lastTarget.id()==2)
                        {
                            //  Intent movetoAlbum = new Intent(SongActivity.this,MainActivity.class);
                            // startActivity(movetoAlbum);

                          //  elapsedTimeSeekBar.setProgress(50);
                          //  mediaPlaybackService.seekTo(50);
                            // play.setImageResource(R.drawable.ic_play_arrow_grey_700_24dp);
                        }


                        else if(lastTarget.id()==3)
                        {
                          //  Intent movetoAlbum = new Intent(SongActivity.this,MainActivity.class);
                           // startActivity(movetoAlbum);


                            mediaPlaybackService.stop();
                            clearInfos();

                            Intent movetoPlayer = new Intent(External_Player.this,MainActivity.class);
                            movetoPlayer.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            startActivity(movetoPlayer);
                            finish();
                            /*
                            mediaPlaybackService.stop();
                            buttonPlayPause.setImageResource(R.drawable.ic_play_circle_outline_black_48dp);
                            buttonPlayPause.setEnabled(false);
                            clearInfos();
                            */
                            // sound.pause();
                            // play.setImageResource(R.drawable.ic_play_arrow_grey_700_24dp);
                        }


                        else if(lastTarget.id()==4)
                        {
                            //  Intent movetoAlbum = new Intent(SongActivity.this,MainActivity.class);
                            // startActivity(movetoAlbum);

                            Intent movetoPlayer = new Intent(External_Player.this,MainActivity.class);
                            movetoPlayer.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
                            startActivity(movetoPlayer);
                            finish();
                            // sound.pause();
                            // play.setImageResource(R.drawable.ic_play_arrow_grey_700_24dp);
                        }
                        //Toast.makeText(SongActivity.this, "sup", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {
                        final AlertDialog dialog = new AlertDialog.Builder(External_Player.this)
                                .setTitle("Uh oh")
                                .setMessage("You canceled the sequence")
                                .setPositiveButton("Oops", null).show();
                        TapTargetView.showFor(dialog,
                                TapTarget.forView(dialog.getButton(DialogInterface.BUTTON_POSITIVE), "Uh oh!", "You canceled the sequence at step " + lastTarget.id())
                                        .cancelable(false)
                                        .tintTarget(false), new TapTargetView.Listener() {
                                    @Override
                                    public void onTargetClick(TapTargetView view) {
                                        super.onTargetClick(view);
                                        dialog.dismiss();
                                    }
                                });
                    }
                });

        if (getvalue1()==0) {
            TapTargetView.showFor(this, TapTarget.forView(fab, "Song selection", " Select song from storage")
                    .cancelable(false).outerCircleColor(R.color.colorAccent)
                    .targetCircleColor(android.R.color.black)
                    .drawShadow(true).tintTarget(true)                   // Whether to tint the target view's color
                    .transparentTarget(true)
                    //.titleTextDimen(R.dimen.title_text_size)
                    .tintTarget(false), new TapTargetView.Listener() {
                @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                @Override
                public void onTargetClick(TapTargetView view) {

                    //Intent movetoExt = new Intent(External_Player.this, External_Player.class);

                    savedata(1);


                    //sequence.start();
                    //Toast.makeText(MainActivity.this, albumnameString[position], Toast.LENGTH_SHORT).show();
                   // startActivity(movetoExt);

                    sequence.start();
                    mediaPlaybackService.stop();
                    clearInfos();
                    Intent intent = new Intent();
                    intent.setType("audio/*");
                    intent.setAction(Intent.ACTION_GET_CONTENT);
                    startActivityForResult(Intent.createChooser(intent, "Choose Track"), 1);
                    super.onTargetClick(view);


                    // .. which evidently starts the sequence we defined earlier
                    // sequence.start();
                }

                @Override
                public void onOuterCircleClick(TapTargetView view) {
                    super.onOuterCircleClick(view);
                    Toast.makeText(view.getContext(), "You clicked the outer circle!", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onTargetDismissed(TapTargetView view, boolean userInitiated) {
                    Log.d("TapTargetViewSample", "You dismissed me :(");
                }
            });
        }


    }


    public void savedata(int i) {
        SharedPreferences sharedpref = getSharedPreferences("value4", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpref.edit();
        editor.putInt("twovalue1", i);
       // editor.putInt("twovalue2",j);
        editor.apply();
    }

    public  int getvalue1()
    {
        SharedPreferences sharedpref = getSharedPreferences("value4", Context.MODE_PRIVATE);
        Integer i = sharedpref.getInt("twovalue1",0);
        return i;

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onResume() {

        getApplicationContext().bindService(new Intent(getApplicationContext(),
                MediaPlaybackService.class), connection, BIND_AUTO_CREATE);


        LocalBroadcastManager.getInstance(this).registerReceiver(receiverElapsedTime,
                new IntentFilter(MediaPlaybackService.MPS_RESULT)
        );
        LocalBroadcastManager.getInstance(this).registerReceiver(receiverCompleted,
                new IntentFilter(MediaPlaybackService.MPS_COMPLETED)
        );

       // clearInfos();
        super.onResume();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onBackPressed() {

        mediaPlaybackService.stop();
        clearInfos();

        Intent movetoPlayer = new Intent(External_Player.this,MainActivity.class);
        movetoPlayer.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);
        startActivity(movetoPlayer);
        finish();


        super.onBackPressed();
    }

    @Override
    protected void onPause() {

        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiverElapsedTime);
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiverCompleted);
        super.onPause();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(resultCode==RESULT_OK)
        {

            Uri selectedtrack = data.getData();

            mediaPlaybackService.init(selectedtrack);

            initInfos(selectedtrack);
        }
    }

    private String secondsToString(int time) {
        time = time / 1000;
        return String.format("%2d:%02d", time / 60, time % 60);
    }

    private void initInfos(Uri uri) {
        if (uri != null) {

            MediaMetadataRetriever mData = new MediaMetadataRetriever();
            mData.setDataSource(this, uri);

            int duration = Integer.parseInt(mData.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION));

            titleTextView.setText(mData.extractMetadata(MediaMetadataRetriever.METADATA_KEY_TITLE));
            artistTextView.setText(mData.extractMetadata(MediaMetadataRetriever.METADATA_KEY_ARTIST));
            durationTextView.setText(secondsToString(duration));

            elapsedTimeSeekBar.setMax(duration);
            elapsedTimeSeekBar.setEnabled(true);

            try {
                byte art[] = mData.getEmbeddedPicture();
                Bitmap image = BitmapFactory.decodeByteArray(art, 0, art.length);
                albumArt.setImageBitmap(image);

                Palette.from(image).generate(new Palette.PaletteAsyncListener() {
                    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
                    @Override
                    public void onGenerated(Palette palette) {
                        setColors(palette);
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void updateElapsedTime(int elapsedTime) {
        elapsedTimeSeekBar.setProgress(elapsedTime);
        elapsedTimeTextView.setText(secondsToString(elapsedTime));


        if (mediaPlaybackService.isPlaying()) {
            buttonPlayPause.setEnabled(true);
            buttonPlayPause.setImageResource(R.drawable.ic_pause_circle_outline_black_48dp);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void clearInfos() {
        durationTextView.setText("");
        elapsedTimeTextView.setText("");
        titleTextView.setText("-");
        artistTextView.setText("-");
        elapsedTime = 0;
        elapsedTimeSeekBar.setEnabled(false);
        elapsedTimeSeekBar.setProgress(0);
        albumArt.setImageResource(R.drawable.ic_album_white_400_128dp);
        buttonPlayPause.setEnabled(false);
        buttonPlayPause.setImageResource(R.drawable.ic_play_circle_outline_black_48dp);
        setColors(null);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void setColors(Palette palette) {
        int colorPrimaryDark = (palette != null) ? palette.getDarkVibrantColor(
                ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark)
        ) : ContextCompat.getColor(getApplicationContext(), R.color.colorPrimaryDark);
        int colorPrimary  = (palette != null) ? palette.getVibrantColor(
                ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary)
        ) : ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary);
        int colorAccent = (palette != null) ? palette.getLightVibrantColor(
                ContextCompat.getColor(getApplicationContext(), R.color.colorAccent)
        ) : ContextCompat.getColor(getApplicationContext(), R.color.colorAccent);


        if (Build.VERSION.SDK_INT >= 21) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(colorPrimaryDark);
        }

        rootView.setBackgroundColor(colorPrimary);

        buttonPlayPause.setColorFilter(colorAccent);
        buttonStop.setColorFilter(colorAccent);

        elapsedTimeSeekBar.getProgressDrawable().setColorFilter(colorAccent, PorterDuff.Mode.SRC_IN);
        elapsedTimeSeekBar.getThumb().setColorFilter(colorAccent, PorterDuff.Mode.SRC_IN);

        fab.setBackgroundTintList(ColorStateList.valueOf(colorAccent));
    }
}