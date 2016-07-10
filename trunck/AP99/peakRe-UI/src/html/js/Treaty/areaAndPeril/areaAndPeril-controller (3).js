/**
 * Created by gang.wang on 10/20/2015.
 */
(function(context){
    var $page= $pt.getService(context,'$page');
    // var countryDialog = NModalForm.createFormModal("Select Country...");
    var me = {
        initializeErrorModel : function () {
            //this.errorModel = $pt.createModel($page.model.error);
            return true;
        },
        initializeData : function(){
            console.debug("initializeData");
            //this.codes = $pt.createModel($page.codes);
        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout);
            var form = <NForm model={$pt.createModel($page.model)} layout={layout}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        },
        // showCountryDialog: function(treeModel, model, propertyName) {
        //   var _this = this;
        //   var layout = $pt.createFormLayout($page.countryDialogLayout);
        //   countryDialog.show({
        //       model: treeModel,
        //       layout: layout,
        //       buttons:{
        //         reset: false,
        //         validate: false,
        //         cancel: false,
        //         // left:{},
        //         right: [{
        //             icon: 'ban',
        //             text: 'cancel',
        //             style: 'danger',
        //             click: function(model) {
        //               _this.onCountryDialogCancelled(treeModel, model, propertyName);
        //             }
        //         },
        //         {
        //             // icon: 'pencil',
        //             text: 'Confirm',
        //             style: 'primary',
        //             click: function () {
        //               _this.onCountryDialogConfirmed(treeModel, model, propertyName);
        //             }
        //         }]
        //       },
        //       draggable:true
        //   });
        // },
        // onCountryDialogCancelled: function(treeModel, model, propertyName) {
        //     var base = treeModel.getOriginalModel();
        //     // treeModel.set('countries', base["countries"].slice(0));
        //     // treeModel.__base =  $pt.cloneJSON(base);
        //     var countries = $.extend(true, {}, {countries:$pt.getValueFromJSON(base, 'countries')}).countries;
        //     treeModel.set('countries', countries);
        //     countryDialog.hide();
        // },
        // onCountryDialogConfirmed: function(treeModel, model, propertyName) {
        //     var concat = function(array) {
        //         return array.filter(function(label) {
        //           return label != null && !label.isBlank();
        //         }).join('/');
        //     };
        //     var getLabels = function(node) {
        //       if (node.selected) {
        //         return node.text;
        //       } else if (node.children) {
        //         return concat(node.children.map(function(child) {
        //           return getLabels(child);
        //         }));
        //       } else {
        //         return '';
        //       }
        //     };
        //     var data = treeModel.get("countries");
        //     var labels = concat(data.map(function(node) {
        //         return getLabels(node);
        //     }));
        //     model.set(propertyName, labels);
        //     // treeModel.__base =  $pt.cloneJSON(treeModel.getCurrentModel());
        //     treeModel.applyCurrentToBase();
        //     countryDialog.hide();
        // }
    };

    var Controller = jsface.Class($.extend({},$page.ControllerInterface,me));
    $page.control = new Controller();
    //for layout purpose
    //$page.control.initializeErrorModel();
    //$page.control.initialize();
}(typeof window !== "undefined" ? window : this));
