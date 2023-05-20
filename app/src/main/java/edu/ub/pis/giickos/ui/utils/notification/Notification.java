package edu.ub.pis.giickos.ui.utils.notification;

import android.Manifest;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import edu.ub.pis.giickos.R;


public class Notification {

    private static final String CHANNEL_ID = "Giickos_channel_id"; //ha de ser únic
    private static int current_id_nt = 0;

    public static int sendNotification(AppCompatActivity context, String textTitile, String textContent,
                                        Class actToStart){
        NotificationCompat.Builder nt = createNotification(context, textTitile, textContent);
        nt = setTapActionExclusive(nt, context, actToStart);
        current_id_nt++;
        showNotification(nt, current_id_nt, context);
        return current_id_nt;
    }
    //codi per crear el channel per les notificacions
    //Context es superclasse de Activity
    //s'huaria de cridar abans possible
    public static void createNotificationChannel(Context context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = context.getString(R.string.channel_name);
            String description = context.getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_DEFAULT; //it's not guaranteed
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = context.getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
            Log.i("Notification", "channel "+CHANNEL_ID+" created");
        }
    }

    //crea el contingut de notificació
    //Context es superclasse de Activity
    private static NotificationCompat.Builder createNotification(Context context, String textTitle, String textContent) {
        NotificationCompat.Builder builder = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            builder = new NotificationCompat.Builder(context, CHANNEL_ID)
                    .setSmallIcon(R.drawable.notification)
                    .setContentTitle(textTitle)
                    .setContentText(textContent)
                    .setPriority(NotificationCompat.PRIORITY_DEFAULT);
            Log.i("Notification", "notification created");
        }
        //si versió de Android < Api 26, no cal channel_id per crear NotificationCompat.Builder

        return builder;
    }

    //afegir quan notificació es clicat activity que s'obrirá
    private static NotificationCompat.Builder setTapActionExclusive(NotificationCompat.Builder builder,
                                                                    AppCompatActivity context, Class actToStart) {
        Intent intent = new Intent(context, actToStart);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        builder.setContentIntent(pendingIntent);
        //TODO starting the activity should create a back stack so that the
        // user's expectations for the Back and Up buttons is preserved.
        Log.i("Notification", "tap action added");
        return builder;
    }


    private static void showNotification(NotificationCompat.Builder builder, int notificationId,
                                        AppCompatActivity context) {
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(context);

        // notificationId is a unique int for each notification that you must define

        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            Log.i("Notification","permission no granted");
            return;
        }
        notificationManager.notify(notificationId, builder.build());
        Log.i("Notification", "notification id: "+notificationId+" sent");
    }

}
