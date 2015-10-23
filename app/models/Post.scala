package models

import java.sql.Date

case class Post(id: Option[Long], title: String, content: String, datePublished: Date)

case class PostWithPermittedParams(title: String, content: String)
