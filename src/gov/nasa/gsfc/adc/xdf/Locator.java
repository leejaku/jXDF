
// XDF Locator class
// CVS $Id$

/*
    This program is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with this program; if not, write to the Free Software
    Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
*/

package gov.nasa.gsfc.adc.xdf;

import java.util.*;

/**
   Identifies a specific datum location within the n-dimensional data space.
   An instance of Locator is always tied to a particular instance of
   DataModel, which determines the range of the valid axis indexes and
   the range of valid datum indexes.
 */

  //double check, should not inherit from BaseObject as is implemented
 public class Locator {
  protected Array parentArray;
  protected List axisOrderList = Collections.synchronizedList(new ArrayList());
  //hashtable to store the (axis, index) pair
  protected Hashtable locations;
  public Locator(Array array) {
    Log.debug("in Locator(Array)");
    parentArray = array;
    List axisList = parentArray.getAxisList();

    /**now, since we KNOW _parentArray is defined
     * (has to be intanciated via XDF::Array ONLY)
     * we can proceed to initialize the axis, index positions
     * to the origin (ie index 0 for each axis).
     * We choose the parent Array axisList ordering for our
     * default location ordering.
     */

    locations = new Hashtable(axisList.size());
    for (int i = 0; i < axisList.size(); i++) {
      Axis axis = (Axis) axisList.get(i);
      axisOrderList.add(axis);
      locations.put(axis, new Integer(0));
    }
  }

  /**setAxisLocation: set the index of an axis
   * @param: Axis, index
   * @return: index if successful
   */
  public int setAxisLocation (Axis axisObj, int index) throws AxisLocationOutOfBoundsException {
    if ((!parentArray.getAxisList().contains(axisObj)) ||
        (index < 0) ||
        (index > axisObj.getLength()-1) ) {
        throw new AxisLocationOutOfBoundsException();
    }
    //now update the axis and index pair in the hashtable
    //locations.remove(axisObj);
    locations.put(axisObj, new Integer(index));
    return index;
  }

  /**getAxisLocation: get the index of an Axis in the Locator object
   * @param: Axis
   * @return: index if successful, -1 if not
   */
  public int getAxisLocation (Axis axisObj) {
     if ((!parentArray.getAxisList().contains(axisObj)) ) {
        Log.error("axisObj is not an Axis ref contained in Locator's parentArray");
        Log.error("regnore request");
        return -1;
     }
     Integer loc = (Integer) locations.get(axisObj);
     if (loc !=null)
      return loc.intValue();
     else {
      Log.error("error, parentArray constains the axisObj, but Location doens't");
      return -1;
    }

  }



  /**setAxisLocationByAxisValue: set the index of an axis to the index of a value
   * along that axis
   * @return: index if successful, -1 if not
   */
  public int setAxisLocationByAxisValue(Axis axisObj, Value valueObj) throws AxisLocationOutOfBoundsException{
    if ((!parentArray.getAxisList().contains(axisObj)) ||
        valueObj == null ) {
        Log.error("either axisObj is not an Axis ref contained in Locator's parentArray or Value is null");
        Log.error("regnore request");
        return -1;
     }
    try {
     return setAxisLocation(axisObj, axisObj.getIndexFromAxisValue(valueObj));
    }
    catch (AxisLocationOutOfBoundsException e) {
      throw e;
    }
  }

  /**next: Change the locator coordinates to the next datacell as
   * determined from the locator iteration order.
   *  Returns '0' if it must cycle back to the first datacell
   *  to set a new 'next' location.
   */
  public boolean next() {
    boolean outofDataCells = true;

    for (int i = 0; i <axisOrderList.size() ; i++) {
      Axis axis = (Axis) axisOrderList.get(i);
      int index = ((Integer) locations.get(axis)).intValue();
      if (index < axis.getLength()-1) {
        outofDataCells = false;
        index++;
        locations.put(axis, new Integer(index));
        break;  //get out of the for loop
      }
      locations.put(axis, new Integer(0));
    }
    return !outofDataCells;
  }

  /**prev: Change the locator coordinates to the previous datacell as
   * determined from the locator iteration order.
   * Returns '0' if it must cycle to the last datacell.
   */
  public boolean prev() {
    boolean outofDataCell = true;

    for (int i = 0; i <axisOrderList.size() ; i++) {
      Axis axis = (Axis) axisOrderList.get(i);
      int index = ((Integer) locations.get(axis)).intValue();
      index--;
      if (index < 0) {
        locations.put(axis, new Integer(axis.getLength()-1));
      }
      else {
        locations.put(axis, new Integer(index));
        outofDataCell = false;
        break;  //get out of the for loop
      }
    }

    return !outofDataCell;
  }

  public void setIterationOrder(List axisOrderListRef) {
    //have to check the list elements are of type Axis, double check
    if (axisOrderListRef == null) {
      Log.error("Locator can't setIterationOrder, axisOrderList arg is null");
      return;
    }
    List oldList = axisOrderList;
    axisOrderList = Collections.synchronizedList(new ArrayList());
    int index = 0;
    for (int i = 0; i < axisOrderListRef.size(); i++) {
      Axis axis = (Axis) axisOrderListRef.get(i);
      for (int j = 0; j < oldList.size(); j++) {
        Axis oldAxis = (Axis) oldList.get(j);
        if (oldAxis.equals(axis)) {
          axisOrderList.add(axis);
          break;
        }
      }  //end of inner for loop
    } //end of outer for loop

    oldList = null;  //force garbage collection
    return;

  }

  /**getIteration:
   * @return: an array of Axises, whose order in the array correspondes to
   * the iteration order
   */
  public List getIterationOrder() {
    return axisOrderList;

  }


  /**reset: reset the locator to the origin
   *
   */
    public void reset() {
      synchronized(locations) {
        Integer origin = new Integer(0);
        Enumeration enum = locations.keys(); // Must be in synchronized block
            while (enum.hasMoreElements()) {
              Axis axis = (Axis) enum.nextElement();
              locations.put(axis, origin);
            }
      }
    }


}  //end of Locator class


/* Modification History:
 *
 * $Log$
 * Revision 1.6  2000/10/26 14:26:08  kelly
 * retrieval order is in sync with the axisOrder now (first axis is the fastest).  -k.z.
 *
 * Revision 1.5  2000/10/22 21:11:10  kelly
 * major rework of the class.
 *
 * Revision 1.4  2000/10/18 15:52:30  kelly
 * pretty much completed the class.  -k.z.
 *
 * Revision 1.3  2000/10/10 19:55:09  cvs
 * merged  in Kellys stuff.
 *
 * Revision 1.2  2000/10/10 17:59:10  cvs
 * Updated documentation. Removed Tickmark variable
 * from setCoordinate method (not a valid class anymore!)
 *
 * Revision 1.1.1.1  2000/09/21 17:53:28  thomas
 * Imported Java Source
 *
 *
 */
