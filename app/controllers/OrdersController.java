package controllers;


import com.avaje.ebean.Expr;
import models.Product;
import models.ProductImage;
import models.ProductOrder;
import models.User;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.cart;
import views.html.purchase_history;
import views.html.single_product;
import views.html.admin.orders;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Baptiste on 6/5/2018.
 */
public class OrdersController extends Controller {

    public static Result store() {
       int count = Integer.valueOf(session().get("count"));
        if(count==0){
            String refererUrl = request().getHeader("referer");
            flash("infor", " Nothing to save cart is empty");
            return redirect(refererUrl);
        }
            if(session().get("user_id")==null){
          flash("info", " Login in order to process your request");
          return redirect(routes.UserController.index());
      }

            for (int i = 1; i <= count; i++) {
                User user = User.find.byId(1l);
                Product product = Product.find.byId(Long.valueOf(session().get("id" + i)));
                ProductOrder order = new ProductOrder();
                order.quantity = Integer.valueOf(session().get("quantity" + i));
                order.product = product;
                order.user = user;
                order.save();
            }
            flash("info", "Added to cart successfully");
            return redirect(controllers.routes.OrdersController.cart());

    }
    public static Result costomer_order(Long id){
        Product product=Product.find.byId(id);
        List<ProductImage> images =ProductImage.find.where().eq("product_id",id).findList();
        List<Product> products=Product.find.where().eq("productCategory.id",product.productCategory.id).not(Expr.eq("id",id)).findList();
        return ok(single_product.render(product,images,products));
    }
    public static Result cart_list(long a,int price){
          int i=1+Integer.valueOf(session().get("count"));
        session("count",String.valueOf(i));
        session("id"+i,String.valueOf(a));
        session("quantity"+i,String.valueOf(price));
        return redirect(routes.Application.index());
    }
    public static List<Product> list(){
         List<Product> products=new ArrayList<>();
        int count=0;
        if (session().get("count")==null){
            count=0;
        }else {
            count=Integer.valueOf(session().get("count"));
        }

        for (int i=1; i<=count;i++){
            Product p=Product.find.byId(Long.valueOf(session().get("id"+i)));
            products.add(p);
        }
        return products;
    }
    public static Double price(){
        int count=0;
        if (session().get("count")==null){
            count=0;
        }else{
            count=Integer.valueOf(session().get("count"));
        }

        Double pric = 0.0;
        for (int i=1; i<=count;i++){
            Product p=Product.find.byId(Long.valueOf(session().get("id"+i)));
            int item=Integer.valueOf(session().get("quantity"+i));
            pric += p.price*item;

        }

        return pric;
    }
    public static Result Remove(){

        return redirect(routes.Application.index());
    }

    public static Result cart(){

        List<Product> products=new ArrayList<>();
        int count=Integer.valueOf(session().get("count"));
        for (int i=1; i<=count;i++){
            Product p=Product.find.byId(Long.valueOf(session().get("id"+i)));
            products.add(p);
        }
        return ok(cart.render(products));
    }
    public static Result history(long id){
        List<ProductOrder> products=ProductOrder.find.where().eq("user_id",id).findList();
        return ok(purchase_history.render(products));
    }
    public static Result OrderList(){
        if(session().get("user_id")!=null) {
            List<ProductOrder> orders = ProductOrder.find.where().eq("product.shop.user.id", Long.valueOf(session().get("user_id"))).findList();
            return ok(views.html.admin.orders.render(orders));
        }else {
            List<ProductOrder> orders =new ArrayList<>();
            return ok(views.html.admin.orders.render(orders));
        }
    }


}
