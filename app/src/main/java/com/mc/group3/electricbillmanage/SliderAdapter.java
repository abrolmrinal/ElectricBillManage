package com.mc.group3.electricbillmanage;

import android.content.Context;
import android.support.constraint.ConstraintLayout;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class SliderAdapter extends android.support.v4.view.PagerAdapter {
    Context context;
    LayoutInflater layoutInflater;

    public SliderAdapter(Context context)
    {
        this.context=context;

    }

    public int[] slides_images = { R.drawable.first_wt,R.drawable.sec_wt,R.drawable.third_wt };

//    public String[] slides_heading={"           Get live meter reading \n                           and \n                 usage analysis ",
//    "        Get monthly bills updates \n                            and \n                      bill history ",
//    "                 File complaints \n                          and\n      track your complaint status"};

    public String[] slides_heading = {"Get live meter reading\nand usage analysis",
                                        "Get monthly bills updates\nand bill history",
                                        "File complaints and\ntrack your complaint status"};

    public String[] slides_upper = {"METER READINGS","BILLS","COMPLAINTS"};





    @Override
    public int getCount() {
        return slides_heading.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object o) {
        return view == (ConstraintLayout) o;
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

        ViewPager viewPager = container.findViewById(R.id.slideviewpager);

//        if(position == 0){
//            viewPager.setBackgroundColor(container.getResources().getColor(R.color.colorWT1));
//        }
//
//        if(position == 1){
//            viewPager.setBackgroundColor(container.getResources().getColor(R.color.colorWT2));
//
//        }
        if(position == 2)
        {
//            viewPager.setBackgroundColor(container.getResources().getColor(R.color.colorWT3));
            bt.setBackground(view.getResources().getDrawable(R.drawable.ic_check_white_24dp));
            bt.setVisibility(View.VISIBLE);
        }
        else
        {
//            bt.setVisibility(View.GONE);
            bt.setClickable(false);
        }


        container.addView(view);

        return  view;

    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ConstraintLayout)object);
    }
}
