/**
 * Created by brad.wu on 9/4/2015.
 */
(function (context) {
    var $page = $pt.getService(context, '$page');

    context.LandingMenu = React.createClass({
        propTypes: {
            menus: React.PropTypes.object
        },
        onClick: function (func) {
            if (func) {
                func.call(this);
            }
        },
        renderMenuItem: function (menuItem) {
            if (menuItem.url) {
                return <div><a href={menuItem.url}>{menuItem.text}</a><br/></div>;
            } else {
                return <div><a href='javascript:void(0);'
                          onClick={this.onClick.bind(this, menuItem.func)}>{menuItem.text}</a><br/></div>;
            }
        },
        renderMenuGroup: function (menuGroup) {
            return (<div><span className='block-menu-link'>
                {menuGroup.map(this.renderMenuItem)}
            </span></div>);
        },
        renderMenu: function (menu) {
            var menuItems = [];
            var group = [];
            menu.children.forEach(function (menuItem) {
                if (menuItem.divider) {
                    menuItems.push(group);
                    group = [];
                } else {
                    group.push(menuItem);
                }
            });
            menuItems.push(group);
            return (<div
                className='col-sm-4 col-md-4 col-lg-4 flex'>
                <div className='panel panel-default'>
                    <div className='panel-body'>
                        <div className='row_index'>
                            <div className='col-sm-4 col-md-4 col-lg-4 center'>
                                <NIcon icon={menu.icon} size='5x' iconClassName='n-label-primary'/>
                            </div>
                            <div className='col-sm-8 col-md-8 col-lg-8'>
                                <NNormalLabel text={menu.text} style='primary' size='1-5x'/>
                                <NNormalLabel text={menu.description}/>
                                {menuItems.map(this.renderMenuGroup)}
                            </div>
                        </div>
                    </div>
                </div>
            </div>);
        },
        renderMenuRow: function (menuRow) {
            return (<div className='row flex'>
                {menuRow.map(this.renderMenu)}
            </div>);
        },
        render: function () {
            var menuRows = [];
            var row = [];
            this.props.menus.forEach(function (menu, index) {
                row.push(menu);
                if (index % 3 == 2) {
                    // the second menu
                    menuRows.push(row);
                    row = [];
                }
            });
            if (row.length > 0) {
                menuRows.push(row);
            }
            return (<div className='index-menu-blocks'>
                {menuRows.map(this.renderMenuRow)}
            </div>);
        }
    });
}(typeof window !== "undefined" ? window : this));