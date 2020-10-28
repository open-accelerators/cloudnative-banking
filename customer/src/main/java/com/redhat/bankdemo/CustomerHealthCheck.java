package com.redhat.bankdemo;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.eclipse.microprofile.health.HealthCheck;
import org.eclipse.microprofile.health.HealthCheckResponse;
import org.eclipse.microprofile.health.Readiness;

@Readiness
@ApplicationScoped
public class CustomerHealthCheck implements HealthCheck {

  @Inject
  private CustomerResource customerResource;

  public HealthCheckResponse call() {

    if (customerResource.getAll() != null) {
      return HealthCheckResponse.named("Success of Customer Health Check!!!").up().build();
    } else {
      return HealthCheckResponse.named("Failure of Customer Health Check!!!").down().build();
    }
  }

}
