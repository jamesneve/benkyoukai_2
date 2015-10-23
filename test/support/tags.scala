package tests

import org.scalatest.Tag

object SlowTest extends Tag("遅いテスト")
object DbTest extends Tag("このテストはDBを使います")
