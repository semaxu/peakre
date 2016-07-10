/**
 * icon based on font-awesome
 */
(function (context, $, $pt) {
	var NIcon = React.createClass({
		propTypes: {
			size: React.PropTypes.oneOf(["lg", "2x", "3x", "4x", "5x"]),
			fixWidth: React.PropTypes.bool,

			icon: React.PropTypes.string.isRequired,
			spin: React.PropTypes.bool,
			pulse: React.PropTypes.bool,
			rotate: React.PropTypes.oneOf([90, 180, 270]),
			flip: React.PropTypes.oneOf(["h", "v"]),
			iconClassName: React.PropTypes.string,

			backIcon: React.PropTypes.string,
			backSpin: React.PropTypes.bool,
			backPulse: React.PropTypes.bool,
			backRotate: React.PropTypes.oneOf([90, 180, 270]),
			backFlip: React.PropTypes.oneOf(["h", "v"]),
			backClassName: React.PropTypes.string,

			tooltip: React.PropTypes.string
		},
		getDefaultProps: function () {
			return {
				fixWidth: false,
				spin: false
			};
		},
		/**
		 * get size
		 * @returns {*}
		 */
		getSize: function () {
			var size = {
				"fa-lg": this.props.size === "lg",
				"fa-2x": this.props.size === "2x",
				"fa-3x": this.props.size === "3x",
				"fa-4x": this.props.size === "4x",
				"fa-5x": this.props.size === "5x",
				"fa-fw": this.props.fixWidth
			};
			if (this.props.size) {
				size['fa-' + this.props.size] = true;
			}
			return size;
		},
		/**
		 * get icon
		 * @returns {*}
		 */
		getIcon: function () {
			var c = {
				"fa": true,
				"fa-spin": this.props.spin,
				"fa-pulse": this.props.pulse,
				"fa-rotate-90": this.props.rotate == 90,
				"fa-rotate-180": this.props.rotate == 180,
				"fa-rotate-270": this.props.rotate == 270,
				"fa-flip-horizontal": this.props.flip === "h",
				"fa-flip-vertical": this.props.flip === "v"
			};
			c["fa-" + this.props.icon] = true;
			if (this.props.iconClassName) {
				c[this.props.iconClassName] = true;
			}
			return c;
		},
		/**
		 * get background icon
		 * @returns {*}
		 */
		getBackIcon: function () {
			var c = {
				"fa": true,
				"fa-spin": this.props.backSpin,
				"fa-pulse": this.props.backPulse,
				"fa-rotate-90": this.props.backRotate == 90,
				"fa-rotate-180": this.props.backRotate == 180,
				"fa-rotate-270": this.props.backRotate == 270,
				"fa-flip-horizontal": this.props.backFlip === "h",
				"fa-flip-vertical": this.props.backFlip === "v"
			};
			c["fa-" + this.props.backIcon] = true;
			if (this.props.backClassName) {
				c[this.props.backClassName] = true;
			}
			return c;
		},
		/**
		 * render
		 * @returns {XML}
		 */
		render: function () {
			var size = this.getSize();
			var iconClasses = this.getIcon();
			if (this.props.backIcon) {
				size["fa-stack"] = true;
				iconClasses['fa-stack-1x'] = true;
				var backIconClasses = this.getBackIcon();
				backIconClasses['fa-stack-2x'] = true;
				return (<span className={$pt.LayoutHelper.classSet(size)} title={this.props.tooltip}>
                <i className={$pt.LayoutHelper.classSet(iconClasses)}/>
                <i className={$pt.LayoutHelper.classSet(backIconClasses)}/>
            </span>);
			}
			return <span className={$pt.LayoutHelper.classSet($.extend(iconClasses, size))}
			             title={this.props.tooltip}/>;
		}
	});
	context.NIcon = NIcon;
}(this, jQuery, $pt));
