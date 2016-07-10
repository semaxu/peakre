(function (context) {
    var $page = $pt.getService(context, '$page');

    $page.layout = {
        _sections: {
            defaultSection: {
                label: "Batch Job Query",
                style: "primary-darken",
                layout: {
                    batchJobModule: {
                        label: "Batch Job Module",
                        comp: {
                            type: $pt.ComponentConstants.Select
                        },
                        pos: {
                            row: 1
                        }
                    },
                    batchJobType: {
                        label: "Batch Job Type",
                        comp: {
                            type: $pt.ComponentConstants.Select
                        },
                        pos: {
                            row: 1
                        }
                    },
                    runID: {
                        label: "Run ID",
                        comp: {
                            type: $pt.ComponentConstants.Text
                        },
                        pos: {
                            row: 1
                        }
                    },
                    batchJobStatus: {
                        label: "Batch Job Status",
                        comp: {
                            type: $pt.ComponentConstants.Select
                        },
                        pos: {
                            row: 2
                        }
                    },
                    processingDate: {
                        label: "Processing Date",
                        comp: {
                            type: $pt.ComponentConstants.Date
                        },
                        pos: {
                            row: 2
                        }
                    },
                    operator: {
                        label: "Operator",
                        comp: {
                            type: $pt.ComponentConstants.Select
                        },
                        pos: {
                            row: 2
                        }
                    },
                    searchPanel: {
                        comp: {
                            type: $pt.ComponentConstants.ButtonFooter,
                            buttonLayout: {
                                right: [
                                    {
                                        text: "Reset",
                                        style: "warning"
                                    }, {
                                        text: "Search",
                                        style: "primary"
                                    }
                                ]
                            }
                        },
                        pos: {
                            row: 3,
                            width: 12
                        }
                    },
                    searchResult: {
                        comp: {
                            type: $pt.ComponentConstants.Table,
                            searchable: false,
                            sortable: false,
                            header: false,
                            moreAsMenu: true,
                            columns: [
                                {
                                    title: "Batch Job Type",
                                    data: "batchJobType",
                                    width: "13%"
                                }, {
                                    title: "Run ID",
                                    data: "runID",
                                    width: "13%"
                                }, {
                                    title: "Processing Date",
                                    data: "processingDate",
                                    width: "13%"
                                }, {
                                    title: "Starting Time",
                                    data: "startingTime",
                                    width: "13%"
                                }, {
                                    title: "Finish Time",
                                    data: "finishTime",
                                    width: "13%"
                                }, {
                                    title: "Operator",
                                    data: "operator",
                                    width: "13%"
                                }, {
                                    title: "Batch Job Status",
                                    data: "batchJobStatus",
                                    width: "13%"
                                }
                            ],
                            rowOperations: [
                                {
                                    tooltip: "view"
                                }
                            ]
                        },
                        pos: {
                            row: 4,
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
                                            window.location.href = "/peak-basic-ui/src/main/webapp/html/index.html";
                                        }
                                    }
                                ]
                            }
                        },
                        pos: {
                            row: 5,
                            width: 12
                        }
                    },
                }
            }
        }
    };
}(typeof window !== "undefined" ? window : this));