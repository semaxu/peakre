(function (context) {
    var $page = $pt.getService(context, '$page');

    var me = {
        //initialize: function () {
        //},
        initializeData: function() {
            var urlData = $pt.getUrlData();
            var partnerId = urlData.partnerId;
            var pageType = urlData.pageType;
            $page.pageType = pageType;
            var _this = this;
            this.model = $pt.createModel($page.model.createModel(), $page.validator);
            this.codes = $pt.createModel($page.codes);
            if(partnerId != null){
                this.load({partnerId: partnerId} , false, true, function (data, textStatus, jqXHR) {
                    _this.model.mergeCurrentModel(data);
                    if (true) {
                        _this.form.forceUpdate();
                    }
                }, null);
            }else{
                _this.model.set("Status","1");
                _this.getBusinessPartnerCode(_this);
            }
            var nowDate = $pt.newSystemDate;
            if(_this.model.get("CreateDate")==null){
                _this.model.set("CreateDate",nowDate);
            }
            nowDate = $pt.newSystemDate.substring(0, 10);
/*            var afterLoadAllUser = function (data, textStatus, jqXHR) {
                console.log(data);
                $helper.lowerKeysOfJSON(data);
                $page.model.userCodes = $page.codes.createMutableCodeTable();
                $page.model.userCodes.reset(data);
            }.bind(this);*/
          //  $page.service.loadAllUserCodes(null, false, false, afterLoadAllUser);
            var afterLoadCurrentUser = function (data, textStatus, jqXHR) {
                //console.log(data);
                _this.model.set("Creator",data.UserId);
            }.bind(this);
            if(partnerId == null){
            $page.service.loadCurrentUserCodes(null, false, false, afterLoadCurrentUser);
            }
        },

        getBusinessPartnerCode:function(_this){
            var url = $ri.getRestfulURL("action.public.partner") + "/getPartnerCode";
            $pt.doGet(url, this.model.getCurrentModel(),  {
                done: function(data, textStatus, jqXHR) {
                    var bizId5 = '1'+data.partnerCode;

                    _this.model.set("BusinessPartnerId",bizId5);

                },
                fail: function(jqXHR, textStatus, errorThrown) {

                }
            });
        },
        load: function (criteria, quiet, async, done, fail) {
            console.log(criteria);
            $page.service.load(criteria, quiet, async, done, fail);
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout.createFormLayout());
            var form = <NForm model={this.model} layout={layout}/>;
            if ($page.pageType == 1) {
                form = <NForm model={this.model} layout={layout} view={true}/>;
            }
            this.form = ReactDOM.render(form, document.getElementById('main'));
        },
        submitData: function() {
            this.model.validate();
            var _this = this;
            var contactList = this.model.getCurrentModel().ContactTable;
            if (undefined != contactList && null != contactList) {
                var len = contactList.length;
                var emailAdrress;
                for (var index = 0; index < len; index++) {
                    emailAdrress = contactList[index].EmailAddress;
                    if (undefined != emailAdrress || null != emailAdrress || "" != emailAdrress) {
                        if(!_this.checkMail(emailAdrress)){
                            NConfirm.getConfirmModal().show({
                                title: "",
                                disableClose: true,
                                messages: [emailAdrress+' is not a valid email address']
                            });
                            return false;
                        }
                    }
                }
            }
            if (this.model.hasError() == true) {
                NConfirm.getConfirmModal().show({
                    title: 'System Message',
                    disableClose: true,
                    messages: ['Please fill in all mandatory information.']
                });
                return false;
            }

            _this.checkInput();
            if( $page.flag == true){
                NConfirm.getConfirmModal().show({
                    title: 'System Message',
                    disableClose: true,
                    messages: ['Business Partner Id exits!.']
                });
                return false;
            }
            var url = $ri.getRestfulURL("action.public.partner") + "/submit";
            $pt.doPost(url, this.model.getCurrentModel(), {
                done: function(data, textStatus, jqXHR) {

                    window.location.href = "businessPartnerSearch.html";
                },
                fail: function(jqXHR, textStatus, errorThrown) {
                    NConfirm.getConfirmModal().show({
                        title: 'Message',
                        disableClose: true,
                        messages: ['Failed']
                    });
                }
            });
        },
        saveData: function() {
            this.model.validate();
            var _this = this;
            var contactList = this.model.getCurrentModel().ContactTable;
            if (undefined != contactList && null != contactList) {
                var len = contactList.length;
                var emailAdrress;
                for (var index = 0; index < len; index++) {
                    emailAdrress = contactList[index].EmailAddress;
                    if (undefined != emailAdrress || null != emailAdrress || "" != emailAdrress) {
                        if(!_this.checkMail(emailAdrress)){
                            NConfirm.getConfirmModal().show({
                                title: "",
                                disableClose: true,
                                messages: [emailAdrress+' is not a valid email address']
                            });
                            return false;
                        }
                    }
                }
            }
            if (this.model.hasError() == true) {
                NConfirm.getConfirmModal().show({
                    title: 'System Message',
                    disableClose: true,
                    messages: ['Please fill in all mandatory information.']
                });
                return false;
            }
            _this.checkInput();
            if( $page.flag == true){
                NConfirm.getConfirmModal().show({
                    title: 'System Message',
                    disableClose: true,
                    messages: ['Business Partner Id exits!.']
                });
                return false;
            }
            var url = $ri.getRestfulURL("action.public.partner") + "/create";
            $pt.doPost(url, this.model.getCurrentModel(), {
                done: function(data, textStatus, jqXHR) {
                    NConfirm.getConfirmModal().show({
                        title: 'Message',
                        disableClose: true,
                        messages: ['Successful']
                    });
                    $page.controller.model.mergeCurrentModel(data);
                },
                fail: function(jqXHR, textStatus, errorThrown) {
                    NConfirm.getConfirmModal().show({
                        title: 'Message',
                        disableClose: true,
                        messages: ['Failed']
                    });
                }
            });
        },
        openDocument:function(){
            var isSaved = $page.controller.saveData();
            if(!isSaved){
                return;
            }
            window.open("../Document/documentUpload.html?businessId="+this.model.get('PartnerId')+"&businessType=1");
        },
        checkInput: function () {
            var businessPartnerId = this.model.get("BusinessPartnerId");
            var checkCode = function (data, textStatus, jqXHR) {
                var checkResult = data.checkResult;
                if(checkResult==true){
                    $page.flag = true;
                }
            }.bind(this);
            $page.service.checkPartnerCode(businessPartnerId, false, false, checkCode)
        },
        checkMail :function(str){
            var reg = /^([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+@([a-zA-Z0-9]+[_|\_|\.]?)*[a-zA-Z0-9]+\.[a-zA-Z]{2,3}$/;
            if(reg.test(str)){
                return true;
            }else{
                return false;
            }
        }
    };

    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, me));
    $page.controller = new Controller();
    // for layout purpose
    //$page.controller.initializeErrorModel();
}(typeof window !== "undefined" ? window : this));