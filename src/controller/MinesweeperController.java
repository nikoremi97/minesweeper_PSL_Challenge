package controller;

import com.minesweeper.model.Board;

import view.MinesweeperViewer;

/**
 * Game's controller class <br>
 */
public class MinesweeperController {
	// -----------------------------------------------------------------
	// Attributes
	// -----------------------------------------------------------------
	/**
	 * Board Model class of current minesweeper game
	 */
	private static Board board;
	/**
	 * Board View class of current minesweeper game
	 */
	private static MinesweeperViewer view;

	// -----------------------------------------------------------------
	// Builder
	// -----------------------------------------------------------------

	public static void main(String[] args) {

		// Creates and prints a view of game
		view = new MinesweeperViewer();
		view.showWelcome();
		while (true) {
			try {

				view.showInstructions();
				// Takes user input to construct the game's Board
				String boardDimension = view.createBoard();
				// A simple validation

				// creates a new Board according to parameters. Validation of these parameters
				// causes or not an BadBoardException
				boolean wasCreated = setUpBoard(boardDimension);
				if (wasCreated) {
					// prints new board on screen
					view.showBoard(board.toString());

					try {
						// while the game is going on, asks user to make a move
						while (board.getGameState().equals(Board.PLAYING)) {
							// ask user to make a move
							String userMove = view.makeMove();
							// validates data and makes that move
							if (makeMoveAction(userMove)) {
								System.out.println("move made");
								// print the current state of game's board
								view.showBoard(board.toString());
								// if user lost the game
								if (board.getGameState().equals(Board.LOST)) {
									view.youLost();
								}
								// if user won the game
								else if (board.getGameState().equals(Board.WON)) {
									view.congrats();
								}
							}
						}
					} catch (BadMoveException e) {
						e.toString();

					}
				}
			} catch (BadBoardException e) {
				e.toString();

			} catch (Exception e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Does the move that user wants to make
	 * 
	 * @param userMove
	 * @throws BadMoveException
	 */

	private static boolean makeMoveAction(String userMove) throws BadMoveException {
		// A simple validation
		boolean moveMade = false;
		if (userMove != null && userMove != "") {
			// To take user input parameters about coordinates x, y and move(Uncover or
			// Mark)

			String[] instructions = userMove.split(" ");
			if (instructions.length == 3) {

				int x = Integer.parseInt(instructions[0]);
				int y = Integer.parseInt(instructions[1]);
				String move = instructions[2] + "";
				System.out.println(move);
				// validation of move instruction
				if (move.equalsIgnoreCase("M") || move.equalsIgnoreCase("U")) {

					// sets board according to parameters
					board.playGame(x, y, move);
					moveMade = true;
				}
			}
		}
		return moveMade;
	}

	/**
	 * Creates a new board. <br>
	 * 
	 * @param boardDimension
	 * @throws BadBoardException
	 * 
	 */
	public static boolean setUpBoard(String boardDimension) throws BadBoardException, NumberFormatException {
		// creates a new Board according to parameters. Validation of these parameters
		// causes or not an BadBoardException
		boolean isCreated = false;
		if (boardDimension != null && boardDimension != "") {
			// To take user input parameters about height, width and mines number
			String[] dimensions = boardDimension.split(" ");
			if (dimensions.length == 3) {
				// takes values of array according to instructions
				int height = Integer.parseInt(dimensions[0]);
				int width = Integer.parseInt(dimensions[1]);
				int mines = Integer.parseInt(dimensions[2]);

				board = new Board(height, width, mines);
				isCreated = true;
			}
		}
		return isCreated;
	}

}
