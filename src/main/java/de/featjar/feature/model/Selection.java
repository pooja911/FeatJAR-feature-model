package de.featjar.feature.model;

/** The {@code Selection} enum represents the possible states of a feature within the feature model.
 * It is used to denote whether a feature is:
 * 
 *   {@link #SELECTED} - The feature is selected or enabled.
 *   {@link #UNSELECTED} - The feature is deselected or disabled.
 *   {@link #UNDEFINED} - The feature has no defined selection state (neither selected nor unselected).
 *   
 * This enum is used across various components in the feature management system to track and manage
 * the selection state of features. The {@code toString} method is overridden to provide a string representation
 * of the enum constants.
 * 
 * 
 * 
 * Author: Pooja Garg
 *
 *  
**/

public enum Selection {
    SELECTED,
    UNSELECTED,
    UNDEFINED;

    @Override
    public String toString() {
        return super.toString();
    }
}
