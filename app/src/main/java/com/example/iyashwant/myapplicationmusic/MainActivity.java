package com.example.iyashwant.myapplicationmusic;

import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.getkeepsafe.taptargetview.TapTarget;
import com.getkeepsafe.taptargetview.TapTargetSequence;
import com.getkeepsafe.taptargetview.TapTargetView;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.MenuInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.PopupMenu;
import android.widget.Toast;

import jp.wasabeef.picasso.transformations.BlurTransformation;

import static java.security.AccessController.getContext;

public class MainActivity extends AppCompatActivity {

    GridView gridView;
    ViewGroup rootView;
    com.github.clans.fab.FloatingActionButton floatingActionButton;


   // MediaPlaybackService mediaPlaybackService;
    String[] albumnameString = {
            "Collage",
            "Fifty shades darker",
            "Memories...",
            "Divide",
            "Back from the Edge",
            "A Head full of Dreams"

    } ;

    String[] artistString = {
            "The Chainsmokers",
            "Tylor Swift",
            "The Chainsmokers",
            "Ed Sheeran",
            "James Arthur",
            "Cold Play"

    };

    String[] albumartID = {
            "http://is3.mzstatic.com/image/thumb/Music71/v4/8b/78/46/8b78469f-6b82-0fb7-fddd-40a66f356347/source/1200x630bb.jpg",
            "http://e-cdn-images.deezer.com/images/cover/fbbc4eaec3e7a083940c84769ea2b96c/500x500.jpg",
            "https://is4-ssl.mzstatic.com/image/thumb/Music111/v4/02/81/16/02811613-b75f-7223-cd91-63448af2ffde/source/1200x630bb.jpg",
            "https://is5-ssl.mzstatic.com/image/thumb/Music122/v4/ef/ca/a4/efcaa44a-517a-98e3-2353-5bff15471ed5/source/1200x630bb.jpg",
            "https://www.jbhifi.com.au/FileLibrary/ProductResources/Images/188420-L-LO.jpg",
            "https://is1-ssl.mzstatic.com/image/thumb/Music62/v4/b8/5d/ff/b85dff39-32cd-34eb-7b0e-f087707bf254/source/1200x630bb.jpg"
    };



    // Using deprecated methods makes you look way cool
    // droidTarget.offset(display.getWidth() , display.getHeight() / 2);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rootView = (ViewGroup) findViewById(android.R.id.content);
        // mediaPlaybackService.pause();

        Picasso.with(this).load("https://is5-ssl.mzstatic.com/image/thumb/Music122/v4/ef/ca/a4/efcaa44a-517a-98e3-2353-5bff15471ed5/source/1200x630bb.jpg").resize(300,300).fetch();

        final Display display = getWindowManager().getDefaultDisplay();
        // Load our little droid guy
        // final Drawable droid = ContextCompat.getDrawable(this,);
        // Tell our droid buddy where we want him to appear
        final Rect droidTarget = new Rect(0, 0, 10 * 2, 10 * 2);
        droidTarget.offsetTo(1100, 1500);

        floatingActionButton = (com.github.clans.fab.FloatingActionButton) findViewById(R.id.material_design_floating_action_menu_item3);
        Picasso.with(this).load("http://is3.mzstatic.com/image/thumb/Music71/v4/8b/78/46/8b78469f-6b82-0fb7-fddd-40a66f356347/source/1200x630bb.jpg").transform(new BlurTransformation(getApplicationContext())).resize(350, 600).fetch();
        Picasso.with(this).load("http://e-cdn-images.deezer.com/images/cover/fbbc4eaec3e7a083940c84769ea2b96c/500x500.jpg").transform(new BlurTransformation(getApplicationContext())).resize(350, 600).fetch();
        Picasso.with(this).load("https://is4-ssl.mzstatic.com/image/thumb/Music111/v4/02/81/16/02811613-b75f-7223-cd91-63448af2ffde/source/1200x630bb.jpg").transform(new BlurTransformation(getApplicationContext())).resize(350, 600).fetch();
        Picasso.with(this).load("https://is5-ssl.mzstatic.com/image/thumb/Music122/v4/ef/ca/a4/efcaa44a-517a-98e3-2353-5bff15471ed5/source/1200x630bb.jpg").transform(new BlurTransformation(getApplicationContext())).resize(350, 600).fetch();
        Picasso.with(this).load("https://www.jbhifi.com.au/FileLibrary/ProductResources/Images/188420-L-LO.jpg").transform(new BlurTransformation(getApplicationContext())).resize(350, 600).fetch();
        // Picasso.with(this).load("").transform(new BlurTransformation(getApplicationContext())).resize(350,600).fetch();
        Picasso.with(this).load("https://is1-ssl.mzstatic.com/image/thumb/Music62/v4/b8/5d/ff/b85dff39-32cd-34eb-7b0e-f087707bf254/source/1200x630bb.jpg").transform(new BlurTransformation(getApplicationContext())).resize(350, 600).fetch();


        gridView = (GridView) findViewById(R.id.gridView1);
        // For the gridview
        AlbumGrid adapter = new AlbumGrid(MainActivity.this, albumartID, albumnameString, artistString);
        gridView.setAdapter(adapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent movetoAlbum = new Intent(MainActivity.this, SongActivity.class)
                        .putExtra("albumname", albumnameString[position])
                        .putExtra("artist", artistString[position])
                        .putExtra("albumart", albumartID[position]);

                //Toast.makeText(MainActivity.this, albumnameString[position], Toast.LENGTH_SHORT).show();


                startActivity(movetoAlbum);
            }
        });


        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent movetoPlayer = new Intent(MainActivity.this, External_Player.class);

                startActivity(movetoPlayer);

                //if(!S_city.isSelected())


            }
        });



     /*   final TapTargetSequence sequence = new TapTargetSequence(this)
                .targets(
                        // This tap target will target the back button, we just need to pass its containing toolbar
                        TapTarget.forToolbarNavigationIcon(toolba, "This is the back button", sassyDesc).id(1),
                        // Likewise, this tap target will target the search button
                        TapTarget.forToolbarMenuItem(toolbar, R.id.search, "This is a search icon", "As you can see, it has gotten pretty dark around here...")
                                .dimColor(android.R.color.black)
                                .outerCircleColor(R.color.colorAccent)
                                .targetCircleColor(android.R.color.black)
                                .transparentTarget(true)
                                .textColor(android.R.color.black)
                                .id(2),
                        // You can also target the overflow button in your toolbar
                        TapTarget.forToolbarOverflow(, "This will show more options", "But they're not useful :(").id(3),
                        // This tap target will target our droid buddy at the given target rect
                        TapTarget.forBounds(droidTarget, "Oh look!", "You can point to any part of the screen. You also can't cancel this one!")
                                .cancelable(false)
                                //.icon(droid)
                                .id(4)
                )
                .listener(new TapTargetSequence.Listener() {


                    // This listener will tell us whneen interesting(tm) events happen in regards
                    // to the sequence
                    @Override
                    public void onSequenceFinish() {
                        //((TextView) findViewById(R.id.educated)).setText("Congratulations! You're educated now!");
                    }

                    @Override
                    public void onSequenceStep(TapTarget lastTarget, boolean targetClicked) {
                        Log.d("TapTargetView", "Clicked on " + lastTarget.id());
                        if(lastTarget.id()==2)
                            Toast.makeText(MainActivity.this, "sup", Toast.LENGTH_SHORT).show();

                    }

                    @Override
                    public void onSequenceCanceled(TapTarget lastTarget) {
                        final AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
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



*/
        if (getvalue1() == 0) {
            TapTargetView.showFor(this, TapTarget.forBounds(droidTarget, "Welcome to the music player", " Just take a short one time tutorial")
                    .cancelable(false)
                    .drawShadow(true).tintTarget(true)                   // Whether to tint the target view's color
                    .transparentTarget(true)
                    //.titleTextDimen(R.dimen.title_text_size)
                    .tintTarget(false), new TapTargetView.Listener() {
                @Override
                public void onTargetClick(TapTargetView view) {

                    Intent movetoAlbum = new Intent(MainActivity.this, SongActivity.class)
                            .putExtra("albumname", albumnameString[3])
                            .putExtra("artist", artistString[3])
                            .putExtra("albumart", albumartID[3]);



                    //Toast.makeText(MainActivity.this, albumnameString[position], Toast.LENGTH_SHORT).show();


                    startActivity(movetoAlbum);
                    super.onTargetClick(view);

                    savedata(1, 1);
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


        if (getvalue2()==1) {
            TapTargetView.showFor(this, TapTarget.forView(floatingActionButton, "External Player", " You can use this to play songs from memrory card")
                    .cancelable(false)
                    .drawShadow(true).tintTarget(true)                   // Whether to tint the target view's color
                    .transparentTarget(true)
                    //.titleTextDimen(R.dimen.title_text_size)
                    .tintTarget(false), new TapTargetView.Listener() {
                @Override
                public void onTargetClick(TapTargetView view) {

                    Intent movetoExt = new Intent(MainActivity.this, External_Player.class);

                    savedata(1, 2);

                    //Toast.makeText(MainActivity.this, albumnameString[position], Toast.LENGTH_SHORT).show();
                    startActivity(movetoExt);
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




       /* RelativeLayout relay =(RelativeLayout)findViewById(R.id.relay);
        rootView.setOnTouchListener(new OnSwipeTouchListener(MainActivity.this) {
            public void onSwipeTop() {
               // Toast.makeText(MainActivity.this, "top", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeRight() {
               // Toast.makeText(MainActivity.this, "right", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeLeft() {

                Intent movetoPlayer = new Intent(MainActivity.this,External_Player.class);

                startActivity(movetoPlayer);

               // Toast.makeText(MainActivity.this, "left", Toast.LENGTH_SHORT).show();
            }
            public void onSwipeBottom() {
               // Toast.makeText(MainActivity.this, "bottom", Toast.LENGTH_SHORT).show();
            }

        });


    }
*/
    /*
    @Override
    public void onBackPressed() {
        super.onBackPressed();

        this.finish();
        overridePendingTransition(R.anim.right_out,R.anim.left_enter);
    }
    */



    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent homeIntent = new Intent(Intent.ACTION_MAIN);
        homeIntent.addCategory( Intent.CATEGORY_HOME );
        homeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }

    public void savedata(int i ,int j) {
        SharedPreferences sharedpref = getSharedPreferences("value2", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpref.edit();
        editor.putInt("twovalue1", i);
        editor.putInt("twovalue2",j);
        editor.apply();
    }

    public  int getvalue1()
    {
        SharedPreferences sharedpref = getSharedPreferences("value2", Context.MODE_PRIVATE);
        Integer i = sharedpref.getInt("twovalue1",0);
        return i;

    }


    public  int getvalue2()
    {
        SharedPreferences sharedpref = getSharedPreferences("value2", Context.MODE_PRIVATE);
        Integer i = sharedpref.getInt("twovalue2",0);
        return i;

    }
}

