package com.larbotech.karate.util;

import java.util.UUID;

public class DataGeneratorUtils {

  public static String uuid() {
    return UUID.randomUUID().toString();
  }
}
