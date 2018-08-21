package view;

import java.util.Scanner;

/**
 * Game board's view class <br>
 */
public class MinesweeperViewer {

	// -----------------------------------------------------------------
	// Attributes
	// -----------------------------------------------------------------
	/**
	 * Scanner to read user input text
	 */
	private Scanner scanner;

	// -----------------------------------------------------------------
	// Builder
	// -----------------------------------------------------------------
	/**
	 * Builder of class. <br>
	 */
	public MinesweeperViewer() {
		this.scanner = new Scanner(System.in);
		
	}

	// -----------------------------------------------------------------
	// methods
	// -----------------------------------------------------------------
	/**
	 * Gets user's input text to create a new Board
	 * 
	 * @return createdBorad
	 */
	public String createBoard() {
		String createdBoard = this.scanner.nextLine();
		return createdBoard;
	}

	/**
	 * Gets user's input text to make a new move
	 * 
	 * @return moveMaked
	 */
	public String makeMove() {
		System.out.println("Make your move:");
		String moveMaked = this.scanner.nextLine();
		return moveMaked;

	}

	/**
	 * Shows actual game's board to user
	 * 
	 * @param displayedBoard
	 */
	public void showBoard(String displayedBoard) {
		System.out.println("" + displayedBoard);
	}

	/**
	 * A congratulations message for successfully finish the game
	 */
	public void congrats() {
		System.out.println("Nice work. You found all mines in Board! :)");
	}
	/**
	 * A message for  finish the game without success
	 */
	public void youLost() {
		System.out.println("You've blown up because of the explosion of this mine! Better luck next time :(");
	}

	/**
	 * Show welcome message of game to user
	 */
	public void showWelcome() {
		// A classic welcome phrase from video games. LOL.
		String separator = "--------------------------------------";
		String welcomeStranger = "| Welcome, stranger! I've prepared this Minesweeper Game for you.             |";
		String madeBy = "| This game is brought to you by nikoremi97 on Github. I hope you enjoy it :) |";
		System.out.println(separator + "\n" + welcomeStranger + "\n" + madeBy + "\n" + separator);
	}

	/**
	 * Show instructions of game to user
	 */
	public void showInstructions() {

		String separator = "--------------------------------------";
		String boardInstructions = "| To start to play this game you must type Height Widht Mines in positive numbers only";
		String moveInstructions = "| To make a move you must type Row Column Move in positive numbers and U (uncover) or M (mark) only. "
				+ "\n"+"The way Row and Column is selected is up to down. You can not mark an uncovered cell";
		System.out.println(separator + "\n" + boardInstructions + "\n" + moveInstructions);

	}

}
