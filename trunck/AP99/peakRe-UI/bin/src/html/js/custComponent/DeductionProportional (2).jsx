(function (context, $, $pt) {
    var DeductionProportional = React.createClass($pt.defineCellComponent({
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
                            ClaimSec: {
                                label: "Deductions",
                                style: 'primary-darken',
                                collapsible: true,
                                expanded: false,
                                pos: {
                                    width: 12
                                },
                                layout: {
                                    BusinessConditionVO_RICommission: {
                                        label: "RI Commission",
                                        comp: {
                                            type: $pt.ComponentConstants.Select,
                                            data: $page.codes.RICommission
                                        },
                                        pos: {
                                            row: 1,
                                            width: 6
                                        }
                                    },
                                    BusinessConditionVO_RiPercentage: {
                                       comp: {
                                            type: {type: $pt.ComponentConstants.Text, label: false},
                                          
                                        },
                                        base : $helper.basePercentage(2),
                                        pos: {
                                            row: 1,
                                            width: 2
                                        }
                                    },
                                    CommSlidingDetail: {
                                        label: "Comm Sliding Detail",
                                        comp: {
                                            type: $pt.ComponentConstants.Button,
                                            style: "link",
                                            click: function (model) {
                                                if (this.getModel().get("BusinessConditionVO_OperateType") != 0) {
                                                    if (!$page.controller.save(false)) {
                                                        return;
                                                    }
                                                }
                                                var url = "commSlidingDetail.html?DeductionsId=" + model.get("BusinessConditionVO_DeductionsId")
                                                    + "&OperateType=" + model.get("BusinessConditionVO_OperateType");
                                                if(model.get("BusinessConditionVO_OperateType") == 3 && model.get("EndoType") == 3){
                                                    url = url + "&EndoType=" + model.get("EndoType");
                                                }
                                                if(model.get("OperateId")){
                                                    url += "&OperateId=" + model.get("OperateId");
                                                }
                                                window.open(url);
                                            }
                                        },
                                        pos: {
                                            row: 1,
                                            width: 4
                                        },
                                        css: {
                                            comp: 'pull-right'
                                        }
                                    },
                                    ProfitCommission: {
                                        label: "Profit Commission",
                                        comp: {
                                            type: $pt.ComponentConstants.Panel,
                                            editLayout: {
                                                BusinessConditionVO_ProfitPercentageType: {
                                                    label: "Percentage",
                                                    comp: {
                                                        type: $pt.ComponentConstants.Select,
                                                        data: $page.codes.RICommission
                                                    },
                                                    pos: {
                                                        row: 1,
                                                        width: 6
                                                    }
                                                },
                                                BusinessConditionVO_ProfitPercentage: {

                                                    base : $helper.basePercentage(2),
                                                    pos: {
                                                        row: 1,
                                                        width: 2
                                                    }
                                                },
                                                ViewContractButton2: {
                                                    label: "PC Sliding Detail",
                                                    comp: {
                                                        type: $pt.ComponentConstants.Button,
                                                        style: "link",
                                                        click: function (model) {
                                                            if (this.getModel().get("BusinessConditionVO_OperateType") != 0) {
                                                                if (!$page.controller.save(false)) {
                                                                    return;
                                                                }
                                                            }
                                                            var url = "pcSlidingDetail.html?DeductionsId=" + model.get("BusinessConditionVO_DeductionsId")
                                                                + "&OperateType=" + model.get("BusinessConditionVO_OperateType");
                                                            if(model.get("BusinessConditionVO_OperateType") == 3 && model.get("EndoType") == 3){
                                                                url = url + "&EndoType=" + model.get("EndoType");
                                                            }
                                                            if(model.get("OperateId")){
                                                                url += "&OperateId=" + model.get("OperateId");
                                                            }
                                                            window.open(url);
                                                        }
                                                    },
                                                    pos: {
                                                        row: 1,
                                                        width: 4
                                                    },
                                                    css: {
                                                        comp: 'pull-right'
                                                    }
                                                },
                                                ManagementExpenses: {
                                                    label: "Management Expenses",
                                                    comp: {
                                                        type: $pt.ComponentConstants.Label,
                                                        textFromModel: false
                                                    },
                                                    pos: {
                                                        row: 2,
                                                        width: 6
                                                    },
                                                    css: {
                                                        cell: 'date-range-link'
                                                    }
                                                },
                                                BusinessConditionVO_ExpensesPercentage: {

                                                    base : $helper.basePercentage(2),
                                                    pos: {
                                                        row: 2,
                                                        width: 2
                                                    }
                                                },
                                                BusinessConditionVO_DeficitCarryForward: {
                                                    label: "Deficit Carry Forward",
                                                    comp: {
                                                        type: $pt.ComponentConstants.Select,
                                                        data: $page.codes.DeficitCarryForward
                                                    },
                                                    pos: {
                                                        row: 3,
                                                        width: 6
                                                    }
                                                },
                                                BusinessConditionVO_NumberOfYears: {
                                                    label: "Number of Years",
                                                    comp: {
                                                        type: $pt.ComponentConstants.Text,
                                                        enabled: {
                                                            when: function (model) {
                                                                if(model.get('BusinessConditionVO_DeficitCarryForward') == 1){
                                                                    model.set('BusinessConditionVO_NumberOfYears', null);
                                                                    return false;
                                                                }
                                                                return true;
                                                            },
                                                            depends: 'BusinessConditionVO_DeficitCarryForward'
                                                        }
                                                    },
                                                    css: {comp: 'currency-align-right-text'},
                                                    pos: {
                                                        row: 3,
                                                        width: 6
                                                    }
                                                }
                                            }
                                        },
                                        pos: {
                                            row: 2,
                                            width: 12
                                        }
                                    },
                                    BrokerAge: {
                                        label: "Brokerage",
                                        comp: {
                                            type: $pt.ComponentConstants.Panel,
                                            editLayout: {
                                                BusinessConditionVO_PercentOfPremium: {
                                                    label: "% of Premium",
                                                    comp: {
                                                        enabled: {
                                                            when: function (model) {
                                                                return  !model.get('BusinessConditionVO_FixedAmountHunredPercent') || model.get('BusinessConditionVO_FixedAmountHunredPercent') == 0;
                                                            },
                                                            depends: 'BusinessConditionVO_FixedAmountHunredPercent'
                                                        }
                                                    },
                                                    base : $helper.basePercentage(2)
                                                },
                                                BusinessConditionVO_FixedAmountHunredPercent: {
                                                    label: "Fixed Amount",
                                                    comp: {
                                                        type: $pt.ComponentConstants.Text,
                                                        labelWidth: 7,
                                                        format: $helper.formatNumber(2),
                                                        enabled: {
                                                            when: function (model) {
                                                                return  !model.get('BusinessConditionVO_PercentOfPremium') || model.get('BusinessConditionVO_PercentOfPremium') == 0;
                                                            },
                                                            depends: 'BusinessConditionVO_PercentOfPremium'
                                                        }
                                                    },
                                                    base : $helper.baseAmount(2),
                                                    css: {cell: 'currency-align-right-text'}
                                                }
                                            }
                                        },

                                        pos: {
                                            row: 3,
                                            width: 12
                                        }
                                    },
                                    DeductionsList: {
                                        label: "Other Deductions",
                                        comp: {
                                            type: $pt.ComponentConstants.Panel,
                                            editLayout: {
                                                BusinessConditionVO_DeductionsList: {
                                                    label: "",
                                                    comp: {
                                                        type: $pt.ComponentConstants.Table,
                                                        sortable: false,
                                                        searchable: false,
                                                        removable: true,
                                                        addable: true,
                                                        rowListener : [
                                                            {
                                                                id:'Percentage',listener:function(evt){
                                                                var perCent=evt.model.get("Percentage");
                                                                var amountPercent = evt.model.get("AmountPercent");
                                                                var  amountPercentFlag = (amountPercent == undefined || amountPercent == "" || amountPercent == null);
                                                                if(!amountPercentFlag){
                                                                    NConfirm.getConfirmModal().show({
                                                                        title: 'Message',
                                                                        disableClose: true,
                                                                        messages: ['Either In Percentage or In Amount could  be input' ]
                                                                    });
                                                                    evt.model.set("Percentage", "");
                                                                }
                                                                if (perCent > 1) {
                                                                    NConfirm.getConfirmModal().show({
                                                                        title: 'System Message',
                                                                        disableClose: true,
                                                                        messages: ['Percentage must be not more than 100%.']
                                                                    });
                                                                    evt.model.set("Percentage", "");
                                                                }
                                                            }
                                                            }, {
                                                                id:'AmountPercent',listener:function(evt){
                                                                    var percentage=evt.model.get("Percentage");
                                                                    var  percentageFlag = (percentage == undefined || percentage == "" || percentage == null|| percentage == 0);
                                                                    if(!percentageFlag){
                                                                        NConfirm.getConfirmModal().show({
                                                                            title: 'Message',
                                                                            disableClose: true,
                                                                            messages: ['Either In Percentage or In Amount could  be input' ]
                                                                        });
                                                                        evt.model.set("AmountPercent", "");
                                                                    }

                                                                }
                                                            }],
                                                        columns: [
                                                            {
                                                                title: "Deduction Item",
                                                                data: "Item",
                                                                inline: "select",
                                                                codes: $page.codes.DeductionItem,
                                                                width: "23%"
                                                            },
                                                            {
                                                                title: "In Percentage",
                                                                data: "Percentage",
                                                                //inline: "percentage",
                                                                inline:$helper.registerInlinePercentage("Percentage",2),
                                                                width: "23%",
                                                                render: function (model) {
                                                                    var percentage=model.get("Percentage");
                                                                   model.set("Percentage",percentage.toFixed(2));
                                                                }

                                                            },
                                                            {
                                                                title: "In Amount",
                                                                data: "AmountPercent",
                                                                inline : $helper.registerInlineAmount("AmountPercent",2),
                                                                width: "23%"
                                                            },
                                                            //{title: "In Amount Our Share", data: "AmountPercent",inline:"number",width:"23%"},
                                                            {
                                                                title: "Remarks",
                                                                data: "Remarks",
                                                                inline: "text",
                                                                width: "30%"
                                                            }
                                                        ],
                                                        canRemove: function (model, item) {
                                                            if (item.DeductionsItemId && item.DeductionsItemId != 0) {
                                                                model.add("BusinessConditionVO_DeleteDeductionsList", item);
                                                            }
                                                            this.getModel().remove(this.getDataId(), item);
                                                        },
                                                        addClick: function (model, item, layout) {
                                                            model.add("BusinessConditionVO_DeductionsList", item.getCurrentModel());
                                                        }
                                                    },
                                                    css:{
                                                        comp: "inline-editor",
                                                        cell: "title-align"
                                                    },
                                                    pos: {
                                                        width: 12,
                                                        row: 1

                                                    }
                                                }

                                            }
                                        },
                                        pos: {
                                            row: 4,
                                            width: 12
                                        }
                                    },
                                    RemarkDeducion: {
                                        label: "Remarks",
                                        comp: {
                                            type: $pt.ComponentConstants.Panel,
                                            editLayout: {
                                                BusinessConditionVO_RemarkPanel: {
                                                    comp: {
                                                        type: {type: $pt.ComponentConstants.TextArea, label: false},
                                                        lines: 3
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
                                    bottomButtons: {
                                        comp: {
                                            type: $pt.ComponentConstants.ButtonFooter,
                                            buttonLayout: {
                                                right: [
                                                    {
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
            if (this.getModel().get('BusinessConditionVO_OperateType') == 0 || this.getModel().get('BusinessConditionVO_OperateType') == 5 || (this.getModel().get('BusinessConditionVO_OperateType') == 3 && (this.getModel().get('EndoType') == 3) && this.getModel().get('HasInforce'))) {
                return <NForm model={this.getModel()} layout={this.state.editLayout} view={true}/>;
            }
            return <NForm model={this.getModel()} layout={this.state.editLayout}/>;
        }
    }));
    context.DeductionProportional = DeductionProportional;
    NFormCell.registerComponentRenderer('DeductionProportional', function (model, layout, direction) {
        return <DeductionProportional model={model} layout={layout} direction={direction}/>;
    });
    $pt.ComponentConstants.DeductionProportional = {type: 'DeductionProportional', label: false, popover: false};
}(typeof window !== "undefined" ? window : this, jQuery, $pt));
