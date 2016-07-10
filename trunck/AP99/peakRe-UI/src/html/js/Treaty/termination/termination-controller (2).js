(function (context) {
    var $page = $pt.getService(context, '$page');

    var me = {
        initializeData: function () {
            this.model = $pt.createModel($page.model.createBaseModel);
            var urlData = $pt.getUrlData();
            var contCompId = urlData.ContCompId;
            this.loadTermination({
                ContCompId: contCompId
            });
            this.model.set("ContCompId", contCompId);
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout.createLayout());
            var form = <NForm model={this.model} layout={layout}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        },
        loadTermination: function (criteria) {
            var _self = this;
            var done = function (data) {
                _self.model.mergeCurrentModel(data);
            }.bind(this);
            $page.service.loadTermination(criteria, false, false, done, null);
        },
        save: function () {
            var _self = this;
            var isSaved = false;
            var done = function (data) {
                _self.model.mergeCurrentModel(data);
                isSaved = true;
            }.bind(this);
            var fail = function () {
                NConfirm.getConfirmModal().show({
                    title: 'Alert',
                    disableClose: true,
                    messages: $pt.getMessage("SaveFail")
                });
            }.bind(this);
            $page.service.saveTermination(this.model.getCurrentModel(), false, false, done, fail);
            return isSaved;
        }
    };

    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, me));
    $page.controller = new Controller();
}(typeof window !== "undefined" ? window : this));