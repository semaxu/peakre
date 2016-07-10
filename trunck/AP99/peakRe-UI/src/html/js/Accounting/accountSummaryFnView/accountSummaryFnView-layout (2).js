(function(context){
    var $page = $pt.getService(context,'$page');

    $page.layout = {
        _sections:{
            defaultSection:{
                label:"Account Summary(FN view)",
                style:"primary-darken",
                collapsible:false,
                layout:{
                    summaryTable:{
                        comp:{
                            type:$pt.ComponentConstants.Table,
                            searchable:false,
                            sortable:false,
                            columns:[
                                {
                                    title:"Entry Code",
                                    data:"entryCode"
                                }, {
                                    title:"Entry Item",
                                    data:"entryItem"
                                }, {
                                    title:"2015 Q1",
                                    data:"s2015Q1"
                                }, {
                                    title:"2015 Q2",
                                    data:"s2015Q2"
                                }, {
                                    title:"2015 Q3",
                                    data:"s2015Q3"
                                }, {
                                    title:"2015 Q4",
                                    data:"s2015Q4"
                                }, {
                                    title:"Grand Total",
                                    data:"grandTotal"
                                }
                            ]
                        },
                        pos:{
                            width:12
                        }
                    },
                    buttonPanel:{
                        comp:{
                            type:$pt.ComponentConstants.ButtonFooter,
                            buttonLayout:{
                                right:[
                                    {
                                        text:"Exit",
                                        style:"warning"
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
    };

}(typeof window !== "undefined" ? window : this));