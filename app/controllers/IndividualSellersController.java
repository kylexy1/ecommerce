package controllers;

import com.avaje.ebean.Expr;
import models.Market;
import models.Shop;
import models.ShopCategory;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.html.admin.newSeller;
import views.html.admin.restaurants;
import views.html.admin.sellers;

import java.io.File;
import java.util.List;

/**
 * Created by Baptiste on 6/3/2018.
 */
public class IndividualSellersController extends Controller {
    public static Result store(){
        String refererUrl = request().getHeader("referer");
        Form<Shop> shopForm = Form.form(Shop.class).bindFromRequest();
        long marketId=Long.valueOf(shopForm.field("market").value());
        long userId=Long.valueOf(shopForm.field("user").value());

        Market market=Market.find.byId(marketId);
        User user=User.find.byId(userId);
        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart filePart = body.getFile("photo");
        Shop shop = new Shop();
        if (filePart != null) {
            String fileName = "" + System.currentTimeMillis() + ".png";
            File file = filePart.getFile();
            if (file.renameTo(new File("public/images/shops", fileName))) {
                fileName = "images/shops/" + fileName;
                shop.image=fileName;
            }
        }

                if (market == null || user == null) {
                    return ok("something went wrong");
                }

                if (market == null || user == null) {
                    return ok("something went wrong");
                }
                shop.name = shopForm.field("name").value();
                shop.type = "Shop";
                shop.openAt = shopForm.field("opening").value();
                shop.closeAt = shopForm.field("closing").value();
                shop.location = shopForm.field("location").value();

                shop.market = market;
                shop.user = user;
                shop.save();

        return redirect(refererUrl);
    }
    public static Result index(){
        List<Shop> shops=Shop.find.where().eq("type","shop").eq("market.id",1L).findList();
        List<Market> marketList=Market.find.all();
        List<User> users=User.find.where().eq("role","admin").findList();
        return ok(sellers.render(shops,marketList,users));
    }
    public static Result newSeller(){
        List<Shop> shops=Shop.find.where().eq("type","shop").findList();
        List<Market> marketList=Market.find.all();
        return ok(newSeller.render(shops,marketList));
    }
    public static Result store_self(){
        Form<Shop> shopForm = Form.form(Shop.class).bindFromRequest();
        long marketId=Long.valueOf(shopForm.field("market").value());
        long userId=Long.valueOf(shopForm.field("user").value());

        Market market=Market.find.byId(marketId);
        User user=User.find.byId(userId);
        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart filePart = body.getFile("photo");
        Shop shop = new Shop();
        if (filePart != null) {
            String fileName = "" + System.currentTimeMillis() + ".png";
            File file = filePart.getFile();
            if (file.renameTo(new File("public/images/shops", fileName))) {
                fileName = "images/shops/" + fileName;

                if (market == null || user == null) {
                    return ok("something went wrong");
                }

                if (market == null || user == null) {
                    return ok("something went wrong");
                }
                shop.name = shopForm.field("name").value();
                shop.type = "Shop";
                shop.openAt = shopForm.field("opening").value();
                shop.closeAt = shopForm.field("closing").value();
                shop.location = shopForm.field("location").value();
                shop.image=fileName;
                shop.market = market;
                shop.user = user;
                shop.save();
                session("shop_id",String.valueOf(shop.id));
                session("status", "exist");

            }
        }
        return redirect(routes.AdminController.shopAdmin(shop.id));
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
        shop.type = "Shop";
        shop.market = market;
        shop.openAt = shopForm.field("opening").value();
        shop.closeAt = shopForm.field("closing").value();
        shop.user = user;
        shop.save();
        return redirect(routes.SuperMarketController.index());
    }
}
