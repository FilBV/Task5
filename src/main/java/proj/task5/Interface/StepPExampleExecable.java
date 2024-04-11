package proj.task5.Interface;

import org.springframework.http.ResponseEntity;
import proj.task5.productExample.model.ProdExample;


public interface StepPExampleExecable {
  Object execute(ProdExample prodExample);
}
