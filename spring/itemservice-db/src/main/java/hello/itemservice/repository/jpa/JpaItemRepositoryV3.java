package hello.itemservice.repository.jpa;

import static hello.itemservice.domain.QItem.*;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import hello.itemservice.domain.Item;
import hello.itemservice.domain.QItem;
import hello.itemservice.repository.ItemRepository;
import hello.itemservice.repository.ItemSearchCond;
import hello.itemservice.repository.ItemUpdateDto;
import java.util.List;
import java.util.Optional;
import javax.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.util.StringUtils;

//@Repository
public class JpaItemRepositoryV3 implements ItemRepository {

  private final EntityManager em;
  private final JPAQueryFactory query;

  public JpaItemRepositoryV3(EntityManager em) {
    this.em = em;
    this.query = new JPAQueryFactory(em);
  }

  @Override
  public Item save(Item item) {
    em.persist(item);
    return item;
  }

  @Override
  public void update(Long itemId, ItemUpdateDto updateParam) {
    Item findItem = em.find(Item.class, itemId);
    findItem.setItemName(updateParam.getItemName());
    findItem.setPrice(updateParam.getPrice());
    findItem.setQuantity(updateParam.getQuantity());
  }

  @Override
  public Optional<Item> findById(Long id) {
    Item item = em.find(Item.class, id);
    return Optional.ofNullable(item);
  }

  //  @Override
  public List<Item> findAllOld(ItemSearchCond cond) {
    String itemName = cond.getItemName();
    Integer maxPrice = cond.getMaxPrice();

    BooleanBuilder booleanBuilder = new BooleanBuilder();
    if (StringUtils.hasText(itemName)) {
      booleanBuilder.and(item.itemName.like("%" + itemName + "%"));
    }
    if (maxPrice != null) {
      booleanBuilder.and(item.price.loe(maxPrice));
    }

    List<Item> result = query.select(item).from(item).where(booleanBuilder).fetch();

    return result;
  }

  @Override
  public List<Item> findAll(ItemSearchCond cond) {
    String itemName = cond.getItemName();
    Integer maxPrice = cond.getMaxPrice();

    return query.select(item)
        .from(item)
        .where(likeItemName(itemName), lessOrEqualThanMaxPrice(maxPrice))
        .fetch();
  }


  private BooleanExpression likeItemName(String itemName) {
    if (StringUtils.hasText(itemName)) {
      return item.itemName.like("%" + itemName + "%");
    }
    return null;
  }

  private BooleanExpression lessOrEqualThanMaxPrice(Integer maxPrice) {
    if (maxPrice != null) {
      return item.price.loe(maxPrice);
    }
    return null;
  }
}
