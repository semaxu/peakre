(function (context, $, $pt) {
    var $page = $pt.getService(context, '$page');
    var Reinstatement = React.createClass($pt.defineCellComponent({
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
                            Reinstatement: {
                                label: "Reinstatement",
                                style: 'primary-darken',
                                collapsible: true,
                                expanded: false,
                                layout: {
                                    typeOfReinstatementPanel: {
                                        // label:"Type of Reinstatement",
                                        comp: {
                                            type: $pt.ComponentConstants.Panel,
                                            editLayout: {
                                                BusinessConditionVO_ReinType: {
                                                    label: "Type of Reinstatement",
                                                    comp: {
                                                        type: $pt.ComponentConstants.Radio,
                                                        data: $page.codes.TypeOfReinstatement,
                                                        labelWidth: 3
                                                    },
                                                    pos: {
                                                        width: 10
                                                    }
                                                },
                                                ReinNum: {
                                                    comp: {
                                                        type: $pt.ComponentConstants.Panel,
                                                        visible: {
                                                            when: function (model) {
                                                                return model.get('BusinessConditionVO').ReinType != '1'
                                                            }, depends: 'BusinessConditionVO_ReinType'
                                                        },
                                                        editLayout: {
                                                            BusinessConditionVO_ReinNum: {
                                                                label: "Select Number of Reinstatement",
                                                                comp: {
                                                                    visible: {
                                                                        when: function (model) {
                                                                            return model.get('BusinessConditionVO').ReinType == '2'
                                                                        }, depends: 'BusinessConditionVO_ReinType'
                                                                    },
                                                                    labelWidth: 8,
                                                                },
                                                                base : $helper.baseNumber(),
                                                                pos: {
                                                                    width: 4,
                                                                    row: 1
                                                                }
                                                            },
                                                            BusinessConditionVO_ReinstateSpecificList: {
                                                                // label:"Reinstatement",
                                                                comp: {
                                                                    type: $pt.ComponentConstants.Table,
                                                                    // editable: true,
                                                                    removable: true,
                                                                    addable: true,
                                                                    searchable: false,
                                                                    sortable: false,
                                                                    visible: {
                                                                        when: function (model) {
                                                                            return model.get('BusinessConditionVO').ReinType == '2'
                                                                        }, depends: 'BusinessConditionVO_ReinType'
                                                                    },
                                                                    columns: [
                                                                        {
                                                                            title: "Reinstatement",
                                                                            data: "Reinstate",
                                                                            // inline: $helper.baseNumber(),
                                                                            inline: "integer",
                                                                            width: "25%",
                                                                            maxlength: 2
                                                                        },
                                                                        {
                                                                            title: "Rate",
                                                                            data: "ReinRate",
                                                                            //inline: "percentage",
                                                                            inline: $helper.registerInlinePercentage("ReinRate", 2),
                                                                            width: "25%"
                                                                        },
                                                                        {
                                                                            title: "Pro Rata to Time",
                                                                            data: "ReinTime",
                                                                            inline: "select",
                                                                            codes: $page.codes.Boolean,
                                                                            width: "25%"
                                                                        },
                                                                        {
                                                                            title: "Pro Rata to Amount",
                                                                            data: "ReinAmount",
                                                                            inline: "select",
                                                                            codes: $page.codes.Boolean,
                                                                            width: "25%"
                                                                        }
                                                                    ],
                                                                    addClick: function (model) {
                                                                        console.log(model.get('BusinessConditionVO').ReinType);
                                                                            model.add("BusinessConditionVO_ReinstateSpecificList", $.extend(true, {}, $page.model.getReinstateSpecificListTemplate()));

                                                                    },
                                                                    canRemove: function (model, item) {
                                                                        if (item.ItemId && item.ItemId != 0) {
                                                                            model.add("BusinessConditionVO_DeleteReinstateItemList", item);
                                                                        }
                                                                        this.getModel().remove(this.getDataId(), item);
                                                                    }
                                                                },
                                                                css: {
                                                                    cell: "title-align"
                                                                },
                                                                pos: {
                                                                    width: 12,
                                                                    row: 2
                                                                }
                                                            },
                                                            BusinessConditionVO_ReinstateUnlimitedList: {
                                                                // label:"Reinstatement",
                                                                comp: {
                                                                    type: $pt.ComponentConstants.Table,
                                                                    // editable: true,
                                                                    removable: true,
                                                                    addable: true,
                                                                    searchable: false,
                                                                    sortable: false,
                                                                    visible: {
                                                                        when: function (model) {
                                                                            return  model.get('BusinessConditionVO').ReinType == '3'
                                                                        }, depends: 'BusinessConditionVO_ReinType'
                                                                    },
                                                                    columns: [
                                                                        {
                                                                            title: "Reinstatement",
                                                                            data: "Reinstate",
                                                                            inline: "integer",
                                                                            width: "25%",
                                                                            maxlength: 2
                                                                        },
                                                                        {
                                                                            title: "Rate",
                                                                            data: "ReinRate",
                                                                            //inline: "percentage",
                                                                            inline: $helper.registerInlinePercentage("ReinRate", 2),
                                                                            width: "25%"
                                                                        },
                                                                        {
                                                                            title: "Pro Rata to Time",
                                                                            data: "ReinTime",
                                                                            inline: "select",
                                                                            codes: $page.codes.Boolean,
                                                                            width: "25%"
                                                                        },
                                                                        {
                                                                            title: "Pro Rata to Amount",
                                                                            data: "ReinAmount",
                                                                            inline: "select",
                                                                            codes: $page.codes.Boolean,
                                                                            width: "25%"
                                                                        }
                                                                    ],
                                                                    addClick: function (model) {
                                                                        model.add("BusinessConditionVO_ReinstateUnlimitedList", $.extend(true, {}, $page.model.getReinstateUnlimitedListTemplate()));
                                                                    },
                                                                    canRemove: function (model, item) {
                                                                        if (item.ItemId && item.ItemId != 0) {
                                                                            model.add("BusinessConditionVO_DeleteReinstateItemList", item);
                                                                        }
                                                                        this.getModel().remove(this.getDataId(), item);
                                                                    }
                                                                },
                                                                css: {
                                                                    cell: "title-align"
                                                                },
                                                                pos: {
                                                                    width: 12,
                                                                    row: 2
                                                                }
                                                            },

                                                            BusinessConditionVO_ReinCurrency: {
                                                                label: "Reinstatement Currency",
                                                                comp: {
                                                                    type: $pt.ComponentConstants.Select,
                                                                    data: $page.codes.Currency,
                                                                    visible: {
                                                                        depends: 'BusinessConditionVO_ReinType',
                                                                        when: function (model) {
                                                                            return model.get('BusinessConditionVO').ReinType == '2' || model.get('BusinessConditionVO').ReinType == '3'
                                                                        }
                                                                    }
                                                                }
                                                            },
                                                            BusinessConditionVO_ExchCalc: {
                                                                label: "Exchange Calculation",
                                                                comp: {
                                                                    type: $pt.ComponentConstants.Select,
                                                                    data: $page.codes.ExchangeType,
                                                                    visible: {
                                                                        depends: 'BusinessConditionVO_ReinType',
                                                                        when: function (model) {
                                                                            return model.get('BusinessConditionVO').ReinType == '2' || model.get('BusinessConditionVO').ReinType == '3'
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
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
                return <NForm model={this.getModel()} layout={this.state.editLayout} view={true}/>;
            }
            return <NForm model={this.getModel()} layout={this.state.editLayout}/>;
        }
    }));
    context.Reinstatement = Reinstatement;
    NFormCell.registerComponentRenderer('Reinstatement', function (model, layout, direction) {
        return <Reinstatement model={model} layout={layout} direction={direction}/>;
    });
    $pt.ComponentConstants.Reinstatement = {type: 'Reinstatement', label: false, popover: false};
}(typeof window !== "undefined" ? window : this, jQuery, $pt));
