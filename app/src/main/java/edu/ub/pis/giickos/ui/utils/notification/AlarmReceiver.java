package edu.ub.pis.giickos.ui.utils.notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import edu.ub.pis.giickos.ui.activities.main.MainActivity;

public class AlarmReceiver extends BroadcastReceiver{
    @Override
    public void onReceive(Context context, Intent intent) {
        // Handle the notification logic here
        String title = intent.getStringExtra("title");
        String description = intent.getStringExtra("content");
        String className = intent.getStringExtra("class");
        Class<?> targetClass = MainActivity.class;
        if (className != null && !className.isEmpty()) {
            try {
                targetClass = Class.forName(className);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }

        Notification.sendNotification(context, title, description, targetClass);
    }
}
