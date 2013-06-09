package models;

import javax.persistence.*;
import play.db.ebean.*;
import com.avaje.ebean.*;

@Entity
public class OlzUser extends Model {

    @Id
    public String email;
    public String name;
    public String password;
    
    public OlzUser(String email, String name, String password) {
      this.email = email;
      this.name = name;
      this.password = password;
    }

    public static Finder<String,OlzUser> find = new Finder<String,OlzUser>(
        String.class, OlzUser.class
    ); 
    
    public static OlzUser authenticate(String email, String password) {
        return find.where().eq("email", email)
            .eq("password", password).findUnique();
    }
}