package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import java.sql.Date
import java.util.Calendar

import dao._
import models._

import com.roundeights.hasher.Implicits._

import scala.concurrent.ExecutionContext.Implicits.global

class UsersController extends Controller {
  private val userForm: Form[UserWithPermittedParams] = Form(
    mapping(
      "username" -> nonEmptyText,
      "password" -> nonEmptyText
    )(UserWithPermittedParams.apply)(UserWithPermittedParams.unapply)
  )

  // 新しいUserの作成フォームを表示
  def add = Action { implicit request => 
    Ok(views.html.users.add(userForm))
  }

  // フォームのデータからUserをDBに入力
  def create = Action { implicit request =>
    val newPostForm = userForm.bindFromRequest()

    newPostForm.fold(
      hasErrors = { userForm =>
        Redirect(routes.UsersController.add)
      },

      success = { userData =>
        UserWriteDAO.insert(
          User(
            None,
            userData.username, 
            userData.password.sha1.hex
          )
        ).map { result =>
          println(result)
        }
        Redirect(routes.PostsController.index)
      }
    )
  }
}
