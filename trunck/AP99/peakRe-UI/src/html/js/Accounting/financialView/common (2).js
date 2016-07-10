(function (context) {
    NTable.registerInlineEditor('linkedBtn', {
        comp: {
            type: $pt.ComponentConstants.Button,
            style: 'link',
            labelFromModel: true,
            click: function (model) {
                var entryCode = model.get('entryCode');
                var url = $pt.getURL('ui.accounting.transDetail');
                window.open(url + "?entryCode=" + entryCode);
            }
        },
        css: {
            comp: 'link-in-table-cell'
        }
    });
}(typeof window !== "undefined" ? window : this));