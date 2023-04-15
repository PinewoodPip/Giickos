package edu.ub.pis.giickos.ui.section.calendar;

import static com.kizitonwose.calendar.core.ExtensionsKt.firstDayOfWeekFromLocale;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.kizitonwose.calendar.core.Week;
import com.kizitonwose.calendar.core.WeekDay;
import com.kizitonwose.calendar.core.WeekDayPosition;
import com.kizitonwose.calendar.view.ViewContainer;
import com.kizitonwose.calendar.view.WeekCalendarView;
import com.kizitonwose.calendar.view.WeekDayBinder;
import com.kizitonwose.calendar.view.WeekHeaderFooterBinder;

import java.time.LocalDate;
import java.util.Locale;

import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.main.MainViewModel;
import edu.ub.pis.giickos.ui.section.Section;

// Fragment for the calendar section.
public class CalendarSection extends Section {

    private ViewModel viewModel;

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
        for (int i = 0; i < ViewModel.HOURS_IN_DAY; i++)
        {
            LayoutInflater inflater = LayoutInflater.from(getContext());
            View timeFrame = inflater.inflate(R.layout.item_calendar_timeframe, list, false);
            TextView label = timeFrame.findViewById(R.id.label_time);
            label.setText(String.format(Locale.getDefault(), "%s:00", Integer.toString(i))); // TODO use locale for displaying
            list.addView(timeFrame);
        }
    }

    // Should be called each time the tasks need to be rerendered
    private void setupTasks() {
        View view = getView();
        FrameLayout taskContainer = view.findViewById(R.id.container_tasks);
        LocalDate selectedDate = viewModel.getSelectedDate().getValue();

        if (selectedDate != null) {
            taskContainer.removeAllViews();
            for (int i = 0; i < 24; ++i)
            {
                LayoutInflater inflater = LayoutInflater.from(view.getContext());
                int timeFrameHeight = 190 * i;

                View task = inflater.inflate(R.layout.item_calendar_task, taskContainer, false);
                taskContainer.addView(task);
                FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) task.getLayoutParams();
                layoutParams.setMargins(0, timeFrameHeight, 0, 0);
                task.setLayoutParams(layoutParams);
            }
        }
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

        LocalDate currentCalendarDate = viewModel.getCurrentWeekDate();
        calendar.setup(currentCalendarDate.minusWeeks(ViewModel.MAX_PAST_WEEKS), currentCalendarDate.plusWeeks(ViewModel.MAX_FUTURE_WEEKS), firstDayOfWeekFromLocale());
        WeekDay weekDay = new WeekDay(currentCalendarDate, WeekDayPosition.RangeDate);
        calendar.scrollToDay(weekDay);

        setupTimeFrames(view);
        setupTasks();

        // Listen for selected date being changed to rerender tasks
        viewModel.getSelectedDate().observe(getViewLifecycleOwner(), new Observer<LocalDate>() {
            @Override
            public void onChanged(LocalDate localDate) {
                setupTasks();
            }
        });
    }

    @Override
    public MainViewModel.TYPE getType() {
        return MainViewModel.TYPE.CALENDAR;
    }
}