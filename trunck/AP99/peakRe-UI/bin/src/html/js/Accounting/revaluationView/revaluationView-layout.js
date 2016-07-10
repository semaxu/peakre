(function (context) {
    var $page = $pt.getService(context, '$page');

    $page.layout = {
        _sections: {
            defaultSection: {
                label: "Revaluation",
                style: "primary-darken",
                layout: {

                    queryResult: {
                        label: " ",
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
                                    data: "Fnquarter",
                                    width: "14%"
                                },
                                {
                                    title: "Revaluate Value",
                                    data: "RevaluateValue",
                                    width: "14%"
                                },
                                {
                                    title: "Currency Code",
                                    data: "CurrencyCode",
                                    width: "14%"
                                }
                            ],

                            rowOperations: [
                                {
                                    tooltip: "Export Excel",
                                    click: function (rowModel) {
                                        $page.controller.exportExcel();
                                    }
                                }
                            ]
                        },
                        pos: {
                            row: 1,
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
                                            window.location.href = "batchJobTrigger.html";
                                        }
                                    }
                                ]
                            }
                        },
                        pos: {
                            row: 2,
                            width: 12
                        }
                    }
                }
            }
        }
    };
}(typeof window !== "undefined" ? window : this));