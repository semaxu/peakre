(function (context, $, $pt) {
    NFormCell.registerComponentRenderer('divider', function() {
        return <hr className='divider'/>;
    });
    $pt.ComponentConstants.Divider = {type: 'divider', popover: false, label: false};
}(typeof window !== "undefined" ? window : this, jQuery, $pt));
