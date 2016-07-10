(function (context) {
    var $page = $pt.getService(context, '$page');

    var me = {
        initializeData: function () {
            this.model = $pt.createModel($page.model.createBaseModel);
            var urlData = $pt.getUrlData();
            var contCompId = urlData.ContCompId;
            $page.status = urlData.status;
            this.loadEndorsementList({
                ContCompId: contCompId
            });
            this.model.set("ContCompId", contCompId);
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout.createLayout());
            var form = <NForm model={this.model} layout={layout}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        },

        isEnabledForEditAndDel: function (endoId) {
            var isEnabled = true;
            if ($page.status == "4" || $page.status == "2" || $page.status == "5") {
                return false;
            } else {
                if (this.model.get('EndorsementList') != undefined && this.model.get('EndorsementList') != null
                    && this.model.get('EndorsementList').length > 0) {
                    this.model.get('EndorsementList').forEach(function (item) {
                        if (item.EndoId > endoId) {
                            isEnabled = false;
                        }
                    })
                }
            }
            return isEnabled;
        },
        loadEndorsementList: function (criteria) {
            var _self = this;
            $page.service.loadEndorsementList(criteria, false, false, function (data, textStatus, jqXHR) {
                _self.model.mergeCurrentModel({
                    EndorsementList: data
                });
            }, null);
        },
        delRevert: function (model) {
            var isDleReverted = false;
            var done = function (data) {
                isDleReverted = true;
                $page.status = data.LatestStatus
            }
            $page.service.deleteEndorsement(model, false, false, done, null)
            return isDleReverted;
        },
        updateResult: function () {
            if (this.form) {
                this.form.forceUpdate();
            }
        }
    };

    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, me));
    $page.controller = new Controller();
}(typeof window !== "undefined" ? window : this));