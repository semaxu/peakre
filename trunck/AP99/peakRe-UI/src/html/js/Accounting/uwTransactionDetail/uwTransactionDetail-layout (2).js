(function (context) {
    var $page = $pt.getService(context, '$page');

    var layout = jsface.Class({
        createSectionLabel: function (status) {
            var label;
            if (status == '1') {
                label = "Forecast Detail page";
            } else if (status == '3') {
                label = "Estimation Detail page";
            } else if (status == '4') {
                label = "Estimation Reversal Detail page";
            } else if (status == '5') {
                label = "Actual Detail page";
            }
            return label;
        },
        createTableColumns: function (status) {
            var columns = [{
                title: "Item",
                data: "entryCode",
                codes: $page.codes.detailEntry,
                width: 200
            }];
            if (status == '1') {
                columns.push({
                    title: "Text",
                    data: "changeReason",
                    width: 300
                }, {
                    title: "Date",
                    data: "insertTime",
                    //inline: "date",
                    render: function (row) {
                        return $helper.formatDateForTableView(row.insertTime, "DD/MM/YYYY");
                    },
                    width: 100
                }, {
                    title: "Input User",
                    data: "insertBy",
                    codes: $page.codes.SystemUser,
                    width: 200
                }, {
                    title: "Amount",
                    data: "amount",
                    width: 200,
                    inline: {
                        label: {
                            comp: {
                                type: {
                                    label: false,
                                    popover: false,
                                    render: function (model) {
                                        return $helper.formatNumberForLabel(model.get("amount"), 2);
                                    }
                                }
                            },
                            pos: {width: 12},
                            css: {cell: 'currency-align'}
                        }
                    }
                }, {
                    title: "Calculation Formula",
                    data: "calcFormula",
                    width: 200
                });
            } else if (status == '3' || status == '4') {
                columns.push({
                    title: "Text",
                    data: "changeReason",
                    width: 300
                }, {
                    title: "Date",
                    data: "insertTime",
                    //inline: "date",
                    render: function (row) {
                        return $helper.formatDateForTableView(row.insertTime, "DD/MM/YYYY");
                    },
                    width: 200
                }, {
                    title: "Input User",
                    data: "insertBy",
                    codes: $page.codes.SystemUser,
                    width: 250
                }, {
                    title: "Amount",
                    data: "amount",
                    width: 250,
                    inline: {
                        label: {
                            comp: {
                                type: {
                                    label: false,
                                    popover: false,
                                    render: function (model) {
                                        return $helper.formatNumberForLabel(model.get("amount"), 2);
                                    }
                                }
                            },
                            pos: {width: 12},
                            css: {cell: 'currency-align'}
                        }
                    }
                });
            } else if (status == '5') {
                columns.push({
                    title: "Text",
                    data: "text",
                    width: 300
                }, {
                    title: "Date",
                    data: "insertTime",
                    //inline: "date",
                    render: function (row) {
                        return $helper.formatDateForTableView(row.insertTime, "DD/MM/YYYY");
                    },
                    width: 200
                }, {
                    title: "Input User",
                    data: "insertBy",
                    codes: $page.codes.SystemUser,
                    width: 200
                }, {
                    title: "Currency",
                    data: "currency",
                    width: 200
                }, {
                    title: "Amount",
                    data: "amount",
                    width: 200,
                    inline: {
                        label: {
                            comp: {
                                type: {
                                    label: false,
                                    popover: false,
                                    render: function (model) {
                                        return $helper.formatNumberForLabel(model.get("amount"), 2);
                                    }
                                }
                            },
                            pos: {width: 12},
                            css: {cell: 'currency-align'}
                        }
                    }
                }, {
                    title: "Amount(" + $page.controller.model.get("mainCurrency") + ")",
                    data: "switchedAmount",
                    width: 200,
                    inline: {
                        label: {
                            comp: {
                                type: {
                                    label: false,
                                    popover: false,
                                    render: function (model) {
                                        return $helper.formatNumberForLabel(model.get("switchedAmount"), 2);
                                    }
                                }
                            },
                            pos: {width: 12},
                            css: {cell: 'currency-align'}
                        }
                    }
                });
            }
            return columns;
        },
        createBaseLayout: function () {
            return {
                _sections: {
                    defaultSection: {
                        //label: "Transaction details",
                        label: this.createSectionLabel($page.controller.model.get("status")),
                        style: "primary-darken",
                        collapsible: false,
                        layout: {
                            infoPanel: {
                                comp: {
                                    type: $pt.ComponentConstants.Panel,
                                    editLayout: {
                                        item: {
                                            label: "Item",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: $page.codes.entryCode,
                                                enabled: false
                                            },
                                            pos: {
                                                row: 1,
                                                width: 4
                                            }

                                        },
                                        quarterSeq: {
                                            label: "Cedent Quarter",
                                            comp: {
                                                type: $pt.ComponentConstants.Text,
                                                enabled: false
                                            },
                                            pos: {
                                                row: 1,
                                                width: 4
                                            }
                                        }
                                    }
                                },
                                pos: {
                                    row: 1,
                                    width: 12
                                }
                            },
                            accountTable: {
                                comp: {
                                    type: $pt.ComponentConstants.Table,
                                    searchable: false,
                                    sortable: false,
                                    header: false,
                                    columns: this.createTableColumns($page.controller.model.get("status"))
                                },
                                pos: {
                                    row: 2,
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
                                                    window.close();
                                                }
                                            }
                                        ]
                                    }
                                },
                                pos: {
                                    row: 3,
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