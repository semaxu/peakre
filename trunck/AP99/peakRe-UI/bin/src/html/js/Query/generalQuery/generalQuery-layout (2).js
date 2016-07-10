(function (context) {
    var $page = $pt.getService(context, '$page');
    var codes = $pt.getService($page, 'codes');

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
                    conditionSection: {
                        label: "Search Condition",
                        style: 'primary-darken',
                        layout: {
                            subPanel01: {
                                comp: {
                                    type: $pt.ComponentConstants.Panel,
                                    editLayout: {
                                        condition_ContractCode: {
                                            label: "Contract ID",
                                            comp: {
                                                type: $pt.ComponentConstants.Text
                                            }
                                        },

                                        condition_ContractName: {
                                            label: "Contract Name",
                                            comp: {
                                                type: $pt.ComponentConstants.Text
                                            }
                                        },
                                        condition_Mainclass: {
                                            label: "Main CoB",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: $page.codes.Class
                                            }
                                        },
                                        condition_BrokerRef: {
                                            label: "Broker Reference",
                                            comp: {
                                                type: $pt.ComponentConstants.Text
                                            }
                                        },
                                        condition_CedentRef: {
                                            label: "Cedent Reference",
                                            comp: {
                                                type: $pt.ComponentConstants.Text
                                            }
                                        },
                                        condition_Subclass: {
                                            label: "Main Sub CoB",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: $page.codes.SubClass,
                                                parentPropId: "condition_Mainclass",
                                                parentFilter: "parentId"
                                            }
                                        },
                                        condition_AppianNo: {
                                            label: "Appian No.",
                                            comp: {
                                                type: $pt.ComponentConstants.Text
                                            }
                                        },
                                        condition_PricingRef: {
                                            label: "Pricing Reference",
                                            comp: {
                                                type: $pt.ComponentConstants.Text
                                            }
                                        }
                                    }
                                },
                                pos: {
                                    row: 1,
                                    width: 12
                                }
                            },
                            divider1: {
                                base: this.createDividerBase(),
                                pos: {
                                    row: 3,
                                    width: 12
                                }
                            },
                            subPanel02: {
                                comp: {
                                    type: $pt.ComponentConstants.Panel,
                                    editLayout: {
                                        condition_LatestStatus: {
                                            label: "Contract Status",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: $page.codes.LatestStatus
                                            }
                                        },

                                        condition_ContractType: {
                                            label: "Contract Type",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: $page.codes.ContractType
                                            }
                                        },
                                        condition_ContractNature: {
                                            label: "Contract Nature",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: $page.codes.ContractNature
                                            }
                                        },
                                        condition_ContractCategory: {
                                            label: "Contract Category",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: $page.codes.ContractCategory
                                            }
                                        },
                                        condition_Fronting: {
                                            label: "Fronting",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: $page.codes.Boolean
                                            }
                                        },
                                        condition_DepositAccounting: {
                                            label: "Deposit Accounting",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: $page.codes.Boolean
                                            }
                                        },
                                        condition_ProtectionBasic: {
                                            label: "Protection Basis",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: $page.codes.ProtectionBasis
                                            }
                                        },
                                        condition_MainCurrency: {
                                            label: "Main Currency",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: $page.codes.Currency
                                            }
                                        },
                                        condition_PortfolioTransfer: {
                                            label: "Portfolio Transfer",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data:$page.codes.TerminationCondition
                                            }
                                        }
                                    }
                                },
                                pos: {
                                    width: 12,
                                    row: 4
                                }
                            },
                            divider2: {
                                base: this.createDividerBase(),
                                pos: {
                                    row: 6,
                                    width: 12
                                }
                            },
                            subPanel03: {
                                comp: {
                                    type: $pt.ComponentConstants.Panel,
                                    editLayout: {

                                        condition_ReinsStarting: {
                                            label: "Period Start",
                                            comp: {
                                                type: $pt.ComponentConstants.Date,

                                                format: "DD/MM/YYYY"
                                                // valueFormat: "DD/MM/YYYY 00:00:00"
                                            }
                                        },

                                        condition_UwYear: {
                                            label: "UW Year",
                                            comp: {
                                                type: $pt.ComponentConstants.Text
                                            },
                                            css: {comp: 'currency-align-right-text'}
                                        },

                                        condition_InforceDate: {
                                            label: "In Force Date",
                                            comp: {
                                                type: $pt.ComponentConstants.Date,
                                                format: "DD/MM/YYYY"
                                            }
                                        },

                                        condition_ReinsEnding: {
                                            label: "Period End",
                                            comp: {
                                                type: $pt.ComponentConstants.Date,

                                                format: "DD/MM/YYYY"
                                                // valueFormat: "DD/MM/YYYY 23:59:59"
                                            }
                                        }
                                    }
                                },
                                pos: {
                                    row: 7,
                                    width: 12
                                }
                            },
                            divider3: {
                                base: this.createDividerBase(),
                                pos: {
                                    row: 9,
                                    width: 12
                                }
                            },
                            subPanel04: {
                                comp: {
                                    type: $pt.ComponentConstants.Panel,
                                    editLayout: {
                                        condition_Cedent: {
                                            label: "Cedent",
                                            comp : {
                                                enabled :{
                                                    when: function (model) {
                                                        return model.get('OperateType') != '3';
                                                    },
                                                    depends: 'OperateType'
                                                }
                                            },
                                            base: $helper.basePartnerSearch(1)
                                        },
                                        condition_Broker: {
                                            label: "Broker",
                                            base: $helper.basePartnerSearch(2)
                                        },
                                        condition_CedentCountry: {
                                            label: "Cedent Country",
                                            comp: {

                                                type: $pt.ComponentConstants.Select,
                                            }
                                        },
                                        condition_Mga: {
                                            label: "MGA",
                                            base: $helper.basePartnerSearch(5)
                                        },

                                        condition_CoBroker: {
                                            label: "Co-Broker",
                                            base: $helper.basePartnerSearch(2)
                                        },

                                        condition_Insured: {
                                            label: "Insured",
                                            base: $helper.basePartnerSearch(3)
                                        },
                                        condition_Leader: {
                                            label: "Leader",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: $page.codes.Boolean
                                            }
                                        },
                                        condition_LeaderName: {
                                            label: "Leader Name",
                                            comp: {
                                                type: $pt.ComponentConstants.Text,
                                            }
                                        }
                                    }
                                },
                                pos: {
                                    row: 10,
                                    width: 12
                                }
                            },
                            divider4: {
                                base: this.createDividerBase(),
                                pos: {
                                    row: 12,
                                    width: 12
                                }
                            },
                            subPanel05: {
                                comp: {
                                    type: $pt.ComponentConstants.Panel,
                                    editLayout: {
                                        condition_Underwriting: {
                                            label: "UW Responsible",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: codes.SystemUser
                                            }
                                        },
                                        condition_AnalyticsResp: {
                                            label: "Analytic Responsible",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: codes.SystemUser
                                            }
                                        },
                                        condition_MarketResp: {
                                            label: "Market Responsible",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: codes.SystemUser
                                            }
                                        },
                                        condition_CreatedBy: {
                                            label: "Created By",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: codes.SystemUser
                                            }
                                        },
                                        condition_CreatedOn: {
                                            label: "Created On",
                                            comp: {
                                                type: $pt.ComponentConstants.Date,
                                                format: "DD/MM/YYYY"
                                            }
                                        },
                                        condition_TreatyOwner: {
                                            label: "Treaty Owner",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: codes.SystemUser
                                            }
                                        },
                                        condition_LastChanged: {
                                            label: "Last Changed By",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: codes.SystemUser
                                            }
                                        },
                                        condition_LastChangedOn: {
                                            label: "Changed On",
                                            comp: {
                                                type: $pt.ComponentConstants.Date,
                                                format: "DD/MM/YYYY"
                                            }
                                        },
                                        condition_UwCompany: {
                                            label: "UW Company",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: $page.codes.UwCompany
                                            }
                                        }
                                    }
                                },
                                pos: {
                                    row: 13,
                                    width: 12
                                }
                            },
                            searchButtonPanel: {
                                comp: {
                                    type: $pt.ComponentConstants.ButtonFooter,
                                    buttonLayout: {
                                        right: [
                                            {
                                                text: "Reset",

                                                style: "warning",
                                                click: function (model) {
                                                    $page.controller.reset();
                                                }
                                            }, {
                                                text: "Search",
                                                style: "primary",
                                                click: function (model) {
                                                    $page.controller.doSearch();
                                                }
                                            }
                                        ]
                                    }
                                },
                                pos: {
                                    row: 16,
                                    width: 12
                                }
                            },
                            resultTable: {
                                label: "Search Result",
                                comp: {
                                    type: $pt.ComponentConstants.Table,
                                    sortable: true,
                                    pageable: true,
                                    searchable: false,
                                    criteria: "cachedCriteria",
                                    columns: [{
                                        title: "Contract Id",
                                        data: "ContractCode",
                                        width: "15%"
                                    }, {
                                        title: "Contract Name",
                                        data: "ContractName",
                                        width: "15%"
                                    }, {
                                        title: "Cedent",
                                        data: "Cedent",
                                        // inline:$helper.registerInlineBPSearch(),
                                        width: "15%"
                                    }, {
                                        title: "Broker",
                                        data: "Broker",
                                        // inline:$helper.registerInlineBPSearch(),
                                        width: "15%"
                                    }, {
                                        title: "Main CoB",
                                        data: "Mainclass",
                                        codes: $page.codes.Class,
                                        width: "15%"
                                    }, {
                                        title: "UW Year",
                                        data: "UwYear",
                                        width: "10%"
                                    },{
                                            title: "Period Start",
                                            data: "ReinsStarting",
                                        render: function (row) {
                                            return $helper.formatDateForTableView(row.ReinsStarting, "DD/MM/YYYY");
                                        },
                                            width: "15%"
                                    }, {
                                        title: "Status",
                                        data: "LatestStatus",
                                        codes: $page.codes.LatestStatus,
                                        width: "10%"
                                    }],
                                    maxOperationButtonCount: 3,
                                    moreAsMenu: true,
                                    rowOperations: [{
                                        tooltip: "VIEW",
                                        click:function(model){
                                            var ContCompId = model.ContCompId;
                                            console.log("+++++++++");
                                            console.log(model);
                                            //var contractCode = model.get('ContractCode');
                                            //var uwYear = model.get('UwYear');
                                            var url = "";
                                            url = $pt.getURL("ui.queryView.view");
                                            url = url + "?ContCompId="+ContCompId+"&OperateType="+"0";
                                            console.log("QWE");
                                            console.info(url);
                                            window.location.href = url;
                                            //window.open(url);
                                        }
                                    }],

                                },
                                css:{
                                    comp: "inline-editor",
                                    cell: "title-align"
                                },
                                pos: {
                                    row: 17,
                                    width: 12
                                }
                            },
                            buttonPanel: {
                                comp: {
                                    type: $pt.ComponentConstants.ButtonFooter,
                                    buttonLayout: {
                                        right: [
                                            {
                                                text: "Exit",
                                                style: "warning",
                                                click: function () {
                                                    window.location.href = "../index.html";
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
        createLayout: function () {
            return $.extend(true, {}, this.createBaseLayout());
        }
    });

    $page.layout = new layout();

}(typeof window !== "undefined" ? window : this));
