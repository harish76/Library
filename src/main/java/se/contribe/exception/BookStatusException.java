/**
 * 
 */
package se.contribe.exception;

import se.contribe.Status;

/**
 * @author harish
 *
 */
public class BookStatusException extends RuntimeException {

	private static final long serialVersionUID = -7536756117810504525L;
	public BookStatusException (Status status) {
		super(status.getStatus().toString());
	}
}
