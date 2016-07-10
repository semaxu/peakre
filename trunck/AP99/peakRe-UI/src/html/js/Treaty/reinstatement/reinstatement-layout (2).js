/**
 * Created by gang.wang on 10/20/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');

    $page.layout = {
        _sections: {
            reinstatement: {
                label: "Reinstatement",
                style:'primary-darken',
                collapsible : true,
                expanded : true,
                layout: {
                    typeOfReinstatementPanel:{
                        // label:"Type of Reinstatement",
                        comp:{
                            type:$pt.ComponentConstants.Panel,
                            editLayout:{
                                typeOfReinstatement:{
                                    label:"Type of Reinstatement",
                                    comp:{
                                      type:$pt.ComponentConstants.Radio,
                                      data:$page.codes.TypeOfReinstatement,
                                      labelWidth:3
                                    },
                                    pos:{
                                      width:10
                                    }
                                },
                                subPanel:{
                                  // label:"subPanel",
                                  comp:{
                                    type:$pt.ComponentConstants.Panel,
                                    visible : {when: function(model) {return model.get('typeOfReinstatement') == '2' || model.get('typeOfReinstatement') == '3';}, depends: 'typeOfReinstatement'},
                                    editLayout:{
                                      numberOfReinstatement:{
                                        label:"Select Number of Reinstatement",
                                        comp:{
                                          // visible : {when: function(model) {return model.get('typeOfReinstatement') == '2';}, depends: 'typeOfReinstatement'},
                                          labelWidth:8,
                                        },
                                        pos:{
                                          width:4,
                                          row:1
                                        }
                                      },
                                      specificReinstatementData:{
                                        // label:"Reinstatement",
                                        comp:{
                                          type:$pt.ComponentConstants.Table,
                                          // editable: true,
                                          removable: true,
                                          addable : true,
                                          searchable:false,
                                          sortable:false,
                                          columns : [
                                              {title : "Reinstatement", data : "reinstatementNum",inline:"text",width:"25%"},
                                              {title : "Rate", data : "rate",inline:"percentage",width:"25%"},
                                              {title : "Time", data : "time",inline:"select",codes: $page.codes.Boolean,width:"25%"},
                                              {title : "Amount", data : "amount",inline:"select",codes: $page.codes.Boolean,width:"25%"}
                                          ],
                                          addClick:function(model, item, layout){
                                            // alert("addClick");
                                            // model.add("specificReinstatementData",
                                            //   {reinstatementNum:"1",rate:0.1,time:"N",amount:"Y"}
                                            // );
                                            // console.log(item);
                                            model.add("specificReinstatementData", item.getCurrentModel());
                                            // console.log(model.get("specificReinstatementData"));
                                            // this.forceUpdate();
                                          },
                                          modelTemplate: {
                                            reinstatementNum: null,
                                            rate: 1,
                                            time: null,
                                            amount: null
                                          }
                                        },
                                        css: {
                                            comp: "inline-editor"
                                        },
                                        pos:{
                                          width:12,
                                          row:2
                                        }
                                      }
                                    }
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
                    reinstatementCurrency:{
                      label:"Reinstatement Currency",
                      comp:{
                        type:$pt.ComponentConstants.Select,
                        data:$page.codes.Currency
                      }
                    },
                    exchangeCalc:{
                      label:"Exchange Calculation",
                      comp:{
                        type:$pt.ComponentConstants.Select,
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
