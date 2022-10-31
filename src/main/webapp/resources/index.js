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
    ],
});


$('#sortTable2').DataTable({
    order: [[0, 'desc']],
    columnDefs: [
        {
            orderable: false,
            targets: 4,
        },
    ],
});

$('#sortTable3').DataTable({
    filter: false,
    info: false,
    paginate: false,
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
    order: [[6, 'asc'], [4, 'desc']],
    columnDefs: [
        {
            orderable: false,
            targets: 5,
        },
    ],
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

// DateTime Picker#1
const d = new Date();
const one_month = d.setMonth(d.getMonth() + 1);
$(function () {
    $('#datetimepicker1').datetimepicker({
        timeZone: 'Europe/Kyiv',
        sideBySide: true,
        minDate: new Date(),
        maxDate: one_month,
        defaultDate: new Date(),
        format: 'YYYY-MM-DD HH:mm',
        stepping: 30,
        enabledHours: [9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19]
    });
});
