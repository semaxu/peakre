(function (context) {
    var $page = $pt.getService(context, '$page');

    $page.layout = {
        _sections: {
            defaultSection: {
                label: "Exception Report",
                style: "primary-darken",
                layout: {
                    financialYear: {
                        label: "Financial Year",
                        comp: {
                            type: $pt.ComponentConstants.Select,
                            data: $page.codes.FNYear
                        },
                        pos: {
                            row: 1
                        }
                    },
                    financialQuarter: {
                        label: "Financial Quarter",
                        comp: {
                            type: $pt.ComponentConstants.Select,
                            data: $page.codes.FNQuarter
                        },
                        pos: {
                            row: 1
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
                                            window.location.href = "/peak-basic-ui/src/main/webapp/html/index.html";
                                        }
                                    }, {
                                        text: "Search",
                                        style: "primary",
                                        click: function (model) {
                                            $page.controller.doSearch();
                                        }
                                    }, {
                                        text: "Execute",
                                        style: "primary",
                                        click: function (model) {
                                            $page.controller.doExecute();
                                        }
                                    }
                                ]
                            }
                        },
                        pos: {
                            row: 2,
                            width: 12
                        }
                    },
                    queryResult: {
                        label: "  ",
                        comp: {
                            type: $pt.ComponentConstants.Table,
                            searchable: false,
                            sortable: false,
                            pageable: true,
                            criteria: 'cachedCriteria',
                            //addable:true,
                            columns: [
                                {
                                    title: "FN Year Quarter",
                                    data: "FnyearAndQuarter",
                                    width: "15%"
                                },
                                {
                                    title: "contracCompID",
                                    data: "ContracCompID",
                                    width: "8%",
                                    visible:false
                                }
                            ],

                            rowOperations: [
                                {
                                    tooltip: "View",
                                    click: function (rowModel) {
                                        $page.controller.doView(rowModel);
                                    }
                                }
                            ]
                        },
                        pos: {
                            row: 7,
                            width: 12
                        }
                    },
                }
            }
        }
    };
}(typeof window !== "undefined" ? window : this));