// XDF NoDataException Class
// CVS $Id$

// NoDataException.java Copyright (C) 2000 Brian Thomas,
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


/** this exception is constructed and thrown by Array class
 * and DataCube class when no data is found in the requested cell
 * @version $Revision$
 *
 */

 public class NoDataException extends Exception {
  public NoDataException() {
    super("No data in the requested location");
  }

  public NoDataException(String errorMsg) {
    super(errorMsg);
  }
 }
