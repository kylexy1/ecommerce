package controllers;

import models.Market;
import models.Shop;
import models.ShopCategory;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.html.admin.restaurants;
import views.html.admin.sellers;
import views.html.allRestaurent;
import views.html.allSupermarket;

import java.io.File;
import java.util.List;

/**
 * Created by Baptiste on 6/3/2018.
 */
public class RestaurantController extends Controller {
    public static Result index() {
        List<Shop> supermarket=Shop.find.where().eq("type","Restaurant").findList();
        List<Market> marketList=Market.find.all();
        List<User> users=User.find.where().eq("role","admin").findList();
        return ok(restaurants.render(supermarket,marketList,users));
    }

    public static Result store(){
        Form<Shop> shopForm = Form.form(Shop.class).bindFromRequest();
        long marketId=Long.valueOf(shopForm.field("market").value());
        long userId=Long.valueOf(shopForm.field("user").value());
        Market market=Market.find.byId(marketId);
        User user=User.find.byId(userId);
        Shop shop=new Shop();
        if (market==null || user == null ){
            return ok("something went wrong");
        }
        shop.name=shopForm.field("name").value();
        shop.type="Restaurant";
        shop.openAt = shopForm.field("opening").value();
        shop.closeAt = shopForm.field("closing").value();
        shop.location = shopForm.field("location").value();
        shop.market=market;
        shop.user=user;
        shop.save();
        return redirect(routes.RestaurantController.index());
    }
    public static Result update() {
        Form<Shop> shopForm = Form.form(Shop.class).bindFromRequest();
        long marketId = Long.valueOf(shopForm.field("market").value());
        long userId = Long.valueOf(shopForm.field("user").value());
        Market market = Market.find.byId(marketId);
        User user = User.find.byId(userId);
        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart filePart = body.getFile("photo");
        Shop shop =Shop.find.byId(Long.valueOf(shopForm.field("id").value()));
        if (filePart != null) {
            String fileName = "" + System.currentTimeMillis() + ".png";
            File file = filePart.getFile();
            if (file.renameTo(new File("public/images/shops", fileName))) {
                fileName = "images/shops/" + fileName;
                shop.image = fileName;
            }
        }

        if (market == null || user == null) {
            flash("error","something went wrong");
            return redirect(routes.SuperMarketController.index());
        }
        shop.name = shopForm.field("name").value();
        shop.location = shopForm.field("location").value();
        shop.type="Restaurant";
        shop.market = market;
        shop.openAt = shopForm.field("opening").value();
        shop.closeAt = shopForm.field("closing").value();
        shop.user = user;
        shop.save();
        return redirect(routes.SuperMarketController.index());
    }
    public static Result allResto(){
        List<Shop> shops=Shop.find.where().eq("type","Restaurant").findList();
        return ok(allRestaurent.render(shops));
    }

}
