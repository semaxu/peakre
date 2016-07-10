(function (context) {
    var $page = $pt.getService(context, '$page');

    var me = {
        initializeData: function () {
            this.codes = $pt.createModel($page.codes);
            this.model = $pt.createModel($page.model.createModel());

            var _this = this;
            this.model.addPostChangeListener("condition_DateOfLossTo", function (evt) {
                var DateOfLossTo = evt.model.get("condition_DateOfLossTo");
                var DateOfLossFrom = evt.model.get("condition_DateOfLossFrom");
                var nowDate = moment(new Date()).format("YYYY-MM-DD HH:mm");
                if(null !=  DateOfLossTo && "" != DateOfLossTo && undefined != DateOfLossTo){
                    DateOfLossTo = moment(DateOfLossTo).format("YYYY-MM-DD HH:mm");
                    if(null !=  DateOfLossFrom && "" != DateOfLossFrom && undefined != DateOfLossFrom){
                        DateOfLossFrom = moment(DateOfLossFrom).format("YYYY-MM-DD HH:mm");
                        if (DateOfLossTo < DateOfLossFrom) {
                            NConfirm.getConfirmModal().show({
                                title: 'System Message',
                                disableClose: true,
                                messages: ['Loss End Date must be later or equal to Loss Start Date.']
                            });
                            _this.model.set("condition_DateOfLossTo", "");
                        }
                    }
                    if(DateOfLossTo > nowDate){
                        NConfirm.getConfirmModal().show({
                            title: 'System Message',
                            disableClose: true,
                            messages: ['Loss End Date must be earlier or equal to Today.']
                        });
                    }
                }
            });
            this.model.addPostChangeListener("condition_DateOfLossFrom", function (evt) {
                var DateOfLossTo = evt.model.get("condition_DateOfLossTo");
                var DateOfLossFrom = evt.model.get("condition_DateOfLossFrom");
                var nowDate = moment(new Date()).format("YYYY-MM-DD HH:mm");
                if(null !=  DateOfLossFrom && "" != DateOfLossFrom && undefined != DateOfLossFrom){
                    DateOfLossFrom = moment(DateOfLossFrom).format("YYYY-MM-DD HH:mm");
                    if(null !=  DateOfLossTo && "" != DateOfLossTo && undefined != DateOfLossTo){
                        DateOfLossTo = moment(DateOfLossTo).format("YYYY-MM-DD HH:mm");
                        if ( DateOfLossFrom > DateOfLossTo) {
                            NConfirm.getConfirmModal().show({
                                title: 'System Message',
                                disableClose: true,
                                messages: ['Loss Start Date must be earlier or equal to Loss End Date.']
                            });
                            _this.model.set("condition_DateOfLossFrom", "");
                        }
                    }
                    if(DateOfLossFrom > nowDate){
                        NConfirm.getConfirmModal().show({
                            title: 'System Message',
                            disableClose: true,
                            messages: ['Loss Start Date must be earlier or equal to Loss End Date.']
                        });
                        _this.model.set("condition_DateOfLossFrom", nowDate);
                    }
                }

            });

            //var afterLoadAllUser = function (data, textStatus, jqXHR) {
            //    //console.log(data);
            //    $helper.lowerKeysOfJSON(data);
            //    $page.model.userCodes = $page.codes.createMutableCodeTable();
            //    $page.model.userCodes.reset(data);
            //}.bind(this);
            //$page.service.loadAllUserCodes(null, false, false, afterLoadAllUser);

            var afterLoadCurrentUser = function (data, textStatus, jqXHR) {
                //console.log(data);
                _this.model.set("condition_TaskOwner", data.UserId);
            }.bind(this);
            $page.service.loadCurrentUserCodes(null, false, false, afterLoadCurrentUser);
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout.createFormLayout());
            var form = <NForm model={this.model} layout={layout}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        },
        resetEventQueryPram: function(){
            delete this.model.getCurrentModel().condition;
            this.form.forceUpdate();
            $page.controller.model.mergeCurrentModel({
                condition: {
                    CountPerPage: 10,
                    PageIndex: 1
                }
            });
        },

        doSearchEventQuery: function () {
            var criteria = $.extend({}, this.model.getCurrentModel());
            delete criteria.searchResult;
            delete criteria.cachedCriteria.pageIndex;
            delete criteria.cachedCriteria.PageCount;

            var _this = this;
            var qureyReturn = function(data, textStatus, jqXHR){
                _this.updateSearchResult(data, true);
            }.bind(this);
            $page.service.eventQuery(criteria.cachedCriteria.url,_this.model.getCurrentModel().condition , false, true,qureyReturn);
        },
        updateSearchResult: function (data, updateUI) {
            this.model.mergeCurrentModel({
                searchResult: data.Rows ? data.Rows: [],
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
        loadEventXml: function(rowModel,type) {
            var resource = {
                ResourceId: rowModel.EventId,
                ResourceType: 2,
                ResourceNo: rowModel.EventCode,
                OwnerId: rowModel.TaskOwner
            };
            var url = $pt.getURL('ui.claim.eventInfo');
            if (type == 5){

                var afterLoadXML = function (data, textStatus, jqXHR) {

                }.bind(this);
                $page.service.loadXMLService(rowModel.EventId, false, false, afterLoadXML);
            }
            else if (type == 2) {
                window.location.href = url + "?eventId=" + rowModel + "&pageType=" + type;
            } else {
                var done =function(){
                    window.location.href = url + "?eventId=" +rowModel.EventId+"&pageType="+type;
                }.bind(this);
                this.lockInfo(resource, done);
            }
        },
        loadEvent:function(eventId,type) {
            var url = $pt.getURL('ui.claim.eventInfo');
            window.location.href = url +"?eventId=" +eventId +"&pageType="+type;
        }

    };

    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, me));
    $page.controller = new Controller();
}(typeof window !== "undefined" ? window : this));