package controllers;

import com.avaje.ebean.Expr;
import models.*;
import play.*;
import play.mvc.*;
import views.html.*;
import views.html.admin.market;

import java.util.List;

public class Application extends Controller {

    public static Result index() {
        List<Market> marketList=Market.find.setMaxRows(4).findList();
        if(session().get("count")==null){
            session("count","0");
            session("id","0");
            session("quantity","0");
        }
        return ok(index.render(marketList));
    }



}
