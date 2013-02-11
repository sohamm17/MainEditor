

/**
 * Class line
 * An object of line means different connected line-segments
 * 
 */
public class line {

  //
  // Fields
  //

  public point line_segs;
  public int id;
  
  //
  // Constructors
  //
  public line () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of line_segs
   * @param newVar the new value of line_segs
   */
  public void setLine_segs ( point newVar ) {
    line_segs = newVar;
  }

  /**
   * Get the value of line_segs
   * @return the value of line_segs
   */
  public point getLine_segs ( ) {
    return line_segs;
  }

  /**
   * Set the value of id
   * @param newVar the new value of id
   */
  public void setId ( int newVar ) {
    id = newVar;
  }

  /**
   * Get the value of id
   * @return the value of id
   */
  public int getId ( ) {
    return id;
  }

  //
  // Other methods
  //

  /**
   * @param        a To get the relevant ID
   */
  public void line( connections a )
  {
  }


  /**
   * @param        x
   */
  public void add_line_seg( point x )
  {
  }


  /**
   * @param        strt one end of line-segment
   * @param        end another end of line-segment
   */
  public void remove_line_seg( point strt, point end )
  {
  }


}
