package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * Created by Baptiste on 6/6/2018.
 */
@Entity
public class ProductOrder extends Model {
    @Id
    public long id;
    public Integer quantity;
    public double  price;
    @ManyToOne
    public User user;
    @ManyToOne
    public Product product;
    public String status="1";
    public Date createdAt=new Date();
    public Date updatedAt=new Date();
    public static Finder<Long, ProductOrder> find = new Finder<Long, ProductOrder>(Long.class, ProductOrder.class);
}
