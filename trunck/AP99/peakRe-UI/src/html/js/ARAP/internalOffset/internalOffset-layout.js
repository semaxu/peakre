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
    NTable.registerInlineEditor('inlineReadOnlyDate', {
        comp: {
            type: {type: $pt.ComponentConstants.Date, label: false},
            format: "DD/MM/YYYY",
            enabled: false
        }
    });
    var Layout = jsface.Class({
        createInternalOffset: function () {
            return {
                label: "Internal Offset",
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
                    OffsetInformation: {
                        label: "Offset Information",
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                RegistrationDate: {
                                    label: "Registration Date",
                                    comp: {
                                        type: $pt.ComponentConstants.Date,
                                        format: "DD/MM/YYYY",
                                        required:true
                                    },
                                    pos: {
                                        row: 1
                                    }
                                }
                            }
                        },
                        pos: {
                            row: 1,
                            width: 12
                        }
                    },
                    balanceSearch: {
                        label: "Balance Search",
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                ContractId: {
                                    label: "Contract ID",
                                    comp: {
                                        type: $pt.ComponentConstants.TextArea,
                                        //labelDirection: "vertical",
                                        lines: 3,
                                        visible: {
                                            when: function () {
                                                return $page.viewEnable == "";
                                            }
                                        }
                                    },
                                    pos: {
                                        row: 1,
                                        width: 6
                                    }
                                },
                                PartnerCode: {
                                    label: "Business Partner",
                                    base: $helper.basePartnerSearch(),
                                    comp: {
                                        visible: {
                                            when: function () {
                                                return $page.viewEnable == "";
                                            }
                                        }
                                    },
                                    pos: {
                                        row: 1,
                                        width: 6
                                    }
                                },
                                searchPanel: {
                                    comp: {
                                        type: $pt.ComponentConstants.ButtonFooter,
                                        visible: {
                                            when: function () {
                                                return $page.viewEnable == "";
                                            }
                                        },
                                        buttonLayout: {
                                            right: [
                                                {
                                                    text: "Reset",
                                                    style: "warning",
                                                    click: function () {
                                                        $page.controller.resetBalanceSearch();
                                                    }
                                                }, {
                                                    text: "Search",
                                                    style: "primary",
                                                    click: function (model) {
                                                        console.info(model);
                                                        var postData = {
                                                            ContractId: model.get("ContractId"),
                                                            PartnerCode: model.get("PartnerCode")
                                                        };
                                                        $page.controller.searchSuspense(postData);
                                                    }
                                                }
                                            ]
                                        }
                                    },
                                    pos: {
                                        row: 2,
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
                                }
                            }
                        },
                        pos: {
                            row: 1,
                            width: 12
                        }
                    },
                    debitCreditSearch: {
                        label: "Search Criteria",
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            visible: {
                                when: function () {
                                    return $page.viewEnable == "";
                                }
                            },
                            editLayout: {
                                Condition_ContractId: {
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
                                searchPanel: {
                                    comp: {
                                        type: $pt.ComponentConstants.ButtonFooter,
                                        buttonLayout: {
                                            right: [
                                                {
                                                    text: "Reset",
                                                    style: "warning",
                                                    click: function () {
                                                        $page.controller.resetDebitCredit();
                                                    }
                                                }, {
                                                    text: "Search",
                                                    style: "primary",
                                                    click: function (model) {
                                                        console.info(this.model);
                                                        var postData = {
                                                            BrokerCode: model.get("Condition_BrokerCode"),
                                                            PartnerCode: model.get("Condition_ParterCode"),
                                                            ContractId: model.get("Condition_ContractId"),
                                                            StatementId: model.get("Condition_StatementId"),
                                                            ClaimNo: model.get("Condition_ClaimNo")
                                                        };
                                                        console.info(postData);
                                                        $page.controller.searchCreditDebit(postData);
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
                            row: 4,
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
                                        visible: {
                                            when: function () {
                                                return $page.viewEnable == "";
                                            }
                                        },
                                        click: function () {
                                            $page.controller.submitOffset();
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
        },
        createFormLayout: function () {
            return {
                _sections: {
                    internalOffsetSection: this.createInternalOffset()
                }
            }
        }
    });
    $page.layout = new Layout();
}(typeof window !== "undefined" ? window : this));