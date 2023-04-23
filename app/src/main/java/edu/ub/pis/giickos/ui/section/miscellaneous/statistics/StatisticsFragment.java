package edu.ub.pis.giickos.ui.section.miscellaneous.statistics;

import android.app.DatePickerDialog;
import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import edu.ub.pis.giickos.GiickosFragment;
import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.ui.generic.form.FormStatistics;

// Displays the user's statistics. TODO
public class StatisticsFragment extends GiickosFragment {

    Calendar startDate = Calendar.getInstance();
    Calendar endDate = Calendar.getInstance();
    Calendar picker = Calendar.getInstance();;
    public StatisticsFragment() {
        // Required empty public constructor
    }

    public static StatisticsFragment newInstance() {
        StatisticsFragment fragment = new StatisticsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_section_miscellaneous_statistics, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        //TODO change value to a livedata array of strings
        addOnClickListeners(view);
        addFragments();


    }
    private void addFragments()
    {
        //Create the fragments
        FormStatistics tasksCreated = FormStatistics.newInstance(R.drawable.projects, "Tasks created:", "0");
        FormStatistics bambooPlanted = FormStatistics.newInstance(R.drawable.bamboo, "Bamboo planted:", "0");
        FormStatistics pomodoroTime = FormStatistics.newInstance(R.drawable.timer, "Pomodoro time:", "0");

        FormStatistics tasksCompleted = FormStatistics.newInstance(R.drawable.projects, "Tasks completed:", "0");
        FormStatistics tasksCollected = FormStatistics.newInstance(R.drawable.bamboo, "Bamboo collected:", "0");
        FormStatistics screenDetoxTime = FormStatistics.newInstance(R.drawable.timer, "Screen detox time:", "0");

        //Add the fragments to the layout
        addChildFragment(tasksCreated, R.id.First_Line);
        addChildFragment(bambooPlanted, R.id.First_Line);
        addChildFragment(pomodoroTime, R.id.First_Line);

        addChildFragment(tasksCompleted, R.id.Second_Line);
        addChildFragment(tasksCollected, R.id.Second_Line);
        addChildFragment(screenDetoxTime, R.id.Second_Line);
    }

    private void addOnClickListeners(View view)
    {
        //Date things
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        //Add the listeners
        TextView startDateText = (TextView) view.findViewById(R.id.statistics_start_range_date);
        TextView endDateText = (TextView) view.findViewById(R.id.statistics_end_range_date);
        Button todayButton = (Button) view.findViewById(R.id.statistics_today_button);

        //In default, we start and end in the same day
        startDateText.setText(sdf.format(startDate.getTime()));
        endDateText.setText(sdf.format(startDate.getTime()));
        //Not pretty btw
        startDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                //Create calendar and set picked date
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, monthOfYear);
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                // Get the current time in milliseconds
                                long currentTimeMillis = System.currentTimeMillis();

                                // Set the selected date in the Calendar instance
                                calendar.set(Calendar.HOUR_OF_DAY, 0);
                                calendar.set(Calendar.MINUTE, 0);
                                calendar.set(Calendar.SECOND, 0);
                                calendar.set(Calendar.MILLISECOND, 0);

                                // Calculate the selected date and time in milliseconds from epoch
                                long selectedTimeMillis = calendar.getTimeInMillis() + currentTimeMillis % 86400000;

                                startDate.setTimeInMillis(selectedTimeMillis);
                                startDateText.setText(sdf.format(startDate.getTime()));
                            }
                        },
                        Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

                );
                datePickerDialog.show();
                //Set the max date to the end date, start date can't be after end date
                datePickerDialog.getDatePicker().setMaxDate(endDate.getTimeInMillis());
            }
        });

        endDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(
                        getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                //Create calendar and set picked date
                                Calendar calendar = Calendar.getInstance();
                                calendar.set(Calendar.YEAR, year);
                                calendar.set(Calendar.MONTH, monthOfYear);
                                calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                                // Get the current time in milliseconds
                                long currentTimeMillis = System.currentTimeMillis();

                                // Set the selected date in the Calendar instance
                                calendar.set(Calendar.HOUR_OF_DAY, 0);
                                calendar.set(Calendar.MINUTE, 0);
                                calendar.set(Calendar.SECOND, 0);
                                calendar.set(Calendar.MILLISECOND, 0);

                                // Calculate the selected date and time in milliseconds from epoch
                                long selectedTimeMillis = calendar.getTimeInMillis() + currentTimeMillis % 86400000;

                                endDate.setTimeInMillis(selectedTimeMillis);
                                endDateText.setText(sdf.format(endDate.getTime()));

                            }
                        },
                        Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)

                );
                datePickerDialog.show();
                //Limit the date to the start date, end date can't be before start date
                datePickerDialog.getDatePicker().setMinDate(startDate.getTimeInMillis());
            }
        });
        todayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Set the start and end date to today
                startDate.setTimeInMillis(System.currentTimeMillis());
                endDate.setTimeInMillis(System.currentTimeMillis());
                startDateText.setText(sdf.format(startDate.getTime()));
                endDateText.setText(sdf.format(endDate.getTime()));
            }
        });



    }

}