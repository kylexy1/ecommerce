package controllers;

import models.Market;
import models.Product;
import models.ProductImage;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;

import java.io.File;

/**
 * Created by Baptiste on 5/30/2018.
 */
public class ProductImageController extends Controller {

    public static Result store(){
        Form<ProductImage> marketForm = Form.form(ProductImage.class).bindFromRequest();
        Http.MultipartFormData body = request().body().asMultipartFormData();
        Http.MultipartFormData.FilePart filePart = body.getFile("photo");
        if (filePart != null) {
            String fileName = "" + System.currentTimeMillis() + ".png";
            File file = filePart.getFile();
            if (file.renameTo(new File("public/images/product", fileName))) {
                fileName = "images/product/" + fileName;
                Product product=Product.find.byId(Long.valueOf(marketForm.field("product").value()));
                ProductImage productImage=new ProductImage();
                productImage.image=fileName;
                productImage.product=product;
                productImage.save();
            }
            return ok("success");
        }
        else{
            return ok("failed");
        }

    }
}
