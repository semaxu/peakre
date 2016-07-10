(function (context) {
    var $page = $pt.getService(context, '$page');
    NTable.registerInlineEditor('linkedBtn', {
        comp: {
            type: $pt.ComponentConstants.Button,
            style: 'link',
            labelFromModel: true,
            click: function (model) {
            	console.log('link to entryItemInformation query ');
                var generalLedgerId = model.get('GeneralLedgerId');
                console.log('GeneralLedgerId = '+GeneralLedgerId);
                var url = $pt.getURL('ui.gl.entryItemInformation');
                window.location.href = (url + "?generalLedgerId=" + generalLedgerId);
                console.log('query entryItemInformation url = '+url);
            }
        },
        css: {
            comp: 'link-in-table-cell'
        }
    });
    var Layout = jsface.Class({
        createGeneralLedgerQuery: function () {
            return {
                label: "GL Data Query",
                style: 'primary-darken',
                layout: {
                    SearchCriteria: {
                        label: "Search Criteria",
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                condition_GlAccount: {
                                    label: "GL Account",
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
                                        row: 1
                                    }
                                },
                                condition_PostStatus: {
                                    label: "Post Status",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.codes.GL_PostStatus
                                    },
                                    pos: {
                                        row: 1
                                    }
                                },
                                condition_DocumentDateFrom: {
                                    label: "Document Date from",
                                    comp: {
                                        type: $pt.ComponentConstants.Date,
                                        format: "DD/MM/YYYY",
                                        valueFormat: "DD/MM/YYYY"
                                    },
                                    pos: {
                                        row: 2
                                    }
                                },
                                condition_DocumentDateTo: {
                                    label: "Document Date to",
                                    comp: {
                                        type: $pt.ComponentConstants.Date,
                                        format: "DD/MM/YYYY",
                                        valueFormat: "DD/MM/YYYY"
                                    },
                                    pos: {
                                        row: 2
                                    }
                                },
                                condition_PostDateFrom: {
                                    label: "Post Date from",
                                    comp: {
                                        type: $pt.ComponentConstants.Date,
                                        format: "DD/MM/YYYY",
                                        valueFormat: "DD/MM/YYYY"
                                    },
                                    pos: {
                                        row: 2
                                    }
                                },
                                condition_PostDateTo: {
                                    label: "Post Date to",
                                    comp: {
                                        type: $pt.ComponentConstants.Date,
                                        format: "DD/MM/YYYY",
                                        valueFormat: "DD/MM/YYYY"
                                    },
                                    pos: {
                                        row: 3
                                    }
                                },
                                searchButton: {
                                    comp: {
                                        type: $pt.ComponentConstants.ButtonFooter,
                                        buttonLayout: {
                                            right: [{
                                                    text: "Reset",
                                                    style: "warning",
                                                    click: function () {
                                                        $page.controller.resetGeneralLedgerQuery();
                                                    }
                                                }, {
                                                    text: "Search",
                                                    style: "primary",
                                                    click: function (model) {
                                                        $page.controller.searchGeneralLedgerQuery(model);
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
	                                  title: "General Ledger Id",
	                                  data: "GeneralLedgerId",
	                                  visible: {
                                          when: function () {
                                              return false;
                                          }
                                      }
	                              }, {
                                    title: "GL Account",
                                    data: "GlAccount"
                                }, {
                                    title: "GL Account Text",
                                    data: "GlAccountText"/*,
                                    width: 120*/
                                }, {
                                    title: "Sub Transaction",
                                    data: "SubTransaction"
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
                                    title: "Credit/Debit",
                                    data: "CreditDebit"
                                }, {
                                    title: "Document Date",
                                    data: "DocumentDate",
                                    render: function (row) {
                                        var documentDate = "";
                                        if (undefined != row.DocumentDate && null != row.DocumentDate && "" != row.DocumentDate){
                                            documentDate = moment(row.DocumentDate,"YYYY-MM-DD").format("DD/MM/YYYY")
                                        }
                                        return documentDate;
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
                                    title: "Post Status",
                                    data: "PostStatus",
                                    codes: $page.codes.GL_PostStatus
                                }
                            ],
                    		rowOperations: [
                                            {
                                                tooltip: "View",
                                                click:function(model){
                                                	var generalLedgerId = model.GeneralLedgerId;
                                                    console.log('GeneralLedgerId = '+generalLedgerId);
                                                    var url = $pt.getURL('ui.gl.entryItemInformation');
                                                    window.open(url + "?generalLedgerId=" + generalLedgerId);
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
                    generalLedgerQuerySection: this.createGeneralLedgerQuery()
                }
            }
        }
    });
    $page.layout = new Layout();
}(typeof window !== "undefined" ? window : this));
