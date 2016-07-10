(function (context) {
    var $page = $pt.getService(context, '$page');

    $page.layout = {
        _sections: {
            defaultSection: {
                label: "Underlying Contracts",
                style: 'primary-darken',
                collapsible: true,
                expanded: true,
                layout: {
                    CoveredSectionList: {
                        comp: {
                            type: $pt.ComponentConstants.Table,
                            searchable: false,
                            addable: false,
                            removable: false,
                            columns: [
                                {
                                    title: "Section",
                                    data: "Section",
                                    width: "10%"
                                }, {
                                    title: "Sub-Section",
                                    data: "SubSection",
                                    width: "10%"
                                }, {
                                    title: "Underlying Contract",
                                    data: "UnderlyingContract",
                                    width: "15%"
                                }, {
                                    title: "Underlying Section/Sub-Section",
                                    data: "UnderlyingSec",
                                    width: "25%"
                                }, {
                                    title: "Cedent",
                                    data: "Cedent",
                                    width: "10%"
                                }, {
                                    title: "Broker",
                                    data: "Broker",
                                    width: "10%"
                                }, {
                                    title: "UW Year",
                                    data: "Uwyear",
                                    width: "10%"
                                }, {
                                    title: "Main CoB",
                                    data: "MainLoB",
                                    width: "10%",
                                    codes: $page.codes.Class
                                }
                            ]
                        },
                        pos: {
                            width: 12
                        }
                    },
                    buttonPanel: {
                        comp: {
                            type: $pt.ComponentConstants.ButtonFooter,
                            buttonLayout: {
                                right: [
                                    {
                                        text: "Exit",
                                        style: "warning",
                                        click: function (model) {
                                            var url = "contractHome.html?ContCompId=" + this.getModel().get("ContCompId")
                                                + "&OperateType=" + this.getModel().get("OperateType");
                                            if(this.getModel().get("OperateId")){
                                                url += "&OperateId=" + this.getModel().get("OperateId");
                                            }
                                            window.location.href = url;
                                        }
                                    }
                                ]
                            }
                        },
                        pos: {
                            width: 12
                        }
                    }
                }
            }
        }
    };
}(typeof window !== "undefined" ? window : this));
