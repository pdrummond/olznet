package controllers;

import models.OlzAction;
import models.OlzList;
import play.*;
import play.mvc.*;
import play.data.*;
import views.html.*;


public class Application extends Controller {

	public static Result index() {
		return ok(index.render(
				OlzList.find.all(),
				OlzAction.find.all()));
	}

}
