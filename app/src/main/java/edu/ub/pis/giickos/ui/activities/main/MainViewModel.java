package edu.ub.pis.giickos.ui.activities.main;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.model.ModelHolder;
import edu.ub.pis.giickos.model.user.UserManager;

import com.google.android.gms.tasks.Task;

public class MainViewModel extends ViewModel {

    public enum SECTION_TYPE {
        TASK_EXPLORER(R.drawable.projects, R.drawable.projects_colored, R.string.taskexplorer_name, R.string.taskexplorer_help),
        MISCELLANEOUS(R.drawable.profile, R.drawable.profile_colored, R.string.miscellaneous_name, R.string.miscellaneous_help),
        CALENDAR(R.drawable.calendar, R.drawable.calendar_colored, R.string.calendar_name, R.string.calendar_help),
        TIMER(R.drawable.timer, R.drawable.timer_colored, R.string.timer_name, R.string.timer_help),
        BAMBOO(R.drawable.bamboo, R.drawable.bamboo_colored, R.string.bamboo_name, R.string.bamboo_help),
        ;

        private final int iconResource;
        private final int selectedIconResource;
        private final int nameStringResource;
        private final int helpStringResource;

        SECTION_TYPE(int iconResource, int selectedIconResource, int nameStringResource, int helpStringResource) {
            this.iconResource = iconResource;
            this.selectedIconResource = selectedIconResource;
            this.nameStringResource = nameStringResource;
            this.helpStringResource = helpStringResource;
        }

        public int getIconResource() {
            return iconResource;
        }

        public int getSelectedIconResource() {
            return selectedIconResource;
        }

        public int getNameStringResource() {
            return nameStringResource;
        }

        public int getHelpStringResource() {
            return helpStringResource;
        }
    }

    private UserManager userManager;

    private MutableLiveData<SECTION_TYPE> currentSection;
    private boolean isPoppingStack = false;

    public MainViewModel() {
        userManager = ModelHolder.INSTANCE.getUserManager();

        currentSection = new MutableLiveData<>(SECTION_TYPE.CALENDAR);
    }

    public LiveData<SECTION_TYPE> getCurrentSection() {
        return currentSection;
    }

    public void setCurrentSection(SECTION_TYPE newSection) {
        if (newSection != currentSection.getValue()) {
            currentSection.setValue(newSection);
        }
    }

    public boolean isLoggedIn() {
        return userManager.isLoggedIn();
    }

    public boolean isPoppingStack() {
        return isPoppingStack;
    }

    public void setPoppingStack(boolean poppingStack) {
        isPoppingStack = poppingStack;
    }
}