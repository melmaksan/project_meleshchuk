
$('#sortTable').DataTable({
    info: false,
    filter: false,
    columnDefs: [
        {
            searchable: false,
            orderable: false,
            targets: 5,
        },

        {
            searchable: false,
            orderable: false,
            targets: 6,
        }
    ],
});


$('#sortTable2').DataTable({
    order: [[0, 'desc']],
});

$('[data-toggle=confirmation]').confirmation({
    rootSelector: '[data-toggle=confirmation]',
    // other options
});