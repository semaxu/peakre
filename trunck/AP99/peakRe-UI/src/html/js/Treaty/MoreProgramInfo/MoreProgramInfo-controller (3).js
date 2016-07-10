(function (context) {
    var $page = $pt.getService(context, '$page');
    var defaultDialog = NModalForm.createFormModal("Select Object");
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
        showDefaultDialog : function(treeModel, model, propertyName, layoutName, modelName) {
            var _this = this;
            var layout = $pt.createFormLayout(layoutName);
            defaultDialog.show({
                model: treeModel,
                layout: layout,
                buttons:{
                    reset: false,
                    validate: false,
                    cancel: false,
                    // left:{},
                    right: [{
                        icon: 'ban',
                        text: 'cancel',
                        style: 'warning',
                        click: function(model) {
                            _this.onDefaultDialogCancelled(treeModel, model, propertyName, modelName);
                        }
                    },
                        {
                            // icon: 'pencil',
                            text: 'Confirm',
                            style: 'primary',
                            click: function () {
                                _this.onDefaultDialogConfirmed(treeModel, model, propertyName, modelName);
                            }
                        }]
                },
                draggable:true
            });
        },
        onDefaultDialogCancelled: function(treeModel, model, propertyName, modelName) {
            var base = treeModel.getOriginalModel();
            treeModel.set(modelName, base.classes.slice(0));
            defaultDialog.hide();
        },
        onDefaultDialogConfirmed: function(treeModel, model, propertyName, modelName) {
            var concat = function(array) {
                return array.filter(function(label) {
                    return label != null && !label.isBlank();
                }).join('/');
            };
            var getLabels = function(node) {
                if (node.selected) {
                    return node.text;
                } else if (node.children) {
                    return concat(node.children.map(function(child) {
                        return getLabels(child);
                    }));
                } else {
                    return '';
                }
            };
            var data = treeModel.get(modelName);
            var labels = concat(data.map(function(node) {
                return getLabels(node);
            }));
            model.set(propertyName, labels);
            defaultDialog.hide();
        }
    };

    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, me));
    $page.controller = new Controller();
    // for layout purpose
    //$page.controller.initializeErrorModel();
}(typeof window !== "undefined" ? window : this));