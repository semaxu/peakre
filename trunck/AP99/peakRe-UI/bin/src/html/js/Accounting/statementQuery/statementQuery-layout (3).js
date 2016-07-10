(function (context) {
    var $page = $pt.getService(context, '$page');
    var codes = $pt.getService($page, 'codes');
    $page.layout = {
        _sections: {
            searchCondition: {
                label: "Statement Query",
                style: 'primary-darken',
                collapsible: false,
                layout: {
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
                    condition_Cedent: {
                        label: "Cedent",
                        base: $helper.basePartnerSearch(),
                        pos: {
                            row: 1
                        }
                    },
                    condition_Broke: {
                        label: "Broker",
                        base: $helper.basePartnerSearch(),
                        pos: {
                            row: 2
                        }
                    },
                    condition_SoaId: {
                        label: "Statement ID",
                        comp: {
                            type: $pt.ComponentConstants.Text
                        },
                        pos: {
                            row: 2
                        }
                    },
                    condition_BizType: {
                        label: "Business Type",
                        comp: {
                            type: $pt.ComponentConstants.Radio,
                            data: $page.codes.BusinessType
                        },
                        pos: {
                            row: 2
                        }
                    },
                    condition_CedentYear: {
                        label: "Cedent Year",
                        comp: {
                            type: $pt.ComponentConstants.Select,
                            data: $page.codes.CedentYear
                        },
                        pos: {
                            row: 3
                        }
                    },
                    condition_ReceivedDate: {
                        label: "Date Received",
                        comp: {
                            type: $pt.ComponentConstants.Date,
                            format: "DD/MM/YYYY"
                        },
                        pos: {
                            row: 3
                        }
                    },
                    condition_TaskReleaser: {
                        label: "Task Releaser",
                        comp: {
                            type: $pt.ComponentConstants.Select,
                            data: codes.SystemUser
                        },
                        pos: {
                            row: 3
                        }
                    },
                    condition_CedentQuarter: {
                        label: "Cedent Quarter",
                        comp: {
                            type: $pt.ComponentConstants.Select,
                            data: $page.codes.CedentQuarter
                        },
                        pos: {
                            row: 4
                        }
                    },
                    condition_FinancialYear: {
                        label: "Financial Year",
                        comp: {
                            type: $pt.ComponentConstants.Select,
                            data: $page.codes.FNYear
                        },
                        pos: {
                            row: 4
                        }
                    },
                    condition_TaskCreator: {
                        label: "Task Creator",
                        comp: {
                            type: $pt.ComponentConstants.Select,
                            data: codes.SystemUser
                        },
                        pos: {
                            row: 4
                        }
                    },
                    condition_UwYear: {
                        label: "UW Year",
                        comp: {
                            type: $pt.ComponentConstants.Select,
                            data: $page.codes.UwYear
                        },
                        pos: {
                            row: 5
                        }
                    },
                    condition_FinancialQuarter: {
                        label: "Financial Quarter",
                        comp: {
                            type: $pt.ComponentConstants.Select,
                            data: $page.codes.FNQuarter
                        },
                        pos: {
                            row: 5
                        }
                    },
                    condition_SoaStatus: {
                        label: "Status",
                        comp: {
                            type: $pt.ComponentConstants.Select,
                            data: $page.codes.SoaStatus
                        },
                        pos: {
                            row: 5
                        }
                    },
                    condition_CedentPeriod: {
                        label: "Cedent Period",
                        comp: {
                            type: $pt.ComponentConstants.Select,
                            data: $page.codes.CedentPeriod
                        },
                        pos: {row: 6}
                    },
                    searchButton: {
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
                            row: 6,
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
                            criteria: 'cachedCriteria',
                            //addable:true,
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
                                    title: "Contract ID",
                                    data: "ContractCode",
                                    width: "8%"
                                }, {
                                    title: "Contract Name",
                                    data: "ContractName",
                                    width: "8%"
                                }, {
                                    title: "UW Year",
                                    data: "UwYear",
                                    width: "8%"
                                }, {
                                    title: "FN Year",
                                    data: "FinancialYear",
                                    width: "8%"
                                }, {
                                    title: "FN Quarter",
                                    data: "FinancialQuarter",
                                    codes: $page.codes.FNQuarter,
                                    width: "8%"
                                }, {
                                    title: "Cedent",
                                    data: "Cedent",
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
                                    title: "Reviewed",
                                    data: "ReviewedFlag",
                                    codes: $page.codes.ReviewedFlag,
                                    width: "8%"
                                }, {
                                    title: "Statement Text",
                                    data: "SoaText",
                                    width: "8%"
                                }, {
                                    title: "Task Creator",
                                    data: "TaskCreator",
                                    width: "8%",
                                    visible:false
                                }, {
                                    title: "Statement Type",
                                    data: "StatementType",
                                    width: "8%",
                                    visible:false
                                }
                            ],

                            rowOperations: [
                                {
                                    tooltip: "View",

                                    click: function (rowModel) {
                                        $page.controller.doView(rowModel);
                                    }

                                }, {
                                    tooltip: "Edit",
                                    enabled: {
                                        depends: "status",
                                        when: function (rowModel) {
                                            return rowModel.getCurrentModel().SoaStatus == '1';
                                        }
                                    },
                                    click: function (rowModel) {
                                        $page.controller.loadSoa(rowModel);
                                    }
                                }, {
                                    tooltip: "Cancel",
                                    enabled: {
                                        depends: "status",
                                        when: function (rowModel) {
                                            return rowModel.getCurrentModel().SoaStatus == '1';
                                        }
                                    },
                                    click: function (rowModel) {
                                        $page.controller.doCancel(rowModel);
                                    }
                                }, {
                                    tooltip: "Withdraw",
                                    enabled: {
                                        depends: "status",
                                        when: function (rowModel) {
                                            return rowModel.getCurrentModel().SoaStatus == '2';
                                        }
                                    },
                                    click: function (rowModel) {
                                        $page.controller.doWithdraw(rowModel);
                                    }
                                }
                                //,
                                //{
                                //    tooltip: "xml",
                                //
                                //    click: function (rowModel) {
                                //        $page.controller.loadSoaInfo(rowModel,5);
                                //    }
                                //}
                            ]
                        },
                        pos: {
                            row: 7,
                            width: 12
                        }
                    },
                    buttonFooter: {
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
                                        text: "Create",
                                        style: "primary",
                                        click: function () {
                                            window.location.href = "newStatement.html";
                                        }
                                    }
                                    ,
                                    {
                                        text: "Statement Upload",
                                        style: "primary",
                                        visible: {
                                            when: function (model) {
                                                var urlData = $pt.getUrlData();
                                                var operateType = urlData.OperateType;
                                                return operateType != '4' && operateType != '5';
                                            },
                                            depends: 'OperateType'
                                        },
                                        click: function () {
                                            $pt.dataImport(26, 0);
                                        }
                                    }
                                ]
                            }
                        },
                        pos: {
                            row: 8,
                            width: 12
                        }
                    }
                }
            }
        }
    };

}(typeof window !== "undefined" ? window : this));
