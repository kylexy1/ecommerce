package models;

import play.db.ebean.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Created by Baptiste on 5/29/2018.
 */
@Entity
public class Product extends Model {
    @Id
    public Long id;
    public String name="";
    public String description="";
    public Double quanty=0.0;
    public String quality="";
    public Double price=0.0;
    public int popularity=0;


    @ManyToOne(cascade = CascadeType.ALL)
    public ProductCategory productCategory;

    @ManyToOne(cascade = CascadeType.ALL)
    public Brand brand;

    @ManyToOne(cascade = CascadeType.ALL)
    public Shop shop;

    public Date createdAt=new Date();
    public Date updatedAt=new Date();

    public static Finder<Long, Product> find = new Finder<Long, Product>(Long.class, Product.class);
}
