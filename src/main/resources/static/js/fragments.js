document.addEventListener("DOMContentLoaded", function () {
    const bell = document.getElementById("bell");
    const contentNotification = document.querySelector(".content-notification");
    const systemNotification = document.querySelector(".system-notification");
    const profileDropdownList = document.querySelector(".profile-dropdown-list");
    const profileDropdownBtn = document.querySelector(".profile-dropdown-btn");
    const notificationModal = document.getElementById("notificationModal");
    const notificationTitle = document.getElementById("notificationTitle");
    const notificationDetails = document.getElementById("notificationDetails");
    const closeModal = document.getElementById("viewNotificationModalClose");
    const userRole = document.getElementById("user-role").innerHTML;
    let apiRole;
    let apiNotificationUrlGet;
    let allNotifications = [];
    let allNotificationsUnseen = [];
    let allNotificationSeen = [];
    let allNotificationSystem = [];
    let allNotificationSystemUnseen = [];
    let allNotificationSystemSeen = [];
    let allNotificationCenter = [];
    let allNotificationCenterSeen = [];
    let allNotificationCenterUnseen = [];
    let allNotificationPrivate = [];
    let allNotificationPrivateUnseen = [];
    let allNotificationPrivateSeen = [];

    switch (userRole){
        case "PARENT":
            apiNotificationUrlGet = `api/parent/notifications/all`;
            apiRole = `api/parent`;
            break;
        case "STUDENT":
            apiNotificationUrlGet = `api/student/notifications/all`
            apiRole = `api/student`;
            break;
        case "TEACHER":
            apiNotificationUrlGet = `api/teacher/notifications/all`
            apiRole = `api/teacher`;
            break;
    }
    function toggleProfileDropdown() {
        profileDropdownList.classList.toggle("active");
        contentNotification.style.display = "none";
    }

    function toggleNotificationDropdown() {
        if (contentNotification.style.display === "block") {
            contentNotification.style.display = "none";
        } else {
            contentNotification.style.display = "block";
            systemNotification.style.display = "block";
            fetchNotifications(apiNotificationUrlGet);
        }
        profileDropdownList.classList.remove("active");
    }

    bell.addEventListener("click", function () {
        console.log("Bell clicked"); // Kiểm tra xem sự kiện click có được kích hoạt hay không
        toggleNotificationDropdown();
    });

    profileDropdownBtn.addEventListener("click", function () {
        toggleProfileDropdown();
    });

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

    const hamburger = document.querySelector('.hamburger');
    const wrapper = document.querySelector('.wrapper');
    const search = document.querySelector('.search');

    hamburger.addEventListener('click', function () {
        wrapper.classList.toggle('collapse');
        if (window.innerWidth < 768) {
            search.classList.toggle('show-search');
        }
    });

    let notificationCount = 5; // Example count
    const notificationCountElement = document.getElementById('notificationCount');

    if (notificationCount > 0) {
        notificationCountElement.textContent = notificationCount;
        notificationCountElement.style.display = 'inline-block';
    } else {
        notificationCountElement.style.display = 'none';
    }

    // function fetchNotifications(url) {
    //     fetch(url)
    //         .then(response => response.json())
    //         .then(data => {
    //             allNotificationCenter = data.centerNotifications;
    //             allNotificationPrivate = data.individualNotifications;
    //             allNotificationSystem = data.systemNotifications;
    //
    //             for (const notification of allNotificationSystem){
    //                 const hasSeen = await checkHasSeenSystemNotification(notification.id);
    //                 if(hasSeen){
    //                     allNotificationS
    //                 }
    //             }


                allNotifications.push(...data.individualNotifications);
                if(userRole != "PARENT"){
                    allNotifications.push(...data.centerNotifications);
                }
                allNotifications.push(...data.systemNotifications);
                allNotifications.sort((a, b) => new Date(b.createdAt) - new Date(a.createdAt));
                console.log(allNotifications)
                notificationCount = allNotifications.length;
                displayNotifications(allNotifications);
            })
            .catch(error => console.error('Error fetching notifications:', error));
    }

    async function checkHasSeenSystemNotification(systemNotificationId){
        const response = await fetch(`${apiRole}/systemNotification/${systemNotificationId}/check`);
        if (!response.ok) {
            throw new Error('Error checking system notification status');
        }
        const hasSeen = await response.json();
        return hasSeen;
    }

    function displayNotifications(notifications) {
        contentNotification.innerHTML = ''; // Clear existing notifications

        const maxDisplay = 5;
        let displayedCount = 0;
        let moreButton;

        function renderNotifications(notificationsToRender) {
            notificationsToRender.forEach(notification => {
                const notificationItem = document.createElement('div');
                notificationItem.classList.add('notification-item');

                const icon = document.createElement('img');
                icon.classList.add('bell-decor');
                icon.src = `https://cdn3d.iconscout.com/3d/premium/thumb/notifications-6162342-5034125.png?f=webp`

                const textContainer = document.createElement('div');
                textContainer.classList.add('text-container');

                const text = document.createElement('span');
                text.classList.add('notification-title');
                text.textContent = notification.title; // Adjust based on your notification structure

                const time = document.createElement('span');
                time.classList.add('notification-time');
                time.textContent = timeAgo(notification.createdAt);

                textContainer.appendChild(text);
                textContainer.appendChild(time);

                notificationItem.appendChild(icon);
                notificationItem.appendChild(textContainer);

                notificationItem.addEventListener('click', () => {
                    window.location.href = `/notification?id=${notification.id}`; // Redirect to notification details page
                });

                contentNotification.appendChild(notificationItem);
            });

            if (moreButton) {
                contentNotification.appendChild(moreButton);
            }
        }

        function loadMoreNotifications() {
            const nextBatch = notifications.slice(displayedCount, displayedCount + maxDisplay);
            renderNotifications(nextBatch);
            displayedCount += nextBatch.length;

            if (displayedCount >= notifications.length && moreButton) {
                moreButton.style.display = 'none';
            }
        }

        loadMoreNotifications(); // Load initial batch

        if (notifications.length > maxDisplay) {
            moreButton = document.createElement('div');
            moreButton.classList.add('notification-item', 'more-button');

            // Create the text element for the "More" button
            const moreText = document.createElement('span');
            moreText.textContent = 'Xem thông báo trước';

            moreButton.appendChild(moreText);

            moreButton.addEventListener('click', loadMoreNotifications);

            contentNotification.appendChild(moreButton);
        }
    }

    function timeAgo(date) {
        const now = new Date();
        const diff = now - new Date(date);
        const seconds = Math.floor(diff / 1000);
        const minutes = Math.floor(seconds / 60);
        const hours = Math.floor(minutes / 60);
        const days = Math.floor(hours / 24);
        const weeks = Math.floor(days / 7);
        const months = Math.floor(days / 30);
        const years = Math.floor(days / 365);

        if (years > 0) return `${years} year${years > 1 ? 's' : ''} ago`;
        if (months > 0) return `${months} month${months > 1 ? 's' : ''} ago`;
        if (weeks > 0) return `${weeks} week${weeks > 1 ? 's' : ''} ago`;
        if (days > 0) return `${days} day${days > 1 ? 's' : ''} ago`;
        if (hours > 0) return `${hours} hour${hours > 1 ? 's' : ''} ago`;
        if (minutes > 0) return `${minutes} minute${minutes > 1 ? 's' : ''} ago`;
        return `${seconds} second${seconds > 1 ? 's' : ''} ago`;
    }


    closeModal.addEventListener('click', () => {
        notificationModal.style.display = "none";
    });

    window.addEventListener('click', (event) => {
        if (event.target === notificationModal) {
            notificationModal.style.display = "none";
        }
    });

    const textarea = document.querySelector('.chatbox-message-input');
    const chatboxForm = document.querySelector('.chatbox-message-form');

    textarea.addEventListener('input', function () {
        let line = textarea.value.split('\n').length;

        if (textarea.rows < 6 || line < 6) {
            textarea.rows = line;
        }

        if (textarea.rows > 1) {
            chatboxForm.style.alignItems = 'flex-end';
        } else {
            chatboxForm.style.alignItems = 'center';
        }
    });

    // TOGGLE CHATBOX
    const chatboxToggle = document.querySelector('.chatbox-toggle');
    const chatboxMessage = document.querySelector('.chatbox-message-wrapper');

    if (chatboxToggle) {
        chatboxToggle.addEventListener('click', function () {
            console.log("It show"); // Check if this logs to the console
            chatboxMessage.classList.toggle('show');
        });
    } else {
        console.error("chatboxToggle element not found");
    }

    // DROPDOWN TOGGLE
    const dropdownToggle = document.querySelector('.chatbox-message-dropdown-toggle');
    const dropdownMenu = document.querySelector('.chatbox-message-dropdown-menu');

    dropdownToggle.addEventListener('click', function () {
        dropdownMenu.classList.toggle('show');
    });

    document.addEventListener('click', function (e) {
        if (!e.target.matches('.chatbox-message-dropdown, .chatbox-message-dropdown *')) {
            dropdownMenu.classList.remove('show');
        }
    });

    // CHATBOX MESSAGE
    const chatboxMessageWrapper = document.querySelector('.chatbox-message-content');
    const chatboxNoMessage = document.querySelector('.chatbox-message-no-message');

    chatboxForm.addEventListener('submit', function (e) {
        e.preventDefault();

        if (isValid(textarea.value)) {
            writeMessage();
            setTimeout(autoReply, 1000);
        }
    });

    function addZero(num) {
        return num < 10 ? '0' + num : num;
    }

    function writeMessage() {
        const today = new Date();
        let message = `
            <div class="chatbox-message-item sent">
                <span class="chatbox-message-item-text">
                    ${textarea.value.trim().replace(/\n/g, '<br>\n')}
                </span>
                <span class="chatbox-message-item-time">${addZero(today.getHours())}:${addZero(today.getMinutes())}</span>
            </div>
        `;
        chatboxMessageWrapper.insertAdjacentHTML('beforeend', message);
        chatboxForm.style.alignItems = 'center';
        textarea.rows = 1;
        textarea.focus();
        textarea.value = '';
        chatboxNoMessage.style.display = 'none';
        scrollBottom();
    }

    function autoReply() {
        const today = new Date();
        let message = `
            <div class="chatbox-message-item received">
                <span class="chatbox-message-item-text">
                    Thank you for your awesome support!
                </span>
                <span class="chatbox-message-item-time">${addZero(today.getHours())}:${addZero(today.getMinutes())}</span>
            </div>
        `;
        chatboxMessageWrapper.insertAdjacentHTML('beforeend', message);
        scrollBottom();
    }

    function scrollBottom() {
        chatboxMessageWrapper.scrollTo(0, chatboxMessageWrapper.scrollHeight);
    }

    function isValid(value) {
        let text = value.replace(/\n/g, '');
        text = text.replace(/\s/g, '');

        return text.length > 0;
    }
    // End chat box

});
$(document).ready(function() {
    $('#search-input').on('input', function() {
        var keyword = $(this).val();
        if (keyword.length >= 2) {
            $.ajax({
                url: '/api/students/search',
                type: 'GET',
                data: { keyword: keyword },
                success: function(response) {
                    var dropdown = '';
                    response.slice(0, 5).forEach(function(item) {
                        var detailUrl = (item.type === 'Course') ? `/courseDetail?courseId=${item.id}` : `/centerDetail?centerId=${item.id}`;
                        dropdown += `<a href="javascript:void(0);" class="search-item" data-url="${detailUrl}">${item.type}: ${item.name}</a>`;
                    });
                    $('#search-results').html(dropdown).show();

                    // Thêm sự kiện click cho mỗi kết quả tìm kiếm
                    $('.search-item').click(function() {
                        var url = $(this).data('url');
                        sessionStorage.setItem('searchDetails', $(this).text());
                        window.location.href = url;
                    });
                },
                error: function(error) {
                    console.error('Error fetching search results:', error);
                }
            });
        } else {
            $('#search-results').hide();
        }
    });

    // Ẩn kết quả tìm kiếm khi nhấn vào bất kỳ nơi nào bên ngoài
    $(document).click(function(event) {
        if (!$(event.target).closest('.search').length) {
            $('#search-results').hide();
        }
    });
});


