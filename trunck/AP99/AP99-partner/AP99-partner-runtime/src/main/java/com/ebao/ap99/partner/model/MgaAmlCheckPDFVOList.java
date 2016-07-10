package com.ebao.ap99.partner.model;


import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


import com.ebao.ap99.file.service.XMLParseInterface;

@XmlRootElement(name = "Root")
public class MgaAmlCheckPDFVOList implements XMLParseInterface {

    @XmlElementWrapper
    @XmlElement(name = "brokerAmlCheckPDFVO")
    private List<MgaAmlCheckPDFVO> VOList = new ArrayList<MgaAmlCheckPDFVO>();

    public void addNewObject(MgaAmlCheckPDFVO o) {
        VOList.add(o);
    }

    @Override
    public List getBoList() {
        return VOList;
    }

}