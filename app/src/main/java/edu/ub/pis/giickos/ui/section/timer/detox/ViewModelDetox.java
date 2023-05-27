package edu.ub.pis.giickos.ui.section.timer.detox;



import android.app.NotificationManager;
import android.content.Context;

import androidx.lifecycle.MutableLiveData;

public class ViewModelDetox extends androidx.lifecycle.ViewModel{
    public NotificationManager notificationManager;
    public MutableLiveData<Boolean> emergencyCallsSwitchIsChecked, noNotificationSwitchIsChecked, isDetoxCheckBoxCheked;

    public ViewModelDetox() {
        emergencyCallsSwitchIsChecked = new MutableLiveData<>(false);
        noNotificationSwitchIsChecked = new MutableLiveData<>(false);
        isDetoxCheckBoxCheked = new MutableLiveData<>(false);

    }

    // Aquest metode controla les notificacions, podem dir que depenent dels dos
    // switches (emergency call i notifications) habilitem les notificaicions d'una manera o d'un altre
    public void controlNotification(){
        if (isDetoxCheckBoxCheked.getValue()){
            if (emergencyCallsSwitchIsChecked.getValue() && noNotificationSwitchIsChecked.getValue()){
                notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE);
            }else if (emergencyCallsSwitchIsChecked.getValue() && !noNotificationSwitchIsChecked.getValue()){
                notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_NONE);
                NotificationManager.Policy policy = new NotificationManager.Policy(
                        NotificationManager.Policy.PRIORITY_CATEGORY_MESSAGES,
                        NotificationManager.Policy.PRIORITY_SENDERS_ANY,
                        NotificationManager.Policy.SUPPRESSED_EFFECT_SCREEN_ON |
                                NotificationManager.Policy.SUPPRESSED_EFFECT_SCREEN_OFF |
                                NotificationManager.Policy.SUPPRESSED_EFFECT_AMBIENT);

                notificationManager.setNotificationPolicy(policy);
            }else if (!emergencyCallsSwitchIsChecked.getValue() && noNotificationSwitchIsChecked.getValue()){
                notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_PRIORITY);
            }else if (!emergencyCallsSwitchIsChecked.getValue() && !noNotificationSwitchIsChecked.getValue()){
                notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL);
            }
        }else{
            notificationManager.setInterruptionFilter(NotificationManager.INTERRUPTION_FILTER_ALL);
        }

    }





}
