package edu.ub.pis.giickos.ui.section.miscellaneous.statistics;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import androidx.gridlayout.widget.GridLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Map;

import edu.ub.pis.giickos.GiickosFragment;
import edu.ub.pis.giickos.R;
import edu.ub.pis.giickos.Utils;
import edu.ub.pis.giickos.ui.generic.form.FormStatistics;

// Displays the user's statistics. TODO
public class StatisticsFragment extends GiickosFragment {

    private ViewModel viewModel;

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

        viewModel = new ViewModelProvider(getActivity()).get(ViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_section_miscellaneous_statistics, container, false);
    }

    public void onViewCreated(View view, Bundle savedInstanceState)
    {
        addOnClickListeners(view);
        setupStats();
    }

    private void setupStats()
    {
        Map<ViewModel.STATISTIC, String> statValues = viewModel.getStatistics();
        GridLayout grid = getView().findViewById(R.id.grid_stats);

        grid.removeAllViews();

        for (ViewModel.STATISTIC stat : ViewModel.STATISTIC.values()) {
            FormStatistics tasksCreated = FormStatistics.newInstance(stat.iconDrawableResource, getString(stat.nameStringResource), statValues.containsKey(stat) ? statValues.get(stat) : getString(R.string.generic_label_notavailable));
            addChildFragment(tasksCreated, R.id.grid_stats, true);
        }
    }

    private void addOnClickListeners(View view)
    {
        TextView startDateText = (TextView) view.findViewById(R.id.statistics_start_range_date);
        TextView endDateText = (TextView) view.findViewById(R.id.statistics_end_range_date);
        Button todayButton = (Button) view.findViewById(R.id.statistics_today_button);

        // Update date button labels when the date changes,
        // and re-render stats
        viewModel.getStartDate().observe(getViewLifecycleOwner(), new Observer<LocalDate>() {
            @Override
            public void onChanged(LocalDate localDate) {
                startDateText.setText(localDate.format(DateTimeFormatter.ISO_DATE));
                setupStats();
            }
        });
        viewModel.getEndDate().observe(getViewLifecycleOwner(), new Observer<LocalDate>() {
            @Override
            public void onChanged(LocalDate localDate) {
                endDateText.setText(localDate.format(DateTimeFormatter.ISO_DATE));
                setupStats();
            }
        });

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

                                // Set the selected date in the Calendar instance
                                calendar.set(Calendar.HOUR_OF_DAY, 0);
                                calendar.set(Calendar.MINUTE, 0);
                                calendar.set(Calendar.SECOND, 0);
                                calendar.set(Calendar.MILLISECOND, 0);

                                viewModel.setStartDate(LocalDate.of(year, monthOfYear + 1, dayOfMonth));
                            }
                        },
                        Calendar.getInstance().get(Calendar.YEAR),
                        Calendar.getInstance().get(Calendar.MONTH),
                        Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();

                // Set the max date to the end date, start date can't be after end date
                datePickerDialog.getDatePicker().setMaxDate(Utils.localDateToUTC(viewModel.getEndDate().getValue()).toInstant().toEpochMilli());
            }
        });

        endDateText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog( getContext(),
                    new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                            // Create calendar and set picked date
                            Calendar calendar = Calendar.getInstance();
                            calendar.set(Calendar.YEAR, year);
                            calendar.set(Calendar.MONTH, monthOfYear);
                            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                            // Set the selected date in the Calendar instance
                            calendar.set(Calendar.HOUR_OF_DAY, 0);
                            calendar.set(Calendar.MINUTE, 0);
                            calendar.set(Calendar.SECOND, 0);
                            calendar.set(Calendar.MILLISECOND, 0);

                            viewModel.setEndDate(LocalDate.of(year, monthOfYear + 1, dayOfMonth));
                        }
                    },
                    Calendar.getInstance().get(Calendar.YEAR),
                    Calendar.getInstance().get(Calendar.MONTH),
                    Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
                );
                datePickerDialog.show();

                // Limit the date to the start date, end date can't be before start date
                datePickerDialog.getDatePicker().setMinDate(Utils.localDateToUTC(viewModel.getStartDate().getValue()).toInstant().toEpochMilli());
            }
        });

        // Listen for the "today" button being clicked
        todayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Set the start and end date to today
                viewModel.setToToday();
            }
        });
    }
}