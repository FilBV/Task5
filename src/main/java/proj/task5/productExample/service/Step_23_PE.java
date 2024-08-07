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
import proj.task5.service.CreateAgreement;

import java.util.List;

@Data
@Service
@Qualifier("23_PE")
public class Step_23_PE implements StepPExampleExecable {


    @Autowired
    @Qualifier("Agreement")
    private CreateAgreement createAgreement;


    @Autowired
    private Tpp_product_registerRepo tpp_product_registerRepo;

    @Autowired
    private Tpp_productRepo tpp_productRepo;

    private Tpp_product tppProduct;
    private List<Agreement> tppAgrLst;
    private List<Tpp_product_register> tppRegLst;

    @Transactional
    public void create_records_agreement(ProdExample modelProdExample) {
        tppProduct = tpp_productRepo.findById(modelProdExample.getInstanceId()).orElse(null);
        tppAgrLst = createAgreement.create_recs_table_agreement(modelProdExample, tppProduct);
        tppAgrLst = createAgreement.findAllAgreement(tppProduct);
        tppRegLst = tpp_product_registerRepo.findByproductId(tppProduct.getId());
    }


    @Override
    public Object execute(ProdExample prodExample) {
        create_records_agreement(prodExample);
        StructOkAnswer okAnswer = new StructOkAnswer();
        okAnswer.setFields(tppProduct.getId(), tppAgrLst, tppRegLst);
        return okAnswer;

    }
}
