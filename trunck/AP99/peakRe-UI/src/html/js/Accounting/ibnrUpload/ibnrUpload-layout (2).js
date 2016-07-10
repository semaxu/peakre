(function (context) {
    var $page = $pt.getService(context, '$page');
    $page.layout = {
        _sections: {
            defaultSection: {
                label: "IBNR Upload",
                style: "primary-darken",
                layout: {
                    uploadFile: {
                        label: "Please Select IBNR Document",
                        comp: {
                            type: $pt.ComponentConstants.File,
                            showUpload: false,
                            showUploadedThumbs: false,
                            showPreview: false,
                            // showCaption:false,
                            showClose: false,
                            labelWidth: 4
                        },
                        pos: {
                            row: 1,
                            width: 6
                        }
                    },
                    buttonPanel: {
                        comp: {
                            type:$pt.ComponentConstants.ButtonFooter,
                            buttonLayout:{
                                left:[
                                    {
                                        text:"Upload",
                                        style:"primary"
                                    }
                                ]
                            }
                        },
                        pos: {
                            row: 1,
                            width: 6
                        }
                    }
                }
            }
        }
    };
}(typeof window !== "undefined" ? window : this));