/**
 * Created by Gordon.Cao on 10/20/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');
    var Controller = jsface.Class($.extend({}, $page.ControllerInterface, {
        initializePageType: function () {

        },
        initializeErrorModel: function () {
            return true;
        },
        initializeData: function () {
            var urlData = $pt.getUrlData();
            var partnerId = urlData.partnerId;
            var _this = this;
            this.model = $pt.createModel($page.model.createModel());
            if(partnerId>0){
                this.model.set("condition_PartnerId", partnerId);
            }else{
                this.model.set("condition_PartnerId", 0);
            }

            this.search(this.model.getCurrentModel().condition , false, false, function (data, textStatus, jqXHR) {
                _this.updateSearchResult(data, false);
            }, function (jqXHR, textStatus, errorThrown) {
                console.log(jqXHR);
                console.warn('Failed to query data when initializing, status=[' + textStatus + ']');
                console.warn(errorThrown);
            });
            var afterLoadAllUser = function (data, textStatus, jqXHR) {
                console.log(data);
                $helper.lowerKeysOfJSON(data);
                $page.model.userCodes = $page.codes.createMutableCodeTable();
                $page.model.userCodes.reset(data);
            }.bind(this);
            $page.service.loadAllUserCodes(null, false, false, afterLoadAllUser);
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
                resultTable: data.Rows,
                cachedCriteria: {
                    pageIndex: data.PageIndex,
                    pageCount: data.PageCount,
                    countPerPage: data.CountPerPage
                    //url: 'http://localhost:8080/peakre/restlet/v1/public/partner/page'
                }
            });
            if (updateUI) {
                this.form.forceUpdate();
            }

        }


    }));

    $page.controller = new Controller();
    $page.controller.initializePageType();

}(typeof window !== "undefined" ? window : this));