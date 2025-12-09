/**
 * 
 */
package br.com.wss.exception;

import org.springframework.http.HttpStatus;
import org.springframework.lang.Nullable;
import org.springframework.web.server.ResponseStatusException;

/**
 * @author Willians Silva Santos
 *
 */
public class BusinessException extends ResponseStatusException {

	private static final long serialVersionUID = 7889581595552619019L;

	public BusinessException(HttpStatus status) {
		super(status);
	}

	/**
	 * Constructor with a response status and a reason to add to the exception
	 * message as explanation.
	 * 
	 * @param status the HTTP status (required)
	 * @param reason the associated reason (optional)
	 */
	public BusinessException(HttpStatus status, @Nullable String reason) {
		super(status, reason, new Throwable(reason));
	}

}
