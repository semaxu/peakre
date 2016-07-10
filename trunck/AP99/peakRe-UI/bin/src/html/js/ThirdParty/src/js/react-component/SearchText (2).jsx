/**
 * search text
 */
(function (context, $, $pt) {
	var NSearchText = React.createClass($pt.defineCellComponent({
		statics: {
			ADVANCED_SEARCH_BUTTON_ICON: 'search',
			ADVANCED_SEARCH_DIALOG_NAME_LABEL: 'Name',
			ADVANCED_SEARCH_DIALOG_BUTTON_TEXT: 'Search',
			ADVANCED_SEARCH_DIALOG_CODE_LABEL: 'Code',
			ADVANCED_SEARCH_DIALOG_RESULT_TITLE: 'Search Result'
		},
		propTypes: {
			// model
			model: React.PropTypes.object,
			// CellLayout
			layout: React.PropTypes.object
		},
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
			// remove post change listener to handle model change
			this.removePostChangeListener(this.onModelChange);
			this.removeEnableDependencyMonitor();
		},
		/**
		 * did update
		 * @param prevProps
		 * @param prevState
		 */
		componentDidUpdate: function (prevProps, prevState) {
			this.getComponent().val(this.getValueFromModel());
			// add post change listener to handle model change
			this.addPostChangeListener(this.onModelChange);
			this.addEnableDependencyMonitor();
		},
		/**
		 * did mount
		 */
		componentDidMount: function () {
			// set model value to js
			this.getComponent().val(this.getValueFromModel());
			// add post change listener to handle model change
			this.addPostChangeListener(this.onModelChange);
			this.addEnableDependencyMonitor();
		},
		/**
		 * will unmount
		 */
		componentWillUnmount: function () {
			// remove post change listener to handle model change
			this.removePostChangeListener(this.onModelChange);
			this.removeEnableDependencyMonitor();
		},
		/**
		 * render
		 * @returns {XML}
		 */
		render: function () {
			var enabled = this.isEnabled();
			var css = {
				'n-search-text': true
			};
			if (!enabled) {
				css['n-disabled'] = true;
			}
			var middleSpanStyle = {
				width: '0'
			};
			return (<div className={this.getComponentCSS($pt.LayoutHelper.classSet(css))}>
				<div className="input-group">
					<input type="text" className="form-control search-code" onKeyUp={this.onComponentChange} ref="code"
					       disabled={!enabled} onFocus={this.onComponentFocused} onBlur={this.onComponentBlurred}/>
					<span className="input-group-btn" style={middleSpanStyle}/>
					<input type="text" className="form-control search-label" onFocus={this.onLabelFocused} ref="label"
					       disabled={!enabled}/>
				<span className="input-group-addon advanced-search-btn"
				      onClick={enabled ? this.showAdvancedSearchDialog : null}>
					<span className={'fa fa-fw fa-' + NSearchText.ADVANCED_SEARCH_BUTTON_ICON}/>
				</span>
					{this.renderNormalLine()}
					{this.renderFocusLine()}
				</div>
			</div>);
		},
		/**
		 * transfer focus to first text input
		 */
		onLabelFocused: function () {
			this.getComponent().focus();
		},
		onComponentFocused: function () {
			$(ReactDOM.findDOMNode(this.refs.focusLine)).toggleClass('focus');
			$(ReactDOM.findDOMNode(this.refs.normalLine)).toggleClass('focus');
		},
		onComponentBlurred: function () {
			$(ReactDOM.findDOMNode(this.refs.focusLine)).toggleClass('focus');
			$(ReactDOM.findDOMNode(this.refs.normalLine)).toggleClass('focus');
		},
		/**
		 * on js changed
		 */
		onComponentChange: function (evt) {
			if (this.state.search != null) {
				clearTimeout(this.state.search);
			}
			var value = evt.target.value;

			var triggerDigits = this.getSearchTriggerDigits();
			if (triggerDigits == null) {
				throw new $pt.createComponentException(
					$pt.ComponentConstants.Err_Search_Text_Trigger_Digits_Not_Defined,
					"Trigger digits cannot be null in search text.");
			}
			this.setLabelText("");
			if (value != null) {
				if (triggerDigits == -1 || value.length == triggerDigits) {
					var _this = this;
					this.state.search = setTimeout(function () {
						$pt.doPost(_this.getSearchUrl(), {
							code: value
						}, {
							quiet: true
						}).done(function (data) {
							if (typeof data === 'string') {
								data = JSON.parse(data);
							}
							_this.setLabelText(data.name);
						});
					}, 300);
				}
			}
			this.setValueToModel(value);
		},
		/**
		 * on model change
		 * @param evt
		 */
		onModelChange: function (evt) {
			this.getComponent().val(evt.new);
		},
		/**
		 * show advanced search dialog
		 */
		showAdvancedSearchDialog: function () {
			if (!this.state.searchDialog) {
				this.state.searchDialog = NModalForm.createFormModal(this.getLayout().getLabel(), 'advanced-search-dialog');
			}
			this.state.searchDialog.show({
				model: this.getAdvancedSearchDialogModel(),
				layout: this.getAdvancedSearchDialogLayout(),
				buttons: {
					reset: false,
					validate: false,
					cancel: false
				}
			});
		},
		/**
		 * pickup advanced result item
		 * @param item
		 */
		pickupAdvancedResultItem: function (item) {
			this.setLabelText(item.name);
			this.getModel().set(this.getDataId(), item.code);
		},
		setLabelText: function (text) {
			$(ReactDOM.findDOMNode(this.refs.label)).val(text);
		},
		/**
		 * get search url
		 * @returns {string}
		 */
		getSearchUrl: function () {
			return this.getComponentOption("searchUrl");
		},
		/**
		 * get advanced search url
		 * @returns {string}
		 */
		getAdvancedSearchUrl: function () {
			return this.getComponentOption("advancedUrl");
		},
		/**
		 * get minimum digits to trigger search
		 * @returns {number}
		 */
		getSearchTriggerDigits: function () {
			return this.getComponentOption("searchTriggerDigits");
		},
		getComponent: function () {
			return $(ReactDOM.findDOMNode(this.refs.code));
		},
		// search dialog
		getAdvancedSearchDialogModel: function () {
			var model = this.getComponentOption('searchDialogModel');
			if (model == null) {
				model = {
					name: null,
					countPerPage: 10,
					pageIndex: 1,

					items: null,
					criteria: {
						pageIndex: 1,
						pageCount: 1,
						countPerPage: 10
					}
				};
			}
			return $pt.createModel(model);
		},
		getAdvancedSearchDialogLayout: function () {
			var _this = this;
			var layout = this.getComponentOption('searchDialogLayout');
			if (layout == null) {
				layout = {
					name: {
						label: NSearchText.ADVANCED_SEARCH_DIALOG_NAME_LABEL,
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
								var currentModel = $.extend({}, model.getCurrentModel());
								// 移除查询结果和翻页查询条件JSON, 只留下当前需要查询的条件数据
								delete currentModel.items;
								delete currentModel.criteria;

								$pt.doPost(_this.getAdvancedSearchUrl(), currentModel, {
									done: function (data) {
										if (typeof data === 'string') {
											data = JSON.parse(data);
										}
										model.mergeCurrentModel(data);
										model.set('criteria_url', this.getAdvancedSearchUrl());
										console.debug(model.getCurrentModel());
										this.state.searchDialog.forceUpdate();
									}.bind(_this)
								});
							}
						},
						css: {
							comp: 'pull-right pull-down'
						},
						pos: {
							row: 10,
							col: 20,
							width: 6
						}
					},
					items: {
						label: NSearchText.ADVANCED_SEARCH_DIALOG_RESULT_TITLE,
						comp: {
							type: $pt.ComponentConstants.Table,
							indexable: true,
							searchable: false,
							rowOperations: {
								icon: "check",
								click: function (row) {
									_this.pickupAdvancedResultItem(row);
									_this.state.searchDialog.hide();
								}
							},
							pageable: true,
							criteria: "criteria",
							columns: [{
								title: NSearchText.ADVANCED_SEARCH_DIALOG_CODE_LABEL,
								width: 200,
								data: "code"
							}, {
								title: NSearchText.ADVANCED_SEARCH_DIALOG_NAME_LABEL,
								width: 400,
								data: "name"
							}]
						},
						pos: {
							row: 20,
							col: 10,
							width: 12
						}
					}
				};
			} else {
				layout = layout.call(this);
			}
			return $pt.createFormLayout(layout);
		}
	}));
	context.NSearchText = NSearchText;
}(this, jQuery, $pt));
