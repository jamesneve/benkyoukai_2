package tests

import dao.common.UserReadDAO
import dao.common.UserWriteDAO
import models.common.User

import com.jamesneve.factoryhedgehog.Factory
import com.jamesneve.factoryhedgehog.FactoryDAO

import scala.reflect.runtime.universe._
import com.roundeights.hasher.Implicits._

trait UserFactory {
  // FactoryDAOを定義
  private lazy val factoryDao = new FactoryDAO[User, Long](
    UserWriteDAO.insert,
    UserReadDAO.findById,
    Some(UserWriteDAO.hardDeleteById)
  )

  // 作りたいオブジェクト
  private val user = User(None, "Nishimura", "banana".sha1.hex)

  // Factoryに追加してから、「User」というストリングだけわかったら、いつでもこのユーザーを作成できる
  Factory.add("User", new Factory(
    user,
    typeOf[User],
    Some(factoryDao)
  ))
}
