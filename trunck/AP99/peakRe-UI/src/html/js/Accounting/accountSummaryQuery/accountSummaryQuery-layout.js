(function (context) {
    var $page = $pt.getService(context, '$page');
    $page.layout = $pt.getService($page, 'layout');
    $page.layout.client = $pt.getService($page.layout, 'client');

    var layout = jsface.Class({
        createBaseLayout : function(){
            return{
                _sections: {
                    defaultSection: {
                        label: "Account Summary Query",
                        style: 'primary-darken',
                        collapsible: false,
                        layout: {
                            searchCriteria: {
                                comp: {
                                    type: $pt.ComponentConstants.Panel,
                                    editLayout: {
                                        condition_ContractCode: {
                                            label: "Contract ID",
                                            comp: {
                                                type: $pt.ComponentConstants.Text
                                            },
                                            pos: {
                                                row: 1
                                            }
                                        },
                                        condition_ContractName: {
                                            label: "Contract Name",
                                            comp: {
                                                type: $pt.ComponentConstants.Text
                                            },
                                            pos: {
                                                row: 1
                                            }
                                        },
                                        condition_UwYear: {
                                            label: "UW Year",
                                            comp: {
                                                type: $pt.ComponentConstants.Date,
                                                format:"YYYY",
                                                valueFormat:"YYYY"
                                            },
                                            pos: {
                                                row: 1
                                            }
                                        },
                                        condition_Broker: {
                                            label: "Broker",
                                            // comp: {
                                            //     type: $pt.ComponentConstants.CodeSearch,
                                            //     searchTriggerDigits: 8,
                                            //     codeTableId: "800000"
                                            // },
                                            base: $helper.basePartnerSearch(),
                                            pos: {
                                                row: 2
                                            }
                                        },
                                        condition_Cedent: {
                                            label: "Cedent",
                                            // comp: {
                                            //     type: $pt.ComponentConstants.CodeSearch,
                                            //     searchTriggerDigits: 8,
                                            //     codeTableId: "800001"
                                            // },
                                            base: $helper.basePartnerSearch(),
                                            pos: {
                                                row: 2
                                            }
                                        },
                                        condition_Subclass: {
                                            label: "Main Sub CoB",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: $page.codes.SubClass
                                            },
                                            pos: {
                                                row: 2
                                            }
                                        }
                                    }
                                },
                                pos: {
                                    row: 1,
                                    width: 12
                                }
                            },
                            searchPanel: {
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
                                    row: 2,
                                    width: 12
                                }
                            },
                            queryResult: {
                                label: "Query Result",
                                comp: {
                                    type: $pt.ComponentConstants.Table,
                                    searchable: false,
                                    sortable: false,
                                    pageable: true,
                                    view:true,
                                    criteria: 'cachedCriteria',
                                    columns: [
                                        {
                                            title: "Contract ID",
                                            data: "ContractCode",
                                            width: "10%"
                                        }, {
                                            title: "Contract Name",
                                            data: "ContractName",
                                            width: "15%"
                                        }, {
                                            title: "Cedent",
                                            data: "Cedent",
                                            //inline:$helper.registerInlineBPSearch(),
                                            width: "22%"
                                        }, {
                                            title: "Broker",
                                            data: "Broker",
                                           // inline:$helper.registerInlineBPSearch(),
                                            width: "22%"
                                        }, {
                                            title: "Main Sub CoB",
                                            data: "Subclass",
                                            codes: $page.codes.SubClass,
                                            width: "15%"
                                        }, {
                                            title: "UW Year",
                                            data: "UwYear",
                                            width: "5%"
                                        }, {
                                            data: "ContCompId",
                                            visible: false
                                        }
                                    ],
                                    rowOperations: [
                                        {
                                            tooltip: "Cedent Quarter View",
                                            click: function (rowModel) {
                                                var ContractCode = rowModel.ContractCode;
                                                var UwYear = rowModel.UwYear;
                                                var url = $pt.getURL('ui.accounting.accountSummary');
                                                window.open(url + "?ContractCode=" + ContractCode + "&UwYear=" + UwYear);
                                            }
                                        }
                                        //, {
                                        //    tooltip: "   |   ",
                                        //}, {
                                        //    tooltip: "Financial View",
                                        //    click: function (rowModel) {
                                        //        var contCompId = rowModel.ContCompId;
                                        //        var url = $pt.getURL('ui.accounting.financialView');
                                        //        window.open(url + "?ContCompId=" + contCompId);
                                        //    }
                                        //}
                                    ]
                                },
                                pos: {
                                    row: 3,
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
                                    row: 4,
                                    width: 12
                                }
                            }
                        }
                    }
                }
            }
        },
        createLayout : function(){
            return $.extend(true,{},this.createBaseLayout());
        }
    });

    $page.layout = new layout();

}(typeof window !== "undefined" ? window : this));
