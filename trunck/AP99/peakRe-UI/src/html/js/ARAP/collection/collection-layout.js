(function (context) {
    var $page = $pt.getService(context, '$page');
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
    NTable.registerInlineEditor('BalanceTypeSelect', {
        comp: {
            type: {type: $pt.ComponentConstants.Select, label: false},
            data: $page.codes.BalanceType,
            enabled: {
                when: function () {
                    return $page.viewEnable == "";
                }
            }
        }
    });
    NTable.registerInlineEditor('BusinessPartnerCodeSearch', {
        comp: {
            type: {type: $pt.ComponentConstants.BPSearch, label: false},
            searchTriggerDigits: 6,
            enabled: {
                when: function () {
                    return $page.viewEnable == "";
                }
            }
        }
    });
    NTable.registerInlineEditor('CollectionAmountText', {
        comp: {
            type: {type: $pt.ComponentConstants.Text, label: false},
            format: $helper.formatNumber(2)
        },
        pos: {width: 12},
        css: {comp: 'currency-align-right-text'}
    });
    NTable.registerInlineEditor('CollectionCurrencySelect', {
        comp: {
            type: {type: $pt.ComponentConstants.Select, label: false},
            data: $page.codes.Currency
        }
    });
    var Layout = jsface.Class({
        createCollectionInfo: function () {
            return {
                label: "Collection",
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
                    ReceiptInformation: {
                        label: "Collection Information",
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                Payer: {
                                    label: "Payer",
                                    base: $helper.basePartnerSearch(),
                                    comp: {
                                        visible: {
                                            when: function () {
                                                return $page.viewEnable == "";
                                            }
                                        },
                                        enabled: {
                                            when: function () {
                                                return $page.collectInfoEnable;
                                            }
                                        }
                                    },
                                    pos: {
                                        row: 1
                                    }
                                },
                                PayerName: {
                                    label: "Payer",
                                    comp: {
                                        visible: {
                                            when: function () {
                                                return $page.viewEnable != "";
                                            }
                                        },
                                        enabled: {
                                            when: function () {
                                                return $page.collectInfoEnable;
                                            }
                                        },
                                        type: $pt.ComponentConstants.Text
                                    },
                                    pos: {
                                        row: 1
                                    }
                                },
                                CollectionDate: {
                                    label: "Registration Date",
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
                                        row: 1
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
                                        row: 1
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
                                        row: 2
                                    }
                                },
                                Bank: {
                                    label: "Bank",
                                    comp: {
                                    	visible: {
                                            when: function () {
                                                return $page.viewEnable == "";
                                            }
                                        },
                                        enabled: {
                                            when: function () {
                                                return $page.collectInfoEnable;
                                            }
                                        },
                                        type: $pt.ComponentConstants.BankSearch,
                                        searchTriggerDigits: 3
                                    },
                                    pos: {
                                        row: 2
                                    }
                                },
                                BankName: {
                                	label: "Bank",
                                	comp: {
                                		visible: {
                                            when: function () {
                                                return $page.viewEnable != "";
                                            }
                                        },
                                        enabled: {
                                            when: function () {
                                                return $page.collectInfoEnable;
                                            }
                                        },
                                		type: $pt.ComponentConstants.Text
                                	},
                                	pos: {
                                		row: 2
                                	}
                                },
                                BankCharge: {
                                    label: "Bank Charge",
                                    base : $helper.baseAmount(2),
                                    comp: {
                                        enabled: {
                                            when: function () {
                                                return $page.collectInfoEnable;
                                            }
                                        }
                                    },
                                    pos: {
                                        row: 2
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
                                        row: 3,
                                        width: 12
                                    }
                                },
                                CollectionCurrency: {
                                    label: "Collection Currency",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.codes.Currency,
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
                                CollectionAmount: {
                                    label: "Collection Amount",
                                    base : $helper.baseAmount(2),
                                    comp: {
                                        enabled: false
                                    },
                                    pos: {
                                        row: 4
                                    }
                                },
                                NetAmount: {
                                    label: "Net Amount",
                                    base : $helper.baseAmount(2),
                                    comp: {
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
                                CollectToBankAccount: {
                                    label: "Collect To Bank Account",
                                    comp: {
                                        visible: {
                                            when: function () {
                                                return $page.viewEnable == "";
                                            }
                                        },
                                        enabled: {
                                            when: function () {
                                                return $page.collectInfoEnable;
                                            }
                                        },
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.bankAccountColleciton,
                                        parentFilter: {name: "parentId"},
                                        parentPropId: "CollectionCurrency"
                                    },
                                    pos: {
                                        row: 5
                                    }
                                },
                                CollectToBankAccountView: {
                                    label: "Collect To Bank Account",
                                    comp: {
                                        visible: {
                                            when: function () {
                                                return $page.viewEnable != "";
                                            }
                                        },
                                        enabled: {
                                            when: function () {
                                                return $page.collectInfoEnable;
                                            }
                                        },
                                        type: $pt.ComponentConstants.Text
                                    },
                                    pos: {
                                        row: 5
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
                                Condition_ContractIds: {
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
                                Condition_ClaimNumber: {
                                    label: "Claim number",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    }
                                },
                                buttonPanel1: {
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
                                                        var postData = {
                                                            Broker: dialogModel.get("Condition_Broker"),
                                                            PartnerCode: dialogModel.get("Condition_PartnerCode"),
                                                            ContractIds: dialogModel.get("Condition_ContractIds"),
                                                            StatementId: dialogModel.get("Condition_StatementId"),
                                                            ClaimNumber:dialogModel.get("Condition_ClaimNumber"),
                                                            CollectionCurrency:dialogModel.get("CollectionCurrency")
                                                        };
                                                        $page.controller.selectCollection(postData);
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
                                        style: "primary",
                                        click: function () {
                                            $page.controller.saveCollection();
                                        },
                                        visible: {
                                            when: function () {
                                                return $page.viewEnable == "";
                                            }
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
                    collectionInfoSection: this.createCollectionInfo()
                }
            }
        }
    });

    $page.layout = new Layout();
}(typeof window !== "undefined" ? window : this));