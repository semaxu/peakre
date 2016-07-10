/**
 * Created by gang.wang on 10/20/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');

    $page.layout = {
        _sections: {
            share: {
                label: "Accounting Conditions",
                style:'primary-darken',
                layout: {
                    accountingBasis:{
                        label:"Accounting Basis",
                        comp:{
                          type:$pt.ComponentConstants.Select,
                          // data:$page.codes.Boolean
                        },
                        pos:{
                          row:1
                          // width:10
                        }
                    },
                    earningPattern:{
                        label:"Earning Pattern",
                        comp:{
                          type:$pt.ComponentConstants.Select
                        },
                        pos:{
                          row:1
                          // width:10
                        }
                    },
                    accountFreq:{
                        label:"Account Frequency",
                        comp:{
                          type:$pt.ComponentConstants.Select
                        },
                        pos:{
                          row:2
                          // width:10
                        }
                    },
                    asAtDate:{
                      label:"First Account as at Date",
                      comp:{
                        type:$pt.ComponentConstants.Date,
                      },
                      pos:{
                        row:2
                        // width:12
                      }
                    },
                    threshold:{
                      label:"Threshold for Difference(Actual vs. Accrual)",
                      comp:{
                        type:$pt.ComponentConstants.Panel,
                        editLayout:{
                          percentageOfPremium:{
                            label:"Percentage Of Premium",
                            comp:{
                              type:$pt.ComponentConstants.Text
                            }
                          },
                          dataForReview:{
                            label:"Date for Review",
                            comp:{
                              type:$pt.ComponentConstants.Date
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
    }
}(typeof window !== "undefined" ? window : this));
