(function (context, $, $pt) {
    var limitType = $pt.createCodeTable([{id: '3', text: "XOL"}, {id: '4', text: "Stop Loss"}]);
    var amountTypeCodes = $pt.createCodeTable([{id: "1", text: "Amount"}, {id: "2", text: "Percentage"}]);
    var codes = $pt.getService(context, "codes");
    var $page= $pt.getService(context,'$page');
    var BCLimitNonProportion = React.createClass($pt.defineCellComponent({
        statics: {},
        propTypes: {
            // model
            model: React.PropTypes.object,
            // CellLayout
            layout: React.PropTypes.object
        },
        getDefaultProps: function () {
          var autoCalculation = [
            {id:'Liability',listener:function(evt){return $page.businessCalculator.calculationLimitAmount(evt)}},
            {id:'LiabilityWrittenShare',listener:function(evt){return $page.businessCalculator.calculationLimitWritten(evt)}},
            {id:'LiabilitySignedShare',listener:function(evt){return $page.businessCalculator.calculationLimitSigned(evt)}}
          ];
            return {
                defaultOptions: {
                    editLimitLayout: {
                        _sections: {
                            limit: {
                                label: "Limit",
                                style: 'primary-darken',
                                collapsible: true,
                                expanded: false,
                                pos: {
                                    width: 12
                                },
                                layout: {
                                    BusinessConditionVO_LimitType: {
                                        label: "Limit Type",
                                        comp: {
                                            type: $pt.ComponentConstants.Select,
                                            data: limitType
                                        }
                                    },
                                    BusinessConditionVO_PerRisk: {
                                        label: "Per Risk/Event",
                                        comp: {
                                            type: $pt.ComponentConstants.Select,
                                            data: $page.codes.PerRiskEvent,
                                            visible: {
                                                when: function (model) {
                                                    return model.get('BusinessConditionVO').LimitType == '3';
                                                }, depends: 'BusinessConditionVO_LimitType'
                                            }
                                        }
                                    },
                                    BusinessConditionVO_Unlimited: {
                                        label: "Unlimited",
                                        comp: {
                                            type: {type: $pt.ComponentConstants.Check, label: false},
                                            labelAttached: true,
                                            visible: {
                                                when: function (model) {
                                                    return model.get('BusinessConditionVO').LimitType == '3';
                                                }, depends: 'BusinessConditionVO_LimitType'
                                            }
                                        }
                                    },
                                    BusinessConditionVO_AmountType: {
                                        comp: {
                                            type: {type: $pt.ComponentConstants.Select, label: false},
                                            data: amountTypeCodes,
                                            visible: {
                                                when: function (model) {
                                                    return model.get('BusinessConditionVO').LimitType == '4';
                                                }, depends: 'BusinessConditionVO_LimitType'
                                            }
                                        },
                                        pos: {width: 2}
                                    },
                                    BusinessConditionVO_LimitRegularList: {
                                        comp: {
                                            type: $pt.ComponentConstants.Table,
                                            addable: true,
                                            removable: true,
                                            rowListener:[
                                                {id:'LimitLayer',listener:function(evt){return $page.businessCalculator.calculationPremiumAmount("LayerWrittenShare","LayerShare",evt)}},
                                                {id:'LayerWrittenShare',listener:function(evt){return $page.businessCalculator.calculationWrittenPremium("LimitLayer","LayerShare",evt)}},
                                                {id:'LayerShare',listener:function(evt){return $page.businessCalculator.calculationSignedPremium("LimitLayer","LayerWrittenShare",evt)}},
                                                {id:'Aal',listener:function(evt){return $page.businessCalculator.calculationPremiumAmount("AalWrittenShare","AalShare",evt)}},
                                                {id:'AalWrittenShare',listener:function(evt){return $page.businessCalculator.calculationWrittenPremium("Aal","AalShare",evt)}},
                                                {id:'AalShare',listener:function(evt){return $page.businessCalculator.calculationSignedPremium("Aal","AalWrittenShare",evt)}}
                                            ],
                                            sortable: false,
                                            visible: {
                                                when: function (model) {
                                                    return model.get('BusinessConditionVO').LimitType == '3';
                                                }, depends: 'BusinessConditionVO_LimitType'
                                            },
                                            searchable: false,
                                            addClick: function (model) {
                                                model.add("BusinessConditionVO_LimitRegularList", $.extend(true, {}, $page.model.getLimitRegularListTemplate()));
                                            },
                                            canRemove: function (model, item) {
                                                if (item.ItemId && item.ItemId != 0) {
                                                    model.add("BusinessConditionVO_DeleteLimitItemList", item);
                                                }
                                                this.getModel().remove(this.getDataId(), item);
                                            },
                                            columns: [
                                                {
                                                    title: "Currency",
                                                    data: "Currency",
                                                    inline: "select",
                                                    codes: $page.codes.Currency,
                                                    width: "10%"
                                                }, {
                                                    title: "Limit 100%",
                                                    data: "LimitLayer",
                                                    inline: $helper.registerInlineAmount("LimitLayer",2),
                                                    ////inline: "number",
                                                    //inline: {
                                                    //    LimitLayer: {
                                                    //        comp: {
                                                    //            type: {
                                                    //                label: false,
                                                    //                popover: false
                                                    //            },
                                                    //            format: $helper.formatNumber(2)
                                                    //        },
                                                    //        pos: {width: 12},
                                                    //        css: {cell: 'currency-align-right-text'}
                                                    //    }
                                                    //},
                                                    width: "8%"
                                                }, {
                                                    title: "Deductible",
                                                    data: "Deductible",
                                                    inline: $helper.registerInlineAmount("Deductible",2),
                                                    width: "8%"
                                                }, {
                                                    title: "AAD",
                                                    data: "Aad",
                                                    inline: $helper.registerInlineAmount("Aad",2),
                                                    width: "8%"
                                                }, {
                                                    title: "AAL 100%",
                                                    data: "Aal",
                                                    inline: $helper.registerInlineAmount("Aal",2),
                                                    width: "8%"
                                                }, {
                                                    title: "Limit Our Written Share",
                                                    data: "LayerWrittenShare",
                                                    inline: $helper.registerInlineAmount("LayerWrittenShare",2),
                                                    width: "10%"
                                                }, {
                                                    title: "Limit Our Signed Share",
                                                    data: "LayerShare",
                                                    inline: $helper.registerInlineAmount("LayerShare",2),
                                                    width: "10%"
                                                }, {
                                                    title: "AAL Our Written Share",
                                                    data: "AalWrittenShare",
                                                    inline: $helper.registerInlineAmount("AalWrittenShare",2),
                                                    width: "10%"
                                                }, {
                                                    title: "AAL Our Signed Share",
                                                    data: "AalShare",
                                                    inline: $helper.registerInlineAmount("AalShare",2),
                                                    width: "10%"
                                                }, {
                                                    title: "Remarks",
                                                    data: "Remark",
                                                    inline: "text",
                                                    width: "18%"
                                                }
                                            ]
                                        },
                                        css:{
                                            comp: "inline-editor",
                                            cell: "title-align"
                                        },
                                        pos: {
                                            width: 12
                                        }
                                    },
                                    BusinessConditionVO_LimitStopList: {
                                        comp: {
                                            type: $pt.ComponentConstants.Table,
                                            addable: true,
                                            removable: true,
                                            searchable: false,
                                            sortable: false,
                                            addClick: function (model) {
                                                model.add("BusinessConditionVO_LimitStopList", $.extend(true, {}, $page.model.getLimitStopListTemplate()));
                                            },
                                            canRemove: function (model, item) {
                                                if (item.ItemId && item.ItemId != 0) {
                                                    model.add("BusinessConditionVO_DeleteLimitItemList", item);
                                                }
                                                this.getModel().remove(this.getDataId(), item);
                                            },
                                            visible: {
                                                when: function (model) {
                                                    return this.getModel().get('BusinessConditionVO').LimitType != '3' && this.getModel().get('BusinessConditionVO').LimitType == '4'&& model.get('BusinessConditionVO').AmountType == '1';
                                                   // return model.get('BusinessConditionVO').AmountType == '1';
                                                }, depends: ['BusinessConditionVO_LimitType', 'BusinessConditionVO_AmountType']
                                            },
                                            columns: [
                                                {
                                                    title: "Currency",
                                                    data: "Currency",
                                                    inline: "select",
                                                    codes: $page.codes.Currency,
                                                    width: "16%"
                                                }, {
                                                    title: "Limit",
                                                    data: "LimitLayer",
                                                    inline: $helper.registerInlineAmount("LimitLayer",2),
                                                    width: "16%"
                                                }, {
                                                    title: "Deductible",
                                                    data: "Deductible",
                                                    inline: $helper.registerInlineAmount("Deductible",2),
                                                    width: "16%"
                                                }, {
                                                    title: "Min",
                                                    data: "LimitMin",
                                                    inline: $helper.registerInlineAmount("LimitMin",2),
                                                    width: "16%"
                                                }, {
                                                    title: "Max",
                                                    data: "LimitMax",
                                                    inline: $helper.registerInlineAmount("LimitMax",2),
                                                    width: "16%"
                                                }, {
                                                    title: "Remarks",
                                                    data: "Remark",
                                                    inline: "text",
                                                    width: "20%"
                                                }
                                            ]
                                        },
                                        css:{
                                            comp: "inline-editor",
                                            cell: "title-align"
                                        },
                                        pos: {
                                            width: 12
                                        }
                                    },
                                    BusinessConditionVO_LimitStopPerList: {
                                        comp: {
                                            type: $pt.ComponentConstants.Table,
                                            addable: true,
                                            removable: true,
                                            searchable: false,
                                            sortable: false,
                                            addClick: function (model) {
                                                model.add("BusinessConditionVO_LimitStopPerList", $.extend(true, {}, $page.model.getLimitStopPerListTemplate()));
                                            },
                                            canRemove: function (model, item) {
                                                if (item.ItemId && item.ItemId != 0) {
                                                    model.add("BusinessConditionVO_DeleteLimitItemList", item);
                                                }
                                                this.getModel().remove(this.getDataId(), item);
                                            },
                                            visible: {
                                                when: function (model) {
                                                    return this.getModel().get('BusinessConditionVO').LimitType != '3' && this.getModel().get('BusinessConditionVO').LimitType == '4'&& model.get('BusinessConditionVO').AmountType == '2';
                                                    //return model.get('BusinessConditionVO').AmountType == '2';
                                                }, depends: ['BusinessConditionVO_LimitType', 'BusinessConditionVO_AmountType']
                                            },
                                            columns: [
                                                {
                                                    title: "Currency",
                                                    data: "Currency",
                                                    inline: "select",
                                                    codes: $page.codes.Currency,
                                                    width: "16%"
                                                }, {
                                                    title: "Limit",
                                                    data: "LimitLayerPer",
                                                    //inline: "percentage",
                                                    inline:$helper.registerInlinePercentage("LimitLayerPer",2),
                                                    width: "16%"
                                                }, {
                                                    title: "Deductible",
                                                    data: "DeductiblePer",
                                                    //inline: "percentage",
                                                    inline:$helper.registerInlinePercentage("DeductiblePer",2),
                                                    width: "16%"
                                                }, {
                                                    title: "Min",
                                                    data: "LimitMin",
                                                    inline: $helper.registerInlineAmount("LimitMin",2),
                                                    width: "16%"
                                                }, {
                                                    title: "Max",
                                                    data: "LimitMax",
                                                    inline: $helper.registerInlineAmount("LimitMax",2),
                                                    width: "16%"
                                                }, {
                                                    title: "Remarks",
                                                    data: "Remark",
                                                    inline: "text",
                                                    width: "20%"
                                                }
                                            ]
                                        },
                                        css:{
                                            comp: "inline-editor",
                                            cell: "title-align"
                                        },
                                        pos: {
                                            width: 12
                                        }
                                    },
                                    BusinessConditionVO_LimitEventXolList: {
                                        comp: {
                                            type: $pt.ComponentConstants.Table,
                                            rowListener:[
                                              {id:'LimitHundred',listener:function(evt){return $page.businessCalculator.calculationPremiumAmount("WrittenShare","LimitShare",evt)}},
                                              {id:'WrittenShare',listener:function(evt){return $page.businessCalculator.calculationWrittenPremium("LimitHundred","LimitShare",evt)}},
                                              {id:'LimitShare',listener:function(evt){return $page.businessCalculator.calculationSignedPremium("LimitHundred","WrittenShare",evt)}}
                                            ],
                                            addable: true,
                                            removable: true,
                                            searchable: false,
                                            sortable: false,
                                            addClick: function (model) {
                                                model.add("BusinessConditionVO_LimitEventXolList", $.extend(true, {}, $page.model.getLimitEventXolListTemplate()));
                                            },
                                            canRemove: function (model, item) {
                                                if (item.EventId && item.EventId != 0) {
                                                    model.add("BusinessConditionVO_DeleteLimitEventList", item);
                                                }
                                                this.getModel().remove(this.getDataId(), item);
                                            },
                                            visible: {
                                                when: function (model) {
                                                    return model.get('BusinessConditionVO').LimitType == '3'
                                                }, depends: 'BusinessConditionVO_LimitType'
                                            },
                                            columns: [
                                                {
                                                    title: "Currency",
                                                    data: "Currency",
                                                    inline: "select",
                                                    codes: $page.codes.Currency,
                                                    width: "16%"
                                                },
                                                {
                                                    title: "Event",
                                                    data: "Event",
                                                    inline: "select",
                                                    codes: $page.codes.LimitPeril,
                                                    width: "16%"
                                                },
                                                {
                                                    title: "Limit in 100%",
                                                    data: "LimitHundred",
                                                    inline: $helper.registerInlineAmount("LimitHundred",2),
                                                    width: "16%"
                                                }, {
                                                    title: "Limit Our Written Share",
                                                    data: "WrittenShare",
                                                    inline: $helper.registerInlineAmount("WrittenShare",2),
                                                    width: "16%"
                                                },
                                                {
                                                    title: "Limit Our Signed Share",
                                                    data: "LimitShare",
                                                    inline: $helper.registerInlineAmount("LimitShare",2),
                                                    width: "16%"
                                                },

                                                {
                                                    title: "Remarks",
                                                    data: "CommRemark",
                                                    inline: "text",
                                                    width: "16%"
                                                }
                                            ]
                                        },
                                        css:{
                                            comp: "inline-editor",
                                            cell: "title-align"
                                        },
                                        pos: {
                                            width: 12
                                        }
                                    },
                                    BusinessConditionVO_LimitEventStoplossList: {
                                        comp: {
                                            type: $pt.ComponentConstants.Table,
                                            rowListener:[
                                              {id:'LimitHundred',listener:function(evt){return $page.businessCalculator.calculationPremiumAmount("WrittenShare","LimitShare",evt)}},
                                              {id:'WrittenShare',listener:function(evt){return $page.businessCalculator.calculationWrittenPremium("LimitHundred","LimitShare",evt)}},
                                              {id:'LimitShare',listener:function(evt){return $page.businessCalculator.calculationSignedPremium("LimitHundred","WrittenShare",evt)}}
                                            ],
                                            addable: true,
                                            removable: true,
                                            searchable: false,
                                            sortable: false,
                                            addClick: function (model) {
                                                model.add("BusinessConditionVO_LimitEventStoplossList", $.extend(true, {}, $page.model.getLimitEventStoplossListTemplate()));
                                            },
                                            canRemove: function (model, item) {
                                                if (item.EventId && item.EventId != 0) {
                                                    model.add("BusinessConditionVO_DeleteLimitEventList", item);
                                                }
                                                this.getModel().remove(this.getDataId(), item);
                                            },
                                            visible: {
                                                when: function (model) {
                                                    return model.get('BusinessConditionVO').LimitType == '4'
                                                }, depends: 'BusinessConditionVO_LimitType'
                                            },
                                            columns: [
                                                {
                                                    title: "Currency",
                                                    data: "Currency",
                                                    inline: "select",
                                                    codes: $page.codes.Currency,
                                                    width: "16%"
                                                },
                                                {
                                                    title: "Event",
                                                    data: "Event",
                                                    inline: "select",
                                                    codes: $page.codes.LimitPeril,
                                                    width: "16%"
                                                },
                                                {
                                                    title: "Limit in 100%",
                                                    data: "LimitHundred",
                                                    inline: $helper.registerInlineAmount("LimitHundred",2),
                                                    width: "16%"
                                                }, {
                                                    title: "Limit Our Written Share",
                                                    data: "WrittenShare",
                                                    inline: $helper.registerInlineAmount("WrittenShare",2),
                                                    width: "16%"
                                                },
                                                {
                                                    title: "Limit Our Signed Share",
                                                    data: "LimitShare",
                                                    inline: $helper.registerInlineAmount("LimitShare",2),
                                                    width: "16%"
                                                },

                                                {
                                                    title: "Remarks",
                                                    data: "CommRemark",
                                                    inline: "text",
                                                    width: "16%"
                                                }
                                            ]
                                        },
                                        css:{
                                            comp: "inline-editor",
                                            cell: "title-align"
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
                                                    style: "primary",
                                                    visible: {
                                                        when: function (model) {
                                                            return model.get('BusinessConditionVO_OperateType') != '0' && model.get('BusinessConditionVO_OperateType') != '5';
                                                        }
                                                    },
                                                    click: function () {
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
                editLimitLayout: $pt.createFormLayout(this.getComponentOption('editLimitLayout'))
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
                return <NForm model={this.getModel()} layout={this.state.editLimitLayout} view={true}/>;
            }
            return <NForm model={this.getModel()} layout={this.state.editLimitLayout}/>;
        }
    }));
    context.BCLimitNonProportion = BCLimitNonProportion;
    NFormCell.registerComponentRenderer('BCLimitNonProportion', function (model, layout, direction) {
        return <BCLimitNonProportion model={model} layout={layout} direction={direction}/>;
    });
    $pt.ComponentConstants.BCLimitNonProportion = {type: 'BCLimitNonProportion', label: false, popover: false};
}(typeof window !== "undefined" ? window : this, jQuery, $pt));
