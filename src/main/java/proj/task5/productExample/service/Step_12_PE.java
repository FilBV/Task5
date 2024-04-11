package proj.task5.productExample.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import proj.task5.Interface.StepPExampleExecable;
import proj.task5.exceptions.BadReqException;
import proj.task5.productExample.model.InstanceArrangement;
import proj.task5.productExample.model.ProdExample;
import proj.task5.repository.AgreementRepo;
import proj.task5.entity.Agreement;


import java.util.List;

@Service
@Qualifier("12_PE")
public class Step_12_PE implements StepPExampleExecable {
    @Autowired
    AgreementRepo agreementRepo;

    @Override
    public ResponseEntity<?> execute(ProdExample prodExample) {
        List<InstanceArrangement>  agreeementLst = prodExample.getInstanceArrangement();
        for (InstanceArrangement agr: agreeementLst) {
            Agreement agreement = agreementRepo.findFirstByNumber(agr.getNumber());
            if (!(agreement == null))
                throw new BadReqException("Параметр Дополнительного соглашения(сделки) Number " + agr.getNumber() +
                                        " уже существует для ЭП с ИД " + agreement.getId());
        }
        return null;
    }
}
