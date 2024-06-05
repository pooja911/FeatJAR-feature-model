package de.featjar.feature.model;

public class FeatureNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4112750233088590678L;

	public FeatureNotFoundException(String message) {
        super(message);
    }
}
