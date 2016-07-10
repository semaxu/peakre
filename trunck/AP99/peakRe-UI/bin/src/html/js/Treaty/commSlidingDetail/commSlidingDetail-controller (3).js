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
            this.model = $pt.createModel($page.model, $page.validator.commSlidingValidate());
            this.model.mergeCurrentModel(urlData);
            if(this.model.get("OperateId")){
                this.loadCommSlidingForLog(this.model);
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
            var _self = this;
            var isSaved = true;
            if(!this.validate(this.model)){
                return false;
            }

            this.model.validate();
            if (this.model.hasError() == true) {
                NConfirm.getConfirmModal().show({
                    title: 'System Message',
                    disableClose: true,
                    messages: ['Please fill in all mandatory information.']
                });
                return false;
            }

            var afterSave = function (data) {
                delete data.deleteDeductionsAttach;
                _self.model.mergeCurrentModel(data);
                if (needAlert) {
                    NConfirm.getConfirmModal().show({
                        title: 'Message',
                        disableClose: true,
                        messages: ['Save successful.']
                    });
                }
                if (_self.form) {
                    _self.form.forceUpdate();
                }
                isSaved = true;
            }.bind(this);
            var savedFail = function () {
                isSaved = false;
                NConfirm.getConfirmModal().show({
                    title: 'Alert',
                    disableClose: true,
                    messages: ['Save fail.']
                });
            }.bind(this);
            $page.service.save(this.model.getCurrentModel(), false, false, afterSave, savedFail);
            return isSaved;
        },
        validate: function(model){
            var isNormal = true;
            model.get("DeductionsCarried").forEach(function(item){
                if(item.Name == "Profit" && item.Yn == "1"){
                    if(!(item.Extinction || item.Years)){
                        NConfirm.getConfirmModal().show({
                            title: 'Attention',
                            disableClose: true,
                            messages: ['Profit is carried forward, please provide detail information.']
                        });
                        isNormal = false;
                    }
                    if(item.Extinction == "0" && !item.Years){
                        NConfirm.getConfirmModal().show({
                            title: 'Attention',
                            disableClose: true,
                            messages: ["Please input Profit's Number Of Years."]
                        });
                        isNormal = false;
                    }
                }
                if(item.Name == "Loss" && item.Yn == "1"){
                    if(!(item.Extinction || item.Years)){
                        NConfirm.getConfirmModal().show({
                            title: 'Attention',
                            disableClose: true,
                            messages: ['Loss is carried forward, please provide detail information.']
                        });
                        isNormal = false;
                    }
                    if(item.Extinction == "0" && !item.Years){
                        NConfirm.getConfirmModal().show({
                            title: 'Attention',
                            disableClose: true,
                            messages: ["Please input Loss's Number Of Years."]
                        });
                        isNormal = false;
                    }
                }
            })
            if (model.get("MinimumRISs") || model.get("MaximumRISs") || model.get("MinimumLossSs") || model.get("MaximumLossSs")) {
                if(!model.get("MinimumRISs") || !model.get("MaximumRISs") || !model.get("MinimumLossSs") || !model.get("MaximumLossSs")){
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
            var _self = this;
            var afterLoad = function (data) {
                _self.model.mergeCurrentModel(data);
                if (_self.form) {
                    _self.form.forceUpdate();
                }
            }.bind(this);
            $page.service.load(model.getCurrentModel(), afterLoad, function () {
            });
        },
        loadCommSlidingForLog: function (model) {
            var _self = this;
            var afterLoad = function (data) {
                _self.model.mergeCurrentModel(data);
                if (_self.form) {
                    _self.form.forceUpdate();
                }
            }.bind(this);
            $page.service.loadCommSlidingForLog(model.getCurrentModel(), afterLoad, function () {
            });
        }
    };

    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, me));
    $page.controller = new Controller();
}(typeof window !== "undefined" ? window : this));