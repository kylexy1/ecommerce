
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
public class Market extends Model{
    @Id
    public long id;
    public String name = "";
    public String location = "";
    public String image = "";
    public String openAt = "";
    public String closeAt = "";
    public Date createdAt=new Date();
    public Date updatedAt=new Date();
    public static Finder<Long, Market> find = new Finder<Long, Market>(Long.class, Market.class);
}