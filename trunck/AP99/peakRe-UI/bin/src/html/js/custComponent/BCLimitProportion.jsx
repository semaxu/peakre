(function (context, $, $pt) {
    var limitType = $pt.createCodeTable([{id: '1', text: "QS"}, {id: '2', text: "Surplus"},{id: '7', text: "Combined QS&SPL"}]);
    var baseType = $pt.createCodeTable([{id: '1', text: "PML"}, {id: '2', text: "Sum Insured"}]);

    var codes = $pt.getService(context, "codes");
    var $page= $pt.getService(context,'$page');
    var BCLimitProportion = React.createClass($pt.defineCellComponent({
        statics: {},
        propTypes: {
            // model
            model: React.PropTypes.object,
            // CellLayout
            layout: React.PropTypes.object
        },
        getDefaultProps: function () {
          var autoCalculation = [
            {id:'Liability',listener:function(evt){return $page.businessCalculator.calculationPremiumAmount("LiabilityWrittenShare","LiabilitySignedShare",evt)}},
            {id:'LiabilityWrittenShare',listener:function(evt){return $page.businessCalculator.calculationWrittenPremium("Liability","LiabilitySignedShare",evt)}},
            {id:'LiabilitySignedShare',listener:function(evt){return $page.businessCalculator.calculationSignedPremium("Liability","LiabilityWrittenShare",evt)}}
          ];
            return {
                defaultOptions: {
                    editLimitLayout: {
                        _sections: {
                            Limit: {
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
                                    qsPanel: {
                                        comp: {
                                            type: $pt.ComponentConstants.Panel,
                                            visible: {
                                                when: function (model) {
                                                    return model.get('BusinessConditionVO').LimitType == '1';
                                                }, depends: 'BusinessConditionVO_LimitType'
                                            },
                                            editLayout: {
                                                BusinessConditionVO_LimitQsList: {
                                                    comp: {
                                                        type: $pt.ComponentConstants.Table,
                                                        rowListener:autoCalculation,
                                                        addable: true,
                                                        removable: true,
                                                        searchable: false,
                                                        sortable: false,
                                                        addClick: function (model) {
                                                            model.add("BusinessConditionVO_LimitQsList",   $.extend(true, {}, $page.model.getLimitQsListTemplate()));
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
                                                                width: "12%"
                                                            }, {
                                                                title: "Sum Insured",
                                                                data: "SumInsured",
                                                                inline : $helper.registerInlineAmount("SumInsured",2),
                                                                width: "12%"
                                                            }, {
                                                                title: "QS%",
                                                                data: "Qs",
                                                                //inline: "percentage",
                                                                inline:$helper.registerInlinePercentage("Qs",2),

                                                                width: "8%"
                                                            }, {
                                                                title: "Liability 100%",
                                                                data: "Liability",
                                                                inline : $helper.registerInlineAmount("Liability",2),
                                                                width: "20%"
                                                            }, {
                                                                title: "Liability Our Written Share",
                                                                data: "LiabilityWrittenShare",
                                                                inline : $helper.registerInlineAmount("LiabilityWrittenShare",2),
                                                                width: "20%"
                                                            },{
                                                                title: "Liability Our Signed Share",
                                                                data: "LiabilitySignedShare",
                                                                inline : $helper.registerInlineAmount("LiabilitySignedShare",2),
                                                                width: "20%"
                                                            },   {
                                                                title: "Remarks",
                                                                data: "Remark",
                                                                inline: "text",
                                                                width: "8%"
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
                                                BusinessConditionVO_LimitEventQsList: {
                                                    comp: {
                                                        type: $pt.ComponentConstants.Table,
                                                        rowListener:autoCalculation,
                                                        addable: true,
                                                        removable: true,
                                                        searchable: false,
                                                        sortable: false,
                                                        addClick: function (model) {
                                                            model.add("BusinessConditionVO_LimitEventQsList",   $.extend(true, {}, $page.model.getLimitEventQsListTemplate()));
                                                        },
                                                        canRemove: function (model, item) {
                                                            if (item.EventId && item.EventId != 0) {
                                                                model.add("BusinessConditionVO_DeleteLimitEventList", item);
                                                            }
                                                            this.getModel().remove(this.getDataId(), item);
                                                        },
                                                        columns: [
                                                            {
                                                                title: "Currency",
                                                                data: "Currency",
                                                                inline: "select",
                                                                codes: $page.codes.Currency,
                                                                width: "12%"
                                                            },
                                                            {
                                                                title: "Event",
                                                                data: "Event",
                                                                inline: "select",
                                                                codes: $page.codes.LimitPeril,
                                                                width: "12%"
                                                            },
                                                            {
                                                                title: "Liability 100%",
                                                                data: "Liability",
                                                                inline : $helper.registerInlineAmount("Liability",2),
                                                                width: "21%"
                                                            },{
                                                                title: "Liability Our Written Share",
                                                                data: "LiabilityWrittenShare",
                                                                inline : $helper.registerInlineAmount("LiabilityWrittenShare",2),
                                                                width: "21%"
                                                            },
                                                            {
                                                                title: "Liability Our Signed Share",
                                                                data: "LiabilitySignedShare",
                                                                inline : $helper.registerInlineAmount("LiabilitySignedShare",2),
                                                                width: "21%"
                                                            },

                                                            {
                                                                title: "Remarks",
                                                                data: "CommRemark",
                                                                inline: "text",
                                                                width: "12%"
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
                                                }
                                            }
                                        },
                                        pos: {
                                            width: 12
                                        }
                                    },
                                    spPanel: {
                                        comp: {
                                            type: $pt.ComponentConstants.Panel,
                                            visible: {
                                                when: function (model) {
                                                    return model.get('BusinessConditionVO').LimitType == '2';
                                                }, depends: 'BusinessConditionVO_LimitType'
                                            },
                                            editLayout: {
                                                BusinessConditionVO_LimitSurplusList: {
                                                    comp: {
                                                        type: $pt.ComponentConstants.Table,
                                                        rowListener:autoCalculation,
                                                        addable: true,
                                                        removable: true,
                                                        searchable: false,
                                                        sortable: false,
                                                        addClick: function (model) {
                                                            model.add("BusinessConditionVO_LimitSurplusList",   $.extend(true, {}, $page.model.getLimitSurplusListTemplate()));
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
                                                                width: "12%"
                                                            }, {
                                                                title: "Retention",
                                                                data: "Retente",
                                                                inline:$helper.registerInlineAmount("Retente",2),
                                                                width: "12%"
                                                            }, {
                                                                title: "No.of Lines",
                                                                data: "NoOfLines",
                                                                // inline: "text",
                                                                inline: {
                                                                    NoOfLines: {
                                                                        comp: {
                                                                            type:{label:false}
                                                                        },
                                                                        base:$helper.baseNumber(),
                                                                        pos: {width: 12}
                                                                    }
                                                                },
                                                                width: "12%"
                                                            }, {
                                                                title: "Liability 100%",
                                                                data: "Liability",
                                                                inline : $helper.registerInlineAmount("Liability",2),
                                                                width: "12%"
                                                            },{
                                                                title: "Liability Our Written Share",
                                                                data: "LiabilityWrittenShare",
                                                                inline : $helper.registerInlineAmount("LiabilityWrittenShare",2),
                                                                width: "16%"
                                                            },{
                                                                title: "Liability Our Signed Share",
                                                                data: "LiabilitySignedShare",
                                                                inline : $helper.registerInlineAmount("LiabilitySignedShare",2),
                                                                width: "16%"
                                                            },  {
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
                                                BusinessConditionVO_LimitEventSurplusList: {
                                                    comp: {
                                                        type: $pt.ComponentConstants.Table,
                                                        rowListener:autoCalculation,
                                                        addable: true,
                                                        removable: true,
                                                        searchable: false,
                                                        sortable: false,
                                                        addClick: function (model) {
                                                            model.add("BusinessConditionVO_LimitEventSurplusList",  $.extend(true, {}, $page.model.getLimitEventSurplusListTemplate()));
                                                        },
                                                        canRemove: function (model, item) {
                                                            if (item.EventId && item.EventId != 0) {
                                                                model.add("BusinessConditionVO_DeleteLimitEventList", item);
                                                            }
                                                            this.getModel().remove(this.getDataId(), item);
                                                        },
                                                        columns: [
                                                            {
                                                                title: "Currency",
                                                                data: "Currency",
                                                                inline: "select",
                                                                codes: $page.codes.Currency,
                                                                width: "12%"
                                                            },
                                                            {
                                                                title: "Event",
                                                                data: "Event",
                                                                inline: "select",
                                                                codes: $page.codes.LimitPeril,
                                                                width: "12%"
                                                            },
                                                            {
                                                                title: "Liability 100%",
                                                                data: "Liability",
                                                                inline : $helper.registerInlineAmount("Liability",2),
                                                                width: "12%"
                                                            },{
                                                                title: "Liability Our Written Share",
                                                                data: "LiabilityWrittenShare",
                                                                inline : $helper.registerInlineAmount("LiabilityWrittenShare",2),
                                                                width: "21%"
                                                            },
                                                            {
                                                                title: "Liability Our Signed Share",
                                                                data: "LiabilitySignedShare",
                                                                inline : $helper.registerInlineAmount("LiabilitySignedShare",2),
                                                                width: "21%"
                                                            },
                                                            {
                                                                title: "Remarks",
                                                                data: "CommRemark",
                                                                inline: "text",
                                                                width: "12%"
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
                                                }
                                            }
                                        },
                                        pos: {
                                            width: 12
                                        }
                                    },
                                    cbPanel: {
                                        comp: {
                                            type: $pt.ComponentConstants.Panel,
                                            visible: {
                                                when: function (model) {
                                                    return model.get('BusinessConditionVO').LimitType == '7';
                                                }, depends: 'BusinessConditionVO_LimitType'
                                            },
                                            editLayout: {
                                                BusinessConditionVO_LimitCbList: {
                                                    comp: {
                                                        type: $pt.ComponentConstants.Table,
                                                        rowListener:autoCalculation,
                                                        addable: true,
                                                        removable: true,
                                                        searchable: false,
                                                        sortable: false,
                                                        addClick: function (model) {
                                                            model.add("BusinessConditionVO_LimitCbList",   $.extend(true, {}, $page.model.getLimitCbListTemplate()));
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
                                                                width: "12%"
                                                            }, {
                                                                title: "Sum Insured",
                                                                data: "SumInsured",
                                                                inline : $helper.registerInlineAmount("SumInsured",2),
                                                                width: "12%"
                                                            }, {
                                                                title: "QS%",
                                                                data: "Qs",
                                                                //inline: "percentage",
                                                                inline:$helper.registerInlinePercentage("Qs",2),

                                                                width: "8%"
                                                            },
                                                            {
                                                                title: "No.of Lines",
                                                                data: "NoOfLines",
                                                                // inline: "text",
                                                                inline: {
                                                                    NoOfLines: {
                                                                        comp: {
                                                                            type:{label:false}
                                                                        },
                                                                        base:$helper.baseNumber(),
                                                                        pos: {width: 12}
                                                                    }
                                                                },
                                                                width: "12%"
                                                            },
                                                            {
                                                                title: "Liability 100%",
                                                                data: "Liability",
                                                                inline : $helper.registerInlineAmount("Liability",2),
                                                                width: "20%"
                                                            }, {
                                                                title: "Liability Our Written Share",
                                                                data: "LiabilityWrittenShare",
                                                                inline : $helper.registerInlineAmount("LiabilityWrittenShare",2),
                                                                width: "20%"
                                                            },{
                                                                title: "Liability Our Signed Share",
                                                                data: "LiabilitySignedShare",
                                                                inline : $helper.registerInlineAmount("LiabilitySignedShare",2),
                                                                width: "20%"
                                                            },   {
                                                                title: "Remarks",
                                                                data: "Remark",
                                                                inline: "text",
                                                                width: "8%"
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
                                                BusinessConditionVO_LimitEventCbList: {
                                                    comp: {
                                                        type: $pt.ComponentConstants.Table,
                                                        rowListener:autoCalculation,
                                                        addable: true,
                                                        removable: true,
                                                        searchable: false,
                                                        sortable: false,
                                                        addClick: function (model) {
                                                            model.add("BusinessConditionVO_LimitEventCbList",   $.extend(true, {}, $page.model.getLimitEventCbListTemplate()));
                                                        },
                                                        canRemove: function (model, item) {
                                                            if (item.EventId && item.EventId != 0) {
                                                                model.add("BusinessConditionVO_DeleteLimitEventList", item);
                                                            }
                                                            this.getModel().remove(this.getDataId(), item);
                                                        },
                                                        columns: [
                                                            {
                                                                title: "Currency",
                                                                data: "Currency",
                                                                inline: "select",
                                                                codes: $page.codes.Currency,
                                                                width: "12%"
                                                            },
                                                            {
                                                                title: "Event",
                                                                data: "Event",
                                                                inline: "select",
                                                                codes: $page.codes.LimitPeril,
                                                                width: "12%"
                                                            },
                                                            {
                                                                title: "Liability 100%",
                                                                data: "Liability",
                                                                inline : $helper.registerInlineAmount("Liability",2),
                                                                width: "21%"
                                                            },{
                                                                title: "Liability Our Written Share",
                                                                data: "LiabilityWrittenShare",
                                                                inline : $helper.registerInlineAmount("LiabilityWrittenShare",2),
                                                                width: "21%"
                                                            },
                                                            {
                                                                title: "Liability Our Signed Share",
                                                                data: "LiabilitySignedShare",
                                                                inline : $helper.registerInlineAmount("LiabilitySignedShare",2),
                                                                width: "21%"
                                                            },

                                                            {
                                                                title: "Remarks",
                                                                data: "CommRemark",
                                                                inline: "text",
                                                                width: "12%"
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
    context.BCLimitProportion = BCLimitProportion;
    NFormCell.registerComponentRenderer('BCLimitProportion', function (model, layout, direction) {
        return <BCLimitProportion model={model} layout={layout} direction={direction}/>;
    });
    $pt.ComponentConstants.BCLimitProportion = {type: 'BCLimitProportion', label: false, popover: false};
}(typeof window !== "undefined" ? window : this, jQuery, $pt));
