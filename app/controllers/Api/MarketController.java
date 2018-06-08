package controllers.Api;

import com.fasterxml.jackson.databind.node.ObjectNode;
import models.Market;


import models.Product;
import models.Shop;
import models.User;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import scala.util.parsing.json.JSONObject;


import java.util.List;

/**
 * Created by Baptiste on 5/30/2018.
 */
public class MarketController extends Controller {

    public static Result SendResponse(int action,String message){
        ObjectNode result=Json.newObject();
        result.put("action",action);
        result.put("message",message);
        return ok(result);
    }

    public static Result MarketList(){
        List<Market> markets=Market.find.all();
        if (markets.size()<=0) {
            return SendResponse(0, "no product Available");
        }
        return ok(Json.toJson(markets));
    }

    public static Result shoptList(){
        Form<Shop> shopForm = Form.form(Shop.class).bindFromRequest();
        long id=Long.valueOf(shopForm.field("id").value());
        List<Shop> shops=Shop.find.where().eq("market_id",id).findList();
        if (shops.size()<=0) {
            return SendResponse(0, "no product Available");
        }
        return ok(Json.toJson(shops));
    }

    public static Result productList(){
        Form<Product> shopForm = Form.form(Product.class).bindFromRequest();
        long id=Long.valueOf(shopForm.field("id").value());
        List<Product> products=Product.find.where().eq("shop_id",id).findList();
        if (products.size()<=0) {
            return SendResponse(0, "no product Available");
        }
        return ok(Json.toJson(products));
    }

    public static Result Search_productList(){
        Form<Product> shopForm = Form.form(Product.class).bindFromRequest();
        String name=shopForm.field("name").value();
        List<Product> products=Product.find.where().contains("name", "%"+name+"%").findList();
        if (products.size()<=0) {
            return SendResponse(0, "no product Available");
        }
        return ok(Json.toJson(products));
    }
    public static Result brandProduct(){
        Form<Product> shopForm = Form.form(Product.class).bindFromRequest();
        long id=Long.valueOf(shopForm.field("id").value());
        List<Product> products=Product.find.where().eq("brand_id",id).findList();
        if (products.size()<=0) {
            return SendResponse(0, "no product Available");
        }
        return ok(Json.toJson(products));
    }

    public static Result catProduct(){
        Form<Product> shopForm = Form.form(Product.class).bindFromRequest();
        long id=Long.valueOf(shopForm.field("id").value());
        List<Product> products=Product.find.where().eq("product_category_id",id).findList();
        if (products.size()<=0) {
            return SendResponse(0, "no product Available");
        }
        return ok(Json.toJson(products));
    }
    public static Result productDiscr(){
        Form<Product> shopForm = Form.form(Product.class).bindFromRequest();
        long id=Long.valueOf(shopForm.field("id").value());
        Product products=Product.find.byId(id);
        if (products==null) {
            return SendResponse(0, "product discription not available");
        }
        return ok(Json.toJson(products));
    }

}
