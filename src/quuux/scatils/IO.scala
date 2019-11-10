package quuux.scatils

import java.io.{FileInputStream, FileOutputStream, File}

/**
 * Utility functions related to file io.
 * @author Stefan Maetschke
 */
object io {
	
  /** copies a file */
  def copy(src:File, dest:File):Unit = {
    val fos = new FileOutputStream(dest).getChannel
    val fis = new FileInputStream(src).getChannel
    fos.transferFrom(fis, 0, fis.size)
    fos.close()
    fis.close()
  }

  /** copies a file */
  def copy(srcPath:String, destPath:String):Unit  =
    copy(new File(srcPath), new File(destPath))

}