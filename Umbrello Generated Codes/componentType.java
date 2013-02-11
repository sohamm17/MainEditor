

/**
 * Class componentType
 * This class is to define one componentType of our circuit editor.
 */
public class componentType {

  //
  // Fields
  //

  /**
   * height of the componentType
   */
  public int height;
  /**
   * width of componentType
   */
  public int width;
  /**
   * the image of the componentType
   */
  public Label img;
  /**
   * mark_points with respect to Top-Left as (0,0)
   */
  public iopoints mark_points;
  /**
   * Unique id.
   */
  public int id;
  /**
   * In which package it belongs
   */
  public package_cls type_pkg;
  
  //
  // Constructors
  //
  public componentType () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of height
   * height of the componentType
   * @param newVar the new value of height
   */
  public void setHeight ( int newVar ) {
    height = newVar;
  }

  /**
   * Get the value of height
   * height of the componentType
   * @return the value of height
   */
  public int getHeight ( ) {
    return height;
  }

  /**
   * Set the value of width
   * width of componentType
   * @param newVar the new value of width
   */
  public void setWidth ( int newVar ) {
    width = newVar;
  }

  /**
   * Get the value of width
   * width of componentType
   * @return the value of width
   */
  public int getWidth ( ) {
    return width;
  }

  /**
   * Set the value of img
   * the image of the componentType
   * @param newVar the new value of img
   */
  public void setImg ( Label newVar ) {
    img = newVar;
  }

  /**
   * Get the value of img
   * the image of the componentType
   * @return the value of img
   */
  public Label getImg ( ) {
    return img;
  }

  /**
   * Set the value of mark_points
   * mark_points with respect to Top-Left as (0,0)
   * @param newVar the new value of mark_points
   */
  public void setMark_points ( iopoints newVar ) {
    mark_points = newVar;
  }

  /**
   * Get the value of mark_points
   * mark_points with respect to Top-Left as (0,0)
   * @return the value of mark_points
   */
  public iopoints getMark_points ( ) {
    return mark_points;
  }

  /**
   * Set the value of id
   * Unique id.
   * @param newVar the new value of id
   */
  public void setId ( int newVar ) {
    id = newVar;
  }

  /**
   * Get the value of id
   * Unique id.
   * @return the value of id
   */
  public int getId ( ) {
    return id;
  }

  /**
   * Set the value of type_pkg
   * In which package it belongs
   * @param newVar the new value of type_pkg
   */
  public void setType_pkg ( package_cls newVar ) {
    type_pkg = newVar;
  }

  /**
   * Get the value of type_pkg
   * In which package it belongs
   * @return the value of type_pkg
   */
  public package_cls getType_pkg ( ) {
    return type_pkg;
  }

  //
  // Other methods
  //

  /**
   * initialized with 'pkg' package_cls and updates the counts.
   * @param        pkg
   * @param        a needed so that counts can be updated.
   */
  public void componentType( package_cls pkg, counts a )
  {
  }


  /**
   * creates thye image or the Label
   * @param        path
   * @param        description
   */
  public void createImageIcon( String path, String description )
  {
  }


}
