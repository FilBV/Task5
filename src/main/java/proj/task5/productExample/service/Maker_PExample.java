package proj.task5.productExample.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import proj.task5.Interface.StepPExampleExecable;

import proj.task5.productExample.model.ProdExample;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Service
public class Maker_PExample {
    ProdExample modelProdExample;

    List<StepPExampleExecable> stepLstNullInstance = new ArrayList<>();
    List<StepPExampleExecable> stepLstNotNullInstance = new ArrayList<>();
    public Maker_PExample() {}
    @Autowired
    public Maker_PExample(@Qualifier("1_PE")  Step_1_PE step_1
                        , @Qualifier("11_PE") Step_11_PE step_11
                        , @Qualifier("12_PE") Step_12_PE step_12
                        , @Qualifier("13_PE") Step_13_PE step_13
                        , @Qualifier("14_PE") Step_14_PE step_14
                        , @Qualifier("21_PE") Step_21_PE step_21
                        , @Qualifier("23_PE") Step_23_PE step_23
                        ) {

        stepLstNullInstance = List.of(step_1, step_11, step_12, step_13, step_14); // Если НЕ задан InstanceId
        stepLstNotNullInstance = List.of(step_1, step_21, step_12, step_23); // Если Задан InstanceId

    }

    Object CreateAnswerOk(StructOkAnswer structOkAnswer) {
        Map<String,  StructOkAnswer> mp = new HashMap<>();
        mp.put("data", structOkAnswer);
        return mp;
    }
   public Object execute(){
       List<StepPExampleExecable>   listExecArr;
       if (modelProdExample.getInstanceId() == null)
           listExecArr = new ArrayList<>(stepLstNullInstance);
       else
           listExecArr = new ArrayList<>(stepLstNotNullInstance);

       Object  resp = null;
       for (StepPExampleExecable step : listExecArr) {
           resp = step.execute(modelProdExample);
       }

       if (!(resp == null))
           return CreateAnswerOk((StructOkAnswer) resp);
       return ResponseEntity.status((HttpStatus.INTERNAL_SERVER_ERROR))
               .body(Map.of(HttpStatus.INTERNAL_SERVER_ERROR.value(), "Не предвиденная ошибка чтения/записи данных"));

    }


}
