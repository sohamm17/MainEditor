

/**
 * Class MainEditor
 */
public class MainEditor {

  //
  // Fields
  //

  private Container content;
  private JFrame frame;
  private Pad_Draw draw_pad;
  private JTextArea taskbar;
  private JPanel panel;
  
  //
  // Constructors
  //
  public MainEditor () { };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of content
   * @param newVar the new value of content
   */
  private void setContent ( Container newVar ) {
    content = newVar;
  }

  /**
   * Get the value of content
   * @return the value of content
   */
  private Container getContent ( ) {
    return content;
  }

  /**
   * Set the value of frame
   * @param newVar the new value of frame
   */
  private void setFrame ( JFrame newVar ) {
    frame = newVar;
  }

  /**
   * Get the value of frame
   * @return the value of frame
   */
  private JFrame getFrame ( ) {
    return frame;
  }

  /**
   * Set the value of draw_pad
   * @param newVar the new value of draw_pad
   */
  private void setDraw_pad ( Pad_Draw newVar ) {
    draw_pad = newVar;
  }

  /**
   * Get the value of draw_pad
   * @return the value of draw_pad
   */
  private Pad_Draw getDraw_pad ( ) {
    return draw_pad;
  }

  /**
   * Set the value of taskbar
   * @param newVar the new value of taskbar
   */
  private void setTaskbar ( JTextArea newVar ) {
    taskbar = newVar;
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
  private void setPanel ( JPanel newVar ) {
    panel = newVar;
  }

  /**
   * Get the value of panel
   * @return the value of panel
   */
  private JPanel getPanel ( ) {
    return panel;
  }

  //
  // Other methods
  //

  /**
   * @param        pkg_name Package name is given and componentType buttons will be
   * created from there
   */
  public void add_componentType_buttons( package_cls pkg_name )
  {
  }


  /**
   * Removes the buttons in 'pkg_name" package.
   * @param        pkg_name
   */
  public void remove_componentType_buttons( package_cls pkg_name )
  {
  }


  /**
   */
  public void initGUI(  )
  {
  }


  /**
   */
  public void main(  )
  {
  }


}
