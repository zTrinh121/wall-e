$(document).ready(function () {
    // Start delete modal
    const deleteUserLinks = $('.delete-user');
    const modalde = $('#deleteModal');
    const userNameSpan = $('#userName');
    const confirmDeleteButton = $('#confirmDelete');
    const cancelDeleteButton = $('#cancelDelete');
    const toast = $('#toast');
    let currentUserRow;

    deleteUserLinks.on('click', function (event) {
        event.preventDefault();
        currentUserRow = $(this).closest('tr');
        const title = currentUserRow.find('td p').first().text();
        userNameSpan.text(title);
        modalde.show();
    });

    confirmDeleteButton.on('click', function () {
        currentUserRow.remove();
        modalde.hide();
        showToast("Xóa thành công!");
    });

    cancelDeleteButton.on('click', function () {
        modalde.hide();
    });

    $(window).on('click', function (event) {
        if ($(event.target).is(modal)) {
            modalde.hide();
        }
    });

    function showToast(message) {
        toast.find('p').text(message);
        toast.addClass('show');
        setTimeout(function () {
            toast.removeClass('show');
        }, 2000);
    }

    // Get the current date
    function getCurrentDate() {
        var currentDate = new Date();
        var dayOfWeek = ['Chủ Nhật', 'Thứ Hai', 'Thứ Ba', 'Thứ Tư', 'Thứ Năm', 'Thứ Sáu', 'Thứ Bảy'];
        var day = String(currentDate.getDate()).padStart(2, "0");
        var month = String(currentDate.getMonth() + 1).padStart(2, "0");
        var year = currentDate.getFullYear();
        var dayIndex = currentDate.getDay();
        var dayName = dayOfWeek[dayIndex];
        return dayName + ", " + day + "-" + month + "-" + year + " ";
    }

    document.getElementById('currentDate').innerText = getCurrentDate();

    // Open edit description modal
    function editDescription() {
        const descriptionModal = document.getElementById('editDescriptionModal');
        const descriptionInput = document.getElementById('descriptionInput');
        const currentDescription = document.getElementById('centreDescription').textContent;
        descriptionInput.value = currentDescription.trim();
        descriptionModal.style.display = 'block';
    }

    // Close edit description modal
    function closeEditModal() {
        const descriptionModal = document.getElementById('editDescriptionModal');
        descriptionModal.style.display = 'none';
    }

    // Save edited description
    function saveDescription() {
        const descriptionModal = document.getElementById('editDescriptionModal');
        const descriptionInput = document.getElementById('descriptionInput');
        const newDescription = descriptionInput.value;
        document.getElementById('centreDescription').innerHTML = `<p>${newDescription}</p>`;
        descriptionModal.style.display = 'none';
    }

    // Open delete modal for center
    function openDeleteModal() {
        const deleteModal = document.getElementById('deleteModal');
        deleteModal.style.display = 'block';
    }

    // Close delete modal
    function closeDeleteModal() {
        const deleteModal = document.getElementById('deleteModal');
        deleteModal.style.display = 'none';
    }

    // Deletion
    function deleteCenter() {
        const centerName = document.getElementById('centerName').textContent;
        showToast('Xóa thành công trung tâm ' + centerName);
        closeDeleteModal();
    }

    // Fetch center details and update DOM
//    function fetchCenterDetails() {
//        fetch("/manager/center/{centerId}")
//            .then(response => response.json())
//            .then(data => {
//                document.getElementById("currentDate").textContent = data.currentDate;
//                document.querySelector("h1").textContent = "Chi tiết trung tâm " + data.name;
//                document.querySelector("h3").textContent = data.centerUsers;
//                document.querySelector("#centreDescription p").textContent = data.description;
//            })
//            .catch(error => console.error("Error fetching center details:", error));
//    }

    // Save description via API
    function saveDescriptionAPI(description) {
        fetch("/updateCenterDescription", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({ description: description })
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.json();
            })
            .then(data => {
                console.log("Description updated successfully:", data);
            })
            .catch(error => {
                console.error("Error updating description:", error);
            });
    }

    // Handle save description button click
    function handleSaveDescription() {
        const description = document.getElementById("descriptionInput").value;
        saveDescriptionAPI(description);
    }

    // Fetch center details when the page loads

    // Add event listener to the save button
    document.querySelector("button[onclick='saveDescription()']").addEventListener("click", handleSaveDescription);

});
document.addEventListener("DOMContentLoaded", function () {
console.log("@!#!@ FDDASF ASAD");
    var userId = 43;
    const URL = "/manager/center/43";

    function fetchCenters() {
        fetch(URL)
            .then((response) => response.json())
            .then((data) => {
                console.log("Data received from API:", data);
                document.getElementById("centerDetailName").innerText = "Chi tiết trung tâm " + data.name;
                document.getElementById("centreDescription").innerText = data.description;
            })
            .catch((error) => console.error("Error fetching centers:", error));
    }
    fetchCenters();


    var noResultDiv = document.getElementById("no-result");
});
