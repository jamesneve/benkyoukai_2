package dao

import scala.concurrent.Future
import java.sql.Date

import language.higherKinds

import play.api.Play
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfig
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._

private[dao] abstract class BaseTable[CaseClassType](tableTag: Tag, tableName: String) extends Table[CaseClassType](tableTag, tableName) {
  def id = column[Long]("id", O.PrimaryKey, O.AutoInc)
}

private[dao] abstract trait ReadDAO[TableClassType <: BaseTable[CaseClassType], CaseClassType] extends HasDatabaseConfig[JdbcProfile] {
  protected val dbConfig = DatabaseConfigProvider.get[JdbcProfile]("default")(Play.current)

  val tableQuery : TableQuery[TableClassType]

  def getAll: Future[Seq[CaseClassType]] = db.run(tableQuery.result)

  def first: Future[Option[CaseClassType]] = db.run(tableQuery.result.headOption)

  def last: Future[Option[CaseClassType]] = db.run(tableQuery.sortBy(_.id.desc).result.headOption)

  def insert(newRecord: CaseClassType): Future[Long] = db.run((tableQuery returning tableQuery.map(_.id)) += newRecord)

  def update(updatedRecord: CaseClassType, id: Long) = db.run(tableQuery.filter(_.id === id).update(updatedRecord))

  def findById(id: Long): Future[Option[CaseClassType]] = db.run(tableQuery.filter(_.id === id).result.headOption)
}

private[dao] abstract trait WriteDAO[TableClassType <: BaseTable[CaseClassType], CaseClassType] extends HasDatabaseConfig[JdbcProfile] { 
  protected val dbConfig = DatabaseConfigProvider.get[JdbcProfile]("default")(Play.current)

  val tableQuery : TableQuery[TableClassType]

  def insert(newRecord: CaseClassType): Future[Long] = db.run((tableQuery returning tableQuery.map(_.id)) += newRecord)

  def update(updatedRecord: CaseClassType, id: Long) = db.run(tableQuery.filter(_.id === id).update(updatedRecord))

  def findById(id: Long): Future[Option[CaseClassType]] = db.run(tableQuery.filter(_.id === id).result.headOption)

  // This method is only for test purposes - please avoid this in production
  // これはテストのためだけです。本番環境で使わないでください
  def hardDeleteById(id: Long): Future[Int] = db.run(tableQuery.filter(_.id === id).delete)
}
