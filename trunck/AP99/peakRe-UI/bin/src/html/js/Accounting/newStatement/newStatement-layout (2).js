(function (context) {
    var $page = $pt.getService(context, '$page');

    $page.layout = {
        _sections: {
            defaultSection: {
                label: "New Statement",
                style: 'primary-darken',
                collapsible: false,
                layout: {
                    condition_ContractCode: {

                        label: "Contract ID",
                        comp: {
                            type: $pt.ComponentConstants.NContractSearchText,
                            searchTriggerDigits: 6,
                            contractStatus: [4,5],
                         //   isSingle : true,
                            url: $ri.getRestfulURL("action.contract.contractHome") + "/page"
                        },
                        pos: {
                            row: 1
                        }
                    },
                    condition_UwYear: {
                        label: "UW Year",
                        comp: {
                            type: $pt.ComponentConstants.Text
                         //   data: $page.codes.UWYear
                            //type: $pt.ComponentConstants.Date,
                            //format: "YYYY",
                            ///valueFormat: "YYYY"
                        },
                        pos: {
                            row:1
                        }
                    },
                    condition_CedentYear: {
                        label: "Cedent Year",
                        comp: {
                            type: $pt.ComponentConstants.Date,
                            format: "YYYY",
                            valueFormat: "YYYY"
                        },
                        pos: {
                            row:1
                        }
                    },
                    condition_CedentQuarter: {
                        label: "Cedent Quarter",
                        comp: {
                            type: $pt.ComponentConstants.Select,
                            data: $page.codes.CedentQuarter
                        },
                        pos: {
                            row:2
                        }
                    },
                    condition_StatementType: {
                        label: "Statement Type",
                        comp: {
                            type: $pt.ComponentConstants.Select,
                            data: $page.codes.StatementType
                        },
                        pos: {
                            row:2
                        }
                    },
                    condition_CedentPeriod: {
                        label: "CedentPeriod",
                        comp: {
                            type: $pt.ComponentConstants.Select,
                            data: $page.codes.CedentPeriod
                        },
                        pos: {
                            row:2
                        }
                    },
                    footerButton: {
                        comp: {
                            type: $pt.ComponentConstants.ButtonFooter,
                            buttonLayout: {
                                right: [
                                    {
                                        text: "Exit",
                                        style: "warning",
                                        click: function () {
                                            window.location.href = "statementQuery.html";
                                        }
                                    }, {
                                        text: "Create",
                                        style: "primary",
                                        click: function (model) {

                                            if($page.controller.model.get("condition").StatementType=='1'){
                                                $page.controller.doCreate();
                                            }else{
                                                $page.controller.doCreatePTF();
                                            }

                                        }
                                    }
                                ]
                            }
                        },
                        pos: {
                            // row: 3,
                            width: 12
                        }
                    }
                }
            }
        }
    };
}(typeof window !== "undefined" ? window : this));
