package com.ebao.ap99.partner.convertor;

import java.util.ArrayList;
import java.util.List;

import com.ebao.ap99.partner.entity.TBankAccount;
import com.ebao.ap99.partner.entity.TPartner;
import com.ebao.ap99.partner.model.BankAccount;
import com.ebao.unicorn.platform.foundation.utils.BeanUtils;

public class BankAccountConvertor {
    public TBankAccount modelToEntity(BankAccount bankAccount) {
        TBankAccount entity = new TBankAccount();
        BeanUtils.copyProperties(entity, bankAccount);
        return entity;
    }

    public BankAccount entityToModel(TBankAccount entity) {
        BankAccount bankAccount = new BankAccount();
        BeanUtils.copyProperties(bankAccount, entity);
        return bankAccount;
    }

    public List<TBankAccount> modelListToEntityList(List<BankAccount> bankAccountList) {
        List<TBankAccount> entityList = new ArrayList<TBankAccount>();
        for (BankAccount bankAccount : bankAccountList) {
            TBankAccount entity = modelToEntity(bankAccount);
            entityList.add(entity);
        }
        return entityList;
    }

    public void convertEntityList(List<BankAccount> bankAccountList, TPartner entity) {
        for (BankAccount bankAccount : bankAccountList) {
            TBankAccount bankAccountEntity = modelToEntity(bankAccount);
            entity.addTBankAccount(bankAccountEntity);
        }
    }

    public List<BankAccount> entitylListToModelList(List<TBankAccount> entityList) {
        List<BankAccount> bankAccountList = new ArrayList<BankAccount>();
        for (TBankAccount entity : entityList) {
            BankAccount bankAccount = entityToModel(entity);
            bankAccountList.add(bankAccount);
        }
        return bankAccountList;
    }
}
