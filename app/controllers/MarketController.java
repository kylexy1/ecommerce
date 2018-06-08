package controllers;

import models.Market;
import models.Shop;
import models.ShopCategory;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.html.admin.addMarket;
import views.html.admin.market;
import views.html.admin.marketSellers;
import views.html.allMarkets;
import views.html.front_market;

import java.io.File;
import java.util.List;

/**
 * Created by Baptiste on 5/30/2018.
 */
public class MarketController extends Controller {
    public static Result index() {
        List<Market> marketList=Market.find.all();
        return ok(market.render(marketList));
    }
    public static Result allmarkets() {
        List<Market> marketList=Market.find.all();
        return ok(allMarkets.render(marketList));
    }

    public static Result getAddMarket() {
        return ok(addMarket.render());
    }

    public static Result store() {
        Form<Market> marketForm = Form.form(Market.class).bindFromRequest();
        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart filePart = body.getFile("photo");
        if (filePart != null) {
            String fileName = "" + System.currentTimeMillis() + ".png";
            File file = filePart.getFile();
            if (file.renameTo(new File("public/images/market", fileName))) {
                fileName = "images/market/" + fileName;
                Market market = new Market();
                market.name = marketForm.field("name").value();
                market.location = marketForm.field("location").value();
                market.openAt = marketForm.field("opening").value();
                market.closeAt = marketForm.field("closing").value();
                market.image = fileName;
                market.save();

            }
        }
        return redirect(routes.MarketController.index());
    }

    public static Result storeSeller(){
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
        return redirect(routes.MarketController.index());
    }
    public static Result MarketShops(Long id) {
        Market market =Market.find.byId(id);
        List<Shop> marketList=Shop.find.where().eq("market_id",id).findList();
        return ok(front_market.render(marketList,market));
    }
    public static Result Marketseller(Long id) {
        List<User> users=User.find.where().eq("role","admin").findList();
        Market market =Market.find.byId(id);
        List<Shop> marketList=Shop.find.where().eq("market_id",id).findList();
        return ok(marketSellers.render(marketList,users,market));
    }
    public static Result update() {
        Form<Market> marketForm = Form.form(Market.class).bindFromRequest();
        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart filePart = body.getFile("photo");
        Market market = Market.find.byId(Long.valueOf(marketForm.field("id").value()));
        if (filePart != null) {
            String fileName = "" + System.currentTimeMillis() + ".png";
            File file = filePart.getFile();
            if (file.renameTo(new File("public/images/market", fileName))) {
                fileName = "images/market/" + fileName;
                market.image = fileName;
            }
            }
                market.name = marketForm.field("name").value();
                market.location = marketForm.field("location").value();
                market.openAt = marketForm.field("opening").value();
                market.closeAt = marketForm.field("closing").value();
                market.update();
        return redirect(routes.MarketController.index());
    }
    public static Result destroy(long id) {
        Market market = Market.find.byId(id);
        market.delete();
        return redirect(routes.MarketController.index());
    }

}
