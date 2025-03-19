package vttp.batch4.csf.ecommerce.controllers;


import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import vttp.batch4.csf.ecommerce.Utils;
import vttp.batch4.csf.ecommerce.models.Order;
import vttp.batch4.csf.ecommerce.services.PurchaseOrderService;

@Controller
@RequestMapping(path="/api",produces=MediaType.APPLICATION_JSON_VALUE)
public class OrderController {

  @Autowired
  private PurchaseOrderService poSvc;

  // IMPORTANT: DO NOT MODIFY THIS METHOD.
  // If this method is changed, any assessment task relying on this method will
  // not be marked
  @PostMapping(path="/order")
  public ResponseEntity<String> postOrder(@RequestBody String payload) {
    Document response = new Document();
    // TODO Task 3
    try {
      Order order = Utils.toOrder(payload);
      poSvc.createNewPurchaseOrder(order);
      
      response.append("orderId",order.getOrderId());
      return ResponseEntity.ok(response.toJson());
    } catch (DataAccessException e) {
      e.printStackTrace();
      response.append("message",e.getMessage());
      return ResponseEntity.badRequest().body(response.toJson());
    }
    
  }
}
