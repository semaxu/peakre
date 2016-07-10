(function (context) {
    var $page = $pt.getService(context, '$page');
    var codes = $pt.getService($page, 'codes');
    //var uWResponsible = $pt.createCodeTable([
    //    {id: '1', text: 'Gina Gao'},
    //    {id: '2', text: 'Yan Tang'},
    //    {id: '3', text: 'Jessie Lin'},
    //    {id: '4', text: 'Lynn Chen'}
    //]);
    //var analyticResponsible = $pt.createCodeTable([
    //    {id: '1', text: 'Celine Gu'},
    //    {id: '2', text: 'Cherry Huang'},
    //    {id: '3', text: 'Suri Tong'},
    //    {id: '4', text: 'Alice Liu'}
    //]);
    //var marketResponsible = $pt.createCodeTable([
    //    {id: '1', text: 'Helen Qian'},
    //    {id: '2', text: 'Mike Chen'},
    //    {id: '3', text: 'Allen Xiao'},
    //    {id: '4', text: 'Abbey Ma'}
    //]);
    //var treatyOwner = $pt.createCodeTable([
    //    {id: '1', text: 'Louis Tian'},
    //    {id: '2', text: 'Joe Sun'},
    //    {id: '3', text: 'Justin Ding'},
    //    {id: '4', text: 'Daisy Dai'}
    //]);
    var Layout = jsface.Class(
        {
            DividerBase: function () {
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
            baseLayout: function () {
                var contractLayout = {
                    label: "Contract Information",
                    icon: "bookmark",
                    value: "1",
                    editLayout: {
                        basicContractInfoPanel: {
                            label: "Basic Contract Info",
                            comp: {
                                type: $pt.ComponentConstants.Panel,
                                style: 'primary-darken',
                                collapsible: true,
                                expanded: true,
                                editLayout: {
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
                                                        row: 10,
                                                        col: 1,
                                                        width: 4,
                                                        section: "subPanel01"
                                                    }
                                                },
                                                ContractName: {
                                                    label: "Contract Name",
                                                    comp: {
                                                        type: $pt.ComponentConstants.Text
                                                    },
                                                    pos: {
                                                        row: 10,
                                                        col: 1,
                                                        width: 4,
                                                        section: "subPanel01"
                                                    }
                                                },
                                                Mainclass: {
                                                    label: "Main CoB",
                                                    comp: {
                                                        type: $pt.ComponentConstants.Select,
                                                        data: $page.codes.Class
                                                    },
                                                    pos: {
                                                        row: 10,
                                                        col: 1,
                                                        width: 4,
                                                        section: "subPanel01"
                                                    }
                                                },
                                                BrokerRef: {
                                                    label: "Broker Reference",
                                                    comp: {
                                                        type: $pt.ComponentConstants.Text
                                                    },
                                                    pos: {
                                                        row: 10,
                                                        col: 1,
                                                        width: 4,
                                                        section: "subPanel01"
                                                    }
                                                },
                                                CedentRef: {
                                                    label: "Cedent Reference",
                                                    comp: {
                                                        type: $pt.ComponentConstants.Text
                                                    },
                                                    pos: {
                                                        row: 10,
                                                        col: 1,
                                                        width: 4,
                                                        section: "subPanel01"
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
                                                        row: 10,
                                                        col: 1,
                                                        width: 4,
                                                        section: "subPanel01"
                                                    }
                                                },
                                                AppianNo: {
                                                    label: "Appian No.",
                                                    comp: {
                                                        type: $pt.ComponentConstants.Text
                                                    },
                                                    pos: {
                                                        row: 10,
                                                        col: 1,
                                                        width: 4,
                                                        section: "subPanel01"
                                                    }
                                                },
                                                PricingRef: {
                                                    label: "Pricing Reference",
                                                    comp: {
                                                        type: $pt.ComponentConstants.Text
                                                    },
                                                    pos: {
                                                        row: 10,
                                                        col: 1,
                                                        width: 4,
                                                        section: "subPanel01"
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
                                        base: this.DividerBase(),
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
                                                    }
                                                },
                                                ContractType: {
                                                    label: "Contract Type",
                                                    comp: {
                                                        type: $pt.ComponentConstants.Select,
                                                        data: $page.codes.ContractType
                                                    }
                                                },
                                                ContractNature: {
                                                    label: "Contract Nature",
                                                    comp: {
                                                        type: $pt.ComponentConstants.Select,
                                                        data: $page.codes.ContractNature
                                                    },
                                                    enabled: {
                                                        when: function () {
                                                            if (this.model.get('ContCompId') != undefined && this.model.get('ContCompId') != null) {
                                                                return false;
                                                            }
                                                            return true;
                                                        }
                                                    }
                                                },
                                                ContractCategory: {
                                                    label: "Contract Category",
                                                    comp: {
                                                        type: $pt.ComponentConstants.Select,
                                                        data: $page.codes.ContractCategory
                                                    }
                                                },
                                                Fronting: {
                                                    label: "Fronting",
                                                    comp: {
                                                        type: $pt.ComponentConstants.Select,
                                                        data: $page.codes.Boolean
                                                    }
                                                },
                                                DepositAccounting: {
                                                    label: "Deposit Accounting",
                                                    comp: {
                                                        type: $pt.ComponentConstants.Select,
                                                        data: $page.codes.Boolean
                                                    }
                                                },
                                                ProtectionBasic: {
                                                    label: "Protection Basis",
                                                    comp: {
                                                        type: $pt.ComponentConstants.Select,
                                                        data: $page.codes.ProtectionBasis
                                                    }
                                                },
                                                MainCurrency: {
                                                    label: "Main Currency",
                                                    comp: {
                                                        type: $pt.ComponentConstants.Select,
                                                        data: $page.codes.Currency
                                                    }
                                                },
                                                PortfolioTransfer: {
                                                    label: "Portfolio Transfer",
                                                    comp: {
                                                        type: $pt.ComponentConstants.Select,
                                                        data:$page.codes.TerminationCondition
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
                                        base: this.DividerBase(),
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
                                                        format: "DD/MM/YYYY 00:00:00"
                                                    }
                                                },
                                                UwYear: {
                                                    label: "UW Year",
                                                    comp: {
                                                        type: $pt.ComponentConstants.Text
                                                    },
                                                    css: {comp: 'currency-align-right-text'}
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
                                                        format: "DD/MM/YYYY 23:59:59"
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
                                        base: this.DividerBase(),
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
                                                    base: $helper.basePartnerSearch(),

                                                },
                                                Broker: {
                                                    label: "Broker",
                                                    base: $helper.basePartnerSearch()
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
                                                        data: $page.codes.CedentCountry
                                                    }
                                                },

                                                CoBroker: {
                                                    label: "Co-Broker",
                                                    base: $helper.basePartnerSearch()
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
                                                condition_MainCoverArea: {
                                                    label: "Main Cover Area",
                                                    comp: {
                                                        type: $pt.ComponentConstants.Select,
                                                        // type: $pt.ComponentConstants.SelectTree,
                                                        data:$page.model.CedentCountry
                                                    }
                                                },
                                                Mga: {
                                                    label: "MGA",
                                                    base: $helper.basePartnerSearch()
                                                },
                                                Insured: {
                                                    label: "Insured",
                                                    base: $helper.basePartnerSearch()
                                                }
                                            }
                                        },
                                        pos: {
                                            row: 40,
                                            width: 12
                                        }
                                    },
                                    divider4: {
                                        base: this.DividerBase(),
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
                                                        enabled: false
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
                                                        data: codes.SystemUser
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
                                                        data: codes.UwCompany
                                                    }
                                                }
                                            }
                                        },
                                        pos: {
                                            row: 50,
                                            width: 12
                                        }
                                    }
                                }
                            },
                            pos: {
                                width: 12
                            }
                        },
                        moreContractInfoPanel: {
                            label: "More Contract Info",
                            comp: {
                                type: $pt.ComponentConstants.Panel,
                                style: 'primary-darken',
                                collapsible: true,
                                expanded: false,
                                editLayout: {
                                    SectionList: {
                                        label: "Sections",
                                        comp: {
                                            type: $pt.ComponentConstants.Table,
                                            searchable: false,
                                            addable: true,
                                            removable: true,
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
                                                        if(!value){
                                                            value = "";
                                                        }
                                                        value = isNaN(value) || (value + '').isBlank() ? value : (value + '').movePointRight(2)+ "%";
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
                                                    width: "15%",
                                                    render : function(row){
                                                        var value = row.Deductions;
                                                        if(!value){
                                                            value = "";
                                                        }
                                                        value = isNaN(value) || (value + '').isBlank() ? value : (value + '').movePointRight(2)+ "%";
                                                        return value
                                                    }
                                                }
                                            ],
                                            rowOperations: [
                                                {
                                                    tooltip: "view",
                                                    click: function (row) {
                                                        //var isSaved = $page.controller.saveContract();
                                                        //if (!isSaved) {
                                                        //    return;
                                                        //}
                                                        // var url = "../../Treaty/";
                                                        var  url = $pt.getURL("ui.contract.contractHome");
                                                        url = url + "?ContractNature=" + this.getModel().get("ContractNature")
                                                            + "&OperateType=0" + "&ContCompId=" + row.ContCompId+"&Exit=1";
                                                        //console.info(url);
                                                        window.open(url);
                                                    }
                                                }
                                            ]
                                        },
                                        css:{
                                            comp: "inline-editor",
                                            cell: "title-align"
                                        },
                                        pos: {
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
                                                    click: function (row) {
                                                        var  url = $pt.getURL("ui.contract.statusLogView");
                                                        url = url + "?ContCompId=" + row.ContCompId
                                                            + "&OperateId=" + row.OperateId
                                                            + "&OperateType=0" + "&Exit=1";
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
                                        css:{
                                            comp: "inline-editor",
                                            cell: "title-align"
                                        },
                                        pos: {
                                            width: 12
                                        }
                                    },
                                }
                            },
                            pos: {
                                width: 12
                            }
                        },
                        claimConditionsPanel: {
                            label: "Claim Conditions",
                            comp: {
                                type: $pt.ComponentConstants.Panel,
                                style: 'primary-darken',
                                collapsible: true,
                                expanded: false,
                                editLayout: {
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
                                                    comp: {
                                                        type: $pt.ComponentConstants.Text,
                                                        //format: $helper.formatNumber(2)
                                                    },
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
                                                    comp: {
                                                        type: $pt.ComponentConstants.Text,
                                                        //format: $helper.formatNumber(2)
                                                    },
                                                    base : $helper.baseAmount(2),
                                                    css: {comp: 'currency-align-right-text'}
                                                },
                                                NoOfDay: {
                                                    label: "No. of Days"
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
                                                {   title: "Percentage",
                                                    data: "Percentage",
                                                    inline: $helper.registerInlinePercentage("percentage",2),
                                                    width: "25%"},
                                                {   title: "Amount",
                                                    inline: $helper.registerInlineAmount("amount",2),
                                                    data: "Amount",
                                                    width: "25%"}
                                            ]
                                        },
                                        css:{
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
                                    }
                                }
                            },
                            pos: {
                                width: 12
                            }
                        },
                        accountingConditionsPanel: {
                            label: "Accounting Conditions",
                            comp: {
                                type: $pt.ComponentConstants.Panel,
                                style: 'primary-darken',
                                collapsible: true,
                                expanded: false,
                                editLayout: {
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
                                    //FirstAccountAsAtDate: {
                                    //    label: "First Account as at",
                                    //    comp: {
                                    //        type: $pt.ComponentConstants.Date,
                                    //        format: "DD/MM/YYYY"
                                    //    },
                                    //    pos: {
                                    //        row: 2,
                                    //        width: 6
                                    //    }
                                    //},
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
                                        css: {comp: 'currency-align-right-text'},
                                        pos: {
                                            row: 3,
                                            width: 6
                                        }
                                    },
                                    //DueDate: {
                                    //    label: "Due Date",
                                    //    comp: {
                                    //        type: $pt.ComponentConstants.Date,
                                    //        format: "DD/MM/YYYY"
                                    //    },
                                    //    pos: {
                                    //        row: 4,
                                    //        width: 6
                                    //    }
                                    //},
                                    PortfolioTransfer:{
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
                                                    comp:{
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
                                                    comp:{
                                                        type: $pt.ComponentConstants.Label,
                                                        textFromModel: false
                                                    },
                                                    pos: {
                                                        row:2,
                                                        width: 6
                                                    }
                                                },
                                                PremiumIn:{
                                                    label: "Premium",
                                                    /*comp: {
                                                        type: $pt.ComponentConstants.Text,
                                                        convertor: NText.PERCENTAGE,
                                                        rightAddon: {
                                                            text: "%"
                                                        }
                                                    },*/
                                                    base: $helper.basePercentage(2),
                                                    pos: {
                                                        row: 3,
                                                        width: 6
                                                    }
                                                },
                                                PremiumOut:{ label: "Premium",
                                                    /*comp: {
                                                        type: $pt.ComponentConstants.Text,
                                                        convertor: NText.PERCENTAGE,
                                                        rightAddon: {
                                                            text: "%"
                                                        }
                                                    },*/
                                                    base: $helper.basePercentage(2),
                                                    pos: {
                                                        row: 3,
                                                        width: 6
                                                    }
                                                },
                                                OutstandingLossesIn:{
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
                                                        }
                                                    },
                                                    base: $helper.basePercentage(2),
                                                    pos: {
                                                        row: 4,
                                                        width: 6
                                                    }
                                                },
                                                OutstandingLossesOut:{
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
                                                        }
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
                                                    /*comp: {
                                                        type: $pt.ComponentConstants.Text,
                                                        convertor: NText.PERCENTAGE,
                                                        rightAddon: {
                                                            text: "%"
                                                        }
                                                    },*/
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
                                    }
                                }
                            },
                            pos: {
                                width: 12
                            }
                        },
                        cancellationPanel: {
                            //label: "Cancellation",
                            comp: {
                                type: $pt.ComponentConstants.Panel,
                                style: 'primary-darken',
                                collapsible: true,
                                expanded: false,
                                editLayout: {
                                    cancellation: {
                                        comp: {
                                            type: $pt.ComponentConstants.Cancellation
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
                        remarksPanel: {
                            label: "Remarks",
                            comp: {
                                type: $pt.ComponentConstants.Panel,
                                style: 'primary-darken',
                                collapsible: true,
                                expanded: true,
                                editLayout: {
                                    remarkPanel: {
                                        comp: {
                                            type: $pt.ComponentConstants.Panel,
                                            editLayout: {
                                                Remark: {
                                                    comp: {
                                                        type: {
                                                            type: $pt.ComponentConstants.TextArea,
                                                            label: false
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
                                    }
                                }
                            },
                            pos: {
                                width: 12
                            }
                        }
                    }
                };
                return {
                    _sections: {
                        claimSec: {
                            layout: {
                                formTab: {
                                    comp: {
                                        type: $pt.ComponentConstants.Tab,
                                        justified: false,
                                        onActive: function (currentTabValue, currentTableIndex) {
                                            $page.controller.loadInfo(currentTabValue);
                                        },
                                        //canRemove:true,
                                        tabs: [
                                            contractLayout,
                                            {
                                                label: "Endorsement",
                                                icon: "bookmark",
                                                value: "2",
                                                editLayout: {
                                                    EndorsementList: {
                                                        comp: {
                                                            type: $pt.ComponentConstants.Table,
                                                            searchable: false,
                                                            sortable: false,
                                                            scrollX: true,
                                                            columns: [
                                                                {
                                                                    title: "Number",
                                                                    data: "EndoId",
                                                                    width: "80"
                                                                }
                                                                , {
                                                                    title: "Client No.",
                                                                    data: "ClientEndoNo",
                                                                    width: "100"
                                                                }, {
                                                                    title: "Type",
                                                                    data: "EndoType",
                                                                    codes: $page.codes.EndorsementType,
                                                                    width: "100"
                                                                }, {
                                                                    title: "Condition",
                                                                    data: "ChangeCondition",
                                                                    width: "150"
                                                                }, {
                                                                    title: "Description",
                                                                    data: "Description",
                                                                    width: "150"
                                                                }, {
                                                                    title: "Applicable to",
                                                                    data: "ApplicableTo",
                                                                    width: "150"
                                                                }, {
                                                                    title: "Effectvie Date",
                                                                    data: "EffDate",
                                                                    width: "100",
                                                                    render: function (row) {
                                                                        return $helper.formatDateForTableView(row.EffDate, "DD/MM/YYYY");
                                                                    }
                                                                }, {
                                                                    title: "Agreed Date",
                                                                    data: "AgreedDate",
                                                                    width: "100",
                                                                    render: function (row) {
                                                                        return $helper.formatDateForTableView(row.AgreedDate, "DD/MM/YYYY");
                                                                    }
                                                                }, {
                                                                    title: "User Name",
                                                                    data: "InsertBy",
                                                                    codes:codes.SystemUser,
                                                                    width: "120"
                                                                }, {
                                                                    title: "Date",
                                                                    data: "InsertTime",
                                                                    width: "100",
                                                                    render: function (row) {
                                                                        return $helper.formatDateForTableView(row.InsertTime, "DD/MM/YYYY HH:mm:ss");
                                                                    }
                                                                }, {
                                                                    title: "Approved By",
                                                                    data: "UpdateBy",
                                                                    codes:codes.SystemUser,
                                                                    width: "120"
                                                                }, {
                                                                    title: "Approved Date",
                                                                    data: "UpdateTime",
                                                                    width: "120",
                                                                    render: function (row) {
                                                                        return $helper.formatDateForTableView(row.UpdateTime, "DD/MM/YYYY HH:mm:ss");
                                                                    }
                                                                }
                                                            ]
                                                        },
                                                        pos: {
                                                            width: 12
                                                        }
                                                    }
                                                }
                                            },
                                            {
                                                label: "Claim",
                                                icon: "bookmark",
                                                value: "3",
                                                editLayout: {
                                                    claimTable: {
                                                        label: "",
                                                        comp: {
                                                            type: $pt.ComponentConstants.Table,
                                                            //indexable : true,
                                                            sortable: false,
                                                            //pageable:true,
                                                            //countPerPage : 5,
                                                            searchable: false,
                                                            removable: false,
                                                            addable: false,
                                                            //criteria : "paginationCriteria",
                                                            columns: [
                                                                {title: "Claim No.", data: "ClaimNo"},
                                                                {title: "Section/ Sub-section", data: "SectionName"},
                                                                {title: "Loss Start Date", data: "LossStartDate" ,render: function (row) {
                                                                    return $helper.formatDateForTableView(row.LossStartDate, "DD/MM/YYYY");
                                                                }
                                                                },
                                                                {title: "Cause of Loss", data: "CaseOfLoss"},
                                                                {title: "Loss Reserve($)", data: "LossReverse"},
                                                                {title: "Loss Paid($)", data: "LossPaid"},
                                                                {title: "Claim Status", data: "Status",codes:$page.codes.ClaimStatus},
                                                                //{title: "Claim status", data: "claimStatus"}
                                                            ],
                                                            rowOperations: [
                                                                {
                                                                    tooltip: "View",

                                                                    click: function (rowModel) {
                                                                        $page.controller.loadClaim(rowModel.ClaimId, 2);
                                                                    }
                                                                }
                                                            ]
                                                        },
                                                        pos: {
                                                            width: 12
                                                        }
                                                    }
                                                }
                                            },
                                            {
                                                label: "Statement",
                                                icon: "bookmark",
                                                value: "4",
                                                editLayout: {
                                                    statementTable: {
                                                        label: "",
                                                        comp: {
                                                            type: $pt.ComponentConstants.Table,
                                                            sortable: false,
                                                            searchable: false,
                                                            removable: false,
                                                            addable: false,
                                                            columns: [
                                                                {
                                                                    title: "Statement ID",
                                                                    data: "SoaId",
                                                                    width: "8%"
                                                                }, {
                                                                    title: "Status",
                                                                    data: "SoaStatus",
                                                                    codes: $page.codes.SoaStatus,
                                                                    width: "8%"
                                                                }, {
                                                                    title: "UW Year",
                                                                    data: "UwYear",
                                                                    width: "8%"
                                                                }, {
                                                                    title: "Cedent",
                                                                    data: "Cedent",
                                                                    //codes: $page.codes.SystemUser,
                                                                    //codes: codes.SystemUser,
                                                                    width: "8%"
                                                                }, {
                                                                    title: "Contract ID",
                                                                    data: "ContractCode",
                                                                    width: "8%"
                                                                }, {
                                                                    title: "Cedent Year",
                                                                    data: "CedentYear",
                                                                    width: "8%"
                                                                }, {
                                                                    title: "Cedent Quarter",
                                                                    data: "CedentQuarter",
                                                                    codes: $page.codes.CedentQuarter,
                                                                    width: "8%"
                                                                }, {
                                                                    title: "Financial Year",
                                                                    data: "FinancialYear",
                                                                    codes: $page.codes.FNYear,
                                                                    width: "8%"
                                                                }, {
                                                                    title: "Financial Quarter",
                                                                    data: "FinancialQuarter",
                                                                    codes: $page.codes.FNQuarter,
                                                                    width: "8%"
                                                                }, {
                                                                    title: "Statement Text",
                                                                    data: "SoaText",
                                                                    width: "8%"
                                                                }
                                                            ],
                                                            rowOperations: [
                                                                {
                                                                    tooltip: "View",

                                                                    click: function (rowModel) {
                                                                        $page.controller.doView(rowModel);
                                                                    }

                                                                }
                                                            ]
                                                        },
                                                        pos: {
                                                            width: 12
                                                        }
                                                    }
                                                }
                                            },
                                            {
                                                label: "Cedent Quarter View",
                                                icon: "bookmark",
                                                value: "5",
                                                editLayout: {
                                                    contractLevel: {
                                                        label: "Contract Level",
                                                        comp: {
                                                            type: $pt.ComponentConstants.Panel,
                                                            editLayout: {
                                                                programLevel: {
                                                                    comp: {
                                                                        type: $pt.ComponentConstants.Tree,
                                                                        root: false,
                                                                        data: $pt.createCodeTable($page.controller.model.get("contractCodes")),
                                                                        nodeClick: function (node) {
                                                                            $page.controller.toForecastAndEstimation(1, node.id);
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        },
                                                        pos: {
                                                            width: 12
                                                        }
                                                    },
                                                    sectionLevel: {
                                                        label: "Contract Section Level",
                                                        comp: {
                                                            type: $pt.ComponentConstants.Panel,
                                                            editLayout: {
                                                                sectionLevel: {
                                                                    comp: {
                                                                        type: $pt.ComponentConstants.Tree,
                                                                        root: false,
                                                                        data: $pt.createCodeTable($page.controller.model.get("sectionCodes")),
                                                                        nodeClick: function (node) {
                                                                            console.log(node.id);
                                                                            $page.controller.toForecastAndEstimation(2, node.id);
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        },
                                                        pos: {
                                                            width: 12
                                                        }
                                                    }
                                                }
                                            },
                                            /*{
                                                label: "Accounting FN view",
                                                icon: "bookmark",
                                                value: "6",
                                                editLayout: {
                                                    contractPanel: {
                                                        label: "Contract Level",
                                                        comp: {
                                                            type: $pt.ComponentConstants.Panel,
                                                            collapsible: true,
                                                            editLayout: {
                                                                contractTable: this.createTableLayout($page.controller.model.get("tableColumns"), 1)
                                                            }
                                                        },
                                                        pos: {
                                                            width: 12
                                                        }
                                                    },
                                                    sectionTables: {
                                                        label: 'Section Panel',
                                                        comp: {
                                                            type: $pt.ComponentConstants.ArrayPanel,
                                                            itemTitle: {
                                                                when: function (item) {
                                                                    console.log(item.get("sectionId"));
                                                                    return item.get("sectionName");
                                                                }
                                                            },
                                                            expanded: false,
                                                            collapsible: true,
                                                            editLayout: {
                                                                sectionTable: this.createTableLayout($page.controller.model.get("tableColumns"), 2)
                                                            }
                                                        },
                                                        pos: {
                                                            width: 12
                                                        }
                                                    }
                                                }
                                            },*/
                                            {
                                                label: "Credit note/Debit note",
                                                icon: "bookmark",
                                                value: "7",
                                                editLayout: {
                                                    EntryItems: {
                                                        comp: {
                                                            type: $pt.ComponentConstants.ARAPCreditDebitTable,
                                                            showChecked: true
                                                        },
                                                        pos: {
                                                            row: 3,
                                                            width: 12
                                                        }
                                                    }
                                                }
                                            }
                                        ]
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
            buttonPanel: function () {
                return {
                    _sections: {
                        buttonPanel: {
                            layout: {
                                buttons: {
                                    comp: {
                                        type: $pt.ComponentConstants.ButtonFooter,
                                        buttonLayout: {
                                            right: [
                                                {
                                                    text: "Exit",
                                                    style: "warning",
                                                    click: function () {
                                                        window.location.href = "generalQuery.html";
                                                    }
                                                },
                                                {
                                                    text: "Document",
                                                    style: "primary",
                                                    click: function () {
                                                        var readOnly = 0;
                                                        if (this.getModel().get("OperateType") == 0 || this.getModel().get("OperateType") == 5) {
                                                            readOnly = 1;
                                                        }
                                                        $pt.viewAttachment(21, this.getModel().get("ContCompId"), readOnly);
                                                    },
                                                    visible: {
                                                        when: function () {
                                                            return $page.buttonVisible;
                                                        }
                                                    }
                                                }
                                            ],
                                            left: [
                                                {
                                                    text: "Contract Summary",
                                                    style: "primary",
                                                    click: function () {
                                                        $page.controller.pdfSummary();
                                                    },
                                                    visible: {
                                                        when: function () {
                                                            return $page.buttonVisible;
                                                        }
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
                        }
                    }
                }
            },
            createTableColumns: function (tableColumn, level) {
                console.log("=======");
                console.log(tableColumn);
                var columns = [
                    //{
                    //    title: "Entry Code",
                    //    data: "item",
                    //    width: "10%"
                    //},
                    {
                        title: "Entry Item",
                        data: "item",
                        codes: $page.codes.entryCode,
                        width: 100
                    }
                ];
                console.log(tableColumn);
                tableColumn.forEach(function (tableColumn) {
                    console.log(tableColumn);
                    columns.push({
                        title: tableColumn.text+"(USD)",
                        data: tableColumn.value,
                        width: 100,
                        inline: {
                            label: {
                                dataId: tableColumn.value,
                                comp: {
                                    type: {
                                        label: false,
                                        popover: false,
                                        render: function (model, layout, direction, view) {
                                            var click = function () {
                                                $page.controller.showTransactionDetail(model.get("item"), tableColumn.text);
                                            };
                                            if (level == '1') {
                                                return $helper.formatNumberForLabel(model.get(tableColumn.value), 2);
                                            } else {
                                                return <a href='javascript:void(0);'
                                                          onClick={click}>{$helper.formatNumberForLabel(model.get(tableColumn.value), 2)}</a>;
                                            }
                                        }
                                    }
                                },
                                pos: {width: 12},
                                css: {cell: 'currency-align-right'}
                            }
                        }
                    });
                });
                columns.push({
                    title: "Total",
                    data: "total",
                    width: 100,
                    inline: {
                        label: {
                            comp: {
                                type: {
                                    label: false,
                                    popover: false,
                                    render: function (model, layout, direction, view) {
                                        return $helper.formatNumberForLabel(model.get("total"), 2);
                                    }
                                }
                            },
                            pos: {width: 12},
                            css: {cell: 'currency-align-right'}
                        }
                    }
                });
                return columns;
            },
            createTableLayout: function (columnModel, level) {
                console.log("LZC; createTableLayout");
                console.log(columnModel);
                return {
                    comp: {
                        type: $pt.ComponentConstants.Table,
                        searchable: false,
                        sortable: false,
                        header: false,
                        columns: this.createTableColumns(columnModel, level)
                    },
                    css: {
                        comp: "inline-editor",
                        cell: 'title-align'
                    },
                    pos: {
                        width: 12
                    }
                }
            },
            createQueryViewLayout: function () {
                return $.extend(true, {}, this.baseLayout(), this.buttonPanel())
            }
        }
    );

    $page.layout = new Layout();
}(typeof window !== "undefined" ? window : this));
