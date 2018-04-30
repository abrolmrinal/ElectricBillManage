package com.mc.group3.electricbillmanage;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Settings extends Fragment {


    public Settings() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        Button btn = (Button)view.findViewById(R.id.button_settings);
        final EditText input = (EditText)view.findViewById(R.id.limit_settings);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String tmp = input.getText().toString();
                float f=0;
                try
                {
                    f = Float.valueOf(tmp.trim()).floatValue();
                }
                catch (NumberFormatException nfe)
                {
                    System.err.println("NumberFormatException: " + nfe.getMessage());
                }

                FirebaseBackgroundService.limit=f;
                getActivity().stopService(new Intent(getContext(), FirebaseBackgroundService.class));
                getActivity().startService(new Intent(getContext(), FirebaseBackgroundService.class));
            }
        });

        return view;
    }

}
