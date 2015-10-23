package tests

// Test
import play.api.test._
import org.scalatest._
import org.scalatestplus.play._
import play.api.{Play, Application}

// Implicits
import javax.inject.Inject

// Database
import play.api.db.slick.DatabaseConfigProvider
import play.api.db.slick.HasDatabaseConfig
import slick.driver.JdbcProfile
import slick.driver.MySQLDriver.api._
import com.jamesneve.dbcleaner.DBCleanerWide

object Defaults {
  val dbUrl = "jdbc:mysql://localhost:3306/blog_test"
  val dbUsername = "root"
  val dbPassword = ""
  val dbDriver = "com.mysql.jdbc.Driver"

  // Fake application for all tests
  val fakeAppWithConfiguration: FakeApplication =
    FakeApplication(additionalConfiguration = Map(
      "slick.dbs.default.db.user" -> dbUsername,
      "slick.dbs.default.db.connectionTimeout" -> "5 second",
      "slick.dbs.default.db.url" -> dbUrl,
      "slick.dbs.default.db.password" -> dbPassword,
      "slick.dbs.default.db.driver" -> dbDriver,
      "play.evolutions.autoApply" -> true,
      "play.evolutions.autoApplyDowns" -> true,
      "play.mailer.mock" -> "yes",
      "ecacheplugin" -> "disabled"
    ))
}

abstract trait DBSettings {
  // DBCleaner Configuration (mix in DBCleanerWide trait when required)
  val database = Database.forURL(Defaults.dbUrl, 
                                  driver = Defaults.dbDriver, 
                                  user = Defaults.dbUsername, 
                                  password = Defaults.dbPassword)
}

class UnitSpecs extends Suites(
  new PostDaoSpec,
  new UserDaoSpec,
  new SessionsControllerSpec
) with OneAppPerSuite with DBSettings with DBCleanerWide {
  implicit override lazy val app: FakeApplication = Defaults.fakeAppWithConfiguration
}

class RequestSpecs extends Suites(
  new SessionsRequests
) with DBSettings with DBCleanerWide
