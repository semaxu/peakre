/**
 * search text
 */
(function (context, $, $pt) {
    var NContractSearchText = React.createClass($pt.defineCellComponent({
        displayName: 'NContractSearchText',
        statics: {
           ContractCategory : null
        },
        propTypes: {},
        getDefaultProps: function () {
            return {
                defaultOptions: {}
            };
        },
        getInitialState: function () {
            return {};
        },
        /**
         * will update
         * @param nextProps
         */
        componentWillUpdate: function (nextProps) {
        },
        /**
         * did update
         * @param prevProps
         * @param prevState
         */
        componentDidUpdate: function (prevProps, prevState) {
        },
        /**
         * did mount
         */
        componentDidMount: function () {
        },
        /**
         * will unmount
         */
        componentWillUnmount: function () {
        },
        /**
         * render
         * @returns {XML}
         */
        render: function () {
            var layout = {
                comp: {
                    rightAddon: {
                        icon: 'search',
                        click: this.onAdvancedSearch
                    }
                }
            };
            return <NText model={this.getModel()} layout={$pt.createCellLayout(this.getDataId(), layout)} view={this.isViewMode()}/>;
        },
        onAdvancedSearch: function () {
            if (!this.state.dialog) {
                this.state.dialog = NModalForm.createFormModal("Contract Search","abc");
            }
            var dialog = this.state.dialog;
            this.ContractCategory = this.getComponentOption("contractCategory");
            var model = $pt.createModel({
                condition: {SearchStatus : this.getComponentOption("contractStatus"),ContractCategory : this.ContractCategory},
                cachedCriteria: {}
            });
            dialog.show({
                model: model,
                layout: $pt.createFormLayout(this.getDialogLayout()),
                direction: 'horizontal',
                //title: 'Contract Search',
                footer: false
            });
        },
        onSearchClicked: function(model) {
            console.log(model.get('condition'));
            var url = this.getSearchUrl();
            var searchModel = $.extend({CountPerPage: 10, PageIndex: 1}, model.get('condition'));
            $pt.doPost(url, searchModel, {
                quiet: true,
                async: true
            }).done(function(data) {
                this.updateSearchResult(data, model);
            }.bind(this));
        },
        updateSearchResult: function(responseData, model) {
            model.set('cachedCriteria', $.extend(true, {
                countPerPage: 10,
                pageIndex: responseData.PageIndex,
                pageCount: responseData.PageCount,
                url: this.getSearchUrl()
            }, model.get('condition')));
            model.set('ResultTable', responseData.Rows ? responseData.Rows : []);
        },
        pickupRow: function(row) {
            var isSingle = this.getComponentOption("isSingle");
            if(isSingle && isSingle === true){
                this.setValueToModel(row.ContractCode);
            } else {
                this.getModel().set("ContractRecords",row);
            }
            this.state.dialog.hide();
        },
        resetParams:function(dialogModel){

            dialogModel.set("condition_ContractCode","");
            dialogModel.set("condition_ContractName","");
            dialogModel.set("condition_UwYear","");
            dialogModel.set("condition_Cedent","");
            dialogModel.set("condition_Broker","");
            dialogModel.set("condition_ContractCategory",this.ContractCategory);

            var dialog = this.state.dialog;
            dialog.forceUpdate();
        },
        getDialogLayout: function () {
            var _this = this;
            return {
                conditionSection: {
                    label: "Search Criteria",
                    comp: {
                        type: $pt.ComponentConstants.Panel,
                        collapsible: true,
                        editLayout: {
                            condition_ContractCode: {
                                label: "Contract ID",
                                comp: {},
                                pos: {
                                    row: 1
                                }
                            },
                            condition_ContractName: {
                                label: "Contract Name",
                                comp: {},
                                pos: {
                                    row: 1
                                }
                            },
                            condition_UwYear: {
                                label: "UW Year",
                                comp: {},
                                pos: {
                                    row: 1
                                }
                            },
                            condition_Cedent: {
                                label: "Cedent",
                                comp: {
                                    type: $pt.ComponentConstants.CodeSearch,
                                    searchTriggerDigits: 6,
                                    codeTableId: "800001"
                                },
                                pos: {
                                    row: 2
                                }
                            },
                            condition_Broker: {
                                label: "Broker",
                                comp: {
                                    type: $pt.ComponentConstants.CodeSearch,
                                    searchTriggerDigits: 6,
                                    codeTableId: "800000"
                                },
                                pos: {
                                    row: 2
                                }
                            },

                            condition_ContractCategory: {
                                label: "Contract Category",
                                comp: {
                                    type: $pt.ComponentConstants.Select,
                                    data: $page.codes.ContractCategory,
                                    enabled : {
                                        depends : _this.ContractCategory,
                                        when : function(){
                                        return null == _this.ContractCategory;
                                    }}
                                },
                                pos: {
                                    row: 2
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
                                                    _this.resetParams(dialogModel);
                                                }
                                            }, {
                                                text: "Search",
                                                style: "primary",
                                                click: this.onSearchClicked
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
                            click: function(row) {
                                _this.pickupRow(row);
                            }
                        },
                        columns: [
                            {
                                title: "Contract ID",
                                data: "ContractCode",
                                width: "15%"
                            }, {
                                title: "Contract Name",
                                data: "ContractName",
                                width: "15%"
                            }, {
                                title: "Cedent",
                                data: "Cedent",
                                width: "15%",
                            }, {
                                title: "Broker",
                                data: "Broker",
                                width: "15%"
                            }, {
                                title: "UY",
                                data: "UwYear",
                                width: "10%"
                            },{
                                    title: "Period Start",
                                    data: "ReinsStarting",
                                render: function (row) {
                                    return $helper.formatDateForTableView(row.ReinsStarting, "DD/MM/YYYY");
                                },
                                    width: "15%"
                            },{
                                title: "Status",
                                data: "LatestStatus",
                                codes: $page.codes.LatestStatus,
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
        },
        getSearchUrl: function() {
            return this.getComponentOption('url');
        }
    }));
    context.NContractSearchText = NContractSearchText;
    NFormCell.registerComponentRenderer('ContractSearch', function (model, layout, direction, view) {
        return <NContractSearchText model={model} layout={layout} view={view}/>;
    });
    $pt.ComponentConstants.NContractSearchText = 'ContractSearch';
})(window, jQuery, $pt);
