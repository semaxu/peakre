(function (context) {
    var $page = $pt.getService(context, "$page");
    //var tableViewModel = $pt.createModel({'contractId-Contract1': true});
    NTable.registerInlineEditor('numberForPayment2', {
        comp: {
            type: {type: $pt.ComponentConstants.Text, label: false},
            format: $helper.formatNumber(2),
            enabled: {
                when: function () {
                    return $page.viewEnable == "";
                }
            }
        },
        pos: {width: 12},
        css: {comp: 'currency-align-right-text'}
    });
    NTable.registerInlineEditor('inlineReadOnlyText', {
        comp: {
            type: {type: $pt.ComponentConstants.Text, label: false},
            enabled: false
        }
    });
    NTable.registerInlineEditor('inlineReadOnlyDate', {
        comp: {
            type: {type: $pt.ComponentConstants.Date, label: false},
            format: "DD/MM/YYYY",
            enabled: false
        }
    });
    NTable.registerInlineEditor('PayeePayer', {
        comp: {
            type: {type: $pt.ComponentConstants.BPSearch, label: false},
            searchTriggerDigits: 6,
            enabled: {
                when: function () {
                    return $page.viewEnable == "" && $page.collectInfoEnable;
                }
            }
        }
    });
    NTable.registerInlineEditor('PaymentAmountText', {
        comp: {
            type: {type: $pt.ComponentConstants.Text, label: false},
            format: $helper.formatNumber(2),
            enabled: {
                when: function () {
                    return $page.viewEnable == "" && $page.collectInfoEnable;
                }
            }
        },
        css: {comp: 'currency-align-right-text'}
    });
    var Layout = jsface.Class({
        createPaymentInfo: function () {
            return {
                label: "Payment",
                style: "primary-darken",
                layout: {
                	TransactionInformation: {
                        label: "Transaction Information",
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            visible: {
                                when: function () {
                                    return $page.viewEnable != "";
                                }
                            },
                            editLayout: {
                                TransNumber: {
                                    label: "Transaction number",
                                    comp: {
                                    	type: $pt.ComponentConstants.Text,
                                    	enabled: false
                                    },
                                    pos: {
                                        row: 1
                                    }
                                },
                                TransStatus: {
                                    label: "Status",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.codes.SettlementStatus,
                                        enabled: false
                                    },
                                    pos: {
                                        row: 1
                                    }
                                },
                                OperationDate: {
                                    label: "Operation Date",
                                    comp: {
                                        type: $pt.ComponentConstants.Date,
                                        format: "DD/MM/YYYY",
                                        enabled: {
                                            when: function () {
                                                return true;
                                            }
                                        }
                                    },
                                    pos: {
                                        row: 1
                                    }
                                },
                                OperatorId: {
                                    label: "Operator",
                                    comp: {
                                    	type: $pt.ComponentConstants.Select,
                                    	data: $page.model.userCodes
                                    },
                                    pos: {
                                        row: 2
                                    }
                                }
                            }
                        },
                        pos: {
                            row: 1,
                            width: 12
                        }
                    },
                    paymentInformation: {
                        label: "Payment Information",
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                PaymentCurrency: {
                                    label: "Payment Currency",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.codes.Currency,
                                        enabled: {
                                            when: function () {
                                                return $page.collectInfoEnable && $page.paymentInfoEnable;
                                            }
                                        }
                                    },
                                    pos: {
                                        row: 3
                                    }
                                },
                                PaymentDate: {
                                    label: "Registration Date",
                                    comp: {
                                        type: $pt.ComponentConstants.Date,
                                        format: "DD/MM/YYYY",
                                        enabled: {
                                            when: function () {
                                                return $page.collectInfoEnable && $page.paymentInfoEnable;
                                            }
                                        }
                                    },
                                    pos: {
                                        row: 3
                                    }
                                },
                                ValueDate: {
                                    label: "Value Date",
                                    comp: {
                                        type: $pt.ComponentConstants.Date,
                                        format: "DD/MM/YYYY",
                                        enabled: {
                                            when: function () {
                                                return $page.collectInfoEnable;
                                            }
                                        }
                                    },
                                    pos: {
                                        row: 3
                                    }
                                },
                                PaymentMethod: {
                                    label: "Payment Method",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.codes.PaymentMethod,
                                        enabled: {
                                            when: function () {
                                                return $page.collectInfoEnable;
                                            }
                                        }
                                    },
                                    pos: {
                                        row: 4
                                    }
                                },
                                PaidAccount: {
                                    label: "Paid from Bank Account",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.bankAccountCodeTable,
                                        parentFilter: {name: "parentId"},
                                        parentPropId: "PaymentCurrency",
                                        enabled: {
                                            when: function () {
                                                return $page.collectInfoEnable && $page.paymentInfoEnable;
                                            }
                                        },
                                        visible: {
                                            when: function () {
                                                return $page.viewEnable == "";
                                            }
                                        }
                                    },
                                    pos: {
                                        row: 4
                                    }
                                },
                                PaidAccountView: {
                                    label: "Paid from Bank Account",
                                    comp: {
                                        visible: {
                                            when: function () {
                                                return $page.viewEnable != "";
                                            }
                                        },
                                        type: $pt.ComponentConstants.Text
                                    },
                                    pos: {
                                        row: 4
                                    }
                                },
                                BankCharge: {
                                    label: "Bank Charge",
                                    base : $helper.baseAmount(2),
                                    comp: {
                                        enabled: {
                                            when: function () {
                                                return $page.collectInfoEnable && $page.paymentInfoEnable;
                                            }
                                        }
                                    },
                                    pos: {
                                        row: 4
                                    }
                                },
                                chequePanel: {
                                    comp: {
                                        type: $pt.ComponentConstants.Panel,
                                        visible: {
                                            depends: "PaymentMethod",
                                            when: function () {
                                                return (this.getModel().get("PaymentMethod") == 2);
                                            }
                                        },
                                        editLayout: {
                                            ChequeNumber: {
                                                label: "Cheque Number",
                                                comp: {
                                                    type: $pt.ComponentConstants.Text,
                                                    enabled: {
                                                        when: function () {
                                                            return $page.collectInfoEnable;
                                                        }
                                                    }
                                                }
                                            },
                                            ChequeDate: {
                                                label: "Cheque Date",
                                                comp: {
                                                    type: $pt.ComponentConstants.Date,
                                                    format: "DD/MM/YYYY",
                                                    enabled: {
                                                        when: function () {
                                                            return $page.collectInfoEnable;
                                                        }
                                                    }
                                                }
                                            },
                                            ChequeHolderName: {
                                                label: "Cheque Holder Name",
                                                comp: {
                                                    type: $pt.ComponentConstants.Text,
                                                    enabled: {
                                                        when: function () {
                                                            return $page.collectInfoEnable;
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    },
                                    pos: {
                                        row: 5,
                                        width: 12
                                    }
                                },
                                TotalAmount: {
                                    label: "Total Payment Amount",
                                    base : $helper.baseAmount(2),
                                    comp: {
                                        enabled: false
                                    },
                                    pos: {
                                        row: 6
                                    }
                                },
                                Payee: {
                                    comp: {
                                        type: $pt.ComponentConstants.PayeeTable,
                                        enabled: {
                                            when: function () {
                                                return $page.collectInfoEnable;
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
                            row: 1,
                            width: 12
                        }
                    },
                    searchCriteria: {
                        label: "Search Criteria",
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            visible: {
                                when: function () {
                                    return $page.viewEnable == "";
                                }
                            },
                            editLayout: {
                                Condition_ContractID: {
                                    label: "Contract ID",
                                    comp: {
                                        type: $pt.ComponentConstants.TextArea,
                                        lines: 3
                                    }
                                },
                                Condition_Broker: {
                                    label: "Broker",
                                    base: $helper.basePartnerSearch()
                                },
                                Condition_PartnerCode: {
                                    label: "Cedent/Retrocessionaire",
                                    base: $helper.basePartnerSearch()
                                },
                                Condition_StatementId: {
                                    label: "Statement ID",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    }
                                },
                                Condition_ClaimNo: {
                                    label: "Claim Number",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    }
                                },
                                searchButtons: {
                                    comp: {
                                        type: $pt.ComponentConstants.ButtonFooter,
                                        buttonLayout: {
                                            right: [
                                                {
                                                    text: "Reset",
                                                    style: "warning",
                                                    click: function () {
                                                        $page.controller.reset();
                                                    }
                                                }, {
                                                    text: "Search",
                                                    style: "primary",
                                                    click: function (dialogModel) {
                                                        var paymentDate = dialogModel.get("PaymentDate");
                                                        var postData = {
                                                            ContractID: dialogModel.get("Condition_ContractID"),
                                                            Broker: dialogModel.get("Condition_Broker"),
                                                            PartnerCode:dialogModel.get("Condition_PartnerCode"),
                                                            StatementId: dialogModel.get("Condition_StatementId"),
                                                            ClaimNo:dialogModel.get("Condition_ClaimNo"),
                                                            PaymentCurrency:dialogModel.get("PaymentCurrency")
                                                        };
                                                        $page.controller.queryPayment(postData);
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
                            row: 2,
                            width: 12
                        }
                    },
                    detailInfo: {
                        comp: {
                            type: $pt.ComponentConstants.ARAPCreditDebit,
                            centralId: 'ARAPCreditDebitId'
                        },
                        pos: {
                            row: 3,
                            width: 12
                        }
                    },
                    Balances: {
                        comp: {
                            type: $pt.ComponentConstants.BalanceTable
                        },
                        pos: {
                            row: 3,
                            width: 12
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
                            row: 5,
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
                                            if($page.viewEnable != ""){
                                                window.close();
                                            }else {
                                                window.location.href = $pt.getURL('index');
                                            }
                                        }
                                    }, {
                                        text: "Submit",
                                        visible: {
                                            when: function () {
                                                return $page.viewEnable == "";
                                            }
                                        },
                                        style: "primary",
                                        click: function () {
                                            $page.controller.savePayment();
                                        }
                                    }
                                ]
                            }
                        },
                        pos: {
                            row: 5,
                            width: 12
                        }
                    }
                }
            }
        },
        createFormLayout: function () {
            return {
                _sections: {
                    paymentInfoSection: this.createPaymentInfo()
                }
            }
        }
    });

    $page.layout = new Layout();
}(typeof window !== "undefined" ? window : this));