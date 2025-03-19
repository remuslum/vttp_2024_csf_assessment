package vttp.batch4.csf.ecommerce;

import java.io.StringReader;
import java.math.RoundingMode;
import java.util.LinkedList;
import java.util.List;

import org.bson.Document;

import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonObject;
import vttp.batch4.csf.ecommerce.models.Cart;
import vttp.batch4.csf.ecommerce.models.LineItem;
import vttp.batch4.csf.ecommerce.models.Order;
import vttp.batch4.csf.ecommerce.models.Product;

public class Utils {

  // IMPORTANT: DO NOT MODIFY THIS METHOD.
  // If this method is changed, any assessment task relying on this method will
  // not be marked
  public static Product toProduct(Document doc) {
    Product product = new Product();
    product.setId(doc.getObjectId("_id").toHexString());
    product.setName(doc.getString("ProductName"));
    product.setBrand(doc.getString("Brand"));
    product.setPrice(doc.getDouble("Price").floatValue());
    product.setDiscountPrice(doc.getDouble("DiscountPrice").floatValue());
    product.setImage(doc.getString("Image_Url"));
    product.setQuantity(doc.getString("Quantity"));
    return product;
  }

  // IMPORTANT: DO NOT MODIFY THIS METHOD.
  // If this method is changed, any assessment task relying on this method will
  // not be marked
  public static JsonObject toJson(Product product) {
    return Json.createObjectBuilder()
      .add("prodId", product.getId())
      .add("name", product.getName())
      .add("brand", product.getBrand())
      .add("price", product.getPrice())
      .add("discountPrice", product.getDiscountPrice())
      .add("image", product.getImage())
      .add("quantity", product.getQuantity())
      .build();
  }

  public static Order toOrder(String payload) {
    JsonObject object = Json.createReader(new StringReader(payload)).readObject();
    Order order = new Order();

    order.setName(object.getString("name"));
    order.setAddress(object.getString("address"));
    order.setPriority(object.getBoolean("priority"));
    order.setComments(object.getString("comments"));

    JsonArray lineItemsArray = object.getJsonObject("cart").getJsonArray("lineItems");

    order.setCart(toCart(lineItemsArray));

    return order;
  }

  private static Cart toCart(JsonArray array){
    List<LineItem> lineItems = new LinkedList<>();
    for(int i = 0; i < array.size(); i++){
      LineItem lineItem = new LineItem();
      JsonObject object = array.getJsonObject(i);

      lineItem.setProductId(object.getString("prodId"));
      lineItem.setQuantity(object.getInt("quantity"));
      lineItem.setName(object.getString("name"));
      
      // Round off price to 2 decimal places
      lineItem.setPrice(object.getJsonNumber("price").bigDecimalValue().setScale(2,RoundingMode.HALF_UP).floatValue());

      lineItems.add(lineItem);
    }
    Cart cart = new Cart();
    cart.setLineItems(lineItems);
    return cart;
  }
}
