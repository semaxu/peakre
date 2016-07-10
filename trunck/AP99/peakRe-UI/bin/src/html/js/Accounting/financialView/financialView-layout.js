(function (context) {
    var $page = $pt.getService(context, '$page');

    var layout = jsface.Class({
        createTableColumns: function (tableColumn, level) {
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
            tableColumn.forEach(function (tableColumn) {
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
        createBaseLayout: function () {
            return {
                _sections: {
                    defaultSection: {
                        label: "Financial View",
                        style: "primary-darken",
                        layout: {
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
                                                    window.location.href = $pt.getURL('ui.accounting.accountSummaryQuery');
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
