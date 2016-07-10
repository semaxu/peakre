(function (context) {
    var $page = $pt.getService(context, '$page');
    NTable.registerInlineEditor('linkedBtn', {
        comp: {
            type: $pt.ComponentConstants.Button,
            style: 'link',
            labelFromModel: true,
            click: function (model) {
                var transType = model.get('TransType');
                var transId = model.get('TransId');
                var url = "";
                if(transType == 1){
                    url = $pt.getURL('ui.payment.collection');
                }
                else if(transType == 2){
                    url = $pt.getURL('ui.payment.payment');
                }
                else if(transType == 3){
                    url = $pt.getURL('ui.payment.internalOffset');
                }
                url = url+"?view=true&transId="+transId;
                console.info("++++++"+url);
                window.open(url);
            }
        },
        css: {
            comp: 'link-in-table-cell'
        }
    });

    var Layout = jsface.Class({
        createReversalInfo: function () {
            return {
                label: "Transaction Reversal",
                style: "primary-darken",
                layout: {
                    searchCriteria: {
                        label: "Search Criteria",
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                Condition_TransType: {
                                    label: "Transaction Type",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        minimumResultForSearch:1,
                                        data:$page.codes.ReversalTransactionType,
                                        placeholder:"please select..."
                                    },
                                    pos: {
                                        row: 1
                                    }
                                },
                                Condition_PayerPayee: {
                                    label: "Payer/Payee",
                                    base: $helper.basePartnerSearch(),
                                    pos: {
                                        row: 1
                                    }
                                },
                                Condition_SettleDateFrom: {
                                    label: "Registration Date from",
                                    comp: {
                                        type: $pt.ComponentConstants.Date,
                                        format: "DD/MM/YYYY"
                                    },
                                    pos: {
                                        row: 1
                                    }
                                },
                                Condition_SettleDateTo: {
                                    label: "Registration Date to",
                                    comp: {
                                        type: $pt.ComponentConstants.Date,
                                        format: "DD/MM/YYYY"
                                    },
                                    pos: {
                                        row: 2
                                    }
                                },
                                Condition_TransDateFrom: {
                                    label: "Operation Date from",
                                    comp: {
                                        type: $pt.ComponentConstants.Date,
                                        format: "DD/MM/YYYY"
                                    },
                                    pos: {
                                        row: 2
                                    }
                                },
                                Condition_TransDateTo: {
                                    label: "Operation Date to",
                                    comp: {
                                        type: $pt.ComponentConstants.Date,
                                        format: "DD/MM/YYYY"
                                    },
                                    pos: {
                                        row: 2
                                    }
                                },
                                Condition_SettleNo: {
                                    label: "Transaction Number",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    },
                                    pos: {
                                        row: 3
                                    }
                                },
                                Condition_Broker: {
                                    label: "Broker",
                                    base: $helper.basePartnerSearch(),
                                    pos: {
                                        row: 3
                                    }
                                },
                                Condition_PartnerCode: {
                                    label: "Cedent/Retrocessionaire",
                                    base: $helper.basePartnerSearch(),
                                    pos: {
                                        row: 3
                                    }
                                },
                                Condition_StatementId: {
                                    label: "Statement ID",
                                    comp: {
                                        type: $pt.ComponentConstants.Text,
                                    },
                                    pos: {
                                        row: 4
                                    }
                                },
                                Condition_ClaimNo: {
                                    label: "Claim Number",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    },
                                    pos: {
                                        row: 4
                                    }
                                },
                                Condition_ContractId: {
                                    label: "Contract ID",
                                    comp: {
                                        type: $pt.ComponentConstants.TextArea,
                                        lines: 3
                                    },
                                    pos: {
                                        row: 4
                                    }
                                },
                                searchPanel: {
                                    comp: {
                                        type: $pt.ComponentConstants.ButtonFooter,
                                        buttonLayout: {
                                            right: [
                                                {
                                                    text: "Reset",
                                                    style: "warning",
                                                    click: function () {
                                                        $page.controller.resetTransactionReversal();
                                                    }
                                                }, {
                                                    text: "Search",
                                                    style: "primary",
                                                    click: function (model) {
                                                        $page.controller.searchTransactionReversal(model);
                                                    }
                                                }
                                            ]
                                        }
                                    },
                                    pos: {
                                        row: 6,
                                        width: 12
                                    }
                                }
                            }
                        },
                        pos: {
                            row: 1,
                            width: 12
                        }
                    },
                    TransactionList: {
                        comp: {
                            type: $pt.ComponentConstants.Table,
                            searchable: false,
                            sortable: false,
                            header: false,
                            pageable: true,
                            criteria: 'cachedCriteria',
                            columns: [
                                {
                                    data: "Selected",
                                    inline: "check",
                                    width: "2%"
                                }, {
                                    title: "Transaction Number",
                                    data: "TransNo",
                                    width: "12%"/*,
                                     inline:"linkedBtn"*/
                                }, {
                                    title: "Transaction Type",
                                    data: "TransType",
                                    codes: $page.codes.TransactionType,
                                    width: "10%"
                                }, {
                                    title: "Collection/Payment/Internal Offset Amount",
                                    data: "Amount",
                                    width: "20%",
                                    inline: {
                                        label: {
                                            comp: {
                                                type: {
                                                    label: false,
                                                    popover: false,
                                                    render: function (row) {
                                                        if (undefined != row.get("Amount") && null != row.get("Amount") && "" != row.get("Amount")){
                                                            return row.get("Amount").toFixed(2);
                                                        }
                                                        return "0.00";
                                                    }
                                                }
                                            },
                                            pos: {width: 12},
                                            css: {cell: 'currency-align-right'}
                                        }
                                    }
                                }, {
                                    title: "Transaction Currency",
                                    data: "TransCurrency",
                                    width: "12%"
                                }, {
                                    title: "Payment Method",
                                    data: "PaymentMethod",
                                    codes: $page.codes.PaymentMethod,
                                    width: "10%"
                                }, {
                                    title: "Payer/Payee",
                                    data: "PayerPayeeName",
                                    width: "10%"
                                }, {
                                    title: "Registration Date",
                                    data: "CpDate",
                                    width: "10%",
                                    render: function (row) {
                                        var cpDate = row.CpDate;
                                        if ("" != cpDate && undefined != cpDate) {
                                            cpDate = moment(cpDate,"YYYY-MM-DD").format("DD/MM/YYYY");
                                        }
                                        else {
                                            cpDate = "";
                                        }
                                        return cpDate;
                                    }
                                }, {
                                    title: "Operation Date",
                                    data: "TransDate",
                                    width: "10%",
                                    render: function (row) {
                                        var transDate = row.TransDate;
                                        if ("" != transDate && undefined != transDate) {
                                            transDate = moment(transDate,"YYYY-MM-DD").format("DD/MM/YYYY");
                                        }
                                        else {
                                            transDate = "";
                                        }
                                        return transDate;
                                    }
                                }
                            ],
                            rowOperations: [
                                {
                                    //icon: "eye",
                                    tooltip: "View",
                                    click:function(model){
                                        var transType = model.TransType;
                                        var transId = model.TransId;
                                        var url = "";
                                        if(transType == 1){
                                            url = $pt.getURL('ui.payment.collection');
                                        }
                                        else if(transType == 2){
                                            url = $pt.getURL('ui.payment.payment');
                                        }
                                        else if(transType == 3){
                                            url = $pt.getURL('ui.payment.internalOffset');
                                        }
                                        url = url+"?view=true&transId="+transId;
                                        console.info("++++++"+url);
                                        window.open(url);
                                    }
                                }
                            ]
                        },
                        pos: {
                            row: 8,
                            width: 12
                        }
                    },
                    ReversalReason: {
                        label: "Reversal Reason",
                        comp: {
                            type: $pt.ComponentConstants.Select,
                            data: $page.codes.ReversalReason
                        },
                        pos: {
                            row: 9
                        }
                    },
                    RequestedBy: {
                        label: "Requested by",
                        comp: {
                            type: $pt.ComponentConstants.Text,
                            enabled: false
                        },
                        pos: {
                            row: 9
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
                                        lines: 5
                                    },
                                    pos: {
                                        width: 12
                                    }
                                }
                            }
                        },
                        pos: {
                            row: 10,
                            width: 12
                        }
                    },
                    buttonPanel2: {
                        comp: {
                            type: $pt.ComponentConstants.ButtonFooter,
                            buttonLayout: {
                                right: [
                                    {
                                        text: "Exit",
                                        style: "warning",
                                        click: function () {
                                            window.location.href = $pt.getURL('index');
                                        }
                                    }, {
                                        text: "Submit",
                                        style: "primary",
                                        click: function (model) {
                                            $page.controller.submitTransactionReversal();
                                        }
                                    }
                                ]
                            }
                        },
                        pos: {
                            row: 11,
                            width: 12
                        }
                    }
                }
            }
        },
        createFormLayout: function () {
            return {
                _sections: {
                    reversalSection: this.createReversalInfo()
                }
            }
        }
    });
    $page.layout = new Layout();
}(typeof window !== "undefined" ? window : this));