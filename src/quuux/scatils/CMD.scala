package quuux.scatils

import java.io.File

/**
 * Collection of functions do things with the command line. 
 * Most of it will only work under Windows but still ...
 * @author Stefan Maetschke
 */

object cmd {
  /** runs the given command, e.g mailto:stefan.maetschke@gmail.com or explorer c:\maet\test.txt */
  def run(command:String) = Runtime.getRuntime.exec(command)
    
  /** opens the given file with the default application (works only under windows) */
  def open(filepath:String) {
    run("explorer \"%s\"".format((new File(filepath)).getAbsolutePath))
  }
  
  /** starts the default application for the given file. Same as open() but uses 'start' cmd */
  def start(filepath:String) {
    val file = new File(filepath)
    run("cmd /c start \"%s\" \"%s\"". format(file.getName,file.getAbsolutePath))
  }
  
    
  /** opens the file explorer for the filepath (file or folder) and selects the file or folder
   * if select == true otherwise opens the file or the folder (works only under windows).
   * see http://support.microsoft.com/kb/152457 */      
  def explore(filepath:String, select:Boolean) {
	  val option = if(select) "/select, " else ""
    run("explorer %s\"%s\"".format(option,(new File(filepath)).getAbsolutePath))
  }    

}