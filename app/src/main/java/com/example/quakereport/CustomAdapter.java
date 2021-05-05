package com.example.quakereport;
import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import  java.lang.Math;


public class CustomAdapter extends ArrayAdapter<WordsList>
{
    private static final String LOCATION_SEPARATOR="of";


    public CustomAdapter( Context context,List<WordsList> objects)
    {
        super(context,0,objects);

    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        View listItemView =convertView;
        if(listItemView==null)
        {
         listItemView=LayoutInflater.from(getContext()).inflate(R.layout.patternlist,parent,false);
        }
        WordsList wordQuake=getItem(position);

        TextView magnitudeView=(TextView)listItemView.findViewById(R.id.magnitude);
        String formattedMagnitude=formatMagnitude(wordQuake.getMagnitude());
        GradientDrawable magnitudeCircle=(GradientDrawable)magnitudeView.getBackground();
        int magnitudeColor=getMagnitudeColor(wordQuake.getMagnitude());
        magnitudeCircle.setColor(magnitudeColor);
        magnitudeView.setText(formattedMagnitude);

        String original_loaction=wordQuake.getCity();
        String primary_location;
        String offset_location;
        if(original_loaction.contains(LOCATION_SEPARATOR))
        {
            String[] parts=original_loaction.split(LOCATION_SEPARATOR);
            offset_location=parts[0]+LOCATION_SEPARATOR;
            primary_location=parts[1];
        }
        else
        {
            offset_location=getContext().getString(R.string.near_the);
            primary_location=original_loaction;
        }

        TextView primaryLocationView=(TextView)listItemView.findViewById(R.id.primary_location);
        TextView offsetLocationView=(TextView)listItemView.findViewById(R.id.offset_location);
        primaryLocationView.setText(primary_location);
        offsetLocationView.setText(offset_location);
        TextView dateView=(TextView)listItemView.findViewById(R.id.date);
        Date dateObject = new Date(wordQuake.getmTimeInMilliseconds());
        String formatedDate=formatDate(dateObject);
        dateView.setText(formatedDate);

        TextView timeView =(TextView)listItemView.findViewById(R.id.time);
        String formatedTime=formatTime(dateObject);
        timeView.setText(formatedTime);

        return listItemView;

    }

    private int getMagnitudeColor(double magnitude) {
        int mColorResourseId;
        int mFloormagnitude=(int) Math.floor(magnitude);
        switch (mFloormagnitude) {
            case 0:
            case 1: {
                mColorResourseId = R.color.magnitude1;
                break;
            }
            case 2:
            {
                mColorResourseId = R.color.magnitude2;
                break;
            }
            case 3:
            {
                mColorResourseId = R.color.magnitude3;
                break;
            }
            case 4:
            {
                mColorResourseId = R.color.magnitude4;
                break;
            }
            case 5:
            {
                mColorResourseId = R.color.magnitude5;
                break;
            }
            case 6:
            {
                mColorResourseId = R.color.magnitude6;
                break;
            }
            case 7:
            {
                mColorResourseId = R.color.magnitude7;
                break;
            }
            case 8:
            {
                mColorResourseId = R.color.magnitude8;
                break;
            }
            case 9:
            {
                mColorResourseId = R.color.magnitude9;
                break;
            }
            default:
            {
                mColorResourseId = R.color.magnitude10plus;
                break;
            }
        }
        return ContextCompat.getColor(getContext(),mColorResourseId);
    }

    private String formatMagnitude(double magnitude) {
        DecimalFormat magnitudeFormat=new DecimalFormat("0.0");
        return magnitudeFormat.format(magnitude) ;
    }

    private String formatTime(Date dateObject)
    {
        SimpleDateFormat timeFormat=new SimpleDateFormat("h:mm a");
        return timeFormat.format(dateObject);
    }

    private String formatDate(Date date)
    {
        SimpleDateFormat dateFormat=new SimpleDateFormat("LLL dd, yyyy");
        return dateFormat.format(date);
    }

}
