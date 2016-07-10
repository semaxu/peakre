(function(context){
    var $page = $pt.getService(context,'$page');

    var Layout = jsface.Class({
        createMainInfo: function () {
            return {
                label: "Compliance Check List",
                style: "primary-darken",
                collapsible: false,
                layout:{
                    CompanyName:{
                        label:"Company Name",
                        comp:{
                            type:$pt.ComponentConstants.Text
                        },
                        pos:{
                            row:1,
                            width:6
                        }
                    },
                    CountryIncorporation:{
                        label:"Country of Incorporation",
                        comp:{
                            type: $pt.ComponentConstants.Select,
                            data:$page.codes.CedentCountry
                        },
                        pos:{
                            row:2,
                            width:6
                        }
                    },
                    SanctionedCountry:{
                        label:"Domiciled in an UN sanctioned country",
                        comp:{
                            type:$pt.ComponentConstants.Radio,
                            data:$page.codes.Boolean
                        },
                        pos:{
                            row:3,
                            width:10
                        }
                    },
                    Licensed:{
                        label:"Licensed by the insurance Regulatory Body",
                        comp:{
                            type:$pt.ComponentConstants.Radio,
                            data:$page.codes.Boolean
                        },
                        pos:{
                            row:4,
                            width:10
                        }
                    },
                    internationalScaleRatingFrom:{
                        label:"International Scale Rating from",
                        comp:{
                            type:$pt.ComponentConstants.Label,
                            textFromModel:false
                        },
                        pos:{
                            row:5,
                            width:3
                        }
                    },
                    Sp:{
                        label:"S&P",
                        comp:{
                            type:$pt.ComponentConstants.Radio,
                            data:$page.codes.Boolean
                        },
                        pos:{
                            row:5,
                            width:3
                        }
                    },
                    SpText:{
                        base: $helper.basePercentage(2),
                        comp:{
                            enabled: {
                                when: function (model) {
                                    if (model.get('Sp') != undefined && model.get('Sp') != null) {
                                        if (model.get('Sp') == 0) {
                                            model.set("SpText", null);
                                            return false;
                                        }else{
                                            return true;
                                        }
                                    }else{
                                        return true;
                                    }
                                },
                                depends: 'Sp'
                            }
                        },
                        pos:{
                            row:5,
                            width:2
                        }
                    },
                    placeHolder1:{
                        comp:{
                            type:$pt.ComponentConstants.Label,
                            textFromModel:false
                        },
                        pos:{
                            row:6,
                            width:3
                        }
                    },
                    Ambest:{
                        label:"A.M.Best",
                        comp:{
                            type:$pt.ComponentConstants.Radio,
                            data:$page.codes.Boolean
                        },
                        pos:{
                            row:6,
                            width:3
                        }
                    },
                    AmbestText:{
                        base: $helper.basePercentage(2),
                        comp:{
                            enabled: {
                                when: function (model) {
                                    if (model.get('Ambest') != undefined && model.get('Ambest') != null) {
                                        if (model.get('Ambest') == 0) {
                                            model.set("AmbestText", null);
                                            return false;
                                        }else{
                                            return true;
                                        }
                                    }else{
                                        return true;
                                    }
                                },
                                depends: 'Ambest'
                            }
                        },
                        pos:{
                            row:6,
                            width:2
                        }
                    },
                    placeHolder2:{
                        comp:{
                            type:$pt.ComponentConstants.Label,
                            textFromModel:false
                        },
                        pos:{
                            row:7,
                            width:3
                        }
                    },
                    Fitch:{
                        label:"Fitch",
                        comp:{
                            type:$pt.ComponentConstants.Radio,
                            data:$page.codes.Boolean
                        },
                        pos:{
                            row:7,
                            width:3
                        }
                    },
                    FitchText:{
                        base: $helper.basePercentage(2),
                        comp:{
                            enabled: {
                                when: function (model) {
                                    if (model.get('Fitch') != undefined && model.get('Fitch') != null) {
                                        if (model.get('Fitch') == 0) {
                                            model.set("FitchText", null);
                                            return false;
                                        }else{
                                            return true;
                                        }
                                    }else{
                                        return true;
                                    }
                                },
                                depends: 'Fitch'
                            }
                        },
                        pos:{
                            row:7,
                            width:2
                        }
                    },
                    placeHolder3:{
                        comp:{
                            type:$pt.ComponentConstants.Label,
                            textFromModel:false
                        },
                        pos:{
                            row:8,
                            width:3
                        }
                    },
                    Moodys:{
                        label:"Moody's",
                        comp:{
                            type:$pt.ComponentConstants.Radio,
                            data:$page.codes.Boolean
                        },
                        pos:{
                            row:8,
                            width:3
                        }
                    },
                    MoodysText:{
                        base: $helper.basePercentage(2),
                        comp:{
                            enabled: {
                                when: function (model) {
                                    if (model.get('Moodys') != undefined && model.get('Moodys') != null) {
                                        if (model.get('Moodys') == 0) {
                                            model.set("MoodysText", null);
                                            return false;
                                        }else{
                                            return true;
                                        }
                                    }else{
                                        return true;
                                    }
                                },
                                depends: 'Moodys'
                            }
                        },
                        pos:{
                            row:8,
                            width:2
                        }
                    },
                    placeHolder4:{
                        comp:{
                            type:$pt.ComponentConstants.Label,
                            textFromModel:false
                        },
                        pos:{
                            row:9,
                            width:3
                        }
                    },
                    Others:{
                        label:"Others",
                        comp:{
                            type:$pt.ComponentConstants.Radio,
                            data:$page.codes.Boolean
                        },
                        pos:{
                            row:9,
                            width:3
                        }
                    },
                    OthersText:{
                        comp:{
                            type:$pt.ComponentConstants.Text,
                            label:false,
                            enabled: {
                                when: function (model) {
                                    if (model.get('Others') != undefined && model.get('Others') != null) {
                                        if (model.get('Others') == 0) {
                                            model.set("OthersText", null);
                                            return false;
                                        }else{
                                            return true;
                                        }
                                    }else{
                                        return true;
                                    }
                                },
                                depends: 'Others'
                            }
                        },
                        pos:{
                            row:9,
                            width:2
                        }
                    },

                    commentPanel:{
                        label:"Other Comments",
                        comp:{
                            type:$pt.ComponentConstants.Panel,
                            editLayout:{
                                Comments:{
                                    comp:{
                                        type:$pt.ComponentConstants.TextArea,
                                        labelDirection:"vertical",
                                        textFromModel:false,
                                        lines:3
                                    },
                                    pos:{
                                        width:12
                                    }
                                }
                            }
                        },
                        pos:{
                            row:10,
                            width:8
                        }
                    },

                    AmlCheckResult: {
                        label: "AML Check Result",
                        comp: {
                            type: $pt.ComponentConstants.Radio,
                            data: $page.ApprovedOrDeclined
                        },
                        pos: {
                            row: 11
                        }
                    },
                    CheckDate: {
                        label: "Check Date",
                        comp: {
                            type: $pt.ComponentConstants.Date,
                            format: "DD/MM/YYYY"
                        },
                        pos: {
                            row: 12
                        }
                    },
                    CheckBy: {
                        label: "Checked By",
                        comp: {
                            type: $pt.ComponentConstants.Select,
                            data: $page.codes.SystemUser
                        },
                        pos: {
                            row: 12
                        }
                    },
                    ApprovedBy: {
                        label: "Approved By",
                        comp: {
                            type: $pt.ComponentConstants.Select,
                            data:  $page.codes.SystemUser
                        },
                        pos: {
                            row: 12
                        }
                    },
                    remarkPanel: {
                        label: "Remarks",
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                Remarks: {
                                    comp: {
                                        type: $pt.ComponentConstants.TextArea,
                                        lines: 3,
                                        labelDirection: "vertical"
                                    },
                                    pos: {
                                        width: 12
                                    }
                                }
                            }
                        },
                        pos: {
                            row: 13,
                            width:8
                        }
                    },

                    buttonPanel:{
                        comp:{
                            type:$pt.ComponentConstants.ButtonFooter,
                            buttonLayout:{
                                right:[
                                    {
                                        text:"Exit",
                                        style:"warning",
                                        click:function(model){
                                            var url = "bpCreationForCompany.html?partnerId=" + this.getModel().get("PartnerId")+"&pageType="+ $page.pageType;
                                            window.location.href = url;
                                        }
                                    }, {
                                        text:"Print",
                                        style:"primary",
                                        click:function(model){
                                            if(this.getModel().get("AmlCheckId")==null){
                                                NConfirm.getConfirmModal().show({
                                                    title: 'Message',
                                                    disableClose: true,
                                                    messages: ['Please save the checklist before printing it.']
                                                });
                                            }else{
                                                var criteria = {
                                                    AmlCheckId : this.getModel().get("AmlCheckId")
                                                }
                                                $pt.generateFile(24,criteria);
                                            }
                                        }
                                    }, {
                                        text:"Save",
                                        style:"primary",
                                        enabled: {
                                            depends: "status",
                                            when: function (model) {
                                                return $page.pageType == 0;
                                            }
                                        },
                                        click:function(model){
                                            $page.controller.save();
                                        }
                                    }, {
                                        text: "Attachment",
                                        style: "primary",
                                        enabled: {
                                            depends: "AmlCheckId",
                                            when: function (model) {
                                                return model.getCurrentModel().AmlCheckId && true;
                                            }
                                        },
                                        click: function (model) {
                                            $pt.viewAttachment (1, model.getCurrentModel().AmlCheckId,$page.pageType );

                                        }
                                    }
                                ]
                            }
                        },
                        pos:{
                            width:12
                        }
                    }
                }
            };
        },
        createFormLayout: function () {
            return {
                _sections: {
                    complianceInfoSection: this.createMainInfo()
                }
            }
        }
    });

    $page.layout = new Layout();

}(typeof window !== "undefined" ? window : this));
