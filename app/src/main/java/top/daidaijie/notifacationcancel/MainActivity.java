package top.daidaijie.notifacationcancel;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private CancelBroadCast cancelBroadCast;
    private LocalBroadcastManager localBroadcastManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        createChannel();
        findViewById(R.id.btn_show).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showNotification();
            }
        });
        cancelBroadCast = new CancelBroadCast();
        cancelBroadCast.setOnCancelListener(new CancelBroadCast.OnCancelListener() {
            @Override
            public void onCancel() {
                Toast.makeText(MainActivity.this, "cancel a notification", Toast.LENGTH_SHORT).show();
            }
        });
        localBroadcastManager = LocalBroadcastManager.getInstance(this);
        localBroadcastManager.registerReceiver(cancelBroadCast, CancelBroadCast.newIntentFilter());
    }

    @Override
    protected void onDestroy() {
        localBroadcastManager.unregisterReceiver(cancelBroadCast);
        super.onDestroy();
    }

    private void showNotification() {
        PendingIntent pi = PendingIntent.getBroadcast(this.getApplicationContext(), 0,
                CancelBroadCast.newIntent(), 0);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this.getApplicationContext(),
                CHANNEL_ID)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setDeleteIntent(pi)
                .setContentTitle("Title")
                .setContentText("Hello World");
        NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(0, builder.build());
    }

    private void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_ID,
                    NotificationManager.IMPORTANCE_DEFAULT);

            NotificationManager nm = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            nm.createNotificationChannel(channel);
        }
    }

    private static String CHANNEL_ID = "app_channel";
}
