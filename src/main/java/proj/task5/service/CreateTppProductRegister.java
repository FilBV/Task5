package proj.task5.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import proj.task5.enumState.State;
import proj.task5.Interface.CreateRecordsable;
import proj.task5.productExample.model.ProdExample;
import proj.task5.productRegistr.model.ProdRegistr;
import proj.task5.repository.Account_poolRepo;
import proj.task5.repository.Tpp_product_registerRepo;
import proj.task5.repository.Tpp_ref_product_classRepo;
import proj.task5.entity.*;

import java.util.ArrayList;
import java.util.List;

@Data
@Service
@Qualifier("TppProductRegister")
public class CreateTppProductRegister  implements CreateRecordsable {
    @Autowired
    Account_poolRepo account_poolRepo;
    @Autowired
    Tpp_product_registerRepo tpp_product_registerRepo;
    @Autowired
    Tpp_ref_product_classRepo  tpp_ref_product_classRepo;


    private List<Tpp_ref_product_register_type>     tppTypeLst = new ArrayList<>();
    private Tpp_product tpp_product;

    public List<Tpp_ref_product_register_type> getLstType(String product_code){
        List<Tpp_ref_product_register_type> tppTypeList = new ArrayList<>();
        List<Tpp_ref_product_class>  tppRefProductClassLst;
        tppRefProductClassLst = tpp_ref_product_classRepo.findByValue(product_code); //
        for (Tpp_ref_product_class tpcl : tppRefProductClassLst) {
            List<Tpp_ref_product_register_type> tppProdRegTypeLst = tpcl.getTpp_ref_product_register_types();
            for (Tpp_ref_product_register_type type : tppProdRegTypeLst) {
                if (type.getAccount_type().getValue().equals("Клиентский"))
                    tppTypeList.add(type);
            }
        }
        tppTypeLst = tppTypeList;
        return  tppTypeLst;
    }

    @Override
    public <T, K>  T  create_rec_table(K model){
        ProdRegistr prodRegistr = (ProdRegistr) model;
        Tpp_product_register tpp_product_register = new Tpp_product_register();
        Account_pool account_pool = account_poolRepo.findFirstByBranchCodeAndCurrencyCodeAndMdmCodeAndPriorityCodeAndRegistryTypeCode(
                prodRegistr.getBranchCode()             // branch_code
               , prodRegistr.getCurrencyCode()          //currency_code
               , prodRegistr.getMdmCode()               //mdm_code
               , prodRegistr.getPriorityCode()          //priority_code
               , prodRegistr.getRegistryTypeCode()      //registry_type_code
                );
       tpp_product_register.setProductId(prodRegistr.getInstanceId());
       tpp_product_register.setType(prodRegistr.getRegistryTypeCode());
       tpp_product_register.setCurrency_code(prodRegistr.getCurrencyCode());
       tpp_product_register.setState(State.OPEN.getName());

       if (!(account_pool == null)) {

           List<Account> accountLst = account_pool.getAccountList();
           Account account = accountLst.get(0);
           tpp_product_register.setAccount(account.getId());
           tpp_product_register.setAccountNumber(account.getAccount_number());
       }

       Tpp_product_register tpp_product_registerSave = tpp_product_registerRepo.save(tpp_product_register);
       return  (T) tpp_product_registerSave;
    }

    @Override
    public<T, K> List<T> create_recs_table(K model){
    ProdExample prodExample = (ProdExample) model;
    List<Tpp_product_register> prodRegLst = new ArrayList<>();

    tppTypeLst = getLstType(prodExample.getProductCode());
    for (Tpp_ref_product_register_type typeLst : tppTypeLst) {

        ProdRegistr prodRegistr = new ProdRegistr();
        prodRegistr.setInstanceId(tpp_product.getId());
        prodRegistr.setBranchCode(prodExample.getBranchCode());
        prodRegistr.setCurrencyCode(prodExample.getIsoCurrencyCode());
        prodRegistr.setMdmCode(prodExample.getMdmCode());
        prodRegistr.setPriorityCode(prodExample.getUrgencyCode());
        prodRegistr.setRegistryTypeCode(typeLst.getValue());
        Tpp_product_register tpp_product_register = create_rec_table(prodRegistr);
        prodRegLst.add(tpp_product_register);
    }
    return (List<T>) prodRegLst;
}

   public List<Tpp_product_register> create_recs_table_product_register(ProdExample prodExample
                                                                      , Tpp_product tpp_product){
        this.tpp_product = tpp_product;
       return create_recs_table(prodExample);
    }


}
