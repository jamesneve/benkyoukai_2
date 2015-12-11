package tests

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.concurrent.Future
import org.scalatest.concurrent._
import org.scalatest.DoNotDiscover

import dao.common.PostReadDAO
import models.common.Post

import com.jamesneve.factoryhedgehog.Factory

import play.api.libs.concurrent.Execution.Implicits._

@DoNotDiscover
class PostDaoSpec extends DaoSpec with PostFactory {
  "PostDAO" must {
    "write some tests" in {
      (pending)
    }
  }
}
