package com.monitoring.receiver.api.service;

/**
 * Service level exception
 */
public class ServiceException extends Exception {

  public ServiceException(String message) {
    super(message);
  }
}
