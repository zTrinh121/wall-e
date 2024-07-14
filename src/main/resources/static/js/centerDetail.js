
var centerIdz;
document.addEventListener("DOMContentLoaded", () => {
    // Toggle sidebar collapse
    $(".nva").click(function () {
        $(".wrapper").toggleClass("collapse");
    });
});
// Định nghĩa hàm showToast ở phạm vi toàn cục
function showToast(message) {
    var toast = $('#toast'); // Chuyển toast vào trong hàm
    toast.find('p').text(message);
    toast.addClass('show');
    setTimeout(function () {
        toast.removeClass('show');
    }, 2000);
}

$(document).ready(function () {
    showToast('This is a toast message!');

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

});
// Define fetchCenters function in the global scope
function fetchCenters(centerIdz) {
    let URL = `/manager/center/${centerIdz}`;
    console.log(URL);

    fetch(URL)
        .then((response) => {
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            return response.json();
        })
        .then((data) => {
            console.log("Data received from API:", data);
            document.getElementById("centerDetailName").innerText = "Chi tiết trung tâm " + data.name;
            document.getElementById("centreDescription").innerText = data.description;
            document.getElementById("centerName").innerText = data.name;
            document.getElementById
        })
        .catch((error) => console.error("Error fetching centers:", error));
}

// DOMContentLoaded event listener
document.addEventListener("DOMContentLoaded", function () {
    var managerId = document.getElementById("managerId"); // Assuming you meant to get an element with an ID
    // Get the current URL
    var urlParams = window.location.href;
    console.log("Current URL:", urlParams);
    // Extract centerId from the URL
    var urlParts = urlParams.split("centerIdn=");
    centerIdz = urlParts.length > 1 ? urlParts[1].split("&")[0] : null;
    console.log("Center ID:", centerIdz);

    // Call fetchCenters function with the extracted centerIdz
    fetchCenters(centerIdz);

    var noResultDiv = document.getElementById("no-result");
});
//box-info-redirect
//students
document.addEventListener("DOMContentLoaded", function () {
    var boxInfos = document.getElementById("boxInfos");

    if (boxInfos) {
        boxInfos.addEventListener("click", function(event) {
            event.preventDefault();
                var url = `/manager/qlhv?centerId=`;
                url += encodeURIComponent(centerIdz);
                console.log(url);
                window.location.href = url;
        });
    } else {
        console.error("Element with id 'boxInfos' not found.");
    }
});
//teachers
document.addEventListener("DOMContentLoaded", function () {
    var boxInfot = document.getElementById("boxInfot");

    if (boxInfot) {
        boxInfot.addEventListener("click", function(event) {
            event.preventDefault();
                var url = `/manager/qlgv?centerId=`;
                url += encodeURIComponent(centerIdz);
                console.log(url);
                window.location.href = url;
        });
    } else {
        console.error("Element with id 'boxInfot' not found.");
    }
});
//courses
document.addEventListener("DOMContentLoaded", function () {
    var boxInfoc = document.getElementById("boxInfoc");

    if (boxInfoc) {
        boxInfoc.addEventListener("click", function(event) {
            event.preventDefault();
                var url = `/manager/qlkh?centerId=`;
                url += encodeURIComponent(centerIdz);
                console.log(url);

                window.location.href = url;
        });
    } else {
        console.error("Element with id 'boxInfoc' not found.");
    }
});
//edit + delete
        //edit
document.addEventListener("DOMContentLoaded", () => {
    // Open edit description modal
    var editDescription = document.getElementById('editDescription');
    var descriptionModal = document.getElementById('editDescriptionModal');
    var descriptionInput = document.getElementById('descriptionInput');
    var currentDescription = document.getElementById('centreDescription').textContent;
    var saveDescription = document.getElementById('saveDescription');
    var closeEditDescModal = descriptionModal.querySelector('.close');
    const urlParams = window.location.href;
    const urlParts = urlParams.split("centerIdn=");
    var centerIdzz = urlParts.length > 1 ? urlParts[1].split("&")[0] : null;
    // Click event
    editDescription.addEventListener("click", () => {
        descriptionInput.value = centreDescription.textContent.trim();
        descriptionModal.style.display = 'block';
    });
    // Close desc
    closeEditDescModal.addEventListener("click", () => {
        descriptionModal.style.display = 'none';
    });
    // Save desc
    saveDescription.addEventListener("click", () => {
        const newDescription = descriptionInput.value.trim();
        centreDescription.textContent = newDescription;
        descriptionModal.style.display = 'none';
        // save into api
        console.log(centerIdzz);
        saveDescriptionAPI(newDescription, centerIdzz);
    });

// put api passing newDesc
    function saveDescriptionAPI(description, centerIdzz) {
        fetch(`/manager/center/update/${centerIdzz}`, {
            method: "PUT",
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

    // Close modal if clicked outside of the modal content
    window.addEventListener("click", (event) => {
        if (event.target == descriptionModal) {
            descriptionModal.style.display = 'none';
        }
    });
});
        //dele
document.addEventListener("DOMContentLoaded", () => {
    var urlParams = window.location.href;
    var urlParts = urlParams.split("centerIdn=");
    centerIdz = urlParts.length > 1 ? urlParts[1].split("&")[0] : null;
    console.log("Center ID:", centerIdz);

    //CONFIRM - DELETE
//    confirmDele.addEventListener("click", () => {
//        if (centerIdz) {
//            console.log("Confirm delete for center ID:", centerIdz);
//            fetch(`/manager/center/delete/${centerIdz}`, {
//                method: "DELETE"
//            })
//            .then(response => {
//                if (response.ok) {
//                    deleteModaling.style.display = "none";
//                    showToast("Xóa thành công trung tâm");
//                    console.log("Center deleted successfully");
//                } else {
//                    console.error("Error deleting center:", response.statusText);
//                }
//            })
//            .catch(error => console.error("Error deleting center:", error));
//        }
//    });
});







