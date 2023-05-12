package edu.ub.pis.giickos.model.statistics;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class StatisticsManager {

    private Set<StatisticsProvider> providers;

    public StatisticsManager(Set<StatisticsProvider> providers) {
        this.init();

        for (StatisticsProvider provider : providers) {
            this.registerProvider(provider);
        }
    }

    public StatisticsManager() {
        this.init();
    }

    public void registerProvider(StatisticsProvider provider) {
        this.providers.add(provider);
    }

    public Set<Statistic> getStatistics(LocalDate startDate, LocalDate endDate) {
        Set<Statistic> stats = new HashSet<>();

        for (StatisticsProvider provider : providers) {
            stats.addAll(provider.getStatistics(startDate, endDate));
        }

        return stats;
    }

    private void init() {
        this.providers = new HashSet<>();
    }
}
