

// XDF Log Class
// CVS $Id$

// Log.java Copyright (C) 2000 Brian Thomas,
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

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.io.IOException;

import java.util.Properties;

/**
   General purpose class to handle various levels of program messaging.
   There are four priority levels for
   messages--error|warn|debug|info with priority error>warn>debug>info. The
   default setting for msg handling is that only error msgs are printed out to
   standard System.out.  The user can supply a configuration file for this Log
   to read from.  A sample of the configuration file is included--XDFLogConfig.
   contributor: Kelly Zeng (kelly.zeng@commerceone.com)
   @version $Revision$
 */

 /** SAMPLE XDFLogConfig file :  

 # this is a sample of the XDF log configuration file
 # Output could be either a file for the log messages to write to or
 # standard System.out, for example
 # Output=C:/Data/XDF/gov/nasa/gsfc/adc/xdf/XDFLog or
 # Output=System.out

 Output=System.out

 # four levels of priority, error>warn>debug>info
 # 0: all levels are printed
 # 1: priority >=debug are printed
 # 2: priority >=warn are printed
 # 3: priority >=error are printed

 Priority=0

 */

public class Log {
  //
  //Fields
  //

  public static final OutputStream DEFAULT_OUTPUTSTREAM = System.out;
  public static final int DEFAULT_PRIORITY = Priority.ERROR;


  /**hold the priority, initial is Priority.ERROR;
  */
  protected static int priority = DEFAULT_PRIORITY;
  /**hold the output stream, initial is System.out
   */
  protected static OutputStream output = DEFAULT_OUTPUTSTREAM;


  //
  //constructor and related methods
  //
  /**
   * no arg constructor
   */
   public Log () {
   }

  //
  //Other Public Methods
  //

  /**
   *  read in the properties from the configuration file
   */
   public static void configure(String configFileName) {
    Properties props = new Properties();  //the property hashtable
    try {
      FileInputStream istream = new FileInputStream(configFileName);
      props.load(istream);
      istream.close();
    }
    catch (IOException e) {
/*
      // These lines just are irritating, commenting out 
      System.err.println("Could not read configuration file [" + configFileName+ "].");
      System.err.println("Ignoring configuration file [" + configFileName+"].");
      System.err.println("using default config");
*/
      defaultConfig();
      return;
    }
    // If we reach here, then the config file is alright.
    configure(props);
  }

  /**
   *  set the default configuration
   * Output=System.out
   * Priority=4
   */

  public static void defaultConfig() {
    output = DEFAULT_OUTPUTSTREAM;
    priority = DEFAULT_PRIORITY;
  }

  /**
   *  Read configuration options from <code>properties</code>.
  */

   public static void configure(Properties properties){

    String outputFormat = properties.getProperty("Output");
    String strPri = properties.getProperty("Priority");
    String strTimestamp = properties.getProperty("Timestamp");
    if (strPri == null)
      priority = DEFAULT_PRIORITY;  //default priority
    else
      priority = Integer.parseInt(properties.getProperty("Priority"));

    if(outputFormat != null) {
      if ( outputFormat.equalsIgnoreCase("System.out")) {  //output is System.out
        output = System.out;
      } else if ( outputFormat.equalsIgnoreCase("System.err")) {  //output is System.err
        output = System.err;
      }
      else {  //output is a file
        try {
          output = new FileOutputStream(outputFormat, true);  //write to a file, using append
        }
        catch( FileNotFoundException e) {
          System.out.println("error opening the log file to write to");
          System.out.println("logs are by default printed to System.out");
          // use default output
          output = DEFAULT_OUTPUTSTREAM;
        }
      }
    }
    else { // default output
      output = new PrintStream(DEFAULT_OUTPUTSTREAM);
    }
  }

  /** Log a information level message.
  */
  public static void info (String msg) {

    // check priority
    if (priority > Priority.INFO)
      return;

    try {
      output.write(msg.getBytes());
    }
    catch (IOException e) {
      System.err.println("error in Log.info()");
      e.printStackTrace();
    }
  }

  /** Log a information level message as a single line.
  */
  public static void infoln (String msg) {

    // check priority
    if (priority > Priority.INFO)
      return;

    // write message
    info(msg);

    // now print the newline
    printNewline();

  }

  /** Log a debugging level message.
   */
  public static void debug (String msg) {

    // check priority
    if (priority > Priority.DEBUG)
      return;

    try {
      output.write(msg.getBytes());
    }
    catch (IOException e) {
      System.err.println("error in Log.debug()");
      e.printStackTrace();
    }

  }

  /** Log a debugging level message as single line.
   */
  public static void debugln (String msg) {

    // check priority
    if (priority > Priority.DEBUG)
      return;

    // print debugging message
    debug(msg);

    // now print the newline
    printNewline();

  }

  /** Log a warning level message.
   */
  public static void warn (String msg) {

    // check priority
    if (priority > Priority.WARN)
      return;

    try {
      output.write(msg.getBytes());
    }
    catch (IOException e) {
      System.err.println("error in Log.warn()");
      e.printStackTrace();
    }
  }

  /** Log a warning level message as a single line.
   */
  public static void warnln (String msg) {

    // check priority
    if (priority > Priority.WARN)
      return;

    // print msg
    warn(msg);

    // print newline
    printNewline();

  }

  /** Log an error level message.
   */
  public static void error (String msg) {

    // check priority
    if (priority > Priority.ERROR)
      return;

    // print message
    try {
      output.write(msg.getBytes());
      // output.write(Constants.NEW_LINE.getBytes());
    }
    catch (IOException e) {
      System.err.println("error in Log.error()");
      e.printStackTrace();
    }
  }

  /** Log an error level message as a single line.
   */

  public static void errorln (String msg) {

    // check priority
    if (priority > Priority.ERROR)
      return;

    // print message
    error(msg);

    // print newline
    printNewline();

  }

  /** release the resources held by Log
   * i.e., close the output if it is FileOutputStream
   */
  public static void close() {
    if (output.getClass().getName().equalsIgnoreCase("java.io.FileOutputStream")) {
      try {
        output.close();
      }
      catch (IOException e) {
        System.out.println("in Log.close().  error");
        e.printStackTrace();
        return;
      }
    }
  }

  /** Print a stacktrace to the current error outputstream.
   */
  public static void printStackTrace (Exception e) {
      e.printStackTrace((PrintStream) output);
  }

  public static void printStackTrace (Throwable t) {
      t.printStackTrace((PrintStream) output);
  }

  //
  // Private Methods
  //

  /** prints a newline to the output stream as appropriate
      for the platform.
   */
  private static void printNewline () {
    try {
      output.write(Constants.NEW_LINE.getBytes());
    }
    catch (IOException e) {
      System.err.println("error in Log.warn()");
      e.printStackTrace();
    }
  }

}

/**
 * defines constants for priorities in Log class: error|warn|debug|info
 */
class Priority {
  public static final int INFO = 0;
  public static final int DEBUG = 1;
  public static final int WARN = 2;
  public static final int ERROR = 3;
}
