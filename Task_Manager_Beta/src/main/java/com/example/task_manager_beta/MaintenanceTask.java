package com.example.task_manager_beta;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;

public class MaintenanceTask {
    private final WorkTypes workType;
    private final double hoursRequired;
    private final SimpleBooleanProperty setUpRequired = new SimpleBooleanProperty();
    private final SimpleBooleanProperty cleanUpRequired = new SimpleBooleanProperty();
    private final SimpleStringProperty dateTimeCompleted = new SimpleStringProperty();

    public MaintenanceTask(WorkTypes workType, double hoursRequired, boolean setUpRequired, boolean cleanUpRequired) {
        this.workType = workType;
        this.hoursRequired = hoursRequired;
        this.setUpRequired.set(setUpRequired);
        this.cleanUpRequired.set(cleanUpRequired);
        this.dateTimeCompleted.set("");
    }

    public WorkTypes getWorkType() { return workType; }
    public double getHoursRequired() { return hoursRequired; }
    public SimpleBooleanProperty setUpRequiredProperty(){return setUpRequired;}
    public SimpleBooleanProperty cleanUpRequiredProperty(){return cleanUpRequired;}
    public SimpleStringProperty dateTimeCompletedProperty(){return dateTimeCompleted;}
}
