(function (context) {
    var $page = $pt.getService(context, '$page');

    var Layout = jsface.Class({
        createClaimInfo: function () {
            return {
                label: "Document Upload",
                style: 'primary-darken',
                collapsible: false,
                layout: {
                    DocumentType:{
                        label: "Document Type",
                        comp: {
                            type: $pt.ComponentConstants.Select,
                            //data: $page.model.documentTypeSelection,
                            data:$page.codes.DocumentType
                            //parentFilter: {name: "parentId"},
                            //parentPropId: "BusinessType"
                        },
                        pos: {
                            width: 6,
                            row: 1
                        }
                    },
                    DocumentTypeSelf:{
                        comp: {
                            type: {type :$pt.ComponentConstants.Text,label:false},
                            visible: {
                                depends: 'DocumentType',
                                when: function (model) {
                                    //alert(model.get("DocumentType"));
                                    return !model.get("DocumentType");
                                }
                            }
                        },
                        pos: {
                            width: 6,
                            row: 1
                        }
                    },
                    uploadFile: {
                        label: "Upload File",
                        comp: {
                            type: $pt.ComponentConstants.File,
                            //removeClass: null,
                            //removeIcon: null,
                            //removeLabel: null,
                            //removeTitle: null,
                            //showRemove: false,
                            //showUpload:false,
                            showPreview : false,
                            uploadUrl: $page.controller.getFileUploadURL(),
                            uploadAsync: true,
                            placeholder: "Others",
                            allowedFileExtensions:$page.controller.getFileExtension(),
                            msgValidationError: "please choose correct file type!"

                        },
                        evt: {
                            fileloaded:function(){

                            },
                            fileuploaded:function(event, data, previewId){
                                $page.controller.model.set("DocumentId",  data.response.documentId);
                            }
                        },
                        pos: {
                            row: 2,
                            width: 6
                        }
                    },
                    remarkPanel: {
                        label: "Remarks",
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            editLayout: {
                                Remarks: {
                                    comp: {
                                        type: $pt.ComponentConstants.TextArea,
                                        lines: 3,
                                        labelDirection: "vertical"
                                    },
                                    pos: {
                                        width: 12
                                    }
                                }
                            }
                        },
                        pos: {
                            row:2,
                            width: 12
                        }
                    },
                    buttonPanel: {
                        comp: {
                            type: $pt.ComponentConstants.ButtonFooter,
                            buttonLayout: {
                                right: [
                                    {
                                        text: "Cancel",
                                        style: "warning",
                                        click:function(model){
                                            console.log(model);
                                            NConfirm.getConfirmModal().show({
                                                title: 'Message',
                                                disableClose: false,
                                                messages: "Are you sure to discard the uploading?",
                                                onConfirm: function(){
                                                    window.close();
                                                }
                                            })

                                        }
                                    }, {
                                        text: "Submit",
                                        style: "primary",
                                        click: function (model) {
                                            console.log(model);
                                            $('input[type=file]').fileinput('upload');
                                           $page.controller.saveDocInfo();
                                        }
                                    }
                                ]
                            }
                        },
                        pos: {
                            row: 6,
                            width: 12
                        }
                    }
                }
            }
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
