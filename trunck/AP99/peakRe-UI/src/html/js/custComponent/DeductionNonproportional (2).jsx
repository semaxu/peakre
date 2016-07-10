(function (context, $, $pt) {
    var DeductionNonproportional = React.createClass($pt.defineCellComponent({
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
                            claimSec: {
                                label: "Deductions",
                                style: 'primary-darken',
                                collapsible: true,
                                expanded: false,
                                layout: {
                                    BusinessConditionVO_Brokerage: {
                                        label: "Brokerage",

                                        base : $helper.basePercentage(2),
                                        pos: {
                                            width: 4,
                                            row: 1
                                        },
                                        comp: {
                                            enabled: {
                                                when: function (model) {
                                                    var brokerage = model.get('BusinessConditionVO_Brokerage2');
                                                    var brokerageFlag = false;
                                                    if (brokerage == undefined || brokerage == '') {
                                                        brokerageFlag = true;
                                                    }
                                                    return brokerageFlag;
                                                },
                                                depends: 'BusinessConditionVO_Brokerage2'
                                            }
                                        }
                                    },
                                    BusinessConditionVO_Brokerage2: {
                                        label: "",
                                        comp: {
                                            type: {
                                                labelDirection: "horizontal",
                                                enabled: true
                                            },
                                            format:$helper.formatNumber(2),
                                            enabled: {
                                                when: function (model) {
                                                    var brokerage2 = model.get('BusinessConditionVO_Brokerage');
                                                    var brokerage2Flag = false;
                                                    if(brokerage2==undefined||brokerage2==''||brokerage2==0){
                                                        brokerage2Flag = true;
                                                    }
                                                    return brokerage2Flag;
                                                },
                                                depends: 'BusinessConditionVO_Brokerage'
                                            }

                                        },
                                        base : $helper.baseAmount(2),
                                        pos: {
                                            width: 4,
                                            row: 1
                                        }
                                    },
                                    BusinessConditionVO_DeductionsList: {
                                        label: "Other Deductions",
                                        comp: {
                                            type: $pt.ComponentConstants.Table,
                                            sortable: false,
                                            searchable: false,
                                            removable: true,
                                            addable: true,
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
                                                    width: "23%"
                                                },
                                                {
                                                    title: "In Amount",
                                                    data: "AmountPercent",
                                                    inline: $helper.registerInlineAmount("AmountPercent",2),
                                                    width: "23%"
                                                },
                                                {title: "Remarks", data: "Remarks", inline: "text", width: "30%"}
                                            ],
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
                                    },
                                    RemarkPanel: {
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
            // if (this.getModel().get('BusinessConditionVO_OperateType') == 3 && (this.getModel().get('EndoType') == 1 || this.getModel().get('EndoType') == 2)) {
            //     return <NForm model={this.getModel()} layout={this.state.editLimitLayout} view={true}/>;
            // }
            return <NForm model={this.getModel()} layout={this.state.editLayout}/>;
        }
    }));
    context.DeductionNonproportional = DeductionNonproportional;
    NFormCell.registerComponentRenderer('DeductionNonproportional', function (model, layout, direction) {
        return <DeductionNonproportional model={model} layout={layout} direction={direction}/>;
    });
    $pt.ComponentConstants.DeductionNonproportional = {type: 'DeductionNonproportional', label: false, popover: false};
}(typeof window !== "undefined" ? window : this, jQuery, $pt));
