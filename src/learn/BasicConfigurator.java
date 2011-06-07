package learn;

import java.io.*;
import java.util.HashMap;

public class BasicConfigurator {
  private static final HashMap<String, String> setup = new HashMap<String, String>();

  public static void set(String key, String value) {
    setup.put(key, value);
  }

  public static HashMap<String, String> settings() {
    return setup;
  }

  public static String settings(String key) {
    return setup.get(key);
  }

}
