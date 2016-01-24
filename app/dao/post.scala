package dao

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfig
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._
import scala.concurrent.Future

import models.Post

import java.sql.Date

class PostTable(tag: Tag) extends Table[Post] (tag, "posts") {

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def title = column[String]("title")
  def content = column[String]("content")
  def datePublished = column[Date]("date_published")

  def * = (id.?, title, content, datePublished) <> (Post.tupled, Post.unapply)
}

object PostReadDAO extends HasDatabaseConfig[JdbcProfile] {
  val dbConfig = DatabaseConfigProvider.get[JdbcProfile]("default")(Play.current)
  val tableQuery = TableQuery[PostTable]

  def getAll: Future[Seq[Post]] = db.run(tableQuery.result)

  def first: Future[Option[Post]] = db.run(tableQuery.result.headOption)

  def last: Future[Option[Post]] = db.run(tableQuery.sortBy(_.id.desc).result.headOption)

  def insert(newRecord: Post): Future[Long] = db.run((tableQuery returning tableQuery.map(_.id)) += newRecord)

  def update(updatedRecord: Post, id: Long) = db.run(tableQuery.filter(_.id === id).update(updatedRecord))

  def findById(id: Long): Future[Option[Post]] = db.run(tableQuery.filter(_.id === id).result.headOption)
}

object PostWriteDAO extends HasDatabaseConfig[JdbcProfile] {
  val dbConfig = DatabaseConfigProvider.get[JdbcProfile]("default")(Play.current)
  val tableQuery = TableQuery[PostTable]

  def insert(newRecord: Post): Future[Long] = db.run((tableQuery returning tableQuery.map(_.id)) += newRecord)

  def update(updatedRecord: Post, id: Long) = db.run(tableQuery.filter(_.id === id).update(updatedRecord))

  def findById(id: Long): Future[Option[Post]] = db.run(tableQuery.filter(_.id === id).result.headOption)

  // This method is only for test purposes - please avoid this in production
  // これはテストのためだけです。本番環境で使わないでください
  def hardDeleteById(id: Long): Future[Int] = db.run(tableQuery.filter(_.id === id).delete)
}
