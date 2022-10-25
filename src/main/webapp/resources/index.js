$('#sortTable').DataTable({
    info: false,
    filter: false,
    order: [[3, 'asc']],
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

$('#sortTable3').DataTable({
    order: [[0, 'desc']],
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
        },

        {
            searchable: false,
            orderable: false,
            targets: 7,
        },

        {
            searchable: false,
            orderable: false,
            targets: 8,
        },

        {
            searchable: false,
            orderable: false,
            targets: 9,
        },
    ],
});

$('#sortTable4').DataTable({
    info: false,
    paginate: false,
    order: [[0, 'asc']],
    columnDefs: [
        {
            orderable: false,
            targets: 4,
        },

        {
            searchable: false,
            orderable: false,
            targets: 5,
        },

        {
            orderable: false,
            targets: 3,
        },
    ]
});

$('#sortTable5').DataTable({
    columnDefs: [
        {
            searchable: false,
            orderable: false,
            targets: 4,
        },
    ]
});

$('#sortTable6').DataTable({
    info: false,
    paginate: false,
    columnDefs: [
        {
            searchable: false,
            orderable: false,
            targets: 4,
        },
    ]
});

$('#sortTable7').DataTable({
    order: [[5, 'asc'], [4, 'desc']],
});


$('[data-toggle=confirmation]').confirmation({
    rootSelector: '[data-toggle=confirmation]',
    // other options
});

$('[data-toggle=confirmation2]').confirmation({
    rootSelector: '[data-toggle=confirmation2]',
    // other options
});

$('#select').selectpicker();