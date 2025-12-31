package org.example.helpers;

import com.microsoft.playwright.Page;

import java.util.List;

public class NetworkUtils {

  public static void blockAdsAndMedia(Page page) {
    List<String> blockResources = List.of(
        "googlesyndication.com",
        "adservice.google.com",
        "doubleclick.net",
        "facebook.net",
        "analytics",
        "banner",
        "track",
        "ads.",
        "promo",
        "metrics"
    );

    page.route("**/*", route -> {
      String url = route.request().url();
      String resourceType = route.request().resourceType();

      boolean isBlockedDomain = blockResources.stream().anyMatch(url::contains);
      boolean isMediaType = resourceType.equals("image") ||
          resourceType.equals("media") ||
          resourceType.equals("font") ||
          resourceType.equals("stylesheet");

      if (isBlockedDomain || isMediaType) {
        route.abort();
      } else {
        route.resume();
      }
    });
  }
}
