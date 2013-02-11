package mainpackage;

import circuits.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.apache.xerces.jaxp.DocumentBuilderFactoryImpl;
import org.apache.xml.serialize.OutputFormat;
import org.apache.xml.serialize.XMLSerializer;
import org.w3c.dom.*;


/**
 * Class MainEditor which contains main method also
 */
public class MainEditor {

  //
  // Fields
  //

  private JFrame frame;
  private Container content;
  private JTextArea taskbar;
  private JPanel panel;
  private Pad_Draw drawPad;
  
  /**
   * the instance of the frame is saved or not
   * true - saved
   * false - not saved
   */
  public boolean saved;
  
  /**
   * the path where the instance is saved
   */
  String path;
  
  //
  // Constructors
  //
  /**
   * 
   */
  public MainEditor ()
  {
      this.initGUI();
  };

  //
  // Accessor methods
  //
  
  /**
   * @param args 
   */
  public static void main(String[] args)
  {
      MainEditor theApp = new MainEditor();
  }
  
    /**
   * Set the value of frame
   * @param newVar the new value of frame
   */
  private void setFrame (final counts a, final connections cnc_a) {
    //Creates a frame with a title of "Circuit Editor"  
    frame = new JFrame("SRSCKT - Circuit Editor");
    
    JMenu menu;
    JMenuBar menuBar;
    JMenuItem menuItem;
    /*******************Menu Bar*****************/
    menuBar= new JMenuBar();//Creating MenuBar

    //Build the first (Main)-menu.
    menu = new JMenu("File");
    menu.setMnemonic(KeyEvent.VK_F);
    menuBar.add(menu);
        
    //Sub-menu of "File"
    //a group of JMenuItems
    menuItem = new JMenuItem("New",KeyEvent.VK_N);
    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N,InputEvent.CTRL_MASK));
    menuItem.getAccessibleContext().setAccessibleDescription("A New Window");
    // File -> New || ActionListener
    ActionListener new_window=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            MainEditor new_wndw=new MainEditor();
        };
    };
    menuItem.addActionListener(new_window);
    menu.add(menuItem);
        
    menuItem = new JMenuItem("Open",KeyEvent.VK_O);
    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O,InputEvent.CTRL_MASK));
    menuItem.getAccessibleContext().setAccessibleDescription("Open previously saved file");
    // File -> Open || ActionListener
    ActionListener open=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(a.is_empty())
            {
                String tempPath;
                JFileChooser opn_c = new JFileChooser();
                FileFilter xml = new FileNameExtensionFilter("XML Files", "xml");
                opn_c.setFileFilter(xml);
                    int rVal = opn_c.showOpenDialog(panel);
                    if (rVal == JFileChooser.APPROVE_OPTION)
                    {
                        tempPath = opn_c.getSelectedFile().getPath();
                        drawPad.load(tempPath, a, cnc_a);
                        setSaved(true);
                        setPath(tempPath);
                        changeTitle(new File(tempPath).getName());
                    }
            }
            else
                JOptionPane.showMessageDialog(frame, "Your Pad is not empty. Open a new window and then try.", "Error", JOptionPane.ERROR_MESSAGE);
        };
    };
    menuItem.addActionListener(open);
    menu.add(menuItem);
    
    menuItem = new JMenuItem("Save",KeyEvent.VK_S);
    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S,InputEvent.CTRL_MASK));
    menuItem.getAccessibleContext().setAccessibleDescription("Save a file");
    // File -> Save || ActionListener
    ActionListener save=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(!saved)
            {
                String []extns={"xml"};
                String path_ret=save_listener("XML Files",extns);
                if(path_ret!=null)
                    try {
                        save(path_ret,a,cnc_a);
                    } catch (ParserConfigurationException ex) {
                        Logger.getLogger(MainEditor.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IOException ex) {
                        Logger.getLogger(MainEditor.class.getName()).log(Level.SEVERE, null, ex);
                    }
            }
            else
            {
                File f=new File(path);
                try {
                    f.createNewFile();
                } catch (IOException ex) {
                    Logger.getLogger(MainEditor.class.getName()).log(Level.SEVERE, null, ex);
                }
                try {
                    save(path, a, cnc_a);
                } catch (ParserConfigurationException ex) {
                    Logger.getLogger(MainEditor.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(MainEditor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
    };
    menuItem.addActionListener(save);
    menu.add(menuItem);
    
    menuItem = new JMenuItem("Save As",KeyEvent.VK_A);
    menuItem.getAccessibleContext().setAccessibleDescription("Save in a new file");
    // File -> Save || ActionListener
    ActionListener saveas=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String []extns={"xml"};
            String path_ret=save_listener("XML Files",extns);
            if(path_ret!=null)
                try {
                    save(path_ret,a,cnc_a);
                } catch (ParserConfigurationException ex) {
                    Logger.getLogger(MainEditor.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(MainEditor.class.getName()).log(Level.SEVERE, null, ex);
                }
        };
    };
    menuItem.addActionListener(saveas);
    menu.add(menuItem);
    
    menuItem = new JMenuItem("Export",KeyEvent.VK_E);
    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E,InputEvent.CTRL_MASK));
    menuItem.getAccessibleContext().setAccessibleDescription("Export as picture");
    // File -> Export || ActionListener
    ActionListener export=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String []extns={"jpg","jpeg"};
            String path_ret=save_listener("JPEG Picture Files",extns);
            if (path_ret!=null)
            {
                try {
                        BufferedImage bi = Get_Component_Image(drawPad,drawPad.getBounds());
                        try {
                            System.out.println(ImageIO.write(bi, "jpg", new File(path_ret)));
                        } catch (IOException ex) {
                            Logger.getLogger(MainEditor.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (IOException ex) {
                    Logger.getLogger(MainEditor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        };
    };
    menuItem.addActionListener(export);
    menu.add(menuItem);
    
    menuItem = new JMenuItem("Exit",KeyEvent.VK_X);
    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X,InputEvent.CTRL_MASK));
    menuItem.getAccessibleContext().setAccessibleDescription("Close the window");
    // File -> Open || ActionListener
    ActionListener exit=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            frame.dispose();
            };
    };
    menuItem.addActionListener(exit);
    menu.add(menuItem);
     
    menu = new JMenu("Edit");
    menu.setMnemonic(KeyEvent.VK_E);
    menuBar.add(menu);
        
    menu = new JMenu("View");
    menu.setMnemonic(KeyEvent.VK_V);
    menuBar.add(menu);
    
    ButtonGroup view_btn_grp=new ButtonGroup();
    
    //Sub-menu of "View"
    //a group of JMenuItems
    menuItem = new JRadioButtonMenuItem("None");
    menuItem.getAccessibleContext().setAccessibleDescription("No Grid");
    // File -> New || ActionListener
    ActionListener no_grid=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
                try {
                    drawPad.view(0);
                } catch (IOException ex) {
                    Logger.getLogger(MainEditor.class.getName()).log(Level.SEVERE, null, ex);
                }
        };
    };
    view_btn_grp.add(menuItem);
    menuItem.addActionListener(no_grid);
    menu.add(menuItem);
    
    menuItem = new JRadioButtonMenuItem("Small Grid");
    menuItem.getAccessibleContext().setAccessibleDescription("Small Grid View");
    // File -> New || ActionListener
    ActionListener sml_grid=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
                try {
                    drawPad.view(1);
                } catch (IOException ex) {
                    Logger.getLogger(MainEditor.class.getName()).log(Level.SEVERE, null, ex);
                }
        };
    };
    view_btn_grp.add(menuItem);
    menuItem.addActionListener(sml_grid);
    menu.add(menuItem);
    
    menuItem = new JRadioButtonMenuItem("Medium Grid");
    menuItem.getAccessibleContext().setAccessibleDescription("Medium Grid View");
    // File -> New || ActionListener
    ActionListener mdm_grid=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
                try {
                    drawPad.view(2);
                } catch (IOException ex) {
                    Logger.getLogger(MainEditor.class.getName()).log(Level.SEVERE, null, ex);
                }
        };
    };
    view_btn_grp.add(menuItem);
    menuItem.addActionListener(mdm_grid);
    menu.add(menuItem);
    
    menuItem = new JRadioButtonMenuItem("Large Grid");
    menuItem.getAccessibleContext().setAccessibleDescription("Large Grid View");
    // File -> New || ActionListener
    ActionListener lrg_grid=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
                try {
                    drawPad.view(3);
                } catch (IOException ex) {
                    Logger.getLogger(MainEditor.class.getName()).log(Level.SEVERE, null, ex);
                }
        };
    };
    view_btn_grp.add(menuItem);
    menuItem.addActionListener(lrg_grid);
    menu.add(menuItem);
      
    menu = new JMenu("Tool");
    menu.setMnemonic(KeyEvent.VK_T);
    menuBar.add(menu);
      
    //a group of JMenuItems
    menuItem = new JMenuItem("Install New Package",KeyEvent.VK_I);
    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,InputEvent.CTRL_MASK));
    menuItem.getAccessibleContext().setAccessibleDescription("Add a new type of circuit component through a Package");
    ActionListener new_package=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String tempPath;
            JFileChooser i_n_p = new JFileChooser();//i_n_p => install new package
            FileFilter b = new FileNameExtensionFilter("Zip Files", "zip");
            i_n_p.setFileFilter(b);
            int rVal = i_n_p.showOpenDialog(panel);
            if (rVal == JFileChooser.APPROVE_OPTION)
            {
                frame.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
                tempPath = i_n_p.getSelectedFile().getPath();
                package_cls new_pkg= new package_cls(tempPath,a,0);//sysCall is 0, as zipped file will be opened
                add_componentType_buttons(new_pkg,a);
                frame.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                taskbar.setText("Package is installed");
            }
        };
    };
    menuItem.addActionListener(new_package);
    menu.add(menuItem);
    
    menuItem = new JMenuItem("Uninstall Package",KeyEvent.VK_U);
    menuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U,InputEvent.CTRL_MASK));
    menuItem.getAccessibleContext().setAccessibleDescription("Uninstall a previously installed package");
    ActionListener unInstl=new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            //It means a JDialog with frame as parent or owner and 'true' assures that
            JDialog chooser = new JDialog(frame,"Choose from Packages",true);
            Container chooser_content = chooser.getContentPane();
            List pkg_list = new List(a.noUserPkg(),true);
            for(package_cls x:a.getPackage_list())
            {
                if(!x.getIsSysPkg())
                    pkg_list.add(x.getName());
            }
            chooser_content.setLayout(new FlowLayout());
            chooser_content.add(pkg_list);
            chooser.setType(Window.Type.POPUP);
            chooser.setAlwaysOnTop(true);
            chooser.setSize(200, 200);
            chooser.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            chooser.setResizable(false);
            chooser.setVisible(true);
        };
    };
    menuItem.addActionListener(unInstl);
    menu.add(menuItem);
       
    menu = new JMenu("Help");
    menu.setMnemonic(KeyEvent.VK_H);
    menuBar.add(menu);
       
    frame.setJMenuBar(menuBar);
    /*******************End of Menu Bar*****************/  
    this.setContent(a,cnc_a);
    //sets the size of the frame
    frame.setSize(1000, 700);
    //frame.setLayout(new BoxLayout(frame, BoxLayout.Y_AXIS));
    //makes it so that you can close different instance indiviually(if you've pressed "File->New")
    frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    //so that u can't change the dafault size
    //frame.setResizable(false);
    //makes it so you can see it
    frame.setVisible(true);
    
  }

  /**
   * Get the value of frame
   * @return the value of frame
   */
  private JFrame getFrame ( ) {
    return frame;
  }

  /**
   * Set the value of content
   * @param newVar the new value of content
   */
  private void setContent (final counts a,final connections cnc_a) {
    //Creates a new container  
    content = frame.getContentPane();
    //sets the layout
    content.setLayout(new BorderLayout());
    
    this.setTaskbar();//sets the taskbar
    //adding the taskbar to the bottom-part
    content.add(taskbar,BorderLayout.SOUTH);
    
    this.setDraw_pad(a,cnc_a,this.getTaskbar());//sets the drawPad
    JScrollPane Padscroller = new JScrollPane();
    Padscroller.setWheelScrollingEnabled(true);
    Padscroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
    Padscroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_ALWAYS);
    Padscroller.setPreferredSize(new Dimension(200, 100));
    //Padscroller.setMinimumSize(new Dimension(200, 100));
    //Padscroller.setMaximumSize(new Dimension(200, 100));
    Padscroller.setViewportView(drawPad);
    content.add(Padscroller, BorderLayout.CENTER);
    
    this.setPanel(a,cnc_a);//sets the panel
    JScrollPane scroller = new JScrollPane(panel);
    scroller.setWheelScrollingEnabled(true);
    scroller.setPreferredSize(new Dimension(125, 80));
    scroller.setMinimumSize(new Dimension(125, 80));
    scroller.setMaximumSize(new Dimension(125, 80));
    //sets the scroller to the west portion
    content.add(scroller, BorderLayout.WEST);
    //content.add(panel, BorderLayout.WEST);
  }

  /**
   * Get the value of content
   * @return the value of content
   */
  private Container getContent ( ) {
    return content;
  }

  /**
   * Set the value of draw_pad
   * @param newVar the new value of draw_pad
   */
  private void setDraw_pad (counts a, connections cnc_a, JTextArea task) {
    //creates a new PadDraw
    drawPad = new Pad_Draw(a,cnc_a,task);
  }

  /**
   * Get the value of draw_pad
   * @return the value of draw_pad
   */
  private Pad_Draw getDraw_pad ( ) {
    return drawPad;
  }

  /**
   * Set the value of taskbar
   * @param newVar the new value of taskbar
   */
  private void setTaskbar ( ) {
    /*******************Task Bar*****************/
    taskbar = new JTextArea();
    taskbar.setVisible(true);
    taskbar.setBackground(Color.lightGray);
    taskbar.setEditable(false);
    /*******************End of Task Bar*****************/
  }

  /**
   * Get the value of taskbar
   * @return the value of taskbar
   */
  private JTextArea getTaskbar ( ) {
    return taskbar;
  }

  /**
   * Set the value of panel
   * @param newVar the new value of panel
   */
  private void setPanel (final counts a,final connections cnc_a) {
      
    //creates a JPanel
    panel = new JPanel();
    panel.setLayout(new ModifiedFlowLayout());
           
    JButton clearButton = new JButton("CLEAR");
    clearButton.addActionListener(new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            drawPad.clear(a,cnc_a);
            taskbar.setText(null);
        }
    });
    
    JButton wireButton = new JButton("WIRE ");
    wireButton.addActionListener(new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            drawPad.wire(cnc_a, a);
            taskbar.setText("Add Wire by Mouse click.");
        }
    });
    
    JButton slctButton= new JButton("SELECT");
    slctButton.addActionListener(new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            drawPad.select(a,cnc_a);
            taskbar.setText("Press & Drag to move component");
        }
    });
    
    JButton showOutputButton= new JButton("Show Output");
    showOutputButton.addActionListener(new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            drawPad.showOutputs(a, cnc_a);
            taskbar.setText("Click Hide Output, otherwise it will behave unpredictably");
        }
    });
    
    JButton hideOutputButton= new JButton("Hide Output");
    hideOutputButton.addActionListener(new ActionListener()
    {
        @Override
        public void actionPerformed(ActionEvent e)
        {
            drawPad.hideOutputs(a, cnc_a);
            taskbar.setText("Hide the Circuit component Expressions");
        }
    });
    
    //adds the buttons to the panel
    panel.add(clearButton);
    panel.add(wireButton);
    panel.add(slctButton);
    panel.add(showOutputButton);
    panel.add(hideOutputButton);
    
    //Default System Packages have some componentType-s. Those buttons are installed
    for(package_cls x:a.getPackage_list())
    {
        this.add_componentType_buttons(x, a);
    }
  }

  /**
   * Get the value of panel
   * @return the value of panel
   */
  private JPanel getPanel ( ) {
    return panel;
  }
  
  /**
   * Sets the value of saved and also save the instance
   * @param aFlag the value of saved
   */
  public void setSaved(boolean aFlag)
  {
      saved=aFlag;
  }

  /**
   * Get the value of saved
   * @return the value of saved
   */
  public boolean getSaved()
  {
      return saved;
  }
  
  /**
   * 
   * @param newVar
   */
  public void setPath(String newVar)
  {
      path=newVar;
  }
  
  /**
   * 
   * @return
   */
  public String getPath()
  {
      return path;
  }
  
  //
  // Other methods
  //

  /**
   * Initialize the GUI
   */
  private void initGUI(  )
  {
      counts theApp_new_counts = new counts();
      String sysPath=System.getProperty("user.home")+File.separator+"SRSCKT"+File.separator+"pkg";
      File sysDir=new File(sysPath);
      //if our system folder doesn't exist it creates it
      if(!sysDir.exists())
          sysDir.mkdirs();
      String [] SysPkgs=sysDir.list();
      for(String x:SysPkgs)
      {
          System.out.println(x);
          package_cls defaultPkgs= new package_cls(x,theApp_new_counts,1);//sysCall is 1 as System Packages will be installed
      }
      connections theApp_new_connections = new connections();
      this.setFrame(theApp_new_counts,theApp_new_connections);
      this.setPath(null);
      this.setSaved(false);//default false
  }
  
  /**
   * Adds the componentType buttons to the Panel of Main Window.
   * @param pkg_name whose componentType buttons will be added
   * @param a needed for the addition of the ActionListener of respective buttons and calling method of Pad_Draw
   */
  public void add_componentType_buttons(package_cls pkg_name,final counts a)
  {
      int i;
      JLabel pkg_hdng=new JLabel(pkg_name.getName());
      panel.add(pkg_hdng);
      for(i=0;i<pkg_name.getComponentType_list().size();i++)
      {
          final componentType cmp_Types=pkg_name.getComponentType_list().get(i);
          int btnHeight,btnWidth;
          double scale=0.5;
          btnWidth=(int)(scale*(double)cmp_Types.getWidth());
          btnHeight=(int)(scale*(double)cmp_Types.getHeight());
          ImageIcon myIcon=new ImageIcon(cmp_Types.getType_Img());
          
          BufferedImage bi = new BufferedImage(btnWidth,btnHeight,BufferedImage.TYPE_INT_ARGB);
          Graphics2D g = bi.createGraphics();
          g.scale(scale,scale);
          myIcon.paintIcon(null,g,0,0);
          g.dispose();

          JButton strctButton = new JButton(new ImageIcon(bi));
          strctButton.addActionListener(new ActionListener(){
              @Override                       
              public void actionPerformed(ActionEvent e)
              {
                  drawPad.add_componentType(cmp_Types,a);
                  taskbar.setText("Click to add a component");
              }
          });
          panel.add(strctButton);
      }
      panel.validate(); //Updates the Panel
  }
  
  /**
   * Removes the buttons in 'pkg_name" package.
   * @param        pkg_name
   */
  public void remove_componentType_buttons( package_cls pkg_name )
  {
      
  }

     /**
      * concatinate name to the Title Bar
      * @param name the string to be placed in the title bar
      */
     private void changeTitle(String name)
     {
         frame.setTitle(name+" - SRSCKT - Circuit Editor");
     }
     
   /**
   * 
   * @param path_ret where the file will be saved
   * @param a getting the information of current status of the editor
      * @param cnc_a getting the information of current status of the editor
      * @throws ParserConfigurationException 
      * @throws IOException  
   */
  public void save( String tempPath, counts a, connections cnc_a ) throws ParserConfigurationException, IOException
  {
        try {
            DocumentBuilderFactory  dbf = DocumentBuilderFactoryImpl.newInstance();
            //get an instance of builder
            DocumentBuilder db = dbf.newDocumentBuilder();
            //create an instance of DOM
            Document dom = db.newDocument();
            
            Element el_root=dom.createElement("circuit");
            dom.appendChild(el_root);
            
            //Root of the components
            Element cmp_root=dom.createElement("components");
            el_root.appendChild(cmp_root);
            for(component x:a.getComponent_list())
            {
                Element cmpEle=this.createCmpEle(x, dom);
                cmp_root.appendChild(cmpEle);
            }
            
            //Root of the components
            Element wire_root=dom.createElement("wires");
            el_root.appendChild(wire_root);
            for(line x:cnc_a.getLine_logs())
            {
                Element wireEle = null;
                if(x.getId()>0)
                {
                    wireEle=this.createWireEle(x, dom);
                    wire_root.appendChild(wireEle);
                }
            }
            
            OutputFormat format=new OutputFormat();
            XMLSerializer serializer = new XMLSerializer(new FileOutputStream(new File(tempPath)), format);
            
            serializer.serialize(dom);
            this.changeTitle(new File(tempPath).getName());
            this.setSaved(true);
            this.setPath(tempPath);
            taskbar.setText("File is saved successfully.");
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Pad_Draw.class.getName()).log(Level.SEVERE, null, ex);
        }
  }

  /**
   * Creates an Element(or Node in XML in Document d) for single component
   * @param x the component for which Element will be created
   * @param d Where the element will be added
   * @return the Element(for XML) created from component x
   */
  private Element createCmpEle(component x,Document d)
  {
      Element cmp_el = d.createElement("comp");
      cmp_el.setAttribute("id", String.valueOf(x.getId()));
      
      Element type_el = d.createElement("comp_type_id");
      Text type_txt = d.createTextNode(String.valueOf(x.getType().getId()));
      type_el.appendChild(type_txt);
      cmp_el.appendChild(type_el);
      
      Element pkg_el = d.createElement("pkg_name");
      Text pkg_txt = d.createTextNode(x.getType().getType_pkg_name());
      pkg_el.appendChild(pkg_txt);
      cmp_el.appendChild(pkg_el);
      
      Element pos_el = d.createElement("position");
      
      Element x_el = d.createElement("x");
      Text x_txt = d.createTextNode(String.valueOf(x.getPosition().x));
      x_el.appendChild(x_txt);
      pos_el.appendChild(x_el);
      
      Element y_el = d.createElement("y");
      Text y_txt = d.createTextNode(String.valueOf(x.getPosition().y));
      y_el.appendChild(y_txt);
      pos_el.appendChild(y_el);
      
      cmp_el.appendChild(pos_el);
      
      return cmp_el;
  }
  
  /**
   * Creates an Element(or Node in XML in Document d) for single line
   * @param x the line for which Element will be created
   * @param d Where the element will be added
   * @return the Element(for XML) created from line x
   */
  private Element createWireEle(line x, Document d)
  {
      Element wire_el = d.createElement("wire");
      wire_el.setAttribute("id", String.valueOf(x.getId()));
      
      Element strt_el = d.createElement("start");
      Element strt_cmp_el = d.createElement("comp_id");
      Text strt_cmp_txt = d.createTextNode(String.valueOf(x.getStartCmpId()));
      strt_cmp_el.appendChild(strt_cmp_txt);
      Element strt_mrk_el = d.createElement("mark_point");
      Text strt_mrk_txt = d.createTextNode(String.valueOf(x.getStartMrkPt()));
      strt_mrk_el.appendChild(strt_mrk_txt);
      strt_el.appendChild(strt_cmp_el);
      strt_el.appendChild(strt_mrk_el);
      wire_el.appendChild(strt_el);
      
      Element inter_el = d.createElement("inter_point");
      inter_el.setAttribute("no", String.valueOf(x.getInter_Line_segs().size()));
      for(Point p:x.getInter_Line_segs())
      {
          Element pos_el = d.createElement("position");
      
          Element x_el = d.createElement("x");
          Text x_txt = d.createTextNode(String.valueOf(p.x));
          x_el.appendChild(x_txt);
          pos_el.appendChild(x_el);
          Element y_el = d.createElement("y");
          Text y_txt = d.createTextNode(String.valueOf(p.y));
          y_el.appendChild(y_txt);
          pos_el.appendChild(y_el);
          
          inter_el.appendChild(pos_el);
      }
      wire_el.appendChild(inter_el);
      
      Element end_el = d.createElement("end");
      Element end_cmp_el = d.createElement("comp_id");
      Text end_cmp_txt = d.createTextNode(String.valueOf(x.getEndCmpId()));
      end_cmp_el.appendChild(end_cmp_txt);
      Element end_mrk_el = d.createElement("mark_point");
      Text end_mrk_txt = d.createTextNode(String.valueOf(x.getEndMrkPt()));
      end_mrk_el.appendChild(end_mrk_txt);
      end_el.appendChild(end_cmp_el);
      end_el.appendChild(end_mrk_el);
      wire_el.appendChild(end_el);
      
      return wire_el;
  }
  
  /**
   * 
   * @param a
   * @param cnc_a 
   */
  public String save_listener(String description,String[] extensions)
  {
        String tempPath = null;
        boolean ovrwrtNO=false;
        int desn=1;
        do
        {
            JFileChooser sav_c = new JFileChooser();
            FileFilter filter = new FileNameExtensionFilter(description, extensions);
            sav_c.setFileFilter(filter);
            int rVal = sav_c.showSaveDialog(frame);
            if (rVal == JFileChooser.APPROVE_OPTION)
            {
                tempPath = sav_c.getSelectedFile().getPath();
                for(String iter:extensions)
                {
                    if(!tempPath.endsWith(iter))
                    {
                        tempPath=tempPath+"."+iter;
                        break;
                    }
                }
                File f=new File(tempPath);
                if(f.exists())
                {
                    int ovrwrt = 1000;//random value
                        try {
                            ovrwrt = JOptionPane.showConfirmDialog(sav_c.getClass().newInstance(), "Do you want to overwrite the existing file", "File already exists", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
                        } catch (InstantiationException ex) {
                            Logger.getLogger(MainEditor.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (IllegalAccessException ex) {
                            Logger.getLogger(MainEditor.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    if(ovrwrt==JOptionPane.YES_OPTION)
                    {
                        try {
                            f.createNewFile();
                            ovrwrtNO=false;
                            /*
                            else
                            {
                                desn=0;
                                JOptionPane.showMessageDialog(sav_c, "The file cannot be accessed", "File Protected", JOptionPane.ERROR_MESSAGE);
                                this.actionPerformed(e);
                            }*/
                            } catch (IOException ex) {
                                Logger.getLogger(MainEditor.class.getName()).log(Level.SEVERE, null, ex);
                            }
                    }
                    else
                    {
                        desn=0;
                        ovrwrtNO=true;
                    }
                    System.out.println("There");
                }
                else
                {
                    try {
                        f.createNewFile();
                        ovrwrtNO=false;
                    } catch (IOException ex) {
                        Logger.getLogger(MainEditor.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            else
            {
                desn=0;
                ovrwrtNO=false;
            }
        }
        while(ovrwrtNO);
        if(desn==0)
        {
            tempPath=null;
        }
        return tempPath;
}
  
  /**
   * Extracts the buffered image of some component
   * @param myComponent the component for which the BufferedImage is required
   * @param region region to be captured
   * @return the BufferedImage which has the current condition of component
   * @throws IOException 
   */
  public static BufferedImage Get_Component_Image(Component myComponent,Rectangle region) throws IOException
  {
    BufferedImage img = new BufferedImage(myComponent.getWidth(), myComponent.getHeight(), BufferedImage.TYPE_INT_RGB);
    Graphics g = img.getGraphics();
    myComponent.paint(g);
    g.dispose();
    return img;
  }
}