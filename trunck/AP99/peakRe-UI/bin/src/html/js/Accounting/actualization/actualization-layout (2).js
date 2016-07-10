(function (context) {
    var $page = $pt.getService(context, '$page');

    var layout = jsface.Class({
        createBaseLayout: function () {
            return {
                _sections: {
                    defaultSection: {
                        label: "Actualization",
                        style: 'primary-darken',
                        collapsible: false,
                        layout: {
                            condition_ContractIds: {
                                label: "Contract ID Batch Search",
                                comp: {
                                    type: $pt.ComponentConstants.TextArea,
                                    labelDirection: "vertical",
                                    lines: 3,
                                    labelWidth: 3
                                },
                                pos: {
                                    row: 1,
                                    width: 6
                                }
                            },
                            condition_BrokerRef: {
                                label: "Broker",
                                base: $helper.basePartnerSearch(),
                                pos: {
                                    row: 2
                                }
                            },
                            condition_CedentRef: {
                                label: "Cedent",
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
                            },
                            condition_UwYear: {
                                label: "UW Year",
                                comp: {
                                    type: $pt.ComponentConstants.Date,
                                    format: "YYYY",
                                    valueFormat: "YYYY"
                                },
                                pos: {
                                    row: 3
                                }
                            },
                            condition_Exceeding: {
                                label: "N + 8 quarters",
                                comp: {
                                    type: $pt.ComponentConstants.Check,
                                    labelWidth: 5
                                },
                                pos: {
                                    row: 3
                                }
                            },
                            buttonPanel1: {
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
                                    row: 4,
                                    width: 12
                                }
                            },
                            resultTable: {
                                label: "Search Result",
                                comp: {
                                    type: $pt.ComponentConstants.Table,
                                    searchable: false,
                                    sortable: false,
                                    pageable: true,
                                    criteria: 'cachedCriteria',
                                    //rowSelectable: "selected",
                                    columns: [
                                        {
                                            title: "",
                                            data: "selected",
                                            inline: {
                                                selected: {
                                                    comp: {
                                                        type: {type: $pt.ComponentConstants.Check, label: false},
                                                        enabled: {
                                                            depends: 'Actualized',
                                                            when: function (rowModel) {
                                                                return rowModel.get("Actualized") == 0;
                                                                //return true;
                                                            }
                                                        }
                                                    }
                                                }
                                            },
                                            width: "5%"
                                        }, {
                                            title: "Contract ID",
                                            data: "ContractCode",
                                            width: "15%"
                                        }, {
                                            title: "Contract Name",
                                            data: "ContractName",
                                            width: "15%"
                                        }, {
                                            title: "Cedent",
                                            data: "CedentRef",
                                            width: "10%"
                                        }, {
                                            title: "Broker",
                                            data: "BrokerRef",
                                            width: "10%"
                                        }, {
                                            title: "Main Sub CoB",
                                            data: "Subclass",
                                            codes: $page.codes.SubClass,
                                            width: "15%"
                                        }, {
                                            title: "UW Year",
                                            data: "UwYear",
                                            width: "10%"
                                        }, {
                                            title: "Actualized",
                                            data: "Actualized",
                                            codes: $page.codes.Actualized,
                                            width: "10%"
                                        }, {
                                            data: "ContCompId",
                                            visible: false
                                        }
                                    ],
                                    rowOperations: [
                                        {
                                            tooltip: "View",
                                            click: function (rowModel) {
                                                var ContractCode = rowModel.ContractCode;
                                                var UwYear = rowModel.UwYear;
                                                var url = $pt.getURL('ui.accounting.accountSummary');
                                                window.open(url + "?ContractCode=" + ContractCode + "&UwYear=" + UwYear);
                                            }
                                        }
                                    ]
                                },
                                pos: {
                                    row: 5,
                                    width: 12
                                }
                            },
                            buttonPanel2: {
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
                                            }, {
                                                text: "Actualization",
                                                style: "primary",
                                                click: function () {
                                                    $page.controller.doActualization();
                                                }
                                            }, {
                                                text: "Cancel Selection",
                                                style: "primary",
                                                click: function () {
                                                    $page.controller.cancelSelectio();
                                                }
                                            }, {
                                                text: "Select All",
                                                style: "primary",
                                                click: function () {
                                                    $page.controller.selectAll();
                                                }
                                            }
                                        ]
                                    }
                                },
                                pos: {
                                    row: 6,
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