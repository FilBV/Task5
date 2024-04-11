package proj.task5.repository;

import org.springframework.data.repository.CrudRepository;
import proj.task5.entity.Tpp_product_register;

import java.util.List;

public interface Tpp_product_registerRepo extends CrudRepository<Tpp_product_register, Long> {
    public List<Tpp_product_register> findByproductId(Long productId);
}
