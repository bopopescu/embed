package main.java.core.web.controller;

import io.dropwizard.auth.Auth;
import java.security.Principal;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import main.java.core.annotation.Controller;
import main.java.core.api.request.CreateOrderRequest;
import main.java.core.api.request.UpdateOrderRequest;
import main.java.core.api.response.CreateOrderResponse;
import main.java.core.api.response.UpdateOrderResponse;
import services.order.impl.OrderServiceImpl;

/**
 * Created by digvijaysharma on 05/02/17.
 */
@Controller
@Path("/order") @Consumes(MediaType.APPLICATION_JSON) @Produces(MediaType.APPLICATION_JSON)
public class OrderResource {

    private OrderServiceImpl orderService;

    public OrderResource(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @POST @Path("/create") public CreateOrderResponse createOrder(@Auth Principal user, CreateOrderRequest request)
    {
        request.setCallingUserId(user.getName());
        return orderService.createOrder(request);
    }

    @POST @Path("/update") public UpdateOrderResponse updateOrder(@Auth Principal user, UpdateOrderRequest request)
    {
        request.setCallingUserId(user.getName());
        return orderService.updateOrder(request);
    }
}
