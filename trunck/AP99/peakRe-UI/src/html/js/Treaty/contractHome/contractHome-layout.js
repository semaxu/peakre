(function (context) {
    var $page = $pt.getService(context, '$page');
    var codes = $pt.getService($page, 'codes');
    var reviewResult = $pt.createCodeTable([
        {id: '0', text: 'Reject'},
        {id: '1', text: 'Approve'}
    ]);

    NTable.registerInlineEditor("AmountForClaim", {
        comp: {
            type:{label:false},
            enabled: {
                when: function (model) {
                    if (!model.get('IsCheck')) {
                        model.set("Amount", null);
                        return false;
                    }
                    return true;
                },
                depends: 'IsCheck'
            }
        },
        base:$helper.baseAmount(2)
    });

    var layout = jsface.Class({
        createDividerBase: function () {
            return {
                comp: {
                    type: $pt.ComponentConstants.Divider
                },
                pos: {
                    width: 12
                },
                css: {
                    cell: 'form-cell-divider'
                }
            }
        },
        createBaseLayout: function () {
            return {
                _sections: {
                    headSection: {
                        layout: {
                            viewDocumentButtonPanel: {
                                comp: {
                                    type: $pt.ComponentConstants.ButtonFooter,
                                    buttonLayout: {
                                        left: [
                                            {
                                                text: "View Imported XML",
                                                style: "link",
                                                click: function (model) {
                                                    $page.controller.viewContractDocument({
                                                        DocumentId: model.get('DocumentId'),
                                                        PageIndex: '1',
                                                        CountPerPage: '10'
                                                    });
                                                },
                                                visible: {
                                                    when: function (model) {
                                                        return model.get('DocumentId') != null && model.get('DocumentId') != '' && model.get('DocumentId') != undefined;
                                                    },
                                                    depends: 'DocumentId'
                                                }
                                            }
                                        ]
                                    }
                                },
                                pos: {
                                    width: 4
                                }
                            },
                            LinkName: {
                                label: "Linked Contract Name",
                                comp: {
                                    type: $pt.ComponentConstants.Text,
                                    visible: {
                                        when: function (model) {
                                            return !((model.get('LinkName') == undefined || model.get('LinkName') == null) && (model.get('OperateType') == 0 || model.get('OperateType') == 5));
                                        }
                                    },
                                    labelWidth: 6
                                },
                                pos: {
                                    width: 4
                                }
                            },
                            buttonPanel: {
                                comp: {
                                    type: $pt.ComponentConstants.ButtonFooter,
                                    buttonLayout: {
                                        right: [
                                            {
                                                text: "Linked Contract",
                                                style: "link",
                                                visible: {
                                                    when: function (model) {
                                                        return !((model.get('LinkName') == undefined || model.get('LinkName') == null) && (model.get('OperateType') == 0 || model.get('OperateType') == 5));
                                                    }
                                                },
                                                click: function (model) {
                                                    if (this.getModel().get("OperateType") != 0) {
                                                        if (!model.get("LinkName")) {
                                                            NConfirm.getConfirmModal().show("Confirm Dialog", {
                                                                messages: ['Please input Link Contract Name.']
                                                            });
                                                            return;
                                                        }
                                                        if (!model.get("Cedent")) {
                                                            NConfirm.getConfirmModal().show("Confirm Dialog", {
                                                                messages: ['Please input Cedent.']
                                                            });
                                                            return;
                                                        }
                                                        if (!$page.controller.save()) {
                                                            return;
                                                        }
                                                    }
                                                    $page.controller.doShowLinkContractDialog({
                                                        contractId: model.get("ContractCode"),
                                                        linkName: model.get("LinkName"),
                                                        uwYear: model.get("UwYear")
                                                    });
                                                }
                                            }
                                        ]
                                    }
                                },
                                pos: {
                                    width: 2
                                }
                            }

                        }
                    }
                }
            }
        },
        createBasicContractLayout: function () {
            return {
                _sections: {
                    basicContractInfo: {
                        label: "Basic Contract Info",
                        style: 'primary-darken',
                        collapsible: true,
                        expanded: $page.controller.isExpanded(),
                        layout: {
                            subPanel01: {
                                comp: {
                                    type: $pt.ComponentConstants.Panel,
                                    editLayout: {
                                        ContractCode: {
                                            label: "Contract ID",
                                            comp: {
                                                type: $pt.ComponentConstants.Text,
                                                enabled: false
                                            },
                                            pos: {
                                                row: 1
                                            }
                                        },
                                        ContractName: {
                                            label: "Contract Name",
                                            comp: {
                                                type: $pt.ComponentConstants.Text
                                            },
                                            pos: {
                                                row: 1
                                            }
                                        },
                                        Mainclass: {
                                            label: "Main CoB",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: $page.codes.Class
                                            },
                                            pos: {
                                                row: 1
                                            }
                                        },
                                        BrokerRef: {
                                            label: "Broker Reference",
                                            comp: {
                                                type: $pt.ComponentConstants.Text
                                            },
                                            pos: {
                                                row: 1
                                            }
                                        },
                                        CedentRef: {
                                            label: "Cedent Reference",
                                            comp: {
                                                type: $pt.ComponentConstants.Text
                                            },
                                            pos: {
                                                row: 1
                                            }
                                        },
                                        Subclass: {
                                            label: "Main Sub CoB",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: $page.codes.SubClass,
                                                parentPropId: "Mainclass",
                                                parentFilter: "parentId"
                                            },
                                            pos: {
                                                row: 1
                                            }
                                        },
                                        AppianNo: {
                                            label: "Appian No.",
                                            comp: {
                                                type: $pt.ComponentConstants.Text
                                            },
                                            pos: {
                                                row: 1
                                            }
                                        },
                                        PricingRef: {
                                            label: "Pricing Reference",
                                            comp: {
                                                type: $pt.ComponentConstants.Text
                                            },
                                            pos: {
                                                row: 1
                                            }
                                        }
                                    }
                                },
                                pos: {
                                    row: 10,
                                    width: 12
                                }
                            },
                            divider1: {
                                base: this.createDividerBase(),
                                pos: {
                                    row: 15
                                }
                            },
                            subPanel02: {
                                comp: {
                                    type: $pt.ComponentConstants.Panel,
                                    editLayout: {
                                        LatestStatus: {
                                            label: "Contract Status",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: $page.codes.LatestStatus,
                                                enabled: false
                                            },
                                            pos: {
                                                row: 20
                                            }
                                        },
                                        ContractType: {
                                            label: "Contract Type",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: $page.codes.ContractType,
                                                enabled: {
                                                    when: function (model) {
                                                        return model.get('OperateType') != '3' && model.get('OperateType') != '2' ;
                                                    }
                                                }
                                            },
                                            pos: {
                                                row: 20
                                            }
                                        },
                                        ContractNature: {
                                            label: "Contract Nature",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: $page.codes.ContractNature,
                                                enabled: {
                                                    when: function (model) {
                                                        return model.get('OperateType') != '3' && model.get('OperateType') != '2' ;
                                                    }
                                                }
                                            },
                                            pos: {
                                                row: 20
                                            }
                                        },
                                        ContractCategory: {
                                            label: "Contract Category",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: $page.codes.ContractCategory,
                                                enabled: {
                                                    when: function (model) {
                                                        return model.get('OperateType') != '3' && model.get('OperateType') != '2' ;
                                                    }
                                                }
                                            },
                                            pos: {
                                                row: 20
                                            }
                                        },
                                        Fronting: {
                                            label: "Fronting",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: $page.codes.Boolean
                                            },
                                            pos: {
                                                row: 20
                                            }
                                        },
                                        DepositAccounting: {
                                            label: "Deposit Accounting",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: $page.codes.Boolean
                                            },
                                            pos: {
                                                row: 20
                                            }
                                        },
                                        ProtectionBasic: {
                                            label: "Protection Basis",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: $page.codes.ProtectionBasis
                                            },
                                            pos: {
                                                row: 20
                                            }
                                        },
                                        MainCurrency: {
                                            label: "Main Currency",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: $page.codes.Currency,
                                                enabled: {
                                                    when: function (model) {
                                                        return model.get('OperateType') != '3' && model.get('OperateType') != '2' ;
                                                    }
                                                }
                                            },
                                            pos: {
                                                row: 20
                                            }
                                        },
                                        PortfolioTransfer: {
                                            label: "Portfolio Transfer",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: $page.codes.TerminationCondition,
                                                visible: {
                                                    when: function (model) {
                                                        if (model.get('ContractNature') == '2') {
                                                            return false;
                                                        }
                                                        return true;
                                                    },
                                                    depends: 'ContractNature'
                                                }
                                            },
                                            pos: {
                                                row: 20
                                            }
                                        }

                                    }
                                },
                                pos: {
                                    row: 20,
                                    width: 12
                                }
                            },
                            divider2: {
                                base: this.createDividerBase(),
                                pos: {
                                    row: 25
                                }
                            },
                            subPanel03: {
                                comp: {
                                    type: $pt.ComponentConstants.Panel,
                                    editLayout: {
                                        ReinsStarting: {
                                            label: "Period Start",
                                            comp: {
                                                type: $pt.ComponentConstants.Date,
                                                //format: "DD/MM/YYYY HH:mm:ss",
                                                format: "DD/MM/YYYY 00:00:00",
                                                enabled: {
                                                    when: function (model) {
                                                        return model.get('OperateType') != '3' && model.get('OperateType') != '2' ;
                                                    }
                                                }
                                            }
                                        },
                                        UwYear: {
                                            label: "UW Year",
                                            base : $helper.baseNumber(),
                                            comp: {
                                                type: $pt.ComponentConstants.Text,
                                                enabled: {
                                                    when: function (model) {
                                                        return model.get('OperateType') != '3' && model.get('OperateType') != '2' ;
                                                    }
                                                }
                                            },
                                            css: {comp: 'text-left'}
                                        },
                                        InforceDate: {
                                            label: "In Force Date",
                                            comp: {
                                                type: $pt.ComponentConstants.Date,
                                                format: "DD/MM/YYYY"
                                            }
                                        },
                                        ReinsEnding: {
                                            label: "Period End",
                                            comp: {
                                                type: $pt.ComponentConstants.Date,
                                                //format: "DD/MM/YYYY HH:mm:ss",
                                                format: "DD/MM/YYYY 23:59:59",
                                                enabled: {
                                                    when: function (model) {
                                                        return model.get('OperateType') != '3' || (model.get('OperateType') == '3' && (model.get('EndoType') == 1 || model.get('EndoType') == 2));
                                                    }
                                                }
                                            }
                                        }
                                    }
                                },
                                pos: {
                                    row: 30,
                                    width: 12
                                }
                            },
                            divider3: {
                                base: this.createDividerBase(),
                                pos: {
                                    row: 35
                                }
                            },
                            subPanel04: {
                                comp: {
                                    type: $pt.ComponentConstants.Panel,
                                    editLayout: {
                                        Cedent: {
                                            label: "Cedent",
                                            comp: {
                                                enabled: {
                                                    when: function (model) {
                                                        return model.get('OperateType') != '3' && model.get('OperateType') != '2' ;
                                                    },
                                                    depends: 'OperateType'
                                                }
                                            },
                                            base: $helper.basePartnerSearch(1),

                                        },
                                        Broker: {
                                            label: "Broker",
                                            
                                            base: $helper.basePartnerSearch(2)
                                        },
                                        Leader: {
                                            label: "PeakRe Leader",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: $page.codes.Boolean
                                            }
                                        },
                                        CedentCountry: {
                                            label: "Cedent Country",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: $page.codes.CedentCountry,
                                                enabled :false
                                            }
                                        },

                                        CoBroker: {
                                            label: "Co-Broker",
                                            base: $helper.basePartnerSearch(2)
                                        },
                                        LeaderName: {
                                            label: "Leader Name",
                                            comp: {
                                                type: $pt.ComponentConstants.Text,
                                                visible: {
                                                    when: function (model) {
                                                        return model.get('Leader') == false;
                                                    },
                                                    depends: 'Leader'
                                                },
                                            }
                                        },
                                        MainCoverArea: {
                                            label: "Main Cover Area",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                // type: $pt.ComponentConstants.SelectTree,
                                                data:$page.codes.MainCoveredArea
                                            }
                                        },
                                        Mga: {
                                            label: "MGA",
                                            base: $helper.basePartnerSearch(5)
                                        },
                                        Insured: {
                                            label: "Insured",
                                            base: $helper.basePartnerSearch(3)
                                        }
                                    }
                                },
                                pos: {
                                    row: 40,
                                    width: 12
                                }
                            },
                            divider4: {
                                base: this.createDividerBase(),
                                pos: {
                                    row: 45
                                }
                            },
                            subPanel05: {
                                comp: {
                                    type: $pt.ComponentConstants.Panel,
                                    editLayout: {
                                        Underwriting: {
                                            label: "UW Responsible",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: codes.SystemUser
                                            }
                                        },
                                        AnalyticsResp: {
                                            label: "Analytics Responsible",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: codes.SystemUser
                                            }
                                        },
                                        MarketResp: {
                                            label: "Market Responsible",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: codes.SystemUser
                                            }
                                        },
                                        CreatedBy: {
                                            label: "Created By",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: codes.SystemUser,
                                                enabled :false

                                            }
                                        },
                                        CreatedOn: {
                                            label: "Created On",
                                            comp: {
                                                type: $pt.ComponentConstants.Date,
                                                format: "DD/MM/YYYY",
                                                enabled: false
                                            }
                                        },
                                        TreatyOwner: {
                                            label: "Treaty Owner",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: codes.SystemUser
                                            }
                                        },
                                        LastChanged: {
                                            label: "Last Changed By",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: codes.SystemUser,
                                                enabled :false
                                            }
                                        },
                                        LastChangedOn: {
                                            label: "Changed On",
                                            comp: {
                                                type: $pt.ComponentConstants.Date,
                                                format: "DD/MM/YYYY",
                                                enabled: false
                                            }
                                        },
                                        UwCompany: {
                                            label: "UW Company",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: codes.UwCompany,
                                                enabled: {
                                                    when: function (model) {
                                                        return model.get('OperateType') != '3' && model.get('OperateType') != '2' ;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                },
                                pos: {
                                    width: 12,
                                    row: 50
                                }
                            },
                            buttonPanel: {
                                label: "",
                                comp: {
                                    type: $pt.ComponentConstants.ButtonFooter,
                                    buttonLayout: {
                                        right: [
                                            {
                                                text: "Save",
                                                style: "primary",
                                                visible: $page.controller.isVisible(),
                                                click: function () {
                                                    $page.controller.save(true);
                                                }
                                            }
                                        ]
                                    }
                                },
                                pos: {
                                    row: 60,
                                    width: 12
                                }
                            }
                        }
                    }
                }
            }
        },
        createMoreContractLayout: function () {
            return {
                _sections: {
                    moreContractInfo: {
                        label: "More Contract Info",
                        style: 'primary-darken',
                        collapsible: true,
                        expanded: !($page.controller.isExpanded()),
                        layout: {
                            SectionList: {
                                label: "Sections",
                                comp: {
                                    type: $pt.ComponentConstants.Table,
                                    searchable: false,
                                    addable: true,
                                    view: {
                                      depends: "OperateType",
                                      when : function(model){return this.model.get("OperateType") == 0 || this.model.get("OperateType") == 5}
                                    },
                                    columns: [
                                        {
                                            title: "Section No",
                                            data: "ContCompId",
                                            width: "8%"
                                        }, {
                                            title: "Cession Type",
                                            data: "ShareType",
                                            width: "13%",
                                            codes: $page.codes.ShareType
                                        }, {
                                            title: "Section Name",
                                            data: "SectionName",
                                            width: "15%"
                                        }, {
                                            title: "Main Currency",
                                            data: "MainCurrency",
                                            width: "15%"
                                        }, {
                                            title: "Our share",
                                            data: "OurShare",
                                            width: "12%",
                                            render : function(row){
                                              var value = row.OurShare;
                                              if(value == 0){
                                                return "0.00%";
                                              }else{
                                                  if(!value){
                                                    value = "";
                                                  }
                                              }
                                              value = isNaN(value) || (value + '').isBlank() ? value : (value.toFixed(4) + '').movePointRight(2)+ "%";
                                              return value
                                            }
                                        }, {
                                            title: "Premium",
                                            data: "Premium",
                                            width: "15%",
                                            render: function (row) {
                                                return $helper.formatNumberForLabel(row.Premium, 2);
                                            }
                                        }, {
                                            title: "Deductions",
                                            data: "Deductions",
                                            render : function(row){
                                              var value = row.Deductions;
                                              if(value == 0){
                                                return "0.00%";
                                              }else{
                                                  if(!value){
                                                    value = "";
                                                  }
                                              }
                                              value = isNaN(value) || (value + '').isBlank() ? value : (value.toFixed(4) + '').movePointRight(2)+ "%";
                                              return value
                                            },
                                            width: "15%"
                                        }
                                    ],
                                    addClick: function (model, rowModel, layout) {
                                        $page.controller.addSection(model, rowModel, layout);
                                    },
                                    rowOperations: [
                                        {
                                            icon: "edit",
                                            click: function (row) {
                                                if (this.getModel().get("OperateType") != 0) {
                                                    var isSaved = $page.controller.save();
                                                    if (!isSaved) {
                                                        return;
                                                    }
                                                }
                                                var url = "section.html?ContCompId=" +  row.ContCompId
                                                    + "&ContractNature=" + this.getModel().get("ContractNature")
                                                    + "&OperateType=" + this.getModel().get("OperateType");
                                                if (this.getModel().get("OperateId")) {
                                                    url += "&OperateId=" + this.getModel().get("OperateId");
                                                }
                                                window.location.href = url;
                                            }
                                        }, {
                                            icon: "trash",
                                            text: "delete",
                                            visible: {
                                                when: function (row) {
                                                    if ($page.controller.model.get("OperateType") == 0 || $page.controller.model.get("OperateType") == 5) {
                                                        return false;
                                                    }else{
                                                      return !row.get("HasInforce");
                                                    }
                                                }
                                            },
                                            click: function (row) {
                                                var removeRow = function (row) {
                                                    if (row.ContCompId && row.ContCompId != 0) {
                                                        this.getModel().add("DeleteSectionList", row.ContCompId);
                                                    }
                                                    this.getModel().remove(this.getDataId(), row);
                                                    $pt.Components.NConfirm.getConfirmModal().hide();
                                                };
                                                $pt.Components.NConfirm.getConfirmModal().show(NTable.REMOVE_CONFIRM_TITLE, NTable.REMOVE_CONFIRM_MESSAGE, removeRow.bind(this, row));
                                            }
                                        }
                                    ]
                                },
                                css: {
                                    comp: "inline-editor",
                                    cell: "title-align"
                                },
                                pos: {
                                    row: 70,
                                    width: 12
                                }
                            },
                            LogList: {
                                label: "Status Log",
                                comp: {
                                    type: $pt.ComponentConstants.Table,
                                    searchable: false,
                                    sortable: false,
                                    rowOperations: [
                                        {
                                            tooltip: "view",
                                            visible: {
                                                when: function (model) {
                                                    return model.get('Status') != '5';
                                                },
                                                depends: 'Status'
                                            },
                                            click: function (row) {
                                                var url = "contractHome.html?ContCompId=" + row.ContCompId
                                                    + "&OperateId=" + row.OperateId
                                                    + "&OperateType=0";
                                                window.open(url);
                                            }
                                        }
                                    ],
                                    columns: [
                                        {
                                            title: "Contract Status",
                                            data: "Status",
                                            codes: $page.codes.LatestStatus,
                                            width: "20%"
                                        }, {
                                            title: "Update Date",
                                            data: "UpdateDate",
                                            width: "20%",
                                            render: function (row) {
                                                return $helper.formatDateForTableView(row.UpdateDate, "DD/MM/YYYY HH:mm:ss");
                                            }
                                        }, {
                                            title: "Update By",
                                            data: "UpdateUserId",
                                            codes: codes.SystemUser,
                                            width: "20%"
                                        }, {
                                            title: "Endorsement #",
                                            data: "EndoId",
                                            width: "20%"
                                        }
                                    ]
                                },
                                css: {
                                    comp: "inline-editor",
                                    cell: "title-align"
                                },
                                pos: {
                                    row: 80,
                                    width: 12
                                }
                            },
                            buttonPanel2: {
                                comp: {
                                    type: $pt.ComponentConstants.ButtonFooter,
                                    buttonLayout: {
                                        right: [
                                            {
                                                text: "Save",
                                                style: "primary",
                                                visible: $page.controller.isVisible(),
                                                click: function () {
                                                    $page.controller.save(true);
                                                }
                                            }
                                        ]
                                    }
                                },
                                pos: {
                                    row: 90,
                                    width: 12
                                }
                            }
                        }
                    }
                }
            }
        },
        createClaimConditionsLayout: function () {
            return {
                _sections: {
                    claimConditionsSection: {
                        label: "Claim Conditions",
                        style: 'primary-darken',
                        collapsible: true,
                        expanded: false,
                        layout: {
                            ClaimBasis: {
                                label: "Claim Basis",
                                comp: {
                                    type: $pt.ComponentConstants.Select,
                                    data: $page.codes.ClaimBasis
                                },
                                pos: {
                                    row: 1,
                                    width: 4
                                }
                            },
                            RetroactiveDate: {
                                label: "Retroactive Date",
                                comp: {
                                    type: $pt.ComponentConstants.Date,
                                    format: "DD/MM/YYYY"
                                },
                                pos: {
                                    row: 1,
                                    width: 4
                                }
                            },
                            SunsetCheck: {
                                label: "Sunset Clause",
                                comp: {
                                    type: {type: $pt.ComponentConstants.Check, label: false},
                                    labelAttached: true
                                },
                                pos: {
                                    row: 1,
                                    width: 1
                                }
                            },
                            SunsetClause: {
                                comp: {
                                    type: $pt.ComponentConstants.Date,
                                    format: "DD/MM/YYYY",
                                    enabled: {
                                        when: function (model) {
                                            if (model.get('SunsetCheck') != undefined && model.get('SunsetCheck') != null) {
                                                if (model.get('SunsetCheck') != true) {
                                                    model.set("SunsetClause", null);
                                                }
                                                return model.get('SunsetCheck');
                                            }
                                            return false;
                                        },
                                        depends: 'SunsetCheck'
                                    }
                                },
                                pos: {
                                    row: 1,
                                    width: 2
                                }
                            },
                            claimAdviceLimit: {
                                label: "Claim Advice Limit",
                                comp: {
                                    type: $pt.ComponentConstants.Panel,
                                    editLayout: {
                                        ClaimCurrency: {
                                            label: "Currency",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: $page.codes.Currency
                                            }
                                        },
                                        LossAdvice: {
                                            label: "Loss Advice",
                                            //comp: {
                                            //    type: $pt.ComponentConstants.Text,
                                            //    //format: $helper.formatNumber(2)
                                            //},
                                            base : $helper.baseAmount(2),
                                            css: {comp: 'currency-align-right-text'}
                                        },
                                        Posting: {
                                            label: "Posting",
                                            comp: {
                                                type: {
                                                    type: $pt.ComponentConstants.Check,
                                                    label: false
                                                },
                                                labelAttached: true
                                            }
                                        },
                                        CashCallAdvice: {
                                            label: "Cash Call Advice",
                                            //comp: {
                                            //    type: $pt.ComponentConstants.Text,
                                            //    //format: $helper.formatNumber(2)
                                            //},
                                            base : $helper.baseAmount(2),
                                            css: {comp: 'currency-align-right-text'}
                                        },
                                        NoOfDay: {
                                            label: "No. of Days",
                                            base : $helper.baseNumber(),
                                            css: {comp: 'currency-align-right-text'}
                                        }
                                    }
                                },
                                css: {comp: 'currency-align-right-text'},
                                pos: {
                                    width: 12
                                }
                            },
                            ContractClaimConditionItemList: {
                                label: "Additional Claim Limits",
                                comp: {
                                    type: $pt.ComponentConstants.Table,
                                    searchable: false,
                                    sortable: false,
                                    columns: [
                                        {
                                            title: "",
                                            data: "IsCheck",
                                            inline: "check",
                                            width: "2%",
                                            type: {type: $pt.ComponentConstants.Check, label: false},
                                            labelAttached: true
                                        },
                                        {
                                            title: "",
                                            render: function (row) {
                                                return $page.codes.ClaimLimitCate.getText(row.CateId);
                                            }, width: "48%"
                                        },
                                        {
                                            title: "Percentage",
                                            data: "Percentage",
                                            // inline: $helper.registerInlinePercentage("percentage", 2),
                                            inline: {
                                                Percentage: {
                                                    comp: {
                                                        type:{label:false},
                                                        enabled: {
                                                            when: function (model) {
                                                                if (!model.get('IsCheck')) {
                                                                    model.set("Percentage", '');
                                                                    return false;
                                                                }
                                                                return true;
                                                            },
                                                            depends: 'IsCheck'
                                                        }
                                                    },
                                                    base:$helper.basePercentage(2),
                                                    pos: {width: 12}
                                                }
                                            },
                                            width: "25%"
                                        },
                                        {
                                            title: "Amount",
                                            // inline: $helper.registerInlineAmount("amount", 2),
                                            data: "Amount",
                                            inline: "AmountForClaim"
                                        }]
                                    },
                                css: {
                                    comp: "inline-editor",
                                    cell: "title-align"
                                },
                                pos: {
                                    width: 12
                                }
                            },
                            indexProduct: {
                                label: "Index Product",
                                comp: {
                                    type: $pt.ComponentConstants.Panel,
                                    editLayout: {
                                        WetherIndexList: {
                                            label: "Weather Index",
                                            comp: {
                                                type: $pt.ComponentConstants.ArrayCheck,
                                                data: $page.codes.WeatherIndexs,
                                                labelWidth: 3
                                            },
                                            pos: {
                                                row: 1,
                                                width: 8
                                            }
                                        },
                                        PricesIndexList: {
                                            label: "Price Index",
                                            comp: {
                                                type: $pt.ComponentConstants.ArrayCheck,
                                                data: $page.codes.PriceIndexs,
                                                labelWidth: 3
                                            },
                                            pos: {
                                                row: 2,
                                                width: 8
                                            }
                                        },
                                        Specify: {
                                            label: "Specify",
                                            comp: {
                                                type: $pt.ComponentConstants.Text,
                                                visible: {
                                                    when: function (model) {
                                                        if (model.get('PricesIndexList') != undefined && model.get('PricesIndexList') != null) {
                                                            var pricesIndexList = model.get('PricesIndexList');
                                                            return pricesIndexList.indexOf('3') == -1 ? false : true;
                                                        }
                                                        return false;
                                                    },
                                                    depends: 'PricesIndexList'
                                                },
                                            },
                                            pos: {
                                                row: 2
                                            }
                                        }
                                    }
                                },
                                pos: {
                                    width: 12
                                }
                            },
                            rightButtons: {
                                comp: {
                                    type: $pt.ComponentConstants.ButtonFooter,
                                    buttonLayout: {
                                        right: [{
                                            text: "Save",
                                            style: "primary",
                                            visible: $page.controller.isVisible(),
                                            click: function () {
                                                $page.controller.save(true);
                                            }
                                        }]
                                    }
                                },
                                pos: {
                                    width: 12
                                }
                            }
                        }
                    }
                }
            }
        },
        createAccountingConditionsLayout: function () {
            return {
                _sections: {
                    accountConditionSection: {
                        label: "Accounting Conditions",
                        style: 'primary-darken',
                        collapsible: true,
                        expanded: false,
                        layout: {
                            AccountingBasis: {
                                label: "Accounting Basis",
                                comp: {
                                    type: $pt.ComponentConstants.Select,
                                    data: $page.codes.AccountingBasis
                                },
                                pos: {
                                    row: 1,
                                    width: 6
                                }
                            },
                            EarningPattern: {
                                label: "Earning Pattern",
                                comp: {
                                    type: $pt.ComponentConstants.Select,
                                    data: $page.codes.EarningPattern
                                },
                                pos: {
                                    row: 1,
                                    width: 6
                                }
                            },
                            AccountFrequency: {
                                label: "Account Frequency",
                                comp: {
                                    type: $pt.ComponentConstants.Select,
                                    data: $page.codes.AccountFreq
                                },
                                pos: {
                                    row: 2,
                                    width: 6
                                }
                            },
                            AccountDays: {
                                label: "Account Days",
                                comp: {
                                    visible: {
                                        when: function (model) {
                                            return model.get('ContractNature') != '2';
                                        },
                                        depends: 'ContractNature'
                                    }
                                },
                                base: $helper.baseNumber(),
                                css: {comp: 'currency-align-right-text'},
                                pos: {
                                    row: 3,
                                    width: 6
                                }
                            },
                            SettlementDays: {
                                label: "Settlement Days",
                                comp: {
                                    visible: {
                                        when: function (model) {
                                            return model.get('ContractNature') != '2';
                                        },
                                        depends: 'ContractNature'
                                    }
                                },
                                base: $helper.baseNumber(),
                                css: {comp: 'currency-align-right-text'},
                                pos: {
                                    row: 3,
                                    width: 6
                                }
                            },
                            PortfolioTransfer: {
                                label: "Portfolio Transfer",
                                comp: {
                                    type: $pt.ComponentConstants.Panel,
                                    visible: {
                                        when: function (model) {
                                            if (model.get('ContractNature') == '2') {
                                                return false;
                                            }
                                            return true;
                                        },
                                        depends: 'ContractNature'
                                    },
                                    editLayout: {
                                        PortfolioIn: {
                                            label: "Portfolio In",
                                            comp: {
                                                type: $pt.ComponentConstants.Label,
                                                textFromModel: false
                                            },
                                            pos: {
                                                row: 2,
                                                width: 6
                                            }
                                        },
                                        PortfolioOut: {
                                            label: "Portfolio Out",
                                            comp: {
                                                type: $pt.ComponentConstants.Label,
                                                textFromModel: false
                                            },
                                            pos: {
                                                row: 2,
                                                width: 6
                                            }
                                        },
                                        PremiumIn: {
                                            label: "Premium",
                                            //comp: {
                                            //    type: $pt.ComponentConstants.Text,
                                            //    convertor: NText.PERCENTAGE,
                                            //},
                                            base: $helper.basePercentage(2),
                                            pos: {
                                                row: 3,
                                                width: 6
                                            }
                                        },
                                        PremiumOut: {
                                            label: "Premium",
                                            //comp: {
                                            //    type: $pt.ComponentConstants.Text,
                                            //    convertor: NText.PERCENTAGE,
                                            //},
                                            base: $helper.basePercentage(2),
                                            pos: {
                                                row: 3,
                                                width: 6
                                            }
                                        },
                                        OutstandingLossesIn: {
                                            label: "Outstanding Losses",
                                            comp: {
                                                //type: $pt.ComponentConstants.Text,
                                                //convertor: NText.PERCENTAGE,
                                                visible: {
                                                    when: function (model) {
                                                        if (model.get('ContractNature') == '2') {
                                                            return false;
                                                        }
                                                        return true;
                                                    },
                                                    depends: 'ContractNature'
                                                },
                                            },
                                            base: $helper.basePercentage(2),
                                            pos: {
                                                row: 4,
                                                width: 6
                                            }
                                        },
                                        OutstandingLossesOut: {
                                            label: "Outstanding Losses",
                                            comp: {
                                                //type: $pt.ComponentConstants.Text,
                                                //convertor: NText.PERCENTAGE,
                                                visible: {
                                                    when: function (model) {
                                                        if (model.get('ContractNature') == '2') {
                                                            return false;
                                                        }
                                                        return true;
                                                    },
                                                    depends: 'ContractNature'
                                                },
                                            },
                                            base: $helper.basePercentage(2),
                                            pos: {
                                                row: 4,
                                                width: 6
                                            }
                                        }

                                    }
                                },
                                pos: {
                                    row: 5,
                                    width: 12
                                }
                            },
                            threshold: {
                                label: "Threshold for Difference(Actual vs. Accrual)",
                                comp: {
                                    type: $pt.ComponentConstants.Panel,
                                    editLayout: {
                                        PercentageOfPremium: {
                                            label: "Percentage Of Premium",
                                            //comp: {
                                            //    type: $pt.ComponentConstants.Text,
                                            //    convertor: NText.PERCENTAGE,
                                            //},
                                            base: $helper.basePercentage(2),
                                            pos: {
                                                row: 2,
                                                width: 6
                                            }
                                        },
                                        DateForReview: {
                                            label: "Date for Review",
                                            comp: {
                                                type: $pt.ComponentConstants.Date,
                                                format: "DD/MM/YYYY"
                                            },
                                            pos: {
                                                row: 2,
                                                width: 6
                                            }
                                        }
                                    }
                                },
                                pos: {
                                    width: 12
                                }
                            },
                            Remark: {
                                label: "Remarks",
                                comp: {
                                    type: $pt.ComponentConstants.Panel,
                                    visible: {
                                        when: function (model) {
                                            if (model.get('ContractNature') == '2') {
                                                return false;
                                            }
                                            return true;
                                        },
                                        depends: 'ContractNature'
                                    },
                                    editLayout: {
                                        AccountRemark: {
                                            comp: {
                                                type: {type: $pt.ComponentConstants.TextArea, label: false},
                                                lines: 3
                                            },
                                            pos: {
                                                width: 12
                                            }
                                        }
                                    }
                                },
                                pos: {
                                    width: 12
                                }
                            },
                            rightButtons: {
                                comp: {
                                    type: $pt.ComponentConstants.ButtonFooter,
                                    buttonLayout: {
                                        right: [{
                                            text: "Save",
                                            style: "primary",
                                            visible: $page.controller.isVisible(),
                                            click: function () {
                                                $page.controller.save(true);
                                            }
                                        }]
                                    }
                                },
                                pos: {
                                    width: 12
                                }
                            }
                        }
                    }
                }
            }
        },
        createCancellationLayout: function () {
            return {
                _sections: {
                    cancellationSection: {
                        layout: {
                            cancellation: {
                                comp: {
                                    type: $pt.ComponentConstants.Cancellation
                                },
                                pos: {
                                    row: 1,
                                    width: 12
                                }
                            }
                        }

                    }
                }
            }
        },
        createRemarksLayout: function () {
            return {
                _sections: {
                    remarksSection: {
                        label: "Remarks",
                        style: 'primary-darken',
                        collapsible: true,
                        layout: {
                            remarkPanel: {
                                comp: {
                                    type: $pt.ComponentConstants.Panel,
                                    editLayout: {
                                        Remark: {
                                            comp: {
                                                type: {type: $pt.ComponentConstants.TextArea, label: false},
                                                lines: 3
                                            },
                                            pos: {
                                                width: 12
                                            }
                                        }
                                    }
                                },
                                pos: {
                                    width: 12
                                }
                            }

                        }
                    }
                }
            }
        },
        createReviewed: function () {
            return {
                _sections: {
                    footerSection: {
                        layout: {
                            Reviewed: {
                                label: "Review Result",
                                comp: {
                                    type: $pt.ComponentConstants.Select,
                                    data: reviewResult,
                                    visible: {
                                        when: function (model) {
                                            return model.get('LatestStatus') == '2';
                                        },
                                        depends: 'LatestStatus'
                                    }
                                }
                            },
                            Reason: {
                                label: "Reject Reason",
                                comp: {
                                    type: $pt.ComponentConstants.Panel,
                                    visible: {
                                        when: function (model) {
                                            return model.get('Reviewed') == '0' || ((model.get('LatestStatus') == '1' || model.get('LatestStatus') == '3') && (model.get('RejectReason') != undefined || model.get('RejectReason') != null));
                                        },
                                        depends: 'Reviewed'
                                    },
                                    editLayout: {
                                        RejectReason: {
                                            comp: {
                                                type: {type: $pt.ComponentConstants.TextArea, label: false},
                                                enabled: {
                                                    when: function (model) {
                                                        return model.get('LatestStatus') == '2';
                                                    }
                                                },
                                                lines: 3
                                            },
                                            pos: {
                                                width: 12
                                            }
                                        }
                                    }
                                },
                                pos: {
                                    width: 12
                                }
                            },
                        }
                    }
                }
            }
        },
        createButtonLayout: function () {
            return {
                buttonPanel: {
                    comp: {
                        type: $pt.ComponentConstants.ButtonFooter,
                        buttonLayout: {
                            left: [
                                {
                                    text: "Retrocession",
                                    style: "primary",
                                    visible: {
                                        when: function (model) {
                                            return model.get('ContractCategory') == '2';
                                        },
                                        depends: 'ContractCategory'
                                    },
                                    click: function (model) {
                                        if (this.getModel().get("OperateType") != 0) {
                                            var isSaved = $page.controller.save();
                                            if (!isSaved) {
                                                return;
                                            }
                                        }
                                        var url = "retrocession_view.html?ContCompId=" + this.getModel().get("ContCompId")
                                            + "&OperateType=" + this.getModel().get("OperateType");
                                        if (this.getModel().get("OperateId")) {
                                            url += "&OperateId=" + this.getModel().get("OperateId");
                                        }
                                        window.location.href = url;
                                    }
                                }, {
                                    text: "Contract Summary",
                                    style: "primary",
                                    click: function () {
                                        if (this.getModel().get("OperateType") != 0) {
                                            var isSaved = $page.controller.save();
                                            if (!isSaved) {
                                                return;
                                            }
                                        }
                                        $page.controller.pdfSummary();
                                    }
                                }
                            ],
                            right: [
                                {
                                    text: "Exit",
                                    style: "warning",
                                    click: function () {
                                        var urlData = $pt.getUrlData();
                                        var exitBool = urlData.Exit;
                                        var url = "contractQuery.html";
                                        if (this.getModel().get("OperateType") == 5) {
                                            url += "?OperateType=5";
                                        }
                                        if(exitBool == 1){
                                            window.close();
                                        }else{
                                            if(this.getModel().get("OperateType") == 1 || this.getModel().get("OperateType") == 3){
                                                var resource = {
                                                    ResourceId: this.getModel().get("ContCompId"),
                                                    ResourceType: 1,
                                                    ResourceNo: this.getModel().get("ContractCode"),
                                                    OwnerId: this.getModel().get("LastChanged")
                                                };
                                                var done = function () {
                                                    window.location.href = url;
                                                };
                                                $page.controller.unlock(resource, done);
                                            }else{
                                                window.location.href = url;
                                            }
                                        }
                                    }
                                }, {
                                    text: "Document",
                                    style: "primary",
                                    click: function () {
                                        if (this.getModel().get("OperateType") != 0) {
                                            var isSaved = $page.controller.save();
                                            if (!isSaved) {
                                                return;
                                            }
                                        }
                                        var readOnly = 0;
                                        if (this.getModel().get("OperateType") == 0 || this.getModel().get("OperateType") == 5) {
                                            readOnly = 1;
                                        }
                                        $pt.viewAttachment(21, this.getModel().get("ContCompId"), readOnly);
                                    }
                                }, {
                                    text: "Submit",
                                    style: "primary",
                                    visible: $page.controller.isVisible(),
                                    click: function () {
                                        var _self = this;
                                        if (this.getModel().getCurrentModel().LatestStatus == '2') {
                                            if (this.getModel().getCurrentModel().Reviewed == undefined || this.getModel().getCurrentModel().Reviewed == null) {
                                                NConfirm.getConfirmModal().show({
                                                    title: 'Alert',
                                                    disableClose: true,
                                                    messages: ['Please select the review result.']
                                                });
                                                return;
                                            }
                                        }
                                        if (this.getModel().getCurrentModel().SectionList == undefined || this.getModel().getCurrentModel().SectionList == null) {
                                            NConfirm.getConfirmModal().show({
                                                title: 'Alert',
                                                disableClose: true,
                                                messages: ['At least one section is required on this contract.']
                                            });
                                            return;
                                        }
                                        if (!$page.controller.isPricingEstimated()) {
                                            NConfirm.getConfirmModal().show({
                                                title: 'Alert',
                                                disableClose: true,
                                                messages: ['Pricing Estimation info is required on this contract.']
                                            });
                                            return;
                                        }

                                        if(!$page.controller.AMLCheck()){
                                            return;
                                        }

                                        var ret = $page.controller.getNonRetroList();
                                        if(ret && ret.length != 0){
                                            var msg = [];
                                            msg.push('Ceded contract not found for following section(s)/subsection(s):');
                                            ret.forEach(function(item){
                                                msg.push(item);
                                            })
                                            NConfirm.getConfirmModal().show({
                                                title: 'Alert',
                                                disableClose: true,
                                                messages: msg
                                            });
                                            return;
                                        }

                                        var confirmFlag = false;
                                        NConfirm.getConfirmModal().show({
                                            title: 'Confirm',
                                            messages: ['The contract will be submitted. Are you sure?'],
                                            onConfirm: function () {
                                                confirmFlag = true;
                                            },
                                            afterClose: function(){
                                                if(confirmFlag && $page.controller.submit()){
                                                    if(_self.getModel().get("OperateType") == 1 || _self.getModel().get("OperateType") == 3){
                                                        var resource = {
                                                            ResourceId: _self.getModel().get("ContCompId"),
                                                            ResourceType: 1,
                                                            ResourceNo: _self.getModel().get("ContractCode"),
                                                            OwnerId: _self.getModel().get("LastChanged")
                                                        };
                                                        var done = function () {
                                                            window.location.href = "contractQuery.html";
                                                        };
                                                        $page.controller.unlock(resource, done);
                                                    }else{
                                                        window.location.href = "contractQuery.html";
                                                    }
                                                }
                                            }
                                        });
                                    }
                                }, {
                                    text: "Submit Ignoring Cut-off Date",
                                    style: "danger",
                                    enabled: {
                                        when: function (model) {
                                            return model.get('Reviewed') == '1';
                                        },
                                        depends: 'Reviewed'
                                    },
                                    visible: {
                                        when: function (model) {
                                            return model.get('LatestStatus') == '2' && model.get('OperateType') == '2'
                                                && model.get("IsClosingPeriod") && model.get("IsClosingPeriod") == true;
                                        },
                                        depends: 'LatestStatus'
                                    },
                                    click: function () {
                                        if (this.getModel().getCurrentModel().SectionList == undefined || this.getModel().getCurrentModel().SectionList == null) {
                                            NConfirm.getConfirmModal().show({
                                                title: 'Alert',
                                                disableClose: true,
                                                messages: ['At least one section is required on this contract.']
                                            });
                                            return;
                                        }

                                        if(!$page.controller.AMLCheck()){
                                            return;
                                        }

                                        var ret = $page.controller.getNonRetroList();
                                        if(ret && ret.length != 0){
                                            var msg = [];
                                            msg.push('Ceded contract not found for following section(s)/subsection(s):');
                                            ret.forEach(function(item){
                                                msg.push(item);
                                            })
                                            NConfirm.getConfirmModal().show({
                                                title: 'Alert',
                                                disableClose: true,
                                                messages: msg
                                            });
                                            return;
                                        }
                                        
                                        NConfirm.getConfirmModal().show({
                                            title: 'Confirm',
                                            messages: ['The contract will be submitted. Are you sure?'],
                                            onConfirm: function () {
                                                if ($page.controller.submit(true)) {
                                                    window.location.href = "contractQuery.html";
                                                }
                                            }
                                        });
                                    }
                                }, {
                                    text: "Save",
                                    style: "primary",
                                    visible: $page.controller.isVisible(),
                                    click: function () {
                                        $page.controller.save(true);
                                    }
                                }
                            ]
                        }
                    },
                    pos: {
                        width: 12
                    }
                }
            }
        },
        createLayout: function () {
            return $.extend(true, {}, this.createBaseLayout(), this.createBasicContractLayout(), this.createMoreContractLayout(),
                this.createClaimConditionsLayout(), this.createAccountingConditionsLayout(),
                this.createCancellationLayout(), this.createRemarksLayout(), this.createReviewed(), this.createButtonLayout());
        },
        createLinkStructureTabs: function (data) {
            return data.map(function (item) {
                return {
                    label: item.uwYear,
                    editLayout: {
                        linkName: {
                            label: "Link Name",
                            comp: {
                                type: $pt.ComponentConstants.Text,
                                enabled: false
                            },
                            view: 'view',
                            pos: {
                                width: 6
                            }
                        },
                        structureTree: {
                            comp: {
                                type: $pt.ComponentConstants.Tree,
                                data: $pt.createCodeTable(item.structureVO),
                                root: "Contracts",
                                expandLevel: 'all',
                                nodeOperations: [{
                                    icon: "eye",
                                    text: 'view',
                                    click: function (node) {
                                        var url = $pt.getURL("ui.contract.contractView") + "&ContCompId=" + node.id;
                                        window.open(url);
                                    },
                                    visible: function (node) {
                                        return node.level == '1'
                                    }
                                }]
                            },
                            pos: {
                                width: 12
                            }
                        }
                    }
                }
            });
        }
    });

    $page.layout = new layout();


}(typeof window !== "undefined" ? window : this));
