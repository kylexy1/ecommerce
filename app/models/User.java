package models;

import play.db.ebean.Model;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;

/**
 * Created by Baptiste on 5/29/2018.
 */
@Entity
public class User extends Model {
    @Id
    public long id;
    public String names = "";
    public String profile = "";
    public String email = "";
    public String password = "";
    public String phone = "";
    public String role = "";
    public String address = "";
    public String gender = "";
    public Date createdAt=new Date();
    public Date updatedAt=new Date();
    public static Finder<Long, User> find = new Finder<Long, User>(Long.class, User.class);
}
