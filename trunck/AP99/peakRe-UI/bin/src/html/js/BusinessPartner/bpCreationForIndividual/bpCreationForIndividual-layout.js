(function (context) {
    var $page = $pt.getService(context, '$page');


    var Layout = jsface.Class({
        createHeadInfo: function () {
        return{
            layout: {
                buttonPanel: {
                    comp: {
                        type: $pt.ComponentConstants.ButtonFooter,
                        buttonLayout: {
                            right: [
                                {
                                    text: "History",
                                    style: "link",
                                    click: function (model) {
                                        window.open("bpChangeHistory.html?partnerId="+model.get('PartnerId'));
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

        createMainInfo: function () {
        return {
            label: "Business Partner Maintenance",
            style: "primary-darken",
            collapsible: false,
            layout: {
                    basicInformation: {
                        label: "Basic Information",
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                BusinessPartnerCategory: {
                                    label: "Business Partner Category",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.BusinessPartnerCategory,
                                        enabled:false
                                    },
                                    pos: {
                                        width: 6,
                                        row: 1
                                    }
                                },
                                BusinessPartnerId: {
                                    label: "Business Partner ID",
                                    comp: {
                                        type: $pt.ComponentConstants.Text,
                                        enabled: false
                                    },
                                    pos: {
                                        width: 6,
                                        row: 1
                                    }
                                },
                                Status: {
                                    label: "Status",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.Status
                                    },
                                    pos: {
                                        width: 6,
                                        row: 2
                                    }
                                },
                                Creator: {
                                    label: "Creator",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data:  $page.codes.SystemUser
                                    },
                                    pos: {
                                        width: 6,
                                        row: 2
                                    }
                                },
                                CreateDate: {
                                    label: "Created on",
                                    comp: {
                                        type: $pt.ComponentConstants.Date,
                                        format: "DD/MM/YYYY",
                                        enabled :false
                                    },
                                    pos: {
                                        width: 6,
                                        row: 3
                                    }
                                },
                                RoleSelectIds: {
                                    label: "Business Partner Role",
                                    comp: {
                                        type: $pt.ComponentConstants.SelectTree,
                                        data: $page.partnerRole,
                                        // root: true,
                                        // check: true,
                                        hideChildWhenParentChecked: true,
                                     //   labelWidth: 3,
                                        enabled:false,
                                        treeLayout: {
                                            comp: {
                                                hierarchyCheck: true,
                                                expandLevel: "all",
                                                inactiveSlibing: false,
                                                valueAsArray: true
                                            }
                                        }
                                    },
                                    pos: {
                                        row: 3,
                                        width: 6
                                    }
                                }
                            }
                        },
                        pos: {
                            width: 12
                        }
                    },
                    businessInformation: {
                        label: "Personal Information",
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                FirstName: {
                                    label: "First Name",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    },
                                    pos: {
                                        row: 1,
                                        width: 6
                                    }
                                },
                                LastName: {
                                    label: "Last Name",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    },
                                    pos: {
                                        row: 1,
                                        width: 6
                                    }
                                },
                                IdType: {
                                    label: "ID Type",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.codes.IDType
                                    },
                                    pos: {
                                        row: 2,
                                        width: 6
                                    }
                                },
                                IdNumber: {
                                    label: "ID Number",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    },
                                    pos: {
                                        row: 2,
                                        width: 6
                                    }
                                },
                                DuedateOfIdCertification: {
                                    label: "Due Date Of ID Certification",
                                    comp: {
                                        type: $pt.ComponentConstants.Date,
                                        format: "DD/MM/YYYY"
                                    },
                                    pos: {
                                        row: 3,
                                        width: 6
                                    }
                                },
                                DateOfBirth: {
                                    label: "Date of Birth",
                                    comp: {
                                        type: $pt.ComponentConstants.Date,
                                        format: "DD/MM/YYYY"
                                    },
                                    pos: {
                                        row: 3,
                                        width: 6
                                    }
                                },
                                Gender: {
                                    label: "Gender",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.codes.Gender
                                    },
                                    pos: {
                                        row: 4,
                                        width: 6
                                    }
                                },
                                Country: {
                                    label: "Country",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.codes.CedentCountry
                                    },
                                    pos: {
                                        row: 4,
                                        width: 6
                                    }
                                }
                            }
                        },
                        pos: {
                            width: 12
                        }
                    },
                    contactInformation: {
                    label: "Contact Information",
                    comp: {
                        type: $pt.ComponentConstants.Panel,
                        editLayout: {
                            ContactTable: {
                                comp: {
                                    type: $pt.ComponentConstants.Table,
                                    searchable: false,
                                    sortable: false,
                                    addable: true,
                                    removable: false,
                                    editable: false,
                                    columns: [
                                        {
                                            title: "Contact Person",
                                            inline: "text",
                                            data: "ContactPerson",
                                            width: "15%"
                                        }, {
                                            title: "Contact Title",
                                            inline: "text",
                                            data: "ContactTitle",
                                            width: "15%"
                                        }, {
                                            title: "Email Address",
                                            inline: "text",
                                            data: "EmailAddress",
                                            width: "20%"
                                        }, {
                                            title: "Telephone Number",
                                            inline: "text",
                                            data: "TelephoneNumber",
                                            width: "20%"
                                        }, {
                                            title: "Remarks",
                                            inline: "text",
                                            data: "Remark",
                                            width: "20%"
                                        }
                                    ],
                                    addClick: function (model) {
                                        model.add("ContactTable", $.extend(true, {}, $page.model.getContactTable));
                                    },
                                    rowOperations: [
                                        {
                                            tooltip: "delete",
                                            visible: {
                                                when: function (row) {
                                                    return $page.pageType == 0;
                                                }
                                            },
                                            click: function (rowModel) {
                                                this.getModel().remove(this.getDataId(), rowModel);
                                            }
                                        }
                                    ]
                                },
                                pos: {
                                    row: 1,
                                    width: 12
                                }
                            }
                        }
                    },
                    pos: {
                        width: 12
                    }
                },
                    bankAccountInfomation: {
                    label: "Bank Account Information",
                    comp: {
                        type: $pt.ComponentConstants.Panel,
                        editLayout: {
                            BankAccountTable: {
                                comp: {
                                    type: $pt.ComponentConstants.Table,
                                    searchable: false,
                                    sortable: false,
                                    addable: true,
                                    removable: false,
                                    editable: false,
                                    columns: [
                                        {
                                            title: "Account Holder Type",
                                            data: "AccountHolderType",
                                            inline: "select",
                                            codes: $page.codes.AccountHolderType,
                                            width: "15%"
                                        }, {
                                            title: "Account Holder Name",
                                            inline: "text",
                                            data: "AccountHolderName",
                                            width: "15%"
                                        }, {
                                            title: "Bank Code",
                                            data: "BankCode",
                                            inline: "text",
                                            codes: $page.bankAccountCodeTable,
                                            width: "20%"
                                        }, {
                                            title: "Account Number",
                                            inline: "text",
                                            data: "AccountNumber",
                                            width: "20%"
                                        }, {
                                            title: "Debit/Credit",
                                            data: "DebitCredit",
                                            inline: "select",
                                            codes: $page.DebitOrCredit,
                                            width: "15%"
                                        }, {
                                            title: "Account Status",
                                            data: "AccountStatus",
                                            inline: "select",
                                            codes: $page.Status,
                                            width: "15%"
                                        }
                                    ],
                                    addClick: function (model) {
                                        model.add("BankAccountTable", $.extend(true, {}, $page.model.getBankAccountTable()));
                                    },
                                    rowOperations: [
                                        {
                                            tooltip: "delete",
                                            visible: {
                                                when: function (row) {
                                                    return $page.pageType == 0;
                                                }
                                            },
                                            click: function (rowModel) {
                                                this.getModel().remove(this.getDataId(), rowModel);
                                            }
                                        }
                                    ]
                                },
                                pos: {
                                    row: 1,
                                    width: 12
                                }
                            }
                        }
                    },
                    pos: {
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
                                        click:function(){
                                            window.location.href = "businessPartnerSearch.html";
                                        }
                                    }, {
                                        text: "Save",
                                        style: "primary",
                                        enabled: {
                                            depends: "status",
                                            when: function (model) {
                                                return $page.pageType == 0;
                                            }
                                        },
                                        click: function () {
                                            $page.controller.saveData();
                                        }
                                    }, {
                                        text: "Submit",
                                        style: "primary",
                                        enabled: {
                                            depends: "status",
                                            when: function (model) {
                                                return $page.pageType == 0;
                                            }
                                        },
                                        click: function () {
                                            $page.controller.submitData();
                                        }
                                    }, {
                                        text: "Attachment",
                                        style: "primary",
                                        enabled: {
                                            depends: "status",
                                            when: function (model) {
                                                return $page.pageType == 0;
                                            }
                                        },
                                        click: function (model) {
                                            $pt.viewAttachment (1, model.getCurrentModel().PartnerId, 0);
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
        };
    },

        createFormLayout: function () {
        return {
            _sections: {
                headInfoSection:this.createHeadInfo(),
                claimInfoSection: this.createMainInfo()
            }
        }
    }

    });

    $page.layout = new Layout();

}(typeof window !== "undefined" ? window : this));