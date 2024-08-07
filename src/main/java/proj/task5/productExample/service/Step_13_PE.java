package proj.task5.productExample.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import proj.task5.Interface.StepPExampleExecable;
import proj.task5.exceptions.BadReqException;
import proj.task5.exceptions.NotFoundReqException;
import proj.task5.productExample.model.ProdExample;
import proj.task5.repository.Tpp_ref_product_classRepo;
import proj.task5.entity.Tpp_ref_product_register_type;
import proj.task5.service.CreateTppProductRegister;


import java.util.List;

@Data
@Service
@Qualifier("13_PE")
public class Step_13_PE implements StepPExampleExecable {
    @Autowired
    Tpp_ref_product_classRepo tpp_ref_product_classRepo;
    @Autowired
    CreateTppProductRegister createTppProductRegister;

    @Override
    public ResponseEntity<?> execute(ProdExample prodExample) {
        List<Tpp_ref_product_register_type>   tppTypeLst =  createTppProductRegister.getLstType(prodExample.getProductCode());
        if (tppTypeLst.isEmpty())
            throw new NotFoundReqException("Код продукта  " + prodExample.getProductCode() +
                                            " не найден в каталоге продуктов tpp_ref_product_class");
        return null;
    }
}
