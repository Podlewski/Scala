package webscraper

import net.ruippeixotog.scalascraper.browser.JsoupBrowser
import net.ruippeixotog.scalascraper.dsl.DSL._
import net.ruippeixotog.scalascraper.scraper.ContentExtractors.{element, elementList}
import webscraper.Utils.{proxyHost, retry, setProxy}

import scala.util.{Failure, Success, Try}

object ProxyTest extends App {

  setProxy()
  testProxy()

  def testProxy(verbose: Boolean = false): Unit = {
    val browser = JsoupBrowser()
    val site = retry(10)(browser.get("http://www.pokapoka.pl/"))

    val ip = (site >> elementList(".new_content > div")).take(1)

    var results = ip map { e =>
      val result = Try(e >> element("span"))
      result match {
        case Success(a) => a.text
        case Failure(_) => e.text
      }
    }

    if (verbose) {
      val details = (site >> elementList("tr > td")).take(10)
      results ++= details map { e =>
        val result = Try(e >> element("b"))
        result match {
          case Success(a) => a.text
          case Failure(_) => e.text
        }
      }

      println(results.mkString("\n") + '\n')
    }

    if (results.take(1) == List(proxyHost)) {
      println("Proxy is ok")
    }
  }
}
