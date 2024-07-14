let notificationCount = 0;
const notificationCountElement = document.getElementById('notificationCount');

document.addEventListener("DOMContentLoaded", async function () {
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

    await fetchNotifications(apiNotificationUrlGet);

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
            allNotifications = [];
            allNotificationsUnseen = [];
            allNotificationSeen = [];
            allNotificationSystem = [];
            allNotificationSystemUnseen = [];
            allNotificationSystemSeen = [];
            allNotificationCenter = [];
            allNotificationCenterSeen = [];
            allNotificationCenterUnseen = [];
            allNotificationPrivate = [];
            allNotificationPrivateUnseen = [];
            allNotificationPrivateSeen = [];
            fetchNotifications(apiNotificationUrlGet);
        }
        profileDropdownList.classList.remove("active");
    }

    bell.addEventListener("click", function () {
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



    async function fetchNotifications(url) {
        try {
            const response = await fetch(url);
            const data = await response.json();

            if (userRole !== "PARENT") {
                allNotifications.push(...data.centerNotifications);
                allNotificationCenter = data.centerNotifications;

                const centerNotificationPromises = allNotificationCenter.map(async (notification) => {
                    const hasSeen = await checkHasSeenCenterNotification(notification.id);
                    if (hasSeen) {
                        allNotificationCenterSeen.push(notification);
                        allNotificationSeen.push(notification);
                    } else {
                        allNotificationCenterUnseen.push(notification);
                        allNotificationsUnseen.push(notification);
                    }
                });
                await Promise.all(centerNotificationPromises);
            }

            allNotificationPrivate = data.individualNotifications;
            allNotificationPrivateSeen = data.individualNotifications.filter(notification => notification.hasSeen === true);
            allNotificationPrivateUnseen = data.individualNotifications.filter(notification => notification.hasSeen !== true);

            allNotificationSystem = data.systemNotifications;

            const systemNotificationPromises = allNotificationSystem.map(async (notification) => {
                const hasSeen = await checkHasSeenSystemNotification(notification.id);
                if (hasSeen) {
                    allNotificationSystemSeen.push(notification);
                    allNotificationSeen.push(notification);
                } else {
                    allNotificationSystemUnseen.push(notification);
                    allNotificationsUnseen.push(notification);
                }
            });
            await Promise.all(systemNotificationPromises);

            allNotificationSeen.push(...allNotificationPrivateSeen);
            allNotificationsUnseen.push(...allNotificationPrivateUnseen);

            notificationCount = allNotificationsUnseen.length;

            if (notificationCount > 0) {
                notificationCountElement.textContent = notificationCount;
                notificationCountElement.style.display = 'inline-block';
            } else {
                notificationCountElement.style.display = 'none';
            }

            allNotifications.push(...data.individualNotifications);
            allNotifications.push(...data.systemNotifications);

            const allNotificaitonSort = allNotifications.sort((a, b) => parseStringToDate(b.createdAt) - parseStringToDate(a.createdAt));
            displayNotifications(allNotificaitonSort);
        } catch (error) {
            console.error('Error fetching notifications:', error);
        }
    }

    function parseStringToDate(dateString) {
        const [datePart, timePart] = dateString.split(' ');
        const [day, month, year] = datePart.split('-');
        const [hours, minutes, seconds] = timePart.split(':');

        // JavaScript uses month index 0-11, so we subtract 1 from the month
        return new Date(year, month - 1, day, hours, minutes, seconds);
    }

    allNotifications.sort((a, b) => parseStringToDate(b.createdAt) - parseStringToDate(a.createdAt));



    async function checkHasSeenCenterNotification(centerNotificationId) {
        const response = await fetch(`${apiRole}/centerNotification/${centerNotificationId}/check`);
        if (!response.ok) {
            throw new Error('Error checking center notification status');
        }
        const hasSeen = await response.json();
        return hasSeen;
    }

    async function checkHasSeenSystemNotification(systemNotificationId) {
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
                let systemNotificationUnSeen;
                let centerNotificationUnSeen;

                if (notification.center) {
                    notificationItem.classList.add('notification-center');
                    const isSeen = allNotificationCenterSeen.some(seenNotification => seenNotification.id === notification.id);
                    if (isSeen) {
                        notificationItem.classList.add('center-seen');
                    } else {
                        notificationItem.classList.add('center-unseen');
                        centerNotificationUnSeen = notification;
                    }
                } else if (notification.actor) {
                    notificationItem.classList.add('notification-private');
                    if (notification.hasSeen) {
                        notificationItem.classList.add('private-seen');
                    } else {
                        notificationItem.classList.add('private-unseen');
                    }
                } else {
                    notificationItem.classList.add('notification-system');
                    const isSeen = allNotificationSystemSeen.some(seenNotification => seenNotification.id === notification.id);
                    if (isSeen) {
                        notificationItem.classList.add('system-seen');
                    } else {
                        notificationItem.classList.add('system-unseen');
                        systemNotificationUnSeen = notification;
                    }
                }

                const icon = document.createElement('img');
                icon.classList.add('bell-decor');
                icon.src = `https://cdn3d.iconscout.com/3d/premium/thumb/notifications-6162342-5034125.png?f=webp`;

                const textContainer = document.createElement('div');
                textContainer.classList.add('text-container');

                const text = document.createElement('span');
                text.classList.add('notification-title');
                text.textContent = notification.title;

                const time = document.createElement('span');
                time.classList.add('notification-time');
                time.textContent = timeAgo(notification.createdAt);

                textContainer.appendChild(text);
                textContainer.appendChild(time);

                notificationItem.appendChild(icon);
                notificationItem.appendChild(textContainer);

                notificationItem.addEventListener('click', async () => {
                    try {
                        let response;
                        if (systemNotificationUnSeen) {
                            response = await fetch(`${apiRole}/viewSystemNotification/update/${systemNotificationUnSeen.id}`, { method: 'POST' });
                            if (response.ok) {
                                notificationItem.classList.remove('system-unseen');
                                notificationItem.classList.add('system-seen');
                            }
                        } else if (centerNotificationUnSeen) {
                            response = await fetch(`${apiRole}/viewCenterNotification/update/${centerNotificationUnSeen.id}`, { method: 'POST' });
                            if (response.ok) {
                                notificationItem.classList.remove('center-unseen');
                                notificationItem.classList.add('center-seen');
                            }
                        } else {
                            response = await fetch(`${apiRole}/individualNotification/update/${notification.id}`, { method: 'PATCH' });
                            if (response.ok) {
                                notificationItem.classList.remove('private-unseen');
                                notificationItem.classList.add('private-seen');
                            }
                        }

                        if (!response.ok) {
                            throw new Error('Error updating notification status');
                        }

                        // Redirect after updating
                        window.location.href = `/notification?id=${notification.id}`;
                    } catch (error) {
                        console.error('Error updating notifications:', error);
                    }
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

    function parseDateString(dateString) {
        const parts = dateString.split(' ');
        const dateParts = parts[0].split('-');
        const timeParts = parts[1].split(':');

        const day = dateParts[0];
        const month = dateParts[1];
        const year = dateParts[2];
        const hours = timeParts[0];
        const minutes = timeParts[1];
        const seconds = timeParts[2];

        return `${year}-${month}-${day}T${hours}:${minutes}:${seconds}`;
    }


    function timeAgo(date) {
        const parsedDate = parseDateString(date);
        const now = new Date();
        const diff = now - new Date(parsedDate);
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

    function showToast(message) {
        const toastContainer = document.getElementById("toastContainer");
        toastContainer.innerHTML = `${message}`;
        toastContainer.classList.add("show");
        setTimeout(() => {
            toastContainer.classList.remove("show");
        }, 3000);
    }

});
$(document).ready(function() {
    function performSearch() {
        var keyword = $('#search-input').val();
        var searchType = $('#searchType').val();
        if (keyword.length >= 2) {
            $.ajax({
                url: '/api/student/searchh',
                type: 'GET',
                data: {
                    keyword: keyword,
                    searchType: searchType
                },
                success: function(response) {
                    var dropdown = '';
                    response.slice(0, 5).forEach(function(item) {
                        var detailUrl = (item.type === 'Course') ? `/searchDetail?courseId=${item.id}&centerId=${item.centerId}` : `/searchDetail?centerId=${item.id}`;
                        if(searchType == "all" ||
                            (searchType == "course" && item.type == "Course") ||
                            (searchType == "center" && item.type == "Center")){
                            dropdown += `<a href="javascript:void(0);" class="search-item" data-url="${detailUrl}">${item.type}: ${item.name}</a>`;
                        }
                    });
                    $('#search-results').html(dropdown).show();

                    $('.search-item').click(function() {
                        var url = $(this).data('url');
                        sessionStorage.setItem('searchDetails', $(this).text());
                        window.location.href = url;
                    });

                    $('.search').addClass('open');
                },
                error: function(error) {
                    console.error('Error fetching search results:', error);
                }
            });
        } else {
            $('#search-results').hide();
            $('.search').removeClass('open');
        }
    }

    $('#search-input').on('input', performSearch);
    $('#searchType').on('change', performSearch);

    $(document).click(function(event) {
        if (!$(event.target).closest('.search').length) {
            $('#search-results').hide();
            $('.search').removeClass('open');
        }
    });
});