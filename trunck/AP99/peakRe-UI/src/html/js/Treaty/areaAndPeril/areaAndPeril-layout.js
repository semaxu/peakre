/**
 * Created by gang.wang on 10/20/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');

    $page.uwCountryDialogLayout = {
        uwCountries: {
            comp: {
              type: $pt.ComponentConstants.Tree,
              check: true,
              hierarchyCheck: true,
              inactiveSlibing: false,
              expandLevel:"all",
              valueAsArray:true,
              root: false,
              data:$page.codes.Country
            }
        }
    };

    $page.coveredCountryDialogLayout = {
        coveredCountries: {
            comp: {
              type: $pt.ComponentConstants.Tree,
              check: true,
              hierarchyCheck: true,
              inactiveSlibing: false,
              expandLevel:"all",
              valueAsArray:true,
              root: false,
              data:$page.codes.Country
            }
        }
    };

    // $page.countryDialogLayout = {
    //     countries: {
    //         comp: {
    //           type: $pt.ComponentConstants.Tree,
    //           check: true,
    //           hierarchyCheck: true,
    //           inactiveSlibing: false,
    //           expandLevel:"all",
    //           root: false,
    //           data:$page.codes.Country
    //         }
    //     }
    // };

    $page.layout = {
        _sections: {
            areaPeril: {
                label: "Area & Peril",
                style:'primary-darken',
                layout: {
                    uwArea:{
                        label:"UW Area",
                        comp:{
                            type:$pt.ComponentConstants.Panel,
                            editLayout:{
                                area:{
                                  // label:"UW Area : ",
                                  comp:{
                                    type:$pt.ComponentConstants.Label
                                  },
                                  pos:{
                                    row:1,
                                    width:1
                                  }
                                },
                                uwAreaSelected:{
                                  comp:{
                                    type:{tyep:$pt.ComponentConstants.Text, label:false},
                                    enabled:false
                                  },
                                  pos:{
                                    row:1,
                                    width:4
                                  }
                                },
                                uwAreaButton:{
                                    // label:"Select Area",
                                    comp:{
                                      type:$pt.ComponentConstants.Button,
                                      style:"link",
                                      icon:"plus",
                                      click:function(model){
                                        $page.control.showTreeDialog(model, $page.codes.Country, $page.uwCountryDialogLayout, 'uwCountries', 'uwAreaSelected');
                                      }
                                    },
                                    pos:{
                                      row:1,
                                      width:1
                                    }
                                },
                                areaRemarkPanel:{
                                    label:"Remarks",
                                    comp:{
                                      type:$pt.ComponentConstants.Panel,
                                      editLayout:{
                                        areaRemark:{
                                          comp:{
                                            type:{type:$pt.ComponentConstants.TextArea,label:false},
                                            lines:3,
                                          },
                                          pos:{
                                            width:12
                                          }
                                        }
                                      }
                                    },
                                    pos:{
                                      row:2,
                                      width:12
                                    }
                                }
                            }
                        },
                        pos:{
                          width:12
                        }
                    },
                    covered:{
                        label:"Covered",
                        comp:{
                            type:$pt.ComponentConstants.Panel,
                            editLayout:{
                              area:{
                                // label:"UW Area : ",
                                comp:{
                                  type:$pt.ComponentConstants.Label
                                },
                                pos:{
                                  row:1,
                                  width:1
                                }
                              },
                              coveredAreaSelected:{
                                comp:{
                                  type:{tyep:$pt.ComponentConstants.Text, label:false},
                                  enabled:false
                                },
                                pos:{
                                  row:1,
                                  width:4
                                }
                              },
                              coveredAreaButton:{
                                  // label:"Select Area",
                                  comp:{
                                    type:$pt.ComponentConstants.Button,
                                    style:"link",
                                    icon:"plus",
                                    click:function(model){
                                      $page.control.showTreeDialog(model, $page.codes.Country, $page.coveredCountryDialogLayout, 'coveredCountries', 'coveredAreaSelected');
                                    }
                                  },
                                  pos:{
                                    row:1,
                                    width:1
                                  }
                              },
                              coveredRemarkPanel:{
                                label:"Remarks",
                                comp:{
                                  type:$pt.ComponentConstants.Panel,
                                  editLayout:{
                                    coveredRemark:{
                                      comp:{
                                        type:{type:$pt.ComponentConstants.TextArea,label:false},
                                        lines:3,
                                      },
                                      pos:{
                                        width:12
                                      }
                                    }
                                  }
                                },
                                pos:{
                                  row:2,
                                  width:12
                                }
                              }
                            }
                        },
                        pos:{
                          width:12
                        }
                    },
                    peril:{
                        label:"Peril",
                        comp:{
                            type:$pt.ComponentConstants.Panel,
                            editLayout:{
                              // area:{
                              //   // label:"UW Area : ",
                              //   comp:{
                              //     type:$pt.ComponentConstants.Label
                              //   },
                              //   pos:{
                              //     row:1,
                              //     width:1
                              //   }
                              // },
                              // perilAreaSelected:{
                              //   comp:{
                              //     type:{tyep:$pt.ComponentConstants.Text, label:false},
                              //     enabled:false
                              //   },
                              //   pos:{
                              //     row:1,
                              //     width:4
                              //   }
                              // },
                              // perilAreaButton:{
                              //     // label:"Select Area",
                              //     comp:{
                              //       type:$pt.ComponentConstants.Button,
                              //       style:"link",
                              //       icon:"plus",
                              //       click:function(model){
                              //         $page.control.showTreeDialog(model, $page.perilCountryModel, $page.countryDialogLayout, 'countries', 'perilAreaSelected');
                              //       }
                              //     },
                              //     pos:{
                              //       row:1,
                              //       width:1
                              //     }
                              // },
                              perilAreaSelect:{
                                label:"Area",
                                comp:{
                                  type:$pt.ComponentConstants.SelectTree,
                                  data:$page.codes.Country,
                                  // root: true,
                                  // check: true,
                                  hideChildWhenParentChecked: true,
                                  labelWidth:3,
                                  treeLayout:{
                                    comp:{
                                      hierarchyCheck: true,
                                      expandLevel:"all",
                                      inactiveSlibing: false,
                                      valueAsArray:true
                                    }
                                  }
                                },
                                pos:{
                                  row:1,
                                  width:6
                                }
                              },
                              perilRemarkPanel:{
                                label:"Remarks",
                                comp:{
                                  type:$pt.ComponentConstants.Panel,
                                  editLayout:{
                                    perilRemark:{
                                      comp:{
                                        type:{type:$pt.ComponentConstants.TextArea,label:false},
                                        lines:3,
                                      },
                                      pos:{
                                        width:12
                                      }
                                    }
                                  }
                                },
                                pos:{
                                  row:2,
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
    };

}(typeof window !== "undefined" ? window : this));
