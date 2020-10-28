package com.redhat.bankdemo;

import java.util.List;
import java.util.stream.Collectors;

import javax.json.Json;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import org.jboss.resteasy.annotations.jaxrs.PathParam;

@Path("/services/customers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CustomerResource {

  @GET
  public List<Customer> getAll() {
    return Customer.listAll();
  }

  @GET
  @Path("/{customerId}")
  public List<Customer> getAvailability(@PathParam Long customerId) {
    return Customer.<Customer>streamAll()
      .filter(c -> c.customerId.equals(customerId))
      .collect(Collectors.toList());
  }

  @Provider
  public static class ErrorMapper implements ExceptionMapper<Exception> {

    @Override
    public Response toResponse(Exception exception) {
      int code = 500;
      if (exception instanceof WebApplicationException) {

      }
      return Response.status(code)
        .entity(Json.createObjectBuilder().add("error", exception.getMessage()).add("code", code).build())
        .build();
    }
    
  }

}
