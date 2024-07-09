var centerIdz;

document.addEventListener("DOMContentLoaded", function () {
  const roleFilter = document.getElementById("subject");
  const tableBody = document.getElementById("tableBody");
//
//  function buildTable(data) {
//    tableBody.innerHTML = ""; // Clear existing table rows
//    data.forEach((e) => {
//      let ban = e.status == 1 ? "Hoạt động" : "Đã xoá";
//      let row = `<tr>
//                          <td><p>${e.name}</p></td>
//                          <td><p>${e.address}</p></td>
//                          <td><p><button class="openModalBtn">Xem</button></p</td>
//                     </tr>`;
//      tableBody.innerHTML += row;
//    });
//    // <td><button class="action-button">${ban}</button></td>
//    addEventListenersToButtons();
//  }
//
//  function filterTable() {
//    const selectedRole = roleFilter.value;
//    let roleEnum = 0; // Default to 'all'
//    if (selectedRole === "Maths") {
//      roleEnum = 1;
//    } else if (selectedRole === "English") {
//      roleEnum = 2;
//    } else if (selectedRole === "Literature") {
//      roleEnum = 3;
//    } else if (selectedRole === "Physics") {
//      roleEnum = 4;
//    }
//
//    const filteredData =
//      roleEnum === 0
//        ? filteredUserList
//        : filteredUserList.filter((user) => user.id === roleEnum);
//
//    buildTable(filteredData);
//  }

  // Listen button
//  function addEventListenersToButtons() {
//    const buttons = document.querySelectorAll(".action-button");
//    buttons.forEach((button) => {
//      button.addEventListener("click", function () {
//        const userName =
//          button.parentElement.parentElement.querySelector(
//            "td:first-child p"
//          ).innerText;
//        // Toggle ban status
//        const newStatus = button.innerText === "Xoá" ? "Hoạt động" : "Xoá";
//        // Update button text
//        button.innerText = newStatus;
//        showToast(newStatus, userName);
//      });
//    });
//  }

  function showToast(message, userName) {
    const toast = document.getElementById("toast");
    const toastMessage = document.getElementById("toastMessage");
    toastMessage.innerHTML = `<i class="fas fa-info"></i> Tài khoản ${userName} đã ${message.toLowerCase()}`;
    toast.className = "toast show";
    setTimeout(function () {
      toast.className = toast.className.replace("show", "");
    }, 3000);
  }

//  roleFilter.addEventListener("change", filterTable);

  // Initial table build

});
    //FETCH
document.addEventListener("DOMContentLoaded", () => {
    //get-stu-list
    const urlParams = window.location.href;
    console.log("Current URL:", urlParams);
    // Extract centerId from the URL
    const urlParts = urlParams.split("centerId=");
    centerIdz = urlParts.length > 1 ? urlParts[1].split("&")[0] : null;
    console.log("Center ID:", centerIdz);
    //fetch-api-for-stu
    function fetchStudents(centerIdz) {
      var URL = `/manager/students/${centerIdz}`;
      var nameURL = `/manager/center/${centerIdz}`;
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
              if (!Array.isArray(data)) {
                data = [data];
              }
              displayStudentLists(data, centerIdz);
          })
          .catch((error) => console.error("Error fetching centers:", error));

          //get-center-name-in-fetch
          fetch(nameURL)
            .then((getCTN) => {
                if (!getCTN.ok) {
                throw new Error("Network response was not ok");
            }
            return getCTN.json();
            })
            .then((ctn) => {
            document.getElementById("centerDetailName").innerText = "Quản lý học sinh của trung tâm " + ctn.name;
            })
            .catch((err) => console.error("loi lay ten center: ", err));
    }
    fetchStudents(centerIdz);

    //display-stu-into-list
function displayStudentLists(centers, centerIdz) {
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
      centers.forEach((center, index) => {
        index = sttIncreasing(index);
        var row = `
          <tr class="view-details" data-id="${center.id}">
            <td><p>${index}</p></td>
            <td><p>${center.name}</p></td>
            <td><p>${center.email}</p></td>
            <td><p>${center.address}</p></td>
            <td><p><button class="openModalBtn" data-id="${center.id}">Xem</button></p</td>
          </tr>
        `;
        tableBody.insertAdjacentHTML("beforeend", row);
      });
      // Reattach event listeners for new buttons
      document.querySelectorAll(".openModalBtn").forEach((button) => {
        button.addEventListener("click", function () {
          var stuId = this.getAttribute("data-id");
          console.log("student id from button is: " + stuId);
          openModalWithStudentDetails(stuId, centerIdz);
        });
      });
    }
  }
  //STT incremental
  function sttIncreasing(index){
    if(index === 0){
      index = 1;
      return index;
    } else{
      return ++index;
    }
  };

  //open-by-student-id
function openModalWithStudentDetails(stuId, centerIdz) {
    console.log(stuId);//id cua hv
    var queryUrl = "/manager/tthv?";
    queryUrl += "centerIdn=" + encodeURIComponent(centerIdz) + "?stuIdn=" + encodeURIComponent(stuId);
    console.log(queryUrl);
    //chuyen-huong-mode
    window.location.href = queryUrl;
}

var noResultDiv = document.getElementById("no-result");
});

//box-info-redirect + sidebar
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
                // Construct the correct URL based on centerId
                var url = `/manager/qlgv?centerId=`;
                url += encodeURIComponent(centerIdz);
                console.log(url); // Verify the constructed URL

                // Perform any further actions with the constructed URL
                window.location.href = url; // Example: Redirect to the constructed URL
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
                // Construct the correct URL based on centerId
                var url = `/manager/qlkh?centerId=`;
                url += encodeURIComponent(centerIdz);
                console.log(url); // Verify the constructed URL

                // Perform any further actions with the constructed URL
                window.location.href = url; // Example: Redirect to the constructed URL
        });
    } else {
        console.error("Element with id 'boxInfoc' not found.");
    }
});
//money
document.addEventListener("DOMContentLoaded", function () {
    var boxInfom = document.getElementById("boxInfom");

    if (boxInfom) {
        boxInfom.addEventListener("click", function(event) {
            event.preventDefault();
                var url = `/manager/dthu?centerId=`;
                url += encodeURIComponent(centerIdz);
                console.log(url);
                window.location.href = url;
        });
    } else {
        console.error("Element with id 'boxInfom' not found.");
    }
});
// sidebarPost

// sidebarNoti

//  sidebarTime
document.addEventListener("DOMContentLoaded", function () {
    var sidebarTime = document.getElementById("sidebarTime");
    if (sidebarTime) {
        sidebarTime.addEventListener("click", function(event) {
            event.preventDefault();
                var url = `/manager/centerTime?centerId=`;
                url += encodeURIComponent(centerIdz);
                console.log(url);
                window.location.href = url;
        });
    } else {
        console.error("Element with id 'sidebarTime' not found.");
    }
});






