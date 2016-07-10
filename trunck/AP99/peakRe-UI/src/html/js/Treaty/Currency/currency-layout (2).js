/**
 * Created by Gordon.Cao on 10/20/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');



    $page.layout = {
        _sections: {
            Currency: {
                label: "Currency",
                style:'primary-darken',
                layout: {
                    Currency:{
                               comp:{
                                    type:$pt.ComponentConstants.Panel,
                                    editLayout:{
                                      CurrencyList:{
                                        // label:"Reinstatement",
                                        comp:{
                                          type:$pt.ComponentConstants.Table,
                                          // editable: true,
                                          removable: true,
                                          sortable:false,
                                          addable : true,
                                          searchable:false,
                                          columns : [
                                              {title : "CurrencyType", data : "currencyType",inline:"select",codes: $page.codes.Currency,width:"20%"},
                                              {title : "MainCurrency", data : "mainCurrency",inline:"check",width:"15%", type: {type: $pt.ComponentConstants.Check, label: false},
                                                  labelAttached: true},
                                              {title : "Type", data : "ExchangeType",inline:"select",codes: $page.codes.Boolean,width:"15%"},
                                              {title : "Rate", data : "rateOrAmount",inline:"text",codes: $page.codes.Boolean,width:"25%"},
                                              {title : "Data", data : "fixedData",inline : "number",codes: $page.codes.Boolean,width:"13%"},
                                              {title : "Remarks", data : "remarks",inline:"text",codes: $page.codes.Boolean,width:"25%"}
                                          ],
                                          addClick:function(model, item, layout){
                                            // alert("addClick");
                                            // model.add("specificReinstatementData",
                                            //   {reinstatementNum:"1",rate:0.1,time:"N",amount:"Y"}
                                            // );
                                            // console.log(item);
                                            model.add("CurrencyList", item.getCurrentModel());
                                            // console.log(model.get("specificReinstatementData"));
                                            // this.forceUpdate();
                                          },
                                          //modelTemplate: {
                                          //  reinstatementNum: null,
                                          //  rate: 1,
                                          //  time: null,
                                          //  amount: null
                                          //}
                                          // editLayout:{
                                          //   reinstatementNum:{
                                          //     label:"Reinstatement",
                                          //     pos:{
                                          //       width:6
                                          //     }
                                          //   },
                                          //   rate:{
                                          //     label:"Rate",
                                          //     comp:{
                                          //       type:$pt.ComponentConstants.Text,
                                          //       convertor:NText.PERCENTAGE,
                                          //       rightAddon:{
                                          //          text:"%"
                                          //       },
                                          //     },
                                          //     pos:{
                                          //       width:6
                                          //     }
                                          //   },
                                          //   time:{
                                          //     label:"Time",
                                          //     comp:{
                                          //       type:$pt.ComponentConstants.Select,
                                          //       data:$page.codes.Boolean
                                          //     },
                                          //     pos:{
                                          //       width:6
                                          //     }
                                          //   },
                                          //   amount:{
                                          //     label:"Amount",
                                          //     comp:{
                                          //       type:$pt.ComponentConstants.Select,
                                          //       data:$page.codes.Boolean
                                          //     },
                                          //     pos:{
                                          //       width:6
                                          //     }
                                          //   }
                                          // }
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
