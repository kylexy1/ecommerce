package controllers;

import models.*;
import play.mvc.Result;

import java.util.List;

/**
 * Created by Baptiste on 6/5/2018.
 */
public class DirectTools {
    public static List<Shop> superMaket(){
        List<Shop> supermarket= Shop.find.where().eq("type","Supermarket").setMaxRows(4).findList();
        return supermarket;
    }
    public static List<Shop> allSupermarket(){
        List<Shop> supermarket= Shop.find.where().eq("type","Supermarket").setMaxRows(4).findList();
        return supermarket;
    }
    public static List<Market> allMarket(){
        List<Market> market= Market.find.all();
        return market;
    }
    public static List<Brand> brands(){
        List<Brand> brandList= Brand.find.all();
        return brandList;
    }
    public static List<Shop> coffeeShops(){
        List<Shop> coffeshop= Shop.find.where().eq("type","CoffeeShops").findList();
        return coffeshop;
    }
    public static List<Shop> restaurant(){
        List<Shop> restaurant= Shop.find.where().eq("type","Restaurant").findList();
        return restaurant;
    }
    public static List<Product> product(){
        List<Product> supermarket= Product.find.orderBy("id Asc").setMaxRows(4).findList();
        return supermarket;
    }
    public static List<Product> popular(){
        List<Product> popular= Product.find.orderBy("popularity desc").setMaxRows(10).findList();
        return popular;
    }
    public static ProductImage image(long id){
        ProductImage image= ProductImage.find.where().eq("product_id",id).setMaxRows(1).findUnique();
        return image;
    }
    public  static List<Product> newproduct(){
        List<Product> products=Product.find.where().eq("quality","New").setMaxRows(10).findList();
        return products;
    }

}
