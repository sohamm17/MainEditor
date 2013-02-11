package circuits;

import java.awt.*;
import java.util.ArrayList;

/**
 * Class line
 */
public class line {

  //
  // Fields
  //

  /**
   * only internal points are stored in this
   */
  public ArrayList<Point> line_segs;
  
  /*
   * this id is slightly different and it will be assigned valid only if this line is added to connections 
   * otherwise it is 0 and invalid line
   */
  public int id;
  
  /**
   * The starting Mark Point of the line, with the component
   */
  public int strt_cmp_id,strt_mrk_pt;
  
  /**
   * The ending Mark Point of the line, with the component
   */
  public int end_cmp_id,end_mrk_pt;
  
  //
  // Constructors
  //
  public line () { 
      line_segs=new ArrayList<Point>();
      //id will be given when it's added to connections
      id=0;//means that this line is not added to connections
  };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of line_segs
   * @param newVar the new value of line_segs
   */
  /*public void setLine_segs ( Point newVar ) {
    line_segs = newVar;
  }*/

  /**
   * Get the value of line_segs
   * @return the value of line_segs
   */
  public ArrayList<Point> getLine_segs ( ) {
    return line_segs;
  }

  /**
   * The internal points i.e points except starting and ending point
   * @return the value of line_segs
   */
  public ArrayList<Point> getInter_Line_segs ( ) {
    ArrayList<Point> tmp = new ArrayList<Point>();
    for(int i=1;i<this.getLine_segs().size()-1;i++)
    {
        tmp.add(this.getLine_segs().get(i));
    }
    return tmp;
  }
  
  /**
   * Set the value of id
   * @param newVar the new value of id
   */
  public final void setId ( int newVar ) {
    id = newVar;
  }

  /**
   * Get the value of id
   * @return the value of id
   */
  public int getId ( ) {
    return id;
  }
  
  /**
   * Get Starting component ID
   * @return Starting component ID
   */
  public int getStartCmpId ( ) {
    return this.strt_cmp_id;
  }
  
  /**
   * Get Ending component ID
   * @return Ending component ID
   */
  public int getEndCmpId ( ) {
    return this.end_cmp_id;
  }

  /**
   * Get Starting Mark Point of component ID
   * @return Starting Mark Point of component ID
   */
  public int getStartMrkPt ( ) {
    return this.strt_mrk_pt;
  }
  
  /**
   * Get Ending Mark Point of component ID
   * @return Ending Mark Point of component ID
   */
  public int getEndMrkPt ( ) {
    return this.end_mrk_pt;
  }
  
  //
  // Other methods
  //

  /**
   * adds a Point to the line
   * @param        x
   */
  public void add_line_seg( Point x )
  {
      line_segs.add(x);
  }
  
  /**
   * delete the segments within 'strt' and 'end'
   * @param        strt one end of line-segment
   * @param        end another end of line-segment
   */
  public void remove_line_seg( Point strt, Point end )
  {
      int strt_indx,end_indx,i;
      strt_indx=line_segs.indexOf(strt);
      end_indx=line_segs.indexOf(end);
      for(i=strt_indx;i<=end_indx;i++)
      {
          line_segs.remove(i);
      }
  }
  
  /**
   * sets the starting point parameters
   */
  public void set_start_param(int cmp_id,int mrk_pt,counts a)
  {
      this.strt_cmp_id = cmp_id;
      this.strt_mrk_pt = mrk_pt;
      this.add_line_seg(a.get_component_by_id(this.strt_cmp_id).get_mark_points(this.strt_mrk_pt));
  }
  
  /**
   * sets the terminating point parameters
   */
  public void set_end_param(int cmp_id,int mrk_pt,counts a)
  {
      this.end_cmp_id = cmp_id;
      this.end_mrk_pt = mrk_pt;
      Point p=a.get_component_by_id(this.end_cmp_id).get_mark_points(this.end_mrk_pt);
      this.add_line_seg(p);
  }

  /**
   * this call should be implicit but still have been made explicit(have to think about its calling time)
   * Updates the end point when components are moved to a new position
   * @param a 
   */
  public void update_end_point_params(counts a)
  {
      this.getLine_segs().get(0).setLocation(a.get_component_by_id(this.strt_cmp_id).get_mark_points(this.strt_mrk_pt));
      this.getLine_segs().get(this.getLine_segs().size()-1).setLocation(a.get_component_by_id(this.end_cmp_id).get_mark_points(this.end_mrk_pt));
  }
}