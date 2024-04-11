package proj.task5.productExample.service;

import jakarta.validation.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;
import proj.task5.Interface.StepPExampleExecable;
import proj.task5.exceptions.BadReqException;
import proj.task5.productExample.model.InstanceArrangement;
import proj.task5.productExample.model.ProdExample;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;


@Service
@Validated
@Qualifier("1_PE")
public class Step_1_PE implements StepPExampleExecable {

   private List<String> errLst = new ArrayList<>();

   private <T>  void messOut(Set<ConstraintViolation<T>> violations) {
        if(!violations.isEmpty())
            for (ConstraintViolation<T> violation : violations) {
                if (violation.getMessage().contains("blank") || violation.getMessage().contains("null"))
                    errLst.add("Имя обязательного параметра " + violation.getPropertyPath().toString() + " : не зполнено значение ");
            }

    }


    void validateModel(@Valid  InstanceArrangement instanceArrangement,  Validator validator){
        Set<ConstraintViolation<InstanceArrangement>> violations = validator.validate(instanceArrangement);
        messOut(violations);
    }

    void validateModel(@Valid  ProdExample prodExample,  Validator validator){
        Set<ConstraintViolation<ProdExample>> violations = validator.validate(prodExample);
        messOut(violations);
    }

    public Object execute(ProdExample prodExample) {
        errLst = new ArrayList<>();
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        validateModel(prodExample, validator);
        List<InstanceArrangement> instArr = prodExample.getInstanceArrangement();
        for (InstanceArrangement instanceArrangement : instArr)
            validateModel(instanceArrangement, validator);

        if (!errLst.isEmpty())
            throw new BadReqException(errLst.toString());
        return null;
    }



}
