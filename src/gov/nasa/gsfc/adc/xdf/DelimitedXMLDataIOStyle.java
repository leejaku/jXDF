// XDF DelimitedXMLDataIOStyle Class
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
import java.io.*;

// DelimitedXMLDataIOStyle.java Copyright (C) 2000 Brian Thomas,
// ADC/GSFC-NASA, Code 631, Greenbelt MD, 20771

/**DelimitedXMLDataIOStyle.java: DelimitedDataIOStyle is a class that indicates
 * how delimited ASCII records are to be read in
 * @version $Revision$
 */


public class DelimitedXMLDataIOStyle extends XMLDataIOStyle {

 //
  //Fields
  //


  public final static String DefaultDelimiter =" ";
  public final static String DefaultRepeatable = "yes";
  //double check
  public final static String DefaultRecordTerminator = Constants.NEW_LINE;

  protected String delimiter =  DefaultDelimiter;
  protected String repeatable = DefaultRepeatable;
  protected String recordTerminator = DefaultRecordTerminator;

   //
   // Constructors 
   //
   public DelimitedXMLDataIOStyle (Array parentArray)
   {
      this.parentArray = parentArray;
      init();
   }

   /**  This constructor takes a Java Hashtable as an initializer of
        the XML attributes of the object to be constructed. The
        Hashtable key/value pairs coorespond to the class XDF attribute
        names and their desired values.
    */
   public DelimitedXMLDataIOStyle ( Array parentArray, Hashtable InitXDFAttributeTable )
   {

      this.parentArray = parentArray;

      // init the XML attributes (to defaults)
      init();

      // init the value of selected XML attributes to HashTable values
      hashtableInitXDFAttributes(InitXDFAttributeTable);
   }


  //
  //Get/Set Methods
  //

  /**setDelimiter: set the *delimiter* attribute
   * @return: the current *delimiter* attribute
   */
  public String setDelimiter (String strDelimiter)
  {
     delimiter=strDelimiter;
     return delimiter;
  }

  /**getDelimiter
   * @return: the current *delimiter* attribute
   */
  public String getDelimiter()
  {
    return delimiter;
  }

  /**setRepeatable: set the *repeatable* attribute
   * @return: the current *repeatable* attribute
   */
  public String setRepeatable (String strIsRepeatable)
  {
    if (!strIsRepeatable.equals("yes")  && !strIsRepeatable.equals("no") ) {
      Log.error("*repeatable* attribute can only be set to yes or no");
      Log.error("tend to set as" + strIsRepeatable);
      Log.error("invalid. ignoring request");
      return null;
    }
    repeatable=strIsRepeatable;
    return repeatable;
  }

  /**getRepeatable
   * @return: the current *repeatable* attribute
   */
  public String getRepeatable()
  {
    return repeatable;
  }


   /**setRecordTerminator: set the *recordTerminator* attribute
   * @return: the current *recordTerminator* attribute
   */
  public String setRecordTerminator (String strRecordTerminator)
  {
    recordTerminator = strRecordTerminator;
    return recordTerminator;

  }

  /**getRecordTerminator
   * @return: the current *recordTerminator* attribute
   */
  public String getRecordTerminator()
  {
    return recordTerminator;
  }

  //
  //PROTECTED methods
  //

   protected void specificIOStyleToXDF( OutputStream outputstream,String indent) {
    int stop = parentArray.getAxisList().size()-1;
    nestedToXDF(outputstream, indent, 0, stop);

   }

   //
   //PRIVATE methods
   //

   private void nestedToXDF(OutputStream outputstream, String indent, int which, int stop) {
    if (which > stop) {
      if (sPrettyXDFOutput) {
        writeOut(outputstream, Constants.NEW_LINE);
        writeOut(outputstream, indent);
      }
      writeOut(outputstream, "<" + classXDFNodeName);
      if (delimiter !=null)
        writeOut(outputstream, " delimiter =\"" + delimiter + "\"");
      writeOut(outputstream, " repeatable=\"" + repeatable +  "\"");
      if (recordTerminator !=null)
        writeOut(outputstream, " recordTerminator=\"" + recordTerminator + "\"/>");
    }
    else {
      if (sPrettyXDFOutput) {
        writeOut(outputstream, Constants.NEW_LINE);
        writeOut(outputstream, indent);
      }
      writeOut(outputstream, "<" + UntaggedInstructionNodeName + " axisIdRef=\"");
      writeOut(outputstream, ((AxisInterface) parentArray.getAxisList().get(which)).getAxisId() + "\">");
      which++;
      nestedToXDF(outputstream, indent + sPrettyXDFOutputIndentation, which, stop);

      if (sPrettyXDFOutput) {
        writeOut(outputstream, Constants.NEW_LINE);
        writeOut(outputstream, indent);
      }
       writeOut(outputstream, "</" + UntaggedInstructionNodeName + ">");
    }
   }

   /** A special private method used by constructor methods to
       convienently build the XML attribute list for a given class.
    */
   private void init()
   {   
      classXDFNodeName = "textDelimiter";

      attribOrder.add(0,"delimiter");
      attribOrder.add(0,"repeatable");
      attribOrder.add(0,"recordTerminator");

      attribHash.put("delimiter", new XMLAttribute(new String(" "), Constants.STRING_TYPE));
      attribHash.put("repeatable", new XMLAttribute(new String("yes"), Constants.STRING_TYPE));
      attribHash.put("recordTerminator", new XMLAttribute(Constants.NEW_LINE, Constants.STRING_TYPE));

   }





}
/* Modification History:
 *
 * $Log$
 * Revision 1.3  2000/11/03 21:22:23  thomas
 * Had to add in XMLAttributes to init method. Added
 * Hashtable init constructor also. -b.t.
 *
 * Revision 1.2  2000/10/31 21:43:11  kelly
 * --completed the *toXDF*.
 * --got rid of the Perl specific stuff  -k.z.
 *
 * Revision 1.1  2000/10/17 21:57:29  kelly
 * created and pretty much completed the class.  -k.z.
 *
 */
