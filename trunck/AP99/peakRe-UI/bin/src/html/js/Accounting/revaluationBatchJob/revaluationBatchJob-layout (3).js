(function (context) {
    var $page = $pt.getService(context, '$page');
    $page.layout = {
        _sections: {
            defaultSection: {
                label: "Revaluation Batch Job",
                style: "primary-darken",
                layout: {
                        financialYear: {
                        label: "Financial Year",
                        comp: {
                            type: $pt.ComponentConstants.Date,
                            format: "YYYY",
                            valueFormat: "YYYY"
                        },
                        pos: {
                            row: 1
                        }
                    },
                    financialQuarter: {
                        label: "Financial Quarter",
                        comp: {
                            type: $pt.ComponentConstants.Select,
                            data: $page.codes.FNQuarter
                        },
                        pos: {
                            row: 1
                        }
                    },
                    status: {
                        label: "Status",
                        comp: {
                            type: $pt.ComponentConstants.Select,
                            data: $page.codes.RevaluationStatus
                        },
                        pos: {
                            row: 1
                        }
                    },
                    buttonPanel: {
                        comp: {
                            type: $pt.ComponentConstants.ButtonFooter,
                            buttonLayout: {
                                left: [
                                    {
                                        text: "New Revaluation Execute",
                                        style: "primary",
                                        click:function(){
                                            $page.controller.doValid();
                                        }
                                    }
                                ],
                                right: [
                                    {
                                        text: "Exit",
                                        style: "warning",
                                        click:function(){
                                            window.location.href = "../index.html";
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
                        label: "Result",
                        comp: {
                            type: $pt.ComponentConstants.Table,
                            searchable: false,
                            sortable: false,
                            pageable: true,
                            criteria: 'cachedCriteria',
                            //addable:true,
                            columns: [
                                {
                                    title: "FN Year",
                                    data: "Fnyear",
                                    width: "8%"
                                },
                                {
                                    title: "FN Quarter",
                                    data: "Fnquarter",
                                    width: "8%"
                                }
                                ,
                                {
                                    title: "Revaluation Amount(USD)",
                                    data: "RevaluateValue",
                                    width: "14%",
                                    inline: {
                                        label: {
                                            comp: {
                                                type: {
                                                    label: false,
                                                    popover: true,
                                                    render: function (model) {
                                                        return $helper.formatNumberForLabel(model.get("RevaluateValue"), 2);
                                                    }
                                                }
                                            },
                                            pos: {width: 12},
                                            css: {cell: 'currency-align-right'}
                                        }
                                    }
                                },
                                {
                                    title: "Execute Date",
                                    data: "ExecuteDate",
                                    width: "14%",
                                    render: function (row) {
                                        return $helper.formatDateForTableView(row.ExecuteDate, "DD/MM/YYYY HH:mm:ss");
                                    }
                                },
                                {
                                    title: "Starting Date",
                                    data: "StartingData",
                                    width: "14%",
                                    render: function (row) {
                                        return $helper.formatDateForTableView(row.StartingData, "DD/MM/YYYY HH:mm:ss");
                                    }
                                },
                                {
                                    title: "Finishing Date",
                                    data: "FinishingData",
                                    width: "14%",
                                    render: function (row) {
                                        return $helper.formatDateForTableView(row.FinishingData, "DD/MM/YYYY HH:mm:ss");
                                    }
                                },
                                {
                                    title: "Operator",
                                    data: "Operator",
                                    width: "8%"
                                },
                                {
                                    title: "contracCompID",
                                    data: "RevaluationId",
                                    width: "8%",
                                    visible:false
                                }
                            ],

                            rowOperations: [
                                {
                                    tooltip: "View",
                                    click: function (rowModel) {
                                        $page.controller.exportExcel(rowModel);
                                    }
                                }
                            ]
                        },
                        pos: {
                            row: 7,
                            width: 12
                        }
                    }
                }
            }
        }
    };
}(typeof window !== "undefined" ? window : this));