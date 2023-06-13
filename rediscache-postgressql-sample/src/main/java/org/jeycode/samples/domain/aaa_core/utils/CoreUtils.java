package org.jeycode.samples.domain.aaa_core.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class CoreUtils {

  public static boolean isActionValid(final Runnable action) {
    try {
      action.run();
    } catch (final Exception ex) {
      return false;
    }
    return true;
  }

}
