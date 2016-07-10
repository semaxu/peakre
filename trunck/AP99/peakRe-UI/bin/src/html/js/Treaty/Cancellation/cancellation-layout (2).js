/**
 * Created by Gordon.Cao on 10/14/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var codes = $pt.getService($page, 'codes');
    codes.Gender = $pt.createCodeTable([
        {id: 'M', text: 'Male'},
        {id: 'F', text: 'Female'}
    ]);
    codes.IdType = $pt.createCodeTable([
        {id: '1', text: 'ID Card'},
        {id: '2', text: 'Driver License'}
    ]);


    $page.layout = {
        _sections: {
            cancellation: {
                label: "Cancellation",
                style:'primary-darken',
                collapsible: true,
                expanded: true,
                layout: {
                    PNOC : {
                        label:"PNOC Terms",
                        pos:{
                            width:12
                        },
                        comp:{
                            type : $pt.ComponentConstants.Panel,
                            editLayout: {
                                forCedent:{
                                    label:"For Cedent",
                                    comp:{
                                        type: $pt.ComponentConstants.Label,
                                        textFromModel: false
                                    },
                                    pos:{
                                        width:4,
                                        row:1
                                    }
                                },
                                monthsForCedent:{
                                    label:"Months",
                                    comp:{
                                        labelDirection: "horizontal",
                                        enabled:true
                                    },
                                    pos:{
                                        width:4,
                                        row:1
                                    }
                                },
                                daysForCedent:{
                                    label:"Days",
                                    comp:{
                                        labelDirection: "horizontal",
                                        enabled:true
                                    },
                                    pos:{
                                        width:4,
                                        row:1
                                    }
                                },
                                forReinsurer:{
                                    label:"Months",
                                    comp:{
                                        type: $pt.ComponentConstants.Label,
                                        textFromModel: false
                                    },
                                    pos:{
                                        width:4,
                                        row:2
                                    }
                                },
                               PnocReinsurerMonth:{
                                    label:"Months",
                                    comp:{
                                        labelDirection: "horizontal",
                                        enabled:true
                                    },
                                    pos:{
                                        width:4,
                                        row:2
                                    }
                                },
                                PnocReinsurerDay:{
                                    label:"Days",
                                    comp:{
                                        labelDirection: "horizontal",
                                        enabled:true
                                    },
                                    pos:{
                                        width:4,
                                        row:2
                                    }
                                },
                                automatic:{
                                    label:"Automatic",
                                    comp:{
                                        type: $pt.ComponentConstants.Label,
                                        textFromModel: false
                                    },
                                    pos:{
                                        width:4,
                                        row:3
                                    }
                                },
                                y_n:{
                                    label:"Y/N",
                                    comp:{
                                        labelDirection: "horizontal",
                                        enabled:true
                                    },
                                    pos:{
                                        width:4,
                                        row:3
                                    }
                                }
                            }
                        }
                    },
                    DNOC : {
                        label:"DNOC Terms",
                        pos:{
                            width:12
                        },
                        comp:{
                            type : $pt.ComponentConstants.Panel,
                            editLayout: {
                                war: {
                                    label: "War",
                                    comp: {
                                        type: $pt.ComponentConstants.Label,
                                        textFromModel: false
                                    },
                                    css: {
                                        cell: 'date-range-link'
                                    },
                                    pos: {
                                        row: 1,
                                        col: 2,
                                        width: 4
                                    }
                                },
                                monthsForWar:{
                                    label:"Months",
                                    comp:{
                                        labelDirection: "horizontal",
                                        enabled:true
                                    },
                                    pos:{
                                        width:4,
                                        row:1
                                    }
                                },
                                daysForWar:{
                                    label:"Days",
                                    comp:{
                                        labelDirection: "horizontal",
                                        enabled:true
                                    },
                                    pos:{
                                        width:4,
                                        row:1
                                    }
                                },
                                politicalRisks:{
                                    label:"Political Risks",
                                    comp:{
                                        type: $pt.ComponentConstants.Label,
                                        textFromModel: false
                                    },
                                    pos:{
                                        width:4,
                                        row:2
                                    }
                                },
                                monthsForPoliticalRisks:{
                                    label:"Months",
                                    comp:{
                                        labelDirection: "horizontal",
                                        enabled:true
                                    },
                                    pos:{
                                        width:4,
                                        row:2
                                    }
                                },
                                daysForPoliticalRisks:{
                                    label:"Days",
                                    comp:{
                                        labelDirection: "horizontal",
                                        enabled:true
                                    },
                                    pos:{
                                        width:4,
                                        row:2
                                    }
                                },
                                sanction:{
                                    label:"Sanction",
                                    comp:{
                                        type: $pt.ComponentConstants.Label,
                                        textFromModel: false
                                    },
                                    pos:{
                                        width:4,
                                        row:3
                                    }
                                },
                                monthsForSanction:{
                                    label:"Months",
                                    comp:{
                                        labelDirection: "horizontal",
                                        enabled:true
                                    },
                                    pos:{
                                        width:4,
                                        row:3
                                    }
                                },
                                daysForSanction:{
                                    label:"Days",
                                    comp:{
                                        labelDirection: "horizontal",
                                        enabled:true
                                    },
                                    pos:{
                                        width:4,
                                        row:3
                                    }
                                }
                            }
                        }
                    },
                    bottomButtons: {
                        comp: {
                            type: $pt.ComponentConstants.ButtonFooter,
                            buttonLayout: {
                                right: [{
                                    text: "Save",
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

        }
    }

}(typeof window !== "undefined" ? window : this));
