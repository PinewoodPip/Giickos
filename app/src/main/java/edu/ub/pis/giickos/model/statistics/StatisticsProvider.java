package edu.ub.pis.giickos.model.statistics;

import java.time.LocalDate;
import java.util.Set;

public interface StatisticsProvider {
    Set<Statistic> getStatistics(LocalDate startDate, LocalDate endDate);
}
