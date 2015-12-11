package dao.common

import play.api.Play
import slick.driver.MySQLDriver.api._
import scala.concurrent.Future

import models.common.User

import java.sql.Date

class UserTable(tag: Tag) extends BaseTable[User] (tag, "users") {

  def username = column[String]("username")
  def password = column[String]("password")

  def * = (id.?, username, password) <> (User.tupled, User.unapply)
}

object UserReadDAO extends ReadDAO[UserTable, User] {
  val tableQuery = TableQuery[UserTable]

  def findByUsername(username: String): Future[Option[User]] = 
    db.run(tableQuery.filter(_.username === username).result.headOption)
}

object UserWriteDAO extends WriteDAO[UserTable, User] {
  val tableQuery = TableQuery[UserTable]
}
