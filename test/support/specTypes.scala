package tests

// Test helpers
import org.scalatest._
import play.api.test._
import play.api.test.Helpers._
import org.scalatestplus.play._
import org.scalatest.concurrent._
import org.scalatest.time.{Millis, Seconds, Span}

import com.jamesneve.factoryhedgehog.CleanerHedgehog

// Base base specs
abstract class BaseUnitSpec extends PlaySpec with PatienceConfiguration {
  implicit val defaultPatience = PatienceConfig(timeout = Span(5, Seconds), interval = Span(500, Millis))
}

abstract class BaseRequestSpec extends PlaySpec

// Base specs
abstract class DaoSpec extends BaseUnitSpec with ScalaFutures with CleanerHedgehog

abstract class ModelSpec extends BaseUnitSpec with ScalaFutures

abstract class ControllerSpec extends BaseUnitSpec with ScalaFutures with CleanerHedgehog

abstract class RequestSpec extends BaseRequestSpec with OneServerPerSuite with OneBrowserPerSuite with FirefoxFactory with ScalaFutures with CleanerHedgehog {
  implicit override lazy val app: FakeApplication = Defaults.fakeAppWithConfiguration
}
