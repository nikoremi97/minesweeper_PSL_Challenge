package controller;

public class BadBoardException extends Exception {
	// -----------------------------------------------------------------
    // Constructor
    // -----------------------------------------------------------------

    /**
     *Builds a Exception each time an user tries to violate the format of Board builder and tells the rules
     */
    public BadBoardException()
    {
        System.out.println("Wrong set up");
    }
}
