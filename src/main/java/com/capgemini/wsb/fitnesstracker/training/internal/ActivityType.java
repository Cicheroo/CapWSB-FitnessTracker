package com.capgemini.wsb.fitnesstracker.training.internal;

/**
 * Enum representing possible activity types of training.
 */
public enum ActivityType {

    RUNNING("Running"),
    CYCLING("Cycling"),
    WALKING("Walking"),
    SWIMMING("Swimming"),
    TENNIS("Tenis");

    private final String displayName;

    ActivityType(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Gets display name.
     *
     * @return display name
     */
    public String getDisplayName() {
        return displayName;
    }

}
