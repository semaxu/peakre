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
                            width:8
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
                            width:8
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
                            width:8
                        }
                    },
                    InsuranceBody:{
                        label:"Licensed By The Insurance Regulatory Body",
                        comp:{
                            type:$pt.ComponentConstants.Radio,
                            data:$page.codes.Boolean
                        },
                        pos:{
                            row:4,
                            width:8
                        }
                    },
                    BrokerAssociation:{
                        label:"Member Of Local Broker Association",
                        comp:{
                            type:$pt.ComponentConstants.Radio,
                            data:$page.codes.Boolean
                        },
                        pos:{
                            row:5,
                            width:12
                        }
                    },
                    LloydBroker:{
                        label:"Lloyd's Broker",
                        comp:{
                            type:$pt.ComponentConstants.Radio,
                            data:$page.codes.Boolean
                        },
                        pos:{
                            row:6,
                            width:12
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
                            row:7,
                            width:8
                        }
                    },

                    CertificateIncorporation:{
                        label:"Certificate Of Incorporation",
                        comp:{
                            type:$pt.ComponentConstants.Text
                        },
                        pos:{
                            row:8,
                            width:8
                        }
                    },
                    OwnershipStructure:{
                        label:"Ownership Structure",
                        comp:{
                            type:$pt.ComponentConstants.Text
                        },
                        pos:{
                            row:9,
                            width:8
                        }
                    },
                    NamesOwners:{
                        label:"Name(s) Of Owner(s)",
                        comp:{
                            type:$pt.ComponentConstants.Text
                        },
                        pos:{
                            row:10,
                            width:8
                        }
                    },
                    NamesDirectors:{
                        label:"Name(s) Of Director(s)",
                        comp:{
                            type:$pt.ComponentConstants.Text
                        },
                        pos:{
                            row:11,
                            width:8
                        }
                    },
                    NamesSeniorManagements:{
                        label:"Name(s) Of Senior Management",
                        comp:{
                            type:$pt.ComponentConstants.Text
                        },
                        pos:{
                            row:12,
                            width:8
                        }
                    },
                    IsBrokerSanctioned:{
                        label:"Is The Broker On UN Sanctioned List",
                        comp:{
                            type:$pt.ComponentConstants.Radio,
                            data:$page.codes.Boolean
                        },
                        pos:{
                            row:13,
                            width:12
                        }
                    },
                    IsOwnerTerrorist:{
                        label:"Is(Are) The Owner On UN Terrorist List",
                        comp:{
                            type:$pt.ComponentConstants.Radio,
                            data:$page.codes.Boolean
                        },
                        pos:{
                            row:14,
                            width:12
                        }
                    },
                    IsDirectorTerrorist:{
                        label:"Is(Are) The Director On UN Terrorist List",
                        comp:{
                            type:$pt.ComponentConstants.Radio,
                            data:$page.codes.Boolean
                        },
                        pos:{
                            row:15,
                            width:12
                        }
                    },
                    IsManagementTerrorist:{
                        label:"Is Any Person Of Senior Management On UN Terrorist List",
                        comp:{
                            type:$pt.ComponentConstants.Radio,
                            data:$page.codes.Boolean
                        },
                        pos:{
                            row:16,
                            width:12
                        }
                    },
                    remarksPanel:{
                        label:"Other Comments",
                        comp:{
                            type:$pt.ComponentConstants.Panel,
                            editLayout:{
                                Remarks:{
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
                            row:17,
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
                            row: 18
                        }
                    },
                    CheckDate: {
                        label: "Check Date",
                        comp: {
                            type: $pt.ComponentConstants.Date,
                            format: "DD/MM/YYYY"
                        },
                        pos: {
                            row: 19
                        }
                    },
                    CheckBy: {
                        label: "Checked By",
                        comp: {
                            type: $pt.ComponentConstants.Select,
                            data:  $page.codes.SystemUser
                        },
                        pos: {
                            row: 19
                        }
                    },
                    ApprovedBy: {
                        label: "Approved By",
                        comp: {
                            type: $pt.ComponentConstants.Select,
                            data: $page.codes.SystemUser
                        },
                        pos: {
                            row: 19
                        }
                    },
                    remarkPanel: {
                        label: "Remarks",
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                CheckRemarks: {
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
                            row: 20,
                            width: 12
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
                                                $pt.generateFile(17,criteria);
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
                                    },  {
                                        text: "Attachment",
                                        style: "primary",
                                        enabled: {
                                            depends: "AmlCheckId",
                                            when: function (model) {
                                                return model.getCurrentModel().AmlCheckId && true;
                                            }
                                        },
                                        click: function (model) {
                                            $pt.viewAttachment (1, model.getCurrentModel().AmlCheckId, $page.pageType );
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
