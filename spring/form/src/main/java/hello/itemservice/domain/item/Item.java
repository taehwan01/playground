package hello.itemservice.domain.item;

import java.util.List;
import lombok.Data;

@Data
public class Item {

  private Long id;
  private String itemName;
  private Integer price;
  private Integer quantity;

  private Boolean open;
  private List<String> regions;
  private ItemType itemType;
  private DeliveryCode deliveryCode;

  public Item() {}

  public Item(String itemName, Integer price, Integer quantity) {
    this.itemName = itemName;
    this.price = price;
    this.quantity = quantity;
  }
}
