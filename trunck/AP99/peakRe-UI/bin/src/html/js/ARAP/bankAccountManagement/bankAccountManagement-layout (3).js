(function (context) {
    var $page = $pt.getService(context, '$page');
    var Layout = jsface.Class({
        createBankAccountInfo: function () {
            return {
                label: "Bank Account Management",
                style: "primary-darken",
                layout: {
                    searchCriteria: {
                        label: "Search Criteria",
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                condition_BankAccountName: {
                                    label: "Bank Account Name",
                                    comp: {
                                        type: $pt.ComponentConstants.Text//,
                                        //labelDirection: "vertical"
                                    },
                                    pos: {
                                        row: 1,
                                        width: 6
                                    }
                                },
                                condition_BankAccountNumber: {
                                    label: "Bank Account Number",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    },
                                    pos: {
                                        row: 1,
                                        width: 6
                                    }
                                },
                                condition_Currency: {
                                    label: "Currency",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.codes.Currency
                                    },
                                    pos: {
                                        row: 2,
                                        width: 6
                                    }
                                },
                                condition_Branch: {
                                    label: "Branch",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.codes.Branch
                                    },
                                    pos: {
                                        row: 2,
                                        width: 6
                                    }
                                },
                                searchButton: {
                                    comp: {
                                        type: $pt.ComponentConstants.ButtonFooter,
                                        buttonLayout: {
                                            left:[{
                                                text: 'Create',
                                                style: 'primary',
                                                click: function () {
                                                    var sectionForm = NModalForm.createFormModal("Add Bank Account", '');
                                                    var sectionModel = $pt.createModel();
                                                    var sectionLayout = $pt.createFormLayout({
                                                        Branch: {
                                                            label: "Branch",
                                                            comp: {
                                                                type: $pt.ComponentConstants.Select,
                                                                minimumResultsForSearch: -1,
                                                                data: $page.codes.Branch,
                                                                placeholder: "please select...",
                                                                required: true
                                                            },
                                                            pos: {
                                                                row: 1,
                                                                width: 6
                                                            }
                                                        },
                                                        BankAccountNumber: {
                                                            label: "Account Number",
                                                            comp: {
                                                                type: $pt.ComponentConstants.Text,
                                                                required: true
                                                            },
                                                            pos: {
                                                                row: 1,
                                                                width: 6
                                                            }
                                                        },
                                                        BankAccountName: {
                                                            label: "Account Name",
                                                            comp: {
                                                                type: $pt.ComponentConstants.Text,
                                                                required: true
                                                            },
                                                            pos: {
                                                                row: 2,
                                                                width: 6
                                                            }
                                                        },
                                                        Currency: {
                                                            label: "Currency",
                                                            comp: {
                                                                type: $pt.ComponentConstants.Select,
                                                                data: $page.codes.Currency,
                                                                placeholder: "please select...",
                                                                required: true
                                                            },
                                                            pos: {
                                                                row: 2,
                                                                width: 6
                                                            }
                                                        },
                                                        Bank: {
                                                            label: "Bank",
                                                            comp: {
                                                                type: $pt.ComponentConstants.BankSearch,
                                                                searchTriggerDigits: 3,
                                                                required: true
                                                            },
                                                            pos: {
                                                                row: 3,
                                                                width: 6
                                                            }
                                                        },
                                                        AccountType: {
                                                            label: "Account Type",
                                                            comp: {
                                                                type: $pt.ComponentConstants.Select,
                                                                minimumResultsForSearch: -1,
                                                                data: $page.codes.AccountType,
                                                                placeholder: "please select...",
                                                                required: true
                                                            },
                                                            pos: {
                                                                row: 3,
                                                                width: 6
                                                            }
                                                        }
                                                    });
                                                    sectionModel.set("Bank", "001");
                                                    sectionForm.show({
                                                        model: sectionModel,
                                                        layout: sectionLayout,
                                                        buttons: {
                                                            reset: false,
                                                            validate: false,
                                                            cancel: false,
                                                            right: [{
                                                                icon: 'eject',
                                                                text: 'Cancel',
                                                                style: 'warning',
                                                                click: function () {
                                                                    sectionForm.hide();
                                                                }
                                                            }, {
                                                                icon: 'save',
                                                                text: 'Save',
                                                                style: 'primary',
                                                                click: function (dialogModel) {
                                                                    var result = $page.controller.checkInputValue(dialogModel);
                                                                    if (!result) {
                                                                        return;
                                                                    }
                                                                    if ($page.controller.saveBankAccount(dialogModel.getCurrentModel())){
                                                                        sectionForm.hide();
                                                                    }
                                                                }
                                                            }]
                                                        },
                                                        draggable: false
                                                    });
                                                }
                                            }],
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
                                                    click: function () {
                                                        $page.controller.searchBankAccount();
                                                    }
                                                }
                                            ]
                                        }
                                    },
                                    pos: {
                                        row: 2,
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
                    searchTable: {
                        label: "Search Result",
                        comp: {
                            type: $pt.ComponentConstants.Table,
                            searchable: false,
                            sortable: false,
                            pageable: true,
                            addable: false,
                            criteria: 'cachedCriteria',
                            //removable: true,
                            columns: [
                                {
                                    title: "Branch",
                                    data: "BranchId",
                                    //inline: "select",
                                    codes: $page.codes.Branch,
                                    width: "12%"
                                }, {
                                    title: "Bank Account Number",
                                    data: "AccountNo",
                                    //inline: "text",
                                    width: "18%"
                                }, {
                                    title: "Bank Account Name",
                                    data: "AccountName",
                                    //inline: "text",
                                    width: "18%"
                                }, {
                                    title: "Currency",
                                    data: "CurrencyCode",
                                    //inline: "select",
                                    codes: $page.codes.Currency,
                                    width: "13%"
                                }, {
                                    title: "Bank",
                                    data: "BankId",
                                    codes: $page.codes.Bank,
                                    width: "12%"
                                }, {
                                    title: "Account Type",
                                    data: "AccountType",
                                    //inline: "select",
                                    codes: $page.codes.AccountType,
                                    width: "15%"
                                }, {
                                    title: "Status",
                                    data: "InUse",
                                    //inline: "select",
                                    codes: $page.codes.AccountStatus,
                                    width: "12%"
                                }
                            ],
                            rowOperations: [{
                                tooltip: "Edit",
                                enabled:{
                                    depends:"InUse",
                                    when:function(rowModel){
                                        var result = true;
                                        if (rowModel.get("InUse") == 2){
                                            result = false;
                                        }
                                        return result;
                                    }
                                },
                                click: function (rowModel) {
                                    var sectionForm2 = NModalForm.createFormModal("Edit Bank Account", '');
                                    var sectionModel2 = $pt.createModel();
                                    var sectionLayout2 = $pt.createFormLayout({
                                        AccountId: {
                                            label: "Account ID",
                                            comp: {
                                                type: $pt.ComponentConstants.Text,
                                                visible: {
                                                    when: function () {
                                                        return false;
                                                    }
                                                },
                                                pos: {
                                                    row: 1,
                                                    width: 6
                                                }
                                            }
                                        },
                                        Status: {
                                            label: "Status",
                                            comp: {
                                                type: $pt.ComponentConstants.Text,
                                                visible: {
                                                    when: function () {
                                                        return false;
                                                    }
                                                },
                                                pos: {
                                                    row: 1,
                                                    width: 6
                                                }
                                            }
                                        },
                                        Branch: {
                                            label: "Branch",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                minimumResultsForSearch: -1,
                                                data: $page.codes.Branch,
                                                required: true,
                                                placeholder: "please select..."
                                            },
                                            pos: {
                                                row: 1,
                                                width: 6
                                            }
                                        },
                                        BankAccountNumber: {
                                            label: "Account Number",
                                            comp: {
                                                type: $pt.ComponentConstants.Text,
                                                required: true
                                            },
                                            pos: {
                                                row: 1,
                                                width: 6
                                            }
                                        },
                                        BankAccountName: {
                                            label: "Account Name",
                                            comp: {
                                                type: $pt.ComponentConstants.Text,
                                                required: true
                                            },
                                            pos: {
                                                row: 2,
                                                width: 6
                                            }
                                        },
                                        Currency: {
                                            label: "Currency",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                data: $page.codes.Currency,
                                                required: true,
                                                placeholder: "please select..."
                                            },
                                            pos: {
                                                row: 2,
                                                width: 6
                                            }
                                        },
                                        Bank: {
                                            label: "Bank",
                                            comp: {
                                                type: $pt.ComponentConstants.BankSearch,
                                                searchTriggerDigits: 3,
                                                required: true
                                            },
                                            pos: {
                                                row: 3,
                                                width: 6
                                            }
                                        },
                                        AccountType: {
                                            label: "Account Type",
                                            comp: {
                                                type: $pt.ComponentConstants.Select,
                                                minimumResultsForSearch: -1,
                                                data: $page.codes.AccountType,
                                                required: true,
                                                placeholder: "please select..."
                                            },
                                            pos: {
                                                row: 3,
                                                width: 6
                                            }
                                        }
                                    });
                                    var bankCode = "";
                                    var bankId = parseInt(rowModel.BankId);
                                    if (bankId < 10){
                                        bankCode = "00" + bankId;
                                    }
                                    else if (bankId < 100){
                                        bankCode = "0" + bankId;
                                    }
                                    sectionModel2.set("AccountId", rowModel.AccountId);
                                    sectionModel2.set("Branch", rowModel.BranchId);
                                    sectionModel2.set("BankAccountNumber", rowModel.AccountNo);
                                    sectionModel2.set("BankAccountName", rowModel.AccountName);
                                    sectionModel2.set("Currency", rowModel.CurrencyCode);
                                    sectionModel2.set("Bank", bankCode);
                                    sectionModel2.set("AccountType", rowModel.AccountType);
                                    sectionModel2.set("Status", rowModel.InUse);

                                    sectionForm2.show({
                                        model: sectionModel2,
                                        layout: sectionLayout2,
                                        buttons: {
                                            reset: false,
                                            validate: false,
                                            cancel: false,
                                            right: [{
                                                icon: 'eject',
                                                text: 'Cancel',
                                                style: 'warning',
                                                click: function () {
                                                    sectionForm2.hide();
                                                }
                                            }, {
                                                icon: 'save',
                                                text: 'Update',
                                                style: 'primary',
                                                click: function (updateModel) {
                                                    var result = $page.controller.checkInputValue(updateModel);
                                                    if (!result) {
                                                        return;
                                                    }
                                                    if ($page.controller.saveBankAccount(updateModel.getCurrentModel())){
                                                        sectionForm2.hide();
                                                    }
                                                }
                                            }]
                                        },
                                        draggable: false
                                    });
                                }
                            },{
                                tooltip: "Delete",
                                enabled:{
                                    depends:"InUse",
                                    when:function(rowModel){
                                        var result = true;
                                        if (rowModel.get("InUse") == 2){
                                            result = false;
                                        }
                                        return result;
                                    }
                                },
                                click: function (rowModel) {
                                    $page.controller.deleteBankAccount(rowModel);
                                }
                            }]
                        },
                        /*evt: {
                            pageChange: function (evt) {
                                $page.controller.doPageJump(evt.criteria, evt.target);
                            }
                        },*/
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
                    collectionInfoSection: this.createBankAccountInfo()
                }
            }
        }
    });
    $page.layout = new Layout();
}(typeof window !== "undefined" ? window : this));