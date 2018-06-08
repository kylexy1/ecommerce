package controllers;

import models.*;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.html.admin.supermarket;
import views.html.allSupermarket;
import views.html.front_supermarket;

import java.io.File;
import java.util.List;

/**
 * Created by Baptiste on 6/3/2018.
 */
public class SuperMarketController extends Controller {
    public static Result index() {
        List<Shop> supermarket = Shop.find.where().eq("type", "SuperMarket").findList();
        List<Market> marketList = Market.find.all();
        List<User> users = User.find.where().eq("role", "admin").findList();
        return ok(views.html.admin.supermarket.render(supermarket, marketList, users));
    }

    public static Result store() {
        try {
            Form<Shop> shopForm = Form.form(Shop.class).bindFromRequest();
            long marketId = Long.valueOf(shopForm.field("market").value());
            long userId = Long.valueOf(shopForm.field("user").value());
            Market market = Market.find.byId(marketId);
            User user = User.find.byId(userId);
            Http.MultipartFormData body = request().body().asMultipartFormData();
            Http.MultipartFormData.FilePart filePart = body.getFile("photo");
            if (filePart != null) {
                String fileName = "" + System.currentTimeMillis() + ".png";
                File file = filePart.getFile();
                if (file.renameTo(new File("public/images/shops", fileName))) {
                    fileName = "images/shops/" + fileName;
                    Shop shop = new Shop();
                    if (market == null || user == null) {
                        flash("error","something went wrong");
                        return redirect(routes.SuperMarketController.index());
                    }
                    shop.name = shopForm.field("name").value();
                    shop.location = shopForm.field("location").value();
                    shop.type = "SuperMarket";
                    shop.market = market;
                    shop.openAt = shopForm.field("opening").value();
                    shop.closeAt = shopForm.field("closing").value();
                    shop.user = user;
                    shop.image = fileName;
                    shop.save();
                    return redirect(routes.SuperMarketController.index());
                }
            }
            return redirect(routes.SuperMarketController.index());
        }
    catch (Exception e){
        flash("error","an error occurred");
        return notFound();
    }

    }

    public static Result productList(long id) {
        List<Product> products = Product.find.where().eq("shop_id", id).findList();
        return ok(views.html.admin.products.render(products));
    }
    public static Result front_superMarket(long id) {
        List<Product> products = Product.find.where().eq("shop_id", id).findList();
        Shop shop =Shop.find.byId(id);
        return ok(front_supermarket.render(products,shop));
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
                shop.type = "SuperMarket";
                shop.market = market;
                shop.openAt = shopForm.field("opening").value();
                shop.closeAt = shopForm.field("closing").value();
                shop.user = user;
                shop.save();
                return redirect(routes.SuperMarketController.index());
    }
    public static Result destroy(long id){
        Shop shop=Shop.find.byId(id);
        shop.delete();
        return redirect(routes.SuperMarketController.index());
    }
    public static Result allsupermarket(){
        List<Shop> shops=Shop.find.where().eq("type","SuperMarket").findList();
        return ok(allSupermarket.render(shops));
    }
}
