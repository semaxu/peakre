package com.ebao.ap99.arap.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.ebao.ap99.arap.service.FeeService;
import com.ebao.ap99.arap.service.FinanceService;
import com.ebao.ap99.arap.service.GLDataService;
import com.ebao.ap99.arap.service.SettlementService;
import com.ebao.ap99.arap.vo.BusinessFeeModel;
import com.ebao.ap99.arap.vo.FinancialItemVO;
import com.ebao.ap99.arap.vo.FnViewVO;
import com.ebao.ap99.arap.vo.SectionTableModel;
import com.ebao.ap99.arap.vo.SettlementHistory;
import com.ebao.ap99.arap.vo.TableColumn;
import com.ebao.ap99.contract.entity.ContractModel;
import com.ebao.ap99.contract.service.ContractService;
import com.ebao.ap99.parent.model.TreeModel;

public class FinanceServiceImpl implements FinanceService {

    @Autowired
    FeeService feeService;

    @Autowired
    SettlementService settlementService;

    @Autowired
    GLDataService glDataService;

    @Autowired
    private ContractService contractService;

    @Override
    public Integer writeToFinance(List<BusinessFeeModel> modelList) throws Exception {
        return feeService.writeToFinance(modelList);
    }

    @Override
    public List<SettlementHistory> getSettlementHistory(Integer bizTransType, Long bizTransId) throws Exception {
        return settlementService.getSettlementHistory(bizTransType, bizTransId);
    }

    @Override
    public FnViewVO getFnView(Long contractId) throws Exception {

        FnViewVO fnViewVO = new FnViewVO();
        ContractModel contractModel = contractService.getContractInfoByCompId(contractId);
        List<TreeModel> list = contractService.getContractStructureByCode(contractModel.getContractCode(),
                contractModel.getUwYear(), true);
        if (CollectionUtils.isNotEmpty(list)) {
            TreeModel contractLevel = list.get(0);

            initialTableColumn(fnViewVO, 1L, contractId);
            fnViewVO.setContractTable(glDataService.getFnView(1L, contractId));

            List<SectionTableModel> sectionTables = new ArrayList<SectionTableModel>();
            List<TreeModel> sectionLevel = contractLevel.getChildren();
            if (CollectionUtils.isNotEmpty(sectionLevel)) {
                for (TreeModel temp : sectionLevel) {
                    Long sectionId = (Long)temp.getId();
                    String sectionName = (String)temp.getText();
                    List<FinancialItemVO> itemTable = glDataService.getFnView(2L, sectionId);
                    sectionTables.add(new SectionTableModel(sectionName, sectionId, itemTable));
                }
            }
            fnViewVO.setSectionTables(sectionTables);
        }
        return fnViewVO;
    }

    private void initialTableColumn(FnViewVO fnViewVO, Long level, Long conCompId) throws Exception {
        List<TableColumn> tableColumns = new ArrayList<TableColumn>();
        int i = 1;
        List<String> fnQuarters = glDataService.getFnQuarters(level, conCompId);
        for (String fnQuarter : fnQuarters) {
            TableColumn tableColumn = new TableColumn(fnQuarter, TableColumn.VALUE_KEY + i);
            tableColumns.add(tableColumn);
            i++;
        }
        fnViewVO.setTableColumns(tableColumns);
    }
}
