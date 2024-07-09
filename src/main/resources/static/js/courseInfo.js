document.addEventListener("DOMContentLoaded", function () {
    //Get information from URL
    // Get the current URL
    var urlParams = window.location.href;
    console.log("Current URL:", urlParams);

    // Extract centerId from the URL
    var urlCenParts = urlParams.split("centerIdn=");
    var centerIdz = urlCenParts.length > 1 ? urlCenParts[1].split(/[?&]/)[0] : null;

    // Extract courseId from the URL
    var urlCourseParts = urlParams.split("cidn=");
    var courseIdz = urlCourseParts.length > 1 ? urlCourseParts[1].split("&")[0] : null;

    console.log("Center ID:", centerIdz);
    console.log("Course ID:", courseIdz);
//End getting
//turnBack
    var turnBack = document.getElementById("turnBack");

    turnBack.addEventListener("click", function(event) {
        event.preventDefault();
            var url = `/manager/qlkh?centerId=`;
            url += encodeURIComponent(centerIdz);
            window.location.href = url;
    });
//Ending


//Fetch API
    function fetchCourses(centerIdz, courseIdz) {
          let urlC = `/manager/center/${centerIdz}`;
            console.log(urlC);
          fetch(urlC)
            .then((response) => {
                if(!response.ok){
                    throw new Error("Network response was not ok");
                }
                return response.json();
            })
            .then((data) => {
                console.log("Data received from API:", data);
                document.getElementById("centerCourseName").innerText = data.name;
            })
            .catch((erz) => console.error("loi lay ten center: ", erz));

      var URL = `/manager/course/${courseIdz}`;
      console.log(URL);
      //get-center-name-in-fetch
      fetch(URL)
        .then((response) => {
            if (!response.ok) {
                throw new Error("Network response was not ok");
            }
            return response.json();
        })
        .then((data) => {
        console.log("Data received from API:", data);
        document.getElementById("courseDetailName").innerText = "Thông tin lớp " + data.name;
        document.getElementById("courseDetailName2").innerText = data.code;
        document.getElementById("courseSubject").innerText = data.subjectName;
        document.getElementById("courseDecs").innerText = data.description;
        document.getElementById("teaCourseName").innerText = data.teacherName;
        document.getElementById("coursePrice").innerText = data.courseFee;
        })
        .catch((err) => console.error("loi lay course: ", err));
    }
    fetchCourses(centerIdz, courseIdz);
    //Getting real gender
    function returnGender(g){
        if(g)   return "Nam";
        if(!g)  return "Nữ";
    }
//End fetching

//Date
    document.getElementById("currentDate").innerText = getCurrentDate();
    function getCurrentDate() {
            var currentDate = new Date();
            var dayOfWeek = [
              "Chủ Nhật",
              "Thứ Hai",
              "Thứ Ba",
              "Thứ Tư",
              "Thứ Năm",
              "Thứ Sáu",
              "Thứ Bảy",
            ];
            var day = String(currentDate.getDate()).padStart(2, "0");
            var month = String(currentDate.getMonth() + 1).padStart(2, "0");
            var year = currentDate.getFullYear();
            var dayIndex = currentDate.getDay();
            var dayName = dayOfWeek[dayIndex];
            return dayName + ", " + day + "-" + month + "-" + year;
          }
//End date
});

