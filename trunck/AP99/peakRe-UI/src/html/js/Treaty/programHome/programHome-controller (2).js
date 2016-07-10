(function (context) {
    var $page = $pt.getService(context, '$page');
    var defaultDialog = NModalForm.createFormModal("Linked Program");
    var me = {
        //initialize: function () {
        //},
        initializeErrorModel : function () {
            //this.errorModel = $pt.createModel($page.model.error);
            return true;
        },
        initializeData: function() {
            this.model = $pt.createModel($page.model);
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout);
            var form = <NForm model={this.model} layout={layout}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        },
        showDefaultDialog : function(treeModel, model, layoutName) {
            var _this = this;
            var layout = $pt.createFormLayout(layoutName);
            defaultDialog.show({
                model: treeModel,
                layout: layout,
                buttons:{
                    reset: false,
                    validate: false,
                    cancel: false,
                    right : [
                        {
                            text: 'Exit',
                            style: 'warning',
                            click: function(model) {
                                defaultDialog.hide();
                            }
                        }
                    ]
                },
                draggable:true
            });
        },
        convert : function(dataInServer) {
            var codes = $page.codes.ClaimLimitCate.list();
            return codes.map(function(code) {
                var codeId = code.id;
                var item = dataInServer.find(function(item) {
                    return item.typeId == codeId;
                });
                if (item) {
                    item.selected = true;
                    item.label = code.text;
                    return item;
                } else {
                    return {
                        id: null,
                        typeId: codeId,
                        percentage: null,
                        amount: null,
                        selected: false,
                        label: code.text
                    };
                }
            })
        }
    };

    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, me));
    $page.controller = new Controller();
    // for layout purpose
    //$page.controller.initializeErrorModel();
}(typeof window !== "undefined" ? window : this));