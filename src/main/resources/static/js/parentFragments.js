
// Start: Drop down
document.addEventListener("DOMContentLoaded", function () {
    const bell = document.getElementById("bell");
    const contentNotification = document.querySelector(".content-notification");
    const profileDropdownList = document.querySelector(".profile-dropdown-list");
    const profileDropdownBtn = document.querySelector(".profile-dropdown-btn");
    const notificationModal = document.getElementById("notificationModal");
    const notificationTitle = document.getElementById("notificationTitle");
    const notificationDetails = document.getElementById("notificationDetails");
    const closeModal = document.getElementById("viewNotificationModalClose");

    function toggleProfileDropdown() {
        profileDropdownList.classList.toggle("active");
        contentNotification.style.display = "none"; // Hide notification dropdown
    }

    function toggleNotificationDropdown() {
        if (contentNotification.style.display === "block") {
            contentNotification.style.display = "none";
        } else {
            contentNotification.style.display = "block";
            fetchNotifications();
        }
        profileDropdownList.classList.remove("active");
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

    const hamburger = document.querySelector('.hamburger');
    const wrapper = document.querySelector('.wrapper');
    const search = document.querySelector('.search');

    hamburger.addEventListener('click', function () {
        wrapper.classList.toggle('collapse');
        if (window.innerWidth < 768) {
            search.classList.toggle('show-search');
        }
    });

    function fetchNotifications() {
        fetch('api/teachers/notifications/system')
            .then(response => response.json())
            .then(data => {

                displayNotifications(data); // Assuming response contains notifications array
            })
            .catch(error => console.error('Error fetching notifications:', error));
    }

    function displayNotifications(notifications) {
        contentNotification.innerHTML = ''; // Clear existing notifications

        const maxDisplay = 5;
        const displayedNotifications = notifications.slice(0, maxDisplay);

        displayedNotifications.forEach(notification => {
            const notificationItem = document.createElement('div');
            notificationItem.classList.add('notification-item');
            notificationItem.textContent = notification.title; // Adjust based on your notification structure
            notificationItem.addEventListener('click', () => {
                showNotificationDetails(notification);
            });
            contentNotification.appendChild(notificationItem);
        });

        if (notifications.length > maxDisplay) {
            const moreButton = document.createElement('div');
            moreButton.classList.add('notification-item', 'more-button');
            moreButton.textContent = 'More';
            moreButton.addEventListener('click', () => {
                window.location.href = '/parent-notification'; // Replace with the actual URL of your notifications list page
            });
            contentNotification.appendChild(moreButton);
        }
    }

    function showNotificationDetails(notification) {
        notificationTitle.textContent = notification.title;
        notificationDetails.textContent = notification.content; // Adjust based on your notification structure
        notificationModal.style.display = "block";
    }

    closeModal.addEventListener('click', () => {
        notificationModal.style.display = "none";
    });

    window.addEventListener('click', (event) => {
        if (event.target === notificationModal) {
            notificationModal.style.display = "none";
        }
    });

    // TOGGLE CHATBOX
    const chatboxToggle = document.querySelector('.chatbox-toggle');
    const chatboxMessage = document.querySelector('.chatbox-message-wrapper');

    if (chatboxToggle) {
        console.log("chatboxToggle element found:", chatboxToggle);
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

    // Start chat box
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
