package controllers;

import models.Market;
import models.Shop;
import models.ShopCategory;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;

import java.util.List;

/**
 * Created by Baptiste on 5/30/2018.
 */
public class ShopCategoryController extends Controller {

    public static Result store(){
        Form<ShopCategory> request = Form.form(ShopCategory.class).bindFromRequest();
        ShopCategory shopCategory=new ShopCategory();
        shopCategory.name=request.field("name").value();
        shopCategory.save();
        return ok("success");
    }
    public static Result shoptList(){
        Form<Shop> shopForm = Form.form(Shop.class).bindFromRequest();
        long id=Long.valueOf(shopForm.field("id").value());
        List<Shop> shops=Shop.find.where().eq("market_id",id).findList();
        if (shops.size()<=0) {
            return ok("no product Available");
        }
        return ok(Json.toJson(shops));
    }
}
