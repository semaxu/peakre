/**
 * Created by gang.wang on 10/20/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');

    $page.layout = {
        _sections: {
            share: {
                label: "Claim Conditions",
                style:'primary-darken',
                layout: {
                    claimBasis:{
                        label:"Claim Basis",
                        comp:{
                          type:$pt.ComponentConstants.Select,
                          // data:$page.codes.Boolean
                        },
                        pos:{
                          // row:1
                          // width:10
                        }
                    },
                    retroactiveDate:{
                        label:"Retroactive Date",
                        comp:{
                          type:$pt.ComponentConstants.Date
                        },
                        pos:{
                          // row:2
                          // width:10
                        }
                    },
                    sunsetClause:{
                        label:"Sunset Clause",
                        comp:{
                          type:$pt.ComponentConstants.Date
                        },
                        pos:{
                          // row:2
                          // width:10
                        }
                    },
                    claimAdviceLimit:{
                      label:"Claim Advice Limit",
                      comp:{
                        type:$pt.ComponentConstants.Panel,
                        editLayout:{
                          currency:{
                            label:"Currency",
                            comp:{
                              type:$pt.ComponentConstants.Select,
                              data:$page.codes.Currency
                            }
                          },
                          lossAdvice:{
                            label:"Loss Advice"
                          },
                          post:{
                            label:"Posting",
                            comp:{
                              type:$pt.ComponentConstants.Check
                            }
                          },
                          cashCallAdvice:{
                            label:"Cash Call Advice"
                          },
                          noOfDay:{
                            label:"No. of Days"
                          }
                        }
                      },
                      pos:{
                        width:12
                      }
                    },
                    ContractClaimConditionItemList:{
                      label:"Additional Claim Limits",
                      comp:{
                        type:$pt.ComponentConstants.Table,
                        addable:false,
                        removable: false,
                        searchable:false,
                        sortable:false,
                        columns : [
                            {title : "", data : "IsCheck",inline:"check",width:"2%", type: {type: $pt.ComponentConstants.Check, label: false},
                                labelAttached: true},
                            {title : "",
                              render:function(row){
                                return $page.codes.ClaimLimitCate.getText(row.cateId);
                              },width:"48%"},
                            {title : "Percentage", data : "percentage",inline:"percentage",width:"25%"},
                            {title : "Amount", data : "amount",inline:"number",width:"25%"}
                        ],
                      },
                      css: {
                          comp: "inline-editor"
                      },
                      pos:{
                        width:12
                      }
                    },
                    indexProduct:{
                      label:"Index Product",
                      comp:{
                        type:$pt.ComponentConstants.Panel,
                        editLayout:{
                          weatherIndexs:{
                            label:"Weather Indexs",
                            comp:{
                              type:$pt.ComponentConstants.ArrayCheck,
                              data:$page.codes.WeatherIndexs,
                              labelWidth:3
                            },
                            pos:{
                              row:1,
                              width:8
                            }
                          },
                          priceIndexs:{
                            label:"Price Indexs",
                            comp:{
                              type:$pt.ComponentConstants.ArrayCheck,
                              data:$page.codes.PriceIndexs,
                              labelWidth:3
                            },
                            pos:{
                              row:2,
                              width:8
                            }
                          },
                          specify:{
                            label:"Specify",
                            comp:{
                              type:$pt.ComponentConstants.Text,
                              visible:{
                                when: function(model) {
                                  // alert(model.get('priceIndexs'));
                                  var selectedIndex = model.get('priceIndexs');
                                  for (var i in selectedIndex) {
                                    if(selectedIndex[i] == '3')
                                      return true;
                                  }
                                  return false;
                                },
                                depends: 'priceIndexs'
                              },
                            },
                            pos:{
                              row:2
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
