(function (context) {
    var $page = $pt.getService(context, '$page');
    $page.layout = {
        _sections: {
            defaultSection: {
                label: "Transaction details",
                style: "primary-darken",
                collapsible: false,
                layout: {
                    accountTable: {
                        comp: {
                            type: $pt.ComponentConstants.Table,
                            searchable: false,
                            sortable: false,
                            header: false,
                            columns: [
                                {
                                    title: "Entry Code",
                                    data: "entryCode",
                                    width:"10%"
                                }, {
                                    title: "Entry Item",
                                    data: "entryItem",
                                    width:"20%"
                                }, {
                                    title: "Text(Source)",
                                    data: "text",
                                    width:"20%"
                                }, {
                                    title: "Input Date",
                                    data: "inputDate",
                                    width:"15%"
                                }, {
                                    title: "Amount(USD)",
                                    data: "amount",
                                    width:"15%"
                                }, {
                                    title: "Input User",
                                    data: "inputUser",
                                    width:"20%"
                                }
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
                                            window.close();
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
}(typeof window !== "undefined" ? window : this));