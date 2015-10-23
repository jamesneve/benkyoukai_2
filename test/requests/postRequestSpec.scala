package tests

import org.scalatest._
import play.api.test._
import play.api.test.Helpers._
import org.scalatestplus.play._
import play.api.libs.ws._
import play.api.mvc._
import Results._

import models.Post

import org.scalatest.DoNotDiscover

import com.jamesneve.factoryhedgehog.Factory

@DoNotDiscover
class PostsRequests extends RequestSpec with PostFactory {
  "Index page" must {
    "implement some tests" in {
      (pending)
    }
  }
}
