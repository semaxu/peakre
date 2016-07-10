(function (context) {
    var $page = $pt.getService(context, '$page');

    var initialPricing = {
        initializeData: function () {
            var urlData = $pt.getUrlData();
            this.model = $pt.createModel($page.model.getBasicModel(), $page.validator.checkPricingValidate());
            this.model.mergeCurrentModel(urlData);
            var operateId = urlData.OperateId;
            var _this = this;
            if(operateId){
                $page.service.loadPricingEstimateForLog(
                    urlData.ContCompId,urlData.OperateId,
                    function (data) {
                        _this.model.mergeCurrentModel(data);
                        if(_this.form){
                            _this.form.forceUpdate();
                        }
                    }
                );
            }else{
                $page.service.load(
                    urlData.ContCompId,
                    function (data) {
                        if(!data.PricingId && _this.model.get("OperateType") != 1 && _this.model.get("OperateType") != 5){
                            _this.model.set('WrittenPartner', null);
                            _this.model.set('EarningPartner', null);
                        }else{
                            _this.model.mergeCurrentModel(data);
                        }
                        if(_this.form){
                            _this.form.forceUpdate();
                        }
                    }
                );
            }
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout.createLayout());
            var view = this.model.get("OperateType") != "1" && this.model.get("OperateType") != "5";
            var form = <NForm model={this.model} layout={layout} view={view}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        },
        savePricingInfo: function (model) {
          var _this = this;
          _this.model.validate();
          if (_this.model.hasError() == true) {
              NConfirm.getConfirmModal().show({
                  title: 'System Message',
                  disableClose: true,
                  messages: ['Please fill in all mandatory information.']
              });
              return false;
          }

            $page.service.save(
                model.getCurrentModel(),
                function (data) {
                    _this.model.mergeCurrentModel(data);
                    _this.exitPricing();
                }
            );
        },
        isVisible: function () {
            var isVisible = true;
            if (this.model.get("OperateType") != 1 && this.model.get("OperateType") != 5) {
                isVisible = false;
            }
            return isVisible;
        },
        exitPricing: function(){
            var url = "section.html?ContCompId=" + this.model.get("ContCompId")
                + "&ContractNature=" + this.model.get("ContractNature")
                + "&OperateType=" + this.model.get("OperateType")
            window.location.href = url;
        }
    };

    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, initialPricing));
    $page.controller = new Controller();

}(typeof window !== "undefined" ? window : this));
