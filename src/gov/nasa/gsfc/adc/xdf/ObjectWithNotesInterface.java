
// XDF ObjectWithNotesInterface Class
// CVS $Id$

// ObjectWithNotesInterface.java Copyright (C) 2001 Brian Thomas,
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

import java.util.List;

/**  ObjectWithNotesInterface aggregates the common signature of XDF derived
 *   data structures that have notes.
 */

public interface ObjectWithNotesInterface {

   public boolean addNote (NoteInterface note);
   public boolean removeNote (NoteInterface note);
   public boolean removeNote (int index);
   public void setNotes(List notesList);
   public List getNotes();

}

/* Modification History:
 *
 * $Log$
 * Revision 1.2  2001/06/26 21:22:25  huang
 * changed return type to boolean for all addObject()
 *
 * Revision 1.1  2001/05/04 20:05:53  thomas
 * Initial version
 *
 *
 */

