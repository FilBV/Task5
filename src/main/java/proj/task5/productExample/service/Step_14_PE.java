package proj.task5.productExample.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import proj.task5.Interface.StepPExampleExecable;
import proj.task5.productExample.model.ProdExample;
import proj.task5.repository.Tpp_productRepo;
import proj.task5.repository.Tpp_product_registerRepo;
import proj.task5.entity.Agreement;
import proj.task5.entity.Tpp_product;
import proj.task5.entity.Tpp_product_register;
import proj.task5.service.CreateTppProduct;
import proj.task5.service.CreateTppProductRegister;

import java.util.List;


@Service
@Data
@Qualifier("14_PE")
public class Step_14_PE implements StepPExampleExecable {

    @Autowired
    @Qualifier("TppProduct")
    private CreateTppProduct createTppProduct;
    @Autowired
    @Qualifier("TppProductRegister")
    private CreateTppProductRegister createTppProductRegister;


    @Autowired
    private Tpp_productRepo tpp_productRepo;
    @Autowired
    private Tpp_product_registerRepo tpp_product_registerRepo;

    Tpp_product tpp_product;
    List<Agreement> agrList;
    List<Tpp_product_register> tpp_product_registers;

    @Transactional
    public void create_records_tpp(ProdExample modelProdExample) {
        tpp_product = createTppProduct.create_rec_table(modelProdExample);
        tpp_product_registers = createTppProductRegister.create_recs_table_product_register(modelProdExample, tpp_product);
        agrList = tpp_product.getAgreementList();
    }

    @Override
    public Object execute(ProdExample prodExample) {
        create_records_tpp(prodExample);
        StructOkAnswer okAnswer = new StructOkAnswer();
        okAnswer.setFields(tpp_product.getId(), agrList, tpp_product_registers);
        return okAnswer;
    }
}
