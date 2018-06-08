package controllers;

import models.*;
import play.data.Form;
import play.libs.Json;
import play.mvc.Controller;
import play.mvc.Http;
import play.mvc.Result;
import views.html.admin.addProduct;
import views.html.front_product;
import views.html.productDescription;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Baptiste on 5/30/2018.
 */
public class ProductController extends Controller {
    public static Result index(){
        List<Brand> brands =Brand.find.all();
        List<ProductCategory> productCategories=ProductCategory.find.all();
        List<Product> products=new ArrayList<>();
        if (session().get("shop_id")==null){
            products=new ArrayList<>();
        }else {
           products=Product.find.where().eq("shop_id",Long.valueOf(session().get("shop_id"))).findList();
        }

        return ok(views.html.admin.products.render(products));
    }
    public static Result addproduct(){
        List<Brand> brands =Brand.find.all();
        List<ProductCategory> productCategories=ProductCategory.find.all();
        return ok(addProduct.render(brands,productCategories));
    }

public static Result store(){
    Form<Product> productForm = Form.form(Product.class).bindFromRequest();
    long productCat=Long.valueOf(productForm.field("category").value());
    long brandId=Long.valueOf(productForm.field("brand").value());
    long shopId=Long.valueOf(session().get("shop_id"));
    ProductCategory productCategory=ProductCategory.find.byId(productCat);
    Shop shop=Shop.find.byId(shopId);
    Brand brand=Brand.find.byId(brandId);
    if (productCategory==null || brand==null) {
        return ok("failed....");
    }
    Product product=new Product();
    product.name=productForm.field("name").value();
    product.description=productForm.field("discr").value();
    product.quality=productForm.field("quality").value();
    product.quanty=Double.valueOf(productForm.field("quantity").value());
    product.price=Double.valueOf(productForm.field("price").value());
    product.brand=brand;
    product.shop=shop;
    product.productCategory=productCategory;
    product.save();
    Long pro_id= product.id;
    Http.MultipartFormData body = request().body().asMultipartFormData();
    Http.MultipartFormData.FilePart filePart = body.getFile("photo");
    Http.MultipartFormData.FilePart filePart1 = body.getFile("photo1");
    Http.MultipartFormData.FilePart filePart2 = body.getFile("photo2");
    Http.MultipartFormData.FilePart filePart3 = body.getFile("photo3");
    if (filePart != null) {
        String fileName = "" + System.currentTimeMillis() + ".png";
        String fileName1 = "" + System.currentTimeMillis() + ".png";
        String fileName2 = "" + System.currentTimeMillis() + ".png";
        String fileName3 = "" + System.currentTimeMillis() + ".png";
        File file = filePart.getFile();
        if (file.renameTo(new File("public/images/product", fileName))) {
            fileName = "images/product/" + fileName;
        }
        File file1 = filePart.getFile();
        if (file1.renameTo(new File("public/images/product", fileName1))) {
            fileName1 = "images/product/" + fileName1;
        }
        File file2 = filePart.getFile();
        if (file2.renameTo(new File("public/images/product", fileName2))) {
            fileName2 = "images/product/" + fileName;
        }
        File file3 = filePart.getFile();
        if (file3.renameTo(new File("public/images/product", fileName3))) {
            fileName3 = "images/product/" + fileName3;
        }
            Product productId=Product.find.byId(pro_id);
            ProductImage productImage=new ProductImage();
            productImage.image=fileName;
            productImage.product=productId;
            productImage.save();
            ProductImage productImage1=new ProductImage();
            productImage1.image=fileName1;
            productImage1.product=productId;
            productImage1.save();
            ProductImage productImage2=new ProductImage();
            productImage2.image=fileName2;
            productImage2.product=productId;
            productImage2.save();
            ProductImage productImage3=new ProductImage();
            productImage3.image=fileName3;
            productImage3.product=productId;
            productImage3.save();

    }
    return redirect(routes.ProductController.index());
}
    public static Result productList(long id){

        List<Product> products=Product.find.where().eq("shop_id",id).findList();
        if (products.size()<=0) {
            return ok("no product Available");
        }
        return ok(Json.toJson(products));
    }
    public static Result Search_productList(String name){
        List<Product> products=Product.find.where().contains("name", "%"+name+"%").findList();
        if (products.size()<=0) {
            return ok("no product Available");
        }
        return ok(Json.toJson(products));
    }
    public static Result brandProduct(long id){

        List<Product> products=Product.find.where().eq("brand_id",id).findList();
        if (products.size()<=0) {
            return ok("no product Available");
        }
        return ok(Json.toJson(products));
    }
    public static Result catProduct(long id){
        List<Product> products=Product.find.where().eq("product_category_id",id).findList();
        if (products.size()<=0) {
            return ok("no product Available");
        }
        return ok(Json.toJson(products));
    }
    public static Result productDiscr(long id){
        Product product=Product.find.byId(id);
       List<ProductImage> images =ProductImage.find.where().eq("product_id",id).findList();
        List<Product> products=Product.find.where().eq("productCategory.id",product.productCategory.id).findList();
        return ok(front_product.render(product,images,products));
    }
}
