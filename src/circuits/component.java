package circuits;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.regex.Pattern;
import javax.swing.JLabel;
import javax.swing.JTextArea;
// import MainEditor.
        
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
  public Point position;
  /**
   * type of the componentType
   */
  public componentType type;
  /**
   * the unique ID, which will be available from counts.
   */
  public int id;
  
  public JLabel label;

    public JLabel getLabel() {
        return label;
    }

    public void setLabel(JLabel label) {
        this.label = label;
    }
  
    /**
   * the expressions associated with each mark_points
   */
  public Hashtable expression;

  /**
   * gets the expression list counter-clock wise
   * @return 
   */
  public Hashtable getExpression()
  {
      return expression;
  }
  
  public String getExpression(int mark_point_no)
  {
      Object returnValue = expression.get(mark_point_no);
      if(returnValue != null)
          return (String) returnValue;
      else
          return null;
  }

  /**
   * sets the expression from top-left corner mark_points counter-clockwise
   */ 
  public void setExpression(Hashtable expression)
  {
      this.expression = expression;
  }
  
  /**
   * sets an expression to a key, mark_point of that component
   * @param mark_point_no the key, mark_point_no with which value is to be associated
   * @param exp the value to be set, if not null
   */
  public void setExpression(int mark_point_no, String exp)
  {
      if(exp != null)
              expression.put(mark_point_no, exp);
  }
  
  //
  // Constructors
  //
  
  /**
   * componentType will be initialized and counts will be updated with this constructor 
   * Also ID will be set explicitely
   * @param        cmp
   * @param        a to update the counts and to get the id.
   * @param pos the position will be set
   * @param newId Explicit ID definition of the component 
   * this is optional, if you want auto allocation of id or implicit allocation just give '0' as newId
   */
  public component( componentType cmp,Point pos,counts a, int newId)
  {
      this.setType(cmp);
      this.setPosition(pos);
      a.add_new_component(this);
      if(newId==0)
          this.setId(a.getCmp_id());
      else
      {
          this.setId(newId);
          a.getNewCmpId();
      }
      this.label=new JLabel(this.getType().getImg().getIcon());
      this.expression = new Hashtable(this.get_mark_point().size());
      this.runPrimaryRules();
      this.parseOutputRules();
  }
  
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
  public final void setPosition ( Point newVar ) {
    position = newVar;
  }

  /**
   * Get the value of position
   * the Left-most point of the Label(Image) or the component.
   * @return the value of position
   */
  public Point getPosition ( ) {
    return position;
  }

  /**
   * Set the value of type
   * type of the componentType
   * @param newVar the new value of type
   */
  public final void setType ( componentType newVar ) {
    type = newVar;
  }

  /**
   * Get the value of type
   * type of the componentType
   * @return the value of type
   */
  public final componentType getType ( ) {
    return type;
  }

  /**
   * Set the value of id
   * the unique ID, which will be available from counts.
   * @param newVar the new value of id
   */
  private final void setId ( int newVar ) {
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
   * to move 'x' in X-direction
   * @param        x
   */
  public void addX( int x )
  {
      this.setPosition(new Point(this.getPosition().x+x,this.getPosition().y));
  }


  /**
   * to move 'y' in Y-direction
   * @param        y
   */
  public void addY( int y )
  {
      this.setPosition(new Point(this.getPosition().x,this.getPosition().y+y));
  }


  /**
   * real-time mark_points of the component.
   * will be available from package and componentType
   * @return  an ArrayList of iopoints with the calculated mark_points
   */
  public ArrayList<iopoints> get_mark_point(){
      ArrayList<iopoints> rsltd = new ArrayList<iopoints>();
      for(iopoints p : this.getType().getMark_points())
      {
          iopoints a = new iopoints();
          //after adding the offset from componentType to the component's X & Y-Coordinate
          a.setLocation(p.getX()+this.getPosition().getX(), p.getY()+this.getPosition().getY());
          a.setIo(p.getIo()); 
          rsltd.add(a);
      }
      return rsltd;
  }
  
  /**
   * Get the i-th Mark Point
   * @param i desired Mark Point
   * @return Returns the desired Mark Point in iopoints class
   */
  public iopoints get_mark_points (int i) {
      return this.get_mark_point().get(i);      
  }

  /**
   * If a Point is within the covered area of component returns true.
   * @param point
   * @return true, if point is within the component area. false, otherwise
   */
    public boolean isHit(Point point)
    {
        Rectangle2D.Float myarea= new Rectangle2D.Float();
        myarea.setRect(this.getPosition().x, this.getPosition().y, this.getType().getWidth(), this.getType().getHeight());
        if (myarea.contains(point))
        {
            return true;
        }
        else
        {
            return false;
        }
    }
    
    /**
     * returns true if the p Point is within vicinity of any mark_point and with the matched Mark Point at ret_mark_point
     * vicinity means within -8,+8 of the center point
     * @param p the point to be checked
     * @param ret_mark_point if true, then desired Mark Point is stored. null, otherwise.
     * @param ret[0] if method returns true, stores the id of the component. if false, stores -1
     * @param ret[1] if method returns true, stores Mark Point Number(counter). if false, stores -1
     * @return true, if p is within the vicinity of any mark_points
     */
    public boolean isVicinity(Point p, iopoints ret_mark_point,int [] ret)
    {
        Rectangle2D.Float myarea= new Rectangle2D.Float();
        int i=0;
        for(iopoints cntr : this.get_mark_point())
        {
            myarea.setRect(cntr.getX()-8,cntr.getY()-8,16,16);
            if(myarea.contains(p))
            {
                ret_mark_point.change(cntr);
                ret[0]=this.getId();
                ret[1]=i;
                //System.out.println("P"+ret[0]+","+ret[1]);
                return true;
            }
            i++;
        }
        ret[0]=ret[1]=-1;
        ret_mark_point=null;
        return false;
    }
    
    /**
     * Primary Rules after creating a component only for Input Points
     */
    public void runPrimaryRules()
    {
        int counter = 0;
        for(iopoints x:this.get_mark_point())
        {
            if(x.getIo() == 0)
            {
                this.runPrimaryRules(counter);
            }
            counter++;
        }
        parseOutputRules();
    }
    
    public void runPrimaryRules(int i)
    {
        this.setExpression(i, (String) (this.getType().getName() + this.getId() + " " + i));
    }
    
    /**
     * parse individual output rules
     * @param output_mark_point_no
     * @return 
     */
    public String parseRule(int output_mark_point_no)
    {
        StringBuilder returnValue = new StringBuilder();
        String rule = this.getType().getRule(output_mark_point_no);
        String []splitedOperand;
        String []splitedOperator;
        Pattern p = Pattern.compile("[[\\S]&&[\\D]]");
        Pattern p1 = Pattern.compile("[\\d]");
        splitedOperand = p.split(rule);
        splitedOperator = p1.split(rule);
        int index = 1;
        for(String x:splitedOperand)
        {
            returnValue.append("(" + this.getExpression(Integer.parseInt(x.trim())) + ")");
            if(index < splitedOperator.length)
                returnValue.append(splitedOperator[index++]);
        }
        return returnValue.toString();
    }

    public void parseOutputRules()
    {
        int counter = 0;
        for(iopoints x:this.get_mark_point())
        {
            if(x.getIo() == 1)
            {
                this.setExpression(counter, this.parseRule(counter));
            }
            counter++;
        }
    }
    
    public JTextArea[] getMarkPointLabels()
    {
        JTextArea[] returnJLabels = new JTextArea[this.get_mark_point().size()];
        int counter = 0;
        for(iopoints x:this.get_mark_point())
        {
            JTextArea currentJLabel = new JTextArea();
            currentJLabel.setText(this.getExpression(counter));
            currentJLabel.setBounds(x.x-50, x.y-50,400,15);
            currentJLabel.setForeground(Color.red);
            currentJLabel.setBackground(Color.BLACK);
            //Dimension size = currentJLabel.getPreferredSize();
            //currentJLabel.setMinimumSize(size);
            //currentJLabel.setPreferredSize(size);
            currentJLabel.setLineWrap(true);
            currentJLabel.setWrapStyleWord(true);
            //currentJLabel.setOpaque(false);
            currentJLabel.setEditable(false);
            currentJLabel.setVisible(true);
            returnJLabels[counter++] = currentJLabel;
        }
        return returnJLabels;
    }
}