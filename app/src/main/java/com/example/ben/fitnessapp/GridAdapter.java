package com.example.ben.fitnessapp;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class GridAdapter extends BaseAdapter{
    String [] result;
    Context context;
    int [] imageId;

    private static LayoutInflater inflater=null;

    public GridAdapter(Context context){
        this.context = context;
    }

    public GridAdapter(MainMenuActivity mainActivity, String[] osNameList, int[] osImages) {
        // TODO Auto-generated constructor stub
        result=osNameList;
        context=mainActivity;
        imageId=osImages;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return result.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView os_text;
        ImageView os_img;
    }
    @Override
    public View getView(final int position, final View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;

        rowView = inflater.inflate(R.layout.gridlayout, null);
        holder.os_text =(TextView) rowView.findViewById(R.id.home_texts);
        holder.os_img =(ImageView) rowView.findViewById(R.id.home_images);

        holder.os_text.setText(result[position]);
        holder.os_img.setImageResource(imageId[position]);

        rowView.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                /*Toast.makeText(context, "You Clicked "+result[position], Toast.LENGTH_SHORT).show();*/

                //setting a string as the result
                String body = result[position];
/*                Toast.makeText(context, "You Clicked "+body, Toast.LENGTH_SHORT).show();*/

                //passing value
                Intent choice = new Intent(context,ExerciseChoiceActivity.class);
                choice.putExtra("key",body);
                context.startActivity(choice);
            }
        });

        return rowView;
    }
}
