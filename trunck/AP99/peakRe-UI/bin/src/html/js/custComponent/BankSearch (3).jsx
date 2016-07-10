/**
 * Created by elvira.du on 4/20/2016.
 */
(function (context, $, $pt) {
    var $page = $pt.getService(context, '$page');
    var codes = $pt.getService($page, 'codes');
    NTable.PAGE_JUMPING_PROXY = function (criteria){
        var newCriteria = $.extend({}, criteria);
        newCriteria.PageIndex = criteria.pageIndex;
        newCriteria.CountPerPage = criteria.countPerPage;
        delete newCriteria.pageIndex;
        delete newCriteria.countPerPage;
        delete newCriteria.limit;
        delete newCriteria.start;
        return newCriteria;
    };
    var BankSearch = React.createClass($pt.defineCellComponent({
        statics: {
            ADVANCE_SEARCH_URL: $ri.getRestfulURL("action.arap.bankAccount.queryBank"),
            SEARCH_URL:  $ri.getRestfulURL("action.arap.bankAccount.queryBankByCode")
        },
        propTypes: {
            // model
            model: React.PropTypes.object,
            // CellLayout
            layout: React.PropTypes.object
        },
        searchProxy : function (data) {
            return {
                BankCode: data.code
            }
        },
        searchProxyCallBack : function (data) {
            if(data && data.name && data.name != ''){
                return data.name
            }else {
                return null;
            }
        },
        render: function () {
            return <NSearchText model={this.getModel()} layout={this.createNSearchLayout()} view={this.props.view}/>;
        },
        createNSearchLayout: function () {
            var createSearchDialogLayout = function () {
                var _this = this;
                var direction = this.props.direction;
                if (!direction) {
                    direction = NForm.LABEL_DIRECTION;
                }
                var buttonCSS = {
                    'pull-right': true,
                    'pull-down': direction == 'vertical'
                };
                return {
                    conditionSection: {
                        label: "Search Criteria",
                        comp: {
                            type: $pt.ComponentConstants.Panel,
                            collapsible: true,
                            editLayout: {
                                condition_BankCode: {
                                    label: "Bank Code",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    },
                                    pos: {
                                        row: 1,
                                        width: 6
                                    }
                                },
                                condition_BankName: {
                                    label: "Bank Name",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    },
                                    pos: {
                                        row: 1,
                                        width: 6
                                    }
                                },
                                searchPanel: {
                                    comp: {
                                        type: $pt.ComponentConstants.ButtonFooter,
                                        buttonLayout: {
                                            right: [
                                                {
                                                    text: "Reset",
                                                    style: "warning",
                                                    click: function (dialogModel) {
                                                        dialogModel.set("condition_BankCode","");
                                                        dialogModel.set("condition_BankName","");
                                                    }
                                                }, {
                                                    text: "Search",
                                                    style: "primary",
                                                    click: function(model){
                                                        var updateSearchResult= function(responseData, model) {console.info(model);
                                                            model.set('cachedCriteria', $.extend(true, {
                                                                countPerPage: 10,
                                                                pageIndex: responseData.PageIndex,
                                                                pageCount: responseData.PageCount,
                                                                url:BankSearch.ADVANCE_SEARCH_URL
                                                            }, model.get('condition')));
                                                            model.set('ResultTable', responseData.Rows ? responseData.Rows : []);
                                                        };
                                                        console.log(model.get('condition'));
                                                        var url = BankSearch.ADVANCE_SEARCH_URL;
                                                        var searchModel = $.extend({CountPerPage: 10, PageIndex: 1}, model.get('condition'));
                                                        $pt.doPost(url, searchModel, {
                                                            quiet: true,
                                                            async: true
                                                        }).done(function(data) {updateSearchResult(data, model);
                                                        }.bind(this));
                                                    }
                                                }
                                            ]
                                        }
                                    },
                                    pos: {
                                        row: 6,
                                        width: 12
                                    }
                                }
                            }
                        },
                        pos: {
                            row: 1,
                            width: 12
                        }
                    },
                    ResultTable: {
                        comp: {
                            type: $pt.ComponentConstants.Table,
                            searchable: false,
                            sortable: false,
                            header: false,
                            pageable: true,
                            criteria: 'cachedCriteria',
                            rowOperations: {
                                icon: 'check',
                                click:function (row) {console.info(_this);
                                    _this.state.stopRetrieveLabelFromRemote = true;
                                    _this.getModel().set(_this.getDataId(), row.BankCode);
                                    _this.setLabelText(row.BankName);
                                    _this.state.stopRetrieveLabelFromRemote = false;
                                    _this.state.searchDialog.hide();
                                }
                            },
                            columns: [
                                {
                                    title: "Bank Code",
                                    data: "BankCode",
                                    width: "50%"
                                }, {
                                    title: "Bank Name",
                                    data: "BankName",
                                    width: "50%"
                                }
                            ]
                        },
                        pos: {
                            row: 8,
                            width: 12
                        }
                    }
                };
            } ;

            return $pt.createCellLayout(this.getDataId(), {
                comp: {
                    searchTriggerDigits: this.getComponentOption('searchTriggerDigits'),
                    searchUrl: BankSearch.SEARCH_URL,
                    advancedUrl: BankSearch.ADVANCED_SEARCH_URL,
                    searchDialogModel: this.createSearchDialogModel(),
                    searchDialogLayout: createSearchDialogLayout,
                    enabled : this.getComponentRuleValue('enabled',true),
                    searchProxy: this.searchProxy.bind(this),
                    searchProxyCallback: this.searchProxyCallBack.bind(this)
                }
            });
        },
        createSearchDialogModel: function () {
            return {
                Name: null,
                CountPerPage: 10,
                PageIndex: 1,
                Rows: null,
                condition: {},
                cachedCriteria: {},
                criteria: {
                    PageIndex: 1,
                    PageCount: 1,
                    CountPerPage: 10
                }
            };
        }
    }));

    context.BankSearch = BankSearch;
    NFormCell.registerComponentRenderer('BankSearch', function (model, layout, direction, view) {
        return <BankSearch model={model} layout={layout} view={view}/>;
    });
    $pt.ComponentConstants.BankSearch = 'BankSearch';
}(typeof window !== 'undefined' ? window : this, jQuery, $pt));
