package dao

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfig
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._
import scala.concurrent.Future

import models.User

import java.sql.Date

class UserTable(tag: Tag) extends Table[User] (tag, "users") {

  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
  def username = column[String]("username")
  def password = column[String]("password")

  def * = (id.?, username, password) <> (User.tupled, User.unapply)
}

object UserReadDAO extends HasDatabaseConfig[JdbcProfile] {
  val dbConfig = DatabaseConfigProvider.get[JdbcProfile]("default")(Play.current)
  val tableQuery = TableQuery[UserTable]

  def getAll: Future[Seq[User]] = db.run(tableQuery.result)

  def first: Future[Option[User]] = db.run(tableQuery.result.headOption)

  def last: Future[Option[User]] = db.run(tableQuery.sortBy(_.id.desc).result.headOption)

  def insert(newRecord: User): Future[Long] = db.run((tableQuery returning tableQuery.map(_.id)) += newRecord)

  def update(updatedRecord: User, id: Long) = db.run(tableQuery.filter(_.id === id).update(updatedRecord))

  def findById(id: Long): Future[Option[User]] = db.run(tableQuery.filter(_.id === id).result.headOption)

  def findByUsername(username: String): Future[Option[User]] = 
    db.run(tableQuery.filter(_.username === username).result.headOption)
}

object UserWriteDAO extends HasDatabaseConfig[JdbcProfile] {
  val dbConfig = DatabaseConfigProvider.get[JdbcProfile]("default")(Play.current)
  val tableQuery = TableQuery[UserTable]

  def insert(newRecord: User): Future[Long] = db.run((tableQuery returning tableQuery.map(_.id)) += newRecord)

  def update(updatedRecord: User, id: Long) = db.run(tableQuery.filter(_.id === id).update(updatedRecord))

  def findById(id: Long): Future[Option[User]] = db.run(tableQuery.filter(_.id === id).result.headOption)

  // This method is only for test purposes - please avoid this in production
  // これはテストのためだけです。本番環境で使わないでください
  def hardDeleteById(id: Long): Future[Int] = db.run(tableQuery.filter(_.id === id).delete)
}
