package controllers;

import models.*;

/**
 * Created by Baptiste on 6/4/2018.
 */
public class Statistics {
    public static int supermarketProduct(long id){
        return Product.find.where().eq("shop_id",id).findRowCount();
    }
    public static int markets(){
        return Market.find.where().findRowCount();
    }
    public static int supermarket(){
        return Shop.find.where().eq("type","SuperMarket").findRowCount();
    }
    public static int coffeeshop(){
        return Shop.find.where().eq("type","coffeeShops").findRowCount();
    }
    public static int restaurants(){
        return Shop.find.where().eq("type","Restaurant").findRowCount();
    }
    public static int shops(){
        return Shop.find.where().eq("type","Shop").findRowCount();
    }
    public static int customer(){
        return User.find.where().eq("role","customer").findRowCount();
    }
    public static int users(){
        return User.find.findRowCount();
    }
    public static int orders(){
        return ProductOrder.find.findRowCount();
    }
    public static int market_seller(long id){
        return Shop.find.where().eq("market_id",id).findRowCount();
    }

}
