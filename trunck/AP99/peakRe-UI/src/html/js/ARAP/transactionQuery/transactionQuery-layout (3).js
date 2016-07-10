(function (context) {
    var $page = $pt.getService(context, '$page');
    NTable.registerInlineEditor('inlineFormatAmount', {
        comp: {
            type: {type: $pt.ComponentConstants.Text, label: false},
            format: $helper.formatNumber(2),
            enabled: {
                when: function (row) {
                    return false;
                }
            }
        }
    });
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
        createTransQuery: function () {
            return {
                label: "Transaction Query",
                style: 'primary-darken',
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
                                        data: $page.codes.TransactionType
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
                                Condition_SettleNo: {
                                    label: "Transaction Number",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    },
                                    pos: {
                                        row: 3
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
                                Condition_StatementId: {
                                    label: "Statement ID",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
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
                                searchButton: {
                                    comp: {
                                        type: $pt.ComponentConstants.ButtonFooter,
                                        buttonLayout: {
                                            right: [
                                                {
                                                    text: "Reset",
                                                    style: "warning",
                                                    click: function () {
                                                        $page.controller.resetTransactionQuery();
                                                    }
                                                }, {
                                                    text: "Search",
                                                    style: "primary",
                                                    click: function (model) {
                                                        $page.controller.searchTransactionQuery(model);
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
                        },
                        pos: {
                            row: 1,
                            width: 12
                        }
                    },
                    SearchResult: {
                        label: "Search Result",
                        comp: {
                            type: $pt.ComponentConstants.Table,
                            searchable: false,
                            sortable: false,
                            addable: false,
                            pageable: true,
                            criteria: 'cachedCriteria',
                            columns: [
                                {
                                    title: "Transaction Number",
                                    data: "TransNo",
                                    width: "12%"
                                }, {
                                    title: "Transaction Type",
                                    data: "TransType",
                                    codes: $page.codes.TransactionType,
                                    width: "10%"
                                }, {
                                    title: "Collection/Payment/Offset Amount",
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
                                                            return $helper.formatNumberForLabel(row.get("Amount"), 2);
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
                                    codes: $page.codes.Currency,
                                    width: "15%"
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
                                        var cpDate = "";
                                        if (undefined != row.CpDate && null != row.CpDate && "" != row.CpDate){
                                            cpDate = moment(row.CpDate,"YYYY-MM-DD").format("DD/MM/YYYY")
                                        }
                                        return cpDate;
                                    }
                                }, {
                                    title: "Operation Date",
                                    data: "TransDate",
                                    width: "10%",
                                    render: function (row) {
                                        var transDate = "";
                                        if (undefined != row.TransDate && null != row.TransDate && "" != row.TransDate){
                                            transDate = moment(row.TransDate,"YYYY-MM-DD").format("DD/MM/YYYY")
                                        }
                                        return transDate;
                                    }
                                }, {
                                    title: "Status",
                                    data: "Status",
                                    codes: $page.codes.SettlementStatus,
                                    width: "5%"
                                }
                            ],
                            rowOperations: [
                                {
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
                            row: 2,
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
                                            window.location.href = $pt.getURL('index');
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
        },
        createFormLayout: function () {
            return {
                _sections: {
                    transQuerySection: this.createTransQuery()
                }
            }
        }
    });
    $page.layout = new Layout();
}(typeof window !== "undefined" ? window : this));
