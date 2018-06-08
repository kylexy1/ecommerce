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
public class Branch extends Model {
    @Id
    public long id;
    public String location = "";
    public String name = "";

    @ManyToOne(cascade = CascadeType.ALL)
    public Shop shop;
    public Date createdAt=new Date();
    public Date updatedAt=new Date();
    public static Finder<Long, Branch> find = new Finder<Long, Branch>(Long.class, Branch.class);
}
