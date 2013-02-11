package mainpackage;

import circuits.*;
import java.awt.*;
import java.awt.Cursor;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.String;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.JLabel;
import org.w3c.dom.*;
import org.apache.xerces.parsers.*;
import org.xml.sax.SAXException;


/**
 * Class Pad_Draw
 */
public final class Pad_Draw extends JComponent{
    
    /**
     * The Graphics used throughout the class
     */
    private Graphics2D graphics2D;
    /**
     * Local counts class instance, used for moving the component
     * copy of the actual counts class-instance(global)
     */
    private counts Pad_counts;
    /**
     * Local connections class instance, used for drawing the lines
     * copy of the actual connections class-instance(global)
     */
    private connections Pad_connections;
    /**
     * Background Image
     */
    private Image bgImage;
    /**
     * updates the current status to task bar
     * for temporary use
     */
    JTextArea taskbr;
    /**
     * current Image
     */
    Image curImg;
    
    //for lines
    private int currentX = 0, currentY = 0, oldX = 0, oldY = 0,tmpX,tmpY;
    private boolean mousedragActv=false, desn=true,locked=true;//for lines
      
    
    /**
     * Class to define how the Select & Move works
     */
    class MovingAdapter extends MouseAdapter
    {
        private int x=0;
        private int y=0;
        private int mutual=-2;//mutual is to select only one component if co-ordinate coinsides

        @Override
        public void mousePressed(MouseEvent e)
        {
            if(e.getButton()==MouseEvent.BUTTON1)
            {
                x = e.getX();
                y = e.getY();
                int i=0;
                for(component comp : Pad_counts.getComponent_list())
                {
                    //System.out.println("As6e..");
                    if (comp.isHit(e.getPoint()))
                    {
                        //System.out.println("Got the Hit"+i);
                        //System.out.println("AA"+comp.getPosition()+","+x+","+y);
                        set_move_cursor();
                    }
                    i++;
                }
            }
        }

        @Override
        public void mouseDragged(MouseEvent e)
        {
            int dx = e.getX() - x;
            int dy = e.getY() - y;
            //System.out.println("MYDX"+dx+","+dy+","+x+","+y);
            component comp = null;
            //System.out.println("It's coming2");
            for(int i=0; i<Pad_counts.getComponent_list().size(); i++)
            {
                comp=Pad_counts.getComponent_list().get(i);
                if (comp.isHit(e.getPoint()) && (mutual==i || mutual==-2))
                {
                    mutual=i;
                    set_move_cursor();
                    comp.addX(dx);
                    comp.addY(dy);
                    change(i,comp.getPosition());
                    //System.out.println("Got the HitA"+mutual+","+x+","+y+","+e.getX()+","+e.getY());
                    //System.out.println("A"+comp.getPosition());
                    break;
                }
            }
            x+= dx;
            y+= dy;
        }
        
        @Override
        public void mouseMoved(MouseEvent e)
        {
            //System.out.println("It's coming1");
            set_default_cursor();
            mutual=-2;
        }
        
        @Override
        public void mouseClicked(MouseEvent e)
        {
            if(e.getButton()==MouseEvent.BUTTON3)
            {
                int i=0;
                for(component comp : Pad_counts.getComponent_list())
                {
                    //System.out.println("As6e..");
                    if (comp.isHit(e.getPoint()))
                    {
                        final int temp=i;//as we cannot access i without making a final
                        //so dumping it by temp
                        JPopupMenu editMenu = new JPopupMenu();
                        JMenuItem del = new JMenuItem("Delete");
                        ActionListener deL=new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            RemoveI(temp);
                            };
                        };
                        del.addActionListener(deL);
                        editMenu.add(del);
                        editMenu.setInvoker(getComponentI(i));
                        editMenu.setLocation(e.getLocationOnScreen());
                        //System.out.println(e.getPoint()+","+editMenu.getLocation());
                        editMenu.setVisible(true);
                    }
                    i++;
                }
            }
        }
    }
    MovingAdapter slct=new MovingAdapter();
    
  //
  // Fields
  //
  
  //
  // Constructors
  //
    
  /**
     * Initialize the drawing-pad with a and cnc_a
     * @param a
     * @param cnc_a 
     * @param task content of taskbar if needed to be changed
     */  
  public Pad_Draw(counts a,connections cnc_a,JTextArea task)
  {
      this.setDoubleBuffered(true);
      //this.setLayout(new GridLayout());
      //this.setSize(100, 100);
      //this.setMinimumSize(new Dimension(100, 100));
      //this.setMaximumSize(new Dimension(100, 100));
      //this.setPreferredSize(new Dimension(200, 100));
      //this.setAutoscrolls(true);
      Pad_counts=a;
      Pad_connections=cnc_a;
      locked=true;
      this.bgImage=null;
      this.clear(a,cnc_a);
      this.taskbr=task;
  };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  //
  // Other methods
  //

  /**
   * Returns the i-th Component of Pad_Draw
   * @param i index of the Component
   * @return the Component with index i
   */
  public Component getComponentI(int i)
  {
      return this.getComponent(i);
  }
  
  /**
   * Removes the i-th Component from Pad_Draw and i-th component from Pad_counts
   * @param i 
   */
  private void RemoveI(int i)
  {
      this.Pad_counts.remove_component(i);
      this.remove(i);
      Pad_connections.update(Pad_counts);
      repaint();
  }
  
  /**
   * @param        g
   */
    @Override
  public void paintComponent( Graphics g )
  {
      graphics2D = (Graphics2D)g;
      graphics2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
      graphics2D.setPaint(Color.white);
      graphics2D.fillRect(0, 0, getSize().width, getSize().height);
      if(bgImage!=null)
      {
          graphics2D.drawImage(bgImage, 0, 0, null);
      }
      graphics2D.setColor(Color.BLUE);
      int j=1;
      for(line a:Pad_connections.getLine_logs())
      {
          if(a.getId()==0)
              graphics2D.setColor(Color.red);
          for(int i=1;i<a.getLine_segs().size();i++)
          {
              Point p1=a.getLine_segs().get(i-1);
              Point p2=a.getLine_segs().get(i);
              //System.out.println(p1);
              graphics2D.drawLine(p1.x, p1.y, p2.x, p2.y);
          }
          j++;
      }
      graphics2D.setColor(Color.YELLOW);
      if(mousedragActv)
          g.drawLine(oldX, oldY, tmpX, tmpY);
      curImg=this.createImage(this.getWidth(),this.getHeight());
  }


  /**
     * Clears all the components from draw-pad and user can start from new
     * @param a
     * @param cnc_a 
     */  
  public final void clear(counts a,connections cnc_a)
  {
      a.reset();
      cnc_a.clear();
      this.removeAll();
      this.reset();
      repaint();
  }
  
  /**
   * reset to default so that we can add other component
   */
  public void reset(  )
  {
      oldX=oldY=tmpX=tmpY=currentX=currentY=0;
      desn=true;
      this.default_mouse();
      if(!locked && !Pad_connections.getLine_logs().isEmpty())
      {
          System.out.println("REMOVED");
          Pad_connections.remove_line_i(Pad_connections.getLine_logs().size()-1);
          //make the previous line as locked i.e blue-line
          locked=true;
      }
      repaint();
  }


  /**
   * selecting 
   * @param a 
   */
  public void select(final counts a, connections b)
  {
      
      this.reset();
      Pad_counts=a;
      Pad_connections=b;
      this.addMouseListener(slct);
      this.addMouseMotionListener(slct);
  }


  /**
   * Change the location of i-th component to p Point
   * @param i
   * @param p 
   */
  public void change (int i,Point p)
  {
      //System.out.println(p);
      this.getComponent(i).setLocation(p);
      Pad_connections.update(Pad_counts);
      repaint();
  }
  
  /**
   * sets mouse to the default condition
   */
  public void default_mouse(  )
  {
      this.set_default_cursor();
      this.remove_all_mouse_listeners();
  }


  /**
   * Removes all MouseListeners and MouseMotionListeners 
   */
  public void remove_all_mouse_listeners()
  {
      for(MouseListener a : this.getMouseListeners())
          this.removeMouseListener(a);
      for(MouseMotionListener a : this.getMouseMotionListeners())
          this.removeMouseMotionListener(a);
  }
  
  /**
   * this enables user to add new component of compnentType in the draw-pad
   * @param cmpType
   * @param a the counts of this instance of MainEditor
   */
  public void add_componentType( final componentType cmpType, final counts a )
  {
      this.reset();
      this.Pad_counts=a;
      
      MouseListener add_component= new MouseAdapter(){
          @Override
          public void mouseClicked(MouseEvent e)
          {
              
                    update(cmpType,e.getPoint(),a,0);
          }
      };
      this.addMouseListener(add_component);
      //System.out.println("component");
  }


  /**
   * enables user to add wire or line segments in the draw-pad
   * @param a 
   * @param b needed to check the vicinity of component
   */
  public void wire(final connections a, final counts b)
  {
      this.reset();
      Pad_connections=a;
      Pad_counts=b;
      
      MouseListener line_connct = new MouseAdapter(){
          int m=0;//to mark the first entry i.e first line-segment of the current_line
          line current_line=null;
          iopoints temp;
          boolean last_point=false,frst_point=true;//mark that any of the last point is reached
          int [] ret = new int [2];
            @Override            
            public void mousePressed(MouseEvent e)
            {
                mousedragActv=false;
                if(desn && e.getButton()==MouseEvent.BUTTON1)
                {
                    temp=new iopoints();
                    if(vicinityCheck(e.getPoint(),Pad_counts,temp,ret))
                    {
                        //System.out.println("SuccessP"+temp+","+ret[0]+","+ret[1]);
                        oldX = temp.getLocation().x;
                        oldY = temp.getLocation().y;
                        current_line=new line();
                        //current_line.add_line_seg(temp.getLocation());
                        //here current_line is added temporarily by accessing getLine_logs()
                        //so current_line is not getting a valid id, id remains (0)
                        current_line.set_start_param(ret[0], ret[1],b);
                        Pad_connections.getLine_logs().add(current_line);
                        m++;//mark that it's not the first line-segment of current_line
                        desn=false;
                        //make the current_line unlocked i.e red-line
                        locked=false;
                        //System.out.println(oldX+","+oldY+"--"+currentX+","+currentY+"=Press"+','+","+locked);
                        //System.out.println("P"+Pad_connections.getLine_logs().size());
                        frst_point=false;//fisrt point is covered
                    }
                }
            }
            
            //At Mouse-Release we draw the line actually
            @Override
            public void mouseReleased (MouseEvent e) 
            {
                mousedragActv=false;
                currentX=e.getX();
                currentY=e.getY();
                if(desn==false && e.getButton()==MouseEvent.BUTTON1)
                {
                    //if it's not the first then remove the last-added(unlocked) line
                    if(m>0/* && !frst_point*/)
                    {
                        //System.out.println("RREMOVED");
                        Pad_connections.remove_line_i(Pad_connections.getLine_logs().size()-1);
                    }
                    iopoints temp1= new iopoints();
                    if(m!=0)
                    {
                        
                        if(vicinityCheck(e.getPoint(),Pad_counts,temp1,ret) && temp1.equals(temp))
                        {
                            frst_point=true;
                            //here current_line is added temporarily by accessing getLine_logs()
                            //so current_line is not getting a valid id, id remains (0)
                            Pad_connections.getLine_logs().add(current_line);
                            //System.out.println("R+frst");
                            if(m>1) taskbr.setText("First and last point of Wire or line can't be same.");
                            else taskbr.setText("Choose a mark point to end the Wire or line");
                        }
                        else if(vicinityCheck(e.getPoint(),Pad_counts,temp,ret))
                        {
                            frst_point=false;
                            //current_line.add_line_seg(temp.getLocation());
                            //System.out.println("SuccessR"+temp+","+ret[0]+","+ret[1]);
                            //here only, current_line is added permanently and gets a valid id
                            current_line.set_end_param(ret[0], ret[1],b);
                            Pad_connections.add_line(current_line, Pad_counts);
                            //make this line locked i.e blue-line
                            locked=true;
                            //System.out.println("C"+Pad_connections.getLine_logs().size());
                            //repaint to see the effect
                            repaint();
                            taskbr.setText("Wire is added.");
                            //reset so that to add a new wire or line user have to click the button again
                            reset();
                        }
                        else
                        {
                            current_line.add_line_seg(e.getPoint());
                            oldX=currentX;
                            oldY=currentY;
                            //replace with the new
                            //here current_line is added temporarily by accessing getLine_logs()
                            //so current_line is not getting a valid id, id remains (0)
                            Pad_connections.getLine_logs().add(current_line);
                            //make this current_line unlocked i.e red-line
                            locked=false;
                            frst_point=false;
                            taskbr.setText("Choose a mark point to end the Wire or line");
                        }
                        m++;
                    }
                    //System.out.println("R"+Pad_connections.getLine_logs().size());
                    desn=false;
                }
                //System.out.println(oldX+","+oldY+"--"+currentX+","+currentY+"=Release,"+locked);
                repaint();
            }
    };
      
    /**
       * This listener is to show the drawing line or wire when dragged
       */
    MouseMotionListener line_show= new MouseMotionAdapter(){
            @Override
            public void mouseDragged(MouseEvent e)
            {
                if(desn==false)
                {
                    tmpX = e.getX();
                    tmpY = e.getY();
                    mousedragActv=true;
                }
                repaint();
            }
            @Override
            public void mouseMoved(MouseEvent e)
            {
                mousedragActv=false;
            }
    };    
      this.addMouseListener(line_connct);
      this.addMouseMotionListener(line_show);
  }
  
  /**
   * search among the component in a
   * @param a search in a
   * @param ret_point just like isVicinity in component
   * @return just like isVicinity in component
   */
  public boolean vicinityCheck(Point p,counts a, iopoints ret_point,int [] ret)
  {
      //int [] ret = new int[2];
      for(component cntr : a.getComponent_list())
      {
          if(cntr.isVicinity(p, ret_point,ret))
          {
              ret_point.change(ret_point);
              //System.out.println("W"+ret[0]+","+ret[1]);
              return true;
          }
      }
      ret_point=null;
      return false;
  }

  /**
   * Loads the file with path
   * @param path the required path of the file to be loaded
   * @param a 
   */
  public void load( String path, counts a, connections cnc_a )
  {
      int cmp_id = 0,x_pos = 0,y_pos = 0,current_comp_type_id = 0;
      int is_wire=1;//If the package needed for the file is not installed, then wires will not be added
      //is_wire=1 => wires will be added, is_wire=0 => wires will not be added
      String pkg_type = null;
      Pad_counts=a;
      Pad_connections=cnc_a;
      // create a DOMParser
      DOMParser parser=new DOMParser();
        try {
            parser.parse(path);
        } catch (SAXException ex) {
            Logger.getLogger(Pad_Draw.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Pad_Draw.class.getName()).log(Level.SEVERE, null, ex);
        }
      // get the DOM Document object
     Document doc=parser.getDocument();
     
     NodeList nodelist = doc.getElementsByTagName("comp");
     for(int i=0;i<nodelist.getLength();i++)
     {
         //getting single component
         Node node = nodelist.item(i);
         
         //getting the id
         NamedNodeMap id = node.getAttributes();
         cmp_id=Integer.parseInt(id.getNamedItem("id").getNodeValue());
         //System.out.println(cmp_id);
         
         // get the child nodes of a single node
         NodeList childnodelist = node.getChildNodes();
         //getting the package & componentType
         for(int j=0;j<childnodelist.getLength();j++)
         {
             Node childnode = childnodelist.item(j);
             Node textnode = childnode.getFirstChild();
             String childnodename = childnode.getNodeName();
             //System.out.println(childnodename);
             if(childnodename.equals("comp_type_id"))
             {
                 current_comp_type_id=Integer.parseInt(textnode.getNodeValue().trim());
                 //System.out.println(current_comp_type_id);
             }
             else if(childnodename.equals("pkg_name"))
             {
                 pkg_type=(String)textnode.getNodeValue().trim();
                 //System.out.println(pkg_type);
             }
             else if(childnodename.equals("position"))
             {
                 NodeList children=childnode.getChildNodes();
                 for(int k=0;k<children.getLength();k++)
                 {
                     Node pos_child = children.item(k);
                     Node text_pos = pos_child.getFirstChild();
                     String pos_name = pos_child.getNodeName();
                     if(pos_name.equals("x"))
                     {
                         x_pos=Integer.parseInt(text_pos.getNodeValue().trim());
                         //System.out.println(x_pos);
                     }
                     else if(pos_name.equals("y"))
                     {
                         y_pos=Integer.parseInt(text_pos.getNodeValue().trim());
                         //System.out.println(y_pos);
                     }
                 }
             }
         }
         try
         {
             int j=0;
             for(j=0;j<a.getPackage_list().size();j++)
                 if(a.getPackage_list().get(j).getName().equals(pkg_type))
                     break;
             if(j>=0 || j<a.getPackage_list().size())
             {
                 componentType current_comp_type;
                 current_comp_type=a.getPackage_list().get(j).getComponentType_list().get(current_comp_type_id-1);
                 this.update(current_comp_type,new Point(x_pos,y_pos),a,cmp_id);
             }
             else
             {
                 JOptionPane.showMessageDialog(this, "The package needed for the components is not installed.", path,JOptionPane.ERROR_MESSAGE);
                 is_wire=0;
             }
         }
         catch(java.lang.IndexOutOfBoundsException ex)
         {
             System.out.println(ex);
             JOptionPane.showMessageDialog(this, "The package needed for the components is not installed.", path,JOptionPane.ERROR_MESSAGE);
             is_wire=0;
             break;
         }
     }
     
     int wire_id=0,strt_cmp_id=0,strt_mrk_pt=0,end_cmp_id=0,end_mrk_pt=0;
     if(is_wire==1)
     {
         try
         {
             nodelist = doc.getElementsByTagName("wire");
             for(int i=0;i<nodelist.getLength();i++)
             {
                 line crnt_line = new line();
                 //getting single component
                 Node node = nodelist.item(i);

                 //getting the id
                 NamedNodeMap id = node.getAttributes();
                 wire_id=Integer.parseInt(id.getNamedItem("id").getNodeValue());
                 //System.out.println("Wire"+wire_id);

                 // get the child nodes of a single node
                 NodeList childnodelist = node.getChildNodes();
                 //getting the package & componentType
                 for(int j=0;j<childnodelist.getLength();j++)
                 {
                     Node childnode = childnodelist.item(j);
                     Node textnode = childnode.getFirstChild();
                     String childnodename = childnode.getNodeName();
                     //System.out.println(childnodename);
                     if(childnodename.equals("start"))
                     {
                         NodeList children=childnode.getChildNodes();
                         for(int k=0;k<children.getLength();k++)
                         {
                             Node pos_child = children.item(k);
                             Node text_pos = pos_child.getFirstChild();
                             String pos_name = pos_child.getNodeName();
                             if(pos_name.equals("comp_id"))
                             {
                                 strt_cmp_id=Integer.parseInt(text_pos.getNodeValue().trim());
                                 //System.out.println(strt_cmp_id);
                             }
                             else if(pos_name.equals("mark_point"))
                             {
                                 strt_mrk_pt=Integer.parseInt(text_pos.getNodeValue().trim());
                                 //System.out.println(strt_mrk_pt);
                             }
                         }
                         crnt_line.set_start_param(strt_cmp_id, strt_mrk_pt,a);
                     }
                     else if(childnodename.equals("inter_point"))
                     {
                         int x = 0,y = 0;
                         NodeList mark_children=childnode.getChildNodes();
                         for(int k=0;k<mark_children.getLength();k++)
                         {
                             Node pos_node = mark_children.item(k);
                             Node pos_val = pos_node.getFirstChild();
                             String pos_name = pos_node.getNodeName();
                             if(pos_name.equals("position"))
                             {
                                 NodeList children=pos_node.getChildNodes();
                                 for(int l=0;l<children.getLength();l++)
                                 {
                                     Node pos_child = children.item(l);
                                     Node text_child_pos = pos_child.getFirstChild();
                                     String pos_child_name = pos_child.getNodeName();
                                     if(pos_child_name.equals("x"))
                                     {
                                         x=Integer.parseInt(text_child_pos.getNodeValue().trim());
                                     }
                                     else if(pos_child_name.equals("y"))
                                     {
                                         //System.out.println(Integer.parseInt(text_child_pos.getNodeValue().trim()));
                                         y=Integer.parseInt(text_child_pos.getNodeValue().trim());
                                     }
                                 }
                                 crnt_line.add_line_seg(new Point(x,y));
                             }
                         }
                     }
                     else if(childnodename.equals("end"))
                     {
                         NodeList children=childnode.getChildNodes();
                         for(int k=0;k<children.getLength();k++)
                         {
                             Node pos_child = children.item(k);
                             Node text_pos = pos_child.getFirstChild();
                             String pos_name = pos_child.getNodeName();
                             if(pos_name.equals("comp_id"))
                             {
                                 end_cmp_id=Integer.parseInt(text_pos.getNodeValue().trim());
                                 //System.out.println(end_cmp_id);
                             }
                             else if(pos_name.equals("mark_point"))
                             {
                                 end_mrk_pt=Integer.parseInt(text_pos.getNodeValue().trim());
                                 //System.out.println(end_mrk_pt);
                             }
                         }
                         crnt_line.set_end_param(end_cmp_id, end_mrk_pt,a);
                     }
                 }
                 crnt_line.update_end_point_params(a);
                 Pad_connections.add_line(crnt_line, Pad_counts);
                 repaint();
             }
         }
         catch(java.lang.IndexOutOfBoundsException ex)
         {
             JOptionPane.showMessageDialog(this, "Error in XML Parsing. Check your XML Circuit-file", path,JOptionPane.ERROR_MESSAGE);
         }
     }
  }


  /**
   * updates the Pad with a new component added on the pad 
   * Also adds the component to the counts
   */
  private void update(componentType cmpType,Point p,counts a,int id)
  {
      JLabel new_label;
      component current=new component(cmpType,p,a,id);
      new_label=new JLabel(current.getLabel().getIcon());
      //System.out.println(current.getPosition().x+","+current.getPosition().y+","+current.getType().getWidth()+","+current.getType().getHeight());
      new_label.setBounds(current.getPosition().x, current.getPosition().y, current.getType().getWidth(), current.getType().getHeight());
      this.add(new_label);
      //System.out.println("Here");
      repaint();
  }
  
  
  /**
   * Sets the background grids
   * @param        type_of_view depending of 'type_of_view', the grids will be drawn.
   * 0 - no grid, 1 to 3 - small to large
   */
  public void view( int type_of_view ) throws IOException
  {
      switch(type_of_view)
      {
          case 0:
              bgImage=null;
              break;
          case 1:
              bgImage=ImageIO.read(new File("sml_grid.jpg"));
              bgImage=bgImage.getScaledInstance(this.getWidth(), this.getHeight(),Image.SCALE_FAST);
              break;
          case 2:
              bgImage=ImageIO.read(new File("med_grid.jpg"));
              bgImage=bgImage.getScaledInstance(this.getWidth(), this.getHeight(),Image.SCALE_FAST);
              break;
          case 3:
              bgImage=ImageIO.read(new File("lrg_grid.jpg"));
              bgImage=bgImage.getScaledInstance(this.getWidth(), this.getHeight(),Image.SCALE_FAST);
              break;
          default:
              bgImage=null;
      }
      repaint();
  }
  
  
  /**
   * sets the move cursor
   */
   public void set_move_cursor()
    {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.MOVE_CURSOR));
    }
    
   /**
    * sets the default mouse cursor
    */
    public void set_default_cursor()
    {
        this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }

    void showOutputs(counts a, connections cnc_a)
    {
        this.reset();
        for(component x:a.getComponent_list())
        {
            for(int i = 0; i < x.getMarkPointLabels().length; i++)
            {
                //x.getMarkPointLabels()[i].setBounds(i, i, i, i);
                this.add(x.getMarkPointLabels()[i]);
                //this.getcomp
            }
        }
        repaint();
    }
    
    void hideOutputs(counts a, connections cnc_a)
    {
        this.reset();    
        for(component x:a.getComponent_list())
        {
            for(int i = 0; i < x.getMarkPointLabels().length; i++)
            {
                //x.getMarkPointLabels()[i].setBounds(i, i, i, i);
                this.remove(this.getComponentCount()-1);
                //this.getcomp
            }
        }
        repaint();
    }
    
}