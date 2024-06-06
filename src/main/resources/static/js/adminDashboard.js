document.addEventListener("DOMContentLoaded", function () {
    var toast = document.getElementById("toast");
    var toastInfo = document.getElementById("toast-info");
    var createNotificationBtn = document.getElementById("createNotificationBtn");
    var createNotificationModal = document.getElementById("createNotificationModal");
    var closeButton = createNotificationModal.querySelector(".close");
    var notificationForm = document.getElementById("notificationForm");
    var notificationTypeSelect = notificationForm.querySelector("select");
    var publicNotificationBtn = document.getElementById("publicNotificationBtn");
    var privateNotificationBtn = document.getElementById("privateNotificationBtn");
    var currentPage = 1;
    var itemsPerPage = 5;
    var modal = document.getElementById('viewNotificationModal');
    var viewNotificationTitle = modal.querySelector('#viewNotificationTitle');
    var viewNotificationContent = modal.querySelector('#viewNotificationContent');
    var viewNotificationCreatedAt = modal.querySelector('#viewNotificationCreatedAt');
    var notificationTableBody = document.getElementById("notificationTableBody");
    var allPublicNotifications = [];
    var paginationControls = document.getElementById("paginationControls"); // Define paginationControls variable

    publicNotificationBtn.addEventListener("click", function () {
        fetchAndDisplayPublicNotifications();
    });

    function fetchAndDisplayPublicNotifications() {
        fetch("/admin-publicNotifications")
            .then(response => response.json())
            .then(data => {
                allPublicNotifications = data;
                currentPage = 1;
                renderTable(allPublicNotifications);
            })
            .catch(error => console.error("Error fetching public notifications:", error));
    }

    function renderTable(notificationsList) {
        const start = (currentPage - 1) * itemsPerPage;
        const end = start + itemsPerPage;
        const postsToDisplay = notificationsList.slice(start, end);

        displayNotifications(postsToDisplay);
        renderPaginationControls();
    }

    function displayNotifications(notifications) {
        notificationTableBody.innerHTML = "";

        notifications.forEach(function (notification) {
            var row = `
            <tr id="${notification.id}">
                <td>${notification.title}</td>
                <td>${new Date(notification.createdAt).toLocaleDateString('en-GB')}</td>
                <td><button class="view-details" data-title="${notification.title}" data-content="${notification.content}" data-createdat="${notification.createdAt}">Xem</button></td>
                <td>
                    <i class="fas fa-edit" data-id="${notification.id}"></i>
                    <i class="fas fa-trash" data-id="${notification.id}"></i>
                </td>
            </tr>
        `;
            notificationTableBody.insertAdjacentHTML("beforeend", row);
        });
    }

    function displayNotificationDetails(title, content, createdAt) {
        viewNotificationTitle.textContent = title;
        viewNotificationContent.textContent = content;
        viewNotificationCreatedAt.textContent = new Date(createdAt).toLocaleDateString('en-GB');
        modal.style.display = "block";
    }

    function renderPaginationControls() {
        const totalPages = Math.ceil(allPublicNotifications.length / itemsPerPage);
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
                renderTable(allPublicNotifications); // Pass allPublicNotifications array as argument
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

    function displayDeleteModal(notificationId, notificationTitle) {
        var deleteModal = document.getElementById("deleteModal");
        var deleteConfirmBtn = deleteModal.querySelector("#confirmDelete");
        var deleteCancelBtn = deleteModal.querySelector("#cancelDelete");
        var userName = deleteModal.querySelector("#userName");
        userName.textContent = notificationTitle;
        deleteModal.style.display = "block";

        deleteConfirmBtn.addEventListener("click", function () {
            fetch(`/admin-publicNotification/delete/${notificationId}`, {
                method: "DELETE"
            })
                .then(function (response) {
                    if (!response.ok) {
                        throw new Error("Network response was not ok");
                    }
                    toastInfo.innerHTML = '<i class="fas fa-check-circle"></i> Notification deleted successfully';
                    toast.style.backgroundColor = "#4caf50";
                    toast.style.display = "block";
                    setTimeout(function () {
                        toast.style.display = "none";
                    }, 3000);
                    var row = document.getElementById(notificationId);
                    if (row) {
                        row.remove();
                    }
                })
                .catch(function (error) {
                    console.error("Error deleting notification:", error);
                    toastInfo.innerHTML = '<i class="fas fa-times-circle"></i> Notification deletion failed';
                    toast.style.backgroundColor = "red";
                    toast.style.display = "block";
                    setTimeout(function () {
                        toast.style.display = "none";
                    }, 3000);
                });

            deleteModal.style.display = "none";
        });

        deleteCancelBtn.addEventListener("click", function () {
            deleteModal.style.display = "none";
        });
    }

    notificationTableBody.addEventListener("click", function (event) {
        if (event.target.classList.contains("fa-trash")) {
            var notificationId = event.target.getAttribute("data-id");
            var notificationTitle = event.target.closest("tr").querySelector("td:first-of-type").textContent;
            displayDeleteModal(notificationId, notificationTitle);
        }

        if (event.target.classList.contains("fa-edit")) {
            var notificationId = event.target.getAttribute("data-id");
            var notificationRow = event.target.closest("tr");
            var notification = {
                id: notificationId,
                title: notificationRow.querySelector("td:first-of-type").textContent,
                content: notificationRow.querySelector(".view-details").getAttribute("data-content"),
            };
            displayEditModal(notification);
        }

        if (event.target.classList.contains("view-details")) {
            var title = event.target.getAttribute("data-title");
            var content = event.target.getAttribute("data-content");
            var createdAt = event.target.getAttribute("data-createdat");
            displayNotificationDetails(title, content, createdAt);
        }
    });

    function displayEditModal(notification) {
        var editModal = document.getElementById("editModal");
        var editConfirmBtn = editModal.querySelector("#confirmEdit");
        var editCancelBtn = editModal.querySelector("#cancelEdit");
        var editTitle = editModal.querySelector("#editTitle");
        var editContent = editModal.querySelector("#editContent");

        editTitle.value = notification.title;
        editContent.value = notification.content;

        editModal.style.display = "block";

        editConfirmBtn.addEventListener("click", function () {
            var updatedNotification = {
                title: editTitle.value,
                content: editContent.value,
            };
            console.log(`/admin-publicNotification/update/${notification.id}`)
            fetch(`/admin-publicNotification/update/${notification.id}`, {
                method: "PUT",
                headers: {
                    "Content-Type": "application/json"
                },
                body: JSON.stringify(updatedNotification)
            })
                .then(function (response) {
                    if (!response.ok) {
                        throw new Error("Network response was not ok");
                    }
                    toastInfo.innerHTML = '<i class="fas fa-check-circle"></i> Notification updated successfully';
                    toast.style.backgroundColor = "#4caf50";
                    toast.style.display = "block";
                    setTimeout(function () {
                        toast.style.display = "none";
                    }, 3000);
                    return response.json();
                })
                .then(function (data) {
                    console.log("Notification updated successfully:", data);
                    var row = document.getElementById(notification.id);
                    if (row) {
                        row.querySelector("td:first-of-type").textContent = updatedNotification.title;
                        row.querySelector(".view-details").setAttribute("data-content", updatedNotification.content);
                    }
                })
                .catch(function (error) {
                    console.error("Error updating notification:", error);
                });

            editModal.style.display = "none";
        });

        editCancelBtn.addEventListener("click", function () {
            editModal.style.display = "none";
        });

        window.addEventListener('click', function (event) {
            if (event.target == editModal) {
                editModal.style.display = "none";
            }
        });
    }

    // Modal create notification
    createNotificationBtn.addEventListener("click", function () {
        createNotificationModal.style.display = "block";
    });

    closeButton.addEventListener("click", function () {
        createNotificationModal.style.display = "none";
    });

    window.addEventListener("click", function (event) {
        if (event.target == createNotificationModal) {
            createNotificationModal.style.display = "none";
        }
    });

    notificationForm.addEventListener("submit", function (event) {
        event.preventDefault();

        var notificationData = {
            title: notificationForm.querySelector("#notificationTitle").value,
            content: notificationForm.querySelector("#notificationContent").value,
            createdAt: new Date().toISOString()
        };

        var endpoint;

        if (notificationTypeSelect.value === "private") {
            endpoint = "/admin-privateNotification/create";
        }else{
            endpoint = "/admin-publicNotification/create"
        }

        fetch(endpoint, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(notificationData)
        })
            .then(function (response) {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                toastInfo.innerHTML = '<i class="fas fa-check-circle"></i> Notification created successfully';
                toast.style.backgroundColor = "#4caf50";
                toast.style.display = "block";
                setTimeout(function () {
                    toast.style.display = "none";
                }, 3000);
                return response.json();
            })
            .then(function (data) {
                console.log("Notification created successfully:", data);
                var newNotificationRow = `
                <tr id="${data.id}">
                    <td>${data.title}</td>
                    <td>${new Date(data.createdAt).toLocaleDateString('en-GB')}</td>
                    <td><button class="view-details" data-title="${data.title}" data-content="${data.content}" data-createdat="${data.createdAt}">Xem</button></td>
                    <td>
                        <i class="fas fa-edit" data-id="${data.id}"></i>
                        <i class="fas fa-trash" data-id="${data.id}"></i>
                    </td>
                </tr>
                `;
                notificationTableBody.insertAdjacentHTML("beforeend", newNotificationRow);
            })
            .catch(function (error) {
                console.error("Error creating notification:", error);
            });

        createNotificationModal.style.display = "none";
    });

});
