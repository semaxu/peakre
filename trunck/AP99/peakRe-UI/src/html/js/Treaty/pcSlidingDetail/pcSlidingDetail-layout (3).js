(function (context) {
    var $page = $pt.getService(context, '$page');

    $page.layout = {
        _sections: {
            ClaimSec: {
                label: "PC Detail",
                style:'primary-darken',
                layout: {
                    linear : {
                        label:"Sliding Scale",
                        pos:{
                            width:12
                        },
                        comp:{
                            type : $pt.ComponentConstants.Panel,
                            editLayout: {
                                MinimumRIPc:{
                                    label:"Min Profit Comm.",
                                    base : $helper.basePercentage(2),
                                    pos:{
                                        width:6,
                                        row:1
                                    }
                                },
                                MaximumRIPc: {
                                    label: "Max Profit Comm.",
                                    base : $helper.basePercentage(2),
                                    pos: {
                                        row: 1,
                                        width: 6
                                    }
                                },
                                MinimumLossPc:{
                                    label:"Minimum Loss Ratio",
                                    base : $helper.basePercentage(2),
                                    pos:{
                                        width:6,
                                        row:2
                                    }
                                },
                                MaximumLossPc: {
                                    label: "Maximum Loss Ratio",
                                    base : $helper.basePercentage(2),
                                    pos: {
                                        row: 2,
                                        width: 6
                                    }
                                }
                            }
                        }
                    },
                    nonlinear : {
                        label:"Attachment",
                        pos:{
                            width:12
                        },
                        comp:{
                            type : $pt.ComponentConstants.Panel,
                            editLayout: {
                                DeductionsAttach: {
                                    label: "",
                                    comp: {
                                        type: $pt.ComponentConstants.Table,
                                        sortable:true,
                                        searchable:false,
                                        removable: true,
                                        addable: true,
                                        columns: [
                                            {title: "LR % From", data: "LrFrom",  inline:$helper.registerInlinePercentage("LrFrom",2),sort: "number"},
                                            {title: "LR % To", data: "LrTo", inline:$helper.registerInlinePercentage("LrTo",2),sort: "number"},
                                            {title: "Commission %", data: "Commission", inline:$helper.registerInlinePercentage("Commission",2),sort: "number"}
                                        ],
                                        canRemove: function (model, item) {
                                            if (item.AttachmentId && item.AttachmentId != 0) {
                                                model.add("DeleteDeductionsAttach", item);
                                            }
                                            this.getModel().remove(this.getDataId(), item);
                                        },
                                        addClick: function (model, item, layout) {
                                            model.add("DeductionsAttach", item.getCurrentModel());
                                        }
                                    },
                                    css:{
                                        comp: "inline-editor",
                                        cell: "title-align"
                                    },
                                    pos: {
                                        width: 12,
                                        row: 1

                                    }
                                },
                                buttoms: {
                                    comp: {
                                        type: $pt.ComponentConstants.ButtonFooter,
                                        buttonLayout: {
                                            right: [{
                                                text: "Upload Table/Attach Table",
                                                style: "primary",
                                                visible: {
                                                    when: function (model) {
                                                        return model.get('OperateType') != '0' && model.get('OperateType') != '5' && !(model.get('OperateType') == '3' && model.get('EndoType') == '3');
                                                    }
                                                },
                                                click:function(model){
                                                    if ($page.controller.save(false)) {
                                                            var myWindow = $pt.dataImport(5,model.get("PcSlidingId"));
                                                            var loop = setInterval(function(){
                                                                if(myWindow.closed){
                                                                    clearInterval(loop);
                                                                    window.location.href = window.location.href;
                                                                }
                                                            },1000);
                                                    }
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
                    },
                    bottomButtons: {
                        comp: {
                            type: $pt.ComponentConstants.ButtonFooter,
                            buttonLayout: {
                                right: [{
                                    text: "Exit",
                                    style: "warning",
                                    click:function(){
                                        window.close();
                                    }
                                }, {
                                    text: "Save",
                                    style: "primary",
                                    visible: {
                                        when: function (model) {
                                            return model.get('OperateType') != '0' && model.get('OperateType') != '5' && !(model.get('OperateType') == '3' && model.get('EndoType') == '3');
                                        }
                                    },
                                    click:function(){
                                        $page.controller.save(true);
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
