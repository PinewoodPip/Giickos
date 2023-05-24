package edu.ub.pis.giickos.ui.utils.notification;

import android.Manifest;
import android.app.AlarmManager;
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

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

import javax.annotation.Nullable;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.activities.main.MainActivity;


public class Notification {

    private static final String CHANNEL_ID = "Giickos_channel_id"; //ha de ser únic
    private static int current_id_nt = 0;
    private static int current_alarm_id = 0;

    public static int pomodoroNotification(Context context, String textTitle, String textContent,
                                           Class actToStart, int timeLater){

        Calendar calendar = getCurrentTime();
        calendar.add(Calendar.MINUTE, timeLater);

        return createAlarm(context, textTitle, textContent, actToStart, calendar);

    }
    //month january = 1
    public static int scheduledNotification(Context context, String textTitle, String textContent,
                                                    Class actToStart, int year, int month, int day, int hour,
                                            int minute){


        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.MONTH, month -1);
        calendar.set(Calendar.DAY_OF_MONTH, day);
        calendar.set(Calendar.HOUR_OF_DAY, hour);
        calendar.set(Calendar.MINUTE, minute);
        calendar.set(Calendar.SECOND, 0);

        return createAlarm(context, textTitle, textContent, actToStart, calendar);

    }
    public static int sendNotification(Context context, String textTitile, String textContent,
                                       Class actToStart){
        NotificationCompat.Builder nt = createNotification(context, textTitile, textContent);
        nt = setTapActionExclusive(nt, context, actToStart);

        return showNotification(nt, context);

    }
    public static Calendar getCurrentTime(){
        // Get the current time in milliseconds
        long currentTimeMillis = System.currentTimeMillis();

        // Create a Calendar instance and set it to the current time
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(currentTimeMillis);

        /*
        // Extract the current time components
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) +1; //January is 0
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);
        int second = calendar.get(Calendar.SECOND);*/

        return calendar;
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
    private static int createAlarm(Context context, String textTitle, String textContent,
                                   Class actToStart, Calendar calendar){
        AlarmManager alarmManager = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE);

        // Create an intent to the AlarmReceiver class
        Intent intent = new Intent(context, AlarmReceiver.class);

        intent.putExtra("title", textTitle);
        intent.putExtra("content", textContent);

        intent.putExtra("class", actToStart.getName());

        // Set a unique ID for this alarm

        int alarm_id = generateID(calendar);


        // Create a PendingIntent to be triggered when the alarm fires
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, alarm_id, intent, PendingIntent.FLAG_UPDATE_CURRENT);

        // Set the alarm to trigger at the specified time
        alarmManager.setExact(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), pendingIntent);
        Log.i("Notification", "Alarm: "+alarm_id+ " registred");
        return alarm_id;
    }
    private static int generateID(@Nullable Calendar calendar){
        int id = 0;
        if(calendar != null){
            int month = calendar.get(Calendar.MONTH) +1; //January is 0
            int day = calendar.get(Calendar.DAY_OF_MONTH);
            int hour = calendar.get(Calendar.HOUR_OF_DAY);
            int minute = calendar.get(Calendar.MINUTE);
            int second = calendar.get(Calendar.SECOND);
            id = Integer.parseInt(month+""+day+hour+minute+second);
        }else{
            Calendar current_time = getCurrentTime();
            int month = current_time.get(Calendar.MONTH) +1; //January is 0
            int day = current_time.get(Calendar.DAY_OF_MONTH);
            int hour = current_time.get(Calendar.HOUR_OF_DAY);
            int minute = current_time.get(Calendar.MINUTE);
            int second = current_time.get(Calendar.SECOND);
            id = Integer.parseInt(month+""+day+hour+minute+second);
        }
        return id;
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
                                                                    Context context, Class actToStart) {
        Intent intent = new Intent(context, actToStart);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_IMMUTABLE);
        builder.setContentIntent(pendingIntent);
        //TODO starting the activity should create a back stack so that the
        // user's expectations for the Back and Up buttons is preserved.
        Log.i("Notification", "tap action added");
        return builder;
    }


    private static int showNotification(NotificationCompat.Builder builder,
                                        Context context) {
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
            return 0;
        }

        int notificationId = generateID(null);
        notificationManager.notify(notificationId, builder.build());
        Log.i("Notification", "notification id: "+notificationId+" sent");
        return notificationId;
    }

}
