package tests

import org.scalatest._
import play.api.test._
import play.api.test.Helpers._
import org.scalatestplus.play._
import play.api.libs.ws._
import play.api.mvc._
import Results._

import models.User

import org.scalatest.DoNotDiscover

import com.jamesneve.factoryhedgehog.Factory

@DoNotDiscover
class SessionsRequests extends RequestSpec with UserFactory {
  "Login page" must {
    "log user in and out" in {
      val user = Factory.create("User").asInstanceOf[User]

      go to (s"http://localhost:$port/login")
      pageSource.contains("Login") mustBe true

      click on name("username")
      textField("username").value = "Nishimura"
      click on name("password")
      pwdField("password").value = "banana"
      click on name("submit")

      eventually {
        pageSource.contains("logged in") mustBe true
        pageSource.contains(user.username) mustBe true

        click on id("logout")
        eventually {
          pageSource.contains("logged in") mustBe false
        }
      }
    }
  }
}
