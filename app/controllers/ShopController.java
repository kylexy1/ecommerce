package controllers;

import models.*;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.*;

import java.util.List;

/**
 * Created by Baptiste on 5/30/2018.
 */
public class ShopController extends Controller {
    public static Result index(){
        return ok();
    }
    public static Result store(){
        Form<Shop> shopForm = Form.form(Shop.class).bindFromRequest();
        long marketId=Long.valueOf(shopForm.field("market").value());
        long userId=Long.valueOf(shopForm.field("user").value());
        long categoryId=Long.valueOf(shopForm.field("category").value());
        Market market=Market.find.byId(marketId);
        User user=User.find.byId(userId);
        ShopCategory shopCategory =ShopCategory.find.byId(categoryId);
        Shop shop=new Shop();
        if (market==null || user == null || shopCategory ==null){
            return ok("something went wrong");
        }
        shop.name=shopForm.field("name").value();
        shop.type=shopForm.field("type").value();
        shop.openAt = shopForm.field("opening").value();
        shop.closeAt = shopForm.field("closing").value();
        shop.market=market;
        shop.user=user;
        shop.save();
        return ok("success");
    }

    public static Result addproduct(){
        return ok(addProduct.render());
    }


}
