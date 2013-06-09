package models;

import java.util.*;
import javax.persistence.*;
import play.db.ebean.*;

@Entity
public class OlzAction extends Model {

    @Id
    public Long id;
    public String title;
    public boolean done = false;
    public Date dueDate;
    @ManyToOne
    public OlzUser assignedTo;
    public String folder;
    @ManyToOne
    public OlzList olzList;

    public static Model.Finder<Long,OlzAction> find = new Model.Finder<Long, OlzAction>(Long.class, OlzAction.class);

    public static List<OlzAction> findActionInvolving(String user) {
       return find.fetch("olzList").where()
                .eq("done", false)
                .eq("olzList.members.email", user)
           .findList();
    }

    public static OlzAction create(OlzAction olzAction, Long olzList, String folder) {
        olzAction.olzList = OlzList.find.ref(olzList);
        olzAction.folder = folder;
        olzAction.save();
        return olzAction;
    }
}