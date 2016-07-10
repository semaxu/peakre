/**
 * Created by gang.wang on 10/20/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');

    $page.layout = {
        _sections: {
            Clauses: {
                label: "Clauses",
                style:'primary-darken',
                layout: {
                    ClausesRequired:{
                        label:"Required",
                        comp:{
                            type:$pt.ComponentConstants.Panel,
                            editLayout:{
                                uwArea:{
                                    label:"Select Clause",
                                    comp:{
                                        type:$pt.ComponentConstants.Button,
                                        style:"link",
                                        click:function(model){
                                            $page.control.showCountryDialog($page.requiredClauseModel, model, 'uwAreaSelected');
                                        }
                                    },
                                    pos:{
                                        row:1
                                        // width:10
                                    }
                                },
                                uwAreaSelected:{
                                    comp:{
                                        tyep:$pt.ComponentConstants.Text,
                                        enabled:false
                                    },
                                    pos:{
                                        row:1
                                    }
                                }

                            }
                        },
                        pos:{
                            width:12
                        }
                    },
                    ClausesRecommend:{
                        label:"Recommend",
                        comp:{
                            type:$pt.ComponentConstants.Panel,
                            editLayout:{
                                coveredArea:{
                                    label:"Select Clause",
                                    comp:{
                                        type:$pt.ComponentConstants.Button,
                                        style:"link",
                                        click:function(model){
                                            $page.control.showCountryDialog($page.recommendClauseModel, model, 'coveredAreaSelected');
                                        }
                                    },
                                    pos:{
                                        row:1
                                        // width:10
                                    }
                                },
                                coveredAreaSelected:{
                                    comp:{
                                        tyep:$pt.ComponentConstants.Text,
                                        enabled:false
                                    },
                                    pos:{
                                        row:1
                                    }
                                }
                            }
                        },
                        pos:{
                            width:12
                        }
                    },


                    rightButtons: {
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
    };

    $page.countryDialogLayout = {
        clauses: {
            comp: {
                type: $pt.ComponentConstants.Tree,
                check: "selected",
                hierarchyCheck: true,
                inactiveSlibing: false,
                root: false
            }
        }
    };

}(typeof window !== "undefined" ? window : this));
