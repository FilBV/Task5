package proj.task5.productRegistr.service;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import proj.task5.exceptions.BadReqException;
import proj.task5.Interface.StepPRegisterExecable;
import proj.task5.productRegistr.model.ProdRegistr;

@Service
@Qualifier("1_PR")
public class Step_1_PR implements StepPRegisterExecable {

    @Override
    public void  execute(ProdRegistr modelProduct){
        if (modelProduct.getInstanceId() == null) {
            throw new BadReqException("Идентификатор экземпляра продукта <InstanceId>  не заполнен");
        }
    }



}
