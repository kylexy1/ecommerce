package controllers.Api;


import com.fasterxml.jackson.databind.node.ObjectNode;
import controllers.*;
import models.User;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.Objects;

/**
 * Created by Baptiste on 5/29/2018.
 */
public class UserController extends Controller {
    public static Result SendResponse(int action,String message){
        ObjectNode result= Json.newObject();
        result.put("action",action);
        result.put("message",message);
        return ok(result);
    }
  public static Result registerApi(){
      Form<User> usersForm = Form.form(User.class).bindFromRequest();
      User user=new User();

          user.names = usersForm.field("name").value();
          user.email = usersForm.field("email").value();
          user.password = usersForm.field("password").value();
          user.phone = usersForm.field("phone").value();
          user.role = usersForm.field("role").value();
          user.address = usersForm.field("address").value();
          user.save();
          return SendResponse(1, "successfully inserted");
  }


    public static Result login(){
        try{

            Form<User> userForm = Form.form(User.class).bindFromRequest();
            String ema = userForm.get().email;
            String pass = userForm.get().password;
            User user = User.find.where().eq("email", ema).eq("password", pass).findUnique();
            if (user == null) {
                return SendResponse(0,"Invalid email or password");
            }
            else {
                return SendResponse(1,"your are logged in successfully");
            }

        }catch (Exception e){
            return notFound();

        }
    }
}
