package models;

import play.db.ebean.Model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Created by Baptiste on 5/30/2018.
 */
@Entity
public class ProductImage extends Model {
    @Id
    public long Id;
    public String image="";
    @ManyToOne(cascade = CascadeType.ALL)
    public Product product;

    public Date createdAt=new Date();
    public Date updatedAt=new Date();

    public static Finder<Long, ProductImage> find = new Finder<Long, ProductImage>(Long.class, ProductImage.class);
}
