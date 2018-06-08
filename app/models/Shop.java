
package models;

import com.avaje.ebean.Expr;
import com.fasterxml.jackson.annotation.JsonBackReference;
import play.db.ebean.Model;

import javax.persistence.*;
import javax.xml.transform.Result;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Entity
public class Shop extends Model{
    @Id
    public long id;
    public String name = "";
    public String type = "";
    public String image = "";
    public String location = "";
    @ManyToOne(cascade = CascadeType.ALL)
    public Market market;

    @ManyToOne(cascade = CascadeType.ALL)
    public User user;

    public String openAt = "";
    public String closeAt = "";

    public Date createdAt=new Date();
    public Date updatedAt=new Date();

    public static Finder<Long, Shop> find = new Finder<Long, Shop>(Long.class, Shop.class);

}