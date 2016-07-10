(function (context) {
    var $page = $pt.getService(context, '$page');
    $page.IBNRCodes = $pt.createCodeTable({url:$ri.getRestfulURL("action.common.codeTable.generate")+810033});
    var layout = jsface.Class({
        createBaseLayout : function(){
            return{
                _sections: {
                    claimSec: {
                        label: "Create IBNR Segment",
                        style: 'primary-darken',
                        pos: {
                            width: 12
                        },
                        layout: {
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
                            SegmentName: {
                                label: "Segment Name",
                                comp: {
                                    type: $pt.ComponentConstants.Text
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
                            Cob: {
                                label: "Main Sub CoB",
                                comp: {
                                    type: $pt.ComponentConstants.Select,
                                    data: $page.IBNRCodes
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
                                    data: $page.codes.CedentCountry
                                },
                                pos: {
                                    row: 2,
                                    width: 4
                                }
                            },
                            ContractNature: {
                                label: "Contract Nature",
                                comp: {
                                    type: $pt.ComponentConstants.Select,
                                    data: $page.codes.ContractNature
                                },
                                pos: {
                                    row: 2,
                                    width: 4
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
                                                window.location.href = "segmentQuery.html";
                                            }
                                        }, {
                                            text: "Submit",
                                            style: "primary",
                                            click: function () {
                                                $page.controller.submitData();
                                                //window.location.href = "segmentQuery.html";
                                            }
                                        }]
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
