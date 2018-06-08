package controllers;

import models.Market;
import models.Shop;
import models.User;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.admin.sellers;

import java.util.List;

/**
 * Created by Baptiste on 6/3/2018.
 */
public class SellersController extends Controller {
    public static Result index(){
        List<Shop> shops=Shop.find.where().eq("type","IndividualSeller").findList();
        List<Market> marketList=Market.find.all();
        List<User> users=User.find.where().eq("role","admin").findList();
        return ok(sellers.render(shops,marketList,users));
    }

}
