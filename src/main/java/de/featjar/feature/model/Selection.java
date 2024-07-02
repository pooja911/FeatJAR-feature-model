package de.featjar.feature.model;

/** The  enum represents the possible states of a feature within the feature model.
 * It is used to denote whether a feature is:
 * 
 *   {#SELECTED} - The feature is selected or enabled.
 *   {#UNSELECTED} - The feature is deselected or disabled.
 *   {#UNDEFINED} - The feature has no defined selection state (neither selected nor unselected).
 *   
 * This enum is used across various components in the feature management system to track and manage
 * the selection state of features. The  method is overridden to provide a string representation
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
