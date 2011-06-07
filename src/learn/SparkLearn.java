package learn;

import static spark.Spark.*;

import spark.*;

import java.io.*;
import java.util.Map;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.regex.PatternSyntaxException;

public class SparkLearn {

  private static final Map<String, Object> settings = new HashMap<String, Object>();

  public static String parse(String pattern, String text, Map<String, Object> locals) {
    Matcher regexp = Pattern.compile(pattern).matcher(text);
    while (regexp.find()) {
      text = regexp.replaceFirst(locals.get(regexp.group(1)).toString());
    }
    return text;
  }

  public static String parseFile(String file, String pattern, Map<String, Object> locals) {
    StringBuffer content = new StringBuffer("");
    try {
      BufferedReader buffer = new BufferedReader(new FileReader(file));
      String line = null;

      while ((line = buffer.readLine()) != null) {
        content.append(parse(pattern, line, locals) + "\n");
      }

      buffer.close();
    }
    catch (Exception exception) {
      System.out.printf("ERROR: %s\n", exception.getMessage());
    }
    finally {
      return content.toString();
    }
  }

  public static String render(String file, Map<String, Object> locals) {
    return layout(file, parseFile(file, "\\$\\{(\\w.*?)\\}", locals));
  }

  public static String layout(String file, String content) {
    HashMap<String, Object> layout = new HashMap<String, Object>();
    layout.put("content", content);
    return parseFile("views/layout.html", "@\\{(content)\\}", layout);
  }

  public static void set(String key, Object value) {
    settings.put(key, (String) value);
  }

  public static Object settings(String key) {
    return settings.get(key);
  }

  public static void main(String[] args) {

    setPort(8080);

    get(new Route("/") {
      @Override
      public Object handle(Request request, Response response) {
        set("title", "Spark Learn Application");
        set("count", String.valueOf(settings.size()));
        return render("views/index.html", settings);
      }
    });

    get(new Route("/hello") {
      @Override
      public Object handle(Request request, Response response) {
        set("title", "Hello World!");
        set("count", String.valueOf(settings.size()));
        return render("views/index.html", settings);
      }
    });

    get(new Route("/about") {
      @Override
      public Object handle(Request request, Response response) {
        set("title", "About Spark Learn");
        set("author.name", "Hallison Batista");
        set("author.email", "hallison.batista@gmail.com");
        return render("views/about.html", settings);
      }
    });

  }

}

