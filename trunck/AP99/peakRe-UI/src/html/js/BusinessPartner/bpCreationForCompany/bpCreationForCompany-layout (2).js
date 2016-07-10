(function (context) {
    var $page = $pt.getService(context, '$page');
    NTable.registerInlineEditor("BPSearch", {
        comp : {
            type : {type: $pt.ComponentConstants.BPSearch, label : false},
            searchTriggerDigits: 6
        }
    });

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
                                        enabled: false
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
                                TradingName: {
                                    label: "Trading Name",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    },
                                    pos: {
                                        width: 6,
                                        row: 2
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
                                        data: $page.codes.SystemUser
                                    },
                                    pos: {
                                        width: 6,
                                        row: 3
                                    }
                                },
                                CreateDate: {
                                    label: "Created on",
                                    comp: {
                                        type: $pt.ComponentConstants.Date,
                                        format: "DD/MM/YYYY",
                                        enabled: false
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
                                      //  labelWidth: 3,
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
                    businessInformation: {
                        label: "Business Information",
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                RegistrationName: {
                                    label: "Registration Name",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    },
                                    pos: {
                                        row: 1,
                                        width: 6
                                    }
                                },
                                ShortName: {
                                    label: "Short Name",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    },
                                    pos: {
                                        row: 1,
                                        width: 6
                                    }
                                },
                                OrganizationIdType: {
                                    label: "Organization ID Type",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.codes.OrganizationIdType
                                    },
                                    pos: {
                                        row: 2,
                                        width: 6
                                    }
                                },
                                BusinessRegistrationCode: {
                                    label: "Business Registration Code",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    },
                                    pos: {
                                        row: 2,
                                        width: 6
                                    }
                                },
                                DateOfRegistration: {
                                    label: "Date of Registration",
                                    comp: {
                                        type: $pt.ComponentConstants.Date,
                                        format: "DD/MM/YYYY"
                                    },
                                    pos: {
                                        row: 3,
                                        width: 6
                                    }
                                },
                                DuedateOfRegistration: {
                                    label: "Issue Date",
                                    comp: {
                                        type: $pt.ComponentConstants.Date,
                                        format: "DD/MM/YYYY"
                                    },
                                    pos: {
                                        row: 3,
                                        width: 6
                                    }
                                },
                                Country: {
                                    label: "Country",
                                    comp: {
                                        type:$pt.ComponentConstants.Select,
                                        data:$page.codes.CedentCountry
                                    },
                                    pos: {
                                        row: 4,
                                        width: 6
                                    }
                                },
                                City: {
                                    label: "City",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    },
                                    pos: {
                                        row: 4,
                                        width: 6
                                    }
                                },
                                Disctrict: {
                                    label: "District",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    },
                                    pos: {
                                        row: 5,
                                        width: 6
                                    }
                                },
                                Street: {
                                    label: "Street",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    },
                                    pos: {
                                        row: 5,
                                        width: 6
                                    }
                                },
                                Number: {
                                    label: "Number",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    },
                                    pos: {
                                        row: 6,
                                        width: 6
                                    }
                                },
                                Building: {
                                    label: "Building",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    },
                                    pos: {
                                        row: 6,
                                        width: 6
                                    }
                                },
                                Floor: {
                                    label: "Floor",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    },
                                    pos: {
                                        row: 7,
                                        width: 6
                                    }
                                },
                                Room: {
                                    label: "Room",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    },
                                    pos: {
                                        row: 7,
                                        width: 6
                                    }
                                },
                                CmsNo: {
                                    label: "CMS No",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    },
                                    pos: {
                                        row: 8,
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
                                            model.add("ContactTable", $.extend(true, {}, $page.model.getContactTable()));
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
                                        //        codes: $page.bankAccountCodeTable,
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
                    relationship: {
                        label: "Relationship",
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                RelationshipTable: {
                                    comp: {
                                        type: $pt.ComponentConstants.Table,
                                        searchable: false,
                                        sortable: false,
                                        removable: false,
                                        addable: true,
                                        columns: [
                                            {
                                                title: "Relationship Type",
                                                data: "RelationshipType",
                                                inline: "select",
                                                codes: $page.codes.RelationshipType,
                                                width: "40%"
                                            }, {
                                                title: "Related Business Partner",
                                                data: "BusinessPartnerId",
                                                inline : "BPSearch",
                                                width: "60%"
                                            }
                                        ],
                                        addClick: function (model) {
                                            model.add("RelationshipTable", $.extend(true, {}, $page.model.getRelationshipTable));
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
                                    css: {
                                        comp: "inline-editor"
                                    },
                                    pos: {
                                        width: 12
                                    }
                                }
                            }
                        },
                        pos: {
                            width: 12
                        }
                    },
                    formTab: {
                        label: "AML Check",
                        comp: {
                            type: $pt.ComponentConstants.Tab,
                            justified: false,
                            style: 'primary',
                            //tabType:"pill",
                            "titleDirection": "horizontal",
                            //canRemove:true,
                            tabs: [
                                {
                                    label: "Cedent/Retrocessionaire/MGA",
                                    editLayout: {
                                        amlCheckPanel: {
                                            label: "AML Check",
                                            comp: {
                                                type: $pt.ComponentConstants.Panel,
                                                editLayout: {
                                                    AmlCheckTable: {
                                                        comp: {
                                                            type: $pt.ComponentConstants.Table,
                                                            searchable: false,
                                                            sortable: false,
                                                            addable: true,
                                                            removable: false,
                                                            editable: false,
                                                            columns: [
                                                                {
                                                                    title: "Check Year",
                                                                    data: "CheckYear",
                                                                    width: "10%"
                                                                }, {
                                                                    title: "Check Date",
                                                                    data: "CheckDate",
                                                                    width: "10%",
                                                                    render: function (row) {
                                                                        return $helper.formatDateForTableView(row.CheckDate, "DD/MM/YYYY");
                                                                    }
                                                                }, {
                                                                    title: "AML Check Result",
                                                                    data: "AmlCheckResult",
                                                                    codes: $page.ApprovedOrDeclined,
                                                                    width: "10%"
                                                                }, {
                                                                    title: "S&P",
                                                                    data: "SpText",
                                                                    width: "10%"
                                                                }, {
                                                                    title: "A.M.Best",
                                                                    data: "AmbestText",
                                                                    width: "10%"
                                                                }, {
                                                                    title: "Checked By",
                                                                    data: "CheckBy",
                                                                    codes:$page.codes.SystemUser,
                                                                    width: "10%"
                                                                }, {
                                                                    title: "Approved By",
                                                                    data: "ApprovedBy",
                                                                    codes: $page.codes.SystemUser,
                                                                    width: "10%"
                                                                }, {
                                                                    title: "Remarks",
                                                                    data: "Remarks",
                                                                    width: "20%"
                                                                }
                                                            ],
                                                            addClick: function (model, rowModel, layout) {
                                                                $page.controller.addCompliance(model, rowModel, layout);
                                                            },
                                                            rowOperations: [
                                                                {
                                                                    tooltip: "Edit",
                                                                    visible: {
                                                                        when: function (row) {
                                                                            return $page.pageType == 0;
                                                                        }
                                                                    },
                                                                    click: function (rowModel) {
                                                                        $page.controller.updateCompliance(rowModel);
                                                                    }
                                                                },   {
                                                                    tooltip: "View",
                                                                    visible: {
                                                                        when: function (row) {
                                                                            return $page.pageType == 1;
                                                                        }
                                                                    },
                                                                    click: function (rowModel) {
                                                                        $page.controller.updateCompliance(rowModel);
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
                                        }

                                    }

                                    //icon: "bookmark",
                                },
             /*                   {
                                    label: "Retrocessionaire",
                                    editLayout: {
                                        retroAmlCheckPanel: {
                                            label: "AML Check",
                                            comp: {
                                                type: $pt.ComponentConstants.Panel,
                                                editLayout: {
                                                    RetroAmlCheckTable: {
                                                        comp: {
                                                            type: $pt.ComponentConstants.Table,
                                                            searchable: false,
                                                            sortable: false,
                                                            addable: true,
                                                            removable: false,
                                                            editable: false,
                                                            columns: [
                                                                {
                                                                    title: "Check Year",
                                                                    data: "CheckYear",
                                                                    width: "10%"
                                                                }, {
                                                                    title: "Check Date",
                                                                    data: "CheckDate",
                                                                    width: "10%",
                                                                    render: function (row) {
                                                                        return $helper.formatDateForTableView(row.CheckDate, "DD/MM/YYYY");
                                                                    }
                                                                }, {
                                                                    title: "AML Check Result",
                                                                    data: "AmlCheckResult",
                                                                    width: "10%",
                                                                    codes: $page.ApprovedOrDeclined
                                                                }, {
                                                                    title: "S&P",
                                                                    data: "SpText",
                                                                    width: "10%"
                                                                }, {
                                                                    title: "A.M.Best",
                                                                    data: "AmbestText",
                                                                    width: "10%"
                                                                }, {
                                                                    title: "Checked By",
                                                                    data: "CheckBy",
                                                                    codes:$page.codes.SystemUser,
                                                                    width: "10%"
                                                                }, {
                                                                    title: "Approved By",
                                                                    data: "ApprovedBy",
                                                                    codes: $page.codes.SystemUser,
                                                                    width: "10%"
                                                                }, {
                                                                    title: "Remarks",
                                                                    data: "Remarks",
                                                                    width: "20%"
                                                                }
                                                            ],
                                                            addClick: function (model, rowModel, layout) {
                                                                $page.controller.addRetroCompliance(model, rowModel, layout);
                                                            },
                                                            rowOperations: [
                                                                {
                                                                    tooltip: "Edit",
                                                                    visible: {
                                                                        when: function (row) {
                                                                            return $page.pageType == 0;
                                                                        }
                                                                    },
                                                                    click: function (rowModel) {
                                                                        $page.controller.updateRetroCompliance(rowModel);
                                                                    }
                                                                },   {
                                                                    tooltip: "View",
                                                                    visible: {
                                                                        when: function (row) {
                                                                            return $page.pageType == 1;
                                                                        }
                                                                    },
                                                                    click: function (rowModel) {
                                                                        $page.controller.updateRetroCompliance(rowModel);
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
                                        }

                                    }

                                    //icon: "bookmark",
                                },*/
                                {
                                    label: "Broker",
                                    editLayout: {
                                        brokerAmlCheckPanel: {
                                            label: "AML Check",
                                            comp: {
                                                type: $pt.ComponentConstants.Panel,
                                                editLayout: {
                                                    BrokerAmlCheckTable: {
                                                        comp: {
                                                            type: $pt.ComponentConstants.Table,
                                                            searchable: false,
                                                            sortable: false,
                                                            addable: true,
                                                            removable: false,
                                                            editable: false,
                                                            columns: [
                                                                {
                                                                    title: "Check Year",
                                                                    data: "CheckYear",
                                                                    width: "10%"
                                                                }, {
                                                                    title: "Check Date",
                                                                    data: "CheckDate",
                                                                    width: "10%",
                                                                    render: function (row) {
                                                                        return $helper.formatDateForTableView(row.CheckDate, "DD/MM/YYYY");
                                                                    }
                                                                }, {
                                                                    title: "AML Check Result",
                                                                    data: "AmlCheckResult",
                                                                    codes: $page.ApprovedOrDeclined,
                                                                    width: "10%"
                                                                },  {
                                                                    title: "Checked By",
                                                                    data: "CheckBy",
                                                                    codes: $page.codes.SystemUser,
                                                                    width: "10%"
                                                                }, {
                                                                    title: "Approved By",
                                                                    data: "ApprovedBy",
                                                                    codes:$page.codes.SystemUser,
                                                                    width: "10%"
                                                                }, {
                                                                    title: "Remarks",
                                                                    data: "CheckRemarks",
                                                                    width: "20%"
                                                                }
                                                            ],
                                                            addClick: function (model, rowModel, layout) {
                                                                $page.controller.addBrokerCompliance(model, rowModel, layout);
                                                            },
                                                            rowOperations: [
                                                                {
                                                                    tooltip: "Edit",
                                                                    visible: {
                                                                        when: function (row) {
                                                                            return $page.pageType == 0;
                                                                        }
                                                                    },
                                                                    click: function (rowModel) {
                                                                        $page.controller.updateBrokerCompliance(rowModel);
                                                                    }
                                                                },   {
                                                                    tooltip: "View",
                                                                    visible: {
                                                                        when: function (row) {
                                                                            return $page.pageType == 1;
                                                                        }
                                                                    },
                                                                    click: function (rowModel) {
                                                                        $page.controller.updateBrokerCompliance(rowModel);
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
                                        }

                                    }

                                    //icon: "bookmark",
                                },
                 /*               {
                                    label: "MGA",
                                    editLayout: {
                                        mgaAmlCheckPanel: {
                                            label: "AML Check",
                                            comp: {
                                                type: $pt.ComponentConstants.Panel,
                                                editLayout: {
                                                    MgaAmlCheckTable: {
                                                        comp: {
                                                            type: $pt.ComponentConstants.Table,
                                                            searchable: false,
                                                            sortable: false,
                                                            addable: true,
                                                            removable: false,
                                                            editable: false,
                                                            columns: [
                                                                {
                                                                    title: "Check Year",
                                                                    data: "CheckYear",
                                                                    width: "10%"
                                                                }, {
                                                                    title: "Check Date",
                                                                    data: "CheckDate",
                                                                    width: "10%",
                                                                    render: function (row) {
                                                                        return $helper.formatDateForTableView(row.CheckDate, "DD/MM/YYYY");
                                                                    }
                                                                }, {
                                                                    title: "AML Check Result",
                                                                    data: "AmlCheckResult",
                                                                    codes: $page.ApprovedOrDeclined,
                                                                    width: "10%"
                                                                }, {
                                                                    title: "Checked By",
                                                                    data: "CheckBy",
                                                                    codes: $page.codes.SystemUser,
                                                                    width: "10%"
                                                                }, {
                                                                    title: "Approved By",
                                                                    data: "ApprovedBy",
                                                                    codes:$page.codes.SystemUser,
                                                                    width: "10%"
                                                                }, {
                                                                    title: "Remarks",
                                                                    data: "CheckRemarks",
                                                                    width: "20%"
                                                                }
                                                            ],
                                                            addClick: function (model, rowModel, layout) {
                                                                $page.controller.addMgaCompliance(model, rowModel, layout);
                                                            },
                                                            rowOperations: [
                                                                {
                                                                    tooltip: "Edit",
                                                                    visible: {
                                                                        when: function (row) {
                                                                            return $page.pageType == 0;
                                                                        }
                                                                    },
                                                                    click: function (rowModel) {
                                                                        $page.controller.updateMgaCompliance( rowModel);
                                                                    }
                                                                },   {
                                                                    tooltip: "View",
                                                                    visible: {
                                                                        when: function (row) {
                                                                            return $page.pageType == 1;
                                                                        }
                                                                    },
                                                                    click: function (rowModel) {
                                                                        $page.controller.updateMgaCompliance(rowModel);
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
                                        }

                                    }

                                    //icon: "bookmark",
                                }*/
                            ]
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
                                        click: function () {
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
                                            depends: "PartnerId",
                                            when: function (model) {
                                                console.log( model.getCurrentModel().PartnerId);
                                                return  model.getCurrentModel().PartnerId && true;
                                            }
                                        },
                                        click: function (model) {
                                            $pt.viewAttachment (1, model.getCurrentModel().PartnerId,$page.pageType);
                                     //       $page.controller.openDocument();
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
