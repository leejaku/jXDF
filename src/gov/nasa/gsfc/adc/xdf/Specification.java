// XDF Specification Class
// CVS $Id$

// Specification.java Copyright (C) 2000 Brian Thomas,
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

/** a singleton class that manages some specification
 * parameters related to XDF.
 */

public class Specification {
  //
  //Fields
  //
  private static Specification instance;

  private static Boolean mutex = new Boolean(true);
  /** Stores whether nicely formatted XDF should be output from any toXML*
      method. Nice formatting includes nested indentation and return characters
      to improve human readability of output XDF (but blows up the size of
      the XDF file!).
  */
  private boolean prettyXDFOutput = false;

  /** The indentation string that will be used for every nesting level within an
      output XDF. For example, if the sPrettyXDFOutputIndentation string consists
      of 3 spaces, then a doubly nested node will be indented 6 spaces, its parent
      node will be indented 3 spaces and the root node will not be indented at all.
      You can be creative with the indentation: any sequence of characters is valid
      (no need to just use spaces!).
  */
  private String prettyXDFOutputIndentation = "  ";

  /** The default allocation size for each dimension within all XDF arrays.
      The practical meaning of this field is that it indicates the initial
      size of each XDF Axis/FieldAxis (the number of axis values/fields along
      the axis) and the number of data cells within a dimension of the XDF
      DataCube object. If more axis values/fields/datacells are placed on a
      given Axis/FieldAxis or data in a unallocated spot within the dataCube
      then the package allocates the needed memory and enlarges the
      DataCube/Axis/FieldAxis objects as it is needed.

      This automated allocation is slow however, so it is desirable, IF you
      know how big your arrays will be, to pre-set this value to encompass your
      data set. Doing so will to improve efficency in some cases. Note that if
      you are having keeping all of your data in memory (a multi-dimensional
      dataset) it may be desirable to DECREASE the value.
  */

  //brian, do we need this? double check
  private int defaultDataArraySize = 1000;

  /** The version of XML that will be output from a toXML* method call.
  */
  private String XMLSpecVersion = "1.0";

  /** The root node name for any XDF document. The root node is a structure
      node with a different name as specified here.
  */
  private String XDFRootNodeName = "XDF";

  /** The XDF node name for the structure class.
  */
  private String XDFStructureNodeName = "structure"; // bad. we should call the
                                                             // class to find this out. -b.t.

  /** The name of the relevant version of XDF DTD file for this package.
  */
  private String XDFDTDName = "XDF_0.17.dtd";

  /** The name of the XMLAttribute which is written out as PCDATA rather than as
      a node attribute (String/Number type XMLAttributes) or child node (Object
      and List type XMLAttributes). At this time only String-type XMLAttributes
      should be named 'value' (yes, it would be an interesting experiment to call
      an Object-type XMLAttribute 'value'!).
  */
  private String PCDATAAttribute = "value";

  /**
   * This private constructor is definced so the compiler won't generate a
   * default public contructor
   */
  private Specification() {

  }

  /**
   * Return a reference to the only instance of this class
   */

  public static Specification getInstance() {
    if (instance == null) {
      synchronized (mutex) {
        if (instance == null)
          instance = new Specification();
      } //synchronized
    }
    return instance;
  }

  //
  //Get/Set methods
  //

  /** Get the output XDF format style.
      @return the value of sPrettyXDFOutput field  (which is true if nicely formatted
               XML is to be outputted from any call to a toXML* method, false if not).
  */
  public boolean isPrettyXDFOutput() {
    return prettyXDFOutput;
  }

  /** Set this to true for nicely formatted XML output from any call to a toXML* method.
      Setting this value will change the runtime behavior of all XDF Objects within an
      application.
      @return the value of sPrettyXDFOutput field.
  */
  public void setPrettyXDFOutput (boolean turnOnPrettyOutput) {
    synchronized (mutex) {
      prettyXDFOutput = turnOnPrettyOutput;
    }
  }

  /** Gets the indentation string that will be used for every nesting level
      within an output XDF. For example, if the string consists of 3 spaces,
      then a doubly nested node will be indented 6 spaces, its parent node will
      be indented 3 spaces and the root node will not be indented at all.
      @return String object containing XDF output indentation.
  */
  public String getPrettyXDFOutputIndentation() {
    return prettyXDFOutputIndentation;
  }

  /** Set the indentation string for PrettyXDFOutput. You aren't limited to just spaces
     here, ANY sequence of characters may be used to indent your XDF documents.
  */
  public void setPrettyXDFOutputIndentation(String indentString) {
     synchronized (mutex) {
      prettyXDFOutputIndentation = indentString;
     }
  }

  /** Get the default allocation size of each dimension within all XDF arrays.
      @return non-negative integer with the dimension size.
  */
  public int getDefaultDataArraySize(){
    return defaultDataArraySize;
  }

  /** Set the default allocation size of each dimension within all XDF arrays.
   */
  public void setDefaultDataArraySize(int arraySize) {
    synchronized (mutex) {
      defaultDataArraySize = arraySize;
    }
  }

  /** Get the XML version of this package.
      This cooresponds to the XML spec version that this package
      uses to write out XDF.
  */
  public String getXMLSpecVersion() {
    return XMLSpecVersion;
  }

  /** Get the root node name for XDF.
  */
  public String getXDFRootNodeName() {
    return XDFRootNodeName;
  }

  /** Get the name of the XDF DTD to which this package corresponds.
  */
  public String getXDFDTDName() {
    return XDFDTDName;
  }

  /** Get the name of the XMLAttribute which will be written out as PCDATA.
  */
  public String getPCDATAAttribute() {
    return PCDATAAttribute;
  }
  /** Get the XDF node name for the structure class.
   */
  public String getXDFStructureNodeName() {
    return XDFStructureNodeName;
  }
}