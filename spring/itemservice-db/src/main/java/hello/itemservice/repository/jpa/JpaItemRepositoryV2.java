package hello.itemservice.repository.jpa;

import hello.itemservice.domain.Item;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

@Repository
@RequiredArgsConstructor
public class JpaItemRepositoryV2 implements ItemRepository {

  private final SpringDataJpaItemRepository repository;

  @Override
  public Item save(Item item) {
    return repository.save(item);
  }

  @Override
  public void update(Long itemId, ItemUpdateDto updateParam) {
    Item findItem = repository.findById(itemId).orElseThrow();
    findItem.setItemName(updateParam.getItemName());
    findItem.setPrice(updateParam.getPrice());
    findItem.setQuantity(updateParam.getQuantity());
  }

  @Override
  public Optional<Item> findById(Long id) {
    return repository.findById(id);
  }

  @Override
  public List<Item> findAll(ItemSearchCond cond) {
    String itemName = cond.getItemName();
    Integer maxPrice = cond.getMaxPrice();
    if (StringUtils.hasText(itemName) && maxPrice != null) {
//return repository.findByItemNameLikeAndPriceLessThanEqual("%" + itemName +"%", maxPrice);
      return repository.findItems("%" + itemName + "%", maxPrice);
    } else if (StringUtils.hasText(itemName)) {
      return repository.findItemByItemNameLike("%" + itemName + "%");
    } else if (maxPrice != null) {
      return repository.findItemByPriceLessThanEqual(maxPrice);
    } else {
      return repository.findAll();
    }
  }
}
