(function (context, $, $pt) {
    var $page = $pt.getService(context, '$page');
    var isReadOnly = false;
    var Currency = React.createClass($pt.defineCellComponent({
        statics: {},
        propTypes: {
            // model
            model: React.PropTypes.object,
            // CellLayout
            layout: React.PropTypes.object
        },
        getDefaultProps: function () {
            return {
                defaultOptions: {
                    editLayout: {
                        _sections: {
                            Currency: {
                                label: "Currency",
                                style: 'primary-darken',
                                collapsible: true,
                                expanded: true,
                                layout: {
                                    Currency: {
                                        comp: {
                                            type: $pt.ComponentConstants.Panel,
                                            editLayout: {
                                                BusinessConditionVO_CurrencyList: {
                                                    comp: {
                                                        type: $pt.ComponentConstants.Table,
                                                        rowListener: [
                                                            {
                                                                id: 'MainCurrencyType', listener: function (evt) {
                                                                var _self = evt.model.__parent.__model;
                                                                //$page.currencyType = _self.BusinessConditionVO.CurrencyList.CurrencyType;
                                                                //$page.mainCurrencyType = _self.BusinessConditionVO.CurrencyList.MainCurrencyType;
                                                                $page.mainCurrency = 1.00;
                                                                $page.nonMainCurrency = 0.10;
                                                                var currencyType = evt.model.get("CurrencyType");
                                                                var mainCurrencyType = evt.model.get("MainCurrencyType");
                                                                if (mainCurrencyType == true) {
                                                                    evt.model.set("CurrencyRate", $page.mainCurrency);
                                                                } else {
                                                                    evt.model.set("CurrencyRate", $page.nonMainCurrency);
                                                                }
                                                            }
                                                            }, {
                                                                id: 'ExchangeType', listener: function (evt) {
                                                                    var _self = evt.model.__parent.__model;
                                                                    $page.mainCurrency = 1.00;
                                                                    $page.nonMainCurrency = 0.10;
                                                                    evt.model.set("CurrencyDate", "");
                                                                    evt.model.set("CurrencyRate", "");
                                                                    var currencyType = evt.model.get("CurrencyType");
                                                                    var exchangeType = evt.model.get("ExchangeType");
                                                                    var mainCurrencyType = evt.model.get("MainCurrencyType");
                                                                    if (exchangeType == '1' && mainCurrencyType == true && exchangeType && currencyType) {
                                                                        evt.model.set("CurrencyRate", $page.mainCurrency);
                                                                    }
                                                                }
                                                            }, {
                                                                id: 'CurrencyType', listener: function (evt) {
                                                                    var _self = evt.model.__parent.__model;
                                                                    $page.mainCurrency = 1.00;
                                                                    $page.nonMainCurrency = 0.10;
                                                                    evt.model.set("CurrencyRate", "");
                                                                    var currencyType = evt.model.get("CurrencyType");
                                                                    var exchangeType = evt.model.get("ExchangeType");
                                                                    var mainCurrencyType = evt.model.get("MainCurrencyType");
                                                                    if (exchangeType == '1' && mainCurrencyType == true && exchangeType && currencyType) {
                                                                        evt.model.set("CurrencyRate", $page.mainCurrency);
                                                                    }
                                                                }
                                                            }

                                                        ],
                                                        // removable: true,
                                                        sortable: false,
                                                        addable: true,
                                                        searchable: false,
                                                        columns: [
                                                            {
                                                                title: "Currency",
                                                                data: "CurrencyType",
                                                                //inline: "select",
                                                                codes: $page.codes.Currency,
                                                                inline: {
                                                                    CurrencyType: {
                                                                        comp: {
                                                                            label: false,
                                                                            type: $pt.ComponentConstants.Select,
                                                                            data: $page.codes.Currency,
                                                                            enabled: {
                                                                                when: function (model) {
                                                                                    if ((model.get('MainCurrencyType') == true)) {
                                                                                        //console.log(model.get('ExchangeType')+'>>>>>');
                                                                                        return false;
                                                                                    }
                                                                                    else {
                                                                                        return true;
                                                                                    }
                                                                                },
                                                                                depends: 'MainCurrencyType'
                                                                            }
                                                                        },
                                                                        pos: {width: 12},
                                                                    }
                                                                },
                                                                width: "16%"
                                                            },
                                                            {
                                                                title: "Main Currency",
                                                                data: "MainCurrencyType",
                                                                inline: {
                                                                    MainCurrencyType: {
                                                                        comp: {
                                                                            type: $pt.ComponentConstants.Check,
                                                                            label: false,
                                                                            enabled: false
                                                                        },
                                                                        pos: {width: 8},
                                                                    }
                                                                },
                                                                labelAttached: true,
                                                                width: "15%"
                                                            },
                                                            {
                                                                title: "Exchange Type",
                                                                data: "ExchangeType",
                                                                //inline: "select",
                                                                inline: {
                                                                    ExchangeType: {
                                                                        comp: {
                                                                            label: false,
                                                                            type: $pt.ComponentConstants.Select,
                                                                            data: $page.codes.ExchangeType,
                                                                            enabled: {
                                                                                when: function (model) {
                                                                                    if ((model.get('MainCurrencyType') == true)) {
                                                                                        //console.log(model.get('ExchangeType')+'>>>>>');
                                                                                        return false;
                                                                                        model.set('ExchangeType', '1');
                                                                                    }
                                                                                    else {
                                                                                        return true;
                                                                                    }
                                                                                },
                                                                                depends: 'MainCurrencyType'
                                                                            }
                                                                        },
                                                                        pos: {width: 12},
                                                                    }
                                                                },
                                                                codes: $page.codes.ExchangeType,
                                                                width: "15%"
                                                            },
                                                            {
                                                                title: "Rate",
                                                                data: "CurrencyRate",
                                                                //inline: "number",
                                                                inline: {
                                                                    CurrencyRate: {
                                                                        comp: {
                                                                            type:{label:false},
                                                                            enabled: {
                                                                                when: function (model) {
                                                                                    if (!model.get('MainCurrencyType') == true && model.get('ExchangeType') == 1) {
                                                                                        return true;
                                                                                    }
                                                                                    else {
                                                                                        return false;
                                                                                    }
                                                                                },
                                                                                depends: 'ExchangeType'
                                                                            }
                                                                        },
                                                                        base:$helper.baseAmount(3),
                                                                        pos: {width: 12},
                                                                    }
                                                                },
                                                                //render: function (model) {
                                                                //    return $helper.formatNumberForLabel(model.get("CurrencyRate"), 2);
                                                                //},
                                                                width: "13%"
                                                            },
                                                            {
                                                                title: "Date",
                                                                data: "CurrencyDate",
                                                                //inline: "DateFormate",
                                                                width: "13%",
                                                                inline: {
                                                                    CurrencyDate: {
                                                                        comp: {
                                                                            label: false,
                                                                            type: $pt.ComponentConstants.Date,
                                                                            format: "DD/MM/YYYY",
                                                                            enabled: {
                                                                                when: function (model) {
                                                                                    //if ((model.get('ExchangeType') != 2) ) {
                                                                                    //    //console.log(model.get('ExchangeType')+'>>>>>');
                                                                                    //    return false;
                                                                                    //}
                                                                                    //else if ((model.get('ExchangeType') == 2)) {
                                                                                    //    return true;
                                                                                    //}
                                                                                    if (!model.get('MainCurrencyType') == true && model.get('ExchangeType') == 2) {
                                                                                        return true;
                                                                                    }
                                                                                    else {
                                                                                        return false;
                                                                                    }

                                                                                },
                                                                                depends: 'ExchangeType'
                                                                            }
                                                                        },
                                                                        pos: {width: 12},
                                                                    }
                                                                },
                                                            },
                                                            {
                                                                title: "Remarks",
                                                                data: "CurrencyRemarks",
                                                                inline: "text",
                                                                codes: $page.codes.Boolean,
                                                                width: "25%"
                                                            }
                                                        ],
                                                        addClick: function (model) {
                                                            model.add("BusinessConditionVO_CurrencyList", $.extend(true, {}, $page.model.getCurrencyListTemplate));
                                                        },
                                                        // canRemove: function (model, item) {
                                                        //     if (item.CurrencyId && item.CurrencyId != 0) {
                                                        //         model.add("BusinessConditionVO_DeleteCurrencyList", item);
                                                        //     }
                                                        //     this.getModel().remove(this.getDataId(), item);
                                                        // }
                                                        rowOperations: [
                                                            {
                                                                icon: "trash",
                                                                text : "delete",
                                                                visible : {when : function(row){
                                                                  return row.get('MainCurrencyType') != true && !isReadOnly;
                                                                }},
                                                                click: function (row) {
                                                                  var removeRow = function (row) {
                                                                    if (row.CurrencyId && row.CurrencyId != 0) {
                                                                        this.getModel().add("BusinessConditionVO_DeleteCurrencyList", row);
                                                                    }
                                                                    this.getModel().remove(this.getDataId(), row);
                                                                        $pt.Components.NConfirm.getConfirmModal().hide();
                                                                    };
                                                                    $pt.Components.NConfirm.getConfirmModal().show(NTable.REMOVE_CONFIRM_TITLE, NTable.REMOVE_CONFIRM_MESSAGE, removeRow.bind(this, row));
                                                                }
                                                            }
                                                        ]
                                                    },
                                                    css: {
                                                        comp: "inline-editor",
                                                        cell: "title-align"
                                                    },
                                                    pos: {
                                                        width: 12,
                                                        row: 2
                                                    }
                                                }
                                            }
                                        },
                                        pos: {
                                            width: 12
                                        }
                                    },
                                    rightButtons: {
                                        comp: {
                                            type: $pt.ComponentConstants.ButtonFooter,
                                            buttonLayout: {
                                                right: [{
                                                    text: "Save",
                                                    visible: {
                                                        when: function (model) {
                                                            return model.get('BusinessConditionVO_OperateType') != '0' && model.get('BusinessConditionVO_OperateType') != '5';
                                                        }
                                                    },
                                                    style: "primary",
                                                    click: function (evt) {
                                                        $page.controller.save(true);
                                                    }
                                                }]
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
                }
            };
        },
        getInitialState: function () {
            return {
                editLayout: $pt.createFormLayout(this.getComponentOption('editLayout'))
            };
        },
        /**
         * will update
         * @param nextProps
         */
        componentWillUpdate: function (nextProps) {
            // remove post change listener to handle model change
            // this.removePostChangeListener(this.onModelChanged);
            // this.removeEnableDependencyMonitor();
        },
        /**
         * did update
         * @param prevProps
         * @param prevState
         */
        componentDidUpdate: function (prevProps, prevState) {
            // add post change listener to handle model change
            // this.addPostChangeListener(this.onModelChanged);
            // this.addEnableDependencyMonitor();
        },
        /**
         * did mount
         */
        componentDidMount: function () {
            // add post change listener to handle model change
            // this.addPostChangeListener(this.onModelChanged);
            // this.addEnableDependencyMonitor();
        },
        /**
         * will unmount
         */
        componentWillUnmount: function () {
            // remove post change listener to handle model change
            // this.removePostChangeListener(this.onModelChanged);
            // this.removeEnableDependencyMonitor();
        },

        render: function () {
            if (this.getModel().get('BusinessConditionVO_OperateType') == 0 || this.getModel().get('BusinessConditionVO_OperateType') == 5) {
                isReadOnly = true;
                return <NForm model={this.getModel()} layout={this.state.editLayout} view={true}/>;
            }
            return <NForm model={this.getModel()} layout={this.state.editLayout}/>;
        }
    }));
    context.Currency = Currency;
    NFormCell.registerComponentRenderer('Currency', function (model, layout, direction) {
        return <Currency model={model} layout={layout} direction={direction}/>;
    });
    $pt.ComponentConstants.Currency = {type: 'Currency', label: false, popover: false};
}(typeof window !== "undefined" ? window : this, jQuery, $pt));
