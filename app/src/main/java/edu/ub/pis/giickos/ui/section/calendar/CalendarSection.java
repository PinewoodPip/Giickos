package edu.ub.pis.giickos.ui.section.calendar;

import static com.kizitonwose.calendar.core.ExtensionsKt.firstDayOfWeekFromLocale;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProvider;

import com.kizitonwose.calendar.core.Week;
import com.kizitonwose.calendar.core.WeekDay;
import com.kizitonwose.calendar.core.WeekDayPosition;
import com.kizitonwose.calendar.view.ViewContainer;
import com.kizitonwose.calendar.view.WeekCalendarView;
import com.kizitonwose.calendar.view.WeekDayBinder;
import com.kizitonwose.calendar.view.WeekHeaderFooterBinder;

import java.time.LocalDate;

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

        viewModel = new ViewModelProvider(this).get(ViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_section_calendar, container, false);
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

                if (date.equals(viewModel.getSelectedDate())) {
                    backgroundImage.setImageResource(R.drawable.calendar_day_selected);
                }
                else {
                    backgroundImage.setImageResource(R.drawable.calendar_day);
                }

                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LocalDate previousSelectedDate = viewModel.getSelectedDate();

                        if (previousSelectedDate != null) {

                            calendar.notifyDateChanged(previousSelectedDate);
                        }

                        viewModel.setSelectedDate(date);
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
    }

    @Override
    public MainViewModel.TYPE getType() {
        return MainViewModel.TYPE.CALENDAR;
    }
}