package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;

@Entity
public class OlzList extends Model {

    @Id
    public Long id;
    public String name;
    public String folder;
    @ManyToMany(cascade = CascadeType.REMOVE)
    public List<OlzUser> members = new ArrayList<OlzUser>();

    public OlzList(String name, String folder, OlzUser owner) {
        this.name = name;
        this.folder = folder;
        this.members.add(owner);
    }

    public static Model.Finder<Long,OlzList> find = new Model.Finder<Long, OlzList>(Long.class, OlzList.class);

    public static OlzList create(String name, String folder, String owner) {
        OlzList OlzList = new OlzList(name, folder, OlzUser.find.ref(owner));
        OlzList.save();
        OlzList.saveManyToManyAssociations("members");
        return OlzList;
    }

    public static List<OlzList> findInvolving(String user) {
        return find.where()
            .eq("members.email", user)
            .findList();
    }
}