(function (context) {
    var $page = $pt.getService(context, '$page');

    var layout = jsface.Class({
        createBaseLayout : function(){
            return{
                _sections: {
                    claimSec: {
                        label: "Segment Detail",
                        style: 'primary-darken',
                        pos: {
                            width: 12
                        },
                        layout: {
                            SegmentName: {
                                label: "Segment Name",
                                comp: {
                                    type: $pt.ComponentConstants.Text,
                                    enabled: false
                                },
                                pos: {
                                    row: 1,
                                    width: 4
                                }
                            },
                            SegmentCode: {
                                label: "Segment Code",
                                comp: {
                                    type: $pt.ComponentConstants.Text,
                                    enabled: false
                                },
                                pos: {
                                    row: 1,
                                    width: 4
                                }
                            },
                            Status: {
                                label: "Segment Status",
                                comp: {
                                    type: $pt.ComponentConstants.Select,
                                    data: $page.codes.AccountStatus
                                },
                                pos: {
                                    row: 1,
                                    width: 4
                                }
                            },
                            ContractNature: {
                                label: "Contract Nature",
                                comp: {
                                    type: $pt.ComponentConstants.Select,
                                    data: $page.codes.ContractNature,
                                    enabled: false
                                },
                                pos: {
                                    row: 2,
                                    width: 4
                                }
                            },
                            CedentCountry: {
                                label: "Cedent Country",
                                comp: {
                                    type: $pt.ComponentConstants.Select,
                                    data: $page.codes.CedentCountry,
                                    enabled: false
                                },
                                pos: {
                                    row: 2,
                                    width: 4
                                }
                            },
                            Cob: {
                                label: "Main Sub CoB",
                                comp: {
                                    type: $pt.ComponentConstants.Select,
                                    data: $page.codes.SubClass,
                                    enabled: false
                                },
                                pos: {
                                    row: 2,
                                    width: 4
                                }
                            },
                            segmentTable: {
                                label: "",
                                comp: {
                                    type: $pt.ComponentConstants.Table,
                                    sortable: false,
                                    searchable: false,
                                    header: false,
                                    view:true,
                                    columns: [
                                        {title: "", data: "FnQuarter", width: "20%"},
                                        {title: "UY2013(USD)", data: "Uy2013",
                                        inline:$helper.registerInlineAmountUnable(2),
                                        width: "20%"},
                                        {title: "UY2014(USD)", data: "Uy2014",
                                        inline:$helper.registerInlineAmountUnable(2),
                                        width: "20%"},
                                        {title: "UY2015(USD)", data: "Uy2015",
                                        inline:$helper.registerInlineAmountUnable(2),
                                        width: "20%"},
                                        {title: "UY2016(USD)", data: "Uy2016",
                                        inline:$helper.registerInlineAmountUnable(2),
                                        width: "20%"}
                                        //{title: "UY2017(USD)", data: "Uy2017",
                                        //inline:$helper.registerInlineAmountUnable(2),
                                        //width: "20%"}
                                    ]
                                },
                                pos: {
                                    row: 3,
                                    width: 12

                                }
                            },
                            bottomButtons: {
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
                                            }, {
                                                text: "Save",
                                                style: "primary",
                                                click: function () {
                                                    $page.controller.save();
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
            }
        },
        createLayout : function(){
            return $.extend(true,{},this.createBaseLayout());
        }
    });

    $page.layout = new layout();

}(typeof window !== "undefined" ? window : this));
