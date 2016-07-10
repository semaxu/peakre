(function (context) {
    var $page = $pt.getService(context, '$page');
    $page.settlementNo = $page.codes.createMutableCodeTable();
    $page.contractSelection = $page.codes.createMutableCodeTable();

    var me = {
        //initialize: function () {
        //},
        initializeData: function () {
            this.model = $pt.createModel($page.model, $page.validator);
            // this.codes = $pt.createModel($page.codes);
            var urlData = $pt.getUrlData();
            var refId = urlData.refId;
            var businessDirection = urlData.businessDirection;
            var refType = urlData.refType;

            var _this = this;
            var loadSettlement = function (data, textStatus, jqXHR) {
                $helper.lowerKeysOfJSON(data);
                console.log(data);
                $page.settlementNo.reset(data);
            }.bind(this);
            var params = {
                RefId: refId,
                BusinessDirection: businessDirection

            };
            this.model.set("RefId", refId);
            this.model.set("BusinessDirection", businessDirection);

            $page.service.loadSettlementNoListSer(params, false, false, loadSettlement);

            var loadContract = function (data, textStatus, jqXHR) {
                console.log(data);
                $helper.lowerKeysOfJSON(data);
                $page.contractSelection.reset(data);

            }.bind(this);
            params = {
                RefId: refId,
                BusinessDirection: businessDirection,
                RefType: refType

            };
            $page.service.loadContractSection(params, false, false, loadContract);

        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout.createFormLayout());
            var form = <NForm model={this.model} layout={layout}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        },
        searchSettlementItem: function () {
            var _this = this;
            console.log(this.model.get("SettlementId"));
            if (this.model.get("SettlementId") == undefined) {
                NConfirm.getConfirmModal().show({
                    title: 'System Message',
                    disableClose: true,
                    messages: ['Please select Settlement ID.']
                });
            }else{
                var params = {
                    RefId: this.model.get("RefId"),
                    BusinessDirection: this.model.get("BusinessDirection"),
                    SettlementId: this.model.get("SettlementId")
                };
                var loadSettlementItem = function (data, textStatus, jqXHR) {
                    _this.myload(data);
                    console.log($page.contractSelection);
                    this.form.forceUpdate();
                }.bind(this);
                $page.service.loadSettlement(params, false, false, loadSettlementItem);
            }
        },
        myload: function (data) {
            console.log(data);
            //this.model.mergeCurrentModel(data);
            this.model.mergeCurrentModel({
                SettlementItemList:data.SettlementItemList ? data.SettlementItemList: []
            });
        },

        submit: function (model) {
            this.model.validate();
            if(this.model.hasError() == true){
                NConfirm.getConfirmModal().show({
                    title: 'System Message',
                    disableClose: true,
                    messages: ['Please fill in all mandatory information.']
                });
                return false;
            }
            console.log(model);
            var _this = this;
            var afterSubmit = function (data, textStatus, jqXHR) {
                NConfirm.getConfirmModal().show({
                    title: 'System Message',
                    disableClose: true,
                    messages: ['Successful.'],
                    afterClose: function (data) {
                        window.close();
                    }
                });
            }.bind(this);
            $page.service.submitSettlement(model, false, false, afterSubmit);
        }
    };

    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, me));
    $page.controller = new Controller();
    // for layout purpose
    //$page.controller.initializeErrorModel();
}(typeof window !== "undefined" ? window : this));
