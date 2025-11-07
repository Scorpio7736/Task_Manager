package com.example.task_manager_beta;

public enum WorkTypes
{
    TNutReplacement("T-Nut Replacement"),
    HoldReplacement("Hold Replacement"),
    HoldTightening("Hold Tightening"),
    BoltReplacement("Bolt Replacement"),
    TNutThreading("T-Nut Threading"),
    RopeReplacement("Rope Replacement"),
    Other("Other"),
    None("None");

    private final String displayName;

    WorkTypes(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

}
