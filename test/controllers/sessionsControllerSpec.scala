package tests

import org.scalatest._
import play.api.test._
import play.api.test.Helpers._
import org.scalatestplus.play._
import play.api.data._
import play.api.data.Forms._
import org.scalatest.mock.MockitoSugar
import org.mockito.Mockito._

import controllers.SessionsController
import models.User
import com.jamesneve.factoryhedgehog.Factory

import scala.concurrent.Future
import play.api.libs.json._
import play.api.mvc.Session
import com.roundeights.hasher.Implicits._

import scala.concurrent.ExecutionContext.Implicits.global

@DoNotDiscover
class SessionsControllerSpec extends ControllerSpec with UserFactory {
  lazy val controller = new SessionsController

  "login action" must {
    "return 200" in {
      (pending)
    }
  }

  "authenticate action" must {
    "create a session with valid login data" in {
      val user = Factory.create("User").asInstanceOf[User]

      // 書いたアーギュメントでcheckUserAuthenticationがコールされたら、関数を使わずにFuture(true)を返す
      // val controllerSpy = spy(controller)
      // doReturn(Future(true)).when(controllerSpy).checkUserAuthentication("banana".sha1.hex, "banana")

      val result = controller.authenticate.apply(FakeRequest()
        .withFormUrlEncodedBody("username" -> "Nishimura",
                                "password" -> "banana"))

      val session = Session.serialize(
        Session.decodeFromCookie(cookies(result).get(Session.COOKIE_NAME))
      )

      status(result) mustEqual 303
      session("username") mustEqual "Nishimura"
    }

    "not create a session with invalid login data" in {
      (pending)
    }
  }
}
