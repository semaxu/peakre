(function (context) {
    var $page = $pt.getService(context, '$page');



    var Layout = jsface.Class({
        createClaimInfo: function () {
            return {
                label: "Document Record",
                style: "primary-darken",
                layout: {

                    documentTable: {
                                  // label: "Document Record",
                                    comp: {
                                        type: $pt.ComponentConstants.Table,
                                        searchable: false,
                                        sortable: false,
                                        pageable: true,

                                        criteria: 'cachedCriteria',
                                        columns: [
                                            {
                                                title: "Document Id",
                                                data: "DocumentId"
                                            }, {
                                                title: "Record No",
                                                data: "RecordContent"
                                            }, {
                                                title: "Parse Result",
                                                data: "ParseResult"
                                            }
                                        ]

                                    },
                                    pos: {
                                        row: 1,
                                        width: 12
                                    }
                                },

                    buttonPanel: {
                        comp: {
                            type: $pt.ComponentConstants.ButtonFooter,
                            buttonLayout: {
                                right: [
                                    {
                                        text: "Close",
                                        style: "warning",
                                        click:function(){
                                            window.close();
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
            };
        },
        createFormLayout: function () {
            return {
                _sections: {
                    claimInfoSection: this.createClaimInfo()
                }
            }
        }
    });

    $page.layout = new Layout();


}(typeof window !== "undefined" ? window : this));
