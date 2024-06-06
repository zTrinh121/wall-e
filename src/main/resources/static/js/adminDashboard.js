// Define toast and toastInfo in the global scope
var toast = document.getElementById("toast");
var toastInfo = document.getElementById("toast-info");

//Start: Create notification form
function toggleSendToField() {
    var notificationTypeSelect = document.getElementById("notificationType");
    var notificationType = notificationTypeSelect.value; // Lấy giá trị của select

    var sendToInput = document.getElementById("notificationSendTo");
    var sendToLabel = document.getElementById("notifcationLabelSendTo");

    if (notificationType === "private") {
        sendToInput.style.display = "block";
        sendToLabel.style.display = "block";
        sendToInput.setAttribute("required", true);
    } else {
        sendToInput.style.display = "none";
        sendToLabel.style.display = "none";
        sendToInput.removeAttribute("required");
    }
}

//End: Create notification form

//Start: Modal create/delete/pdate notification
document.addEventListener("DOMContentLoaded", function () {
    var createNotificationBtn = document.getElementById("createNotificationBtn");
    var createNotificationModal = document.getElementById("createNotificationModal");
    var closeButton = createNotificationModal.querySelector(".close");
    var notificationForm = document.getElementById("notificationForm");
    var notificationTypeSelect = notificationForm.querySelector("select");

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

        var endpoint = "/admin-publicNotification/create";

        if (notificationTypeSelect.value === "private") {
            endpoint = "/admin-privateNotification/create";
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
                }, 3000); // ẩn toast sau 3 giây
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
//End: Modal create notification

//Start: Button notification public/private
document.addEventListener("DOMContentLoaded", function () {
    var currentPage = 0;
    var itemsPerPage = 10;
    var modal = document.getElementById('viewNotificationModal');
    var closeBtnNotification = modal.querySelector('.close');
    var viewNotificationTitle = modal.querySelector('#viewNotificationTitle');
    var viewNotificationContent = modal.querySelector('#viewNotificationContent');
    var viewNotificationCreatedAt = modal.querySelector('#viewNotificationCreatedAt');
    var notificationTableBody = document.getElementById("notificationTableBody");

    function fetchNotifications(page) {
        var url = `/admin-publicNotificationsPage?page=${page}&size=${itemsPerPage}`;
        fetch(url)
            .then(function (response) {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.json();
            })
            .then(function (data) {
                var totalPages = data.totalPages;
                document.getElementById("totalPages").textContent = totalPages;
                if (currentPage >= totalPages - 1) {
                    document.getElementById("nextPageBtn").disabled = true;
                } else {
                    document.getElementById("nextPageBtn").disabled = false;
                }
                // Hiển thị dữ liệu lên giao diện
                displayNotifications(data.content);
                document.getElementById("currentPage").textContent = data.number + 1;
            })
            .catch(function (error) {
                console.error("Error fetching notifications:", error);
            });
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
        // Set the title, content, and created date of the notification in the modal
        viewNotificationTitle.textContent = title;
        viewNotificationContent.textContent = content;
        viewNotificationCreatedAt.textContent = new Date(createdAt).toLocaleDateString('en-GB');
        // Display the modal
        modal.style.display = "block";
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

        deleteConfirmBtn.addEventListener("click", function() {
            // Gọi API để xóa thông báo và hiển thị toast sau khi xóa thành công
            fetch(`/admin-publicNotification/delete/${notificationId}`, {
                method: "DELETE"
            })
                .then(function(response) {
                    if (!response.ok) {
                        throw new Error("Network response was not ok");
                    }
                    toastInfo.innerHTML = '<i class="fas fa-check-circle"></i> Notification deleted successfully';
                    toast.style.backgroundColor = "#4caf50";
                    toast.style.display = "block";
                    setTimeout(function () {
                        toast.style.display = "none";
                    }, 3000); // ẩn toast sau 3 giây
                    // Remove the notification row from the table
                    var row = document.getElementById(notificationId);
                    if (row) {
                        row.remove();
                    }
                })
                .catch(function(error) {
                    console.error("Error deleting notification:", error);
                    toastInfo.innerHTML = '<i class="fas fa-times-circle"></i> Notification deletion failed';
                    toast.style.backgroundColor = "red";
                    toast.style.display = "block";
                    setTimeout(function () {
                        toast.style.display = "none";
                    }, 3000); // ẩn toast sau 3 giây
                });

            // Đóng modal sau khi xóa
            deleteModal.style.display = "none";
        });

        deleteCancelBtn.addEventListener("click", function() {
            deleteModal.style.display = "none";
        });
    }

    notificationTableBody.addEventListener("click", function(event) {
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

    //Edit notification
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
                    // Hiển thị toast
                    toastInfo.innerHTML = '<i class="fas fa-check-circle"></i> Notification updated successfully';
                    toast.style.backgroundColor = "#4caf50";
                    toast.style.display = "block";
                    setTimeout(function () {
                        toast.style.display = "none";
                    }, 3000); // ẩn toast sau 3 giây
                    return response.json();
                })
                .then(function (data) {
                    console.log("Notification updated successfully:", data);
                    // Update the notification in the table
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

    document.getElementById("prevPageBtn").addEventListener("click", function () {
        if (currentPage >= 1) {
            currentPage--;
            fetchNotifications(currentPage);
        }
    });

    document.getElementById("nextPageBtn").addEventListener("click", function () {
        currentPage++;
        fetchNotifications(currentPage);
    });

    fetchNotifications(currentPage);
});
//End: Button notification public/private
