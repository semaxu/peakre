(function (context) {
    var $page = $pt.getService(context, '$page');

    $page.layout = {
        _sections: {
            defaultSection: {
                label: "Exception Report",
                style: "primary-darken",
                layout: {
                    ExceptionContractList: {
                        label: "Financial Quarter View:",
                        comp: {
                            type: $pt.ComponentConstants.Table,
                            searchable: false,
                            sortable: false,
                            pageable: true,
                            //addable:true,
                            columns: [
                                {
                                    title: "ContractID",
                                    data: "ContractID",
                                    width: "8%"
                                },
                                {
                                    title: "Reviewed",
                                    data: "ReviewedFlag",
                                    inline: "check",
                                    width: "8%"
                                }
                            ]
                        },
                        pos: {
                            row: 7,
                            width: 12
                        }
                    }
                }
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
                                window.location.href = "exceptionReport.html";
                            }
                        }, {
                            text: "Submit",
                            style: "primary",
                            click: function () {
                                $page.controller.doReview();
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
    };
}(typeof window !== "undefined" ? window : this));