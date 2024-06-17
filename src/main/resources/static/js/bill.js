document.addEventListener('DOMContentLoaded', function() {
    var urlParams = new URLSearchParams(window.location.search);
    var status = urlParams.get('status');
    var courseId = urlParams.get('courseId');
    var userId = urlParams.get('userId');
    var amount = urlParams.get('amount');
    var date = urlParams.get('date');

    // Format the date as needed
    var formattedDate = formatDate(date);

    // Update the invoice details
    document.querySelector('.meta tr:nth-child(1) td span').textContent = 'Hóa đơn' + userId;
    document.querySelector('.meta tr:nth-child(2) td span').textContent = formattedDate;

    document.querySelector('.meta tr:nth-child(3) td span').textContent = formatCurrency(amount);

    // Additional details can be added as needed
});

function formatDate(dateString) {
    // Assuming the date format is "YYYYMMDDHHMMSS"
    var year = dateString.substring(0, 4);
    var month = dateString.substring(4, 6);
    var day = dateString.substring(6, 8);
    var hour = dateString.substring(8, 10);
    var minute = dateString.substring(10, 12);
    var second = dateString.substring(12, 14);

    return day + '/' + month + '/' + year + ' ' + hour + ':' + minute + ':' + second;
}

function formatCurrency(amount) {
    // Assuming the amount is in Vietnamese dong
    return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(amount);
}
