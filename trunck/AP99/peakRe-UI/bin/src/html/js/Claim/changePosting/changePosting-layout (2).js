(function (context) {
    var $page = $pt.getService(context, '$page');



    var Layout = jsface.Class({
        changePostingInfo: function () {
            return {

                label: "Change Posting",
                style: "primary-darken",
                layout: {
                    SettlementId: {
                        label: "Settlement ID",
                        comp: {
                            type: $pt.ComponentConstants.Select,
                            data: $page.settlementNo
                        },
                        pos: {
                            row: 1
                        }
                    },
                    searchPanel: {
                        comp: {
                            type: $pt.ComponentConstants.ButtonFooter,
                            buttonLayout: {
                                left: [
                                    {
                                        text: "Search",
                                        style: "primary",
                                        click: function () {
                                            $page.controller.searchSettlementItem();
                                        }
                                    }
                                ], right: [{
                                    text: "Settlement History",
                                    style: "link",
                                    click: function (model) {
                                        var refId = model.get("RefId");
                                        var businessDirection = model.get("BusinessDirection");

                                       // this.model.set("RefId", refId);
                                       // this.model.set("BusinessDirection", businessDirection);
                                        var url = $pt.getURL('ui.claim.settlementHistory');
                                        window.open(url + "?refId=" + refId + "&businessDirection="+businessDirection);
                                    }
                                }]
                            }
                        },
                        pos: {
                            row: 1
                        }
                    },
                    SettlementItemList: {
                        comp: {
                            type: $pt.ComponentConstants.Table,
                            searchable: false,
                            sortable: false,
                            header: false,
                            columns: [

                                {
                                    title: "Contract Section",
                                    data: "SectionId",
                                    codes: $page.contractSelection,
                                    width: "10%"
                                }, {
                                    title: "Settlement Type",
                                    data: "SettlementType",
                                    codes: $page.codes.SettlementType,
                                    width: "10%"
                                }, {
                                    title: "Original Currency",
                                    data: "OriginalCurrency",
                                    codes: $page.codes.Currency,
                                    width: "10%"
                                }, {
                                    title: "Amount(OC)",
                                    data: "AmountOc",
                                    width: "10%",
                                    inline: {
                                        label: {
                                            comp: {
                                                type: {
                                                    label: false,
                                                    popover: false,
                                                    render: function (model) {
                                                        return $helper.formatNumberForLabel(model.get("AmountOc"), 2);
                                                    }
                                                }
                                            },
                                            pos: {width: 12},
                                            css: {cell: 'currency-align-right'}
                                        }
                                    }
                                }, {
                                    title: "Amount(USD)",
                                    data: "AmountUsd",
                                    width: "10%",
                                    inline: {
                                        label: {
                                            comp: {
                                                type: {
                                                    label: false,
                                                    popover: false,
                                                    render: function (model) {
                                                        return $helper.formatNumberForLabel(model.get("AmountUsd"), 2);
                                                    }
                                                }
                                            },
                                            pos: {width: 12},
                                            css: {cell: 'currency-align-right'}
                                        }
                                    }
                                }, {
                                    title: "Transfer to Accounts",
                                    data: "PostingFlag",
                                    codes: $page.codes.Boolean,
                                    inline: "radio",
                                    width: "10%"
                                },{
                                    data: "OrgPostingFlag",
                                    visible: false
                                }
                            ]
                        },
                        pos: {
                            row: 2,
                            width: 12
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
                            row: 3,
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
                                            window.close();
                                        }
                                    }, {
                                        text: "Submit",
                                        style: "primary",
                                        click: function (model) {
                                            $page.controller.submit(model.getCurrentModel());
                                        }
                                    }
                                ]
                            }
                        },
                        pos: {
                            row: 4,
                            width: 12
                        }
                    }
                }
            }
        },

        createFormLayout: function () {
            return {
                _sections: {
                    defaultSection: this.changePostingInfo()
                }
            }
        }

    });
    $page.layout = new Layout();

}(window));
