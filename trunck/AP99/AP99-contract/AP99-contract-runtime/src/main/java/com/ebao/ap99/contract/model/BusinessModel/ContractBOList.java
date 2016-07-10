package com.ebao.ap99.contract.model.BusinessModel;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.ebao.ap99.file.service.XMLParseInterface;

@XmlRootElement(name = "Root")
public class ContractBOList implements XMLParseInterface {

    @XmlElementWrapper
    @XmlElement(name = "contractBO")
    private List<ContractBO> BOList = new ArrayList<ContractBO>();

    public void addNewObject(ContractBO o) {
        BOList.add(o);
    }

    @Override
    public List getBoList() {
        return BOList;
    }

}
