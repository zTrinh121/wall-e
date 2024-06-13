//import { userList } from "../../../dummyData/userDemo.js";
//legendary combo: ctrl + shilf + F9
// Start: nva
$(document).ready(function () {
  $(".nva").click(function () {
    $(".wrapper").toggleClass("collapse");
  });
});
// End: nva

// Display user data
//userList.forEach((element) => {
//  console.log(element);
//});
//close-modal-applied-for-all
const closeButton = document.querySelector(".close");
//add event
closeButton.addEventListener("click", () => {
  // hide-modal
  addModal.style.display = "none";
});

const toggle = () => classList.toggle("active");

let profileDropdownList = document.querySelector(".profile-dropdown-list");
let btn = document.querySelector(".profile-dropdown-btn");

let classList = profileDropdownList.classList;

window.addEventListener("click", function (e) {
  if (!btn.contains(e.target)) classList.remove("active");
});

document.getElementById("currentDate").innerText = getCurrentDate();

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

// modal-up-detail-infor
document.addEventListener("DOMContentLoaded", (event) => {
  const modal = document.getElementById("myModal");
  const span = document.getElementsByClassName("close")[0];
  const modalDetail = document.getElementById("modal-detail");

  document.querySelectorAll(".open-modal-btn").forEach((button) => {
    button.addEventListener("click", () => {
      const detail = button.getAttribute("data-detail");
      console.log("Button clicked, detail:", detail);
      modalDetail.innerText = detail;
      modal.style.display = "block";
    });
  });

  span.onclick = function () {
    modal.style.display = "none";
  };

  window.onclick = function (event) {
    if (event.target == modal) {
      modal.style.display = "none";
    }
  };
});
//end

//Confirm delete center
// get by => '.delete-user'
function attachDeleteEvent() {
    const deleteButtons = document.querySelectorAll('.delete-user');
    const deleteModal = document.getElementById('deleteModal');
    const confirmDeleteButton = document.getElementById('confirmDelete');
    const cancelDeleteButton = document.getElementById('cancelDeleteButton');
    const centerNameSpan = document.getElementById('centerName');

    deleteButtons.forEach((button) => {
        button.addEventListener('click', (event) => {
            event.preventDefault(); // Ngăn chặn hành động mặc định
            const centerName = button.getAttribute('data-center-name');
            centerNameSpan.textContent = centerName;
            deleteModal.style.display = 'block';
        });
    });

    cancelDeleteButton.addEventListener('click', () => {
        deleteModal.style.display = 'none';
    });

    confirmDeleteButton.addEventListener('click', () => {
        const centerName = centerNameSpan.textContent;
        deleteModal.style.display = 'none';
        alert(`Trung tâm ${centerName} đã được xóa!`);
    });

    window.addEventListener('click', (event) => {
        if (event.target === deleteModal) {
            deleteModal.style.display = 'none';
        }
    });
}

//ADDING CENTER
     const addCentreBtn = document.getElementById('addCentreBtn');
        const addCentreModal = document.getElementById('addCentreModal');
        const cancelAddButton = document.getElementById('cancelAdd');
        const confirmAddButton = document.getElementById('confirmAdd');
        const addCentreForm = document.getElementById('addCentreForm');

//        // Modal-show-up
//        addCentreBtn.addEventListener('click', () => {
//            addCentreModal.style.display = 'block';
//        });

        // Hide-modal
        cancelAddButton.addEventListener('click', () => {
            addCentreModal.style.display = 'none';
        });

        // Confirm button
        addCentreForm.addEventListener('submit', (event) => {
            event.preventDefault();
            const centreName = document.getElementById('centreName').value;
            // thêm luôn vào db nếu admin nhấn từ chối -> xoá khỏi db hoặc admin confirm mới thêm vào db
            // cần hiện thông báo đã được thêm thành công hay gì đó hmm chắc là gửi về manager
            addCentreModal.style.display = 'none';
            showToast(`Đã gửi yêu cầu thêm trung tâm "${centreName}" thành công!`);
        });


//MODAL UP DETAIL INFO
//    document.addEventListener("DOMContentLoaded", function () {
//          // Modal for center details
//        var modal = document.getElementById("myModal");
//        var span = document.getElementsByClassName("close")[0];
//        var iframe = modal.querySelector("iframe");
//
//        // Open modal with iframe content
//        document.querySelectorAll(".openModalBtn").forEach(button => {
//            button.addEventListener("click", function () {
//                const centerId = this.getAttribute("data-center-id");
//                iframe.src = `/manager/center/${centerId}`;
//                modal.style.display = "block";
//            });
//        });
//
//        // Close modal
//        span.onclick = function () {
//            modal.style.display = "none";
//            iframe.src = "";
//        }
//
//        window.onclick = function (event) {
//            if (event.target == modal) {
//                modal.style.display = "none";
//                iframe.src = "";
//            }
//        }
//        });
        //


    //fetch-api
document.addEventListener("DOMContentLoaded", function () {
console.log("123")
//    var userId = 43;
    //const URL = "/manager/center/43";
    const URL = "/manager/centers";
        function fetchCenters() {
        fetch(URL)
            .then((response) => response.json())
            .then((data) => {
                console.log("Data received from API:", data);
                if (!Array.isArray(data)) {
                    data = [data];
                }
                displayCenterLists(data);
            })
            .catch((error) => console.error("Error fetching centers:", error));
    }

    fetchCenters();

    function displayCenterLists(centers) {
        var tableBody = document.getElementById("tableBody");
        if (!tableBody) {
            console.error("Element with ID 'tableBody' not found.");
            return;
        }

        if (!Array.isArray(centers)) {
            console.error("Expected an array but got:", centers);
            return;
        }

        tableBody.innerHTML = "";
        console.log(centers);
        console.log(centers.length === 0);

        if (centers.length === 0) {
            noResultDiv.style.display = "block";
        } else {
            noResultDiv.style.display = "none";
            centers.forEach((center) => {
                var row = `
                    <tr id="${center.id}">
                        <td><p>${center.name}</p></td>
                        <td><p>${center.createdAt}</p></td>
                        <td><p>${center.code}</p></td>
                        <td><p><a class="delete-user"><i class="fas fa-trash"></i></a></p></td>
                        <td><button class="openModalBtn">Xem</button></td>
                    </tr>
                `;
                tableBody.insertAdjacentHTML("beforeend", row);
            });
        }
    }

    var noResultDiv = document.getElementById("no-result");
    var currentPage = 1; // Current page number
});
