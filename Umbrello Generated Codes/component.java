

/**
 * Class component
 * This class is to define one component of our circuit editor.
 * 
 * Like a single Two-input OR gate, a single 3-input NAND gate.
 */
public class component {

  //
  // Fields
  //

  /**
   * the Left-most point of the Label(Image) or the component.
   */
  public point position;
  /**
   * type of the componentType
   */
  public componentType type;
  /**
   * the unique ID, which will be available from counts.
   */
  public int id;
  
  //
  // Constructors
  //
  public component () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of position
   * the Left-most point of the Label(Image) or the component.
   * @param newVar the new value of position
   */
  public void setPosition ( point newVar ) {
    position = newVar;
  }

  /**
   * Get the value of position
   * the Left-most point of the Label(Image) or the component.
   * @return the value of position
   */
  public point getPosition ( ) {
    return position;
  }

  /**
   * Set the value of type
   * type of the componentType
   * @param newVar the new value of type
   */
  public void setType ( componentType newVar ) {
    type = newVar;
  }

  /**
   * Get the value of type
   * type of the componentType
   * @return the value of type
   */
  public componentType getType ( ) {
    return type;
  }

  /**
   * Set the value of id
   * the unique ID, which will be available from counts.
   * @param newVar the new value of id
   */
  public void setId ( int newVar ) {
    id = newVar;
  }

  /**
   * Get the value of id
   * the unique ID, which will be available from counts.
   * @return the value of id
   */
  public int getId ( ) {
    return id;
  }

  //
  // Other methods
  //

  /**
   * componentType will be initialized and counts will be updated.
   * @param        cmp
   * @param        a to update the counts and to get the id.
   */
  public void component( componentType cmp, counts a )
  {
  }


  /**
   * to move 'x' in X-direction
   * @param        x
   */
  public void addX( int x )
  {
  }


  /**
   * to move 'y' in Y-direction
   * @param        y
   */
  public void addY( int y )
  {
  }


  /**
   * mark_points of the component.
   * will be available from package and componentType
   * 
   * @return       iopoints
   */
  public iopoints get_mark_point(  )
  {
  }


}
