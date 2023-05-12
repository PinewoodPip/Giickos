package edu.ub.pis.giickos.model.statistics;

public class Statistic {

    private String id;
    private double value;

    public Statistic(String id, double value) {
        this.id = id;
        this.value = value;
    }

    public String getId() {
        return id;
    }

    public double getValue() {
        return value;
    }
}
