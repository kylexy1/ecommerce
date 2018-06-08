package controllers;

import models.Brand;
import models.ProductCategory;
import models.ShopCategory;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.admin.productCategory;

import java.util.List;

/**
 * Created by Baptiste on 5/30/2018.
 */
public class ProductCategoryController extends Controller {
    public static Result store(){
        Form<ProductCategory> request = Form.form(ProductCategory.class).bindFromRequest();
        ProductCategory shopCategory=new ProductCategory();
        shopCategory.name=request.field("name").value();
        shopCategory.save();
        return redirect(routes.ProductCategoryController.list());
    }
    public static Result list(){
        List<ProductCategory> list=ProductCategory.find.all();
        return ok(productCategory.render(list));
    }
}
