(function (context) {
    var $page = $pt.getService(context, '$page');

    $page.layout = {
        _sections: {
            searchCondition: {
                label: "Bank Account Management",
                style: 'primary-darken',
                pos: {
                    width: 12
                },
                layout: {
                    searchPanel: {
                        label: "Search Criteria",
                        pos: {
                            width: 12,
                            row: 1
                        },
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                baseCurrency: {
                                    label: "Basic Currency",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    },
                                    pos: {
                                        row: 1,
                                        width: 3
                                    }
                                },
                                exchangeCurrency: {
                                    label: "Exchange Currency",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    },
                                    pos: {
                                        row: 1,
                                        width: 3
                                    }
                                },
                                currency: {
                                    label: "Currency",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        minimumResultsForSearch: -1,
                                        data: $page.codes.Cause,
                                        //allowClear : true,
                                        placeholder: "please select..."
                                    },
                                    pos: {
                                        row: 1,
                                        width: 3
                                    }
                                },
                                branch: {
                                    label: "Branch",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        minimumResultsForSearch: -1,
                                        data: $page.codes.Cause,
                                        //allowClear : true,
                                        placeholder: "please select..."
                                    },
                                    pos: {
                                        row: 1,
                                        width: 3
                                    }
                                },
                                searchButtons: {
                                    comp: {
                                        type: $pt.ComponentConstants.ButtonFooter,
                                        buttonLayout: {
                                            right: [{
                                                text: "Reset",
                                                style: "warning",
                                                click: function () {

                                                }
                                            }, {
                                                text: "Search",
                                                style: "primary"
                                            }]
                                        }
                                    },
                                    pos: {
                                        width: 12,
                                        row: 2
                                    }
                                }
                            }
                        }
                    },
                    searchTable: {
                        comp: {
                            type: $pt.ComponentConstants.Table,
                            //indexable : true,
                            sortable: false,
                            pageable: true,
                            countPerPage: 5,
                            searchable: false,
                            editable: false,
                            removable: false,
                            addable: true,
                            //   criteria : "paginationCriteria",
                            columns: [
                                {title: "Branch", data: "branch", inline: "text"},
                                {title: "Bank Account Number", data: "bankAccountNumber", inline: "text"},
                                {title: "Bank Account Name", data: "bankAccountName", inline: "text"},
                                {title: "Currency", data: "currency", inline: "select", codes: $page.codes.Currency},
                                {title: "Bank", data: "bank", inline: "text"},
                                {
                                    title: "Account Type",
                                    data: "accountType",
                                    inline: "select",
                                    codes: $page.codes.AccountType
                                },
                                {title: "Status", data: "status", inline: "select", codes: $page.codes.AccountStatus}

                            ],
                            addClick: function (model, item, layout) {
                                // alert("addClick");
                                // model.add("specificReinstatementData",
                                //   {reinstatementNum:"1",rate:0.1,time:"N",amount:"Y"}
                                // );
                                // console.log(item);
                                model.add("searchTable", item.getCurrentModel());
                                // console.log(model.get("specificReinstatementData"));
                                // this.forceUpdate();
                            }

                        },
                        pos: {
                            width: 12,
                            row: 3

                        }
                    },
                    bottomButtons: {
                        comp: {
                            type: $pt.ComponentConstants.ButtonFooter,
                            buttonLayout: {
                                right: [{
                                    text: "Exit",
                                    style: "warning",
                                    click: function () {

                                    }
                                }, {
                                    text: "Save",
                                    style: "primary"
                                }, {
                                    text: "Delete",
                                    style: "primary"
                                }]
                            }
                        },
                        pos: {
                            width: 12,
                            row: 4
                        }
                    }
                }
            }
        }
    }

}(typeof window !== "undefined" ? window : this));