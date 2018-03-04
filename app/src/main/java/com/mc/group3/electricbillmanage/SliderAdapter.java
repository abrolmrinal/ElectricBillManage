package com.mc.group3.electricbillmanage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by abhijeet on 4/3/18.
 */

public class SliderAdapter extends android.support.v4.view.PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context)
    {
        this.context=context;

    }

    public int[] slides_images = { R.drawable.sec,R.drawable.third,R.drawable.third_walk };

    public String[] slides_heading={"           Get live meter reading \n                           and \n                 usage analysis ",
    "        Get monthly bills updates \n                            and \n                      bill history ",
    "                 File complaints \n                          and\n      track your complaint status"};

    public String[] slides_upper = {"METER READING","BILLS","COMPLAINTS"};





    @Override
    public int getCount() {
        return slides_heading.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == (RelativeLayout) o;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.activity_slide,container,false);


        ImageView slideImageView = (ImageView) view.findViewById(R.id.slide_image);
        TextView slideTextView = (TextView) view.findViewById((R.id.slide_text));
        TextView slideTextView1 = (TextView) view.findViewById((R.id.slide_upper));

        Button bt = (Button) view.findViewById(R.id.button);

        slideImageView.setImageResource(slides_images[position]);
        slideTextView.setText(slides_heading[position]);
        slideTextView1.setText(slides_upper[position]);
        bt.setText("Login");

        if(position==2)
        {
            bt.setVisibility(View.VISIBLE);
        }
        else
        {
            bt.setVisibility(View.GONE);
        }


        container.addView(view);

        return  view;

    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((RelativeLayout)object);
    }
}
