/**
 * Created by Gordon.Cao on 10/20/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, {
        initializePageType: function () {

        },
        initializeErrorModel: function () {
            return true;
        },
        initializeData: function () {

            this.model = $pt.createModel($page.model.createModel());
            //this.codes = $pt.createModel($page.codes);

            var urlData = $pt.getUrlData();

            this.model.set("BusinessId",urlData.businessId);
            this.model.set("BusinessType",urlData.businessType);
            //var extension = this.getFileExtension();
            //this.model.set("Extension",extension);
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout.createFormLayout());
            var form = <NForm model={this.model} layout={layout}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        },

        getFileUploadURL : function(){
            return $ri.getRestfulURL("action.public.file")+"/upload/"+this.model.get("BusinessId")+"/"+this.model.get("BusinessType")+"?"+ $.sessionStorage.get($page.constants.CAS_KEY);
        },
        getFileExtension:function(){
            var ex = [];
            var url = $ri.getRestfulURL("action.public.document") + "/getFileExtionsion"+"/"+this.model.get("BusinessType");
            $pt.doGet(url,null,  {
                quiet: null,
                async: false,
                done: function(data, textStatus, jqXHR) {
                     ex = data.extension;
                },
                fail: function(jqXHR, textStatus, errorThrown) {

                }
            });
            console.log(ex);
            return ex;
        },
        saveDocInfo:function(){
            var _this = this;
            if(!this.model.get("DocumentId")){
                NConfirm.getConfirmModal().show({
                    title: 'Message',
                    disableClose: true,
                    messages: "Please upload the file first."
                });
                return false;
            }

            var afterUpload = function (data, textStatus, jqXHR) {
                NConfirm.getConfirmModal().show({
                    title: 'Message',
                    disableClose: true,
                    messages: "Operation done.",
                    onConfirm: function(){
                      if (data.ProcessType == "1") {
                        //after uploading attachments, go back to document list
                        var url = "documentSearch.html?businessType=" + $page.controller.model.get("BusinessType")+"&businessId="+$page.controller.model.get("BusinessId");
                        //window.location.href = url;
                        window.close();
                      } else if (data.ProcessType == "2") {
                        //after parsing document, show result.
                        window.location.href = "documentRecord.html?documentId="+data.DocumentId;
                      } else {
                        alert("Don't know where to go...");
                      }
                    }
                })
            }.bind(this);

            // var savedFail = function () {
            //     isSaved = false;
            //     alert("File Info save fail,please contact administrator!");
            // }.bind(this);
            $page.service.fileUpload(this.model, false, false, afterUpload, null);
        },
        upload:function(){
            $page.service.upload(this.model, false, false, null, null);
        }


    }));

    $page.controller = new Controller();

}(typeof window !== "undefined" ? window : this));
