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
    var BPSearch = React.createClass($pt.defineCellComponent({
        statics: {
            ADVANCE_SEARCH_URL: $ri.getRestfulURL("action.public.partner") + "/query",
            SEARCH_URL:  $ri.getRestfulURL("action.public.partner") + "/queryByPartnerCode",
            BUSINESS_ROLE: null
        },

        propTypes: {
            // model
            model: React.PropTypes.object,
            // CellLayout
            layout: React.PropTypes.object
        },
        searchProxy : function (data) {
            return {
                BusinessPartnerId: data.code
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
            //var codeTableId = this.getComponentOption("codeTableId");
            var _self = this;
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
                                condition_BusinessPartnerCategory: {
                                    label: "BP Category",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: codes.BusinessPartnerCategory
                                    },
                                    pos: {
                                        row: 1
                                    }
                                },
                                condition_RoleSelect: {
                                    label: "BP Role",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: codes.partnerRole,
                                        enabled : {
                                            depends : _self.getComponentOption("BusinessRole"),
                                            when : function(){
                                                return !_self.getComponentOption("BusinessRole") || _self.getComponentOption("BusinessRole") == 1;
                                            }
                                        }
                                    },
                                    pos: {
                                        row: 1
                                    }
                                },
                                condition_BusinessPartnerId: {
                                    label: "BP ID",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    },
                                    pos: {
                                        row: 1
                                    }
                                },
                                condition_TradingName: {
                                    label: "Name",
                                    comp: {
                                        type: $pt.ComponentConstants.Text
                                    },
                                    pos: {
                                        row: 2
                                    }
                                },
                                condition_Status: {
                                    label: "Status",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: codes.BusinessPartnerStatus
                                    },
                                    pos: {
                                        row: 2
                                    }
                                },
                                condition_Pending: {
                                    label: "Pending AML Check",
                                    comp: {
                                        type: $pt.ComponentConstants.Check
                                    },
                                    pos: {
                                        row: 2
                                    }
                                },
                                condition_Country: {
                                    label: "Country",
                                    comp: {
                                        type: $pt.ComponentConstants.Select,
                                        data: $page.codes.CedentCountry
                                    },
                                    pos: {
                                        row: 3
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
                                                        dialogModel.set("condition_BusinessPartnerCategory","");
                                                        dialogModel.set("condition_RoleSelect","");
                                                        dialogModel.set("condition_BusinessPartnerId","");
                                                        dialogModel.set("condition_TradingName","");
                                                        dialogModel.set("condition_Status","");
                                                        dialogModel.set("condition_Pending","");
                                                        dialogModel.set("condition_Country","");
                                                    }
                                                }, {
                                                    text: "Search",
                                                    style: "primary",
                                                    click: function(model){

                                                        var updateSearchResult= function(responseData, model) {
                                                            model.set('cachedCriteria', $.extend(true, {
                                                                countPerPage: 10,
                                                                pageIndex: responseData.PageIndex,
                                                                pageCount: responseData.PageCount,
                                                                url:BPSearch.ADVANCE_SEARCH_URL
                                                            }, model.get('condition')));
                                                            model.set('ResultTable', responseData.Rows ? responseData.Rows : []);
                                                        }
                                                        var url = BPSearch.ADVANCE_SEARCH_URL;
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
                                click:function (row) {
                                    _this.state.stopRetrieveLabelFromRemote = true;
                                    _this.getModel().set(_this.getDataId(), row.BusinessPartnerId);
                                    _this.setLabelText(row.Name);
                                    _this.state.stopRetrieveLabelFromRemote = false;
                                    _this.state.searchDialog.hide();
                                }
                            },
                            columns: [
                                {
                                    title: "BP ID",
                                    data: "BusinessPartnerId",
                                    width: "15%"
                                }, {
                                    title: "Name",
                                    data: "Name",
                                    width: "15%"
                                }, {
                                    title: "BP Category",
                                    data: "BusinessPartnerCategory",
                                    codes: codes.BusinessPartnerCategory,
                                    width: "15%"
                                }, {
                                    title: "BP Role",
                                    data: "RoleSelectIds",
                                    render : function(row){
                                        var value = row.RoleSelectIds;
                                        if(value == null || value == ""){
                                            return;
                                        }
                                        var arrValue = '';
                                        for(var i =0; i<value.length;i++){
                                            arrValue = arrValue +" "+ codes.partnerRole.getText(value[i]);
                                        }
                                        return arrValue;
                                    },
                                    width: "25%"
                                }, {
                                    title: "Status",
                                    data: "Status",
                                    codes: codes.BusinessPartnerStatus,
                                    width: "15%"
                                }
                            ]
                        },
                        pos: {
                            row: 8,
                            width: 12
                        }
                    },
                };
            } ;

            return $pt.createCellLayout(this.getDataId(), {
                comp: {
                    searchTriggerDigits: this.getComponentOption('searchTriggerDigits'),
                    searchUrl: BPSearch.SEARCH_URL,
                    advancedUrl: BPSearch.ADVANCED_SEARCH_URL,
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
                condition: {
                    RoleSelect : this.getComponentOption("BusinessRole")
                },
                cachedCriteria: {},
                criteria: {
                    PageIndex: 1,
                    PageCount: 1,
                    CountPerPage: 10
                }
            };
        }
    }));

    context.BPSearch = BPSearch;
    NFormCell.registerComponentRenderer('BPSearch', function (model, layout, direction, view) {
        return <BPSearch model={model} layout={layout} view={view}/>;
    });
    $pt.ComponentConstants.BPSearch = 'BPSearch';
}(typeof window !== 'undefined' ? window : this, jQuery, $pt));
