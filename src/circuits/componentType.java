package circuits;

import java.awt.Image;
import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import java.util.Hashtable;

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
   * the Label of the componentType
   */
  public JLabel img;
  /**
   * the Image of the componentType
   */
  public Image type_img;
  /**
   * mark_points with respect to Top-Left as (0,0)
   */
  public ArrayList<iopoints> mark_points;
  /**
   * Unique id.
   */
  public int id;
  /**
   * In which package it belongs
   */
  public package_cls type_pkg;
  
  /**
   * name of the componentType
   */
  public String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
  
  /**
   * Rules of the componentType
   * rules may be 0 + 1 or 0 *1
   */
  public Hashtable rules;

    public void setRules(Hashtable rules) {
        this.rules = rules;
    }

    public Hashtable getRules() {
        return rules;
    }
    
    public String getRule(int output_mark_point)
    {
        Object returnValue;
        returnValue = this.rules.get(output_mark_point);
        if(returnValue != null)
            return (String) returnValue;
        else
            return null;
    }

    public void setRules(String[] listOfRules) {
        int outputMarkPointNo = 0, i = 0;
        rules = new Hashtable();
        for(iopoints x:mark_points)
        {
            if(x.getIo() == 1)
            {
                rules.put(outputMarkPointNo, listOfRules[i++]);
            }
            outputMarkPointNo++;
        }        
    }
  
  //
  // Constructors
  //
  /**
   * initialized with 'pkg' package_cls and updates the counts.
   * @param        pkg
   * @param        a needed so that counts can be updated.
   * @param        path needed to create the image
   */
  public componentType( package_cls pkg, counts a, String path, iopoints[] marks, String name, Hashtable rules)
  {
      this.setType_pkg(pkg);
      a.add_new_componentType(this);
      this.setId(a.getCmpType_id());
      this.createImageIcon(path, "componentType");
      this.setMark_points(marks);
      this.setName(name);
      this.rules = new Hashtable(this.getMark_points().size());
      this.setRules(rules);
      //this.setRules(listOfRules);
      this.img.setVisible(true);
      this.img.setOpaque(true);
      this.img.setEnabled(true);
      this.img.setDoubleBuffered(true);
      String[] myexps = new String[3];
      //System.out.println(this.getMark_points());
  }
  
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
  private void setHeight ( int newVar ) {
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
  private void setWidth ( int newVar ) {
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
  public void setImg ( JLabel newVar ) {
    img = newVar;
  }

  /**
   * Get the value of img
   * the image of the componentType
   * @return the value of img
   */
  public final JLabel getImg ( ) {
    return img;
  }

  /**
   * Set the value of img
   * the image of the componentType
   * @param newVar the new value of img
   */
  public void setType_img ( Image newVar ) {
    type_img = newVar;
  }

  /**
   * Get the value of img
   * the image of the componentType
   * @return the value of img
   */
  public final Image getType_Img ( ) {
    return type_img;
  }
  
  /**
   * Set the value of mark_points
   * mark_points with respect to Top-Left as (0,0)
   * @param newVar the new value of mark_points
   */
  private void setMark_points ( iopoints[] newVar ) {
    mark_points = new ArrayList<iopoints>(Arrays.asList(newVar));
  }

  /**
   * Get the value of mark_points
   * mark_points with respect to Top-Left as (0,0)
   * @return the value of mark_points
   */
  public final ArrayList<iopoints> getMark_points ( ) {
    return mark_points;
  }
  

  /**
   * Set the value of id
   * Unique id.
   * @param newVar the new value of id
   */
  public final void setId ( int newVar ) {
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
  public final void setType_pkg ( package_cls newVar ) {
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

  /**
   * Get the name of the package
   * @return the value of type_pkg.getName()
   */
  public String getType_pkg_name ( ) {
    return type_pkg.getName();
  }
  
  //
  // Other methods
  //

  /**
   * creates the image or the Label
   * @param        path
   * @param        description
   */
  public final void createImageIcon( String path, String description )
  {
      ImageIcon a;
      if (path != null)
      {
          a = new ImageIcon(path, description);
      }
      else
      {
          System.err.println("Couldn't find file: " + path);
          a = null;
      }
      this.setType_img(a.getImage());
      this.setHeight(a.getIconHeight());
      this.setWidth(a.getIconWidth());
      this.setImg(new JLabel(a));
  }
}
