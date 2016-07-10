(function (context) {
    var $page = $pt.getService(context, '$page');
    NTable.registerInlineEditor('linkedBtn', {
        comp: {
            type: $pt.ComponentConstants.Button,
            style: 'link',
            labelFromModel: true,
            click: function (model) {
               
            }
        },
        css: {
            comp: 'link-in-table-cell'
        }
    });
    var Layout = jsface.Class({
        createEntryItemInformation: function () {
            return {
                label: "Entry Item Information",
                style: 'primary-darken',
                layout: {
                    SearchResult: {
                        label: "Entry Item Information",
                        comp: {
                            type: $pt.ComponentConstants.Table,
                            searchable: false,
                            sortable: false,
                            addable: false,
                            columns: [
								{
								    title: "General Ledger Mapping Entry Id",
								    data: "MappingEntryId",
								    visible: {
								        when: function () {
								            return false;
								        }
								    }
								}, {
                                    title: "Contract ID",
                                    data: "ContractCode"
                                },  {
                                    title: "Business Type",
                                    data: "BusinessType",
                                    codes:$page.codes.BusinessTransType
                                }, {
                                    title: "Business Number",
                                    data: "BusinessNumber"
                                }, {
                                    title: "Business Description",
                                    data: "BusinessDescription"
                                }, {
                                    title: "Entry Code",
                                    data: "EntryCode"
                                }, {
                                    title: "Entry Item",
                                    data: "EntryItem"
                                }, {
                                    title: "Currency",
                                    data: "Currency"
                                }, {
                                    title: "Amount",
                                    data: "Amount",
                                    render: function (row) {
                                        return $helper.formatNumberForLabel(row.Amount, 2);
                                    }
                                }, {
                                    title: "Amount in USD",
                                    data: "AmountInUSD",
                                    render: function (row) {
                                        return $helper.formatNumberForLabel(row.AmountInUSD, 2);
                                    }
                                }, {
                                    title: "Exchange Rate",
                                    data: "ExchangeRate",
                                    render: function (row) {
                                        return $helper.formatNumberForLabel(row.ExchangeRate, 6);
                                    }
                                }, {
                                    title: "Generation Date",
                                    data: "GenerationDate",
                                    render: function (row) {
                                        var generationDate = "";
                                        if (undefined != row.GenerationDate && null != row.GenerationDate && "" != row.GenerationDate){
                                            generationDate = moment(row.GenerationDate,"YYYY-MM-DD").format("DD/MM/YYYY")
                                        }
                                        return generationDate;
                                    }
                                }, {
                                    title: "Post Date",
                                    data: "PostDate",
                                    render: function (row) {
                                        var postDate = "";
                                        if (undefined != row.PostDate && null != row.PostDate && "" != row.PostDate){
                                            postDate = moment(row.PostDate,"YYYY-MM-DD").format("DD/MM/YYYY")
                                        }
                                        return postDate;
                                    }
                                }, {
                                    title: "Broker",
                                    data: "Broker"
                                }, {
                                    title: "Cedent/Retrocessionaire",
                                    data: "CedentRetrocessionaire"
                                }, {
                                    title: "UW Year",
                                    data: "UWYear"
                                }, {
                                    title: "UW Area",
                                    data: "UWArea",
                                    codes: $page.codes.CoveredArea
                                }, {
                                    title: "Indicator",
                                    data: "Indicator",
                                    codes: $page.codes.GL_Indicator
                                }, {
                                    title: "Post Status",
                                    data: "PostStatus",
                                    codes: $page.codes.GL_PostStatus
                                }, {
                                    title: "Operator",
                                    data: "Operator",
                                    codes: $page.codes.SystemUser
                                }
                            ],
                    		rowOperations: [
                                            {
                                                tooltip: "View",
                                                click:function(model){
                                                	 var mappingEntryId = model.MappingEntryId;
                                                     var url = $pt.getURL('ui.gl.doubleEntries');
                                                     window.open(url + "?mappingEntryId=" + mappingEntryId);
                                                }
                                            }
                                        ]
                        },
                        pos: {
                            row: 2,
                            width: 12
                        }
                    },
                    buttonPanel: {
                        comp: {
                            type: $pt.ComponentConstants.ButtonFooter,
                            buttonLayout: {
                                right: [
                                    {
                                        text: "Close",
                                        style: "warning",
                                        click: function () {
                                        	window.close();
                                        }
                                    }
                                ]
                            }
                        },
                        pos: {
                            row: 3,
                            width: 12
                        }
                    }
                }
            }
        },
        createFormLayout: function () {
            return {
                _sections: {
                    entryItemInformationSection: this.createEntryItemInformation()
                }
            }
        }
    });
    $page.layout = new Layout();
}(typeof window !== "undefined" ? window : this));
