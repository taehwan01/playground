package hello.itemservice.repository.jpa;

import hello.itemservice.domain.Item;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SpringDataJpaItemRepository extends JpaRepository<Item, Long> {

  List<Item> findItemByItemNameLike(String itemName);

  List<Item> findItemByPriceLessThanEqual(Integer price);

  List<Item> findItemByItemNameLikeAndPriceLessThanEqual(String itemName, Integer price);

  @Query("select i from Item i where i.itemName like :itemName and i.price <= :price")
  List<Item> findItems(@Param("itemName") String itemName, @Param("price") Integer price);
}