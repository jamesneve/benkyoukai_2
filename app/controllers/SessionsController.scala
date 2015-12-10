package controllers

import javax.inject.Inject

import play.api._
import play.api.mvc._
import play.api.mvc.Results._
import play.api.data._
import play.api.data.Forms._

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import play.api.Play.current
import scala.language.postfixOps

import com.roundeights.hasher.Implicits._

import dao._
import models._

case class UserForm(email: String, password: String)

// このTraitは認証が必要なControllersと混ぜる
trait Secured {
  // セッションからUsernameを取得
  def username(request: RequestHeader): Option[String] = request.session.get(Security.username)

  // 認証持ってない場合はどうする
  def onUnauthorized(request: RequestHeader) = Redirect(routes.SessionsController.login())

  // 魔法です
  def withAuth(f: => String => Request[AnyContent] => Result): EssentialAction = {
    Security.Authenticated(username, onUnauthorized) { user =>
      Action(request => f(user)(request))
    }
  }
}

class SessionsController extends Controller {
  val loginForm: Form[UserWithPermittedParams] = Form {
    mapping(
      "username" -> nonEmptyText, 
      "password" -> nonEmptyText
    )(UserWithPermittedParams.apply)(UserWithPermittedParams.unapply)
  }

  // ユーザウ認証
  def login = Action { implicit request =>
    Ok(views.html.sessions.login(loginForm))
  }

  // ログアウト
  def logout = Action.async { implicit request =>
    Future(Redirect(routes.PostsController.index()).withNewSession)
  }

  // 認証
  def authenticate = Action.async { implicit request =>
    loginForm.bindFromRequest.fold(
      formWithErrors => Future.successful(BadRequest(views.html.sessions.login(formWithErrors))),
      
      loginData => {
        // Usernameで検索して
        UserReadDAO.findByUsername(loginData.username).flatMap { maybeUser =>
          maybeUser match {
            case Some(user) => {

              // パスワードも大丈夫だったらセッションを作成
              checkUserAuthentication(user.password, loginData.password).map { authResult =>
                if(authResult) {
                  Redirect(routes.PostsController.index()).withSession("username" -> user.username)
                } else {
                  BadRequest(views.html.sessions.login(loginForm))
                }
              }
            }
            case _ => Future(BadRequest(views.html.sessions.login(loginForm)))
          }
        }
      }
    )
  }

  def checkUserAuthentication(passwordHash: String, inputPassword: String): Future[Boolean] = {
    Future { 
      scala.util.Random.nextInt(100) match {
        case 50 => false
        case _ => passwordHash == inputPassword.sha1.hex
      }
    }
  }
}
