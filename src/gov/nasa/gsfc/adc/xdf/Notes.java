
// XDF Notes Class
// CVS $Id$


// Notes.java Copyright (C) 2000 Brian Thomas,
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
// import java.util.Hashtable;
// import java.io.*;

/**
 * Notes.java:
 * @version $Revision$
 */

 public class Notes extends BaseObject {

   //
   //Fields
   //


   //
   // Constructor and related methods
   //

   /** The no argument constructor.
    */
   public Notes ()
   {
      init();
   }

   /**  This constructor takes a Java Hashtable as an initializer of
        the XML attributes of the object to be constructed. The
        Hashtable key/value pairs coorespond to the class XDF attribute
        names and their desired values.
    */
   public Notes ( Hashtable InitXDFAttributeTable )
   {

      // init the XML attributes (to defaults)
      init();

      // init the value of selected XML attributes to HashTable values
      hashtableInitXDFAttributes(InitXDFAttributeTable);
   }


   /** init -- special private method used by constructor methods to
      conviently build the XML attribute list for a given class.
    */
   private void init()
   {

      classXDFNodeName = "notes";

      // order matters! these are in *reverse* order of their
      // occurence in the XDF DTD
      attribOrder.add(0,"noteList");

      attribHash.put("noteList", new XMLAttribute(Collections.synchronizedList(new ArrayList()), Constants.LIST_TYPE));
   }

   //
   //Get/Set Methods
   //

   /** set the *noteList* attribute
       @param: List
       @return: the current *noteList* attribute
    */
   public List setNoteList(List notes) {
      return (List)((XMLAttribute) attribHash.get("noteList")).setAttribValue(notes);
   }

   /**getNoteList
      @return: the current *noteList* attribute
    */
   public List getNoteList() {
     return (List) ((XMLAttribute) attribHash.get("noteList")).getAttribValue();
   }

   /**getNotes: convenience method that returns the list of notes this object holds
    
    */
   public List getNotes() {
      return getNoteList();
   }

   /** getClassNoNoteChildNodeName
       return: Name of the child node to print in the toXDFOutputStream method when
       an  XDF::Notes object contains NO XDF::Note child objects.
    */
   public String getClassNoNoteChildName() {
      return classNoNoteChildNodeName;
   }

   //
   //Other PUBLIC Methods
   //

   /** Insert a Note object into the list of notes held in this object
      @param: Note to be added
      @return: an XDF::Note object if successfull, null if not.
    */
   public Note addNote(Note note) 
   {

      if (note == null) {
         Log.warn("in Notes.addNote(), the Note passed in is null");
         return null;
      }
      getNoteList().add(note);
      return note;
   }

   /** Remove a Note object the list of notes held in
       this object
      @param: Note to be removed
      @return: true if successful, false if not
    */
   public boolean removeNote(Note what) 
   {
      return removeFromList(what, getNoteList(), "noteList");
   }

   /**Remove the Note object from the list of notes held in
      this object according to passed index number.  
      @param: list index number
      @return: true if successful, false if not
   */
   public boolean removeNote(int index) 
   {
      return removeFromList(index, getNoteList(), "noteList");
   }


 }  //end of Notes Class

 /* Modification History:
 *
 * $Log$
 * Revision 1.1  2000/10/31 17:01:31  thomas
 * Initial Version
 *
 *
 */

