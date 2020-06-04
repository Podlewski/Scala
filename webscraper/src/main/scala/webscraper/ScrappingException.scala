package webscraper

case class ScrappingException(message: String = "error.ScrapingException") extends Exception {

  override def getMessage: String = message
}
