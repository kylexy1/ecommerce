package controllers;

import models.Branch;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Result;

/**
 * Created by Baptiste on 5/30/2018.
 */
public class BranchController extends Controller {
    public static Result store(){
        Form<Branch> request = Form.form(Branch.class).bindFromRequest();
        Branch brand=new Branch();
        brand.name=request.field("name").value();
        brand.location=request.field("location").value();
        brand.save();
        return ok("success");
    }
}
