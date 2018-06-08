package controllers;


import play.mvc.Http;
import play.mvc.Result;
import play.mvc.Security;
import views.html.Unauthorized;

/**
 * Created by Baptiste on 6/8/2018.
 */
public class SecuritySuperAdmin extends Security.Authenticator {
    @Override
    public String getUsername(Http.Context ctx){
        String role=ctx.session().get("role");
        if (role !=null || role=="admin" || role=="superAdmin"){
            return role;
        }

        return null;
    }
    @Override
    public Result onUnauthorized(Http.Context ctx){
        return ok(Unauthorized.render());
    }
}
