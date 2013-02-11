package circuits;

import java.awt.Point;


/**
 * Class iopoints
 */
public class iopoints extends Point{

  //
  // Fields
  //

  /**
     * if io=0 then input
     * if io=1 then output
  */
  public int io;
  
  //
  // Constructors
  //
  public iopoints () {
      io=0;//by default it's an input point
  };
  
  /**
   * copy constructor but method-name is change
   * @param newVar 
   */
  public void change(iopoints newVar)
  {
      this.setIo(newVar.getIo());
      this.setLocation(newVar.getLocation());
  }
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of io
   * @param newVar the new value of io
   */
  public void setIo ( int newVar ) {
    io = newVar;
  }

  /**
   * Get the value of io
   * @return the value of io
   * 0 for input, 1 for output
   */
  public int getIo ( ) {
    return io;
  }

  //
  // Other methods
  //
}