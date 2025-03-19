package vttp.batch4.csf.ecommerce.repositories;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import vttp.batch4.csf.ecommerce.models.Cart;
import vttp.batch4.csf.ecommerce.models.Order;

@Repository
public class PurchaseOrderRepository {

  @Autowired
  private JdbcTemplate template;

  private final String INSERT_ORDER = 
  """
    INSERT INTO orders (orderId, date, name, address, priority, comments) VALUES (?,?,?,?,?,?)    
  """;

  private final String INSERT_LINE_ITEM =
  """
    INSERT INTO lineItem (productId, name, quantity, price, orderId) VALUES (?,?,?,?,?)    
  """;

  // IMPORTANT: DO NOT MODIFY THIS METHOD.
  // If this method is changed, any assessment task relying on this method will
  // not be marked
  // You may only add Exception to the method's signature
  // {"name":"Remus","address":"jalan","priority":true,"comments":"comment",
  // "cart":{"lineItems":[
  //{"prodId":"67da29d10ed4321ca7929952","quantity":1,"name":"Cheese Slices - Made From Cow Milk 663 g + Cheese Spread - Cream Cheese 100 g","price":525.9400024414062}]}}
  public void create(Order order) {
    // TODO Task 3
    Cart cart = order.getCart();
    String orderId = order.getOrderId();
    // insert order
    template.update(INSERT_ORDER, orderId, order.getDate(), order.getName(), order.getAddress(),
    order.getPriority(), order.getComments());

    // batch insert lineitems
    List<Object[]> items = cart.getLineItems().stream().map(item -> 
      new Object[]{item.getProductId(), item.getName(), item.getQuantity(),item.getPrice(),orderId})
      .collect(Collectors.toList());

    template.batchUpdate(INSERT_LINE_ITEM, items);
  }
}
