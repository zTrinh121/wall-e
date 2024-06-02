
// Start: Drop down
document.addEventListener("DOMContentLoaded", function () {
    const bell = document.getElementById("bell");
    const contentNotification = document.querySelector(".content-notification");
    const profileDropdownList = document.querySelector(".profile-dropdown-list");
    const profileDropdownBtn = document.querySelector(".profile-dropdown-btn");

    function toggleProfileDropdown() {
        profileDropdownList.classList.toggle("active");
        contentNotification.style.display = "none"; // Hide notification dropdown
    }

    function toggleNotificationDropdown() {
        contentNotification.style.display =
            contentNotification.style.display === "block" ? "none" : "block";
        profileDropdownList.classList.remove("active"); // Hide profile dropdown
    }

    bell.addEventListener("click", function () {
        toggleNotificationDropdown();
    });

    profileDropdownBtn.addEventListener("click", function () {
        toggleProfileDropdown();
    });

    // Close both dropdowns if the user clicks outside of them
    window.addEventListener("click", function (event) {
        if (
            !bell.contains(event.target) &&
            !contentNotification.contains(event.target)
        ) {
            contentNotification.style.display = "none";
        }
        if (
            !profileDropdownBtn.contains(event.target) &&
            !profileDropdownList.contains(event.target)
        ) {
            profileDropdownList.classList.remove("active");
        }
    });
});
// End: Drop down

// Start: Hamburger
document.addEventListener('DOMContentLoaded', function () {
    const hamburger = document.querySelector('.hamburger');
    const wrapper = document.querySelector('.wrapper');
    const search = document.querySelector('.search');

    hamburger.addEventListener('click', function () {
        wrapper.classList.toggle('collapse');
        if (window.innerWidth < 768) {
            search.classList.toggle('show-search');
        }
    });
});
// End: Hamburger

// Start view notification
$(document).ready(function () {
    // Khi trang được tải, thực hiện AJAX để lấy dữ liệu từ endpoint
    $.ajax({
        url: '/admin/notifications',
        method: 'GET',
        success: function (data) {
            // Xóa nội dung cũ của content-notification
            $('.content-notification').empty();
            data.forEach(function (notification) {
                if (notification.sendTo === "All" || notification.sendTo === "admin1@example.com") {
                    var originalDate = new Date(notification.publishedDate);
                    var formattedDate = originalDate.toISOString().split('T')[0];

                    var notificationHtml = '<ul><li><a href="#" id="' + notification.id + '" data-title="' + notification.title + '" data-content="' + notification.content + '" data-publishedDate="' + formattedDate + '">' + notification.title + '</a></li></ul>';
                    $('.content-notification').append(notificationHtml);

                }

            });
            $('.content-notification a').click(function (event) {
                var title = $(this).data('title');
                var content = $(this).data('content');
                var publishedDate = $(this).data('publisheddate');

                $('#notificationTitleViewModal').text(title);
                $('#notificationContentViewModal').text(content);
                $('#notificationPublishedDateViewModal').text(publishedDate);
                $('#viewNotificationModal').css('display', 'block');
            });

            $('#viewNotificationModal .close').click(function () {
                $('#viewNotificationModal').css('display', 'none');
            });

            $(window).click(function (event) {
                if (event.target == $('#viewNotificationModal')[0]) {
                    $('#viewNotificationModal').css('display', 'none');
                }
            });

        },
        error: function (error) {
            console.log('Error:', error);
        }
    });
});
// End view notification