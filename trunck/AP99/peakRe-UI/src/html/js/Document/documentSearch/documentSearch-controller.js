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

            var urlData = $pt.getUrlData();
            var businessId = urlData.businessId;
            var businessType = urlData.businessType;
            var documentType = urlData.documentType;
            var readOnly = urlData.readOnly;
            $page.readOnly = readOnly;
            var url = $ri.getRestfulURL("action.public.document")+"/queryDocument/";
            var _this = this;

            _this.model.set("condition_BusinessId", businessId);
            _this.model.set("condition_BusinessType", businessType);
            _this.model.set("BusinessId",businessId);
            _this.model.set("BusinessType",businessType);
            $page.businessId = businessId;
            $page.businessType = businessType;
            console.log(this.model.getCurrentModel().condition);
            this.codes = $pt.createModel($page.codes);

            this.doSearch();

            var afterLoadAllUser = function (data, textStatus, jqXHR) {
                console.log(data);
                $helper.lowerKeysOfJSON(data);
                $page.model.userCodes = $page.codes.createMutableCodeTable();
                $page.model.userCodes.reset(data);
            }.bind(this);
            $page.service.loadAllUserCodes(null, false, false, afterLoadAllUser);
            var afterLoadCurrentUser = function (data, textStatus, jqXHR) {
                //console.log(data);
                $page.currentUser = data.UserId;

            }.bind(this);
                $page.service.loadCurrentUserCodes(null, false, false, afterLoadCurrentUser);
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout.createFormLayout());
            var form = <NForm model={this.model} layout={layout}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        },
        doSearch: function () {
            var criteria = $.extend({}, this.model.getCurrentModel());
            delete criteria.searchResult;
            delete criteria.cachedCriteria.pageIndex;
            delete criteria.cachedCriteria.PageCount;
            console.log(criteria.cachedCriteria.url);
            var _this = this;
            var qureyReturn = function(data, textStatus, jqXHR){
                _this.updateSearchResult(data, true);
            }.bind(this);
            $page.service.query(criteria.cachedCriteria.url,_this.model.getCurrentModel().condition , false, true,qureyReturn);
        },

        updateSearchResult: function (data, updateUI) {
            console.log(data);
            this.model.mergeCurrentModel({
                documentTable: data.Rows ? data.Rows: [],
                cachedCriteria: {
                    pageIndex: data.PageIndex,
                    pageCount: data.PageCount,
                    countPerPage: data.CountPerPage
                }
            });
            this.model.getCurrentModel().cachedCriteria = $.extend(true,{},this.model.getCurrentModel().cachedCriteria,this.model.getCurrentModel().condition);
            if (updateUI) {
                this.form.forceUpdate();
            }
        },
        fileUpload:function(documentType,remarks){
            var _this = this;
            var isSaved = false;
            _this.model.set("DocumentType",documentType);
            _this.model.set("Remarks",remarks);
            var afterSave = function (data, textStatus, jqXHR) {
                NConfirm.getConfirmModal().show({
                    title: 'Message',
                    disableClose: true,
                    messages: ['Successful'],
                    onConfirm: function(){
                        window.location.reload();
                        window.opener.location.reload();
                    }
                });
                _this.model.mergeCurrentModel({
                    DocumentId: data.DocumentId
                });
                isSaved = true;
            }.bind(this);
            var savedFail = function () {
                isSaved = false;
                alert("File Info save fail,please contact administrator!");
            }.bind(this);
            $page.service.fileUpload(this.model.getCurrentModel(), false, false, afterSave,savedFail);
            return isSaved;
        },
        fileRemove:function(documentId){
            var _this = this;
            var after = function (data, textStatus, jqXHR) {
                NConfirm.getConfirmModal().show({
                    title: 'Message',
                    disableClose: true,
                    messages: ['Document Removed'],
                    onConfirm: function(){
                        window.location.reload();
                        window.opener.location.reload();
                    }
                });
            }.bind(this);
            var fail = function () {
                isSaved = false;
                alert("File remove fails,please contact administrator!");
            }.bind(this);
            $page.service.fileRemove(documentId, false, false, after,fail);
        },
        downloadFile :function(documentId){
        window.open($ri.getRestfulURL("action.public.file")+'/download/'+documentId+'?'+ $.sessionStorage.get($page.constants.CAS_KEY));
    }

    }));

    $page.controller = new Controller();
    $page.controller.initializePageType();

}(typeof window !== "undefined" ? window : this));
