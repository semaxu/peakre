(function (context) {
    var $page = $pt.getService(context, '$page');
    $page.contractSelection = $page.codes.createMutableCodeTable();
    $page.uwYearSelection = $page.codes.createMutableCodeTable();
    var me = {
        //initialize: function () {
        //},
        initializeData: function () {
            this.model = $pt.createModel($page.model);
            var urlData = $pt.getUrlData();
            var refId = urlData.refId;
            var businessDirection = urlData.businessDirection;
            console.log('refId:' + refId);
            var _this = this;
            var loadContract= function (data, textStatus, jqXHR) {
                $helper.lowerKeysOfJSON(data.ContractSectionList);
                $helper.lowerKeysOfJSON(data.UwyearList);
                console.log(data.ContractSectionList);
                console.log(data.UwyearList);

                $page.contractSelection.reset(data.ContractSectionList);
                $page.uwYearSelection.reset(data.UwyearList);
            }.bind(this);
            var params = {
                RefId:refId,
                BusinessDirection:businessDirection
            };
            $page.service.loadContractSer(params, false, false, loadContract);


            var afterLoadAllUser = function (data, textStatus, jqXHR) {
                console.log(data);
                $helper.lowerKeysOfJSON(data);
                $page.model.userCodes = $page.codes.createMutableCodeTable();
                $page.model.userCodes.reset(data);
            }.bind(this);
            $page.service.loadAllUserCodes(null, false, false, afterLoadAllUser);

            var loadSettlementHistory= function (data, textStatus, jqXHR) {
                _this.myload(data);
            }.bind(this);
            params = {
                RefId:refId,
                BusinessDirection:businessDirection
            };
            $page.service.loadSettlementService(params, false, false, loadSettlementHistory);

        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout.createFormLayout());
            var form = <NForm model={this.model} layout={layout}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        },

        searchSettlement:function (){
            var _this = this;
            var params = {
                RefId:this.model.get("RefId"),
                BusinessDirection:this.model.get("BusinessDirection"),
                SettlementType : this.model.get("SettlementType"),
                UnderwritingYear : this.model.get("UnderwritingYear"),
                ContractSection :this.model.get("ContractSection")
            };
            console.log(params);
            var loadSettlementHistory= function (data, textStatus, jqXHR) {
                _this.myload(data);
                this.form.forceUpdate();
            }.bind(this);
            $page.service.loadSettlementService(params, false, false, loadSettlementHistory);
        },
        myload: function (data) {
            console.log(data);
           // this.model.mergeCurrentModel(data);
            this.model.mergeCurrentModel({
                SettlementItemInfo:data.SettlementItemInfo ? data.SettlementItemInfo:[],
                SettlementItemSummary:data.SettlementItemSummary ? data.SettlementItemSummary:[],
                SettlementSummary:data.SettlementSummary ? data.SettlementSummary: [],
                RefId:data.RefId,
                BusinessDirection:data.BusinessDirection
            });
        }
    };

    var detail = {
        getDetail: function () {
            if (!this.addDetailDialog) {
                this.addDetailDialog = NModalForm.createFormModal('Details');
            }
            return this.addDetailDialog;
        },
        createDetail : function(data){
            var detailForm = this.getDetail();
            var detailModel =$pt.createModel(data);
           // detailModel.mergeCurrentModel(data);
            console.log(detailModel);
            //var detailModel = dialogModel ;

            //? dialogModel : $pt.createModel()
            var detailLayout = $pt.createFormLayout($page.layout.settlementDetailLayout());

            detailForm.show({
                model: detailModel,
                layout: detailLayout,
                buttons: {
                    cancel: false,
                    validate: false,
                    reset: false,
                    right: [{
                        text: 'Exit',
                        style: 'warning',
                        click: function () {
                            detailForm.hide();
                        }
                    }]
                }
            }, 'horizontal');
        },

        showDetail : function(SettleDetailId){
            var afterGetDetail = function(data, textStatus, jqXHR) {
                console.log(data);

                this.createDetail(data);
            }.bind(this);
            $page.service.getDetailSer(SettleDetailId, false, false, afterGetDetail);
        }

    };


    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, me,detail));
    $page.controller = new Controller();
    // for layout purpose
    //$page.controller.initializeErrorModel();
}(typeof window !== "undefined" ? window : this));