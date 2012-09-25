package org.owasp.goatdroid.gui.emulator;

class EmulatorResult {

	private String message;

	public EmulatorResult(String message) {
		super();
		this.setMessage(message);
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}