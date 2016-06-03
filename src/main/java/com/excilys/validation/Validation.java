package com.excilys.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * Represent a message list error and a state (visible, hidden) for a DTO validation.
 * @author pqwarlot
 *
 */
public class Validation {
	private boolean stateDisplayed;
	private List<String> messages = new ArrayList<>();

	public Validation() {
	}

	public Validation(boolean displayError) {
		this.stateDisplayed = displayError;
	}

	public void displayError() {
		stateDisplayed = true;
	}

	public void hideError() {
		stateDisplayed = false;
	}

	public void addMessages(String msg) {
		messages.add(msg);
	}

	public boolean isStateDisplayed() {
		return stateDisplayed;
	}

	public List<String> getMessages() {
		return messages;
	}	
}
