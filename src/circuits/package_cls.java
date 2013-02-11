package circuits;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.*;
import javax.swing.JOptionPane;
import org.w3c.dom.*;
import org.apache.xerces.parsers.DOMParser;
import org.xml.sax.SAXException;

/**
 * Class package_cls
 */
public class package_cls {
  //
  // Fields
  //

  /**
     * ComponentType-s listed for this package
     */  
  public ArrayList<componentType> componentType_list;
  /**
   * This is the name of the package. Unique and also for internal use of the Code
   */
  public String name;
  
  /**
   * folder-name of the package in System Directory ../SRSCKT/pkg/___
   */
  public String foldername;
  
  /**
   * If the Package is a System Default Package
   * true - if System Default Package
   * false - if User-installed Package
   */
  boolean isSysPkg;


  //
  // Constructors
  //
  /**
   * This will install a package to the counts as well
   * @param path the Zip file path
   * @param a the counts where the package will be installed or added
   * @param sysCall if 0, then zipped file, if 1, then System Package File is going to be installed
   */
  public package_cls( String path, counts a, int sysCall )
  {
      this.componentType_list=new ArrayList<componentType>();
      a.install_new_package(this);
      if(sysCall==0)
          this.setIsSysPkg(false);
      else if(sysCall==1)
      {
          this.setIsSysPkg(true);
          this.setFoldername(path);//if System Package, then path is foldername
      }
      this.parse(path,a,sysCall);
  }
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of componentType_list
   * @param newVar the new value of componentType_list
   */
  /*public void setComponentType_list ( componentType newVar ) {
    componentType_list = newVar;
  }*/

  /**
   * Get the value of componentType_list
   * @return the value of componentType_list
   */
  public ArrayList<componentType> getComponentType_list ( ) {
    return this.componentType_list;
  }

  /**
   * Set the value of foldername
   * @param newVar the new value of foldername
   */
  private void setFoldername ( String newVar ) {
    foldername = newVar;
  }

  /**
   * Get the value of foldername
   * @return the value of foldername
   */
  public String getFoldername ( ) {
    return foldername;
  }

  /**
   * Set the value of isSysPkg
   * @param newVar the new value of isSysPkg
   */
  private void setIsSysPkg ( boolean newVar ) {
    isSysPkg = newVar;
  }

  /**
   * Get the value of isSysPkg
   * @return the value of isSysPkg. 
   * true - if System Default Package
   * false - if User-installed Package
   */
  public boolean getIsSysPkg ( ) {
    return isSysPkg;
  }
  
  /**
   * Set the value of name
   * @param newVar the new value of name
   */
  public void setName ( String newVar ) {
    name = newVar;
  }

  /**
   * Get the value of name
   * @return the value of name
   */
  public String getName ( ) {
    return name;
  }
  //
  // Other methods
  //

  /**
   * parses the Zip File or System Package Files based on sysCall and initialize the variables of package_cls
   * @param path absolute path of the zipped file or only the folder name if System Package file
   * @param a where the package will be installed
   * @param sysCall if 0, then zipped file, if 1, then System Package File is going to be installed
   */
  public final void parse( String path, counts a, int sysCall )
  {      
      String pkg_foldername = null;
      String pkg_name = null;
      int [] id;
      iopoints [][]marks;
      String [] img_addr;
      String [] name;
      Hashtable[] rules;
        try {
            String mypath=System.getProperty("user.home")+File.separator+"SRSCKT"+File.separator+"pkg";
            //System.out.println(mypath);
            if(sysCall==0)
            {
                //if not a System Package, then foldername will be available after unzip
                this.setFoldername(this.unzipFileIntoDirectory(new ZipFile(path), new File(mypath)));
            }
            pkg_foldername = mypath+File.separator+this.getFoldername();
        } catch (IOException ex) {
            Logger.getLogger(package_cls.class.getName()).log(Level.SEVERE, null, ex);
        }
        File mypkg_dir=new File(pkg_foldername);//getting the foldername
        System.out.println(mypkg_dir.getAbsolutePath());
        File[] fileList=mypkg_dir.listFiles();
        //searching the manifest file
        boolean err=true;
        for(File f:fileList)
        {
            if(f.getName().equals("manifest.xml"))
            {
                err=false;
                break;
            }
        }
        if(!err)
        {
              // create a DOMParser
              DOMParser parser=new DOMParser();
                try {
                    parser.parse(mypkg_dir.getAbsolutePath()+File.separator+"manifest.xml");
                } catch (SAXException ex) {
                    Logger.getLogger(package_cls.class.getName()).log(Level.SEVERE, null, ex);
                } catch (IOException ex) {
                    Logger.getLogger(package_cls.class.getName()).log(Level.SEVERE, null, ex);
                }
             // get the DOM Document object
             Document doc=parser.getDocument();

             try
             {
                 int i,j;
                 int componentType_size = 0;//counter for the componentType, at last total number of componentType
                 int tmp=doc.getElementsByTagName("componentType").getLength();//calculates the number of componentType in package
                 //at last, componentType_size=tmp
                 //System.out.println(tmp);
                 id = new int[tmp];
                 img_addr = new String[tmp];
                 name = new String[tmp];
                 marks = new iopoints[tmp][];
                 rules = new Hashtable[tmp];
                 NodeList nodelist = doc.getElementsByTagName("package");
                 NodeList our_nodes = nodelist.item(0).getChildNodes();
                 for(i=0;i<our_nodes.getLength();i++)
                 {
                     Node mynode=our_nodes.item(i);
                     Node txtnode=mynode.getFirstChild();
                     String mynode_name=mynode.getNodeName();
                     if(mynode_name.equals("name"))
                         pkg_name=txtnode.getNodeValue().trim();
                     else if(mynode_name.equals("componentType"))
                     {
                         nodelist=mynode.getChildNodes();//nodelist can be reused as it's not required.
                         //System.out.println(nodelist.getLength());
                         rules[componentType_size] = new Hashtable();
                         for(j=0;j<nodelist.getLength();j++)
                         {
                             Node child = nodelist.item(j);
                             Node text = child.getFirstChild();
                             String child_name = child.getNodeName();
                             //System.out.println(","+text.getNodeName());
                             if(child_name.equals("id"))
                             {
                                 id[componentType_size]=Integer.parseInt(text.getNodeValue().trim());
                                 //System.out.println(id[componentType_size]);
                             }
                             else if(child_name.equals("name"))
                             {
                                 name[componentType_size] = text.getNodeValue().trim();
                             }
                             else if(child_name.equals("img"))
                             {
                                 img_addr[componentType_size]=mypkg_dir.getAbsolutePath()+File.separator+text.getNodeValue().trim();
                                 //System.out.println(img_addr[componentType_size]);
                             }
                             else if(child_name.equals("mark_points"))
                             {
                                 NamedNodeMap no=child.getAttributes();
                                 //posS - total number of mark_points
                                 int posS=Integer.parseInt(no.getNamedItem("no").getNodeValue());
                                 //if(posS==0)
                                     marks[componentType_size]=new iopoints[posS];
                                 //System.out.println("C"+componentType_size+","+posS+","+marks[componentType_size].length);
                                 int pos_cntr=0;//counter for the number of mark_points
                                 NodeList mark_children=child.getChildNodes();
                                 for(int k=0;k<mark_children.getLength();k++)
                                 {
                                     Node pos_node = mark_children.item(k);
                                     Node pos_val = pos_node.getFirstChild();
                                     String pos_name = pos_node.getNodeName();
                                     if(pos_name.equals("position"))
                                     {
                                         marks[componentType_size][pos_cntr] = new iopoints();
                                         NodeList children=pos_node.getChildNodes();
                                         for(int l=0;l<children.getLength();l++)
                                         {
                                             Node pos_child = children.item(l);
                                             Node text_child_pos = pos_child.getFirstChild();
                                             String pos_child_name = pos_child.getNodeName();
                                             if(pos_child_name.equals("x"))
                                             {
                                                 //System.out.println(Integer.parseInt(text_child_pos.getNodeValue().trim()));
                                                 marks[componentType_size][pos_cntr].x=Integer.parseInt(text_child_pos.getNodeValue().trim());
                                             }
                                             else if(pos_child_name.equals("y"))
                                             {
                                                 //System.out.println(Integer.parseInt(text_child_pos.getNodeValue().trim()));
                                                 marks[componentType_size][pos_cntr].y=Integer.parseInt(text_child_pos.getNodeValue().trim());
                                             }
                                             else if(pos_child_name.equals("io"))
                                             {
                                                 //System.out.println(Integer.parseInt(text_child_pos.getNodeValue().trim()));
                                                 marks[componentType_size][pos_cntr].setIo(Integer.parseInt(text_child_pos.getNodeValue().trim()));
                                             }
                                         }
                                         //System.out.println("P"+marks[componentType_size][pos_cntr]);
                                         pos_cntr++;
                                     }
                                 }
                                 //System.out.println("Coming Here");
                             }
                             else if(child_name.equals("rules"))
                             {
                                 NamedNodeMap no = child.getAttributes();
                                 int ruleNumber = Integer.parseInt(no.getNamedItem("name").getNodeValue());
                                 String ruleString = text.getNodeValue().trim();
                                 //System.out.println(ruleNumber+":"+ruleString+":"+rules);
                                 rules[componentType_size].put(ruleNumber, ruleString);
                             }
                         }
                         //System.out.println("HelloA"+componentType_size);
                         componentType_size++;
                         //System.out.println("HelloB"+componentType_size);
                     }
                 }
                 //System.out.println("BB"+componentType_size);
                 init_package_data(pkg_name,componentType_size,marks,img_addr,name,rules,a);
                 //Node txt_node = a_node.getFirstChild();
                 //if(a_node.getNodeName().equals("name"))
                   //  pkg_name=txt_node.getNodeValue().trim();
                 
                 /*if(!(new File(mypkg_dir.getParent()+File.separator+pkg_name)).exists())
                 {
                     System.out.println(mypkg_dir.renameTo(new File(mypkg_dir.getParent()+File.separator+pkg_name)));
                     //System.out.println(a_node.getNextSibling().getNodeName());
                 }
                 else
                     System.out.println("Directory already exists!");*/
             }
             catch(DOMException ex)
             {
                 JOptionPane.showMessageDialog(null, "Error in XML Parsing. Check your package","Error!",JOptionPane.ERROR_MESSAGE);
             }
        }
        else
            JOptionPane.showMessageDialog(null, "manifest.xml not found!","Error!",JOptionPane.ERROR_MESSAGE);
        
  }

  public void add_componentType(componentType a)
  {
      this.componentType_list.add(a);
  }
  
  
   public String unzipFileIntoDirectory(ZipFile zipFile, File jiniHomeParentDir) {
    Enumeration files = zipFile.entries();
    String tmPfoldername=((ZipEntry) files.nextElement()).getName();
    File f = null;
    FileOutputStream fos = null;
    
    while (files.hasMoreElements()) {
        try {
        ZipEntry entry = (ZipEntry) files.nextElement();
        InputStream eis = zipFile.getInputStream(entry);
        byte[] buffer = new byte[1024];
        int bytesRead = 0;
  
        f = new File(jiniHomeParentDir.getAbsolutePath() + File.separator + entry.getName());
        
        if (entry.isDirectory()) {
          f.mkdirs();
          /*System.out.println("A:"+f.getAbsolutePath());
          System.out.println("B:"+f.getPath());
          System.out.println("C:"+f.getParent());*/
          continue;
        } else {
            /*System.out.println(f.getAbsolutePath());
          System.out.println(f.getPath());
          System.out.println(f.getParent());*/
          f.getParentFile().mkdirs();
          f.createNewFile();
          /*System.out.println("A1:"+f.getAbsolutePath());
          System.out.println("B1:"+f.getPath());
          System.out.println("C1:"+f.getParent());*/
        }
        
        fos = new FileOutputStream(f);
  
        while ((bytesRead = eis.read(buffer)) != -1) {
          fos.write(buffer, 0, bytesRead);
        }
      } catch (IOException e) {
        continue;
      } finally {
        if (fos != null) {
          try {
            fos.close();
          } catch (IOException e) {
            // ignore
          }
        }
      }
    }
    return tmPfoldername;
  }
   

   /**
    * makes the component List of the package,sets the package_name
    * @param pkg_name the name of the package - Unique
    * @param marks the mark points 
    * @param img_addr the image addresses
    * @param a counts for the componentType class updating
    * @param name names of the componentTypes
    */
    private void init_package_data(String pkg_name,int no_componentType,iopoints[][] marks, String[] img_addr,String[] name, Hashtable[] rules,counts a)
    {
        this.setName(pkg_name);
        for(int i=0;i<no_componentType;i++)
        {
            componentType current=new componentType(this,a,img_addr[i],marks[i],name[i],rules[i]);
            this.getComponentType_list().add(current);
            //System.out.println("AAA"+i+","+marks[i]);
        }
    }
}