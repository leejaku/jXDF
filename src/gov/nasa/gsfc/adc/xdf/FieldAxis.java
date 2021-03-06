

// XDF FieldAxis Class
// CVS $Id$

// FieldAxis.java Copyright (C) 2000 Brian Thomas,
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
import java.util.Collections;
import java.util.Set;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Iterator;

/**describe an Axis that consists of Fields
 */

public class FieldAxis extends BaseObjectWithXMLElements implements AxisInterface {

   //
   // Fields
   //

   /* XML attribute names */
   private static final String NAME_XML_ATTRIBUTE_NAME = new String("name");
   private static final String DESCRIPTION_XML_ATTRIBUTE_NAME = new String("description");
   private static final String ALIGN_XML_ATTRIBUTE_NAME = new String("align");
   private static final String ID_XML_ATTRIBUTE_NAME = new String("axisId");
   private static final String IDREF_XML_ATTRIBUTE_NAME = new String("axisIdRef");
   private static final String FIELDLIST_XML_ATTRIBUTE_NAME = new String("fieldList");

   /**length of the FieldAxis
   */
   protected int length;

   /** This field stores object references to those field group objects
    * to which this FieldAxis object belongs
    */
   protected Set fieldGroupOwnedHash = Collections.synchronizedSet(new HashSet());

   private Array parentArray;

   //
   // Constructors
   //

   /** The no argument constructor.
    */
   public FieldAxis ()
   {
      init();
   }

   /**
    *  create a fieldAxis with desired dimension
    *  with each field initialized with default values
    */
   public FieldAxis (int fieldDimension)
   {
      init();
      for (int i=0; i<fieldDimension; i++)
	 addField(new Field());
   }

   /**  This constructor takes a Java Hashtable as an initializer of
        the XML attributes of the object to be constructed. The
        Hashtable key/value pairs coorespond to the class XDF attribute
        names and their desired values.
     */
   public FieldAxis ( Hashtable InitXDFAttributeTable )
   {
       
      // init the XML attributes (to defaults)
      init();

      // init the value of selected XML attributes to HashTable values
      hashtableInitXDFAttributes(InitXDFAttributeTable);

   }

   //
   // Get/Set Methods
   //

  /** set the *name* attribute
   */
  public void setName (String strName)
  {
     ((Attribute) attribHash.get(NAME_XML_ATTRIBUTE_NAME)).setAttribValue(strName);
  }

   /**
   * @return the current *name* attribute
   */
  public String getName()
  {
    return (String) ((Attribute) attribHash.get(NAME_XML_ATTRIBUTE_NAME)).getAttribValue();
  }

   /** set the *description* attribute
   */
  public void setDescription (String strDesc)
  {
     ((Attribute) attribHash.get(DESCRIPTION_XML_ATTRIBUTE_NAME)).setAttribValue(strDesc);
  }

  /**
   * @return the current *description* attribute
   */
  public String getDescription() {
    return (String) ((Attribute) attribHash.get(DESCRIPTION_XML_ATTRIBUTE_NAME)).getAttribValue();
  }

 /** set the *align* attribute
   */
  public void setAlign(String strName)
  {
      ((Attribute) attribHash.get(ALIGN_XML_ATTRIBUTE_NAME)).setAttribValue(strName);
  }

   /**
   * @return the current *align* attribute
   */
  public String getAlign()
  {
     return (String) ((Attribute) attribHash.get(ALIGN_XML_ATTRIBUTE_NAME)).getAttribValue();
  }

  /** set the *axisId* attribute
   */
  public void setAxisId (String strAxisId)
  {
     ((Attribute) attribHash.get(ID_XML_ATTRIBUTE_NAME)).setAttribValue(strAxisId);

  }

   /**
   * @return the current *axisId* attribute
   */
  public String getAxisId()
  {
    return (String) ((Attribute) attribHash.get(ID_XML_ATTRIBUTE_NAME)).getAttribValue();
  }

  /** set the *axisIdRef* attribute
   */
  public void setAxisIdRef (String strAxisIdRef)
  {
     ((Attribute) attribHash.get(IDREF_XML_ATTRIBUTE_NAME)).setAttribValue(strAxisIdRef);

  }

   /**
   * @return the current *axisId* attribute
   */
  public String getAxisIdRef()
  {
    return (String) ((Attribute) attribHash.get(IDREF_XML_ATTRIBUTE_NAME)).getAttribValue();
  }

  /** set the *fieldList* attribute
      @deprecated You should use add/remove methods instead.
   */
  public void setFieldList(List field) {
     ((Attribute) attribHash.get(FIELDLIST_XML_ATTRIBUTE_NAME)).setAttribValue(field);
  }

  /**
   * @return the current *fieldList* attribute
   */
  public List getFieldList() {
    return (List) ((Attribute) attribHash.get(FIELDLIST_XML_ATTRIBUTE_NAME)).getAttribValue();
  }

  /**set the fieldGroupOwnedHash
  */
  public void setFieldGroupOwnedHash(Set fieldGroup)
  {
    fieldGroupOwnedHash = fieldGroup;
  }

  /** return the fieldGroupOwnedHash
  */
  public Set getFieldGroupOwnedHash()
  {
    return fieldGroupOwnedHash;
  }

  /** set the parentArray 
  */
  public void setParentArray (Array parent)
  {
     parentArray = parent;
  }

  /** return the length of this axis (eg number of axis value objects)
   *
   */
  public int getLength() {
    return length;
  }

  //
  //Other PUBLIC Methods
  //

  /** adds a field to this fieldAxis.
   * @param field to be added
   * @return true on success, false on failure.
   *
   */
  public boolean addField (Field field) {

     if (!getFieldList().add(field)) {
        return false;
     }

     length++;

     // inform parent array of the change
     if ( parentArray != null) {
         parentArray.needToUpdateLongArrayMult = true;
     }

     return true;
  }

  /** returns the field object at specified index on success, null on failure
   *
   */
  public Field getField (int index) {
    if ((index < 0) || (index > getFieldList().size()-1))  {//index out of range
      Log.error("in Field, getField().  index out of range");
      return null;
    }
    return (Field) getFieldList().get(index);
  }

  /** convenience method that returns all field object held in this FieldAxis
   * object.
   * @return a list of field object reference(ordered by field axis index)
   */
  public List getFields() {
    return getFieldList();
  }

  /** Set the field object at indicated index.
   */

  public void setField(int index, Field field) {

    if ((index < 0) || (index > getFieldList().size()-1))  //index out of range
      return;

    if (index == getFieldList().size()-1) 
       getFieldList().add(field); //add a field
    else 
       getFieldList().set(index, field);  //replace the old field with the new one

  }
  /**<b> NOT CURRENTLY IMPLEMENTED</b> needs to define the impact on dataCube
   */
  public Field removeField(int index) {
    Log.error("in FieldAxis, removeField, method empty");
    return null;
  }
 /**<b> NOT CURRENTLY IMPLEMENTED</b> needs to define the impact on dataCube
   */
  public Field removeField(Field field) {
    Log.error("in FieldAxis, removeField, method empty");
    return null;
  }
  /**returns the list of dataFormat
   */
  public DataFormat[] getDataFormatList() {
    DataFormat[] dataFormatList = new DataFormat[length];
    List fieldList = getFieldList();
    for (int i = 0; i < length; i++)
      dataFormatList[i]=(((Field) fieldList.get(i)).getDataFormat());
    return dataFormatList;
  }


  /**Insert a FieldGroup object into this object.
   * @param group - FieldGroup to be added
   * @return true on success, false on failure.
   */
  public boolean addFieldGroup (FieldGroup group) {
    //add the group to the groupOwnedHash
    return fieldGroupOwnedHash.add(group);
  }

  /** Remove a FieldGroup object from this object.
   * @param FieldGroup to be removed
   * @return true on success, false on failure
   */
  public boolean removeFieldGroup(FieldGroup group) {
    return fieldGroupOwnedHash.remove(group);
  }
  /**deep copy of the FieldAxis
   */
  public Object clone() throws CloneNotSupportedException {
    FieldAxis cloneObj = (FieldAxis) super.clone();

     synchronized (this.fieldGroupOwnedHash) {
      synchronized(cloneObj.fieldGroupOwnedHash) {
        cloneObj.fieldGroupOwnedHash = Collections.synchronizedSet(new HashSet(this.fieldGroupOwnedHash.size()));
        Iterator iter = this.fieldGroupOwnedHash.iterator();
        while (iter.hasNext()) {
          cloneObj.fieldGroupOwnedHash.add(((Group)iter.next()).clone());
        }
      }
    }

    return cloneObj;
  }

  //
  // Protected Methods
  //

  /** A special private method used by constructor methods to
   *  conveniently build the XML attribute list for a given class.
   */
  protected void init ()
  {

    super.init();

    classXDFNodeName = "fieldAxis";

    // order matters! these are in *reverse* order of their
    // occurence in the XDF DTD
    attribOrder.add(0, FIELDLIST_XML_ATTRIBUTE_NAME);
    attribOrder.add(0, IDREF_XML_ATTRIBUTE_NAME);
    attribOrder.add(0, ID_XML_ATTRIBUTE_NAME);
    attribOrder.add(0, ALIGN_XML_ATTRIBUTE_NAME);
    attribOrder.add(0, DESCRIPTION_XML_ATTRIBUTE_NAME);
    attribOrder.add(0, NAME_XML_ATTRIBUTE_NAME);

     //set up the attribute hashtable key with the default initial value

     //set the minimum array size(essentially the size of the axis)
    attribHash.put(FIELDLIST_XML_ATTRIBUTE_NAME, new Attribute(Collections.synchronizedList(new ArrayList(Specification.getInstance().getDefaultDataArraySize())), Constants.LIST_TYPE));
    attribHash.put(IDREF_XML_ATTRIBUTE_NAME, new Attribute(null, Constants.STRING_TYPE));
    attribHash.put(ID_XML_ATTRIBUTE_NAME, new Attribute(null, Constants.STRING_TYPE));
    attribHash.put(ALIGN_XML_ATTRIBUTE_NAME, new Attribute(null, Constants.STRING_TYPE));  //double check??
    attribHash.put(DESCRIPTION_XML_ATTRIBUTE_NAME, new Attribute(null, Constants.STRING_TYPE));
    attribHash.put(NAME_XML_ATTRIBUTE_NAME, new Attribute(null, Constants.STRING_TYPE));

    length = 0;

  }

}

