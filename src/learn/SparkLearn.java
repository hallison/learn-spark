package learn;

import static spark.Spark.*;

import spark.*;

public class SparkLearn {

  public static void main(String[] args) {

    setPort(8080);

    get(new Route("/") {
      @Override
      public Object handle(Request request, Response response) {
        return "home";
      }
    });

    get(new Route("/hello") {
      @Override
      public Object handle(Request request, Response response) {
        return "hello world";
      }
    });

    get(new Route("/about") {
      @Override
      public Object handle(Request request, Response response) {
        return "about";
      }
    });

  }

}

