

/**
 * Class counts
 * Keeps track of the total component's, componentType's,package_cls's
 */
public class counts {

  //
  // Fields
  //

  /**
   * Total List of the component
   */
  public component component_list;
  /**
   * Total List of the componentType
   */
  public componentType componentType_list;
  /**
   * Total List of the componentType
   */
  public package_cls package_list;
  /**
   * generates ID of the cmp
   */
  public int cmp_id;
  /**
   * generates ID of the cmpType
   */
  public int cmpType_id;
  
  //
  // Constructors
  //
  public counts () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of component_list
   * Total List of the component
   * @param newVar the new value of component_list
   */
  public void setComponent_list ( component newVar ) {
    component_list = newVar;
  }

  /**
   * Get the value of component_list
   * Total List of the component
   * @return the value of component_list
   */
  public component getComponent_list ( ) {
    return component_list;
  }

  /**
   * Set the value of componentType_list
   * Total List of the componentType
   * @param newVar the new value of componentType_list
   */
  public void setComponentType_list ( componentType newVar ) {
    componentType_list = newVar;
  }

  /**
   * Get the value of componentType_list
   * Total List of the componentType
   * @return the value of componentType_list
   */
  public componentType getComponentType_list ( ) {
    return componentType_list;
  }

  /**
   * Set the value of package_list
   * Total List of the componentType
   * @param newVar the new value of package_list
   */
  public void setPackage_list ( package_cls newVar ) {
    package_list = newVar;
  }

  /**
   * Get the value of package_list
   * Total List of the componentType
   * @return the value of package_list
   */
  public package_cls getPackage_list ( ) {
    return package_list;
  }

  /**
   * Set the value of cmp_id
   * generates ID of the cmp
   * @param newVar the new value of cmp_id
   */
  public void setCmp_id ( int newVar ) {
    cmp_id = newVar;
  }

  /**
   * Get the value of cmp_id
   * generates ID of the cmp
   * @return the value of cmp_id
   */
  public int getCmp_id ( ) {
    return cmp_id;
  }

  /**
   * Set the value of cmpType_id
   * generates ID of the cmpType
   * @param newVar the new value of cmpType_id
   */
  public void setCmpType_id ( int newVar ) {
    cmpType_id = newVar;
  }

  /**
   * Get the value of cmpType_id
   * generates ID of the cmpType
   * @return the value of cmpType_id
   */
  public int getCmpType_id ( ) {
    return cmpType_id;
  }

  //
  // Other methods
  //

  /**
   * resets all to zero
   */
  public void reset(  )
  {
  }


  /**
   * adds new package
   * @param        desired_install
   */
  public void install_new_package( package_cls desired_install )
  {
  }


  /**
   * removes package
   * @param        desired_uninstall
   */
  public void uninstall_package( package_cls desired_uninstall )
  {
  }


  /**
   * adds new componentType
   * @param        x
   */
  public void add_new_componentType( componentType x )
  {
  }


  /**
   * removes componentType
   * @param        x
   */
  public void remove_componentType( componentType x )
  {
  }


  /**
   * adds new component
   * @param        x
   */
  public void add_new_component( component x )
  {
  }


  /**
   * removes component
   * @param        x
   */
  public void remove_component( component x )
  {
  }


}
