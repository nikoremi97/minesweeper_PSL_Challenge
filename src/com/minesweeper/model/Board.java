package com.minesweeper.model;

import controller.BadBoardException;
import controller.BadMoveException;

public class Board {

	/**
	 * Game's board class <br>
	 * <br>
	 * inv: <br>
	 * height != 0 <br>
	 * width != 0 <br>
	 * mines != 0 <br>
	 */

	// -----------------------------------------------------------------
	// Constants
	// -----------------------------------------------------------------

	/**
	 * Indicates if the game is won in the method playGame
	 */
	public final static String WON = "WON";
	/**
	 * Indicates if the game is lost in the method playGame
	 */
	public final static String LOST = "LOST";
	/**
	 * Indicates if the game is still going in the method playGame
	 */
	public final static String PLAYING = "PLAYING";

	/**
	 * Indicates if player marks a cell
	 */
	public final String MARKED = "M";
	/**
	 * Indicates if player uncovers a cell
	 */
	public final String UNCOVER = "U";

	// -----------------------------------------------------------------
	// Attributes
	// -----------------------------------------------------------------

	/**
	 * Board's height
	 */
	private int height;
	/**
	 * Board's width
	 */
	private int width;
	/**
	 * Total board's mines
	 */
	private int mines;
	/**
	 * Total board's uncovered cells
	 */
	private int uncoveredCells;
	/**
	 * Mines marked by user
	 */
	private int markedMines;

	/**
	 * Matrix of cells in board
	 */
	private Cell[][] cells;

	/**
	 * Actual board's game state based on constants.
	 */

	private String gameState;

	// -----------------------------------------------------------------
	// Builder
	// -----------------------------------------------------------------
	/**
	 * Builder of class. <br>
	 * 
	 * @param height
	 * @param width
	 * @param mines
	 * @throws BadBoardException
	 * 
	 */
	public Board(int height, int width, int mines) throws BadBoardException {
		super();
		if (height > 0 && width > 0 && mines > 0) {
			this.height = height;
			this.width = width;
			this.mines = mines;
			this.markedMines = 0;
			this.setUncoveredCells(0);
			String actualState = this.PLAYING;
			this.setGameState(actualState);
			// Calls initBoard to set cells' values in matrix
			this.initBoard();
		} else {
			// if parameters are not valid, tells player to please type a valid input
			throw new BadBoardException();
		}
	}

	// -----------------------------------------------------------------
	// methods
	// -----------------------------------------------------------------

	/**
	 * Initializes the board by creating a new Cell in each cell of matrix. Inserts
	 * the number of mines specified by the user. Finally, sets the number of mines
	 * around of each cell.
	 */

	public void initBoard() {

		// Create a matrix of the specified height and width
		this.cells = new Cell[this.height][this.width];

		// Fill the matrix with a new Cell in each cell.
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				this.cells[i][j] = new Cell();
			}
		}

		// Insert the number of mines specified by user in the matrix
		setMinesMatrix();

		// Set the number of mines around of each cell.
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// Calls getMinesAround(i,j) and set this value in the specific cell
				this.cells[i][j].setMinesAround(discoverMinesAround(i, j));
			}
		}

	}

	/**
	 * Inserts the number of mines specified by the user.
	 */
	public void setMinesMatrix() {
		// for a random location of mines
		int realNumberMines = 0;
		for (int i = 0; i < this.mines; i++) {

			// position of mine x,y in board
			int x, y;

			x = (int) (Math.random() * (this.width));
			y = (int) (Math.random() * (this.height));

			this.cells[x][y].setMine(true);
			if (this.cells[x][y].isMine()) {
				System.out.println("MINA IMPLANTADA");
				realNumberMines++;
			}

		}
		mines = realNumberMines;
	}

	/**
	 * Discovers the number of mines around of each cell
	 */

	public int discoverMinesAround(int x, int y) {
		int minesAround = 0;
		int ax;
		int ay;
		for (int i = -1; i < 2; i++) {
			for (int j = -1; j < 2; j++) {
				ay = y + i;
				ax = x + j;
				if (ay >= 0 && ay < this.height && ax >= 0 && ax < this.width) {
					// cambie los indices
					if (this.cells[ax][ay].isMine() == true) {
						minesAround++;
					}
				}

			}
		}
		return minesAround;

	}

	/**
	 * Does the move that user wants to make
	 * 
	 * @param x
	 * @param y
	 * @param move
	 * @throws BadBoardException
	 */

	public String playGame(int x, int y, String move) throws BadMoveException {

		// if player uncovers a mine, game is lost and finished
		if (x < height && y < width) {

			System.out.println(cells[x][y].getState() + "ESTADO " + cells[x][y].isMine() + "IS MINE "
					+ cells[x][y].getMinesAround() + "MINES AROUND");

			if ((cells[x][y].isMine() == true) && (move.equals(this.UNCOVER))) {
				cells[x][y].setState(Cell.MINE);
				this.finishGame();
				this.setGameState(LOST);
			}
			// if player uncovers a cell without a mine, game is still going on
			else if ((cells[x][y].isMine() == false) && (move.equals(this.UNCOVER))) {
				uncoveredCells++;
				this.uncoverCell(x, y);

				if (((height * width) - markedMines) == uncoveredCells) {
					this.finishGame();
					this.setGameState(WON);
				} else {
					this.setGameState(PLAYING);
				}
			}
			// if player marks cells that contains a mine, game is going on only if mines
			// remains on board. Mines number of boards -1
			else if ((cells[x][y].isMine() == true) && (move.equals(this.MARKED))) {
				this.markCell(x, y);
				markedMines++;
				if (((height * width) - markedMines) == uncoveredCells) {
					this.finishGame();
					this.setGameState(WON);
				} else {
					this.setGameState(PLAYING);
				}
			}
			// if player marks a cell that does not contains a mine, game is still going on
			// AND the mines numbers remains the same
			else if ((cells[x][y].isMine() == false) && cells[x][y].getState() != Cell.UNCOVER
					&& move.equals(this.MARKED)) {
				this.markCell(x, y);
				this.setGameState(PLAYING);
			}
		} else {
			throw new BadMoveException();
		}

		return this.getGameState();
	}

	/**
	 * Set the state of a cell as MARKED
	 * 
	 * @param i
	 *            cell's row
	 * @param j
	 *            cell's column
	 */
	public void markCell(int i, int j) {
		this.cells[i][j].setState(Cell.MARKED);
	}

	/**
	 * Set the state of a cell as
	 * 
	 * @param i
	 *            cell's row
	 * @param j
	 *            cell's column
	 */
	public void uncoverCell(int i, int j) {
		int minesNumber = cells[i][j].getMinesAround();
		// if a cell has a mine around, shows minesAround number
		if (minesNumber > 0) {
			this.cells[i][j].setState(Cell.UNCOVER);
			// else shows empty neighbors
		} else {
			discoverEmptiesAround(i, j);
		}
	}

	/**
	 * Find empty cells around a cell
	 */
	public void discoverEmptiesAround(int a, int b) {

		int i = a;
		int j = b;
		// hay 8 opciones fijas
		// a cada una se le pregunta si está UNSELECTED
		// validar indices i-- j-- >0
		// celda temporal para consultar las vacías al rededor
		// tiene minas al redeer? Si, UNCOVER. No, llamo método en celda temporal

		if ((i - 1) >= 0 && (j - 1) >= 0) {
			if ((cells[i][j].isMine() == false) && (cells[i][j].getState() == Cell.UNSELECTED)) {

				cells[i - 1][j - 1].setState(Cell.UNCOVER);

				if (cells[i - 1][j - 1].getMinesAround() == 0) {
					discoverEmptiesAround(i - 1, j - 1);
				}
			}
		}
		if ((i - 1) >= 0) {
			if ((cells[i][j].isMine() == false) && (cells[i][j].getState() == Cell.UNSELECTED)) {
				cells[i - 1][j].setState(Cell.UNCOVER);
				if (cells[i - 1][j].getMinesAround() == 0) {
					discoverEmptiesAround(i - 1, j);
				}
			}
		}
		if ((i - 1) >= 0 && (j + 1) < width) {
			if ((cells[i][j].isMine() == false) && (cells[i][j].getState() == Cell.UNSELECTED)) {
				cells[i - 1][j + 1].setState(Cell.UNCOVER);
				if (cells[i - 1][j + 1].getMinesAround() == 0) {
					discoverEmptiesAround(i - 1, j + 1);
				}
			}
		}
		if ((j - 1) >= 0) {
			if ((cells[i][j].isMine() == false) && (cells[i][j].getState() == Cell.UNSELECTED)) {
				cells[i][j - 1].setState(Cell.UNCOVER);
				if (cells[i][j - 1].getMinesAround() == 0) {
					discoverEmptiesAround(i, j - 1);
				}
			}
		}
		if ((j + 1) < width) {
			if ((cells[i][j].isMine() == false) && (cells[i][j].getState() == Cell.UNSELECTED)) {
				cells[i][j + 1].setState(Cell.UNCOVER);
				if (cells[i][j + 1].getMinesAround() == 0) {
					discoverEmptiesAround(i, j + 1);
				}
			}
		}

		if ((i + 1) < height && (j - 1) >= 0) {
			if ((cells[i][j].isMine() == false) && (cells[i][j].getState() == Cell.UNSELECTED)) {
				cells[i + 1][j - 1].setState(Cell.UNCOVER);
				if (cells[i + 1][j - 1].getMinesAround() == 0) {
					discoverEmptiesAround(i + 1, j - 1);
				}
			}
		}
		if ((i + 1) < height) {
			if ((cells[i][j].isMine() == false) && (cells[i][j].getState() == Cell.UNSELECTED)) {
				cells[i + 1][j].setState(Cell.UNCOVER);
				if (cells[i + 1][j].getMinesAround() == 0) {
					discoverEmptiesAround(i + 1, j);
				}
			}
		}
		if ((i + 1) < height && (j + 1) < width) {
			if ((cells[i][j].isMine() == false) && (cells[i][j].getState() == Cell.UNSELECTED)) {
				cells[i + 1][j + 1].setState(Cell.UNCOVER);
				if (cells[i + 1][j + 1].getMinesAround() == 0) {
					discoverEmptiesAround(i + 1, j + 1);
				}
			}
		}

	}

	/**
	 * Finishes game. All cells of board gets uncovered.
	 */

	public void finishGame() {

		// Uncovers all cells of board. If a cell is a mine, shows it. If it's not, show
		// the number of mines around it.
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				// if a mine was not marked, it shows it.
				if ((cells[i][j].isMine() == true) && (cells[i][j].getState() != Cell.MARKED)) {
					cells[i][j].setState(Cell.MINE);
				} else if (cells[i][j].getState() != Cell.MARKED) {
					// if a cell has a mine around it shows its. Else, shows it as DISABLE ("-")
					if (cells[i][j].getMinesAround() > 0) {
						cells[i][j].setState(Cell.UNCOVER);
					} else {
						cells[i][j].setState(Cell.DISABLE);
					}
				}

			}
		}
	}

	/**
	 * Returns board's height. <br>
	 * 
	 * @return height.
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * Returns board's width. <br>
	 * 
	 * @return width.
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * Returns board's mines. <br>
	 * 
	 * @return mines.
	 */
	public int getMines() {
		return mines;
	}

	/**
	 * Returns board's marked mines. <br>
	 * 
	 * @return markedMines.
	 */
	public int getMarkedMines() {
		return markedMines;
	}

	/**
	 * Sets board's marked mines. <br>
	 * 
	 * @param markedMines
	 *            new numer of marked mines.
	 */
	public void setMarkedMines(int markedMines) {
		this.markedMines = markedMines;
	}

	/**
	 * Returns board's matrix of cells. <br>
	 * 
	 * @return cells.
	 */
	public Cell[][] getCells() {
		return cells;
	}

	/**
	 * Sets board's cells. <br>
	 * 
	 * @param cells
	 *            new matrix of cells.
	 */
	public void setCells(Cell[][] cells) {
		this.cells = cells;
	}

	/**
	 * Returns a specific cell of matrix. <br>
	 * 
	 * @return cell.
	 */
	public Cell getOneCell(int i, int j) {
		return cells[i][j];
	}

	/**
	 * Returns board's state <br>
	 * 
	 * @return gameState.
	 */
	public String getGameState() {
		return gameState;
	}

	/**
	 * Sets board's state. <br>
	 * 
	 * @param gameState
	 *            new gameSate.
	 */
	public void setGameState(String gameState) {
		this.gameState = gameState;
	}

	public int getUncoveredCells() {
		return uncoveredCells;
	}

	public void setUncoveredCells(int uncoveredCells) {
		this.uncoveredCells = uncoveredCells;
	}

	/**
	 * Converts the matrix's cells state into a string
	 */
	public String toString() {

		String game = "";
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				game += cells[i][j].toString() + " ";
			}
			game += "\n";
		}
		return game;
	}
}
