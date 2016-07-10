(function (context) {
        var $page = $pt.getService(context, "$page");
        var codes = $pt.getService(context, "$codes");
        NTable.registerInlineEditor('amountLabel', {
            comp: {
                type: $pt.ComponentConstants.Label,
                style: "danger",
           //     style: "primary"
            }
        });
        var LayoutHelper = jsface.Class({
            createFormLayout: function (tableColumnCount) {
                var layout = $.extend({
                    resultTablePanel: this.createResultLayout(tableColumnCount)},
                    this.createButtonLayout());
                return $pt.createFormLayout(layout);
            },
            createButtonLayout: function () {
                return {
                    buttons: {
                        comp: {
                            type: $pt.ComponentConstants.ButtonFooter,
                            buttonLayout: {
                                right: [
                                    {
                                        text: "Export Execel",
                                        style: "primary",
                                        icon: "eye",
                                        click: function () {
                                            $page.controller.exportExcel();
                                        }
                                    },
                                    {
                                        text: "Exit",
                                        style: "warning",
                                        icon: "eject",
                                        click: function (model) {
                                            var soaId = $page.controller.model.get('SoaID');
                                            var statementTypeTemp = $pt.getUrlData().statementType;
                                            var statementType = '800004';
                                            if(statementTypeTemp=='2'){
                                                statementType = '800020';
                                            }else if(statementTypeTemp=='3'){
                                                statementType = '800021';
                                            }else {
                                                statementType = '800004';
                                            }
                                            window.location.href = "statementInput.html?soaModelType=continue&&soaId=" + soaId+"&&statementType="+ statementType;
                                        }
                                    }
                                ]
                            }
                        },
                        pos: {width: 12}
                    }
                };
            },
            createResultTableColumns: function (tableColumnCount) {
                var columns = [{
                    title: " ",
                    data: "LineTitle",
                    width: "8%"
                }];
                for (var index = 0; index < tableColumnCount; index++) {
                    columns.push({
                        title: ('Statement Section'+0+(index +1)+''),
                        data: 'Column' + (index + 1),
                  //      inline : "amountLabel",
                    //    width: 92 / tableColumnCount + '%'
                        width: "8%"
                    });
                }
                columns.push({
                    title:  'Total',
                    data: 'Total',
                //    inline : "amountLabel",
                    width: "8%"
                });
                return columns;
            },
            createResultLayout: function (tableColumnCount) {
                return {
                    label: 'Currency Panel',
                    dataId: 'Currency',
                    comp: {
                        type: $pt.ComponentConstants.ArrayPanel,
                        itemTitle: {
                            depends: 'ContractCurrecy',
                            when: function (item) {
                                return $page.codes.Currency.getText(item.get("ContractCurrecy"))
                            }
                        },
                        ContractCurrecy: {
                            label: 'ContractCurrecy',
                            comp: {
                                type: $pt.ComponentConstants.Text,
                                enabled: false
                            },
                            pos: {row: 1}
                        },
                        editLayout: {
                            queryResult: {
                                //   label: "Query Result",
                                comp: {
                                    type: $pt.ComponentConstants.Table,
                                    searchable: false,
                                    sortable: false,
                                    columns: this.createResultTableColumns(tableColumnCount)
                                },
                                pos: {width: 12}
                            }
                        },
                        style: 'primary-darken',
                    },
                    pos: {
                        width: 12
                    }
                }
            }
        });
        $page.layoutHelper = new LayoutHelper();
    }(typeof window !== "undefined" ? window : this)
);