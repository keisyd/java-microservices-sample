package com.example.domain;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.joda.money.Money;

import lombok.NonNull;

/**
 * A of Walllett activities.
 */
public class Statement {

    /**
     * The list of Walllett activities within this .
     */
    private List<Operation> activities;

    public Statement(@NonNull List<Operation> activities) {
        this.activities = activities;
    }

    public Statement(@NonNull Operation... activities) {
        this.activities = new ArrayList<>(Arrays.asList(activities));
    }

    public List<Operation> getActivities() {
        return Collections.unmodifiableList(this.activities);
    }

    public void addOperation(Operation Statement) {
        this.activities.add(Statement);
    }
}
