(function (context) {
    var $page = $pt.getService(context, '$page');

    $page.layout = {
        _sections: {
            ClaimSec: {
                label: "Deductions",
                style:'primary-darken',
                layout: {
                    Brokerage:{
                        label:"Brokerage Expense",
                        comp:{
                            type:{
                                labelDirection: "horizontal",
                                enabled:true
                            },
                            rightAddon:{
                                text: "%"
                            }
                        },
                        pos:{
                            width:4,
                            row:1
                        }
                    },
                    Brokerage2:{
                        label:"",
                        comp:{
                            type:{
                                labelDirection: "horizontal",
                                enabled:true
                            }
                        },
                        pos:{
                            width:4,
                            row:1
                        }
                    },
                    nonlinear : {
                        label:"Other Deductions",
                        pos:{
                            width:12,
                            row:2
                        },
                        comp:{
                            type : $pt.ComponentConstants.Panel,
                            editLayout: {
                                    table: {
                                    label: "",
                                    comp: {
                                        type: $pt.ComponentConstants.Table,
                                        //indexable : true,
                                        //sortable:true,
                                        //pageable:true,
                                        //countPerPage : 5,
                                        //searchable:false,
                                        editable: true,
                                        removable: true,
                                        addable: true,
                                        //criteria : "paginationCriteria",
                                        columns: [
                                            {title: "Deduction Item", data: "Item" },
                                            {title: "In Percentage", data: "Percentage"},
                                            {title: "In Amount", data: "Amount"},
                                            {title: "Remarks", data: "Remarks"}
                                        ]

                                    },
                                    pos: {
                                        width: 8,
                                        row: 1

                                    }
                                },
                                buttoms: {
                                    comp: {
                                        type: $pt.ComponentConstants.ButtonFooter,
                                        buttonLayout: {
                                            right: [{
                                                text: "Upload Nonlinear Table",
                                                style: "primary",
                                                click:function(){

                                                }
                                            }]
                                        }
                                    },
                                    pos:{
                                        width:12
                                    }
                                }

                            }
                        }
                    },
                    RemarkDeduction: {
                        label: "Remark",
                        comp: {
                            type: $pt.ComponentConstants.TextArea,
                            lines: 3,
                            labelDirection: "horizontal"
                        },
                        pos: {
                            width: 12,
                            row:3,
                            col: 12
                        }
                    },
                    bottomButtons: {
                        comp: {
                            type: $pt.ComponentConstants.ButtonFooter,
                            buttonLayout: {
                                right: [
                                    {
                                        text: "Exit",
                                        style: "warning"
                                    }, {
                                        text: "Save",
                                        style: "primary"
                                    }
                                ]
                            }
                        },
                        pos:{
                            width:12
                        }
                    }
                }
            }

        }
    }

}(typeof window !== "undefined" ? window : this));