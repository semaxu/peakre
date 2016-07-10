(function (context) {
    var $page = $pt.getService(context, '$page');
    NTable.registerInlineEditor('linkedBtn', {
        comp: {
            type: $pt.ComponentConstants.Button,
            style: 'link',
            labelFromModel: true,
            click: function (model) {
                var contractID = model.get('ContractID');
                var url = $pt.getURL('ui.gl.doubleEntries');
                window.location.href = (url + "?ContractID=" + contractID + "&Type=subLedger");
            }
        },
        css: {
            comp: 'link-in-table-cell'
        }
    });
    var Layout = jsface.Class({
        createSubLedgerQuery: function () {
            return {
                label: "Sub Ledger Query",
                style: 'primary-darken',
                layout: {
                    searchCriteria: {
                        label: "Search Criteria",
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                condition_ContractID: {
                                    label: "Contract ID",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    },
                                    pos: {
                                        row: 1
                                    }
                                },
                                condition_BusinessType: {
                                    label: "Business Type",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.codes.BusinessTransType
                                    },
                                    pos: {
                                        row: 1
                                    }
                                },
                                condition_BusinessNumber: {
                                    label: "Business Number",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    },
                                    pos: {
                                        row: 1
                                    }
                                },
                                condition_Currency: {
                                    label: "Currency",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.codes.Currency
                                    },
                                    pos: {
                                        row: 2
                                    }
                                },
                                condition_UWYear: {
                                    label: "UW Year",
                                    comp: {
                                        type: $pt.ComponentConstants.Date,
                                        format:"YYYY",
                                        valueFormat:"YYYY"
                                    },
                                    pos: {
                                        row: 2
                                    }
                                },
                                condition_UWAreaList: {
                                    label: "UW Area",
                                    comp: {
                                        type: $pt.ComponentConstants.SelectTree,
                                        data: $page.codes.CoveredArea,
                                        hideChildWhenParentChecked: false,
                                        model : $page.controller.viewModelForArea,
                                        treeLayout: {
                                            comp: {
                                                hierarchyCheck: false,
                                                expandLevel: "all",
                                                inactiveSlibing: false,
                                                valueAsArray: true
                                            }
                                        }
                                    },
                                    pos: {
                                        row: 2
                                    }
                                },
                                condition_GenerationDateFrom: {
                                    label: "Generation Date From",
                                    comp: {
                                        type: $pt.ComponentConstants.Date,
                                        format: "DD/MM/YYYY"
                                    },
                                    pos: {
                                        row: 3
                                    }
                                },
                                condition_GenerationDateTo: {
                                    label: "Generation Date To",
                                    comp: {
                                        type: $pt.ComponentConstants.Date,
                                        format: "DD/MM/YYYY"
                                    },
                                    pos: {
                                        row: 3
                                    }
                                },
                                condition_PostDateFrom: {
                                    label: "Post Date from",
                                    comp: {
                                        type: $pt.ComponentConstants.Date,
                                        format: "DD/MM/YYYY"
                                    },
                                    pos: {
                                        row: 3
                                    }
                                },
                                condition_PostDateTo: {
                                    label: "Post Date to",
                                    comp: {
                                        type: $pt.ComponentConstants.Date,
                                        format: "DD/MM/YYYY"
                                    },
                                    pos: {
                                        row: 4
                                    }
                                },
                                condition_PostStatus: {
                                    label: "Post Status",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.codes.GL_PostStatus
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
                                                        $page.controller.resetSubLedgerQuery();
                                                    }
                                                }, {
                                                    text: "Search",
                                                    style: "primary",
                                                    click: function () {
                                                        $page.controller.searchSubLedgerQuery();
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
                        label: "",
                        comp: {
                            type: $pt.ComponentConstants.Table,
                            searchable: false,
                            sortable: false,
                            addable: false,
                            pageable: true,
                            criteria: 'cachedCriteria',
                            columns: [
                                {
								    title: "General Ledger Mapping Entry Id",
								    data: "MappingEntryId",
								    visible: {
								        when: function () {
								            return false;
								        }
								    }
								}, {
                                    title: "Contract ID",
                                    data: "ContractCode"
                                }, {
                                    title: "Business Type",
                                    data: "BusinessType",
                                    codes:$page.codes.BusinessTransType
                                }, {
                                    title: "Business Number",
                                    data: "BusinessNumber"
                                }, /*{
                                    title: "Business Description",
                                    data: "BusinessDescription"
                                },*/ {
                                    title: "Entry Code",
                                    data: "EntryCode"
                                }, {
                                    title: "Entry Item",
                                    data: "EntryItem"
                                }, {
                                    title: "Currency",
                                    data: "Currency"
                                }, {
                                    title: "Amount",
                                    data: "Amount",
                                    render: function (row) {
                                        return $helper.formatNumberForLabel(row.Amount, 2);
                                    }
                                }, {
                                    title: "Amount in USD",
                                    data: "AmountInUSD",
                                    render: function (row) {
                                        return $helper.formatNumberForLabel(row.AmountInUSD, 2);
                                    }
                                }, {
                                    title: "Exchange Rate",
                                    data: "ExchangeRate",
                                    render: function (row) {
                                        return $helper.formatNumberForLabel(row.ExchangeRate, 6);
                                    }
                                }, {
                                    title: "Generation Date",
                                    data: "GenerationDate",
                                    render: function (row) {
                                        var generationDate = "";
                                        if (undefined != row.GenerationDate && null != row.GenerationDate && "" != row.GenerationDate){
                                            generationDate = moment(row.GenerationDate,"YYYY-MM-DD").format("DD/MM/YYYY")
                                        }
                                        return generationDate;
                                    }
                                }, {
                                    title: "Post Date",
                                    data: "PostDate",
                                    render: function (row) {
                                        var postDate = "";
                                        if (undefined != row.PostDate && null != row.PostDate && "" != row.PostDate){
                                            postDate = moment(row.PostDate,"YYYY-MM-DD").format("DD/MM/YYYY")
                                        }
                                        return postDate;
                                    }
                                }, {
                                    title: "Broker",
                                    data: "Broker"
                                }, {
                                    title: "Cedent/Retrocessionaire",
                                    data: "CedentRetrocessionaire"
                                }, {
                                    title: "UW Year",
                                    data: "UWYear"
                                }, {
                                    title: "UW Area",
                                    data: "UWArea",
                                    codes: $page.codes.MainCoveredArea
                                }, {
                                    title: "Indicator",
                                    data: "Indicator",
                                    codes: $page.codes.GL_Indicator
                                }, {
                                    title: "Post Status",
                                    data: "PostStatus",
                                    codes: $page.codes.GL_PostStatus
                                }, {
                                    title: "Operator",
                                    data: "Operator",
                                    codes: $page.codes.SystemUser
                                }
                            ],
                            rowOperations: [
                                             {
                                                 tooltip: "View",
                                                 click:function(model){
                                                 	 var mappingEntryId = model.MappingEntryId;
                                                      var url = $pt.getURL('ui.gl.doubleEntries');
                                                      window.open(url + "?mappingEntryId=" + mappingEntryId + "&Type=subLedger");
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
                    transQuerySection: this.createSubLedgerQuery()
                }
            }
        }
    });
    $page.layout = new Layout();
}(typeof window !== "undefined" ? window : this));
