// XDF DataFormat Class
// CVS $Id$
package gov.nasa.gsfc.adc.xdf;

import java.util.*;
import java.io.OutputStream;

// DataFormat.java Copyright (C) 2000 Brian Thomas,
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



/**
 * DataFormat.java:
 * @version $Revision$
 */


 public abstract class DataFormat extends BaseObject {

  //
  //Fields
  //
  //store the subclass's particular nodeName, "string", "integer", etc.
  String specificDataFormatName;

  /** The no argument constructor.
   */
  public DataFormat ()
  {
    init();
  }

  /** init -- special private method used by constructor methods to
   *  conviently build XML attribute Order list
   */
  private void init()
  {

    classXDFNodeName = "dataFormat";

    // order matters! these are in *reverse* order of their
    // occurence in the XDF DTD
    //the order of the attributes that all sub-classses should have
    attribOrder.add(0,"noDataValue");
    attribOrder.add(0,"infiniteNegativeValue");
    attribOrder.add(0,"infiniteValue");
    attribOrder.add(0,"greaterThanOrEqualValue");
    attribOrder.add(0,"greaterThanValue");
    attribOrder.add(0,"lessThanOrEqualValue");
    attribOrder.add(0,"lessThanValue");

    attribHash.put("lessThanValue", new XMLAttribute(null, Constants.STRING_OR_NUMBER_TYPE));
    attribHash.put("lessThanOrEqualValue", new XMLAttribute(null, Constants.STRING_OR_NUMBER_TYPE));
    attribHash.put("greaterThanValue", new XMLAttribute(null, Constants.STRING_OR_NUMBER_TYPE));
    attribHash.put("greaterThanOrEqualValue", new XMLAttribute(null, Constants.STRING_OR_NUMBER_TYPE));
    attribHash.put("infiniteValue", new XMLAttribute(null, Constants.STRING_OR_NUMBER_TYPE));
    attribHash.put("infiniteNegativeValue", new XMLAttribute(null, Constants.STRING_OR_NUMBER_TYPE));
    attribHash.put("noDataValue", new XMLAttribute(null, Constants.STRING_OR_NUMBER_TYPE));

  }

  //
  //ABSTRACT methods
  //
  //return the number of bytes
  public abstract int numOfBytes();

  public  abstract Object setLessThanValue(Object Obj);
  public  abstract Object setLessThanOrEqualValue(Object Obj);
  public  abstract Object setGreaterThanValue(Object Obj) ;
  public  abstract Object setGreaterThanOrEqualValue(Object Obj) ;
  public  abstract Object setInfiniteValue(Object Obj) ;
  public  abstract Object setInfiniteNegativeValue(Object Obj) ;
  public  abstract Object setNoDataValue(Object Obj) ;


  /**getLessThanValue
   * @return: the current *lessThanValue* attribute
   */
  public Object getLessThanValue()
  {
    return ((XMLAttribute) attribHash.get("lessThanValue")).getAttribValue();
  }

  /**getlessThanOrEqualValue
   * @return: the current *lessThanOrEqualValue* attribute
   */
  public Object getlessThanOrEqualValue()
  {
    return ((XMLAttribute) attribHash.get("lessThanOrEqualValue")).getAttribValue();
  }

  /**getGreaterThanValue
   * @return: the current *greaterThanValue* attribute
   */
  public Object getGreaterThanValue()
  {
    return ((XMLAttribute) attribHash.get("greaterThanValue")).getAttribValue();
  }

   /**getGreaterThanOrEqualValue
   * @return: the current *greaterThanOrEqualValue* attribute
   */
  public Number getGreaterThanOrEqualValue()
  {
    return (Number) ((XMLAttribute) attribHash.get("greaterThanOrEqualValue")).getAttribValue();
  }

   /**getInfiniteValue
   * @return: the current *infiniteValue* attribute
   */
  public Number getInfiniteValue()
  {
    return (Number) ((XMLAttribute) attribHash.get("infiniteValue")).getAttribValue();
  }

   /**getInfiniteNegativeValue
   * @return: the current *infiniteNegativeValue* attribute
   */
  public Number getInfiniteNegativeValue()
  {
    return (Number) ((XMLAttribute) attribHash.get("infiniteNegativeValue")).getAttribValue();
  }


  /**getNoDataValue
   * @return: the current *noDataValue* attribute
   */
  public Object getNoDataValue()
  {
    return ((XMLAttribute) attribHash.get("noDataValue")).getAttribValue();
  }

  /** override the base object method to add a little tailoring
   */
  public void toXDFOutputStream (  OutputStream outputstream,
                                   Hashtable XMLDeclAttribs,
                                   String indent,
                                   boolean dontCloseNode,
                                   String newNodeNameString,
                                   String noChildObjectNodeName
  ) {

    String nodeNameString = classXDFNodeName;
    // 1. open this node, print its simple XML attributes
      if (sPrettyXDFOutput)
        writeOut(outputstream, indent); // indent node if desired

      writeOut(outputstream,"<" + nodeNameString + ">");   // print opening statement

      //writeOut the body of DataFormat
      writeOut(outputstream, "<" + specificDataFormatName);

    // gather info about XMLAttributes in this object/node
    Hashtable xmlInfo = getXMLInfo();

    // 2. Print out string object XML attributes EXCEPT for the one that
    //    matches PCDATAAttribute.
    ArrayList attribs = (ArrayList) xmlInfo.get("attribList");

    synchronized(attribs) {
      int stop = attribs.size();
      for (int i = 0; i < stop; i++) {
        Hashtable item = (Hashtable) attribs.get(i);
        writeOut(outputstream, " "+ item.get("name") + "=\"" + item.get("value") + "\"");
      }
    }

    //writeout end of the boby
    writeOut(outputstream, "/>");

    //writeout closing node
    writeOut(outputstream, "</" + nodeNameString+ ">");
    if (sPrettyXDFOutput)
      writeOut(outputstream, Constants.NEW_LINE);

  }

 }  //end of DataFormat class

 /* Modification History:
 *
 * $Log$
 * Revision 1.4  2000/10/27 21:15:00  kelly
 * completed *toXDF*.  -k.z.
 *
 * Revision 1.3  2000/10/26 20:14:17  kelly
 * major fix.  get methods are now in this abstract class.  all set methods are declared as abstract.  -k.z.
 *
 * Revision 1.2  2000/10/16 14:48:18  kelly
 * pretty much completed the class.  --k.z.
 *
 */
