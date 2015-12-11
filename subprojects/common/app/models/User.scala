package models.common

case class User(id: Option[Long], username: String, password: String)

case class UserWithPermittedParams(username: String, password: String)
