package admin.controllers

import play.api._
import play.api.mvc._

import dao.common._

import models.common.User

import scala.concurrent.ExecutionContext.Implicits.global

class UserManagementController extends Controller {
  def index = Action.async { implicit request =>
    dao.common.UserReadDAO.getAll.map { users =>
      Ok(views.html.users.index(users))
    }
  }
}
