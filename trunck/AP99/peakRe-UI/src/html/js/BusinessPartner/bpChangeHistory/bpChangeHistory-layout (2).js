(function(context){
    var $page = $pt.getService(context,'$page');

    var Layout = jsface.Class({
        createMainInfo: function () {
            return {
                label: "Business Partner History",
                style: "primary-darken",
                collapsible: false,
                layout:{
                    resultTable:{
                        comp:{
                            type:$pt.ComponentConstants.Table,
                            searchable:false,
                            sortable:false,
                            header:false,
                            columns:[
                                {
                                    title:"BP Role",
                                    data:"RoleSelectIds",
                                    render : function(row){
                                        console.log($page.partnerRole);
                                        console.log($page.partnerRole.getText("1"));
                                        var value = row.RoleSelectIds;
                                        if(value == null || value == ""){
                                            return;
                                        }
                                        var arrValue = '';
                                        for(var i =0; i<value.length;i++){
                                            arrValue = arrValue +" "+ $page.partnerRole.getText(value[i]);
                                        }
                                        return arrValue;
                                    },
                                    width:"10%"
                                }, {
                                    title:"Name",
                                    data:"Name",
                                    width:"10%"
                                }, {
                                    title:"Operator",
                                    data:"Operator",
                                    codes: $page.model.userCodes,
                                    width:"10%"
                                }, {
                                    title:"Update Date",
                                    data:"UpdateTime",
                                    width:"15%",
                                    render: function (row) {
                                        return $helper.formatDateForTableView(row.UpdateTime, "DD/MM/YYYY hh:mm:ss");
                                    }
                                }, {
                                    title:"Change Content",
                                    data:"ChangeContent",
                                    width:"40%"
                                }
                            ]
                        },
                        pos:{
                            row:1,
                            width:12
                        }
                    },
                    buttonPanel:{
                        comp:{
                            type:$pt.ComponentConstants.ButtonFooter,
                            buttonLayout:{
                                right:[
                                    {
                                        text:"Exit",
                                        style:"warning",
                                        click:function(){
                                            window.close();
                                        }
                                    }
                                ]
                            }
                        },
                        pos:{
                            row:2,
                            width:12
                        }
                    }
                }
            };
        },

        createFormLayout: function () {
            return {
                _sections: {
                    partnerInfoSection: this.createMainInfo()
                }
            }
        }
    });

    $page.layout = new Layout();

}(typeof window !== "undefined" ? window : this));