/**
 * Created by gang.wang on 10/20/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');

    $page.layout = {
        _sections: {
            reserve: {
                label: "Reserve & Rebate",
                style:'primary-darken',
                collapsible : true,
                expanded : true,
                layout: {
                    reserveConditions:{
                        label:"Reserve Conditions",
                        comp:{
                            type:$pt.ComponentConstants.Panel,
                            editLayout:{
                                premiumCalcMethod:{
                                    label:"Premium Calc Method",
                                    comp:{
                                      type:$pt.ComponentConstants.Select,
                                      // data:$page.codes.TypeOfReinstatement,
                                      // labelWidth:3
                                    },
                                    pos:{
                                      // width:10
                                    }
                                },
                                premiumReserve:{
                                  label:"Premium Reserve",
                                  comp:{
                                    type:$pt.ComponentConstants.Text,
                                    convertor: NText.PERCENTAGE,
                                    rightAddon: {
                                      text: '%'
                                    }
                                  },
                                  base : $helper.basePercentage(2),
                                },
                                interestRate:{
                                  label:"Interest Rate",
                                  comp:{
                                    type:$pt.ComponentConstants.Text,
                                    convertor: NText.PERCENTAGE,
                                    rightAddon: {
                                      text: '%'
                                    }
                                  },
                                  base : $helper.basePercentage(2),
                                },
                                lossCalcMethod:{
                                    label:"Loss Calc Method",
                                    comp:{
                                      type:$pt.ComponentConstants.Select,
                                      // data:$page.codes.TypeOfReinstatement,
                                      // labelWidth:3
                                    },
                                    pos:{
                                      // width:10
                                    }
                                },
                                lossReserves:{
                                  label:"Loss Reserves",
                                    base : $helper.basePercentage(2),
                                  comp:{
                                    type:$pt.ComponentConstants.Text,
                                    convertor: NText.PERCENTAGE,
                                    rightAddon: {
                                      text: '%'
                                    }
                                  }
                                },

                            }
                        },
                        pos:{
                          width:12
                        }
                    },
                    rebateConditions:{
                        label:"Rebate Conditions",
                        comp:{
                            type:$pt.ComponentConstants.Panel,
                            editLayout:{
                                noClaimBonus:{
                                    label:"No Claims Bonus",
                                    comp:{
                                      type:$pt.ComponentConstants.Text,
                                      convertor: NText.PERCENTAGE,
                                      rightAddon: {
                                        text: '%'
                                      }
                                    },
                                    base : $helper.basePercentage(2),
                                    pos:{
                                      row:1
                                      // width:10
                                    }
                                },
                                rebatePercent:{
                                  label:"Rebate Percent",
                                  comp:{
                                    type:$pt.ComponentConstants.Text,
                                    convertor: NText.PERCENTAGE,
                                    rightAddon: {
                                      text: '%'
                                    }
                                  },
                                    base : $helper.basePercentage(2),
                                  pos:{
                                    row:2
                                    // width:10
                                  }
                                },
                                lossRatio:{
                                  label:"Loss Ratio",
                                  comp:{
                                    type:$pt.ComponentConstants.Text,
                                    convertor: NText.PERCENTAGE,
                                    rightAddon: {
                                      text: '%'
                                    }
                                  },
                                    base : $helper.basePercentage(2),
                                  pos:{
                                    row:2
                                    // width:10
                                  }
                                },
                                calculationDate:{
                                  label:"Calculation Date",
                                  comp:{
                                      type:$pt.ComponentConstants.Date,
                                      enabled:false
                                  },
                                  pos:{
                                    row:3
                                  }
                                },
                                yearAfterExpiration:{
                                    label:"Year after expiration",
                                    comp:{
                                      type:$pt.ComponentConstants.Text,
                                    },
                                    pos:{
                                      row:4
                                      // width:10
                                    }
                                },
                                monthAfterExpiration:{
                                  label:"Month after expiration",
                                  comp:{
                                    type:$pt.ComponentConstants.Text
                                  },
                                  pos:{
                                    row:4
                                  }
                                },
                                daysAfterExpiration:{
                                  label:"Days after expiration",
                                  comp:{
                                    type:$pt.ComponentConstants.Text
                                  },
                                  pos:{
                                    row:4
                                  }
                                },
                                yearsBeforeLRCalc:{
                                  label:"Years before LR calc",
                                  comp:{
                                    type:$pt.ComponentConstants.Text
                                  },
                                  pos:{
                                    row:5
                                  }
                                }
                            }
                        },
                        pos:{
                          width:12
                        }
                    },
                    remarkPanel:{
                      label:"Remarks",
                      comp:{
                        type:$pt.ComponentConstants.Panel,
                        editLayout:{
                          remark:{
                            comp:{
                              type:{type:$pt.ComponentConstants.TextArea,label:false},
                              lines:3
                            },
                            pos:{
                              width:12
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
