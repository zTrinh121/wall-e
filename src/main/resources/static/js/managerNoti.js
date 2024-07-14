document.addEventListener("DOMContentLoaded", () => {
    var publicNotificationBtn = document.getElementById("publicNotificationBtn");
    var privateNotificationBtn = document.getElementById("privateNotificationBtn");
    var currentPage = 1;
    var itemsPerPage = 5;
    var modal = document.getElementById('viewNotificationModal');
    var viewNotificationTitle = modal.querySelector('#viewNotificationTitle');
    var viewNotificationCenter = modal.querySelector('#viewNotificationCenter');
    var viewNotificationContent = modal.querySelector('#viewNotificationContent');
    var viewNotificationCreatedAt = modal.querySelector('#viewNotificationCreatedAt');
    var viewNotificationSendTo = modal.querySelector('#viewNotificationSendTo');
    var notificationTableBody = document.getElementById("notificationTableBody");
    var allPublicNotifications = [];
    var allPrivateNotifications = [];
    var paginationControls = document.getElementById("paginationControls");
    const userCode = document.getElementById("userId");

    publicNotificationBtn.addEventListener("click", function () {
        fetchAndDisplayPublicNotifications();
    });

    privateNotificationBtn.addEventListener("click", function () {
        fetchAndDisplayPrivateNotifications();
    });


    function fetchAndDisplayPublicNotifications() {
        Promise.all([
            fetch("/api/teachers/notifications/system").then(response => response.json()),
            fetch("/api/teachers/notifications/public").then(response => response.json())
        ])
            .then(data => {
                allPublicNotifications = data[0].concat(data[1]);
                allPublicNotifications.sort((a, b) => {
                    let dateA = new Date(a.createdAt);
                    let dateB = new Date(b.createdAt);
                    if (dateA < dateB) return 1;
                    if (dateA > dateB) return -1;
                    return a.id - b.id;
                });
                currentPage = 1; // Reset to first page
                renderTable(allPublicNotifications);
            })
            .catch(error => console.error("Error fetching notifications:", error));
    }

    function fetchAndDisplayPrivateNotifications() {
        fetch(`/api/student/private-notifications/${userCode.innerHTML}`)
            .then(response => response.json())
            .then(data => {
                allPrivateNotifications = data;
                currentPage = 1;
                renderTable(allPrivateNotifications);
            })
            .catch(error => console.error("Error fetching private notifications:", error));
    }


    function renderTable(notificationsList) {
        const start = (currentPage - 1) * itemsPerPage;
        const end = start + itemsPerPage;
        const postsToDisplay = notificationsList.slice(start, end);
        displayNotifications(postsToDisplay);
        renderPaginationControls(notificationsList);
    }

    function displayNotifications(notifications) {
        notificationTableBody.innerHTML = "";
        var tableHeader = document.querySelector("#notificationTable thead tr");

        var isPrivate = notifications.length && (notifications[0].centerSendTo || notifications[0].userSendTo);
        var isCenter = notifications.length && (notifications[0].center);
        if (isPrivate) {
            tableHeader.innerHTML = `
            <th>Tiêu đề</th>
            <th>Ngày tạo</th>
            <th>Gửi tới</th>
            <th>Chi tiết</th>
        `;
        } else {
            tableHeader.innerHTML = `
            <th>Tiêu đề</th>
            <th>Ngày tạo</th>
            <th>Phân loại</th>
            <th>Chi tiết</th>
        `;
        }
        console.log(notifications)

        notifications.forEach(function (notification) {
            var row = `
            <tr id="${notification.id}">
                <td>${notification.title}</td>
                <td>${new Date(notification.createdAt).toLocaleDateString('en-GB')}</td>
                ${isPrivate ? `<td>${notification.centerSendTo ? notification.centerSendTo.code : notification.userSendTo.code}</td>` : ''}
                ${isCenter ? `<td>Trung tâm</td>`:`<td>Hệ thống</td>`}
                <td><button class="view-details" data-title="${notification.title}" data-content="${notification.content}" data-createdat="${notification.createdAt}" ${isCenter ? `data-center="${notification.center.name}"` : ``} ${isPrivate ? `data-sendto="${notification.centerSendTo ? notification.centerSendTo.code : notification.userSendTo.code}"` : ''}>Xem</button></td>
            </tr>
        `;
            notificationTableBody.insertAdjacentHTML("beforeend", row);
        });
    }

    function displayNotificationDetails(title, content, createdAt, sendTo, center) {
        viewNotificationTitle.textContent = "Tiêu đề: " +title;
        viewNotificationContent.textContent = "Nội dung: " + content;
        viewNotificationCreatedAt.textContent = "Ngày tạo: " + new Date(createdAt).toLocaleDateString('en-GB');

        if(sendTo !== null){
            viewNotificationSendTo.style.display = "block";
            viewNotificationSendTo.textContent = "Gửi đến " + sendTo;
        }
        else viewNotificationSendTo.style.display = "none"
        console.log(center)
        if(center !== null){
            viewNotificationCenter.style.display = "block";
            viewNotificationCenter.textContent = "Thông báo từ trung tâm " + center;
        }else viewNotificationCenter.textContent = "Thông báo từ hệ thống";
        modal.style.display = "block";
    }

    notificationTableBody.addEventListener("click", function (event) {
        if (event.target.classList.contains("fa-trash")) {
            var notificationId = event.target.getAttribute("data-id");
            var notificationRow = event.target.closest("tr");
            var notification = {
                id: notificationId,
                title: notificationRow.querySelector("td:first-of-type").textContent,
                content: notificationRow.querySelector(".view-details").getAttribute("data-content"),
                sendTo: notificationRow.querySelector(".view-details").getAttribute("data-sendto"),
                center: notificationRow.querySelector(".view-details").getAttribute("data-center")
            };
            console.log(notification)
        }

        if (event.target.classList.contains("view-details")) {
            var title = event.target.getAttribute("data-title");
            var content = event.target.getAttribute("data-content");
            var createdAt = event.target.getAttribute("data-createdat");
            var sendTo = event.target.getAttribute("data-sendto");
            var center = event.target.getAttribute("data-center");
            displayNotificationDetails(title, content, createdAt, sendTo, center);
        }
    });

    function renderPaginationControls(notificationsList) {
        const totalPages = Math.ceil(notificationsList.length / itemsPerPage);
        paginationControls.innerHTML = '';

        for (let i = 1; i <= totalPages; i++) {
            const pageButton = document.createElement('button');
            pageButton.textContent = i;
            pageButton.classList.add('page-button');
            if (i === currentPage) {
                pageButton.classList.add('active');
            }
            pageButton.addEventListener('click', () => {
                currentPage = i;
                renderTable(notificationsList);
            });
            paginationControls.appendChild(pageButton);
        }
    }

    document.getElementById('closeViewNotification').addEventListener('click', function () {
        modal.style.display = "none";
    });

    window.addEventListener('click', function (event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    });

});
//test
function fetchAndDisplayCenterNotifications() {
    fetch("/manager/centerNotifications")
        .then(response => response.json())
        .then(data => {
            renderTable(data);
        })
        .catch(error => console.error("Error fetching center notifications:", error));
}

function fetchAndDisplayCenterNotificationsByCenterId(centerId) {
    fetch(`/manager/centerNotifications/center/${centerId}`)
        .then(response => response.json())
        .then(data => {
            renderTable(data); // Render function to display center notifications
        })
        .catch(error => console.error(`Error fetching center notifications for centerId ${centerId}:`, error));
}
function renderTable(notificationsList) {
    notificationsList.forEach(function(notification) {
        var row = `
            <tr id="${notification.id}">
                <td>${notification.title}</td>
                <td>${new Date(notification.createdAt).toLocaleDateString('en-GB')}</td>
                <td>${notification.someField}</td> <!-- Example field specific to CenterNotification -->
                <td><button class="view-details" data-title="${notification.title}" data-content="${notification.content}" data-createdat="${notification.createdAt}">View</button></td>
                <td>
                    <i class="fas fa-edit" data-id="${notification.id}"></i>
                    <i class="fas fa-trash" data-id="${notification.id}"></i>
                </td>
            </tr>
        `;
        notificationTableBody.insertAdjacentHTML("beforeend", row);
    });
}
function createCenterNotification(data) {
    fetch("/manager/centerNotification/create", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(data)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Network response was not ok");
        }
        return response.json();
    })
    .then(data => {
        showToast("Center notification created successfully!", "#4caf50", "check-circle");
        fetchAndDisplayCenterNotifications(); // Refresh the list after creation
    })
    .catch(error => {
        console.error("Error creating center notification:", error);
        showToast("Failed to create center notification.", "red", "times-circle");
    });
}
function deleteCenterNotification(notificationId) {
    fetch(`/manager/delete/${notificationId}`, {
        method: "DELETE"
    })
    .then(response => {
        if (!response.ok) {
            throw new Error("Network response was not ok");
        }
        showToast("Center notification deleted successfully!", "#4caf50", "check-circle");
        var row = document.getElementById(notificationId);
        if (row) {
            row.remove();
        }
    })
    .catch(error => {
        console.error("Error deleting center notification:", error);
        showToast("Failed to delete center notification.", "red", "times-circle");
    });
}

createNotificationBtn.addEventListener("click", function () {
    var formData = {
        title: "vbcbcv",
        content: "zxcxz",
    };
    createCenterNotification(formData);
});

notificationTableBody.addEventListener("click", function (event) {
    if (event.target.classList.contains("fa-trash")) {
        var notificationId = event.target.getAttribute("data-id");
        deleteCenterNotification(notificationId);
    }
});

