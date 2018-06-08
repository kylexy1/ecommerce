package controllers;

import models.Brand;
import models.ShopCategory;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.admin.brand;

import java.util.List;

/**
 * Created by Baptiste on 5/30/2018.
 */
public class BrandController extends Controller {
    public static Result store(){
        Form<Brand> request = Form.form(Brand.class).bindFromRequest();
        Brand brand=new Brand();
        brand.name=request.field("name").value();
        brand.save();
        return redirect(routes.BrandController.index());
    }
    public static Result index()
    {
        List<Brand> brandList=Brand.find.all();
        return ok(brand.render(brandList));
    }
}
