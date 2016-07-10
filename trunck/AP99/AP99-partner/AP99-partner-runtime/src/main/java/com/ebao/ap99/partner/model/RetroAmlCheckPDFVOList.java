package com.ebao.ap99.partner.model;


import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;


import com.ebao.ap99.file.service.XMLParseInterface;

@XmlRootElement(name = "Root")
public class RetroAmlCheckPDFVOList implements XMLParseInterface {

    @XmlElementWrapper
    @XmlElement(name = "amlCheckPDFVO")
    private List<RetroAmlCheckPDFVO> VOList = new ArrayList<RetroAmlCheckPDFVO>();

    public void addNewObject(RetroAmlCheckPDFVO o) {
        VOList.add(o);
    }

    @Override
    public List getBoList() {
        return VOList;
    }

}