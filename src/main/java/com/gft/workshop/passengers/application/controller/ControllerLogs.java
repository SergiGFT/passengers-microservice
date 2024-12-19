package com.gft.workshop.passengers.application.controller;

import org.slf4j.Logger;
import org.springframework.http.HttpMethod;

public class ControllerLogs {
  private ControllerLogs() {}

  public static void logOnRequest(Logger log, HttpMethod httpMethod, String path) {
    log.info("REST CALL: {} {}, direction: in", httpMethod.name(), path);
  }

  public static void logOnCancel(Logger log, HttpMethod httpMethod, String path) {
    log.info("REST CALL: {} {}, direction: out, result: Cancel", httpMethod.name(), path);
  }

  public static void logOnSuccess(Logger log, HttpMethod httpMethod, String path) {
    log.info("REST CALL: {} {}, direction: out, result: Success", httpMethod.name(), path);
  }

  public static void logOnError(Logger log, HttpMethod httpMethod, String path, Throwable ex) {
    log.info(
        "REST CALL: {} {}, direction: out, result: Error, errorMessage: {} ",
        httpMethod.name(),
        path,
        ex.getMessage());
  }
}
