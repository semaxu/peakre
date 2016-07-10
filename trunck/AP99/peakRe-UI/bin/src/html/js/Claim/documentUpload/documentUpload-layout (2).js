(function (context) {
    var $page = $pt.getService(context, '$page');

    $page.layout = {
        _sections: {
            claimSec: {
                label: "Documents Upload",
                style:'primary-darken',
                pos:{
                    width:12
                },
                layout: {
                    uploadFile: {
                        label: "Select Document",
                        comp: {
                            type: $pt.ComponentConstants.File,
                            showUpload:false,
                            showUploadedThumbs:false,
                            showPreview:false,
                            // showCaption:false,
                            showClose:false,
                            labelWidth:4
                        },
                        pos: {
                            width:6,
                            row: 1
                        }
                    },
                    documentName: {
                        label: "Document Name",
                        comp: {
                            type: {
                                labelDirection: "horizontal",
                                enabled:true
                            }
                        },
                        pos: {
                            row: 2,
                            // width: 4
                        }
                    },
                    documentType: {
                        label: "Document Type",
                        comp: {
                            type: $pt.ComponentConstants.Select,
                            minimumResultsForSearch: -1,
                            data: $page.codes.Boolean,
                            //allowClear : true,
                            placeholder: "please select..."
                        },
                        pos: {
                            row: 2,
                            // width: 4
                        }
                    },
                    receivedDate: {
                        label: "Receive Date",
                        comp: {
                            type: $pt.ComponentConstants.Date
                            //valueFormat:$pt.ComponentConstants.Default_Date_Format,
                            //labelDirection: "horizontal"
                        },
                        pos: {
                            row: 2,
                            // width: 4
                        }
                    },
                    // section: {
                    //     label: "Section",
                    //     comp: {
                    //         type: $pt.ComponentConstants.Select,
                    //         minimumResultsForSearch: -1,
                    //         data: $page.codes.Boolean,
                    //         //allowClear : true,
                    //         placeholder: "please select..."
                    //     },
                    //     pos: {
                    //         row: 3,
                    //         width: 4
                    //     }
                    // },
                    description:{
                        label:"Description",
                        comp:{
                            type:$pt.ComponentConstants.Panel,
                            editLayout:{
                                lossDescription:{
                                    comp:{
                                        type:{
                                            type:$pt.ComponentConstants.TextArea,
                                            label:false
                                        },
                                        lines:2
                                    },
                                    pos:{
                                        width:12
                                    }
                                }
                            }
                        },
                        pos:{
                            row:4,
                            width:12
                        }
                    },
                    uploadFileButton:{
                      // label:"Upload",
                      comp:{
                        type:$pt.ComponentConstants.ButtonFooter,
                        buttonLayout: {
                            right: [{
                                text: "Upload",
                                style: "primary",
                                click:function(){

                                }
                            }]
                        }
                      },
                      pos:{
                        row:5,
                        width:12
                      }
                    },
                    documentList: {
                        label: "Documents List",
                        comp: {
                            type: $pt.ComponentConstants.Table,
                            //indexable : true,
                            sortable:false,
                            //pageable:true,
                            //countPerPage : 5,
                            searchable:false,
                            editable: false,
                            removable: true,
                            addable: false,
                            scrollX: true,
                            //criteria : "paginationCriteria",
                            columns: [
                                {title: "Documents Name", data: "documentsName", width:"20%"},
                                {title: "Document Type", data: "documentType", width:"20%"},
                                {title: "Received Date", data: "receivedDate", width:"20%"},
                                {title: "Section", data: "section", width:"20%"},
                                {title: "Description", data: "description", width:"20%"}
                            ],
                            addClick:function(model, item, layout){
                                model.add("documentList", item.getCurrentModel());
                            }
                        },
                        pos: {
                            width: 12,
                            // row: 1
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
