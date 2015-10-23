package tests

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.concurrent.Future
import org.scalatest.concurrent._
import org.scalatest.DoNotDiscover

import dao.UserReadDAO
import models.User

import com.jamesneve.factoryhedgehog.Factory

import play.api.libs.concurrent.Execution.Implicits._

@DoNotDiscover
class UserDaoSpec extends DaoSpec with UserFactory {
  "UserDAO" must {
    "be able to get a user by id from the DB" taggedAs(DbTest) in {
      // Factoryを使ってUserをDBに入れる
      val user = Factory.create("User").asInstanceOf[User]

      // ユーザーが出ることを確認
      val f = UserReadDAO.findById(user.id.get)
      whenReady(f) { result =>
        result.get.username mustBe user.username
      }
    }

    "be able to get the last user by ID" taggedAs(DbTest) in {
      // ユーザーを二つDBに入れます
      val user1 = Factory.create("User").asInstanceOf[User]
      val user2 = Factory.create("User", Map("username" -> "Wakamatsu")).asInstanceOf[User]

      // 最後のユーザーが出ることを確認
      val f = UserReadDAO.last
      whenReady(f) { result =>
        result.get.username mustBe "Wakamatsu"
      }
    }
  }
}
