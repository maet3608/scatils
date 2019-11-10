package quuux.scatils

import java.io.FileWriter
import javax.swing.JOptionPane

/**
 * Error handling and related stuff.
 * @author Stefan Maetschke
 */
object error {

  /** Writes exception stack trace to an error log */
  def writeErrorLog(e:Throwable, logfile:String) {
    val writer:FileWriter = new FileWriter(logfile)
    writer.write(e.toString + "\r\n")
    e.getStackTrace.foreach(l => writer.write("  " + l + "\r\n"))
    writer.close()
  }



  /** Swing Exception handler that displays the exception message
  * within a dialog box and writes an error log file. */
  class ErrorHandler extends Thread.UncaughtExceptionHandler {
    val LOGFILE = "errorlog.txt"

    def uncaughtException(t:Thread, e:Throwable) {
	    onInit(e)
      //ERR.writeErrorLog(e, LOGFILE)
      val logDir = System.getProperty("user.dir")
      val options = Array[Object]("continue", "exit")
      val selection = JOptionPane.showOptionDialog(null, "ERROR: " + e.toString +
        "\nPlease take the file '" + LOGFILE + "' at " + logDir +
        "\nand send it to stefan.maetschke@gmail.com",
        "System Error", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE,
        null, options, options(0))
      cmd.explore(logDir + "\\" + LOGFILE, true)
      cmd.run("explorer mailto:stefan.maetschke@gmail.com")
      if(selection == 1) onExit(e) else onContinue(e)
    }

  /** Called when user selects "continue". Override if necessary */
  def onContinue(e:Throwable) {}

   /** Called when user selects "exit". Override if necessary */
  def onExit(e:Throwable) { System.exit(-1) }

  /** First method called within the exception handler. Override if necessary */
  def onInit(e:Throwable)  {}

  }
}




