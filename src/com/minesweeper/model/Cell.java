package com.minesweeper.model;

public class Cell {

	/**
	 * Board's cell class <br>
	 * <br>
	 * inv: <br>
	 * state != null <br>
	 */

	// -----------------------------------------------------------------
	// Constants
	// -----------------------------------------------------------------

	/**
	 * Disable cell (a cell that can not be modified by the user)
	 */
	public final static int DISABLE = 0;
	/**
	 * Unselected cell (can be uncovered or marked)
	 */
	public final static int UNSELECTED = 01;
	/**
	 * Uncovered cell
	 */
	public final static int UNCOVER = 02;
	/**
	 * Mine cell (represents a mine)
	 */
	public final static int MINE = 99;
	/**
	 * Represents a flag, used when the user marks a cell
	 */
	public final static int MARKED = 10;

	
	// -----------------------------------------------------------------
	// Attributes
	// ----------------------------------------------------------------

	/**
	 * Cell's state based on a constant
	 */
	private int state;

	/**
	 * defines whether is a mine or not
	 */
	private boolean isMine;
	/**
	 * Mines around this cell
	 */
	private int minesAround;

	// -----------------------------------------------------------------
	// Builder
	// -----------------------------------------------------------------
	/**
	 * Builder of class. <br>
	 */
	public Cell() {
		super();
		this.state = Cell.UNSELECTED;
		this.isMine = false;
		this.minesAround = 0;
	}

	// -----------------------------------------------------------------
	// methods
	// -----------------------------------------------------------------
	/**
	 * Returns cell's state number. <br>
	 * 
	 * @return height.
	 */
	public int getState() {
		return state;
	}

	/**
	 * Sets cell's state number. <br>
	 * 
	 * @param state
	 *            new cell's state number
	 */
	public void setState(int state) {
		this.state = state;
	}

	/**
	 * Returns cell's isMine state. <br>
	 * 
	 * @return isMine.
	 */
	public boolean isMine() {
		return isMine;
	}

	/**
	 * Sets cell's isMine state. <br>
	 * 
	 * @param isMine
	 *            new cell's isMine state
	 */
	public void setMine(boolean isMine) {
		this.isMine = isMine;
	}

	/**
	 * Returns cell's mines around number. <br>
	 * 
	 * @return minesAround.
	 */
	public int getMinesAround() {
		return minesAround;
	}

	/**
	 * Sets cell's mines around number. <br>
	 * 
	 * @param minesAround
	 *            new cell's mines around number
	 */
	public void setMinesAround(int minesAround) {
		this.minesAround = minesAround;
	}
	
	
	/*
	 * Returns the string value of cell
	 */
	public String toString() {
		
		String printState="";
		if(this.state==this.DISABLE) {
			printState="-";
		}else if (this.state==this.UNSELECTED) {
			printState=".";
		}else if (this.state==this.MINE) {
			printState="*";
		}else if (this.state==this.UNCOVER) {
			if(this.minesAround==0) {
				printState="-";
			}else {
				printState=this.minesAround+"";
			}
		}else if (this.state==this.MARKED) {
			printState="P";
		}
		return printState;
	}
	

}
