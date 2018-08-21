package controller;

public class BadMoveException extends Exception {
	// -----------------------------------------------------------------
	// Constructor
	// -----------------------------------------------------------------

	/**
	 * Builds a Exception each time an user tries to violate the format of game's
	 * move
	 */
	public BadMoveException() {
		System.out.println(
				"To make a move you must type Row Column Move in positive numbers and U (uncover) or M (mark) only. You can not mark an uncovered cell. ");
	}
}
