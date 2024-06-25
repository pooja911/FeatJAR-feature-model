package de.featjar.feature.model;

public enum SelectionType {
    MANUAL,
    AUTOMATIC,
    SELECTED(), UNSELECTED(), UNDEFINED();
	
	@Override
	public String toString() {
		return super.toString();
	}
}
