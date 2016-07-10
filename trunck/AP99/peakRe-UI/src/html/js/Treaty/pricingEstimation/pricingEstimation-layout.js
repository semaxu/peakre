(function (context) {
    var $page = $pt.getService(context, '$page');
    var codes = $pt.getService($page, 'codes');
    // var uWResponsible = $pt.createCodeTable([
    //     {id: '1', text: 'Gina Gao'},
    //     {id: '2', text: 'Yan Tang'},
    //     {id: '3', text: 'Jessie Lin'},
    //     {id: '4', text: 'Lynn Chen'}
    // ]);

    var registerInlineSelect = function (name, codes, type) {
        NTable.registerInlineEditor(name, {
            comp: {
                type: {type: type, label: false, popover: true},
                data: codes,
                enabled: {
                    when: function (row) {
                        return false;
                    }
                }
            }
        });
        return name;
    };
    var PricingLayout = jsface.Class({
        createProportionLayout: function () {
            return {
                proportionalPanel: {
                    comp: {
                        type: $pt.ComponentConstants.Panel,
                        visible: {
                            when: function (model) {
                                return model.get('ContractNature') == '1';
                            },
                            depends: 'ContractNature'
                        },
                        editLayout: {
                            SectionName: {
                                label: "Section Name",
                                comp: {
                                    type: $pt.ComponentConstants.Text,
                                    enabled: false
                                },
                                pos: {
                                    row: 1
                                }
                            },
                            WrittenPartner: {
                                label: "Written Pattern",
                                comp: {
                                    type: $pt.ComponentConstants.Select,
                                    data: $page.codes.WrittenPattern
                                },
                                pos: {
                                    row: 2
                                }
                            },
                            EarningPartner: {
                                label: "Earning Pattern",
                                comp: {
                                    type: $pt.ComponentConstants.Select,
                                    data: $page.codes.EarningPattern
                                },
                                pos: {
                                    row: 3
                                }
                            },
                            PricingItemList: {
                                label: "Internal View",
                                comp: {
                                    type: $pt.ComponentConstants.Table,
                                    searchable: false,
                                    sortable: false,
                                    addable: false,
                                    columns: [
                                        {
                                            title: "Date",
                                            data: "PricingDate",
                                            inline: "DateFormate",
                                            width: "12%"
                                        }, {
                                            title: "Modified By",
                                            data: "Underwriter",
                                            //inline: "select",
                                            codes:$page.codes.SystemUser,
                                            inline: registerInlineSelect.call(window, "Underwriter", $page.codes.SystemUser, $pt.ComponentConstants.Select),
                                            width: "12%",
                                            enable:false
                                        }, {
                                            title: "Currency",
                                            data: "Currency",
                                            codes : $page.codes.Currency,
                                            width: "12%"
                                        }, {
                                            title: "EPI",
                                            data: "Epi",
                                            inline: $helper.registerInlineAmount("Amount",2),
                                            width: "13%"
                                        }, {
                                            title: "Commission",
                                            data: "Commission",
                                            inline:$helper.registerInlinePercentage("Commission",2),
                                            width: "13%"
                                        }, {
                                            title: "Brokerage",
                                            data: "Brokerage",
                                            inline: $helper.registerInlinePercentage("Brokerage",2),
                                            width: "13%"
                                        }, {
                                            title: "Tax&Others",
                                            data: "Taxs",
                                            inline: $helper.registerInlinePercentage("Taxs",2),
                                            width: "13%"
                                        }, {
                                            title: "Loss Ratio",
                                            data: "LossRatio",
                                            //inline: "percentage",
                                            inline:$helper.registerInlinePercentage("LossRatio",2),
                                            width: "13%"
                                        }
                                    ]
                                },
                                pos: {
                                    row: 4,
                                    width: 12
                                },
                                css: {
                                    comp: "inline-editor"
                                }
                            },
                            HisList: {
                                label: "History",
                                comp: {
                                    type: $pt.ComponentConstants.Table,
                                    searchable: false,
                                    sortable: false,
                                    view : true,
                                    columns: [
                                        {
                                            title: "Date",
                                            data: "PricingDate",
                                            inline: "DateFormate",
                                            width: "12%"
                                        }, {
                                            title: "Modified By",
                                            data: "Underwriter",
                                            inline: "select",
                                            codes: codes.SystemUser,
                                            width: "12%"
                                        }, {
                                            title: "Currency",
                                            data: "Currency",
                                            codes: $page.codes.Currency,
                                            width: "12%"
                                        }, {
                                            title: "EPI",
                                            data: "Epi",
                                            inline: $helper.registerInlineAmount("Amount",2),
                                            width: "13%"
                                        }, {
                                            title: "Commission",
                                            data: "Commission",
                                            //inline: "percentage",
                                            inline:$helper.registerInlinePercentage("Commission",2),
                                            width: "13%"
                                        }, {
                                            title: "Brokerage",
                                            data: "Brokerage",
                                            inline: $helper.registerInlinePercentage("Brokerage",2),
                                            width: "13%"
                                        }, {
                                            title: "Tax&Others",
                                            data: "Taxs",
                                            inline: $helper.registerInlinePercentage("Taxs",2),
                                            width: "13%"
                                        }, {
                                            title: "Loss Ratio",
                                            data: "LossRatio",
                                            //inline: "percentage",
                                            inline:$helper.registerInlinePercentage("LossRatio",2),
                                            width: "13%"
                                        }
                                    ]
                                },
                                pos: {
                                    row: 5,
                                    width: 12
                                }
                            },
                            Actualized: {
                                label: "Actualized",
                                comp: {
                                    type: $pt.ComponentConstants.Select,
                                    data: $page.codes.Boolean,
                                    enabled: false
                                },
                                pos: {
                                    row: 6
                                }
                            },
                            remarkPanel: {
                                label: "Remarks",
                                comp: {
                                    type: $pt.ComponentConstants.Panel,
                                    editLayout: {
                                        Remark: {
                                            comp: {
                                                type: $pt.ComponentConstants.TextArea,
                                                labelDirection: "vertical",
                                                lines: 3
                                            },
                                            pos: {
                                                width: 12
                                            }
                                        }
                                    }
                                },
                                pos: {
                                    row: 7,
                                    width: 12
                                }
                            }

                        }
                    },
                    pos: {
                        row: 2,
                        width: 12,
                        section: "pricingSection"
                    }
                }
            }
        },
        createNonProportionLayout: function () {
            return {
                nonProportionPanel: {
                    comp: {
                        type: $pt.ComponentConstants.Panel,
                        visible: {
                            when: function (model) {
                                return model.get('ContractNature') == '2';
                            },
                            depends: 'ContractNature'
                        },
                        editLayout: {
                            SectionName: {
                                label: "Section Name",
                                comp: {
                                    type: $pt.ComponentConstants.Text,
                                    enabled: false
                                },
                                pos: {
                                    row: 1
                                }
                            },
                            PricingItemList: {
                                label: "Internal View",
                                comp: {
                                    type: $pt.ComponentConstants.Table,
                                    searchable: false,
                                    sortable: false,
                                    addable: false,
                                    columns: [
                                        {
                                            title: "Date",
                                            data: "PricingDate",
                                            inline: "DateFormate",
                                            width: "25%"
                                        }, {
                                            title: "Modified By",
                                            data: "Underwriter",
                                            // inline: "select",
                                            codes: $page.codes.SystemUser,
                                            inline: registerInlineSelect.call(window, "Underwriter", $page.codes.SystemUser, $pt.ComponentConstants.Select),
                                            width: "25%",
                                            enable:false
                                        }, {
                                            title: "Currency",
                                            data: "Currency",
                                            codes: $page.codes.Currency,
                                            width: "25%"
                                        }, {
                                            title: "Loss Ratio",
                                            data: "LossRatio",
                                            //inline: "percentage",
                                            inline:$helper.registerInlinePercentage("LossRatio",2),
                                            width: "25%"
                                        }
                                    ]
                                },
                                pos: {
                                    row: 4,
                                    width: 12
                                },
                                css: {
                                    comp: "inline-editor"
                                }
                            },
                            HisList: {
                                label: "History",
                                comp: {
                                    type: $pt.ComponentConstants.Table,
                                    searchable: false,
                                    sortable: false,
                                    columns: [
                                        {
                                            title: "Date",
                                            data: "PricingDate",
                                            inline: "DateFormate",
                                            width: "25%"
                                        }, {
                                            title: "Modified By",
                                            data: "Underwriter",
                                            codes:  codes.SystemUser,
                                            inline: "select",
                                            width: "25%"
                                        }, {
                                            title: "Currency",
                                            data: "Currency",
                                            codes: $page.codes.Currency,
                                            width: "25%"
                                        }, {
                                            title: "Loss Ratio",
                                            data: "LossRatio",
                                            //inline: "percentage",
                                            inline:$helper.registerInlinePercentage("LossRatio",2),
                                            width: "25%"
                                        }
                                    ],
                                    view : true
                                },
                                pos: {
                                    row: 5,
                                    width: 12
                                }
                            },

                            Actualized: {
                                label: "Actualized",
                                comp: {
                                    type: $pt.ComponentConstants.Select,
                                    data: $page.codes.Boolean,
                                    enabled: false
                                },
                                pos: {
                                    row: 6
                                }
                            },
                            remarkPanel: {
                                label: "Remark",
                                comp: {
                                    type: $pt.ComponentConstants.Panel,
                                    editLayout: {
                                        Remark: {
                                            comp: {
                                                type: $pt.ComponentConstants.TextArea,
                                                labelDirection: "vertical",
                                                lines: 3
                                            },
                                            pos: {
                                                width: 12
                                            }
                                        }
                                    }
                                },
                                pos: {
                                    row: 7,
                                    width: 12
                                }
                            }
                        }
                    },
                    pos: {
                        row: 2,
                        width: 12,
                        section: "pricingSection"
                    }
                }
            }
        },
        createPricingLayout: function () {
            return {
                _sections: {
                    pricingSection: {
                        label: "Pricing Estimation",
                        style: "primary-darken",
                        layout: {
                            buttonPanel: {
                                comp: {
                                    type: $pt.ComponentConstants.ButtonFooter,
                                    buttonLayout: {
                                        right: [
                                            {
                                                text: "Exit",
                                                style: "warning",
                                                click: function () {
                                                    var urlData = $pt.getUrlData();
                                                    var exitBool = urlData.Exit;
                                                    if(exitBool == 1){
                                                        window.close();
                                                    }else{
                                                        $page.controller.exitPricing();
                                                    }
                                                }
                                            }, {
                                                text: "Save & Return",
                                                style: "primary",
                                                visible: $page.controller.isVisible() && $page.controller.model.get("OperateType") == 1,
                                                click: function (model) {
                                                    $page.controller.savePricingInfo(model);
                                                }
                                            }, {
                                                text: "Submit",
                                                style: "primary",
                                                visible: $page.controller.isVisible() && $page.controller.model.get("OperateType") == 5,
                                                click: function (model) {
                                                    $page.controller.savePricingInfo(model);
                                                }
                                            }
                                        ]
                                    }
                                },
                                pos: {
                                    row: 3,
                                    width: 12
                                }
                            }
                        }
                    }
                }
            }
        },
        createLayout: function () {
            return $.extend(true, {}, this.createPricingLayout(), this.createNonProportionLayout(), this.createProportionLayout())
        }

    });

    $page.layout = new PricingLayout();
}(typeof window !== "undefined" ? window : this));
