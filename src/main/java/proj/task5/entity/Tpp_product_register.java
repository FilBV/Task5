package proj.task5.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
public class Tpp_product_register {
    @Id
    @GeneratedValue(strategy =  GenerationType.IDENTITY)
    private  Long id;

    @Column(name = "product_id")
    private Long productId;

    private String type;
    private  Long account;
    private String currency_code;
    private  String state;
    @Column(name = "account_number")
    private String accountNumber;


}
