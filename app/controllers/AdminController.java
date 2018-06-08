package controllers;

import models.Brand;
import models.ProductOrder;
import models.Shop;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.html.admin.adminDashboard;
import views.html.admin.dashboard;
import views.html.admin.newSellerDashboard;
import views.html.admin.shopAdminDashboard;

import java.io.File;
import java.util.List;

/**
 * Created by Baptiste on 5/30/2018.
 */
public class AdminController extends Controller {
    @play.mvc.Security.Authenticated(SecuritySuperAdmin.class)
    public static Result index(){
        List<Shop> sellers=Shop.find.where().eq("type","shop").setMaxRows(5).findList();
        List<User> customers=User.find.where().eq("role","customer").setMaxRows(5).findList();
        List<ProductOrder> orders=ProductOrder.find.setMaxRows(5).findList();
        List<Brand> brands=Brand.find.setMaxRows(5).findList();
        return ok(dashboard.render());
    }
    public static Result newAdmin(){
        return ok(newSellerDashboard.render());
    }
    public static Result shopAdmin(long id){
        return ok(shopAdminDashboard.render());
    }
    public static Result destroy_admin(long id){
        String refererUrl = request().getHeader("referer");
        User user=User.find.byId(id);
        user.delete();
        return redirect(refererUrl);

    }
    public static Result update_admin() {
        Form<User> usersForm = Form.form(User.class).bindFromRequest();
        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart filePart = body.getFile("photo");
        User user = User.find.byId(Long.valueOf(usersForm.field("id").value()));
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
        user.update();
        return redirect(routes.UserController.admin());
    }
}
