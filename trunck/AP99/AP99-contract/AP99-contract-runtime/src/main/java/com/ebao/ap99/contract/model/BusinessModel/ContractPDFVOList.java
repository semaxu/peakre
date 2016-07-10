package com.ebao.ap99.contract.model.BusinessModel;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;

import com.ebao.ap99.contract.model.PDFModel.ContractPDFVO;
import com.ebao.ap99.file.service.XMLParseInterface;

@XmlRootElement(name = "Root")
public class ContractPDFVOList implements XMLParseInterface {

    @XmlElementWrapper
    @XmlElement(name = "contractPDFVO")
    private List<ContractPDFVO> VOList = new ArrayList<ContractPDFVO>();

    public void addNewObject(ContractPDFVO o) {
        VOList.add(o);
    }

    @Override
    public List getBoList() {
        return VOList;
    }

}
