(function (context, $, $pt) {
    var codes = $pt.getService(context, "codes");
    var $page = $pt.getService(context, "$page");
    //var noOfSchedule = $pt.createCodeTable([{id: '1', text: '1'}, {id: '2', text: '2'}, {id: '3', text: '4'}, {
    //    id: '4',
    //    text: '12'
    //}]);
    var noOfSchedule = $pt.createCodeTable([{id: '1', text: '1'}, {id: '2', text: '2'}, {id: '4', text: '4'}, {
        id: '12',
        text: '12'
    }]);
    var premiumType = $pt.createCodeTable([{id: '1', text: 'Flat premium'}, {id: '2', text: 'Fixed rate'}, {
        id: '3',
        text: 'Swing rate'
    }]);
    var preminumDefined = $pt.createCodeTable([{id: "1", text: "Our Signed Share"}, {
        id: "2",
        text: "Our Written Share"
    }, {id: "3", text: "100%"}]);
    var BCPremiumNonProportion = React.createClass($pt.defineCellComponent({
        statics: {},
        propTypes: {
            // model
            model: React.PropTypes.object,
            // CellLayout
            layout: React.PropTypes.object
        },
        getDefaultProps: function () {
          var autoCalculation = [
            {id:'Amount',listener:function(evt){return $page.businessCalculator.calculationPremiumAmount("OurWrittenShare","OurSignedShare",evt)}},
            {id:'OurWrittenShare',listener:function(evt){return $page.businessCalculator.calculationWrittenPremium("Amount","OurSignedShare",evt)}},
            {id:'OurSignedShare',listener:function(evt){return $page.businessCalculator.calculationSignedPremium("Amount","OurWrittenShare",evt)}}
          ];
          var autoCalculationForMini = [
            {id:'Amount',listener:function(evt){return $page.businessCalculator.calculationPremiumAmountWithPer("OurWrittenShare","OurSignedShare","MinimumAmount",evt)}},
            {id:'OurWrittenShare',listener:function(evt){return $page.businessCalculator.calculationWrittenPremium("Amount","OurSignedShare",evt)}},
            {id:'OurSignedShare',listener:function(evt){return $page.businessCalculator.calculationSignedPremium("Amount","OurWrittenShare",evt)}}
          ];
          var autoCalculationForDep = [
            {id:'Amount',listener:function(evt){return $page.businessCalculator.calculationPremiumAmountWithPer("OurWrittenShare","OurSignedShare","DepositAmount",evt)}},
            {id:'OurWrittenShare',listener:function(evt){return $page.businessCalculator.calculationWrittenPremium("Amount","OurSignedShare",evt)}},
            {id:'OurSignedShare',listener:function(evt){return $page.businessCalculator.calculationSignedPremium("Amount","OurWrittenShare",evt)}}
          ];
          var premiumCalculation = [
            {id:'Currency',listener:function(evt){return $page.businessCalculator.calculationRateAmount(evt)}},
            {id:'Amount',listener:function(evt){return $page.businessCalculator.calculationPremiumAmount("OurWrittenShare","OurSignedShare",evt)}},
            {id:'OurWrittenShare',listener:function(evt){return $page.businessCalculator.calculationWrittenPremium("Amount","OurSignedShare",evt)}},
            {id:'OurSignedShare',listener:function(evt){return $page.businessCalculator.calculationSignedPremium("Amount","OurWrittenShare",evt)}}
          ];
            return {
                defaultOptions: {
                    editLimitLayout: {
                        _sections: {
                            limit: {
                                label: "Premium",
                                style: 'primary-darken',
                                collapsible: true,
                                expanded: false,
                                pos: {
                                    width: 12
                                },
                                layout: {
                                    msg : {
                                        label : "NOTE: Premium revision will cause changes in Pricing & Estimates.",
                                        comp : {
                                            type : $pt.ComponentConstants.Label,
                                            textFromModel: false,
                                            style: "danger",
                                            visible: {
                                                when: function (model) {
                                                    return model.get('BusinessConditionVO_OperateType') == '3' && (model.get('EndoType') == 1 || model.get('EndoType') == 2)
                                                    && model.get('HasInforce');
                                                },
                                                depends: 'BusinessConditionVO_OperateType'
                                            }
                                        },
                                        pos : {width : 12, row: 1},
                                        data: null
                                    },
                                    estimatedSUPIPanel: {
                                        label: "Estimated SUPI",
                                        comp: {
                                            type: $pt.ComponentConstants.Panel,
                                            editLayout: {
                                                BusinessConditionVO_DefinedIn: {
                                                    label: "SUPI defined in",
                                                    comp: {
                                                        type: $pt.ComponentConstants.Select,
                                                        data: $page.codes.PremiumDefinedIn
                                                    }
                                                },
                                                BusinessConditionVO_SupiList: {
                                                    comp: {
                                                        type: $pt.ComponentConstants.Table,
                                                        addable: true,
                                                        removable: true,
                                                        searchable: false,
                                                        sortable: false,
                                                        rowListener:[
                                                            {
                                                                id:'Amount',listener:function(evt) {
                                                                  return $page.businessCalculator.calculationSUPIAmountChange(evt);
                                                                }

                                                            }
                                                        ],
                                                        addClick: function (model) {
                                                            model.add("BusinessConditionVO_SupiList", $.extend(true, {}, $page.model.getSupiListTemplate()));
                                                        },
                                                        canRemove: function (model, item) {
                                                            if (item.ItemId && item.ItemId != 0) {
                                                                model.add("BusinessConditionVO_DeletePremiumList", item);
                                                            }
                                                            this.getModel().remove(this.getDataId(), item);
                                                        },
                                                        columns: [
                                                            {
                                                                title: "Currency",
                                                                data: "Currency",
                                                                inline: "select",
                                                                codes: $page.codes.Currency,
                                                                width: "47%"
                                                            },
                                                            {
                                                                title: "Amount",
                                                                data: "Amount",
                                                                inline: $helper.registerInlineAmount("Amount",2),
                                                                width: "46%"
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
                                            },
                                        },
                                        pos: {
                                            width: 12,
                                            section: "limit",
                                            row: 1
                                        }
                                    },
                                    premiumTypePanel: {
                                        label: "Premium Type",
                                        comp: {
                                            type: $pt.ComponentConstants.Panel,
                                            editLayout: {
                                                BusinessConditionVO_PremiumType: {
                                                    comp: {
                                                        type: $pt.ComponentConstants.Radio,
                                                        data: premiumType,
                                                        labelWidth: 1
                                                    },
                                                    pos: {
                                                        width: 6
                                                    }
                                                },
                                                BusinessConditionVO_FloatPremiumList: {
                                                    comp: {
                                                        type: $pt.ComponentConstants.Table,
                                                        rowListener:premiumCalculation,
                                                        addable: true,
                                                        removable: true,
                                                        searchable: false,
                                                        sortable: false,
                                                        addClick: function (model) {
                                                            model.add("BusinessConditionVO_FloatPremiumList", $.extend(true, {}, $page.model.getFloatPremiumListTemplate()));
                                                        },
                                                        canRemove: function (model, item) {
                                                            if (item.ItemId && item.ItemId != 0) {
                                                                model.add("BusinessConditionVO_DeletePremiumList", item);
                                                            }
                                                            this.getModel().remove(this.getDataId(), item);
                                                        },
                                                        visible: {
                                                            when: function (model) {
                                                                return model.get('BusinessConditionVO').PremiumType == '1';
                                                            }, depends: 'BusinessConditionVO_PremiumType'
                                                        },
                                                        columns: [
                                                            {
                                                                title: "Currency",
                                                                data: "Currency",
                                                                inline: "select",
                                                                codes: $page.codes.Currency,
                                                                width: "25%"
                                                            },
                                                            {
                                                                title: "100%",
                                                                data: "Amount",
                                                                inline: $helper.registerInlineAmount("Amount",2),
                                                                width: "25%"
                                                            },
                                                            {
                                                                title: "Our Written Share",
                                                                data: "OurWrittenShare",
                                                                inline: $helper.registerInlineAmount("OurWrittenShare",2),
                                                                width: '25%'
                                                            },
                                                            {
                                                                title: "Our Signed Share",
                                                                data: "OurSignedShare",
                                                                inline: $helper.registerInlineAmount("OurSignedShare",2),
                                                                width: '25%'
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
                                                fixRatePanel: {
                                                    comp: {
                                                        type: $pt.ComponentConstants.Panel,
                                                        visible: {
                                                            when: function (model) {
                                                                return model.get('BusinessConditionVO').PremiumType == '2';
                                                            }, depends: 'BusinessConditionVO_PremiumType'
                                                        },
                                                        editLayout: {
                                                            BusinessConditionVO_Rate: {
                                                                label: "Rate",

                                                                base : $helper.basePercentage(8),
                                                                pos: {
                                                                    width: 4
                                                                }
                                                            },
                                                            BusinessConditionVO_FixRateList: {
                                                                comp: {
                                                                    type: $pt.ComponentConstants.Table,
                                                                    rowListener:premiumCalculation ,
                                                                    addable: true,
                                                                    removable: true,
                                                                    searchable: false,
                                                                    sortable: false,
                                                                    addClick: function (model) {
                                                                        model.add("BusinessConditionVO_FixRateList", $.extend(true, {}, $page.model.getFixRateListTemplate()));
                                                                    },
                                                                    canRemove: function (model, item) {
                                                                        if (item.ItemId && item.ItemId != 0) {
                                                                            model.add("BusinessConditionVO_DeletePremiumList", item);
                                                                        }
                                                                        this.getModel().remove(this.getDataId(), item);
                                                                    },
                                                                    columns: [
                                                                        {
                                                                            title: "Currency",
                                                                            data: "Currency",
                                                                            inline: "select",
                                                                            codes: $page.codes.Currency,
                                                                            width: "25%"
                                                                        },
                                                                        {
                                                                            title: "100%",
                                                                            data: "Amount",
                                                                            inline: $helper.registerInlineAmount("Amount",2),
                                                                            width: "25%"
                                                                        },
                                                                        {
                                                                            title: "Our Written Share",
                                                                            data: "OurWrittenShare",
                                                                            inline: $helper.registerInlineAmount("OurWrittenShare",2),
                                                                            width: '25%'
                                                                        },
                                                                        {
                                                                            title: "Our Signed Share",
                                                                            data: "OurSignedShare",
                                                                            inline: $helper.registerInlineAmount("OurSignedShare",2),
                                                                            width: '25%'
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
                                                        }
                                                    },
                                                    pos: {
                                                        width: 12
                                                    }
                                                },
                                                swingPanel: {
                                                    comp: {
                                                        type: $pt.ComponentConstants.Panel,
                                                        visible: {
                                                            when: function (model) {
                                                                return model.get('BusinessConditionVO').PremiumType == '3';
                                                            }, depends: 'BusinessConditionVO_PremiumType'
                                                        },
                                                        editLayout: {
                                                            BusinessConditionVO_LossrateFrom: {
                                                                label: "LR From",
                                                                base : $helper.basePercentage(2),
                                                                pos: {
                                                                    row: 1
                                                                }
                                                            },
                                                            BusinessConditionVO_LossrateTo: {
                                                                label: "LR To",
                                                                base : $helper.basePercentage(2),
                                                                pos: {
                                                                    row: 1
                                                                }
                                                            },
                                                            BusinessConditionVO_PremiumrateFrom: {
                                                                label: "PR From",

                                                                base : $helper.basePercentage(2),
                                                                pos: {
                                                                    row: 2
                                                                }
                                                            },
                                                            BusinessConditionVO_PremiumrateTo: {
                                                                label: "PR To",

                                                                base : $helper.basePercentage(2),
                                                                pos: {
                                                                    row: 2
                                                                }
                                                            },
                                                            BusinessConditionVO_ProvisionalRate: {
                                                                label: "Prov.R",

                                                                base : $helper.basePercentage(8),
                                                                pos: {
                                                                    row: 2
                                                                }
                                                            },
                                                            BusinessConditionVO_SwingRateList: {
                                                                comp: {
                                                                    type: $pt.ComponentConstants.Table,
                                                                    rowListener:premiumCalculation,
                                                                    addable: true,
                                                                    removable: true,
                                                                    searchable: false,
                                                                    sortable: false,
                                                                    addClick: function (model) {
                                                                        model.add("BusinessConditionVO_SwingRateList", $.extend(true, {}, $page.model.getSwingRateListTemplate()));
                                                                    },
                                                                    canRemove: function (model, item) {
                                                                        if (item.ItemId && item.ItemId != 0) {
                                                                            model.add("BusinessConditionVO_DeletePremiumList", item);
                                                                        }
                                                                        this.getModel().remove(this.getDataId(), item);
                                                                    },
                                                                    columns: [
                                                                        {
                                                                            title: "Currency",
                                                                            data: "Currency",
                                                                            inline: "select",
                                                                            codes: $page.codes.Currency,
                                                                            width: "25%"
                                                                        }, {
                                                                            title: "Prov AMT",
                                                                            data: "Amount",
                                                                            inline: $helper.registerInlineAmount("Amount",2),
                                                                            width: "25%"
                                                                        }, {
                                                                            title: "Our Written Share",
                                                                            data: "OurWrittenShare",
                                                                            inline: $helper.registerInlineAmount("OurWrittenShare",2),
                                                                            width: '25%'
                                                                        }, {
                                                                            title: "Our Signed Share",
                                                                            data: "OurSignedShare",
                                                                            inline: $helper.registerInlineAmount("OurSignedShare",2),
                                                                            width: '25%'
                                                                        },
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
                                                        }
                                                    },
                                                    pos: {
                                                        width: 12
                                                    }
                                                }
                                            }
                                        },
                                        pos: {
                                            width: 12,
                                            section: "limit",
                                            row: 2

                                        }
                                    },

                                    minimumPremiumPanel: {
                                        label: "Minimum Premium",
                                        comp: {
                                            type: $pt.ComponentConstants.Panel,
                                            visible: {
                                                when: function (model) {
                                                    return  model.get('BusinessConditionVO').PremiumType == '2' || model.get('BusinessConditionVO').PremiumType == '3';
                                                }, depends: 'BusinessConditionVO_PremiumType'
                                            },
                                            editLayout: {
                                                BusinessConditionVO_MinimumPremiumList: {
                                                    comp: {
                                                        type: $pt.ComponentConstants.Table,
                                                        rowListener:autoCalculationForMini,
                                                        addable: true,
                                                        removable: true,
                                                        searchable: false,
                                                        sortable: false,
                                                        addClick: function (model) {
                                                            model.add("BusinessConditionVO_MinimumPremiumList", $.extend(true, {}, $page.model.getMinimumPremiumListTemplate()));
                                                        },
                                                        canRemove: function (model, item) {
                                                            if (item.ItemId && item.ItemId != 0) {
                                                                model.add("BusinessConditionVO_DeletePremiumList", item);
                                                            }
                                                            this.getModel().remove(this.getDataId(), item);
                                                        },
                                                        columns: [
                                                            {
                                                                title: "Currency",
                                                                data: "Currency",
                                                                inline: "select",
                                                                codes: $page.codes.Currency,
                                                                width: "20%"
                                                            },
                                                            {
                                                                title: "Defined In",
                                                                data: "DefinedIn",
                                                                inline: "select",
                                                                codes: preminumDefined,
                                                                width: "20%"
                                                            },
                                                            {
                                                                title: "100%",
                                                                data: "Amount",
                                                                inline: $helper.registerInlineAmount("Amount",2),
                                                                width: "15%"
                                                            },
                                                            {
                                                                title: "Our Written Share",
                                                                data: "OurWrittenShare",
                                                                inline: $helper.registerInlineAmount("OurWrittenShare",2),
                                                                width: '15%'
                                                            },
                                                            {
                                                                title: "Our Signed Share",
                                                                data: "OurSignedShare",
                                                                inline: $helper.registerInlineAmount("OurSignedShare",2),
                                                                width: '15%'
                                                            },

                                                            {
                                                                title: "Minimum %",
                                                                data: "MinimumAmount",
                                                                //inline: "percentage",
                                                                inline:$helper.registerInlinePercentage("MinimumAmount",2),
                                                                width: "15%"
                                                            }
                                                        ]
                                                    },
                                                    css:{
                                                        comp: "inline-editor",
                                                        cell: "title-align"
                                                    },
                                                    pos: {
                                                        width: 12,
                                                        section: "limit"
                                                    }
                                                }
                                            }
                                        },
                                        pos: {
                                            width: 12,
                                            row: 3
                                        }
                                    },

                                    depositPremiumPanel: {
                                        label: "Deposit Premium",
                                        comp: {
                                            type: $pt.ComponentConstants.Panel,
                                            visible: {
                                                when: function (model) {
                                                    return model.get('BusinessConditionVO').PremiumType == '2' || model.get('BusinessConditionVO').PremiumType == '3';
                                                }, depends: 'BusinessConditionVO_PremiumType'
                                            },
                                            editLayout: {
                                                BusinessConditionVO_DepositPremiumList: {
                                                    comp: {
                                                        type: $pt.ComponentConstants.Table,
                                                        rowListener:autoCalculationForDep,
                                                        addable: true,
                                                        removable: true,
                                                        searchable: false,
                                                        sortable: false,
                                                        addClick: function (model) {
                                                            model.add("BusinessConditionVO_DepositPremiumList", $.extend(true, {}, $page.model.getDepositPremiumFixListTemplate()));
                                                        },
                                                        canRemove: function (model, item) {
                                                            if (item.ItemId && item.ItemId != 0) {
                                                                model.add("BusinessConditionVO_DeletePremiumList", item);
                                                            }
                                                            this.getModel().remove(this.getDataId(), item);
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
                                                                title: "Defined In",
                                                                data: "DefinedIn",
                                                                inline: "select",
                                                                codes: preminumDefined,
                                                                width: "16%"
                                                            },
                                                            {
                                                                title: "100%",
                                                                data: "Amount",
                                                                inline:$helper.registerInlineAmount("Amount",2),
                                                                width: "16%"
                                                            },
                                                            {
                                                                title: "Our Written Share",
                                                                data: "OurWrittenShare",
                                                                inline:$helper.registerInlineAmount("OurWrittenShare",2),
                                                                width: '16%'
                                                            },
                                                            {
                                                                title: "Our Signed Share",
                                                                data: "OurSignedShare",
                                                                inline:$helper.registerInlineAmount("OurSignedShare",2),
                                                                width: '16%'
                                                            },

                                                            {
                                                                title: "Deposit %",
                                                                data: "DepositAmount",
                                                                //inline: "percentage",
                                                                inline:$helper.registerInlinePercentage("DepositAmount",2),
                                                                width: '16%'
                                                            }
                                                        ]
                                                    },
                                                    css:{
                                                        comp: "inline-editor",
                                                        cell: "title-align"
                                                    },
                                                    pos: {
                                                        width: 12,
                                                        section: "limit"
                                                    }
                                                }
                                            }
                                        },
                                        pos: {
                                            width: 12,
                                            row: 5
                                        }
                                    },

                                    depositSchedulePanel: {
                                        label: "Deposit Schedule",
                                        comp: {
                                            type: $pt.ComponentConstants.Panel,
                                            visible: {
                                                when: function (model) {
                                                    return model.get('BusinessConditionVO').PremiumType == '1' || model.get('BusinessConditionVO').PremiumType == '2' || model.get('BusinessConditionVO').PremiumType == '3' ;
                                                }, depends: 'BusinessConditionVO_PremiumType'
                                            },
                                            editLayout: {
                                                BusinessConditionVO_NoOfInstallment: {
                                                    label: "No. of Installment",
                                                    comp: {
                                                        type: $pt.ComponentConstants.Select,
                                                        data: noOfSchedule,
                                                        enabled: {
                                                            when: function (model) {
                                                                return (model.get("HasInforce") == false) ;
                                                            }, depends: 'HasInforce'
                                                        }
                                                    },
                                                },
                                                BusinessConditionVO_DepositScheduleList: {
                                                    pos: {width: 12},
                                                    comp: {
                                                        type: $pt.ComponentConstants.Table,
                                                        addable: true,
                                                        removable: true,
                                                        searchable: false,
                                                        sortable: false,
                                                        addClick: function (model) {
                                                            model.add("BusinessConditionVO_DepositScheduleList", $.extend(true, {}, $page.model.getDepositScheduleListTemplate()));
                                                        },
                                                        canRemove: function (model, item) {
                                                            if (item.ItemId && item.ItemId != 0) {
                                                                model.add("BusinessConditionVO_DeletePremiumList", item);
                                                            }
                                                            this.getModel().remove(this.getDataId(), item);
                                                        },
                                                        columns: [
                                                            {
                                                                title: "Installment No.",
                                                                data: "InstalNo",
                                                                // inline: "text",
                                                                inline: {
                                                                    InstalNo: {
                                                                        comp: {
                                                                            type:{label:false}
                                                                        },
                                                                        base:$helper.baseNumber(),
                                                                        pos: {width: 12}
                                                                    }
                                                                },
                                                                width: "19%"
                                                            },
                                                            {
                                                                title: "Due Date",
                                                                data: "DueDate",
                                                                inline: "DateFormate",
                                                                width: "19%"
                                                            },
                                                            {
                                                                title: "Currency",
                                                                data: "Currency",
                                                                inline: "select",
                                                                codes: $page.codes.Currency,
                                                                width: "19%"
                                                            },
                                                            {
                                                                title: "Amount",
                                                                data: "Amount",
                                                                inline: $helper.registerInlineAmount("Amount",2),
                                                                width: "19%"
                                                            },
                                                            {
                                                                title: "Percentage",
                                                                data: "Percentage",
                                                                //inline: "percentage",
                                                                inline:$helper.registerInlinePercentage("Percentage",2),
                                                                width: "19%"
                                                            }
                                                        ]
                                                    },
                                                    css:{
                                                        comp: "inline-editor",
                                                        cell: "title-align"
                                                    }
                                                }
                                            }
                                        },
                                        pos: {
                                            width: 12,
                                            section: "limit",
                                            row: 7
                                        }
                                    },
                                    remarkPanel: {
                                        label: "Remarks",
                                        comp: {
                                            type: $pt.ComponentConstants.Panel,
                                            editLayout: {
                                                BusinessConditionVO_PremiumRemark: {
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
                                            width: 12,
                                            row: 9
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
                                            width: 12,
                                            section: "limit",
                                            row: 10
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
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
            if (this.getModel().get('BusinessConditionVO_OperateType') == 0 || this.getModel().get('BusinessConditionVO_OperateType') == 5 || (this.getModel().get('BusinessConditionVO_OperateType') == 3 && (this.getModel().get('EndoType') == 3)  && this.getModel().get('HasInforce'))) {
                return <NForm model={this.getModel()} layout={this.state.editLimitLayout} view={true}/>;
            }
            return <NForm model={this.getModel()} layout={this.state.editLimitLayout}/>;
        }
    }));
    context.BCPremiumNonProportion = BCPremiumNonProportion;
    NFormCell.registerComponentRenderer('BCPremiumNonProportion', function (model, layout, direction) {
        return <BCPremiumNonProportion model={model} layout={layout} direction={direction}/>;
    });
    $pt.ComponentConstants.BCPremiumNonProportion = {type: 'BCPremiumNonProportion', label: false, popover: false};
}(typeof window !== "undefined" ? window : this, jQuery, $pt));
