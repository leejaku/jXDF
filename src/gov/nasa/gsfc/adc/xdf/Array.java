// XDF Array Class
// CVS $Id$

package gov.nasa.gsfc.adc.xdf;

import java.util.*;

// Array.java Copyright (C) 2000 Brian Thomas,
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

 /** DESCRIPTION
  *  XDF is the eXtensible Data Structure, which is an XML format designed to contain n-dimensional
  * scientific/mathematical data. XDF::Array is the basic container for the n-dimensional array data.
  * It gives access to the array data and its descriptors (such as the array axii, associated
  * parameters, notes, etc).
  *
  *
  * Here is a diagram showing the inter-relations between these components
  * of the XDF::Array in a 2-dimensional dataset with no fields.
  *
  *
  *    axisValue -----> "9" "8" "7" "6" "5" "A"  .   .  "?"
  *    axisIndex ----->  0   1   2   3   4   5   .   .   n
  *
  *                      |   |   |   |   |   |   |   |   |
  *    axisIndex--\      |   |   |   |   |   |   |   |   |
  *               |      |   |   |   |   |   |   |   |   |
  *    axisValue  |      V   V   V   V   V   V   V   V   V
  *        |      |
  *        V      V      |   |   |   |   |   |   |   |   |
  *      "star 1" 0 --> -====================================> axis 0
  *      "star 2" 1 --> -|          8.1
  *      "star 3" 2 --> -|
  *      "star 4" 3 --> -|
  *      "star 5" 4 --> -|
  *        "star 6" 5 --> -|       7
  *         .     . --> -|
  *         .     . --> -|
  *         .     . --> -|
  *       "??"    m --> -|
  *                      |
  *                      v
  *                    axis 1
  *
  *
  */


  /**attribute description:
   * name--
   * The STRING description (short name) of this Array.
   * description--
   * scalar string description (long name) of this Array.
   * arrayId
   * A scalar string holding the array Id of this Array.
   * axisList--
   * a SCALAR (ARRAY REF) of the list of L<XDF::Axis> objects held within this array.
   * paramList--
   * reference of the list of L<XDF::Parameter> objects held within in this Array.
   * noteList--
   * reference of the list of L<XDF::Note> objects held within this object.
   * dataCube
   * object ref of the L<XDF::DataCube> object which is a matrix holding the mathematical data
   * of this array.
   * dataFormat
   * object ref of the L<XDF::DataFormat> object.
   * units
   * object ref of the L<XDF::Units> object of this array. The XDF::Units object
   * is used to hold the XDF::Unit objects.
   * fieldAxis
   * OBJECT REF of the L<XDF::FieldAxis> object.
   */

  public class Array extends BaseObject{

  //
  //Fields
  //

  /** This field stores object references to those parameter group objects
   * to which this array object belongs
  */
  protected Set paramGroupOwnedHash = Collections.synchronizedSet(new HashSet());
 // protected locatorOrder;   //doudle chekc init value

  //
  // Constructor and related methods
  //

  /** The no argument constructor.
   */
  public Array ()
  {
    // init the XML attributes (to defaults)
    init();
  }

  /**  This constructor takes a Java Hashtable as an initializer of
       the XML attributes of the object to be constructed. The
       Hashtable key/value pairs coorespond to the class XDF attribute
       names and their desired values.
    */
  public Array ( Hashtable InitXDFAttributeTable )
  {
    // init the XML attributes (to defaults)
    init();

    // init the value of selected XML attributes to HashTable values
    hashtableInitXDFAttributes(InitXDFAttributeTable);

  }

  /** init -- special private method used by constructor methods to
   *  conviently build the XML attribute list for a given class.
   */
  private void init()
  {

    classXDFNodeName = "array";

    // order matters! these are in *reverse* order of their
    // occurence in the XDF DTD
    attribOrder.add(0,"noteList");
    attribOrder.add(0,"dataCube");
    attribOrder.add(0,"xmlDataIOStyle");
    attribOrder.add(0, "axisList");
    attribOrder.add(0, "dataFormat");
    attribOrder.add(0, "units");
    attribOrder.add(0, "paramList");
    attribOrder.add(0,"description");
    attribOrder.add(0,"name");

    //set up the attribute hashtable key with the default initial value
    attribHash.put("noteList", new XMLAttribute(Collections.synchronizedList(new ArrayList()), Constants.LIST_TYPE));
    attribHash.put("dataCube", new XMLAttribute(new DataCube(), Constants.OBJECT_TYPE));
    getDataCube().setParentArray(this);  //cross reference with dataCube
    //default is TaggedXMLDataIOStyle, xmlDataOStyle.parentArray = this
    attribHash.put("xmlDataIOStyle", new XMLAttribute(new TaggedXMLDataIOStyle(this), Constants.OBJECT_TYPE));
    attribHash.put("dataFormat", new XMLAttribute(new StringDataFormat(), Constants.OBJECT_TYPE));  //
    attribHash.put("units", new XMLAttribute(new Units(), Constants.OBJECT_TYPE));
    attribHash.put("axisList", new XMLAttribute(Collections.synchronizedList(new ArrayList()), Constants.LIST_TYPE));
    attribHash.put("paramList", new XMLAttribute(Collections.synchronizedList(new ArrayList()), Constants.LIST_TYPE));
    attribHash.put("description", new XMLAttribute(null, Constants.STRING_TYPE));
    attribHash.put("name", new XMLAttribute(null, Constants.STRING_TYPE));

  };

  //
  //Get/Set Methods
  //

  /**setName: set the *name* attribute
   * @return: the current *name* attribute
   */
  public String setName (String strName)
  {
    return (String) ((XMLAttribute) attribHash.get("name")).setAttribValue(strName);

  }

  /**getName
   * @return: the current *name* attribute
   */
  public String getName()
  {
    return (String) ((XMLAttribute) attribHash.get("name")).getAttribValue();
  }

   /**setDescription: set the *description* attribute
   * @return: the current *description* attribute
   */
  public String setDescription (String strDesc)
  {
    return (String) ((XMLAttribute) attribHash.get("description")).setAttribValue(strDesc);
  }

   /**getDescription
   * @return: the current *description* attribute
   */
  public String getDescription() {
    return (String) ((XMLAttribute) attribHash.get("description")).getAttribValue();
  }

  /**setParamList: set the *paramList* attribute
   * @return: the current *paramList* attribute
   */
  public List setParamList(List param) {
    return (List)((XMLAttribute) attribHash.get("paramList")).setAttribValue(param);
  }

  /**getParamList
   * @return: the current *paramList* attribute
   */
  public List getParamList() {
    return (List) ((XMLAttribute) attribHash.get("paramList")).getAttribValue();
  }

   /**setUnits: set the *units* attribute
   * @return: the current *units* attribute
   */
  public Units setUnits (Units units)
  {
    return (Units) ((XMLAttribute) attribHash.get("units")).setAttribValue(units);
  }

  /**getUnits
   * @return: the current *units* attribute
   */
  public Units getUnits()
  {
    return (Units) ((XMLAttribute) attribHash.get("units")).getAttribValue();
  }

  /**setDataFormat: Sets the data format *type* for this array (an XDF::DataFormat object
   * is held in the attribute $obj->dataFormat, its type is accessible
   * Takes a SCALAR object reference as its argument. Allowed objects to pass
   * to this method include L<XDF::BinaryIntegerDataFormat>, L<XDF::BinaryFloatDataFormat>,
   * L<XDF::ExponentDataFormat>, L<XDF::FixedDataFormat>, L<XDF::IntegerDataFormat>,
   * or L<XDF::StringDataFormat>.
   * RETURNS an object reference if successfull, null if not.
  */
  public DataFormat setDataFormat(DataFormat dataFormat)
  {

    if (dataFormat == null) {  //failure to setDataFormat
      Log.error("in Array, setDataFormat, param passsed in is null, ignore, returning null");
      return null;
    }
    return (DataFormat) ((XMLAttribute) attribHash.get("dataFormat")).setAttribValue(dataFormat);

  }

  /**getDataFormat
   * @return: the current *dataFormat* attribute
   */
  public DataFormat getDataFormat()
  {
    return (DataFormat) ((XMLAttribute) attribHash.get("dataFormat")).getAttribValue();
  }

   /**setAxisList: set the *axisList* attribute
   * @return: the current *axisList* attribute
   */
  public List setAxisList(List axis) {
    return (List)((XMLAttribute) attribHash.get("axisList")).setAttribValue(axis);
  }

  /**getAxisList
   * @return: the current *axisList* attribute
   */
  public List getAxisList() {
    return (List) ((XMLAttribute) attribHash.get("axisList")).getAttribValue();
  }

  /**setXMLDataIOStyle: set the *xmlDataIOStyle* attribute
   * note we have to nsure that _parentArray is properly updated
   * @return: the current *xmlDataIOStyle* attribute
   */
  public XMLDataIOStyle setXMLDataIOStyle(XMLDataIOStyle xmlDataIOStyle)
  {
    if (xmlDataIOStyle == null) {
      Log.error("in Array.setXMLDataIOStyle(), param passed in is null");
      Log.error("xmlDataIOStyle attribute not updated");
      return getXMLDataIOStyle();
    }
    xmlDataIOStyle.setParentArray(this);  //set the parent array to this object
    return (XMLDataIOStyle) ((XMLAttribute) attribHash.get("xmlDataIOStyle")).setAttribValue(xmlDataIOStyle);
  }

  /**getXMLDataIOStyle
   * @return: the current *xmlDataIOStyle* attribute
   */
  public XMLDataIOStyle getXMLDataIOStyle()
  {
    return (XMLDataIOStyle) ((XMLAttribute) attribHash.get("xmlDataIOStyle")).getAttribValue();
  }

  /**setDataCube: set the *dataCube* attribute
   * @return: the current *dataCube* attribute
   */
  public DataCube setDataCube(DataCube dataCube)
  {
    return (DataCube) ((XMLAttribute) attribHash.get("dataCube")).setAttribValue(dataCube);
  }

  /**getDataCube
   * @return: the current *DataCube* attribute
   */
  public DataCube getDataCube()
  {
    return (DataCube) ((XMLAttribute) attribHash.get("dataCube")).getAttribValue();
  }

  /**setNoteList: set the *noteList* attribute
   * @return: the current *noteList* attribute
   */
  public List setNoteList(List note) {
    return (List)((XMLAttribute) attribHash.get("noteList")).setAttribValue(note);
  }

  /**getNoteList
   * @return: the current *noteList* attribute
   */
  public List getNoteList() {
    return (List) ((XMLAttribute) attribHash.get("noteList")).getAttribValue();
  }


  /** setParamGroupOwnedHash
  */
  public Set setParamGroupOwnedHash(Set paramGroup)
  {
    paramGroupOwnedHash = paramGroup;
    return paramGroupOwnedHash;
  }

  /** getParamGroupOwnedHash
  */
  public Set getParamGroupOwnedHash()
  {
    return paramGroupOwnedHash;
  }
  /**setDimension: set the dimension of the L<XDF::DataCube> held within this Array.
   * @param: Number
   * @return: the current dimension of the L<XDF::DataCube> held within this Array
   */
  public Number setDimension(Number dimension) {
    return getDataCube().setDimension(dimension);
  }

   /**getDimension: set the dimension of the L<XDF::DataCube> held within this Array.
   */
   public Number getDimension() {
     return getDataCube().getDimension();
   }

   /**creatLocator: Create one instance of an L<XDF::Locator> object for this array.
    *
    */
   public Locator createLocator() {
    Locator locatorObj = new Locator(this);
    return locatorObj;
   }

   /**addParamGroup: Insert an XDF::ParameterGroup object into this object.
   * @param: ParameterGroup to be added
   * @return:an XDF::ParameterGroup object reference on success, null on failure.
   */
  public ParameterGroup addParamGroup (ParameterGroup group) {
    if (group !=null) {
      //add the group to the groupOwnedHash
      paramGroupOwnedHash.add(group);
      return group;
    }
    else {
      Log.warn("in Structure.addParamGroup(). ParameterGroup passed in is null");
      return null;
    }
  }

  /**removeParamGroup: Remove an XDF::ParameterGroup object from the hashset--paramGroupOwnedHash
   * @param: ParameterGroup to be removed
   * @return: true on success, false on failure
   */
  public boolean removeParamGroup(ParameterGroup group) {
    if (group == null) {
      Log.warn("in Structure.removeParamGroup().  ParameterGroup passed in is null");
      return false;
    }
    return paramGroupOwnedHash.remove(group);
  }


   /** addAxis: insert an XDF::Axis object into the list of axises held by this Array object
   * @param: XDF::Axis to be added
   * @return: an XDF::Axis object on success, null on failure
   */
  public Axis addAxis(Axis axis) {
    if (axis == null) {
      Log.warn("in Array.addAxis(), the Axis passed in is null");
      return null;
    }

    if (!canAddAxisObjToArray(axis)) //check if the axis can be added
      return null;

    getDataCube().incrementDimension();  //increment the DataCube dimension by 1
    getAxisList().add(axis);
    return axis;
  }

   /**removeAxis: removes an XDF::Axis object from axisList
   * @param: Axis to be removed
   * @return: true on success and decrement the dimension,
   *          false on failure and keep the dimension unchanged
   */
  public boolean removeAxis(Axis what) {
    boolean isRemoveSuccess = removeFromList(what, getAxisList(), "axisList");
    if (isRemoveSuccess)   //remove successful
       getDataCube().decrementDimension();   //decrease the dimension by 1
    return isRemoveSuccess;
  }

  /**removeAxis: removes an XDF::Axis object from AxisList
   * @param: list index number
   *  @return: true on success and decrement the dimension,
   *           false on failure and keep the dimension unchanged
   */
  public boolean removeAxis(int index) {
    boolean isRemoveSuccess = removeFromList(index, getAxisList(), "axisList");
    if (isRemoveSuccess)   //remove successful
       getDataCube().decrementDimension();   //decrease the dimension by 1
    return isRemoveSuccess;
  }

  /**addUnit: Insert an XDF::Unit object into the L<XDF::Units> object
   * held in this object.
   * @param: Unit to be added
   * @return: an XDF::Unit object if successfull, null if not.
   */
  public Unit addUnit(Unit unit) {
    if (unit == null) {
      Log.warn("in Parameter.addUnit(), the Unit passed in is null");
      return null;
    }
    return  getUnits().addUnit(unit);
  }

  /**removeUnit: Remove an XDF::Unit object from the XDF::Units object held in
   * this object
   * @param: Unit to be removed
   * @return: true if successful, false if not
   */
  public boolean removeUnit(Unit what) {
    return getUnits().removeUnit(what);
  }

  /**removeUnit: Remove an XDF::Unit object from the XDF::Units object held in
   * this object
   * @param: list index number
   * @return: true if successful, false if not
   */
  public boolean removeUnit(int index) {
    return getUnits().removeUnit(index);
  }

  /** addParameter: insert an XDF::Parameter object into the paramList
   * @param: XDF::Parameter
   * @return: an XDF::Parameter object on success, null on failure
   */
  public Parameter addParameter(Parameter p) {
    if (p == null) {
      Log.warn("in Structure.addParameter, Parameter passed in is null");
      return null;
    }
    getParamList().add(p);
    return p;
  }
  /**removeParameter: removes an XDF::Parameter object from paramList
   * @param: Parameter to be removed
   * @return: true on success, false on failure
   */
  public boolean removeParameter(Parameter what) {
    return  removeFromList(what, getParamList(), "paramList");
  }

  /**removeParameter: removes an XDF::Parameter object from paramList
   * @param: list index number
   * @return: true on success, false on failure
   */
  public boolean removeParameter(int index) {
    return removeFromList(index, getParamList(), "paramList");
  }

  /**A convenience method [same as $ArrayObj->dataCube()->maxDimensionIndex].
   * Returns a SCALAR ARRAY REF of SCALARS (non-negative INTEGERS) which are the maximum index
   * values along each dimension (FieldAxis and Axis objects).
   */
  public List getMaxDataIndices () {
    return getDataCube().getMaxDataIndex();
  }

 /** addNote: insert an XDF::Note object into the list of notes in this Array object
   * @param: XDF::Note
   * @return: an XDF::Note object on success, null on failure
   */
  public Note addNote(Note n) {
    if (n == null) {
      Log.warn("in Array.addNote(), the Note passed in is null");
      return null;
    }
    getNoteList().add(n);
    return n;
  }

  /**removeNote: removes an XDF::Note object from the list of notes in this Array object
   * @param: Note to be removed
   * @return: true on success, false on failure
   */
   public boolean removeNote(Note what) {
     return removeFromList(what, getNoteList(), "noteList");
  }



   /**removeNote: removes an XDF::Note object from the list of notes in this Array object
   * @param: list index number
   * @return: true on success, false on failure
   */
  public boolean removeNote(int index) {
     return removeFromList(index, getNoteList(), "noteList");
  }

  /**getNotes: Convenience method which returns a list of the notes held by
   * this object.
   */
  public List getNotes() {
    return getNoteList();
  }

  /**addData: Append the SCALAR value onto the requested datacell
   * (via L<XDF::DataCube> LOCATOR REF).
   */
  public double  addData (Locator locator, double numValue) {
    return getDataCube().addData(locator, numValue);
  }

  /**addData: Append the SCALAR value onto the requested datacell
   * (via L<XDF::DataCube> LOCATOR REF).
   */
  public String  addData (Locator locator, String strValue) {
    return getDataCube().addData(locator, strValue);
  }

  /** setData: Set the SCALAR value of the requested datacell
   * (via L<XDF::DataCube> LOCATOR REF).
   * Overwrites existing datacell value if any.
   */

   public double  setData (Locator locator, double numValue) {
    return getDataCube().setData(locator, numValue);
  }

  /** setData: Set the SCALAR value of the requested datacell
   * (via L<XDF::DataCube> LOCATOR REF).
   * Overwrites existing datacell value if any.
   */
  public String  setData (Locator locator, String strValue) {
    return getDataCube().setData(locator, strValue);
  }

  /**removeData : Remove the requested data from the indicated datacell
   * (via L<XDF::DataCube> LOCATOR REF) in the XDF::DataCube held in this Array.
   * B<NOT CURRENTLY IMPLEMENTED>.
   */

   public double  removeData (Locator locator, double numValue) {
    return getDataCube().removeData(locator, numValue);
  }

 /**removeData : Remove the requested data from the indicated datacell
   * (via L<XDF::DataCube> LOCATOR REF) in the XDF::DataCube held in this Array.
   * B<NOT CURRENTLY IMPLEMENTED>.
   */
  public String  removeData (Locator locator, String strValue) {
    return getDataCube().removeData(locator, strValue);
  }

  /**getDataFormatList: Get the dataFormatList for this array.
   *
   */
  public List getDataFormatList() {
    FieldAxis fieldAxis = getFieldAxis();
    if (fieldAxis !=null)
      return fieldAxis.getDataFormatList();
    else {  //not fieldAxis
      List list = new ArrayList();  //syn?
      list.add(getDataFormat());
      return list;
    }
  }
  /** addFieldAxis: A convenience method (same as setFieldAxis()).
   * Changes the L<XDF::FieldAxis> object in this Array to the indicated one.
   * @return: reference to fieldAxis if successful, null if not.
   */
  public FieldAxis addFieldAxis(FieldAxis fieldAxis) {
    if (!canAddFieldAxisObjToArray(fieldAxis))
      return null;

    if (getFieldAxis() !=null) {
      List axisList = getAxisList();
      axisList.remove(0);  //
      axisList.add(0, fieldAxis);  //replace the old fieldAxis with the new one
    }
    else {  //add fieldAxis and increment dimension
      getAxisList().add(0, fieldAxis);
      getDataCube().incrementDimension();
    }

    //inficiency???
    return getFieldAxis();
  }
  public FieldAxis getFieldAxis() {
    List axisList = getAxisList();
    if (axisList.size() == 0){  //empty axisList
      return null;
    }
    if ((axisList.get(0).getClass().getName()).indexOf("FieldAxis")!=-1)
      return (FieldAxis) (getAxisList().get(0));
    else
      return null;
  }

  public FieldAxis setFieldAxis(FieldAxis fieldAxis) {
    return addFieldAxis(fieldAxis);
  }

  //
  //PRIVATE Methods
  //

  /**canAddAxisObjToArray: check if we can add this Axis Object to the array
   * 1- check to see that it has an id
   * 2- we SHOULD also check that the id is unique but DONT currently.
  */
  private boolean canAddAxisObjToArray(Axis axisToAdd) {
    if (axisToAdd == null) {
      Log.error("can't add axis, the axis to add is null");
      return false;

    }
    if (axisToAdd.getAxisId() == null) {
      Log.error("Can't add Axis Object without axisId attribute defined");
      return false;
    }
    return true;
  }

  /**canAddFieldAxisObjToArray: check if we can add this FieldAxis Object to the array
   * 1- check to see that it has an id
   * 2- we SHOULD also check that the id is unique but DONT currently.
  */
  private boolean canAddFieldAxisObjToArray(FieldAxis fieldAxisToAdd) {
    if (fieldAxisToAdd == null) {
      Log.error("can't add fieldAxis, the fieldAxis to add is null");
      return false;

    }
    if (fieldAxisToAdd.getAxisId() == null) {
      Log.error("Can't add FieldAxis Object without axisId attribute defined");
      return false;
    }
    return true;
  }


 }

