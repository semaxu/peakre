/**
 * Created by Gordon.Cao on 10/20/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var me = {
        initializeErrorModel: function () {
            //this.errorModel = $pt.createModel($page.model.error);
            return true;
        },
        initializeData: function () {
            var urlData = $pt.getUrlData();
            // var deductionsId = urlData.DeductionsId;
            // var operateType = urlData.OperateType;
            this.model = $pt.createModel($page.model);
            // this.model.set("DeductionsId", deductionsId);
            // this.model.set("OperateType", operateType);
            this.model.mergeCurrentModel(urlData);
            if(this.model.get("OperateId")){
                this.loadPcSlidingForLog(this.model);
            }else{
                this.load(this.model);
            }
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout);
            var form = <NForm model={this.model} layout={layout}/>;
            if (this.model.get("OperateType") == 0 || this.model.get("OperateType") == 5 || (this.model.get("OperateType") == 3 && this.model.get("EndoType") == 3)) {
                layout = $pt.createFormLayout($page.layout);
                form = <NForm model={this.model} layout={layout} view={true}/>;
            }
            this.form = ReactDOM.render(form, document.getElementById('main'));

        },
        save: function (needAlert) {
            var isSaved = true;
            if(!this.validate(this.model)){
                return false;
            }
            var afterSave = function (data) {
                $page.controller.model.mergeCurrentModel(data);
                if (needAlert) {
                    NConfirm.getConfirmModal().show({
                        title: 'Message',
                        disableClose: true,
                        messages: ['Save successful.']
                    });
                }
                if (this.form) {
                    this.form.forceUpdate();
                }
            }.bind(this);
            var savedFail = function () {
                isSaved = false;
                NConfirm.getConfirmModal().show({
                    title: 'Alert',
                    disableClose: true,
                    messages: ['Save fail.']
                });
            }.bind(this);
            $page.service.save($page.controller.model.getCurrentModel(), false, false, afterSave, savedFail);
            return isSaved;
        },
        validate: function(model){
            var isNormal = true;
            if (model.get("MinimumRIPc") || model.get("MaximumRIPc") || model.get("MinimumLossPc") || model.get("MaximumLossPc")) {
                if(!model.get("MinimumRIPc") || !model.get("MaximumRIPc") || !model.get("MinimumLossPc") || !model.get("MaximumLossPc")){
                    NConfirm.getConfirmModal().show({
                        title: 'Attention',
                        disableClose: true,
                        messages: ['Please fill in all sliding scale information.']
                    });
                    isNormal = false;
                }
            }
            return isNormal;
        },
        load: function (model) {
            var afterLoad = function (data) {
                console.log(data);
                $page.controller.model.mergeCurrentModel(data);
                this.form.forceUpdate();
            }.bind(this);
            $page.service.load(model.getCurrentModel(), afterLoad, function () {
            });
        },
        loadPcSlidingForLog: function (model) {
            var afterLoad = function (data) {
                $page.controller.model.mergeCurrentModel(data);
                this.form.forceUpdate();
            }.bind(this);
            $page.service.loadPcSlidingForLog(model.getCurrentModel(), afterLoad, function () {
            });
        }
    };

    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, me));
    $page.controller = new Controller();
    //for layout purpose
    //$page.control.initializeErrorModel();
    //$page.control.initialize();
}(typeof window !== "undefined" ? window : this));