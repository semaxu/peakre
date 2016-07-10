/**
 * Created by Gordon.Cao on 10/20/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, {
        initializePageType: function () {
            var type = $pt.getUrlData();
            this.isManageClient = type && type.type == 'manage';
        },
        initializeErrorModel: function () {
            return true;
        },
        initializeData: function () {

            var _this = this;
            this.model = $pt.createModel($page.model.createModel());
            var afterLoadAllUser = function (data, textStatus, jqXHR) {
                console.log(data);
                $helper.lowerKeysOfJSON(data);
                $page.model.userCodes = $page.codes.createMutableCodeTable();
                $page.model.userCodes.reset(data);
            }.bind(this);
            $page.service.loadAllUserCodes(null, false, false, afterLoadAllUser);
            this.search(this.model.getCurrentModel().condition , false, false, function (data, textStatus, jqXHR) {
                var realData = data; //JSON.parse(data);
                _this.updateSearchResult(data, false);
            }, function (jqXHR, textStatus, errorThrown) {
                console.log(jqXHR);
                console.warn('Failed to query data when initializing, status=[' + textStatus + ']');
                console.warn(errorThrown);
            });

        },
        renderContent: function () {
            var layout = $pt.createFormLayout($page.layout.createFormLayout());
            var form = <NForm model={this.model} layout={layout}/>;
            this.form = ReactDOM.render(form, document.getElementById('main'));
        },
        doSearch: function () {
            var criteria = $.extend({}, this.model.getCurrentModel());
            delete criteria.partnerTable;
            delete criteria.cachedCriteria;
            var _this = this;
            this.search(this.model.getCurrentModel().condition , false, true, function (data) {
                _this.updateSearchResult(data, true);
            });
        },
        doPageJump: function(criteria, table) {
            console.log(criteria);
            var _this = this;
            this.search(criteria, false, true, function(data) {
                _this.updateSearchResult(data, false);
                table.forceUpdate();
            });
        },
        search: function (criteria, quiet, async, done, fail) {
            $page.service.query(criteria, quiet, async, done, fail);
        },
        updateSearchResult: function (data, updateUI) {
            console.log(data);
            this.model.mergeCurrentModel({
                partnerTable: data.Rows ? data.Rows: [],
                cachedCriteria: {
                    pageIndex: data.PageIndex,
                    pageCount: data.PageCount,
                    countPerPage: data.CountPerPage
                    //url: 'http://localhost:8080/peakre/restlet/v1/public/partner/page'
                }
            });
            this.model.getCurrentModel().cachedCriteria = $.extend(true,{},
                this.model.getCurrentModel().cachedCriteria,
                this.model.getCurrentModel().condition);
            if (updateUI) {
                this.form.forceUpdate();
            }
        },
        doPartnerEdit: function (rowModel, pageType) {

            var partnerId = rowModel.PartnerId;
            var businessPartnerCategory = rowModel.BusinessPartnerCategory;
            if(businessPartnerCategory==1){
                window.location.href = "bpCreationForIndividual.html?partnerId="+partnerId+"&pageType="+pageType;
            }else if(businessPartnerCategory==2){
                window.location.href = "bpCreationForCompany.html?partnerId="+partnerId+"&pageType="+pageType;
            }
        },
        doPartnerCreateIndividual:function(){
            window.location.href = "bpCreationForIndividual.html?pageType=0";
        },
        doPartnerCreateCompany:function(){
            window.location.href = "bpCreationForCompany.html?pageType=0";
        },
        resetQuery : function(){
            this.model.set("condition_BusinessPartnerCategory",null);
            this.model.set("condition_RoleSelect",null);
            this.model.set("condition_BusinessPartnerId",null);
            this.model.set("condition_TradingName",null);
            this.model.set("condition_Status",null);
            this.model.set("condition_Pending",null);
        }
    }));

    $page.controller = new Controller();
    $page.controller.initializePageType();

}(typeof window !== "undefined" ? window : this));