package webscraper

case class ToManyOffersException(message: String = "error.ToManyOffersException") extends Exception {
  override def getMessage: String = message

}