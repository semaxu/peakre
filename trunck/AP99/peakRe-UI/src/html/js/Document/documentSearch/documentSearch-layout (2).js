(function (context) {
    var $page = $pt.getService(context, '$page');


    var Layout = jsface.Class({
        createClaimInfo: function () {
            return {
                label: "Document List",
                style: 'primary-darken',
                collapsible: false,
                layout: {

                    documentTable: {
                                    comp: {
                                        type: $pt.ComponentConstants.Table,
                                        searchable: false,
                                        sortable: false,
                                        pageable: true,

                                        criteria: 'cachedCriteria',
                                        columns: [
                                            {
                                                title: "Document Type",
                                                data: "DocumentType",
                                                render: function(row) {
                                                    if (!row.DocumentType)
                                                        return row.DocumentTypeSelf;
                                                    else
                                                       return $page.codes.DocumentType
                                                           .getText(row.DocumentType);
                                                }
                                            }, {
                                                title: "File Name",
                                                data: "FileName"
                                            }, {
                                                title: "Upload Time",
                                                data: "UploadTime",
                                                render: function (row) {
                                                    return $helper.formatDateForTableView(row.UploadTime, "DD/MM/YYYY");
                                                }
                                            }, {
                                                title: "Upload By",
                                                data: "UploadPerson",
                                                codes: $page.model.userCodes
                                            } , {
                                                title: "Remarks",
                                                data: "Remarks"
                                            }
                                        ],
                                        rowOperations:[
                                            {
                                                tooltip: "Result",
                                                visible: {
                                                    when: function(rowModel) {
                                                        return rowModel.get("ProcessType") == "2"; //for PARSE type
                                                    }
                                                },
                                                click: function (rowModel) {
                                                    window.open("documentRecord.html?documentId="+rowModel.DocumentId);
                                                }
                                            },
                                            {
                                                tooltip: "Download",
                                                click: function (rowModel) {
                                                    console.log(rowModel.DocumentId);
                                                    $page.controller.downloadFile(rowModel.DocumentId);
                                                }
                                            },
                                            {
                                                tooltip: "Remove",
                                                visible: {
                                                    when: function(rowModel) {
                                                        return rowModel.get("UploadPerson") ==  $page.currentUser
                                                    }
                                                },
                                                click: function (rowModel) {
                                                    console.log(rowModel.DocumentId);
                                                    $page.controller.fileRemove(rowModel.DocumentId);
                                                }
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
                                            //window.location.reload();
                                            //window.opener.location.reload();
                                            }
                                    }, {
                                        text: "Upload",
                                        style: "primary",
                                        enabled: {
                                            when: function () {
                                                return $page.readOnly != 1;
                                            }
                                        },
                                        click: function (model) {
                                            console.log( model);
                                            var url = "documentUpload.html?businessType=" +$page.businessType+"&businessId="+$page.businessId;
                                            //window.location.href = url;
                                            var myWindow = window.open(url);
                                            var loop = setInterval(function() {      
                                                 if(myWindow.closed) {     
                                                    clearInterval(loop);     
                                                    //alert('closed');    
                                                    window.location.href = window.location.href; 
                                                 }     
                                                }, 1000); 

                                            //TODO refresh after uploading...
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
