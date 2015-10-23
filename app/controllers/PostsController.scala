package controllers

import play.api._
import play.api.mvc._
import play.api.data._
import play.api.data.Forms._

import java.sql.Date
import java.util.Calendar

import dao._

import models.Post
import models.PostWithPermittedParams

import scala.concurrent.ExecutionContext.Implicits.global

class PostsController extends Controller with Secured {
  private val postForm: Form[PostWithPermittedParams] = Form(
    mapping(
      "title" -> nonEmptyText,
      "content" -> nonEmptyText
    )(PostWithPermittedParams.apply)(PostWithPermittedParams.unapply)
  )

  // Postの一覧ページを表示
  def index = Action.async { implicit request =>
    val username = request.session.get("username").getOrElse("")
    dao.PostReadDAO.getAll.map { posts =>
      Ok(views.html.posts.index(posts, username))
    }
  }

  // Postの一つを表示
  def show(id: Long) = Action.async { implicit request =>
    dao.PostReadDAO.findById(id).map { maybePost =>
      maybePost match {
        case Some(post) => Ok(views.html.posts.show(post))
        case _ => NotFound(views.html.shared.notfound())
      }
    }
  }

  // 新しいPostの作成フォームを表示
  def add = withAuth { username => implicit request =>
    Ok(views.html.posts.add(postForm))
  }

  // フォームのデータからPostをDBに入力
  def create = withAuth { username => implicit request =>
    val newPostForm = postForm.bindFromRequest()

    newPostForm.fold(
      hasErrors = { postForm =>
        Redirect(routes.PostsController.add)
      },

      success = { postData =>
        PostWriteDAO.insert(
          Post(
            None, 
            postData.title, 
            postData.content,
            new java.sql.Date(Calendar.getInstance().getTime().getTime())
          )
        )
        Redirect(routes.PostsController.index)
      }
    )
  }
}
