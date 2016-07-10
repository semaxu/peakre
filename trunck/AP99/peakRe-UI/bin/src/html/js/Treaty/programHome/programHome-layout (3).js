(function (context) {
    var $page = $pt.getService(context, '$page');
    var codes = $pt.getService($page, 'codes');

    var currency = $pt.createCodeTable([
        {id: '1', text: "USD US Dollar"},
        {id: '2', text: "CNY CN Yuan"},
        {id: '3', text: "HKD HK Dollar"}
    ]);
    var sectionData = [
        {
            title: "Section No",
            data: "sectionNo",
            width: "8%"
        }, {
            title: "Section Type",
            data: "sectionType",
            width: "13%"
        }, {
            title: "Section Name",
            data: "sectionName",
            width: "15%"
        }, {
            title: "Main Currency",
            data: "mainCurrency",
            width: "15%",
            codes: currency
        }, {
            title: "Our share",
            data: "ourShare",
            width: "12%"
        }, {
            title: "Premium",
            data: "premium",
            width: "15%",
            render: function (row) {
                return $ri.formatNumberForLabel(row.premium, 2);
            }
        }, {
            title: "Deductions",
            data: "deductions",
            width: "15%",
            render: function (row) {
                return $ri.formatNumberForLabel(row.premium, 2);
            }
        }
    ];
    var logData = [
        {
            title: "Program Status",
            data: "programStatus",
            width: "25%"
        }, {
            title: "Update Time",
            data: "updateTime",
            width: "25%"
        }, {
            title: "Update By",
            data: "updateBy",
            width: "25%"
        }, {
            title: "Remarks",
            data: "remarks",
            width: "25%"
        }
    ];

    var DividerBase = {
        comp: {
            type: $pt.ComponentConstants.Divider
        },
        pos: {
            width: 12
        },
        css: {
            cell: 'form-cell-divider'
        }
    };


    $page.countryDialogLayout = {
        countries: {
            comp: {
                type: $pt.ComponentConstants.Tree,
                check: "selected",
                hierarchyCheck: true,
                inactiveSlibing: false,
                root: false
            }
        }
    };

    $page.classDialogLayout = {
        classes: {
            comp: {
                type: $pt.ComponentConstants.Tree,
                check: "selected",
                hierarchyCheck: true,
                inactiveSlibing: false,
                root: false
            }
        }
    };

    $page.subClassDialogLayout = {
        subClasses: {
            comp: {
                type: $pt.ComponentConstants.Tree,
                check: "selected",
                hierarchyCheck: true,
                inactiveSlibing: false,
                root: false
            }
        }
    };

    $page.currencyDialogLayout = {
        currencys: {
            comp: {
                type: $pt.ComponentConstants.Tree,
                check: "selected",
                hierarchyCheck: true,
                inactiveSlibing: false,
                root: false
            }
        }
    };

    $page.uwAreaDialogLayout = {
        uwAreas: {
            comp: {
                type: $pt.ComponentConstants.Tree,
                check: "selected",
                hierarchyCheck: true,
                inactiveSlibing: false,
                root: false
            }
        }
    };

    $page.coveredAreaDialogLayout = {
        coveredAreas: {
            comp: {
                type: $pt.ComponentConstants.Tree,
                check: "selected",
                hierarchyCheck: true,
                inactiveSlibing: false,
                root: false
            }
        }
    };

    $page.linkeProgramDialogLayout = {
        _sections: {
            defaultSection: {
                layout: {
                    formTab1: {
                        comp: {
                            type: $pt.ComponentConstants.Tab,
                            justified: false,
                            tabs: [
                                {
                                    label: "UY 2015",
                                    editLayout: {
                                        subPanel01: {
                                            comp: {
                                                type: $pt.ComponentConstants.Panel,
                                                editLayout: {
                                                    buttonPanel: {
                                                        comp: {
                                                            type: $pt.ComponentConstants.ButtonFooter,
                                                            buttonLayout: {
                                                                right: [
                                                                    {
                                                                        text: "Expland All",
                                                                        style: "primary"
                                                                    }, {
                                                                        text: "Collapse All",
                                                                        style: "primary"
                                                                    }
                                                                ]
                                                            }
                                                        },
                                                        pos: {
                                                            width: 12
                                                        }
                                                    },
                                                    programTree: {
                                                        comp: {
                                                            type: $pt.ComponentConstants.Tree,
                                                            hierarchyCheck: true,
                                                            inactiveSlibing: false,
                                                            root: false
                                                        },
                                                        pos: {
                                                            width: 12
                                                        }
                                                    }
                                                }
                                            },
                                            pos: {
                                                width: 12,
                                                row: 1
                                            }
                                        }
                                    }
                                }, {
                                    label: "UY 2014",
                                    editLayout: {
                                        subPanel02: {
                                            comp: {
                                                type: $pt.ComponentConstants.Panel,
                                                editLayout: {
                                                    buttonPanel: {
                                                        comp: {
                                                            type: $pt.ComponentConstants.ButtonFooter,
                                                            buttonLayout: {
                                                                right: [
                                                                    {
                                                                        text: "Expland All",
                                                                        style: "primary"
                                                                    }, {
                                                                        text: "Collapse All",
                                                                        style: "primary"
                                                                    }
                                                                ]
                                                            }
                                                        },
                                                        pos: {
                                                            width: 12
                                                        }
                                                    },
                                                    programTree: {
                                                        comp: {
                                                            type: $pt.ComponentConstants.Tree,
                                                            hierarchyCheck: true,
                                                            inactiveSlibing: false,
                                                            root: false
                                                        },
                                                        pos: {
                                                            width: 12
                                                        }
                                                    }
                                                }
                                            },
                                            pos: {
                                                width: 12,
                                                row: 1
                                            }
                                        }
                                    }
                                }, {
                                    label: "UY 2013",
                                    editLayout: {
                                        subPanel03: {
                                            comp: {
                                                type: $pt.ComponentConstants.Panel,
                                                editLayout: {
                                                    buttonPanel: {
                                                        comp: {
                                                            type: $pt.ComponentConstants.ButtonFooter,
                                                            buttonLayout: {
                                                                right: [
                                                                    {
                                                                        text: "Expland All",
                                                                        style: "primary"
                                                                    }, {
                                                                        text: "Collapse All",
                                                                        style: "primary"
                                                                    }
                                                                ]
                                                            }
                                                        },
                                                        pos: {
                                                            width: 12
                                                        }
                                                    },
                                                    programTree: {
                                                        comp: {
                                                            type: $pt.ComponentConstants.Tree,
                                                            hierarchyCheck: true,
                                                            inactiveSlibing: false,
                                                            root: false
                                                        },
                                                        pos: {
                                                            width: 12
                                                        }
                                                    }
                                                }
                                            },
                                            pos: {
                                                width: 12,
                                                row: 1
                                            }
                                        }
                                    }
                                }
                            ]
                        },
                        pos: {
                            width: 12
                        }
                    }
                }
            }
        }
    };

    var baseLayout = {
        _sections: {
            headSection: {
                layout: {
                    buttonPanel: {
                        comp: {
                            type: $pt.ComponentConstants.ButtonFooter,
                            buttonLayout: {
                                right: [
                                    {
                                        text: "Linked Program",
                                        style: "link",
                                        click: function (model) {
                                            $page.controller.showDefaultDialog($page.linkedProgramModel, model, $page.linkeProgramDialogLayout);
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
        }
    };

    var basicProgramLayout = {
        _sections: {
            basicProgramInfo: {
                label: "Basic Program Info",
                style: 'primary-darken',
                collapsible: true,
                expanded: true,
                layout: {
                    subPanel01: {
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                programID: {
                                    label: "Program ID",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    },
                                    pos: {
                                        row: 10,
                                        col: 1,
                                        width: 4,
                                        section: "subPanel01"
                                    }
                                },
                                programName: {
                                    label: "Program Name",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    },
                                    pos: {
                                        row: 10,
                                        col: 1,
                                        width: 4,
                                        section: "subPanel01"
                                    }
                                },
                                mainClass: {
                                    label: "Main Class",
                                    comp: {
                                        type: $pt.ComponentConstants.Select
                                    },
                                    pos: {
                                        row: 10,
                                        col: 1,
                                        width: 4,
                                        section: "subPanel01"
                                    }
                                },
                                brokerRef: {
                                    label: "Broker Ref",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    },
                                    pos: {
                                        row: 10,
                                        col: 1,
                                        width: 4,
                                        section: "subPanel01"
                                    }
                                },
                                cedantRef: {
                                    label: "Cedant Ref",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    },
                                    pos: {
                                        row: 10,
                                        col: 1,
                                        width: 4,
                                        section: "subPanel01"
                                    }
                                },
                                subclass: {
                                    label: "Subclass",
                                    comp: {
                                        type: $pt.ComponentConstants.Select
                                    },
                                    pos: {
                                        row: 10,
                                        col: 1,
                                        width: 4,
                                        section: "subPanel01"
                                    }
                                }
                            }
                        },
                        pos: {
                            width: 12
                        }
                    },
                    divider1: {
                        base: DividerBase
                    },
                    subPanel02: {
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                latestStatus: {
                                    label: "Latest Status",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.codes.LatestStatus
                                    }
                                },
                                programType: {
                                    label: "Program Type",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.codes.ProgramType
                                    }
                                },
                                programNature: {
                                    label: "Program Nature",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.codes.ProgramNature
                                    }
                                },
                                programCategory: {
                                    label: "Program Category",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.codes.ProgramCategory
                                    }
                                },
                                fronting: {
                                    label: "Fronting",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.codes.Boolean
                                    }
                                },
                                depositAccounting: {
                                    label: "Deposit Accounting",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.codes.Boolean
                                    }
                                }
                            }
                        },
                        pos: {
                            width: 12
                        }
                    },
                    divider2: {
                        base: DividerBase
                    },
                    subPanel03: {
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                reinsStarting: {
                                    label: "Reins Starting",
                                    comp: {
                                        type: $pt.ComponentConstants.Date,
                                        format: "DD/MM/YYYY HH:mm:ss"
                                    }
                                },
                                underwritingYear: {
                                    label: "Underwriting Year",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    }
                                },
                                inForceDate: {
                                    label: "In Force Date",
                                    comp: {
                                        type: $pt.ComponentConstants.Date,
                                        format: "DD/MM/YYYY"
                                    }
                                },
                                reinsEnding: {
                                    label: "Reins Ending",
                                    comp: {
                                        type: $pt.ComponentConstants.Date,
                                        format: "DD/MM/YYYY HH:mm:ss"
                                    }
                                }
                            }
                        },
                        pos: {
                            width: 12
                        }
                    },
                    divider3: {
                        base: DividerBase
                    },
                    subPanel04: {
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                cedant: {
                                    label: "Cedant",
                                    comp: {
                                        type: $pt.ComponentConstants.Search
                                    }
                                },
                                broker: {
                                    label: "Broker",
                                    comp: {
                                        type: $pt.ComponentConstants.Search
                                    }
                                },
                                cedantCountry: {
                                    label: "Cedant Country",
                                    comp: {
                                        type: $pt.ComponentConstants.Select
                                    }
                                },
                                mga: {
                                    label: "MGA",
                                    comp: {
                                        type: $pt.ComponentConstants.Search
                                    }
                                },
                                coBroker: {
                                    label: "Co-Broker",
                                    comp: {
                                        type: $pt.ComponentConstants.Search
                                    }
                                },
                                insured: {
                                    label: "Insured",
                                    comp: {
                                        type: $pt.ComponentConstants.Search
                                    }
                                }
                            }
                        },
                        pos: {
                            width: 12
                        }
                    },
                    divider4: {
                        base: DividerBase
                    },
                    subPanel05: {
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                underwriting: {
                                    label: "Underwriting",
                                    comp: {
                                        type: $pt.ComponentConstants.Select
                                    }
                                },
                                analyticsResponsible: {
                                    label: "Analytics Responsible",
                                    comp: {
                                        type: $pt.ComponentConstants.Select
                                    }
                                },
                                marketResponsible: {
                                    label: "Market Responsible",
                                    comp: {
                                        type: $pt.ComponentConstants.Select
                                    }
                                },
                                createdBy: {
                                    label: "Created By",
                                    comp: {
                                        type: $pt.ComponentConstants.Select
                                    }
                                },
                                createdOn: {
                                    label: "Created On",
                                    comp: {
                                        type: $pt.ComponentConstants.Date,
                                        format: "DD/MM/YYYY"
                                    }
                                },
                                ownerResponsible: {
                                    label: "Owner Responsible",
                                    comp: {
                                        type: $pt.ComponentConstants.Select
                                    }
                                },
                                lastChanged: {
                                    label: "Last Changed",
                                    comp: {
                                        type: $pt.ComponentConstants.Select
                                    }
                                },
                                lastChangedOn: {
                                    label: "Last Changed On",
                                    comp: {
                                        type: $pt.ComponentConstants.Date,
                                        format: "DD/MM/YYYY"
                                    }
                                },
                                uwCompany: {
                                    label: "UW Company",
                                    comp: {
                                        type: $pt.ComponentConstants.Select
                                    }
                                }
                            }
                        },
                        pos: {
                            width: 12
                        }
                    },
                    buttonPanel: {
                        label: "",
                        comp: {
                            type: $pt.ComponentConstants.ButtonFooter,
                            buttonLayout: {
                                right: [
                                    {
                                        text: "Save",
                                        style: "primary"
                                    }, {
                                        text: "Add Business Conditions",
                                        style: "link",
                                        click: function (model) {
                                            if (this.getModel().get("programNature") == null) {
                                                //alert('Please choose the Program Nature');
                                                NConfirm.getConfirmModal().show("Confirm Dialog", {
                                                    messages: ['Please choose the Program Nature']
                                                });
                                                return;
                                            }
                                            var programNature = model.get("programNature");
                                            var url = "businessCondition.html?programNature=" + this.getModel().get("programNature");
                                            window.open(url);
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
        }
    };

    var moreProgramLayout = {
        _sections: {
            moreProgramInfo: {
                label: "More Program Info",
                style: 'primary-darken',
                collapsible: true,
                expanded: false,
                layout: {
                    coverageDefinition: {
                        label: "Coverage Definition",
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                class: {
                                    label: "Class",
                                    comp: {
                                        type: $pt.ComponentConstants.Label
                                    },
                                    pos: {
                                        row: 1,
                                        width: 2
                                    }
                                },
                                classSelected: {
                                    comp: {
                                        type: {type: $pt.ComponentConstants.Text, label: false},
                                        enabled: false
                                    },
                                    pos: {
                                        row: 1
                                    }
                                },
                                classButton: {
                                    comp: {
                                        type: $pt.ComponentConstants.Button,
                                        style: "link",
                                        icon: "plus",
                                        click: function (model) {
                                            $page.controller.showTreeDialog(model, $page.classModel, $page.classDialogLayout, 'classes', 'classSelected');
                                        }
                                    },
                                    pos: {
                                        row: 1,
                                        width: 1
                                    }
                                },
                                subClass: {
                                    label: "Sub-class",
                                    comp: {
                                        type: $pt.ComponentConstants.Label
                                    },
                                    pos: {
                                        row: 2,
                                        width: 2
                                    }
                                },
                                subClassSelected: {
                                    comp: {
                                        type: {type: $pt.ComponentConstants.Text, label: false},
                                        enabled: false
                                    },
                                    pos: {
                                        row: 2
                                    }
                                },
                                subClassButton: {
                                    comp: {
                                        type: $pt.ComponentConstants.Button,
                                        style: "link",
                                        icon: "plus",
                                        click: function (model) {
                                            $page.controller.showTreeDialog(model, $page.subClassModel, $page.subClassDialogLayout, 'subClasses', 'subClassSelected');
                                        }
                                    },
                                    pos: {
                                        row: 2,
                                        width: 1
                                    }
                                },
                                currency: {
                                    label: "Currency",
                                    comp: {
                                        type: $pt.ComponentConstants.Label
                                    },
                                    pos: {
                                        row: 3,
                                        width: 2
                                    }
                                },
                                currencySelected: {
                                    comp: {
                                        type: {type: $pt.ComponentConstants.Text, label: false},
                                        enabled: false
                                    },
                                    pos: {
                                        row: 3
                                    }
                                },
                                currencyButton: {
                                    comp: {
                                        type: $pt.ComponentConstants.Button,
                                        style: "link",
                                        icon: "plus",
                                        click: function (model) {
                                            $page.controller.showTreeDialog(model, $page.currencyModel, $page.currencyDialogLayout, 'currencys', 'currencySelected');
                                        }
                                    },
                                    pos: {
                                        row: 3,
                                        width: 1
                                    }
                                },
                                uwArea: {
                                    label: "Area",
                                    comp: {
                                        type: $pt.ComponentConstants.Label
                                    },
                                    pos: {
                                        row: 4,
                                        width: 2
                                    }
                                },
                                uwAreaSelected: {
                                    comp: {
                                        type: {type: $pt.ComponentConstants.Text, label: false},
                                        enabled: false
                                    },
                                    pos: {
                                        row: 4
                                    }
                                },
                                uwAreaButton: {
                                    comp: {
                                        type: $pt.ComponentConstants.Button,
                                        style: "link",
                                        icon: "plus",
                                        click: function (model) {
                                            $page.controller.showTreeDialog(model, $page.uwAreaModel, $page.uwAreaDialogLayout, 'uwAreas', 'uwAreaSelected');
                                        }
                                    },
                                    pos: {
                                        row: 4,
                                        width: 1
                                    }
                                },
                                coveredArea: {
                                    label: "Covered Area",
                                    comp: {
                                        type: $pt.ComponentConstants.Label
                                    },
                                    pos: {
                                        row: 5,
                                        width: 2
                                    }
                                },
                                coveredAreaSelected: {
                                    comp: {
                                        type: {type: $pt.ComponentConstants.Text, label: false},
                                        enabled: false
                                    },
                                    pos: {
                                        row: 5
                                    }
                                },
                                coveredAreaButton: {
                                    comp: {
                                        type: $pt.ComponentConstants.Button,
                                        style: "link",
                                        icon: "plus",
                                        click: function (model) {
                                            $page.controller.showTreeDialog(model, $page.coveredAreaModel, $page.coveredAreaDialogLayout, 'coveredAreas', 'coveredAreaSelected');
                                        }
                                    },
                                    pos: {
                                        row: 5,
                                        width: 1
                                    }
                                },
                                protectionBasis: {
                                    label: "Protection Basis",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        labelDirection: "horizontal"
                                    },
                                    pos: {
                                        row: 6
                                    }
                                }
                            }
                        },
                        pos: {
                            width: 12
                        }
                    },
                    sectionTable: {
                        label: "Sections",
                        comp: {
                            type: $pt.ComponentConstants.Table,
                            searchable: false,
                            addable: true,
                            removable: true,
                            columns: sectionData,
                            addClick: function (model, rowModel, layout) {
                                if (this.getModel().get("programNature") == null) {
                                    alert('Please choose the Program Nature');
                                    return;
                                }
                                var url = "section.html?programNature=" + this.getModel().get("programNature");
                                window.open(url);
                            },
                            rowOperations: [
                                {
                                    icon: "edit",
                                    click: function (row) {
                                        if (this.getModel().get("programNature") == null) {
                                            alert('Please choose the Program Nature');
                                            return;
                                        }
                                        var url = "section.html?programNature=" + this.getModel().get("programNature");
                                        window.open(url);
                                    }
                                }
                            ]
                        },
                        pos: {
                            width: 12
                        }
                    },
                    logTable: {
                        label: "Status Log",
                        comp: {
                            type: $pt.ComponentConstants.Table,
                            searchable: false,
                            sortable: false,
                            columns: logData
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
                                    // {
                                    //     text: "Exit",
                                    //     style: "warning"
                                    // },
                                    {
                                        text: "Save",
                                        style: "primary"
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
        }
    };

    var areaAndPerilsLayout = {
        _sections: {
            areaPeril: {
                label: "Area & Peril",
                style: 'primary-darken',
                collapsible: true,
                expanded: false,
                layout: {
                    uwArea: {
                        label: "UW Area",
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                area: {
                                    // label:"UW Area : ",
                                    comp: {
                                        type: $pt.ComponentConstants.Label
                                    },
                                    pos: {
                                        row: 1,
                                        width: 1
                                    }
                                },
                                uwAreaSelected1: {
                                    comp: {
                                        type: {tyep: $pt.ComponentConstants.Text, label: false},
                                        enabled: false
                                    },
                                    pos: {
                                        row: 1,
                                        width: 4
                                    }
                                },
                                uwAreaButton1: {
                                    // label:"Select Area",
                                    comp: {
                                        type: $pt.ComponentConstants.Button,
                                        style: "link",
                                        icon: "plus",
                                        click: function (model) {
                                            $page.controller.showTreeDialog(model, $page.uwCountryModel, $page.countryDialogLayout, 'countries', 'uwAreaSelected1');
                                        }
                                    },
                                    pos: {
                                        row: 1,
                                        width: 1
                                    }
                                },
                                areaRemarkPanel: {
                                    label: "Remarks",
                                    comp: {
                                        type: $pt.ComponentConstants.Panel,
                                        editLayout: {
                                            areaRemark: {
                                                comp: {
                                                    type: {type: $pt.ComponentConstants.TextArea, label: false},
                                                    lines: 3,
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
                                }
                            }
                        },
                        pos: {
                            width: 12
                        }
                    },
                    covered: {
                        label: "Covered",
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                area: {
                                    // label:"UW Area : ",
                                    comp: {
                                        type: $pt.ComponentConstants.Label
                                    },
                                    pos: {
                                        row: 1,
                                        width: 1
                                    }
                                },
                                coveredAreaSelected1: {
                                    comp: {
                                        type: {tyep: $pt.ComponentConstants.Text, label: false},
                                        enabled: false
                                    },
                                    pos: {
                                        row: 1,
                                        width: 4
                                    }
                                },
                                coveredAreaButton1: {
                                    // label:"Select Area",
                                    comp: {
                                        type: $pt.ComponentConstants.Button,
                                        style: "link",
                                        icon: "plus",
                                        click: function (model) {
                                            $page.controller.showTreeDialog(model, $page.coveredCountryModel, $page.countryDialogLayout, 'countries', 'coveredAreaSelected1');
                                        }
                                    },
                                    pos: {
                                        row: 1,
                                        width: 1
                                    }
                                },
                                coveredRemarkPanel: {
                                    label: "Remarks",
                                    comp: {
                                        type: $pt.ComponentConstants.Panel,
                                        editLayout: {
                                            coveredRemark: {
                                                comp: {
                                                    type: {type: $pt.ComponentConstants.TextArea, label: false},
                                                    lines: 3,
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
                                }
                            }
                        },
                        pos: {
                            width: 12
                        }
                    },
                    peril: {
                        label: "Peril",
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                area: {
                                    // label:"UW Area : ",
                                    comp: {
                                        type: $pt.ComponentConstants.Label
                                    },
                                    pos: {
                                        row: 1,
                                        width: 1
                                    }
                                },
                                perilAreaSelected: {
                                    comp: {
                                        type: {tyep: $pt.ComponentConstants.Text, label: false},
                                        enabled: false
                                    },
                                    pos: {
                                        row: 1,
                                        width: 4
                                    }
                                },
                                perilAreaButton: {
                                    // label:"Select Area",
                                    comp: {
                                        type: $pt.ComponentConstants.Button,
                                        style: "link",
                                        icon: "plus",
                                        click: function (model) {
                                            $page.controller.showTreeDialog(model, $page.perilCountryModel, $page.countryDialogLayout, 'countries', 'perilAreaSelected');
                                        }
                                    },
                                    pos: {
                                        row: 1,
                                        width: 1
                                    }
                                },
                                perilRemarkPanel: {
                                    label: "Remarks",
                                    comp: {
                                        type: $pt.ComponentConstants.Panel,
                                        editLayout: {
                                            perilRemark: {
                                                comp: {
                                                    type: {type: $pt.ComponentConstants.TextArea, label: false},
                                                    lines: 3,
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
                                }
                            }
                        },
                        pos: {
                            width: 12
                        }
                    },
                    rightButtons: {
                        comp: {
                            type: $pt.ComponentConstants.ButtonFooter,
                            buttonLayout: {
                                right: [{
                                    text: "Save",
                                    style: "primary",
                                    click: function () {

                                    }
                                }]
                            }
                        },
                        pos: {
                            width: 12
                        }
                    }
                }
            }
        }
    };

    var claimConditionsLayout = {
        _sections: {
            claimConditionsSection: {
                label: "Claim Conditions",
                style: 'primary-darken',
                collapsible: true,
                expanded: false,
                layout: {
                    claimBasis: {
                        label: "Claim Basis",
                        comp: {
                            type: $pt.ComponentConstants.Select,
                            // data:$page.codes.Boolean
                        },
                        pos: {
                            // row:1
                            // width:10
                        }
                    },
                    retroactiveDate: {
                        label: "Retroactive Date",
                        comp: {
                            type: $pt.ComponentConstants.Date
                        },
                        pos: {
                            // row:2
                            // width:10
                        }
                    },
                    sunsetClause: {
                        label: "Sunset Clause",
                        comp: {
                            type: $pt.ComponentConstants.Date
                        },
                        pos: {
                            // row:2
                            // width:10
                        }
                    },
                    claimAdviceLimit: {
                        label: "Claim Advice Limit",
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                currency: {
                                    label: "Currency",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.codes.Currency
                                    }
                                },
                                lossAdvice: {
                                    label: "Loss Advice"
                                },
                                post: {
                                    label: "Posting",
                                    comp: {
                                        type: {type: $pt.ComponentConstants.Check, label: false},
                                        labelAttached: true,
                                    }
                                },
                                cashCallAdvice: {
                                    label: "Cash Call Advice"
                                },
                                noOfDay: {
                                    label: "No. of Days"
                                }
                            }
                        },
                        pos: {
                            width: 12
                        }
                    },
                    claimLimitDataUI: {
                        label: "Additional Claim Limits",
                        comp: {
                            type: $pt.ComponentConstants.Table,
                            addable: false,
                            removable: false,
                            addable: false,
                            searchable: false,
                            sortable: false,
                            columns: [
                                {title: "", data: "check", inline: "check", width: "2%"},
                                {
                                    title: "",
                                    render: function (row) {
                                        return $page.codes.ClaimLimitCate.getText(row.cateId);
                                    }, width: "48%"
                                },
                                {title: "Percentage", data: "percentage", inline: "percentage", width: "25%"},
                                {title: "Amount", data: "amount", inline: "number", width: "25%"}
                            ],
                        },
                        css: {
                            comp: "inline-editor"
                        },
                        pos: {
                            width: 12
                        }
                    },
                    indexProduct: {
                        label: "Index Product",
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                weatherIndexs: {
                                    label: "Weather Indexs",
                                    comp: {
                                        type: $pt.ComponentConstants.ArrayCheck,
                                        data: $page.codes.WeatherIndexs,
                                        labelWidth: 3
                                    },
                                    pos: {
                                        row: 1,
                                        width: 8
                                    }
                                },
                                priceIndexs: {
                                    label: "Price Indexs",
                                    comp: {
                                        type: $pt.ComponentConstants.ArrayCheck,
                                        data: $page.codes.PriceIndexs,
                                        labelWidth: 3
                                    },
                                    pos: {
                                        row: 2,
                                        width: 8
                                    }
                                },
                                specify: {
                                    label: "Specify",
                                    comp: {
                                        type: $pt.ComponentConstants.Text,
                                        visible: {
                                            when: function (model) {
                                                return model.get('priceIndexs') == '3';
                                            },
                                            depends: 'priceIndexs'
                                        },
                                    },
                                    pos: {
                                        row: 2
                                    }
                                }
                            }
                        },
                        pos: {
                            width: 12
                        }
                    },
                    rightButtons: {
                        comp: {
                            type: $pt.ComponentConstants.ButtonFooter,
                            buttonLayout: {
                                right: [{
                                    text: "Save",
                                    style: "primary",
                                    click: function () {

                                    }
                                }]
                            }
                        },
                        pos: {
                            width: 12
                        }
                    }
                }
            }
        }
    };

    var accountingConditionsLayout = {
        _sections: {
            accountConditionSection: {
                label: "Accounting Conditions",
                style: 'primary-darken',
                collapsible: true,
                expanded: false,
                layout: {
                    accountingBasis: {
                        label: "Accounting Basis",
                        comp: {
                            type: $pt.ComponentConstants.Select,
                            // data:$page.codes.Boolean
                        },
                        pos: {
                            row: 1
                            // width:10
                        }
                    },
                    earningPattern: {
                        label: "Earning Pattern",
                        comp: {
                            type: $pt.ComponentConstants.Select
                        },
                        pos: {
                            row: 1
                            // width:10
                        }
                    },
                    accountFreq: {
                        label: "Account Frequency",
                        comp: {
                            type: $pt.ComponentConstants.Select
                        },
                        pos: {
                            row: 2
                            // width:10
                        }
                    },
                    asAtDate: {
                        label: "First Account as at Date",
                        comp: {
                            type: $pt.ComponentConstants.Date,
                        },
                        pos: {
                            row: 2
                            // width:12
                        }
                    },
                    threshold: {
                        label: "Threshold for Difference(Actual vs. Accrual)",
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                percentageOfPremium: {
                                    label: "Percentage Of Premium",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    }
                                },
                                dataForReview: {
                                    label: "Date for Review",
                                    comp: {
                                        type: $pt.ComponentConstants.Date
                                    }
                                }
                            }
                        },
                        pos: {
                            width: 12
                        }
                    },
                    rightButtons: {
                        comp: {
                            type: $pt.ComponentConstants.ButtonFooter,
                            buttonLayout: {
                                right: [{
                                    text: "Save",
                                    style: "primary",
                                    click: function () {

                                    }
                                }]
                            }
                        },
                        pos: {
                            width: 12
                        }
                    }
                }
            }
        }
    };

    var remarksLayout = {
        _sections: {
            remarksSection: {
                label: "Remarks",
                style: 'primary-darken',
                collapsible: true,
                layout: {
                    remarkPanel: {
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                remark: {
                                    comp: {
                                        type: {type: $pt.ComponentConstants.TextArea, label: false},
                                        lines: 3
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
                    }
                }
            }
        }
    };

    var buttonLayout = {
        _sections: {
            buttonSection: {
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
                                            window.location.href = "programQuery.html";
                                        }
                                    }, {
                                        text: "Document",
                                        style: "primary"
                                    }, {
                                        text: "Submit",
                                        style: "primary",
                                        click: function () {
                                            window.location.href = "programQuery.html";
                                        }
                                    }, {
                                        text: "Save",
                                        style: "primary"
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
        }
    };

    $page.layout = $.extend(true, {}, baseLayout, basicProgramLayout, moreProgramLayout, areaAndPerilsLayout, claimConditionsLayout, accountingConditionsLayout, remarksLayout, buttonLayout);

}(typeof window !== "undefined" ? window : this));
