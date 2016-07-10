(function (context) {
    var $page = $pt.getService(context, '$page');
    var Layout = jsface.Class({
        createDoubleEntries: function () {
            return {
                label: "Double Entries",
                style: 'primary-darken',
                layout: {
                    SearchResult: {
                        label: "Double Entries",
                        comp: {
                            type: $pt.ComponentConstants.Table,
                            searchable: false,
                            sortable: false,
                            addable: false,
                            columns: [
                                {
                                    title: "Credit/Debit",
                                    data: "CreditDebit"
                                },
                                {
                                    title: "GL Account",
                                    data: "GlAccount"
                                }, {
                                    title: "GL Account Text",
                                    data: "GlAccountText"/*,
                                     width: 120*/
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
                                        text: "Close",
                                        style: "warning",
                                        click: function () {
                                        	window.close();
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
                    doubleEntriesSection: this.createDoubleEntries()
                }
            }
        }
    });
    $page.layout = new Layout();
}(typeof window !== "undefined" ? window : this));
