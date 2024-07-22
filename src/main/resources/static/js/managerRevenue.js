//document.addEventListener("DOMContentLoaded", () => {
    const ctx = document.getElementById('myChart');

    fetch('/manager/monthly-revenue?month=7&year=2024&centerId=1')
    .then(function(response){
        if(response.ok == true){
            return response.json();
        }
    })
    .then(function(data){
        renderChart(data);
        console.log(data);
    });
//        labels: ['Tháng 1', 'Tháng 2', 'Tháng 3', 'Tháng 4', 'Tháng 5', 'Tháng 6', 'Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10', 'Tháng 11', 'Tháng 12'],

function renderChart(data){
    new Chart(ctx, {
      type: 'line',
      data: {
        labels: ['Tháng 7', 'Tháng 8', 'Tháng 9', 'Tháng 10', 'Tháng 11', 'Tháng 12'],
//        labels: data.map(row => "Thang" + row.Month),
        datasets: [
          {
            label: 'Teacher Payment',
            data: [120, 190, 300, 250, 220, 430, ],
            borderWidth: 1
          },
          {
            label: 'Course Earning',
            data: [140, 220, 150, 340, 320, 850],
            borderWidth: 1
          }
        ]
      },
      options: {
        scales: {
          y: {
            beginAtZero: true
          }
        }
      }
    });
}

//});

document.addEventListener("DOMContentLoaded", () => {
    const urlParams = window.location.href;
    console.log("Current URL:", urlParams);
    // Extract centerId from the URL
    const urlParts = urlParams.split("centerId=");
    centerIdz = urlParts.length > 1 ? urlParts[1].split("&")[0] : null;
    console.log("Center ID:", centerIdz);
});

document.addEventListener("DOMContentLoaded", () => {
    // Toggle sidebar collapse
    $(".nva").click(function () {
        $(".wrapper").toggleClass("collapse");
    });
});

//box-info-redirect + sidebar
//students
document.addEventListener("DOMContentLoaded", function () {
    var boxInfos = document.getElementById("boxInfos");

    if (boxInfos) {
        boxInfos.addEventListener("click", function(event) {
            event.preventDefault();
                var url = `/manager/centerTime?centerId=`;
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
//document.addEventListener("DOMContentLoaded", function () {
//    var sidebarTime = document.getElementById("sidebarTime");
//    if (sidebarTime) {
//        sidebarTime.addEventListener("click", function(event) {
//            event.preventDefault();
//                var url = `/manager/centerTime?centerId=`;
//                url += encodeURIComponent(centerIdz);
//                console.log(url);
//                window.location.href = url;
//        });
//    } else {
//        console.error("Element with id 'sidebarTime' not found.");
//    }
//});

//lay so luong
document.addEventListener("DOMContentLoaded", () => {
    var urlParams = window.location.href;
    console.log("Current URL:", urlParams);
    // Extract centerId from the URL
    var urlParts = urlParams.split("centerId=");
    const centerIdzzz = urlParts.length > 1 ? urlParts[1].split("&")[0] : null;

function fetchCount(centerIdzzz) {
    var URLs = `/manager/center/${centerIdzzz}`;
    var URLt = `/manager/teacher-count/${centerIdzzz}`;
    var URLc = `/manager/course-count/${centerIdzzz}`;

    fetch(URLs)
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.json();
            })
            .then((data) => {
                document.getElementById("centerCode").innerText = data.code;
            })
            .catch((error) => console.error("Error fetching centers:", error));
    fetch(URLt)
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.json();
            })
            .then((data) => {
                document.getElementById("numt").innerText = data;
            })
            .catch((error) => console.error("Error fetching centers:", error));
    fetch(URLc)
            .then((response) => {
                if (!response.ok) {
                    throw new Error("Network response was not ok");
                }
                return response.json();
            })
            .then((data) => {
                  document.getElementById("numc").innerText = data;

            })
            .catch((error) => console.error("Error fetching centers:", error));
}
fetchCount(centerIdzzz);
});