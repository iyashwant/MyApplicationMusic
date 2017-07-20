package com.example.iyashwant.myapplicationmusic;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

/**
 * Created by iyashwant on 19/7/17.
 */

public class AlbumGrid extends BaseAdapter {

    private Context mContext;
    private final String[] albumartID;
    private final String[] albumnameString;
    private final String[] artistString;

    public AlbumGrid(Context context, String[] albumartID, String[] albumnameString, String[] artistString) {
        mContext = context;
        this.albumartID = albumartID;
        this.albumnameString = albumnameString;
        this.artistString = artistString;
    }

    @Override
    public int getCount() {
        return albumnameString.length;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View gridViewAndroid;

        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) mContext
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            gridViewAndroid = inflater.inflate(R.layout.album_grid,parent,false);

        } else {
            gridViewAndroid = (View) convertView;
        }

        TextView albumname = (TextView) gridViewAndroid.findViewById(R.id.albumname);
        TextView artist = (TextView) gridViewAndroid.findViewById(R.id.artist);
        ImageView albumart = (ImageView) gridViewAndroid.findViewById(R.id.albumart);
        albumname.setText(albumnameString[position]);
        artist.setText(artistString[position]);
        Picasso.with(mContext).load(albumartID[position]).into(albumart);
        return gridViewAndroid;
    }
}
