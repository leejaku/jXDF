
// XDF RepeatFormattedIOCmd Class
// CVS $Id$


// RepeatFormattedIOCmd.java Copyright (C) 2000 Brian Thomas,
// ADC/GSFC-NASA, Code 631, Greenbelt MD, 20771

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

import java.util.Hashtable;
import java.io.OutputStream;
import java.util.Collections;
import java.util.ArrayList;
import java.util.List;

/** this class handles the repeat ELEMENT
   @version $Revision$
 */
public class RepeatFormattedIOCmd extends XMLDataIOStyle implements FormattedIOCmd {

  //
  //Fields
  //

  //list to store the formatted IO commands
  private List formatCommandList = Collections.synchronizedList(new ArrayList());

  //
  //constructor and related methods
  //

  //no-arg constructor
  public RepeatFormattedIOCmd ()
  {

     init();
  }

  /**  This constructor takes a Java Hashtable as an initializer of
       the XML attributes of the object to be constructed. The
       Hashtable key/value pairs coorespond to the class XDF attribute
       names and their desired values.
    */
  public void RepeatFormattedIOCmd ( Hashtable InitXDFAttributeTable )
  {

    // init the XML attributes (to defaults)
    init();

    // init the value of selected XML attributes to HashTable values
    hashtableInitXDFAttributes(InitXDFAttributeTable);

  }

  //
  //Get/Set methods
  //

  /** set the *count* attribute
   */
  public void setCount(Integer numCount) {
    ((XMLAttribute) attribHash.get("count")).setAttribValue(numCount);
  }

  /** get the *count* attribute
   */
  public Integer getCount() {
    return (Integer)  ((XMLAttribute) attribHash.get("count")).getAttribValue();
  }

  /** set the formatCommandList
   */
  public void setFormatCommandList(List formatList) {
     formatCommandList = formatList;
  }

  /** get the formatCommandList
  */
  public List getFormatCommandList() {
   return formatCommandList;
  }

  /** add a command to the formatCommandList
    * @return the command that is added
    */
  public FormattedIOCmd addFormatCommand(FormattedIOCmd formatCmd) {
    formatCommandList.add(formatCmd);
    return formatCmd;
  }

  /** convenience methods that return the command list
   */
  public List getCommands() {
     return formatCommandList;
  }

  /**deep copy of this RepeatFormattedIOCmd object
    */
   public Object clone () throws CloneNotSupportedException {
    RepeatFormattedIOCmd cloneObj = (RepeatFormattedIOCmd) super.clone();
    synchronized (formatCommandList) {
      synchronized (cloneObj.formatCommandList) {
        int stop = formatCommandList.size();
        cloneObj.formatCommandList = Collections.synchronizedList(new ArrayList(stop));
        for (int i = 0; i <stop; i++) {
          cloneObj.formatCommandList.add(((XMLDataIOStyle)formatCommandList.get(i)).clone());
        }
        return cloneObj;
      } //synchronize
    } //synchronize
   }

  //
  // Protected Methods
  //

  protected void specificIOStyleToXDF( OutputStream outputstream,String indent)
  {
     //open the code
     writeOut(outputstream, "<" + classXDFNodeName);
     writeOut(outputstream, " count=\"" + getCount() + "\"");
     writeOut(outputstream, ">");

     //write out nodes in formatCommandList
     synchronized (formatCommandList) {
      int stop = formatCommandList.size();
      String moreIndent = indent + Specification.getInstance().getPrettyXDFOutputIndentation();
      for (int i = 0; i <stop; i++) {
         if (Specification.getInstance().isPrettyXDFOutput()) {
            writeOut(outputstream, Constants.NEW_LINE);
            writeOut(outputstream, moreIndent);
         }
        ((XMLDataIOStyle) formatCommandList.get(i)).specificIOStyleToXDF(outputstream, moreIndent);
      }
     }

     //close the node
     if (Specification.getInstance().isPrettyXDFOutput()) {
      writeOut(outputstream, Constants.NEW_LINE);
      writeOut(outputstream, indent);
     }
     writeOut(outputstream, "</" + classXDFNodeName + ">");

  }

  //
  // Private Methods
  //

  /** special private method used by constructor methods to
      conviently build the XML attribute list for a given class.
   */
  private void init()
  {
    classXDFNodeName = "repeat";
    attribOrder.add(0, "count");
    attribHash.put("count", new XMLAttribute(new Integer(1), Constants.NUMBER_TYPE));
  }

}


/* Modification History:
 *
 * $Log$
 * Revision 1.2  2000/11/16 20:06:09  kelly
 * fixed documentation.  -k.z.
 *
 * Revision 1.1  2000/11/09 23:42:38  kelly
 * created the class
 *
 *
 */
