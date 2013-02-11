

/**
 * Class package_cls
 * This class is to define the package of our circuit editor.
 * A Package contains multiple componentType.
 */
public class package_cls {

  //
  // Fields
  //

  /**
   * After parsing of the zipped file, all componentType of that package will be listed here.
   */
  public componentType componentType_list;  /**

   * name of the package.
   * it'll act as an ID(unique).
   * it'll be available from parsing.   */

  public String name;
  
  //
  // Constructors
  //
  public package_cls () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of componentType_list
   * After parsing of the zipped file, all componentType of that package will be
   * listed here.
   * @param newVar the new value of componentType_list
   */
  public void setComponentType_list ( componentType newVar ) {
    componentType_list = newVar;
  }

  /**
   * Get the value of componentType_list
   * After parsing of the zipped file, all componentType of that package will be
   * listed here.
   * @return the value of componentType_list
   */
  public componentType getComponentType_list ( ) {
    return componentType_list;
  }

  /**
   * Set the value of name
   * name of the package.
   * it'll act as an ID(unique).
   * it'll be available from parsing.
   * @param newVar the new value of name
   */
  public void setName ( String newVar ) {
    name = newVar;
  }

  /**
   * Get the value of name
   * name of the package.
   * it'll act as an ID(unique).
   * it'll be available from parsing.
   * @return the value of name
   */
  public String getName ( ) {
    return name;
  }

  //
  // Other methods
  //

  /**
   * It'll take the path as input.
   * and updates the counts to add itself.
   * @param        path
   * @param        a To update the counts.
   */
  public void package_cls( String path, counts a )
  {
  }


  /**
   * It'll parse the package from the zipped file.
   */
  public void parse(  )
  {
  }


}
