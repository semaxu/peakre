(function (context) {
    var $page = $pt.getService(context, '$page');

    var layout = jsface.Class({
        createTableColumns: function (tableColumn, status, level) {
            var columns = [{
                title: "Item",
                data: "item",
                codes: $page.codes.entryCode,
                width: 200
            }];
            if (status == 3) {
                tableColumn.forEach(function (tableColumn) {
                    columns.push({
                        title: tableColumn.text,
                        data: tableColumn.value,
                        width: 100,
                        inline: {
                            text: {
                                dataId: tableColumn.value,
                                comp: {
                                    type: $pt.ComponentConstants.Text,
                                    visible: {
                                        depends: {on: 'inner', id: 'adjusting'},
                                        when: function (rowModel) {
                                            if (tableColumn.status != 2) {
                                                return false;
                                            }
                                            var entryCode = rowModel.get("item");
                                            if (entryCode == '4112' || entryCode == '4116') {
                                                return this.getInnerModel().get("adjusting") == "adjustingA";
                                            } else {
                                                return this.getInnerModel().get("adjusting") == "adjustingE";
                                            }
                                        }
                                    },
                                    model: $page.controller.model,
                                    useFormModel: true
                                },
                                pos: {width: 12},
                                css: {comp: 'currency-align-right-text'}
                            },
                            label: {
                                dataId: tableColumn.value,
                                comp: {
                                    type: {
                                        label: false,
                                        popover: false,
                                        render: function (model, layout, direction, view) {
                                            var click = function () {
                                                $page.controller.showTransactionDetail(model.get("item"), tableColumn.text, status);
                                            };
                                            if (level == '1') {
                                                return $helper.formatNumberForLabel(model.get(tableColumn.value), 2);
                                            } else {
                                                return <a href='javascript:void(0);'
                                                          onClick={click}>{$helper.formatNumberForLabel(model.get(tableColumn.value), 2)}</a>;
                                            }
                                        }
                                    },
                                    visible: {
                                        depends: {on: 'inner', id: 'adjusting'},
                                        when: function (rowModel) {
                                            if (tableColumn.status != 2) {
                                                return true;
                                            }
                                            var entryCode = rowModel.get("item");
                                            if (entryCode == '4112' || entryCode == '4116') {
                                                return this.getInnerModel().get("adjusting") != "adjustingA";
                                            } else {
                                                return this.getInnerModel().get("adjusting") != "adjustingE";
                                            }
                                        }
                                    },
                                    model: $page.controller.model,
                                    useFormModel: true
                                },
                                pos: {width: 12},
                                css: {cell: 'currency-align-right'}
                            }
                        }
                    });
                });
            } else {
                tableColumn.forEach(function (tableColumn) {
                    columns.push({
                        title: tableColumn.text,
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
                                                $page.controller.showTransactionDetail(model.get("item"), tableColumn.text, status);
                                            };
                                            if (level == '1') {
                                                return $helper.formatNumberForLabel(model.get(tableColumn.value), 2);
                                            } else {
                                                //console.log('test', tableColumn.value, model.get(tableColumn.value));
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
            }
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
        createTableLayout: function (columnModel, status, level) {
            return {
                comp: {
                    type: $pt.ComponentConstants.Table,
                    searchable: false,
                    sortable: false,
                    header: false,
                    columns: this.createTableColumns(columnModel, status, level)
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
        createBaseLayout: function () {
            var urlData = $pt.getUrlData();
            var exitBool = urlData.Exit;
            return {
                _sections: {
                    defaultSection: {
                        label: "Cedent Quarter View",
                        style: 'primary-darken',
                        layout: {
                            currency: {
                                label: "Currency",
                                comp: {
                                    type: $pt.ComponentConstants.Radio,
                                    labelWidth: 2,
                                    data: $page.controller.model.get("currencies")
                                },
                                pos: {
                                    row: 1,
                                    width: 6
                                }
                            },
                            date: {
                                label: "Date",
                                comp: {
                                    type: $pt.ComponentConstants.Date,
                                    format: "DD/MM/YYYY",
                                    valueFormat: "YYYY/MM/DD",
                                    labelWidth: 3
                                },
                                pos: {
                                    row: 2
                                }
                            },
                            searchBtn: {
                                label: "Search",
                                comp: {
                                    type: $pt.ComponentConstants.Button,
                                    style: "primary",
                                    click: function (model) {
                                        $page.controller.doSearch(model.get("date"));
                                    }
                                },
                                pos: {
                                    row: 2
                                }
                            },
                            forecastPanel: {
                                label: "Forecast",
                                comp: {
                                    type: $pt.ComponentConstants.Panel,
                                    collapsible: true,
                                    editLayout: {
                                        forecastTable: this.createTableLayout($page.controller.model.get("tableColumns"), 1, $page.controller.model.get("level"))
                                    }
                                },
                                pos: {
                                    row: 4,
                                    width: 12
                                }
                            },
                            estimationPanel: {
                                label: "Estimation",
                                comp: {
                                    type: $pt.ComponentConstants.Panel,
                                    collapsible: true,
                                    expanded: false,
                                    editLayout: {
                                        estimationTable: this.createTableLayout($page.controller.model.get("tableColumns"), 3, $page.controller.model.get("level"))
                                    }
                                },
                                pos: {
                                    row: 5,
                                    width: 12
                                }
                            },
                            reversalPanel: {
                                label: "Estimation Reversal",
                                comp: {
                                    type: $pt.ComponentConstants.Panel,
                                    collapsible: true,
                                    expanded: false,
                                    editLayout: {
                                        reversalTable: this.createTableLayout($page.controller.model.get("tableColumns"), 4, $page.controller.model.get("level"))
                                    }
                                },
                                pos: {
                                    row: 6,
                                    width: 12
                                }
                            },
                            actualPanel: {
                                label: "Actual",
                                comp: {
                                    type: $pt.ComponentConstants.Panel,
                                    collapsible: true,
                                    expanded: false,
                                    editLayout: {
                                        actualTable: this.createTableLayout($page.controller.model.get("tableColumns"), 5, $page.controller.model.get("level"))
                                    }
                                },
                                pos: {
                                    row: 7,
                                    width: 12
                                }
                            },
                            buttonPanel: {
                                comp: {
                                    type: $pt.ComponentConstants.ButtonFooter,
                                    buttonLayout: {
                                        //left: [
                                        //    {
                                        //        text: "Export Excel",
                                        //        style: "primary",
                                        //        visible: {
                                        //            depends: {on: 'inner', id: 'level'},
                                        //            when: function () {
                                        //                if (exitBool == 1) {
                                        //                    return false;
                                        //                } else {
                                        //                    return this.getInnerModel().get("level") == '2';
                                        //                }
                                        //            }
                                        //        },
                                        //        click: function () {
                                        //            $page.controller.exportExcel();
                                        //        }
                                        //    }
                                        //],
                                        right: [
                                            {
                                                text: "Exit",
                                                style: "warning",
                                                click: function () {
                                                    $page.controller.exit();
                                                }
                                            }, {
                                                text: "Recalculate Forecast",
                                                style: "primary",
                                                enabled: {
                                                    when: function () {
                                                        if (exitBool == 1) {
                                                            return false;
                                                        } else {
                                                            if (this.getInnerModel().get("cleanCut")) {
                                                                return false;
                                                            } else {
                                                                return true;
                                                            }
                                                        }
                                                    }
                                                },
                                                model: $page.controller.model,
                                                click: function () {
                                                    $page.controller.recalculateForecast();
                                                }
                                            }, {
                                                text: "UPR/DAC Adjustment",
                                                style: "primary",
                                                enabled: {
                                                    depends: {on: 'inner', id: 'level'},
                                                    when: function () {
                                                        if (exitBool == 1) {
                                                            return false;
                                                        } else {
                                                            return this.getInnerModel().get("level") == '2' && !this.getInnerModel().get("cleanCut");
                                                        }
                                                    }
                                                },
                                                visible: {
                                                    depends: {on: 'inner', id: 'adjusting'},
                                                    when: function () {
                                                        return this.getInnerModel().get("adjusting") == "" || this.getInnerModel().get("adjusting") == "adjustingE";
                                                    }
                                                },
                                                model: $page.controller.model,
                                                click: function () {
                                                    $page.controller.model.set("adjusting", "adjustingA");
                                                }
                                            }, {
                                                text: "Cancel",
                                                style: "warning",
                                                visible: {
                                                    depends: {on: 'inner', id: 'adjusting'},
                                                    when: function () {
                                                        if (exitBool == 1) {
                                                            return false;
                                                        } else {
                                                            return this.getInnerModel().get("adjusting") == "adjustingA";
                                                        }
                                                    }
                                                },
                                                model: $page.controller.model,
                                                click: function () {
                                                    $page.controller.cancelAdjustmentA();
                                                }
                                            }, {
                                                text: "Save",
                                                style: "primary",
                                                visible: {
                                                    depends: {on: 'inner', id: 'adjusting'},
                                                    when: function () {
                                                        if (exitBool == 1) {
                                                            return false;
                                                        } else {
                                                            return this.getInnerModel().get("adjusting") == "adjustingA";
                                                        }
                                                    }
                                                },
                                                model: $page.controller.model,
                                                click: function () {
                                                    $page.controller.saveAdjustmentA();
                                                }
                                            }, {
                                                text: "Estimation Adjustment",
                                                style: "primary",
                                                enabled: {
                                                    depends: {on: 'inner', id: 'level'},
                                                    when: function () {
                                                        if (exitBool == 1) {
                                                            return false;
                                                        } else {
                                                            return this.getInnerModel().get("level") == '2' && !this.getInnerModel().get("cleanCut");
                                                        }
                                                    }
                                                },
                                                visible: {
                                                    depends: {on: 'inner', id: 'adjusting'},
                                                    when: function () {
                                                        return this.getInnerModel().get("adjusting") == "" || this.getInnerModel().get("adjusting") == "adjustingA";
                                                    }
                                                },
                                                model: $page.controller.model,
                                                click: function () {
                                                    $page.controller.model.set("adjusting", "adjustingE");

                                                }
                                            }, {
                                                text: "Cancel",
                                                style: "warning",
                                                visible: {
                                                    depends: {on: 'inner', id: 'adjusting'},
                                                    when: function () {
                                                        if (exitBool == 1) {
                                                            return false;
                                                        } else {
                                                            return this.getInnerModel().get("adjusting") == "adjustingE";
                                                        }
                                                    }
                                                },
                                                model: $page.controller.model,
                                                click: function () {
                                                    $page.controller.cancelAdjustmentE();
                                                }
                                            }, {
                                                text: "Save",
                                                style: "primary",
                                                visible: {
                                                    depends: {on: 'inner', id: 'adjusting'},
                                                    when: function () {
                                                        if (exitBool == 1) {
                                                            return false;
                                                        } else {
                                                            return this.getInnerModel().get("adjusting") == "adjustingE";
                                                        }
                                                    }
                                                },
                                                model: $page.controller.model,
                                                click: function () {
                                                    $page.controller.saveAdjustmentE();
                                                }
                                            }, {
                                                text: "Post Estimation",
                                                style: "primary",
                                                enabled: {
                                                    depends: {on: 'inner', id: 'level'},
                                                    when: function () {
                                                        if (exitBool == 1) {
                                                            return false;
                                                        } else {
                                                            return this.getInnerModel().get("level") == '2' && !this.getInnerModel().get("cleanCut");
                                                        }
                                                    }
                                                },
                                                model: $page.controller.model,
                                                click: function (model) {
                                                    $page.controller.doShowSectionDialog(model.get("contCompId"));
                                                }
                                            }
                                        ]
                                    }
                                },
                                pos: {
                                    row: 8,
                                    width: 12,
                                    section: "defaultSection"
                                }
                            }
                        }
                    }
                }
            }
        },
        createLayout: function () {
            return $.extend(true, {}, this.createBaseLayout());
        },
        createQuarterCodeTable: function (data) {
            var array = [];
            data.forEach(function (data) {
                array.push({
                    id: data.EstimateId,
                    text: data.YearQuarter.Year + "Q" + data.YearQuarter.Quarter
                })
            });
            return array;
        },
        createQuarterLayout: function (data) {
            return {
                quarterTree: {
                    label: "Please choose quarter",
                    comp: {
                        type: $pt.ComponentConstants.SelectTree,
                        data: $pt.createCodeTable(this.createQuarterCodeTable(data)),
                        treeLayout: {
                            comp: {
                                hierarchyCheck: true,
                                expandLevel: "all",
                                inactiveSlibing: false,
                                valueAsArray: true
                            }
                        }
                    },
                    pos: {
                        width: 12
                    }
                }
            }
        }
    });

    $page.layout = new layout();

}(typeof window !== "undefined" ? window : this));
