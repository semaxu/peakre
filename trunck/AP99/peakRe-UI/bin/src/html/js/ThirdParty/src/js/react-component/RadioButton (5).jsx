/**
 * radio button
 * layout: {
 *      label: string,
 *      dataId: string,
 *      pos: {
 *          row: number,
 *          col: number,
 *          width: number,
 *          section: string,
 *          card: string
 *      },
 *      css: {
 *          cell: string,
 *          comp: string
 *      },
 *      comp: {
 *          type: $pt.ComponentConstants.Radio,
 *          direction: string,
 *          data: CodeTable,
 *          enabled: {
 *              when: function,
 *              depends: string|string[]
 *          },
 *          visible: {
 *              when: function,
 *              depends: string|string[]
 *          }
 *      }
 * }
 */
(function (context, $, $pt) {
	var NRadio = React.createClass($pt.defineCellComponent({
		propTypes: {
			// model
			model: React.PropTypes.object,
			// CellLayout
			layout: React.PropTypes.object
		},
		getDefaultProps: function () {
			return {
				defaultOptions: {
					direction: 'horizontal',
					labelAtLeft: false
				}
			};
		},
		/**
		 * will update
		 * @param nextProps
		 */
		componentWillUpdate: function (nextProps) {
			// remove post change listener to handle model change
			this.removePostChangeListener(this.onModelChanged);
			this.removeEnableDependencyMonitor();
		},
		/**
		 * did update
		 * @param prevProps
		 * @param prevState
		 */
		componentDidUpdate: function (prevProps, prevState) {
			// add post change listener to handle model change
			this.addPostChangeListener(this.onModelChanged);
			this.addEnableDependencyMonitor();
		},
		/**
		 * did mount
		 */
		componentDidMount: function () {
			// add post change listener to handle model change
			this.addPostChangeListener(this.onModelChanged);
			this.addEnableDependencyMonitor();
		},
		/**
		 * will unmount
		 */
		componentWillUnmount: function () {
			// remove post change listener to handle model change
			this.removePostChangeListener(this.onModelChanged);
			this.removeEnableDependencyMonitor();
		},
		/**
		 * render label
		 * @param option {{text:string}}
		 * @param labelInLeft {boolean}
		 * @returns {XML}
		 */
		renderLabel: function (option, labelInLeft) {
			var css = {
				'radio-label': true,
				disabled: !this.isEnabled(),
				'radio-label-left': labelInLeft
			};
			return (<span className={$pt.LayoutHelper.classSet(css)}
			             onClick={this.isEnabled() ? this.onButtonClicked.bind(this, option) : null}>
            	{option.text}
        	</span>);
		},
		/**
		 * render radio button, using font awesome instead
		 * @params option radio option
		 * @returns {XML}
		 */
		renderRadio: function (option) {
			var checked = this.getValueFromModel() == option.id;
			var css = {
				disabled: !this.isEnabled(),
				checked: checked,
				'radio-container': true
			};
			var labelAtLeft = this.isLabelAtLeft();
			return (<div className='n-radio-option'>
				{labelAtLeft ? this.renderLabel(option, true) : null}
				<div className='radio-container'>
                <span className={$pt.LayoutHelper.classSet(css)}
                      onClick={this.isEnabled() ? this.onButtonClicked.bind(this, option) : null}
                      onKeyUp={this.isEnabled() ? this.onKeyUp.bind(this, option): null}
                      tabIndex='0'
                      ref={'out-' + option.id}>
                    <span className='check' onClick={this.onInnerClicked.bind(this, option)}/>
                </span>
				</div>
				{labelAtLeft ? null : this.renderLabel(option, false)}
			</div>);
		},
		render: function () {
			var css = {
				'n-radio': true,
				vertical: this.getComponentOption('direction') === 'vertical',
				'n-disabled': !this.isEnabled()
			};
			return (<div className={this.getComponentCSS($pt.LayoutHelper.classSet(css))}>
				{this.getComponentOption("data").map(this.renderRadio)}
				<input type="hidden" style={{display: "none"}}
				       onChange={this.onComponentChanged} value={this.getValueFromModel()}
				       ref='txt'/>
			</div>);
		},
		/**
		 * inner span clicked, force focus to outer span
		 * for fix the outer span cannot gain focus in IE11
		 * @param option
		 */
		onInnerClicked: function (option) {
			$(ReactDOM.findDOMNode(this.refs['out-' + option.id])).focus();
		},
		/**
		 * on button clicked
		 * @param option
		 */
		onButtonClicked: function (option) {
			this.setValueToModel(option.id);
		},
		onKeyUp: function (option, evt) {
			if (evt.keyCode == '32') {
				this.onButtonClicked(option);
			}
		},
		/**
		 * on js change
		 * @param evt
		 */
		onComponentChanged: function (evt) {
			// synchronize value to model
			this.setValueToModel(evt.target.checked);
		},
		/**
		 * on model changed
		 * @param evt
		 */
		onModelChanged: function (evt) {
			this.getComponent().val(evt.new);
			this.forceUpdate();
		},
		/**
		 * get js
		 * @returns {jQuery}
		 */
		getComponent: function () {
			return $(ReactDOM.findDOMNode(this.refs.txt));
		},
		isLabelAtLeft: function () {
			return this.getComponentOption('labelAtLeft');
		}
	}));
	context.NRadio = NRadio;
}(this, jQuery, $pt));
