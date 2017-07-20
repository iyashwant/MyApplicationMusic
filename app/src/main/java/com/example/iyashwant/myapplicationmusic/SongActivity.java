package com.example.iyashwant.myapplicationmusic;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Rect;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import jp.wasabeef.picasso.transformations.BlurTransformation;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.IOException;

public class SongActivity extends AppCompatActivity {



    ListView listView;
    TextView art, selected_track_title;
    String albumname, artist, albumart;
    ImageView play, pause;
    AppBarLayout app_bar;
    MediaPlayer music;
    MediaPlayer sound;
   // ProgressBar spinner;
    SeekBar sBar;



    String[] collage = {
            "All We Know", "Closer"
    };

    String[] collagelinks = {
            "http://yt-files.com/yt-dd.php?id=lEi_XBg2Fpk&hash=75700e6897bd5c8a94a4df0644e08d70&name=The%20Chainsmokers%20-%20All%20We%20Know%20(Audio)%20ft.%20Phoebe%20Ryan",
            "http://7xmusic.com/download/english/Closer%20-%20128Kbps%20-%20(www.songspksongspk.audio).mp3"
    };

    String[] fifty = {
            "I dont wanna live forever"
    };

    String[] fiftylinks = {
            "http://songspkdload.com/English/I%20Don't%20Wanna%20Live%20Forever-Songspksongspk.Audio.mp3"
    };


    String[] memories = {
            "The One", "Break up Every Night", "Something Just Like This", "My Type", "Paris", "Young"
    };


    String[] memoriesurl = {
            "http://songspkdload.com/English/Memories%20Do%20Not%20Open%20Full%20Album/The%20Chainsmokers-The%20One-Songspksongspks.Com.mp3",
            "http://songspkdload.com/English/Memories%20Do%20Not%20Open%20Full%20Album/Break%20Up%20Every%20Night-Songspksongspks.Com.mp3",
            "http://songspkdload.com/English/Memories%20Do%20Not%20Open%20Full%20Album/Something%20Just%20Like%20This-Songspksongspks.mp3",
            "http://songspkdload.com/English/Memories%20Do%20Not%20Open%20Full%20Album/My%20Type-Songspksongspks.Com.mp3",
            "http://songspkdload.com/English/Memories%20Do%20Not%20Open%20Full%20Album/Paris%20-Songspksongspks.Com.mp3",
            "http://songspkdload.com/English/Memories%20Do%20Not%20Open%20Full%20Album/Young-Songspksongspks.Com.mp3"

    };

    String[] back ={"Say you wont ley go"};

    String[] backlinks={"http://dl.bita-server.in/hossein2/95/6/Music/19/James%20Arthur%20-%20Say%20You%20Won%E2%80%99t%20Let%20Go.mp3"};

    String[] head ={"A head full of dreams","Hymn for the weekend"};
    String[] headlinks ={"http://srv2.teupload.com/Music/Album/Coldplay%20-%20A%20Head%20Full%20of%20Dreams/Coldplay%20-%20A%20Head%20Full%20of%20Dreams%20128/01%20A%20Head%20Full%20of%20Dreams.mp3","https://www.yt-download.org/download/128-596f00c61d6a1-4176000/mp3/YykjpeuMNEk/Coldplay%2B-%2BHymn%2BFor%2BThe%2BWeekend%2B%2528Official%2BVideo%2529.mp3"};

    String[] divide = {
            "Castle on the Hill", "Shape of You", "Galway Girl", "Supermarket Flowers", "Perfect"
    };

    String[] divideurl = {
            "https://www.dlstreams.com/download/a1d34f499ff00d3697eb4c9182faca46",
            "https://www.dlstreams.com/download/9330a932530aeb686f9a88b794c0ba4d",
            "https://www.dlstreams.com/download/e2cbff7b67fd30700d1a00260cb2d44e",
            "https://www.dlstreams.com/download/e3627710ac7be14dbcdc226982110337",
            "https://www.dlstreams.com/download/85d50577121c9cca2f6ddacfef8693c4"
    };

    String the_url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song);





        Toolbar toolbar = (Toolbar) findViewById(R.id.tool);
        setSupportActionBar(toolbar);

       // setSupportActionBar(toolbar);

        listView = (ListView) findViewById(R.id.listView);

        albumname = getIntent().getStringExtra("albumname");
        albumart = getIntent().getStringExtra("albumart");
        artist = getIntent().getStringExtra("artist");

      // app_bar = (AppBarLayout) findViewById(R.id.app_bar);
        selected_track_title = (TextView) findViewById(R.id.selected_track_title);
        play = (ImageButton) findViewById(R.id.play);
        TextView Aname =(TextView)findViewById(R.id.Aname);
        TextView artistName =(TextView)findViewById(R.id.artistName);
       // pause = (ImageButton) findViewById(R.id.pause);
       // spinner = (ProgressBar) findViewById(R.id.loading);
       // spinner.setVisibility(View.GONE);


        Aname.setText(albumname);
        artistName.setText(artist);
        final RelativeLayout relativeLayout =(RelativeLayout)findViewById(R.id.song_bg);


        final Display display = getWindowManager().getDefaultDisplay();
        // Load our little droid guy
        // final Drawable droid = ContextCompat.getDrawable(this,);
        // Tell our droid buddy where we want him to appear
        final Rect droidTarget = new Rect(0, 0, 10 * 2, 10 * 2);
        droidTarget.offsetTo(200,1050);

        final Rect droidTarget2 = new Rect(0, 0, 10 * 2, 10 * 2);
        //droidTarget.offsetTo(200,1050);
        droidTarget2.offset(40, display.getHeight()-80 );

        Picasso.with(getApplicationContext()).load(albumart).transform(new BlurTransformation(getApplicationContext())).resize(350,600).into(new Target() {
            @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                Drawable d = new BitmapDrawable(getResources(), bitmap);
                relativeLayout.setBackground(d);
            }

            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
                Log.d("TAG", "FAILED");
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
                Log.d("TAG", "Prepare Load");
            }
        });

        final SongAdapter customList1 = new SongAdapter(this, collage, collagelinks);
        final SongAdapter customList2 = new SongAdapter(this, memories, memoriesurl);
        final SongAdapter customList3 = new SongAdapter(this, fifty, fiftylinks);
        final SongAdapter customList4 = new SongAdapter(this, back, backlinks);
        final SongAdapter customList5 = new SongAdapter(this, divide, divideurl);
        final SongAdapter customList6 = new SongAdapter(this,head,headlinks);

        switch (albumname) {
            case "Collage":

                listView.setAdapter(customList1);
              //  append();
                break;
            case "Memories...":

                listView.setAdapter(customList2);
               // append();
                break;

            case "Fifty shades darker":

                listView.setAdapter(customList3);



              //  append();
                break;

            case "Back from the Edge":

                listView.setAdapter(customList4);
                //  append();
                break;

            case "Divide":

                listView.setAdapter(customList5);
              //  append();
                break;



            case "A Head full of Dreams":

                listView.setAdapter(customList6);
                //  append();
                break;
        }

        music = new MediaPlayer();
        music.setAudioStreamType(AudioManager.STREAM_MUSIC);

        sBar = (SeekBar) findViewById(R.id.sBar);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                music.reset();

                TextView songname = (TextView) view.findViewById(R.id.songname);
                String name = songname.getText().toString();
                selected_track_title.setText(name);
                TextView surl = (TextView) view.findViewById(R.id.surl);
                the_url = surl.getText().toString();
                play.setImageResource(R.drawable.ic_pause_arrow_grey_700_24dp);


                try {
                    music.setDataSource(the_url);
                    music.prepare();
                    music.start();

                    sBar.setMax(music.getDuration());
                    sBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

                        @Override
                        public void onStopTrackingTouch(SeekBar seekBar) {
                        }

                        @Override
                        public void onStartTrackingTouch(SeekBar seekBar) {
                        }

                        @Override
                        public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                            if (music != null && fromUser) {
                                music.seekTo(progress);
                            }
                        }
                    });


                  //  if (!music.isPlaying())
                      //  spinner.setVisibility(View.VISIBLE);
                  //  else
                       // spinner.setVisibility(View.GONE);
                } catch (IOException e) {

                }
            }
        });

        play.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!music.isPlaying()) {

                    play.setImageResource(R.drawable.ic_pause_arrow_grey_700_24dp);

                    music.start();
                }
                else if (music.isPlaying()) {

                    play.setImageResource(R.drawable.ic_play_arrow_grey_700_24dp);
                    music.pause();
                }
            }
        });



        getSupportActionBar().setTitle(albumname);

        art = (TextView) findViewById(R.id.artist);
       // art.setText("jj");


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);




        final TapTargetSequence sequence = new TapTargetSequence(this)
                .targets(
                        // This tap target will target the back button, we just need to pass its containing toolbar
                        TapTarget.forView(play, "This is the Play/Pause button", " click it to pause").dimColor(android.R.color.black)
                                .outerCircleColor(R.color.colorAccent)
                                .targetCircleColor(android.R.color.black)
                                .transparentTarget(true)
                                .textColor(android.R.color.black).id(1),
                        TapTarget.forBounds(droidTarget2, "Back button", " click it to go back ").dimColor(android.R.color.black)
                                .outerCircleColor(R.color.colorAccent)
                                .targetCircleColor(android.R.color.black)
                                .transparentTarget(true)
                                .textColor(android.R.color.black).id(2)
                        // Likewise, this tap target will target the search button
                       /* TapTarget.forView(toolbar, R.id., "This is a search icon", "As you can see, it has gotten pretty dark around here...")
                                .dimColor(android.R.color.black)
                                .outerCircleColor(R.color.colorAccent)
                                .targetCircleColor(android.R.color.black)
                                .transparentTarget(true)
                                .textColor(android.R.color.black)
                                .id(2),
                        // You can also target the overflow button in your toolbar
                        TapTarget.forToolbarOverflow(toolbar, "This will show more options", "But they're not useful :(").id(3),
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


                       // Toast.makeText(SongActivity.this, "over", Toast.LENGTH_SHORT).show();
                      //  ((TextView) findViewById(R.id.educated)).setText("Congratulations! You're educated now!");
                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
                        Log.d("TapTargetView", "Clicked on " + lastTarget.id());
                        if(lastTarget.id()==1)
                        {
                            sound.pause();
                            play.setImageResource(R.drawable.ic_play_arrow_grey_700_24dp);
                        }

                        else if(lastTarget.id()==2)
                        {
                            Intent movetoAlbum = new Intent(SongActivity.this,MainActivity.class);
                            startActivity(movetoAlbum);


                           // sound.pause();
                           // play.setImageResource(R.drawable.ic_play_arrow_grey_700_24dp);
                        }
                            //Toast.makeText(SongActivity.this, "sup", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {
                        final AlertDialog dialog = new AlertDialog.Builder(SongActivity.this)
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



        if(getvalue1()==0) {

            TapTargetView.showFor(this, TapTarget.forBounds(droidTarget, "Song list", "Click on the song ")
                    .cancelable(false)
                    .drawShadow(true).tintTarget(true)                   // Whether to tint the target view's color
                    .transparentTarget(true)
                    //.titleTextDimen(R.dimen.title_text_size)
                    .tintTarget(false), new TapTargetView.Listener() {
                @Override
                public void onTargetClick(TapTargetView view) {

                    sound = MediaPlayer.create(getApplicationContext(), R.raw.gal);
                    sound.start();
                    sound.setLooping(true);
                    play.setImageResource(R.drawable.ic_pause_arrow_grey_700_24dp);
                    savedata(1);
                    sequence.start();

                    super.onTargetClick(view);


                    // .. which evidently starts the sequence we defined earlier
                    // sequence.start();
                }

                @Override
                public void onOuterCircleClick(TapTargetView view) {
                    super.onOuterCircleClick(view);
                    Toast.makeText(view.getContext(), "Please click the target", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onTargetDismissed(TapTargetView view, boolean userInitiated) {
                    Log.d("TapTargetViewSample", "You dismissed me :(");
                }
            });
        }



    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }

    public void savedata(int i) {
        SharedPreferences sharedpref = getSharedPreferences("value3", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpref.edit();
        editor.putInt("twovalue1", i);
        //editor.putInt("twovalue2",j);
        editor.apply();
    }

    public  int getvalue1()
    {
        SharedPreferences sharedpref = getSharedPreferences("value3", Context.MODE_PRIVATE);
        Integer i = sharedpref.getInt("twovalue1",0);
        return i;

    }



}
