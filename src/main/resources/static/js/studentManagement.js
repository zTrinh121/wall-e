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
//    const urlParams = window.location.href;
//    console.log(urlParams);
//    const centerIdz = urlParams.get('centerIdn');
//    console.log(centerIdz);
    // Get the current URL
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
              displayStudentLists(data);
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
function displayStudentLists(centers) {
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
          <tr class="view-details" data-id="${center.id}">
            <td><p>${center.code}</p></td>
            <td><p>${center.name}</p></td>
            <td><p>${center.address}</p></td>
            <td><p><button class="openModalBtn"><a href="" id="stuInfo">Xem</a></button></p</td>
          </tr>
        `;
        tableBody.insertAdjacentHTML("beforeend", row);
      });
      //get-st-id
          var stuInfo = document.getElementById("stuInfo");
          console.log("stuinfofofofof" + stuInfo);
          if (stuInfo) {
              stuInfo.addEventListener("click", function(event) {
                  event.preventDefault();
                      // Construct the correct URL based on centerId
                      var url = `/manager/qlgv?centerId=`;
                      url += encodeURIComponent(centerIdz);
                      console.log(url); // Verify the constructed URL

                      // Perform any further actions with the constructed URL
                      window.location.href = url; // Example: Redirect to the constructed URL
              });
          } else {
              console.error("Element with id 'stuInfo' not found.");
          }

      // Reattach event listeners for new buttons
      document.querySelectorAll(".open-modal-btn").forEach((button) => {
        button.addEventListener("click", function () {
          var centerId = this.getAttribute("data-id");
          openModalWithStudentDetails(centerId);
        });
      });
    }
  }

  //open-by-student-id
function openModalWithStudentDetails(centerId) {
    console.log(centerId);
    // Construct the URL for fetching center details
    var url = `/manager/students/${centerId}`;

    // Fetch center details by ID and display in modal
    fetch(url)
        .then((response) => response.json())
        .then((data) => {
            console.log("Center details:", data);
            console.log(encodeURIComponent(data.id));

            var queryUrl = "/manager/qlhv?";
            queryUrl += "centerIdn=" + encodeURIComponent(data.id);
            console.log(queryUrl);

            // Update the iframe source with the constructed URL
            var iframe = document.querySelector("#studentDetailModal iframe");
            iframe.src = queryUrl;

            // Display the modal
            var modal = document.getElementById("studentDetailModal");
            modal.style.display = "block";
        })
        .catch((error) => console.error("Error fetching center details:", error));
}

var noResultDiv = document.getElementById("no-result");
});

//box-info-redirect
//students
document.addEventListener("DOMContentLoaded", function () {
    var boxInfos = document.getElementById("boxInfos");

    if (boxInfos) {
        boxInfos.addEventListener("click", function(event) {
            event.preventDefault();
                // Construct the correct URL based on centerId
                var url = `/manager/qlhv?centerId=`;
                url += encodeURIComponent(centerIdz);
                console.log(url); // Verify the constructed URL

                // Perform any further actions with the constructed URL
                window.location.href = url; // Example: Redirect to the constructed URL
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
//student-info-redirect
//document.addEventListener("DOMContentLoaded", function () {
//    var stuInfo = document.getElementById("stuInfo");
//    console.log("stuinfofofofof" + stuInfo);
//    if (stuInfo) {
//        stuInfo.addEventListener("click", function(event) {
//            event.preventDefault();
//                // Construct the correct URL based on centerId
//                var url = `/manager/qlgv?centerId=`;
//                url += encodeURIComponent(centerIdz);
//                console.log(url); // Verify the constructed URL
//
//                // Perform any further actions with the constructed URL
//                window.location.href = url; // Example: Redirect to the constructed URL
//        });
//    } else {
//        console.error("Element with id 'stuInfo' not found.");
//    }
//});






