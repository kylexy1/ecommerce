package controllers;

import models.Shop;
import models.User;
import org.h2.engine.Session;
import play.*;
import play.data.Form;
import play.mvc.*;

import views.html.*;
import views.html.admin.customer;
import views.html.admin.users;

import java.io.File;
import java.util.List;
import java.util.Objects;

public class UserController extends Controller {

    public static Result index() {
      return ok(login.render());
    }
    public static Result logout() {
        session().clear();
        return ok(login.render());
    }
    public static Result registerView() {
        return ok(register.render());
    }

    public static Result login() {
        try{
            Form<User> userForm = Form.form(User.class).bindFromRequest();
            String ema = userForm.get().email;
            String pass = userForm.get().password;
            User user = User.find.where().eq("email", ema).eq("password", pass).findUnique();
            if (user == null) {
                flash("error", "Invalid email or password");
                return redirect(routes.UserController.index());
            } else if (Objects.equals(user.role, "admin")) {

                session("email", user.email);
                session("user_id", String.valueOf(user.id));
                session("role", user.role);
                Shop shop=Shop.find.where().eq("user_id",user.id).findUnique();
                if(shop==null){
                    session("status", "new");
                    return redirect(routes.AdminController.newAdmin());
                }else {
                    session("status", "exist");
                    session("shop_id",String.valueOf(shop.id));
                    return redirect(routes.AdminController.shopAdmin(shop.id));
                }

            }
            else if (Objects.equals(user.role, "customer")) {
                session("user_id", String.valueOf(user.id));
                return redirect(routes.OrdersController.history(user.id));
            }else {
                session("email", user.email);
                session("user_id", String.valueOf(user.id));
                session("role", user.role);
                return redirect(routes.AdminController.index());
            }

        }catch (Exception e){
            return notFound();

        }
    }

    public static Result register() {
        Form<User> usersForm = Form.form(User.class).bindFromRequest();

                User user = new User();
                user.names = usersForm.field("name").value();
                user.email = usersForm.field("email").value();
                user.password = usersForm.field("password").value();
                user.phone = usersForm.field("phone").value();
                user.gender = usersForm.field("gender").value();
                user.profile="images/users/default.png";
        if(usersForm.field("seller").value()==null){
            user.role = "customer";
        }else{
            user.role = "admin";
        }
        user.address = usersForm.field("address").value();
        user.save();
        return redirect(routes.UserController.index());
    }
    public static Result store_admin() {
        Form<User> usersForm = Form.form(User.class).bindFromRequest();
        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart filePart = body.getFile("photo");
        User user = new User();
        if (filePart != null) {
            String fileName = "" + System.currentTimeMillis() + ".png";
            File file = filePart.getFile();
            if (file.renameTo(new File("public/images/users", fileName))) {
                fileName = "images/users/" + fileName;
                user.profile=fileName;
            }
        }
                user.names = usersForm.field("names").value();
                user.email = usersForm.field("email").value();
                user.password = usersForm.field("password").value();
                user.phone = usersForm.field("phone").value();
                user.gender = usersForm.field("gender").value();

                user.role = "admin";
                user.address = usersForm.field("address").value();
                user.save();
                return redirect(routes.UserController.admin());
    }
    public static Result customer(){
        List<User> customers=User.find.where().eq("role","customer").setMaxRows(1).findList();
        return ok(customer.render(customers));
    }
    public static Result admin(){
        List<User> admins=User.find.where().eq("role","admin").findList();
        return ok(users.render(admins));
    }
    public static Result employee(){
        List<User> admins=User.find.where().eq("role","employee").findList();
        return ok(users.render(admins));
    }
    public static Result customer_editProfile() {
        if (session().get("user_id") != null) {
            User user = User.find.byId(Long.valueOf(session().get("user_id")));
            return ok(user_profile.render(user));
        }
        return redirect(routes.UserController.index());
    }
    public static Result passwordForm(){

        return ok(change_password.render());
    }
    public static Result update_user_profile(){
        if (session().get("user_id") != null) {
            Form<User> usersForm = Form.form(User.class).bindFromRequest();
            User user = User.find.byId(Long.valueOf(session().get("user_id")));
            user.names = usersForm.field("name").value();
            user.email = usersForm.field("email").value();
            user.password = usersForm.field("password").value();
            user.phone = usersForm.field("phone").value();
            user.gender = usersForm.field("gender").value();
            user.update();
            return redirect(routes.UserController.customer());
        }
        flash("info","login in order to change your account");
        return redirect(routes.UserController.index());
    }
    public static Result change_password(){
        if (session().get("user_id") != null) {
            Form<User> usersForm = Form.form(User.class).bindFromRequest();
            User user = User.find.byId(Long.valueOf(session().get("user_id")));
            if (usersForm.field("new").value().equals(usersForm.field("repeat").value()) && usersForm.field("old").value().equals(user.password)){
              user.password=usersForm.field("new").value();
                user.update();
            }
            flash("error","password mismatch");
            return redirect(routes.UserController.passwordForm());
        }
        flash("info","login in order to change your account");
        return redirect(routes.UserController.index());
    }


}
