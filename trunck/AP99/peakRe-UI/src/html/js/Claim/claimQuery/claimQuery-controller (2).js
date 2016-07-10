(function (context) {
    var $page = $pt.getService(context, '$page');
    //  $page.EventCodes = $page.codes.createMutableCodeTable();

    var me = {
        //initialize: function () {
        //},
        initializeData: function () {
            //this.model = $pt.createModel($page.model);
            this.codes = $pt.createModel($page.codes);
            //this.model = $pt.createModel({
            //condition: {
            //CountPerPage: 10,
            //PageIndex: 1
            //    ClaimBranch:"1",
            //},
            //        results: [],
            //        lastCriteria: {}
            //    }
            //);
            this.model = $pt.createModel($page.model.createModel());

            var _this = this;
            this.model.addPostChangeListener("condition_DateOfLossTo", function (evt) {
                var DateOfLossTo = evt.model.get("condition_DateOfLossTo");
                var DateOfLossFrom = evt.model.get("condition_DateOfLossFrom");
                var nowDate = moment(new Date()).format("YYYY-MM-DD HH:mm");
                if (null != DateOfLossFrom && "" != DateOfLossFrom && undefined != DateOfLossFrom) {
                    DateOfLossFrom = moment(DateOfLossFrom).format("YYYY-MM-DD HH:mm");
                }
                if (null != DateOfLossTo && "" != DateOfLossTo && undefined != DateOfLossTo) {
                    DateOfLossTo = moment(DateOfLossTo).format("YYYY-MM-DD HH:mm");
                    if (DateOfLossTo < DateOfLossFrom) {
                        NConfirm.getConfirmModal().show({
                            title: 'System Message',
                            disableClose: true,
                            messages: ['Loss End Date must be later or equal to Loss Start Date.']
                        });
                        _this.model.set("condition_DateOfLossTo", "");
                    }
                    if (DateOfLossTo > nowDate) {
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
                if (null != DateOfLossTo && "" != DateOfLossTo && undefined != DateOfLossTo) {
                    DateOfLossTo = moment(DateOfLossTo).format("YYYY-MM-DD HH:mm");
                }
                if (null != DateOfLossFrom && "" != DateOfLossFrom && undefined != DateOfLossFrom) {
                    DateOfLossFrom = moment(DateOfLossFrom).format("YYYY-MM-DD HH:mm");
                    if (DateOfLossFrom > DateOfLossTo) {
                        NConfirm.getConfirmModal().show({
                            title: 'System Message',
                            disableClose: true,
                            messages: ['Loss Start Date must be earlier or equal to Loss End Date.']
                        });
                        _this.model.set("condition_DateOfLossFrom", "");
                    }
                    if (DateOfLossFrom > nowDate) {
                        NConfirm.getConfirmModal().show({
                            title: 'System Message',
                            disableClose: true,
                            messages: ['Loss Start Date must be earlier or equal to Today.']
                        });
                        _this.model.set("condition_DateOfLossFrom", nowDate);
                    }
                }

            });

            this.model.addPostChangeListener("ContractRecords", function (evt) {
                // console.log(_this.model.getCurrentModel().ContractRecords);
                _this.model.set("condition_ContractID", _this.model.getCurrentModel().ContractRecords.ContractCode);
                _this.model.set("condition_UnderwritingYear", _this.model.getCurrentModel().ContractRecords.UwYear);
                delete _this.model.getCurrentModel().ContractRecords;

            });

            var afterEventCode = function (data, textStatus, jqXHR) {
                //console.log(data);
                $helper.lowerKeysOfJSON(data);
                $page.model.eventCodes = $page.codes.createMutableCodeTable();
                $page.model.eventCodes.reset(data);
                //$page.EventCodes.reset(data);
                //console.log($page.EventCodes);

            }.bind(this);
            $page.service.loadEventCodes(null, false, false, afterEventCode);


            var afterLoadAllUser = function (data, textStatus, jqXHR) {
                //console.log(data);
                $helper.lowerKeysOfJSON(data);
                $page.model.userCodes = $page.codes.createMutableCodeTable();
                $page.model.userCodes.reset(data);
            }.bind(this);
            $page.service.loadAllUserCodes(null, false, false, afterLoadAllUser);

            var afterLoadCurrentUser = function (data, textStatus, jqXHR) {
                //console.log(data);
                _this.model.set("condition_TaskOwner", data.UserId);
            }.bind(this);
            $page.service.loadCurrentUserCodes(null, false, false, afterLoadCurrentUser);

            // var afterLoadCountry = function (data, textStatus, jqXHR) {
            //   console.log("==============================");
            //   console.log(data);
            //   $helper.lowerKeysOfJSON(data);
            //   $page.model.Countrys = $page.codes.createMutableCodeTable();
            //   $page.model.Countrys.reset(data);
            // }.bind(this);
            // $page.service.loadCountryService(null, false, false, afterLoadCountry);
            this.doSearchClaimQuery();
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout.createFormLayout());
            var form = <NForm model={this.model} layout={layout}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        },
        //loadClaim: function (claimId, type, opcode) {
        //    var url = $pt.getURL('ui.claim.claimInfo');
        //
        //    var loadClaimFu = function (data, textStatus, jqXHR) {
        //        if (data.OperatingBy == undefined || data.OperatingBy == null) {
        //            window.location.href = url + "?claimId=" + claimId + "&pageType=" + type;
        //        } else if (data.OperatingBy != opcode) {
        //            NConfirm.getConfirmModal().show({
        //                title: 'System Message',
        //                disableClose: true,
        //                messages: [data.OperatingBy + ' is operating this claim, you cannot edit it.']
        //            });
        //        } else {
        //            window.location.href = url + "?claimId=" + claimId + "&pageType=" + type;
        //        }
        //    }.bind(this);
        //    $page.service.loadClaim(claimId, false, false, loadClaimFu);
        //    //  OPERATING_BY
        //    // window.open(url + "?ClaimID=" + claimId);
        //
        //},
        loadClaim: function (rowModel, type) {
            var resource = {
                ResourceId: rowModel.ClaimId,
                ResourceType: 2,
                ResourceNo: rowModel.ClaimNo,
                OwnerId: rowModel.TaskOwner
            };

            var url = $pt.getURL('ui.claim.claimInfo');
            if (type == 5){
                var param = {
                    ClaimId: rowModel.ClaimId
                };
                var afterLoadXML = function (data, textStatus, jqXHR) {

                }.bind(this);
                $page.service.loadXMLService(rowModel.ClaimId, false, false, afterLoadXML);

            }else if (type == 2) {
                window.location.href = url + "?claimId=" + rowModel.ClaimId + "&pageType=" + type;
            } else {
                var done =function(){
                        window.location.href = url + "?claimId=" +rowModel.ClaimId+"&pageType="+type;
                }.bind(this);
                this.lockInfo(resource, done);
            }

            //var ss = this.lockInfo(Params);
        },
        resetClaimQueryPram: function () {
            delete this.model.getCurrentModel().condition;
            this.form.forceUpdate();
            $page.controller.model.mergeCurrentModel({
                condition: {
                    CountPerPage: 10,
                    PageIndex: 1
                }
            });

        },
        //doPageJumpClaimQuery: function(criteria, table) {
        //    var _this = this;
        //    var newCondition = $.extend({}, this.model.getCurrentModel().condition,criteria);
        //    var qureyReturn = function(data, textStatus, jqXHR){
        //        console.log(data);
        //        _this.updateSearchResult(data, false);
        //        table.forceUpdate();
        //    }.bind(this);
        //    $page.service.claimQuery(newCondition, false, true, qureyReturn);
        //    //    console.log(data);
        //    //    _this.updateSearchResult(data, false);
        //    //    table.forceUpdate();
        //    //});
        //},
        doSearchClaimQuery: function () {
            var criteria = $.extend({}, this.model.getCurrentModel());
            delete criteria.searchResult;
            delete criteria.cachedCriteria.pageIndex;
            delete criteria.cachedCriteria.PageCount;
            console.log(criteria.cachedCriteria.url);
            var _this = this;
            var qureyReturn = function (data, textStatus, jqXHR) {
                _this.updateSearchResult(data, true);
            }.bind(this);
            $page.service.claimQuery(criteria.cachedCriteria.url, _this.model.getCurrentModel().condition, false, true, qureyReturn);
        },
        updateSearchResult: function (data, updateUI) {
            console.log(data);
            this.model.mergeCurrentModel({
                searchResult: data.Rows ? data.Rows : [],
                cachedCriteria: {
                    pageIndex: data.PageIndex,
                    pageCount: data.PageCount,
                    countPerPage: data.CountPerPage
                }
            });
            this.model.getCurrentModel().cachedCriteria = $.extend(true, {}, this.model.getCurrentModel().cachedCriteria, this.model.getCurrentModel().condition);
            if (updateUI) {
                this.form.forceUpdate();
            }
        }
    };

    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, me));
    $page.controller = new Controller();
    // for layout purpose
    //$page.controller.initializeErrorModel();
}(typeof window !== "undefined" ? window : this));
