package circuits;

import java.util.ArrayList;


/**
 * Class connections
 * logs the connections
 */
public class connections {

  //
  // Fields
  //

  public ArrayList<line> line_logs;
  public int cnt_line_id;
  
  //
  // Constructors
  //
  public connections () { 
      line_logs=new ArrayList<line>();
      this.setCnt_line_id(0);
  };
  
  //
  // Methods
  //


  //
  // Accessor methods
  //

  /**
   * Set the value of line_logs
   * @param newVar the new value of line_logs
   */
  /*public void setLine_logs ( line newVar ) {
    line_logs = newVar;
  }*/

  /**
   * Get the value of line_logs
   * @return the value of line_logs
   */
  public ArrayList<line> getLine_logs ( ) {
    return line_logs;
  }

  /**
   * Set the value of cnt_line_id
   * @param newVar the new value of cnt_line_id
   */
  public final void setCnt_line_id ( int newVar ) {
    cnt_line_id = newVar;
  }

  /**
   * Get the value of cnt_line_id
   * @return the value of cnt_line_id
   */
  public int getCnt_line_id ( ) {
    return cnt_line_id;
  }

  //
  // Other methods
  //

  /**
   * @return       line
   * @param        new_line
   */
  public void add_line( line new_line, counts a )
  {
      this.setCnt_line_id(this.getCnt_line_id()+1);
      new_line.setId(this.getCnt_line_id());
      this.getLine_logs().add(new_line);
      int start_comp = new_line.getStartCmpId();
      int start_mark_point = new_line.getStartMrkPt();
      iopoints startPoint = a.get_component_by_id(start_comp).get_mark_points(start_mark_point);
      int end_comp = new_line.getEndCmpId();
      int end_mark_point = new_line.getEndMrkPt();
      iopoints endPoint = a.get_component_by_id(end_comp).get_mark_points(end_mark_point);
      if(startPoint.getIo() == 0 && endPoint.getIo() == 0)
      {
          a.get_component_by_id(end_comp).setExpression(end_mark_point,a.get_component_by_id(start_comp).getExpression(start_mark_point));
      }
      else if(startPoint.getIo() == 0 && endPoint.getIo() == 1)
      {
          a.get_component_by_id(start_comp).setExpression(start_mark_point,a.get_component_by_id(end_comp).getExpression(end_mark_point));
      }
      else if(startPoint.getIo() == 1 && endPoint.getIo() == 0)
      {
          a.get_component_by_id(end_comp).setExpression(end_mark_point,a.get_component_by_id(start_comp).getExpression(start_mark_point));
      }
      a.update_expressions();
  }


  /**
   * @param        old_line
   */
  public void remove_line( line old_line )
  {
      this.getLine_logs().remove(old_line);
  }
  
  /**
   * removes the i-th line from line_logs
   * @param i the i-th line to remove
   */
  public void remove_line_i(int i)
  {
      this.getLine_logs().remove(i);
  }


  /**
   * clear all the line_logs and sets to default
   */
  public void clear(  )
  {
      line_logs.clear();
      this.setCnt_line_id(0);
  }
  
  /**
   * Updates the end point parameters of each line in connections with counts a
   * updates the end points of different line(s) when components are moved to a new position
   * also if any component is removed
   * @param a it has all the components and their mark points
   */
  public void update(counts a)
  {
      for(int i = 0;i < this.getLine_logs().size();)
      {
          line cntr=this.getLine_logs().get(i);
          component endComp = a.get_component_by_id(cntr.getEndCmpId());
          component startComp = a.get_component_by_id(cntr.getStartCmpId());
          //System.out.println(this.getLine_logs().size()+","+a.get_component_by_id(cntr.getStartCmpId()));
          if(startComp == null || endComp == null)
          {
              this.remove_line_i(i);
              if(startComp == null)
              {
                  if(endComp.get_mark_points(cntr.getEndMrkPt()).getIo() == 0)
                  {
                      endComp.runPrimaryRules(cntr.getEndMrkPt());
                      endComp.parseOutputRules();
                  }
                  else
                      endComp.parseOutputRules();
              }
              else if(endComp == null)
              {
                  if(startComp.get_mark_points(cntr.getStartMrkPt()).getIo() == 0)
                  {
                      startComp.runPrimaryRules(cntr.getStartMrkPt());
                      startComp.parseOutputRules();
                  }
                  else
                      startComp.parseOutputRules();
              }
          }
          else
          {
              cntr.update_end_point_params(a);
              i++;
          }
      }
  }
}