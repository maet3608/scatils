package quuux.scatils

import java.util.{ Locale }
import java.util.Properties
import java.io.{ File, FileInputStream }

/**
 * Internationalization
 */
object i18n {
  private var DefaultBundleName = "i18n/Messages"
  private var ErrorText = "<<<I18N TRANSLATION MISSING FOR '%s'>>>"
  private var bundle = new Properties()
  private var currentlocale: Locale = _

  setLocale()  // Initialize with system local.

  /** Returns the text for the given key or an error text. */
  def apply(key: String) = bundle.getProperty(key) match {
    case null      => ErrorText format key
    case s: String => s
  }

  /** Returns the formatted text for the given key and args or an error text. */
  def apply(key: String, args: Any*) = bundle.getProperty(key) match {
    case null      => ErrorText format key
    case s: String => s.format(args: _*)
  }

  /** Takes locale in the format "de_DE", "en_US". Default is the locale of the system. */
  def setLocale(localeStr: String = "", bundleName: String = DefaultBundleName) {
    currentlocale = if (localeStr == "") Locale.getDefault else locale(localeStr)
    bundle.load(new FileInputStream(bundlePath(bundleName, currentlocale)))
  }

  /** Returns the bundle path for the given local if it exits otherwise default is used. */
  private def bundlePath(bundleName: String, locale: Locale) = {
    val path = bundleName + "_" + locale.toString + ".properties"
    if ((new File(path)).exists) path else bundleName + ".properties"
  }

  /** Returns the currently set locale */
  def locale = currentlocale
  
  /** Returns a locale for the given specifier which has to be in format "de_DE", "en_US", etc */
  def locale(localeStr: String) = {
    val Array(language, country) = localeStr.split('_')
    new Locale(language, country)
  }
  
  /** Returns the default locale of the system */
  def defaultLocal = Locale.getDefault

  /** Pimps strings to support internationalization. */
  implicit def i18nString(str: String) = new { 
    def i: String = i18n(str) 
    def i(args: Any*): String = i18n(str, args: _*)
  }  
  
  // Usage example.
  def main(args: Array[String]) = {
    //setLocale("en_US")
    setLocale("de_DE")

    println(locale.toString)
    println(locale.getDisplayLanguage.toString)
    println(locale.getDisplayCountry)
    
    println(i18n("welcome"))
    println("welcome".i)
    println("welcome"i)
    
    println(i18n("luckyNumber", 7))
    println("luckyNumber".i(7))
    println("luckyNumber" i 7)
    
    println(i18n("missing text"))
  }
} 