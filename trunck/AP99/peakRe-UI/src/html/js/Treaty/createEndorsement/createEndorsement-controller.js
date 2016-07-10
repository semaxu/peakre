(function (context) {
    var $page = $pt.getService(context, '$page');

    var me = {
        initializeData: function () {
            if (this.model == undefined || this.model == null) {
                this.model = $pt.createModel($page.model, $page.validator);
            }
            var urlData = $pt.getUrlData();
            var contCompId = urlData.ContCompId;
            var endoId = urlData.EndoId;
            $page.status = urlData.status;
            var _self = this;
            this.loadEndorsement({
                EndoId: endoId
            }, false, false, function (data, textStatus, jqXHR) {
                _self.model.mergeCurrentModel(data);
            }, null);
            this.model.set("ContCompId", contCompId);
        },

        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout);
            var form = <NForm model={this.model} layout={layout}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        },
        loadEndorsement: function (criteria, quiet, async, done, fail) {
            $page.service.loadEndorsement(criteria, quiet, async, done, fail);
        },
        isEffective: function () {
            var isEffective = true;
            var _self = this;
            var criteria = {ContCompId: _self.model.get("ContCompId")};
            var done = function (data) {
                var reinsStarting = data.ReinsStarting;
                var reinsEnding = data.ReinsEnding;
                var ContCompId = _self.model.get("ContCompId");
                var EffDate = moment(_self.model.get('EffDate')).format("YYYY-MM-DD");
                if (EffDate && reinsStarting && reinsEnding) {
                    if (EffDate < reinsStarting || reinsEnding < EffDate) {
                        isEffective = false;
                        NConfirm.getConfirmModal().show({
                            title: 'System Message',
                            disableClose: true,
                            messages: ['Effective Date must be between Period Start and Period End.']
                        });
                        _self.model.set("EffDate", "");
                    }
                }
            }.bind(this);
            $page.service.loadContractInfo(criteria, false, false, done, null);
            return isEffective;
        },
        save: function (needAlert) {
            this.model.validate();
            if (this.model.hasError() == true) {
                NConfirm.getConfirmModal().show({
                    title: 'System Message',
                    disableClose: true,
                    messages: ['Please fill in all mandatory information.']
                });
                return false;
            }
            var _self = this;
            var isSaved = false;
            var params = {ContCompId: _self.model.get("ContCompId")};
            var done = function (data) {
                if (typeof needAlert != 'undefined' && needAlert) {
                    NConfirm.getConfirmModal().show({
                        title: 'Message',
                        disableClose: true,
                        messages: ['Save successful.']
                    });
                }
                _self.model.mergeCurrentModel(data);
                $page.status = data.LatestStatus;
                isSaved = true;
            }.bind(this);
            var fail = function () {
                NConfirm.getConfirmModal().show({
                    title: 'Alert',
                    disableClose: true,
                    messages: $pt.getMessage("SaveFail")
                });
            }.bind(this);
            $page.service.saveEndorsement(this.model.getCurrentModel(), false, false, done, fail);
            return isSaved;
        }
    };

    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, me));
    $page.controller = new Controller();
    // for layout purpose
    //$page.controller.initializeErrorModel();
}(typeof window !== "undefined" ? window : this));
