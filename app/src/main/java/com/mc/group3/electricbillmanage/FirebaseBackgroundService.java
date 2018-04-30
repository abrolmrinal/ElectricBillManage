package com.mc.group3.electricbillmanage;

import android.app.IntentService;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;


public class FirebaseBackgroundService extends IntentService {

    private ValueEventListener handler;
    private String curr_addr;
    public static float limit=1.0f;


     FirebaseBackgroundService()
    {
        super("Usage alert");
        System.out.println("in service constructor");

    }

    @Override
    protected void onHandleIntent(Intent workIntent) {
        // Gets data from the incoming Intent
        String dataString = workIntent.getDataString();

        FirebaseUser firebaseUser= FirebaseAuth.getInstance().getCurrentUser();
        FirebaseDatabase fdb= FirebaseDatabase.getInstance();
        DatabaseReference myref=fdb.getReference();
        final DatabaseReference daily_usage=myref.child("day_reading_addr").getRef();
        DatabaseReference my_addr=myref.child("user_data").child(firebaseUser.getUid()).child("address").getRef();
        System.out.println("In service ");

        my_addr.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String address_val = dataSnapshot.getValue(String.class);
                curr_addr=new String(address_val);

                daily_usage.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {


                        String value = (String)dataSnapshot.child(curr_addr).child("usage").getValue();
                        float f=0;
                        try
                        {
                            f = Float.valueOf(value.trim()).floatValue();
                        }
                        catch (NumberFormatException nfe)
                        {
                            System.err.println("NumberFormatException: " + nfe.getMessage());
                        }

                        System.out.println("service: test");

                        if(f>limit)
                        {
                            //notify
                            System.out.println("service: usage exceeded");
                            postNotif("Usage Exceeded Limit");
                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void postNotif(String notifString) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create the NotificationChannel
            CharSequence name = new String("EBM");
            String description = new String("gives Usage Alert");
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel mChannel = new NotificationChannel("EBMID", name, importance);
            mChannel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = (NotificationManager) getSystemService(
                    NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(mChannel);
        }
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this,"EBMID");
        builder.setSmallIcon(R.drawable.logo);
//        Bitmap bitmap = BitmapFactory.decodeResource(mResources, R.drawable.notification_large_icon);
//        builder.setLargeIcon(bitmap);
        builder.setContentTitle("Notification Title");
        builder.setContentText(notifString);
        int notificationId = 001;
        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.notify(notificationId, builder.build());
//
//        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(this)
//                .setSmallIcon(R.drawable.logo)
//                .setContentTitle("My notification")
//                .setContentText("Much longer text that cannot fit one line...")
//                .setStyle(new NotificationCompat.BigTextStyle()
//                        .bigText("Much longer text that cannot fit one line..."))
//                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
    }

}
