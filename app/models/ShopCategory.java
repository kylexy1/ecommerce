
package models;

import com.avaje.ebean.Expr;
import com.fasterxml.jackson.annotation.JsonBackReference;
import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Entity
public class ShopCategory extends Model{
    @Id
    public long id;
    public String name = "";
    public Date createdAt=new Date();
    public Date updatedAt=new Date();
    public static Finder<Long, ShopCategory> find = new Finder<Long, ShopCategory>(Long.class, ShopCategory.class);
}