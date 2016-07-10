(function (context) {
    var $page = $pt.getService(context, '$page');

    var me = {
        //initialize: function () {
        //},
        initializeData: function() {
            this.model = $pt.createModel($page.model);
            this.codes = $pt.createModel($page.codes);
            var urlData = $pt.getUrlData();
            var partnerId = urlData.partnerId;
            var amlCheckId = urlData.amlCheckId;

            var pageType = urlData.pageType;
            $page.pageType = pageType;
            this.model.set("PartnerId", partnerId);
            this.model.set("AmlCheckId",amlCheckId);
            var _this = this;
            if(amlCheckId>0){
                this.loadComplianceInfo({
                    amlCheckId: amlCheckId
                }, false, false, function (data, textStatus, jqXHR) {

                    if (data.AmlCheckId != null && data.AmlCheckId != "") {
                        //_this.model = $pt.createModel(data);
                        _this.model.mergeCurrentModel(data);
                    }
                }, null);
            }else{
                _this.model.set("SanctionedCountry","0");
                _this.model.set("InsuranceBody","1");
                _this.getPartnerInfo(_this,partnerId);
                var nowDate = $pt.newSystemDate;
                _this.model.set("CheckDate", nowDate);
            }

            var afterLoadCurrentUser = function (data, textStatus, jqXHR) {
                //console.log(data);
                _this.model.set("CheckBy",data.UserId);
            }.bind(this);
            if(amlCheckId == null) {
                $page.service.loadCurrentUserCodes(null, false, false, afterLoadCurrentUser);
            }
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout.createFormLayout());
            var form = <NForm model={this.model} layout={layout}/>;
            if ($page.pageType == 1) {
                form = <NForm model={this.model} layout={layout} view={true}/>;
            }
            this.form = ReactDOM.render(form, document.getElementById('main'));
        },
        loadComplianceInfo: function (criteria, quiet, async, done, fail) {
            $page.service.load(criteria, quiet, async, done, fail);
        },
        save: function () {
            var _this = this;
            $page.service.save(this.model.getCurrentModel(), false, false, function (data, textStatus, jqXHR) {
                NConfirm.getConfirmModal().show({
                    title: 'Message',
                    disableClose: true,
                    messages: ['Successful.']
                });
                $page.controller.model.mergeCurrentModel(data);
                _this.form.forceUpdate();
            }, function () {
                NConfirm.getConfirmModal().show({
                    title: 'ERROR',
                    disableClose: true,
                    messages: ['Compliance Info save fail!']
                });
            });
        },
        save2:function(){
            var _this = this;
            var isSaved = false;
            var afterSave = function (data, textStatus, jqXHR) {
                _this.model.mergeCurrentModel({
                    PartnerId: data.PartnerId,
                    AmlCheckId:data.AmlCheckId
                });
                isSaved = true;
            }.bind(this);
            var savedFail = function () {
                isSaved = false;
                alert("AML Info save fail,please contact administrator!");
            }.bind(this);
            $page.service.save(this.model.getCurrentModel(), false, false, afterSave,savedFail);
            return isSaved;
        },
        openDocument:function(){
            var isSaved = $page.controller.save2();
            if(!isSaved){
                return;
            }
            window.open("../Document/documentUpload.html?businessId="+this.model.get('AmlCheckId')+"&businessType=1");
        },
        print:function(){
            var _this = this;
            var afterSave = function (data, textStatus, jqXHR) {
                var  url  = data.fileUrl;
                window.open(url);
            }.bind(this);
            var savedFail = function () {
            }.bind(this);
            $page.service.print(this.model.getCurrentModel(), false, false, afterSave,savedFail);
        },
        getPartnerInfo:function(_this,partnerId){
            var url = $ri.getRestfulURL("action.public.partner") + "/getPartnerInfo/"+partnerId;
            $pt.doGet(url, null,  {
                done: function(data, textStatus, jqXHR) {
                    var country = data.country;
                    var tradingName =data.tradingName;
                    _this.model.set("CountryIncorporation",country);
                    _this.model.set("CompanyName",tradingName);
                },
                fail: function(jqXHR, textStatus, errorThrown) {
                }
            });
        }
    };

    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, me));
    $page.controller = new Controller();
    // for layout purpose
    //$page.controller.initializeErrorModel();
}(typeof window !== "undefined" ? window : this));