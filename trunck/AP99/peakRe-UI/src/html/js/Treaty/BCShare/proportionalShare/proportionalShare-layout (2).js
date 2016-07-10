/**
 * Created by gang.wang on 10/20/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');

    // NTable.registerInlineEditor('shareCate', {
    //   comp: {
    //     type: {type: $pt.ComponentConstants.Select, label: false},
    //     enabled:false,
    //     data:$page.codes.ShareCate
    //   }
    // });

    $page.layout = {
        _sections: {
            Share: {
                label: "Share",
                style:'primary-darken',
                collapsible : true,
                expanded : true,
                layout: {
                    ShareDefine:{
                        label:"Share Defined in",
                        comp:{
                          type:$pt.ComponentConstants.Select,
                          data:$page.codes.Boolean
                        },
                        pos:{
                          row:1
                          // width:10
                        }
                    },
                    Ceded:{
                        label:"Ceded",
                        comp:{
                          type:$pt.ComponentConstants.Text,
                          convertor: NText.PERCENTAGE,
                          rightAddon: {
                            text: '%'
                          }
                        },
                        pos:{
                          row:2
                          // width:10
                        }
                    },
                    CedentRetention:{
                        label:"Cedent's Retention",
                        comp:{
                          type:$pt.ComponentConstants.Text,
                          convertor: NText.PERCENTAGE,
                          rightAddon: {
                            text: '%'
                          }
                        },
                        pos:{
                          row:2
                          // width:10
                        }
                    },
                    CoShareCatePanel1:{
                      label:"From 100%",
                      comp:{
                        type:$pt.ComponentConstants.Panel,
                        editLayout:{
                          WrittenShare1:{
                            label:"Written Shares",
                            comp:{
                              type:$pt.ComponentConstants.Text,
                              convertor: NText.PERCENTAGE,
                              rightAddon: {
                                text: '%'
                              }
                            }
                          },
                          SignedShares1:{
                            label:"Signed Shares",
                            comp:{
                              type:$pt.ComponentConstants.Text,
                              convertor: NText.PERCENTAGE,
                              rightAddon: {
                                text: '%'
                              }
                            }
                          },
                          Comments1:{
                            label:"Comments",
                            comp:{
                              type:$pt.ComponentConstants.Text
                            }
                          }
                        }
                      },
                      pos:{
                        width:12
                      }
                    },
                    CoShareCatePanel2:{
                      label:"From Ceded",
                      comp:{
                        type:$pt.ComponentConstants.Panel,
                        editLayout:{
                          WrittenShare2:{
                            label:"Written Shares",
                            comp:{
                              type:$pt.ComponentConstants.Text,
                              convertor: NText.PERCENTAGE,
                              rightAddon: {
                                text: '%'
                              }
                            }
                          },
                          SignedShares2:{
                            label:"Signed Shares",
                            comp:{
                              type:$pt.ComponentConstants.Text,
                              convertor: NText.PERCENTAGE,
                              rightAddon: {
                                text: '%'
                              }
                            }
                          },
                          Comments2:{
                            label:"Comments",
                            comp:{
                              type:$pt.ComponentConstants.Text
                            }
                          }
                        }
                      },
                      pos:{
                        width:12
                      }
                    },
                    CoShareCatePanel3:{
                      label:"Units",
                      comp:{
                        type:$pt.ComponentConstants.Panel,
                        editLayout:{
                          WrittenShare3:{
                            label:"Written Shares",
                            comp:{
                              type:$pt.ComponentConstants.Text
                            }
                          },
                          SignedShares3:{
                            label:"Signed Shares",
                            comp:{
                              type:$pt.ComponentConstants.Text
                            }
                          },
                          Comments3:{
                            label:"Comments",
                            comp:{
                              type:$pt.ComponentConstants.Text
                            }
                          }
                        }
                      },
                      pos:{
                        width:12
                      }
                    },
                    // shareData:{
                    //   // label:"Share Data",
                    //   comp:{
                    //     type:$pt.ComponentConstants.Table,
                    //     addable:false,
                    //     removable: false,
                    //     addable : false,
                    //     searchable:false,
                    //     sortable:false,
                    //     columns : [
                    //         {title : "",render:function(row){return $page.codes.ShareCate.getText(row.shareCate);},width:"25%"},
                    //         {title : "Written Shares", data : "writtenShare",inline:"percentage",width:"25%"},
                    //         {title : "SIgned Shares", data : "signedShare",inline:"percentage",width:"25%"},
                    //         {title : "Comments", data : "comment",inline:"text",width:"25%"}
                    //     ],
                    //   },
                    //   css: {
                    //       comp: "inline-editor"
                    //   },
                    //   pos:{
                    //     width:12
                    //   }
                    // },
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
