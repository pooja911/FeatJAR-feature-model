package de.featjar.feature.model;

/**
 * FeatureNotFoundException is a custom exception that is thrown when a feature is not found within a feature model.
 * 
 * Example usage:
 *     throw new FeatureNotFoundException("Feature not found");

 *  @author: Pooja Garg
 */

public class FeatureNotFoundException extends RuntimeException {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4112750233088590678L;

	public FeatureNotFoundException(String message) {
        super(message);
    }
}
