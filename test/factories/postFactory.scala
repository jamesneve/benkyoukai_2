package tests

import dao.common.PostReadDAO
import dao.common.PostWriteDAO
import models.common.Post

import com.jamesneve.factoryhedgehog.Factory
import com.jamesneve.factoryhedgehog.FactoryDAO

import scala.reflect.runtime.universe._

trait PostFactory {
  // FactoryDAOを定義
  // private lazy val factoryDao = new FactoryDAO[Post, Long](
  //
  //
  //  )

  // 作りたいオブジェクト
  //private val post = 

  // Factoryに追加してから、「User」というストリングだけわかったら、いつでもこのユーザーを作成できる
  // Factory.add("Post", new Factory(
  //
  //
  //
  // ))
}
