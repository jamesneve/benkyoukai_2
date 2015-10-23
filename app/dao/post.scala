package dao

import play.api.Play
import slick.driver.MySQLDriver.api._
import scala.concurrent.Future

import models.Post

import java.sql.Date

class PostTable(tag: Tag) extends BaseTable[Post] (tag, "posts") {

  def title = column[String]("title")
  def content = column[String]("content")
  def datePublished = column[Date]("date_published")

  def * = (id.?, title, content, datePublished) <> (Post.tupled, Post.unapply)
}

object PostReadDAO extends ReadDAO[PostTable, Post] {
  val tableQuery = TableQuery[PostTable]
}

object PostWriteDAO extends WriteDAO[PostTable, Post] {
  val tableQuery = TableQuery[PostTable]
}
