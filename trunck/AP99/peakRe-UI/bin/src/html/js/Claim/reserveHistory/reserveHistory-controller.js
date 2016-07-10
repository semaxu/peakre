(function (context) {
    var $page = $pt.getService(context, '$page');
    $page.contractSelection = $page.codes.createMutableCodeTable();
    $page.uwYearSelection = $page.codes.createMutableCodeTable();

    var me = {

        initializeData: function () {
            this.model = $pt.createModel($page.model);
           // this.codes = $pt.createModel($page.codes);
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

            var loadReserveHistory= function (data, textStatus, jqXHR) {
                _this.myload(data);
            }.bind(this);
            params = {
                RefId:refId,
                BusinessDirection:businessDirection
            };
            $page.service.loadReserve(params, false, false, loadReserveHistory);
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout.createFormLayout());
            var form = <NForm model={this.model} layout={layout}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        },
        myload: function (data) {
            console.log(data);
           // this.model.mergeCurrentModel(data);
            this.model.mergeCurrentModel({
                //data.Rows ? data.Rows: []
                ReserveHistoryInfo:data.ReserveHistoryInfo ? data.ReserveHistoryInfo:[],
                ReserveInfo:data.ReserveInfo ? data.ReserveInfo:[],
                ReserveSummary:data.ReserveSummary ? data.ReserveSummary: [],
                RefId:data.RefId,
                BusinessDirection:data.BusinessDirection
            });
        },
        searchReserve:function(){
            var _this = this;
            var params = {
                RefId:this.model.get("RefId"),
                BusinessDirection:this.model.get("BusinessDirection"),
                ReserveType : this.model.get("ReserveType"),
                UnderwritingYear : this.model.get("UnderwritingYear"),
                ContractSection :this.model.get("ContractSection")
            };
            console.log(params);
            var loadReserveHistory= function (data, textStatus, jqXHR) {
                _this.myload(data);
                this.form.forceUpdate();
            }.bind(this);
            $page.service.loadReserve(params, false, false, loadReserveHistory);
        }
    };

    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, me));
    $page.controller = new Controller();
    // for layout purpose
    //$page.controller.initializeErrorModel();
}(typeof window !== "undefined" ? window : this));