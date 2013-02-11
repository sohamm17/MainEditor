package circuits;

import java.util.ArrayList;


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
  public ArrayList<component> component_list;
  /**
   * Total List of the componentType
   */
  public ArrayList<componentType> componentType_list;
  /**
   * Total List of the Packages
   */
  public ArrayList<package_cls> package_list;
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
  public counts () 
  {
      this.setCmpType_id(0);
      this.setCmp_id(0);
      this.componentType_list=new ArrayList<componentType>();
      this.component_list=new ArrayList<component>();
      this.package_list=new ArrayList<package_cls>();
  };
  
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
  /*public void setComponent_list ( component newVar )
  {
    component_list = newVar;
  }*/

  /**
   * Get the value of component_list
   * Total List of the component
   * @return the value of component_list
   */
  public ArrayList<component> getComponent_list ( ) {
    return component_list;
  }

  /**
   * Set the value of componentType_list
   * Total List of the componentType
   * @param newVar the new value of componentType_list
   */
  /*public void setComponentType_list ( componentType newVar ) {
    componentType_list = newVar;
  }*/

  /**
   * Get the value of componentType_list
   * Total List of the componentType
   * @return the value of componentType_list
   */
  public ArrayList<componentType> getComponentType_list ( ) {
    return componentType_list;
  }

  /**
   * Set the value of package_list
   * Total List of the componentType
   * @param newVar the new value of package_list
   */
  /*public void setPackage_list ( package_cls newVar ) {
    package_list = newVar;
  }*/

  /**
   * Get the value of package_list
   * Total List of the componentType
   * @return the value of package_list
   */
  public ArrayList<package_cls> getPackage_list ( ) {
    return package_list;
  }

  /**
   * Set the value of component_id
   * generates ID of the component
   * @param newVar the new value of component_id
   */
  public final void setCmp_id ( int newVar ) {
    cmp_id = newVar;
  }

  /**
   * Get the value of component_id
   * generates ID of the component
   * to get the ID, one has to add the component first and the request for the id
   * @return the value of component_id
   */
  public int getCmp_id ( ) {
    return cmp_id;
  }

  /**
   * Set the value of cmpType_id
   * generates ID of the cmpType
   * @param newVar the new value of cmpType_id
   */
  public final void setCmpType_id ( int newVar ) {
    cmpType_id = newVar;
  }

  /**
   * Get the value of cmpType_id
   * generates ID of the cmpType
   * to get the ID, one has to add the componentType first and the request for the id
   * @return the value of cmpType_id
   */
  public int getCmpType_id ( ) {
    return cmpType_id;
  }

  //
  // Other methods
  //

  /**
   * clears all the components.
   */
  public void reset(  )
  {
     this.setCmp_id(0);
     //cmpType_id=0;
     //this.componentType_list.clear();
     this.component_list.clear();
     //this.package_list.clear();
  }


  /**
   * adds new package
   * @param        desired_install
   */
  public void install_new_package( package_cls desired_install )
  {
      this.package_list.add(desired_install);
      //System.out.println("Hello,");
  }


  /**
   * removes package
   * @param        desired_uninstall
   */
  public void uninstall_package( package_cls desired_uninstall )
  {
      
      this.package_list.remove(desired_uninstall);
  }


  /**
   * adds new componentType
   * @param        x
   */
  public void add_new_componentType( componentType x )
  {
      this.componentType_list.add(x);
      this.setCmpType_id(this.getCmpType_id()+1);
  }


  /**
   * removes componentType
   * @param        x
   */
  public void remove_componentType( componentType x )
  {
      this.componentType_list.remove(x);
      //this.setCmpType_id(this.getCmpType_id()-1);
  }


  /**
   * adds new component
   * @param        x
   */
  public void add_new_component( component x )
  {
      this.component_list.add(x);
      this.setCmp_id(this.getCmp_id()+1);
  }


  /**
   * removes component
   * @param        x
   */
  public void remove_component( component x )
  {
      this.component_list.remove(x);
      //this.setCmp_id(this.getCmp_id()-1);
  }
  
  /**
   * if no component is there, then returns true i.e empty
   * otherwise false
   */
  public boolean is_empty()
  {
      if(this.getCmp_id()==0)
          return true;
      else 
          return false;
  }
  
  /**
   * Returns the component with id x
   * @param x
   * @return the component with x id if found, null otherwise
   */
  public component get_component_by_id(int x)
  {
      for(component a:this.getComponent_list())
      {
          if(a.getId()==x)
          {
              return a;
          }
      }
      return null;
  }
  
  /**
   * Tells how many number of user-installed packages are there
   * @return number of packages installed by user and not the System Packages
   */
  public int noUserPkg()
  {
      int ret=0;
      for(package_cls a:this.getPackage_list())
      {
          if(!a.getIsSysPkg())
              ret++;
      }
      return ret;
  }

  /**
   * Removes i-th component from counts
   * @param i the index of component to be removed
   */
    public void remove_component(int i) {
        this.remove_component(this.getComponent_list().get(i));
    }

    /**
     * Searches the highest ID among the component-s and assign new cmpId accordingly
     * This is needed when some random IDs are assigned to component-s
     * Then determining the next ID to be assigned is needed
     */
    public void getNewCmpId()
    {
        int high=0;
        for(component a:this.getComponent_list())
        {
            if(high<a.getId())
                high=a.getId();
        }
        this.setCmp_id(high);
    }
    
    public void update_expressions()
    {
        for(component x:this.getComponent_list())
        {
            x.parseOutputRules();
        }
    }
}
