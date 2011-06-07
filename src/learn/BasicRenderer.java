package learn;

import java.io.*;
import java.util.HashMap;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.regex.PatternSyntaxException;

public class BasicRenderer {
  public static String render(String file, HashMap<String, String> locals) {
    return layout(file, parseFile(file, "\\$\\{(\\w.*?)\\}", locals));
  }

  private static String layout(String file, String content) {
    HashMap<String, String> layout = new HashMap<String, String>();
    layout.put("content", content);
    return parseFile("views/layout.html", "@\\{(content)\\}", layout);
  }

  private static String parse(String pattern, String text, HashMap<String, String> locals) {
    Matcher regexp = Pattern.compile(pattern).matcher(text);
    while (regexp.find()) {
      text = regexp.replaceFirst(locals.get(regexp.group(1)).toString());
    }
    return text;
  }

  private static String parseFile(String file, String pattern, HashMap<String, String> locals) {
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

}
