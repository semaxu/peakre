/**
 *
 */
(function (context, $, $pt) {
    NTable.PAGE_JUMPING_PROXY = function (criteria) {
        var newCriteria = $.extend({}, criteria);
        newCriteria.PageIndex = criteria.pageIndex;
        newCriteria.CountPerPage = criteria.countPerPage;
        delete newCriteria.pageIndex;
        delete newCriteria.countPerPage;
        delete newCriteria.limit;
        delete newCriteria.start;
        return newCriteria;
    };
    var searchEntryItem = React.createClass($pt.defineCellComponent({
        statics: {
            SEARCH_URL: $ri.getRestfulURL("action.common.codeTable") + "/search",
            ADVANCED_SEARCH_URL: $ri.getRestfulURL("action.common.codeTable") + "/page"
        },
        propTypes: {
            // model
            model: React.PropTypes.object,
            // CellLayout
            layout: React.PropTypes.object
        },
        render: function () {
            return <NSearchText model={this.getModel()} layout={this.createNSearchLayout()} view={this.props.view}/>;
        },
        createNSearchLayout: function () {
            var codeTableId = this.getComponentOption("codeTableId");
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
                    Name: {
                        label: "Contract ID",
                        comp: {
                            type: $pt.ComponentConstants.Text
                        },
                        pos: {
                            row: 10,
                            col: 10,
                            width: 6
                        }
                    },
                    button: {
                        label: NSearchText.ADVANCED_SEARCH_DIALOG_BUTTON_TEXT,
                        comp: {
                            type: $pt.ComponentConstants.Button,
                            style: 'primary',
                            click: function (model) {
                                var name = model.get('Name');
                                $pt.doPost(_this.getAdvancedSearchUrl(), {
                                    Name: name,
                                    PageIndex: 1,
                                    CountPerPage: 10,
                                    CodeTableId: codeTableId
                                }, {
                                    done: (function (data) {
                                        if (typeof data === 'string') {
                                            data = JSON.parse(data);
                                        }
                                        model.set('criteria', {
                                            pageIndex: data.PageIndex,
                                            countPerPage: data.CountPerPage,
                                            pageCount: data.PageCount,
                                            Name: name,
                                            url: this.getAdvancedSearchUrl()
                                        });
                                        model.set('Rows', data.Rows);
                                        this.state.searchDialog.forceUpdate();
                                    }).bind(_this)
                                });
                            }
                        },
                        css: {
                            comp: $pt.LayoutHelper.classSet(buttonCSS)
                        },
                        pos: {
                            row: 10,
                            col: 20,
                            width: 6
                        }
                    },
                    Rows: {
                        label: NSearchText.ADVANCED_SEARCH_DIALOG_RESULT_TITLE,
                        comp: {
                            type: $pt.ComponentConstants.Table,
                            indexable: true,
                            searchable: false,
                            rowOperations: {
                                icon: "check",
                                click: function (row) {
                                    //_this.pickupAdvancedResultItem(row);
                                    _this.state.stopRetrieveLabelFromRemote = true;
                                    alert(row.Name);
                                    alert(row.Code);
                                    _this.getModel().set("EntryItem", row.Name);
                                    _this.getModel().set("EntryCode", row.Code);
                                    _this.state.stopRetrieveLabelFromRemote = false;
                                    _this.state.searchDialog.hide();
                                }
                            },
                            pageable: true,
                            criteria: "criteria",
                            columns: [{
                                title: "Contract ID",
                                width: 200,
                                data: "Name"
                            }, {
                                title: "UW YEAR",
                                width: 400,
                                data: "Code"
                            }]
                        },
                        evt: {
                            pageChange: function (evt) {
                                //console.log(evt.target.getModel());
                                //console.log(evt.criteria);
                                var table = evt.target;
                                $pt.doPost(_this.getAdvancedSearchUrl(), evt.criteria, {
                                    done: (function (data) {
                                        if (typeof data === 'string') {
                                            data = JSON.parse(data);
                                        }
                                        //console.log(data);
                                        var model = table.getModel();
                                        model.set('criteria_pageIndex', data.PageIndex);
                                        model.set('criteria_pageCount', data.PageCount);
                                        table.getModel().set(table.getDataId(), data.Rows);
                                        //evt.target.forceUpdate();
                                    }).bind(_this)
                                });
                            }
                        },
                        pos: {
                            row: 20,
                            col: 10,
                            width: 12
                        }
                    }
                };
            };

            return $pt.createCellLayout(this.getDataId(), {
                comp: {
                    searchTriggerDigits: this.getComponentOption('searchTriggerDigits'),
                    searchUrl: searchEntryItem.SEARCH_URL + this.getComponentOption('codeName'),
                    advancedUrl: searchEntryItem.ADVANCED_SEARCH_URL + this.getComponentOption('codeName'),
                    searchDialogModel: this.createSearchDialogModel(),
                    searchDialogLayout: createSearchDialogLayout,
                    codeTableId: this.getComponentOption("codeTableId")
                }
            });
        },
        createSearchDialogModel: function () {
            return {
                Name: null,
                CountPerPage: 10,
                PageIndex: 1,

                Rows: null,
                criteria: {
                    PageIndex: 1,
                    PageCount: 1,
                    CountPerPage: 10
                }
            };
        },
        updateSearchResult: function (data, updateUI) {

        }
    }));

    context.searchEntryItem = searchEntryItem;
    NFormCell.registerComponentRenderer('searchEntryItem', function (model, layout, direction, view) {
        return <searchEntryItem model={model} layout={layout} view={view}/>;
    });
    $pt.ComponentConstants.searchEntryItem = 'searchEntryItem';
}(typeof window !== 'undefined' ? window : this, jQuery, $pt));