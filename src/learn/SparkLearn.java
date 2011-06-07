package learn;

import static spark.Spark.*;
import static learn.BasicRenderer.*;
import static learn.BasicConfigurator.*;

import spark.*;

public class SparkLearn {

  public static void main(String[] args) {

    get(new Route("/") {
      @Override
      public Object handle(Request request, Response response) {
        set("title", "Spark Learn Application");
        set("count", String.valueOf(settings().size()));
        return render("views/index.html", settings());
      }
    });

    get(new Route("/hello") {
      @Override
      public Object handle(Request request, Response response) {
        set("title", "Hello World!");
        set("count", String.valueOf(settings().size()));
        return render("views/index.html", settings());
      }
    });

    get(new Route("/about") {
      @Override
      public Object handle(Request request, Response response) {
        set("title", "About Spark Learn");
        set("author.name", "Hallison Batista");
        set("author.email", "hallison.batista@gmail.com");
        return render("views/about.html", settings());
      }
    });

  }

}

