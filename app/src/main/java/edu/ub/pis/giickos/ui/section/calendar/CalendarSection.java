package edu.ub.pis.giickos.ui.section.calendar;

import static com.kizitonwose.calendar.core.ExtensionsKt.firstDayOfWeekFromLocale;

import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Space;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.kizitonwose.calendar.core.Week;
import com.kizitonwose.calendar.core.WeekDay;
import com.kizitonwose.calendar.core.WeekDayPosition;
import com.kizitonwose.calendar.view.ViewContainer;
import com.kizitonwose.calendar.view.WeekCalendarView;
import com.kizitonwose.calendar.view.WeekDayBinder;
import com.kizitonwose.calendar.view.WeekHeaderFooterBinder;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Set;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.ViewModelHelpers;
import edu.ub.pis.giickos.ui.activities.main.MainViewModel;
import edu.ub.pis.giickos.ui.activities.taskcreator.TaskCreator;
import edu.ub.pis.giickos.ui.dialogs.Alert;
import edu.ub.pis.giickos.ui.section.Section;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

// Fragment for the calendar section.
public class CalendarSection extends Section {

    private ViewModel viewModel;
    private int timeframeHeight = 271;

    public CalendarSection() {
        // Required empty public constructor
    }

    public static CalendarSection newInstance() {
        CalendarSection fragment = new CalendarSection();
        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    private WeekCalendarView getCalendarView() {
        View view = getView();
        WeekCalendarView calendar = null;

        if (view != null) {
            calendar = view.findViewById(R.id.calendar);
        }

        return calendar;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_section_calendar, container, false);
    }

    // Should be called only once to create the background with time slots
    private void setupTimeFrames(View view) {
        LinearLayout list = view.findViewById(R.id.list_timeframes);
        LocalDate date = LocalDate.now();

        for (int i = 0; i < ViewModel.HOURS_IN_DAY; i++)
        {
            int hour = i; // Necessary to be accessible from anonymous listener class

            LocalDateTime time = date.atTime(i, 0, 0);

            LayoutInflater inflater = LayoutInflater.from(getContext());
            View timeFrame = inflater.inflate(R.layout.item_calendar_timeframe, list, false);
            TextView label = timeFrame.findViewById(R.id.label_time);
            label.setText(new SimpleDateFormat("HH:00", Locale.getDefault()).format(Date.from(time.atZone(ZoneId.systemDefault()).toInstant())));
            list.addView(timeFrame);

            ViewTreeObserver viewTreeObserver = timeFrame.getViewTreeObserver();
            if (viewTreeObserver.isAlive()) {
                viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        timeFrame.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                        timeframeHeight = timeFrame.getHeight();
                    }
                });
            }

            // Open task creator on click, with the date and time set
            timeFrame.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Check if there are any projects before trying to go to the task creator
                    if (viewModel.hasProjects()) {
                        LocalDate selectedDate = viewModel.getSelectedDate().getValue();
                        ViewModelHelpers.TaskDate date = new ViewModelHelpers.TaskDate(selectedDate.getDayOfMonth(), selectedDate.getMonthValue(), selectedDate.getYear());
                        ViewModelHelpers.TaskTime time = new ViewModelHelpers.TaskTime(hour, 0);

                        TaskCreator.openCreateActivity(getActivity(), date, time);
                    }
                    else {
                        // Prompt the user to create their first project
                        Alert alert = new Alert(getActivity(), getString(R.string.calendar_msg_noprojects_title), getString(R.string.calendar_msg_noprojects_body));

                        alert.setPositiveButton(R.string.generic_label_go, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                MainViewModel viewModel = new ViewModelProvider(getActivity()).get(MainViewModel.class);

                                viewModel.setCurrentSection(MainViewModel.SECTION_TYPE.TASK_EXPLORER);
                            }
                        });
                        alert.setNegativeButton("", null); // "Cancel"/"No" doesn't make sense in this dialog.

                        alert.show();
                    }
                }
            });
        }
    }

    // Should be called each time the tasks need to be rerendered
    private void setupTasks() {
        View view = getView();
        FrameLayout taskContainer = view.findViewById(R.id.container_tasks);
        LocalDate selectedDate = viewModel.getSelectedDate().getValue();
        Set<ViewModelHelpers.TaskData> tasks = viewModel.getTasks().getValue();

        if (selectedDate != null) {
            taskContainer.removeAllViews();
            for (ViewModelHelpers.TaskData task : tasks) {
                // TODO figure out how to handle these
                if (!task.takesAllDay) {
                    addTask(taskContainer, task);
                }
            }
        }
    }

    private void addTask(FrameLayout container, ViewModelHelpers.TaskData task) {
        View view = getView();
        LayoutInflater inflater = LayoutInflater.from(view.getContext());
        final float TIMEFRAME_HEIGHT = timeframeHeight;
        int timeFrameY = (int) ((TIMEFRAME_HEIGHT * task.startTime.getHour()));
        int minutes = task.startTime.getMinute();
        timeFrameY += (TIMEFRAME_HEIGHT)/60 * minutes;

        View taskView = inflater.inflate(R.layout.item_calendar_task, container, false);
        TextView nameLabel = taskView.findViewById(R.id.label_name);
        ImageView completedIcon = taskView.findViewById(R.id.image_completed);
        completedIcon.setVisibility(viewModel.isTaskCompleted(task.id) ? View.VISIBLE : View.INVISIBLE); // Show checkmark icon for completed tasks
        nameLabel.setText(task.name);
        container.addView(taskView);

        // Set position in the list
        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) taskView.getLayoutParams();
        FrameLayout.LayoutParams newLayout = new FrameLayout.LayoutParams(layoutParams.width, 10 * task.durationInMinutes);
        newLayout.setMargins(0, timeFrameY, 0, 0);
        taskView.setLayoutParams(newLayout);

        Space spaceWidget = taskView.findViewById(R.id.space);
        spaceWidget.setMinimumHeight((int) (TIMEFRAME_HEIGHT / 60 * task.durationInMinutes));

        CardView card = taskView.findViewById(R.id.card_main);
        card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Open the task creator for that task
                TaskCreator.openEditActivity(getActivity(), task.projectID, task.id, viewModel.getSelectedDate().getValue());
            }
        });

        // Set color of card based on task priority
        card.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(task.priority.colorResource)));
    }

    public void onResume() {
        super.onResume();

        // Re-render tasks when the activity is resumed, as their data might've changed
        setupTasks();
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstance) {
        WeekCalendarView calendar = getCalendarView();

        calendar.setDayBinder(new WeekDayBinder<ViewContainer>() {
            @NonNull
            @Override
            public ViewContainer create(@NonNull View view) {
                return new DayViewHolder(view);
            }
            @Override
            public void bind(@NonNull ViewContainer container, WeekDay weekDay) {
                View view = container.getView();
                TextView labelView = view.findViewById(R.id.label);
                ImageView backgroundImage = view.findViewById(R.id.image_background);
                LocalDate date = weekDay.getDate();

                labelView.setText(Integer.toString(weekDay.getDate().getDayOfMonth()));

                if (date.equals(viewModel.getSelectedDate().getValue())) {
                    backgroundImage.setImageResource(R.drawable.calendar_day_selected);
                }
                else {
                    backgroundImage.setImageResource(R.drawable.calendar_day);
                }

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LocalDate previousSelectedDate = viewModel.getSelectedDate().getValue();

                        // Update the previously selected view
                        if (previousSelectedDate != null) {
                            calendar.notifyDateChanged(previousSelectedDate);
                        }

                        viewModel.setSelectedDate(date);

                        // Update the new selected view
                        calendar.notifyDateChanged(date);
                    }
                });
            }
        });
        calendar.setWeekHeaderBinder(new WeekHeaderFooterBinder<ViewContainer>() {
            @NonNull
            @Override
            public ViewContainer create(@NonNull View view) {
                return new WeekHeaderHolder(view);
            }

            @Override
            public void bind(@NonNull ViewContainer container, Week week) {
                WeekHeaderHolder holder = (WeekHeaderHolder) container;

                holder.bind(getContext(), container, week);
            }
        });

        // Listen for the week changing to store the new week in the viewmodel.
        // The selected day is also changed to be the same week-day in the new week
        // (ex. if the selected day was monday, monday will be selected in the new week)
        calendar.setWeekScrollListener(new Function1<Week, Unit>() {
            @Override
            public Unit invoke(Week week) {
                List<WeekDay> weekDays = week.getDays();
                LocalDate selectedDate = viewModel.getSelectedDate().getValue();

                // Find the date within weekDays that corresponds to +/- 1 Week
                // We can't know from this event if we moved back of forwards in time
                LocalDate previousWeekDate = selectedDate.minusWeeks(1);
                LocalDate nextWeekDate = selectedDate.plusWeeks(1);
                for (int i = 0; i < weekDays.size(); ++i) {
                    WeekDay day = weekDays.get(i);

                    if (day.getDate().isEqual(previousWeekDate) || day.getDate().isEqual(nextWeekDate)) {
                        viewModel.setSelectedDate(day.getDate());
                        calendar.notifyCalendarChanged();
                        break;
                    }
                }

                viewModel.setCurrentWeekDate(weekDays.get(0).getDate());
                return null;
            }
        });

        LocalDate currentCalendarDate = viewModel.getCurrentWeekDate();
        calendar.setup(currentCalendarDate.minusWeeks(ViewModel.MAX_PAST_WEEKS), currentCalendarDate.plusWeeks(ViewModel.MAX_FUTURE_WEEKS), firstDayOfWeekFromLocale());
        WeekDay weekDay = new WeekDay(currentCalendarDate, WeekDayPosition.RangeDate);
        calendar.scrollToDay(weekDay);

        setupTimeFrames(view);
        setupTasks();

        // Listen for tasks being changed to rerender tasks
        viewModel.getTasks().observe(getViewLifecycleOwner(), new Observer<Set<ViewModelHelpers.TaskData>>() {
            @Override
            public void onChanged(Set<ViewModelHelpers.TaskData> taskData) {
                setupTasks();
            }
        });
    }

    @Override
    public MainViewModel.SECTION_TYPE getType() {
        return MainViewModel.SECTION_TYPE.CALENDAR;
    }
}