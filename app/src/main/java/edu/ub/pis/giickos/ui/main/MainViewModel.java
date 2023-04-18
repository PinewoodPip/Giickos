package edu.ub.pis.giickos.ui.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import edu.ub.pis.giickos.R;

public class MainViewModel extends ViewModel {

    public enum TYPE {
        TASK_EXPLORER(R.drawable.projects, R.drawable.projects_colored),
        MISCELLANEOUS(R.drawable.profile, R.drawable.profile_colored),
        CALENDAR(R.drawable.calendar, R.drawable.calendar_colored),

        TIMER(R.drawable.timer, R.drawable.timer_colored),
        ;

        private final int iconResource;
        private final int selectedIconResource;

        TYPE(int iconResource, int selectedIconResource) {
            this.iconResource = iconResource;
            this.selectedIconResource = selectedIconResource;
        }

        public int getIconResource() {
            return iconResource;
        }

        public int getSelectedIconResource() {
            return selectedIconResource;
        }
    }

    private MutableLiveData<TYPE> currentSection;

    public MainViewModel() {
        currentSection = new MutableLiveData<>(TYPE.CALENDAR);
    }

    public LiveData<TYPE> getCurrentSection() {
        return currentSection;
    }

    public void setCurrentSection(TYPE newSection) {
        currentSection.setValue(newSection);
    }
}