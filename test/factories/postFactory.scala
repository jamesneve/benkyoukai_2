package tests

import dao.PostReadDAO
import dao.PostWriteDAO
import models.Post

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
