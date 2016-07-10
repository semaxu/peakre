/*
 * Create By elvira.du 10/20/2015
 */
(function (context) {
    var $page = $pt.getService(context, "$page");
    var codes = $pt.getService(context, "codes");
    var limitType = $pt.createCodeTable([{id :'1',text : "Regular"},{id :'2', text : "Stop Loss"}]);
    var eventType = $pt.createCodeTable([{id:'1', text : "Winter Storm"}]);
    var amountTypeCodes = $pt.createCodeTable([{id:"1",text : "Amount"}]);
    $page.layout = {
        _sections : {
            limit : {
                label : "Limit",
                style: 'primary-darken',
                pos : {
                    width : 12
                },
                collapsible : true,
                expanded : true,
                layout : {
                    limitType : {
                        label : "Limit Type",
                        comp : {
                            type : $pt.ComponentConstants.Select,
                            data: limitType
                        }
                    },
                    unlimited : {
                        label : "Unlimited",
                        comp : {
                            type : {type : $pt.ComponentConstants.Check, label : false},
                            labelAttached : true,
                            visible : {when: function(model) {return model.get('limitType') == '1';}, depends: 'limitType'}
                        }
                    },
                    amountType : {
                        comp : {
                            type : {type : $pt.ComponentConstants.Select, label : false},
                            data : amountTypeCodes,
                            visible : {when: function(model) {return model.get('limitType') == '2';}, depends: 'limitType'}
                        },
                        pos : {width : 2}
                    },
                    regular1 : {
                        comp : {
                            type : $pt.ComponentConstants.Table,
                            addable : true,
                            removable : true,
                            sortable : false,
                            visible : {when: function(model) {return model.get('limitType') == '1';}, depends: 'limitType'},
                            searchable : false,
                            addClick:function(model, item, layout){
                                model.add("regular1", item.getCurrentModel());
                            },
                            columns : [
                                {
                                    title: "Currency",
                                    data: "currency",
                                    inline: "select",
                                    codes :  codes.Currency,
                                    width : "12%"
                                },
                                {
                                    title : "Deductible",
                                    data : "deductible",
                                    inline : "number",
                                    width : "12%"
                                },
                                {
                                    title : "Layer 100%",
                                    data : "layer",
                                    inline : "number",
                                    width : "12%"
                                },
                                {
                                    title : "AAL 100%",
                                    data : "aal",
                                    inline : "number",
                                    width : "12%"
                                },
                                {
                                    title : "AAD",
                                    data : "aad",
                                    inline : "number",
                                    width : "12%"
                                },
                                {
                                    title : "Layer in Share",
                                    data : "layerInShare",
                                    inline : "number",
                                    width : "13%"
                                },
                                {
                                    title : "AAL in Share",
                                    data : "aalInShare",
                                    inline : "number",
                                    width : "13%"
                                },
                                {
                                    title : "Remark",
                                    data : "remark",
                                    inline : "text",
                                    width : "12%"
                                }
                            ]
                        },
                        pos : {
                            width : 12
                        }
                    },
                    regular2 : {
                        comp : {
                            type : $pt.ComponentConstants.Table,
                            addable : true,
                            removable : true,
                            searchable : false,
                            sortable : false,
                            visible :  {when: function(model) {return model.get('limitType') == '1';}, depends: 'limitType'},

                    addClick:function(model, item, layout){
                                model.add("regular2", item.getCurrentModel());
                            },
                            columns : [
                                {
                                    title: "Currency",
                                    data: "currency",
                                    inline: "select",
                                    codes :  codes.Currency,
                                    width : "19%"
                                },
                                {
                                    title : "Event",
                                    data : "event",
                                    inline: "select",
                                    codes :  eventType,
                                    width : "19%"
                                },
                                {
                                    title : "Limit in 100%",
                                    data : "limit",
                                    inline : "number",
                                    width : "19%"
                                },
                                {
                                    title : "Limit in Share",
                                    data : "limitInShare",
                                    inline : "number",
                                    width : "19%"
                                },
                                {
                                    title : "Remark",
                                    data : "remark",
                                    inline : "text",
                                    width : "19%"
                                }
                            ]
                        },
                        pos : {
                            width : 12
                        }
                    },
                    stopLoss : {
                        comp : {
                            type : $pt.ComponentConstants.Table,
                            addable : true,
                            removable : true,
                            searchable : false,
                            sortable : false,
                            addClick:function(model, item, layout){
                                model.add("stopLoss", item.getCurrentModel());
                            },
                            visible : {when: function(model) {return model.get('limitType') == '2';}, depends: 'limitType'},
                            columns : [
                                {
                                    title: "Currency",
                                    data: "currency",
                                    inline: "select",
                                    codes :  codes.Currency,
                                    width : "12%"
                                },
                                {
                                    title : "Layer",
                                    data : "layer",
                                    inline : "number",
                                    width : "12%"
                                },
                                {
                                    title : "Deductible",
                                    data : "deductible",
                                    inline : "number",
                                    width : "12%"
                                },
                                {
                                    title : "Event(EQ)",
                                    data : "eventEQ",
                                    inline : "number",
                                    width : "12%"
                                },
                                {
                                    title : "Event(WS)",
                                    data : "eventWS",
                                    inline : "number",
                                    width : "12%"
                                },
                                {
                                    title : "Event(FL)",
                                    data : "eventFL",
                                    inline : "number",
                                    width : "12%"
                                },
                                {
                                    title : "Event(Other)",
                                    data : "eventOther",
                                    inline : "number",
                                    width : "13%"
                                },
                                {
                                    title : "Terrorism",
                                    data : "terrorism",
                                    inline : "number",
                                    width : "13%"
                                }
                            ]
                        },
                        pos : {
                           width : 12
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