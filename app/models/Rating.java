package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by Baptiste on 6/7/2018.
 */
@Entity
public class Rating extends Model {
    @Id
    public long id;
    public String emil="";
    public String comment="";
    public int rating=0;
    public Date createdAt=new Date();
    public Date updatedAt=new Date();
    public static Finder<Long, Rating> find = new Finder<Long, Rating>(Long.class, Rating.class);
}
