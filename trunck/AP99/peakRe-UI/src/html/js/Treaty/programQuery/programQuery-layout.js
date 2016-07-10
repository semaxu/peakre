(function (context) {
    var $page = $pt.getService(context, '$page');
    var codes = $pt.getService($page, 'codes');

    codes.Boolean = $pt.createCodeTable([
        {
            id: 'Y',
            text: 'Yes'
        }, {
            id: 'N',
            text: 'No'
        }
    ]);

    var columnData = [
        {
            title: "Select",
            data: "select",
            inline: "check",
            width: "8%"
        }, {
            title: "Program ID",
            data: "programID",
            width: "13%"
        }, {
            title: "Program Name",
            data: "programName",
            width: "15%"
        }, {
            title: "Cedant",
            data: "cedant",
            width: "15%"
        }, {
            title: "Broker",
            data: "broker",
            width: "13%"
        }, {
            title: "Main Class",
            data: "mainClass",
            width: "13%"
        }, {
            title: "UY",
            data: "uy",
            width: "12%"
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

    $page.layout = {
        _sections: {
            conditionSection: {
                label: "Search Condition",
                style: 'primary-darken',
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
                        comp: {
                            type: $pt.ComponentConstants.ButtonFooter,
                            buttonLayout: {
                                right: [
                                    {
                                        text: "Search",
                                        style: "primary"
                                    }, {
                                        text: "Reset",
                                        style: "warning"
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
            searchResult: {
                label: "Search Result",
                style: 'primary-darken',
                layout: {
                    resultPanel: {
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                resultTable: {
                                    comp: {
                                        type: $pt.ComponentConstants.Table,
                                        sortable: true,
                                        pageable: true,
                                        searchable: false,
                                        criteria: "criteria",
                                        columns: columnData,
                                        rowOperations: [
                                            {
                                                icon: "edit",
                                                click: function (row) {
                                                    window.location.href = "programHome.html";
                                                }
                                            }
                                        ]
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
                    buttonPanel: {
                        comp: {
                            type: $pt.ComponentConstants.ButtonFooter,
                            buttonLayout: {
                                right: [
                                    {
                                        text: "Exit",
                                        style: "warning"
                                    }, {
                                        text: "Termination",
                                        style: "warning"
                                    }, {
                                        text: "Deactivate",
                                        style: "warning"
                                    }, {
                                        text: "Endorsement",
                                        style: "primary"
                                    }, {
                                        text: "Renew",
                                        style: "primary"
                                    }, {
                                        text: "Copy",
                                        style: "primary"
                                    }, {
                                        text: "Review",
                                        style: "primary"
                                    }, {
                                        text: "New",
                                        style: "primary",
                                        click: function () {
                                            window.location.href = "programHome.html";
                                        }
                                    }, {
                                        text: "View",
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

}(typeof window !== "undefined" ? window : this));
