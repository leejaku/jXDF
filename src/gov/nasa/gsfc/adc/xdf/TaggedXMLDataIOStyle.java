
// XDF TaggedXMLDataIOStyle Class
// CVS $Id$

// TaggedXMLDataIOStyle.java Copyright (C) 2000 Brian Thomas,
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
import java.util.List;
import java.io.OutputStream;
import java.io.IOException; 

/** handles tagged IO
 *  @version $Revision$
 */

public class TaggedXMLDataIOStyle extends XMLDataIOStyle {


  //
  //Fields
  //

  public static final String TagToAxisNodeName = "tagToAxis";

  //init hash size to 10, prevent rehashing, which is expansive
  protected Hashtable tagHash = new Hashtable(10);

  //
  //constructors and related methods
  //

  //no-arg contructor
  public TaggedXMLDataIOStyle(Array parentArray) 
  {
    super(parentArray);
  }

  public TaggedXMLDataIOStyle(Array parentArray, Hashtable InitXDFAttributeTable) 
  {
    super(parentArray,InitXDFAttributeTable);
  }
  
  //
  //Get/Set Methods
  //
  public void setTagHash(Hashtable tagHash) {
    this.tagHash = tagHash;
  }

  public Hashtable getTagHash() {
    return tagHash;
  }

  //
  //Other PUBLIC Methods
  //
/**Set an association between an XDF data tag and axis reference.
 * One day we will hopefully be able to support user defined tags, but for the
 * time being you will have to stick to those specified by the XDF DTD
 * (e.g. "d0","d1", ... "d8"). Note that choosing the wrong tag name will break
 * the current XDF DTD, so go with the defaults (e.g. DONT use this method)
 * if you dont know what you are doing here.
 * @parma: tag - tag , axisId - axisId
 * @return tag value
 */
  public void setAxisTag(String tag, String axisId) {
    //insert in hash table, return tag value
    tagHash.put(axisId, tag);
  }

  /**getXMLDataIOStyleTags: Return an String array of tags to
   *  be used to write tagged data, return the tags in the order of
   * d0, d1, ..., d8
   */

   public String[] getAxisTags() {
   List axisList = getParentArray().getAxes();
    int stop = axisList.size();
    String[] tags = new String[stop];
    String tag;
    String axisId;
    String tempTag;


    int counter = stop;
    for (int i = 0; i < stop; i++) {
      axisId = ((AxisInterface)axisList.get(i)).getAxisId();
      counter--;
      tag = "d" + counter;  //the default tag
      //should it exist, we use whats in the tag hash
      //otherwise we go with the default as singed above
      tempTag = (String) tagHash.get(axisId);
      if (tempTag!=null)
        tag = tempTag;
      tags[i] = tag;
    }
    return tags;
   }

  //
  //PROTECTED Methods
  //
   protected void specificIOStyleToXDF( OutputStream outputstream,String indent)
   throws java.io.IOException
   {

      boolean niceOutput = Specification.getInstance().isPrettyXDFOutput();

      //write out the tags info
      String[] tags = getAxisTags();
      String axisId;
      String tag;

      List axisList = parentArray.getAxes();
/*
      List axisList = getIOAxesOrder(); // actually, for tagged data order isnt important
                                        // in the least, its tagToAxis that is important.
*/
      int numberOfAxes = axisList.size();   
      for (int i = 0; i < numberOfAxes ; i++) {
         axisId = ((AxisInterface) axisList.get(i)).getAxisId();
         tag = tags[i];
         if (niceOutput) {
            writeOut(outputstream, Constants.NEW_LINE);
            writeOut(outputstream, indent);
         }
         writeOut(outputstream, "<" + TagToAxisNodeName + " axisIdRef=\"");
         writeOutAttribute(outputstream, axisId);
         writeOut(outputstream, "\"");
         writeOut(outputstream, " tag=\"");
         writeOutAttribute(outputstream, tag);
         writeOut(outputstream, "\"/>");
      }

      // wrap up newline 
      if (niceOutput) {
         writeOut(outputstream, Constants.NEW_LINE);
      }

   }


   /** Remove an axis tag from the tag hash. This should be PROTECTED
    * and occur only when axis is being removed (ie available to array obj only).
    * @param axisId - axisId to be removed
    * @return tag if successful, null if not
    */
   protected String removeAxisTag(String axisId) {
    return (String) getTagHash().remove(axisId);
   }

}
/* Modification History:
 *
 * $Log$
 * Revision 1.16  2001/07/11 22:35:21  thomas
 * Changes related to adding valueList or removeal of unneeded interface files.
 *
 * Revision 1.15  2001/07/06 19:04:23  thomas
 * toXMLOutputStream and related methods now pass on IOExceptions
 * to the application writer (e.g. they throw the error).
 *
 * Revision 1.14  2001/06/18 21:34:09  thomas
 * changes reflecting new getIOAxesOrder in parent and cleanup
 * of specificIO method output.
 *
 * Revision 1.13  2001/05/10 21:41:52  thomas
 * minor change to specificStyletoXDF. Small
 * change to constructors realated to inheritance
 * scheme.
 *
 * Revision 1.12  2001/05/04 20:23:40  thomas
 * Added Interface stuff.
 *
 * Revision 1.11  2001/05/02 18:16:39  thomas
 * Minor changes related to API standardization effort.
 *
 * Revision 1.10  2001/02/07 18:44:03  thomas
 * Converted XML attribute decl
 * to use constants (final static fields within the object). These
 * are private decl for now. -b.t.
 *
 * Revision 1.9  2000/11/27 22:39:25  thomas
 * Fix to allow attribute text to have newline, carriage
 * returns in them (print out as entities: &#010; and
 * &#013;) This allows files printed out to be read back
 * in again(yeah!). -b.t.
 *
 * Revision 1.8  2000/11/16 20:09:35  kelly
 * fixed documentation.  -k.z.
 *
 * Revision 1.7  2000/11/08 22:30:11  thomas
 * Changed set methods to return void. -b.t.
 *
 * Revision 1.6  2000/11/08 20:12:39  thomas
 * Trimmed down import path to just needed classes -b.t
 *
 * Revision 1.5  2000/10/31 21:44:39  kelly
 * minor fix to *toXDF*, the read opening/closing node is handled by
 * XMLDataIOSytle now.  -k.z.
 *
 * Revision 1.4  2000/10/30 18:17:38  kelly
 * Axis and FieldAxis now share common interface -k.z.
 *
 * Revision 1.3  2000/10/26 14:22:45  kelly
 * fixed some for loops (use a simple variable for end condition now).  fixed a bug in *toXDF*.  -k.z.
 *
 * Revision 1.2  2000/10/17 22:03:54  kelly
 * completed the class.  -k.z.
 *
 * Revision 1.1  2000/10/11 19:05:53  kelly
 * created the class.
 *
 */
